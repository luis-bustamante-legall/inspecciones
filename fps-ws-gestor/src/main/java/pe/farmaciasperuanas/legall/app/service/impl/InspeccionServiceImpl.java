package pe.farmaciasperuanas.legall.app.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import pe.farmaciasperuanas.legall.app.model.*;
import pe.farmaciasperuanas.legall.app.repository.*;
import pe.farmaciasperuanas.legall.app.service.BaseService;
import pe.farmaciasperuanas.legall.app.service.InspeccionService;
import pe.farmaciasperuanas.legall.core.exception.NotFoundException;
import pe.farmaciasperuanas.legall.core.util.Constantes;
import pe.farmaciasperuanas.legall.core.util.Util;
import pe.farmaciasperuanas.legall.dto.request.Inspeccion;
import pe.farmaciasperuanas.legall.dto.request.VehiculoAsegurado;

public class InspeccionServiceImpl extends BaseService implements InspeccionService{

	@Inject
	InspeccionRepository inspeccionRepository;
	
	@Inject
	VehiculoAseguradoRepository vehiculoAseguradoRepository;
	
	@Inject
	MarcaRepository marcaRepository;
	
	@Inject
	ModeloRepository modeloRepository;

	@Inject
	EmpleadoRepository empleadoRepository;

	@Inject
	InformeRepository informeRepository;

	@Override
	public Boolean saveInspecction(List<Inspeccion> insp, Integer idTramite, String usuario) {
		List<InspeccionModel> iModel = insp.stream().map(x -> InspeccionModel.builder()
				.codigo_inspeccion_legall(x.getCodigo_inspeccion())
				.codigo_inspeccion(this.generarCodigoInspeccion())
				.id_ct_estado_inspeccion(Constantes.ID_CT_ESTADO_INSPECCION_PENDIENTE)
				.direccion_inspeccion(x.getDireccion_inspeccion())
				.id_distrito(x.getId_distrito())
				.id_vehiculo_asegurado(this.saveUpdateVehiculo(x.vehiculoAsegurado))
				.id_tramite(idTramite)
				.id_empleado_inspector(obtenerIdEmpleado(usuario))
				.usuario_creacion(usuario)
				.fecha_creacion(LocalDateTime.now())
				.build()).collect(Collectors.toList());
		if(Util.isNotNull(iModel)) {
			logger.info("[INFO]-[INSPECCION]: Guardando Inspeccion...");
			iModel.forEach(x -> {
				InspeccionModel inspeccion =  inspeccionRepository.save(x);
				guardarInforme(inspeccion, usuario);
			});
			return Boolean.TRUE;
		}
		else {
			return Boolean.FALSE;
		}
	}

	@Override
	public Integer saveUpdateVehiculo(VehiculoAsegurado vehiculo) {
		VehiculoModel vModel = modelMapper.map(vehiculo, VehiculoModel.class);
		if(Util.isNotNull(validarVehiculo(vehiculo))) { 
			Long id = validarVehiculo(vehiculo).getId_vehiculo_asegurado();
			vModel.setId_vehiculo_asegurado(id);
		}
		MarcaModel marca = validarMarca(vehiculo.getMarca());
		ModeloModel modelo = validarModelo(vehiculo.getModelo());
		vModel.setFecha_creacion(LocalDateTime.now());
		vModel.setUsuario_creacion(Constantes.USUARIO_DEFAULT);
		vModel.setPlaca(vehiculo.getPlaca());
		if(Util.isNotNull(modelo) && Util.isNotNull(marca)) {
			vModel.setIdModelo(modelo.getIdModelo().intValue());
			return vehiculoAseguradoRepository.save(vModel).getId_vehiculo_asegurado().intValue();
		}
		return null;
	}
	
	private VehiculoModel validarVehiculo(VehiculoAsegurado vehiculo) throws NotFoundException{
		logger.info("[INFO]-[VEHICULO]: Validando informacion...");
		Optional<VehiculoModel> x = vehiculoAseguradoRepository.findByPlaca(vehiculo.getPlaca());
		return (x.isPresent()) ? x.get() : x.orElse(null);
	}
	
	private MarcaModel validarMarca(String marca) throws NotFoundException{
		logger.info("[INFO]-[MARCA]: Validando informacion...");
		Optional<MarcaModel> x = marcaRepository.findByNombre(marca).stream().findFirst();
		return (x.isPresent()) ? x.get() : x.orElseThrow(() -> new NotFoundException("La marca del vehiculo no se encuentra registrado"));
	}
	
	private ModeloModel validarModelo(String modelo) throws NotFoundException {
		logger.info("[INFO]-[MODELO]: Validando informacion...");
		Optional<ModeloModel> x = modeloRepository.findByNombre(modelo).stream().findFirst();
		return (x.isPresent()) ? x.get() : x.orElseThrow(() -> new NotFoundException("El modelo de vehiculo no se encuentra registrado"));
	}

	private String generarCodigoInspeccion(){
		//Formato (IVE+ANYO+MES+DIA+0001) -> TRA210218001
		return  "IVE"+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))+"001";
	}

	private Integer obtenerIdEmpleado(String username){
		logger.info("[INFO]-[EMPLEADO]: Obteniendo informacion de Empleado...");
		return empleadoRepository.findByUsername(username).getIdEmpleado().intValue();
	}

	private void guardarInforme (InspeccionModel inspeccion, String usuario){
		InformeModel informe = new InformeModel();
		informe.setIdInspeccion(inspeccion.getId_inspeccion().intValue());
		informe.setIdEmpleado(obtenerIdEmpleado(usuario));
		informe.setIdDistritoAtencion(inspeccion.getId_distrito());
		informe.setDireccionAtencion(inspeccion.getDireccion_inspeccion());
		informe.setIdCtMotivo(Constantes.MOTIVO_INFORME_INSPECCION_PROGRAMADO);
		informe.setUsuarioCreacion(usuario);
		informe.setFechaCreacion(LocalDateTime.now());
		informeRepository.save(informe);
	}
}
