package pe.farmaciasperuanas.legall.app.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import pe.farmaciasperuanas.legall.app.model.TramiteModel;
import pe.farmaciasperuanas.legall.app.repository.TramiteRepository;
import pe.farmaciasperuanas.legall.app.service.BaseService;
import pe.farmaciasperuanas.legall.app.service.GestorService;
import pe.farmaciasperuanas.legall.app.service.InspeccionService;
import pe.farmaciasperuanas.legall.app.service.ValidacionService;
import pe.farmaciasperuanas.legall.config.FirebaseConfig;
import pe.farmaciasperuanas.legall.core.util.Constantes;
import pe.farmaciasperuanas.legall.core.util.Util;
import pe.farmaciasperuanas.legall.dto.request.GestorRequest;
import pe.farmaciasperuanas.legall.dto.response.BaseErrorResponse;
import pe.farmaciasperuanas.legall.dto.response.BaseResponse;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class GestorServiceImpl extends BaseService implements GestorService {

    @Inject
    ValidacionService validacionService;

    @Inject
    InspeccionService inspeccionService;

    @Inject
    TramiteRepository tramiteRepository;

    @Override
    public BaseResponse<String> save(GestorRequest request) {
        String mensajeError = "";
        List<BaseErrorResponse> lista = new ArrayList<>();
        TramiteModel tModel = new TramiteModel();
        if(Util.isNotNull(validarTramite(request.getNro_tramite_compania_seguro()))){
            return new BaseResponse<>(Constantes.ESTADO_ERROR, Constantes.MENSAJE_TRAMITE_DUPLICADO, Constantes.MENSAJE_TRAMITE_DUPLICADO_TECNICO);
        }
        tModel.setNumeroTramiteSeguro(request.getNro_tramite_compania_seguro());
        tModel.setCodigo_tramite_legall(request.getCodigo_tramite_legall());
        tModel.setFecha_asignacion_compania_seguro(request.getFecha_asignacion_compania_seguro());
        tModel.setObservacion_programacion(request.getObservacion_programacion());
        tModel.setId_ct_estado_tramite(Constantes.ID_CT_ESTADO_TRAMITE_PENDIENTE);
        tModel.setId_empresa_cliente(request.getId_empresa());
        tModel.setCodigo_tramite(this.generarCodigoTramite());
        tModel.setFecha_creacion(LocalDateTime.now());
        tModel.setUsuario_creacion(request.getUsuario());
        try {
            tModel.setId_contratante(validacionService.saveUpdateContratante(request.contratante, request));
            tModel.setId_broker(validacionService.saveUpdateBroker(request.broker, request));
            validacionService.saveUpdateAsegurado(request.asegurado, request);
            TramiteModel tramite = tramiteRepository.save(tModel);
            logger.info("[INFO]-[TRAMITE]: Tramite guardado...");
            if (Util.isNotNull(tramite)) {
                logger.info("[INFO]-[INSPECCION]: Guardando Inspeccion...");
                this.inspeccionService.saveInspecction(request.inspeccion, tramite.getId_tramite().intValue(), request.getUsuario());
                Map<String, Object> map = new HashMap<>();
                map.put("current_date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                map.put("insured_name", Constantes.NUEVO_REGISTRO);
                map.put("plate", request.getNro_tramite_compania_seguro());
                sincronizarFirebase(map);
                return new BaseResponse<>(Constantes.ESTADO_OK, Constantes.MENSAJE_GUARDADO_EXITO);
            }
        } catch (Exception e) {
            mensajeError = e.getMessage();
            lista.add(new BaseErrorResponse(mensajeError));
        }
        return new BaseResponse<>(Constantes.ESTADO_ERROR, Constantes.MENSAJE_SAVE_ERROR, lista);
    }

    private String generarCodigoTramite(){
        //Formato (TRA+ANYO+MES+DIA+0001) -> TRA210218001
        return  "TRA"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))+"001";
    }

    private TramiteModel validarTramite(String numero){
        Optional<TramiteModel> tramite = tramiteRepository.findByNumeroTramiteSeguro(numero);
        return (tramite.isPresent()) ? tramite.get() : tramite.orElse(null);
    }

    private void sincronizarFirebase(Map<String, Object> map) throws ExecutionException, InterruptedException, IOException {
        logger.info("[INSPECCION]: Sincronizando Notificacion en FIREBASE...");
        FirebaseConfig firebaseConfig = new FirebaseConfig();
        ApiFuture<QuerySnapshot> query =  firebaseConfig.getFirestoreConnection().collection(Constantes.COLECTION_NOTIFICATION).get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
             documents.stream().collect(Collectors.toList()).forEach(x -> {
                 firebaseConfig.getFirestoreConnection().collection(Constantes.COLECTION_NOTIFICATION)
                         .document(x.getId()).update(map);
             });
        firebaseConfig.closeConection();
    }

}
