package pe.farmaciasperuanas.legall.app.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Inject;

import pe.farmaciasperuanas.legall.app.model.AseguradoModel;
import pe.farmaciasperuanas.legall.app.model.BrokerModel;
import pe.farmaciasperuanas.legall.app.model.ContratanteModel;
import pe.farmaciasperuanas.legall.app.repository.AseguradoRepository;
import pe.farmaciasperuanas.legall.app.repository.BrokerRepository;
import pe.farmaciasperuanas.legall.app.repository.ContratanteRepository;
import pe.farmaciasperuanas.legall.app.service.BaseService;
import pe.farmaciasperuanas.legall.app.service.ValidacionService;
import pe.farmaciasperuanas.legall.core.util.Constantes;
import pe.farmaciasperuanas.legall.core.util.Util;
import pe.farmaciasperuanas.legall.dto.request.Asegurado;
import pe.farmaciasperuanas.legall.dto.request.Broker;
import pe.farmaciasperuanas.legall.dto.request.Contratante;
import pe.farmaciasperuanas.legall.dto.request.GestorRequest;

public class ValidacionServiceImpl extends BaseService implements ValidacionService {

	@Inject
	ContratanteRepository contratanteRepository;

	@Inject
	BrokerRepository brokerRepository;
	
	@Inject
	AseguradoRepository aseguradoRepository;

	@Override
	public Integer saveUpdateContratante(Contratante contratante, GestorRequest request) {
		ContratanteModel cModel = modelMapper.map(contratante, ContratanteModel.class);
		if (Util.isNotNull(validarContratante(contratante))) {
			Long id = validarContratante(contratante).getId_contratante();
			cModel.setId_contratante(id);
		}
		cModel.setFecha_creacion(LocalDateTime.now());
		cModel.setUsuario_creacion(request.getUsuario());
		cModel.setNombre("");
		cModel.setNumeroRuc(contratante.getNumero_ruc());
		return contratanteRepository.save(cModel).getId_contratante().intValue();
	}

	private ContratanteModel validarContratante(Contratante contratante) {
		logger.info("[INFO]-[CONTRATANTE]: Validando numero de ruc...");
		Optional<ContratanteModel> x = contratanteRepository.findByNumeroRuc(contratante.getNumero_ruc());
		return (x.isPresent()) ? x.get() : x.orElse(null);
	}

	@Override
	public Integer saveUpdateBroker(Broker broker, GestorRequest request) {
		BrokerModel bModel = modelMapper.map(broker, BrokerModel.class);
		if (Util.isNotNull(validarBroker(broker))) {
			return validarBroker(broker).getId_broker().intValue();
		}
		bModel.setRazonSocial(broker.getRazon_social());
		bModel.setActivo(Constantes.ESTADO_ACTIVO);
		return brokerRepository.save(bModel).getId_broker().intValue();
	}

	private BrokerModel validarBroker(Broker broker) {
		logger.info("[INFO]-[BROKER]: Validando informacion...");
		Optional<BrokerModel> x = brokerRepository.findByRazonSocial(broker.getRazon_social());
		return (x.isPresent()) ? x.get() : x.orElse(null);
	}

	@Override
	public Integer saveUpdateAsegurado(Asegurado asegurado, GestorRequest request) {
		AseguradoModel aModel = modelMapper.map(asegurado, AseguradoModel.class);
		if(Util.isNotNull(validarAsegurado(asegurado))) {
			return validarAsegurado(asegurado).getId_asegurado().intValue();
		}
		aModel.setNombre(request.asegurado.getNombre());
		aModel.setApellidoPaterno(asegurado.getApellido_paterno());
		aModel.setApellidoMaterno(asegurado.getApellido_materno());
		aModel.setIdDistrito(Integer.parseInt(asegurado.getId_distrito()));
		aModel.setFecha_creacion(LocalDateTime.now());
		aModel.setUsuario_creacion(request.getUsuario());
		aModel.setId_ct_tipo_documento(Constantes.DOCUMENTO_DNI);
		return aseguradoRepository.save(aModel).getId_asegurado().intValue();
	}

	private AseguradoModel validarAsegurado(Asegurado asegurado) {
		logger.info("[INFO]-[ASEGURADO]: Validando informacion...");
		Optional<AseguradoModel> x = aseguradoRepository.findByNombreAndApellidoPaternoAndApellidoMaterno(
				asegurado.getNombre(), asegurado.getApellido_paterno(), asegurado.getApellido_materno());
		return (x.isPresent()) ? x.get() : x.orElse(null);
	}

}
