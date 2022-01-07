package com.farmaciasperuanas.inspector.app.service.impl;

import com.blazebit.persistence.deltaspike.data.Page;
import com.blazebit.persistence.deltaspike.data.PageRequest;
import com.blazebit.persistence.deltaspike.data.Pageable;
import com.blazebit.persistence.deltaspike.data.Sort;
import com.farmaciasperuanas.inspector.app.model.*;
import com.farmaciasperuanas.inspector.app.repository.*;
import com.farmaciasperuanas.inspector.app.service.InspeccionService;
import com.farmaciasperuanas.inspector.config.FirebaseConfig;
import com.farmaciasperuanas.inspector.dto.request.FinalizarInspeccionRequest;
import com.farmaciasperuanas.inspector.dto.request.InspeccionFiltroRequest;
import com.farmaciasperuanas.inspector.dto.request.ReprogramarInspeccionRequest;
import com.farmaciasperuanas.inspector.dto.response.BaseResponse;
import com.farmaciasperuanas.inspector.dto.response.CollectionResponse;
import com.farmaciasperuanas.inspector.util.Constantes;
import com.farmaciasperuanas.inspector.util.Util;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


public class InspeccionServiceImpl implements InspeccionService {

    @Inject
    Logger logger;

    @Inject
    InspeccionViewRepository inspeccionRepository;

    @Inject
    InspeccionModelRepository inspRepository;

    @Inject
    InformeRepository informeRepository;

    @Inject
    AseguradoRepository aseguradoRepository;

    @Inject
    VehiculoAseguradoRepository vehiculoRepository;

    // Filtro, ordenamiento y paginacion.
    @Override
    public CollectionResponse<InspeccionView> findAllInspeccion(InspeccionFiltroRequest request, Integer pagina, Integer size) {
        logger.info("[INSPECCION]: Iniciando lista paginada...");
        Util.toUpperCase(request);
        Pageable pageable = new PageRequest(pagina, size, Sort.Direction.DESC, "id");
        Page<InspeccionView> page = inspeccionRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.and(cb.equal(root.get("idCtMotivo"), "TMII005")));
            if (Util.isNotNull(request.getCodigoInspeccionLegall())) {
                predicates.add(cb.and(cb.like(root.get("codigoInspeccionLegall"), "%".concat(request.getCodigoInspeccionLegall()).concat("%"))));
            }
            if (Util.isNotNull(request.getPlaca())) {
                predicates.add(cb.and(cb.like(cb.lower(root.get("placa")), "%".concat(request.getPlaca().toLowerCase()).concat("%"))));
            }
            if (Util.isNotNull(request.getEstado())) {
                predicates.add(cb.and(cb.like(cb.lower(root.get("estado")), "%".concat(request.getEstado().toLowerCase()).concat("%"))));
            }
            if (Util.isNotNull(request.getFechaDesde()) && !Util.isNotNull(request.getFechaHasta())) {
                predicates.add(cb.and(cb.between(root.get("fechaProgramada"), request.getFechaDesde(), request.getFechaDesde().plusDays(1))));
            }
            if (Util.isNotNull(request.getFechaDesde()) && Util.isNotNull(request.getFechaHasta())) {
                predicates.add(cb.and(cb.between(root.get("fechaProgramada"),
                        request.getFechaDesde(), request.getFechaHasta())));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
        return new CollectionResponse<>(page.getContent(), page.getTotalElements(),
                page.getNumber() + 1, page.getTotalPages());
    }

    @Override
    public BaseResponse<InspeccionView> findInspeccion(Integer id) {
        return new BaseResponse<>(inspeccionRepository.findBy(id.longValue()));
    }

    @Override
    public void updateEstado(Integer id, String estado) {
        InspeccionModel iModel = inspRepository.findBy(id.longValue());
        iModel.setEstado(estado);
        inspRepository.save(iModel);
    }

    @Override
    public void reprogramarInspeccion(ReprogramarInspeccionRequest request) throws IOException, ExecutionException, InterruptedException {
        logger.info("[INSPECCION]: Reprogramando Inspeccion...");
        // Por cada reprogramacion damos de baja el actual informe y creamos uno nuevo;
        InformeModel inf = informeRepository.findBy(request.getIdInforme());
        inf.setIdCtMotivo(Constantes.INFORME_ANULADO_CORE_TABLE);
        inf.setUsuarioModificacion(request.getUsuarioCreacion());
        inf.setDetalleInforme(Constantes.INFORME_ANULADO);
        // La inspeccion reprogramada tiene que volver a habilitarse por el inspector web.
        InspeccionModel iModel = inspRepository.findBy(inf.getIdInspeccion().longValue());
        iModel.setEstado(Constantes.ESTADO_EN_ESPERA);
        inspRepository.save(iModel);
        InformeModel informeAnulado = informeRepository.save(inf);
        if (Util.isNotNull(informeAnulado)) {
            //Creamos un nuevo informe si éste se reprogramo.
            InformeModel informe = new InformeModel();
            informe.setIdInspeccion(informeAnulado.getIdInspeccion());
            informe.setIdEmpleado(informeAnulado.getIdEmpleado());
            informe.setIdDistritoAtencion(informeAnulado.getIdDistritoAtencion());
            informe.setDireccionAtencion(informeAnulado.getDireccionAtencion());
            informe.setIdCtMotivo(Constantes.INFORME_PROGRAMADO_CORE_TABLE);
            informe.setFechaProgramada(request.getFechaProgramada());
            informe.setFechaCreacion(LocalDateTime.now());
            informe.setUsuarioCreacion(request.getUsuarioCreacion());
            logger.info("[INSPECCION]: Sincronizando reprogramacion en POSTGRESQL...");
            informeRepository.save(informe);
            //Sincronizamos la informacion hacia firestore
            Map<String, Object> map = new HashMap<>();
            List<Map<String, Object>> lista = new ArrayList<>();
            Map<String, Object> schedule = new HashMap<>();
            schedule.put("datetime", request.getFechaProgramada().toString());
            schedule.put("type", Constantes.REPROGRAMADO);
            lista.add(0, schedule);
            map.put("schedule", lista);
            map.put("informe_id", informe.getIdInforme().intValue());
            sincronizarFirebase(request.getIdInforme().intValue(), map);
        }
    }

    @Override
    public void finalizarInspeccion(FinalizarInspeccionRequest request) {
        if (Util.isNotNull(request.getEstado())) {
            InspeccionModel iModel = inspRepository.findBy(request.getIdInspeccion().longValue());
            iModel.setEstado(request.getEstado());
            iModel.setLatitude(request.getLatitude());
            iModel.setLongitude(request.getLongitude());
            iModel.setFechaModificacion(LocalDateTime.now());
            iModel.setUsuarioModificacion(request.getUsuarioModificacion());
            inspRepository.save(iModel);
        }
        if (Util.isNotNull(request.getIdInspeccion()) && Util.isNotNull(request.getDireccion())) {
            InspeccionModel iModel = inspRepository.findBy(request.getIdInspeccion().longValue());
            iModel.setDireccionInspeccion(request.getDireccion());
            iModel.setFechaModificacion(LocalDateTime.now());
            iModel.setUsuarioModificacion(request.getUsuarioModificacion());
            inspRepository.save(iModel);
        }
        if (Util.isNotNull(request.getIdVehiculo()) && Util.isNotNull(request.getIdModelo())) {
            VehiculoModel vModel = vehiculoRepository.findBy(request.getIdVehiculo().longValue());
            vModel.setIdModelo(request.getIdModelo());
            vModel.setFechaModificacion(LocalDateTime.now());
            vModel.setUsuarioModificacion(request.getUsuarioModificacion());
            vehiculoRepository.save(vModel);
        }
        if (Util.isNotNull(request.getIdAsegurado()) && Util.isNotNull(request.getCorreo())) {
            AseguradoModel aModel = aseguradoRepository.findBy(request.getIdAsegurado().longValue());
            aModel.setCorreo(request.getCorreo());
            aModel.setFechaModificacion(LocalDateTime.now());
            aModel.setUsuarioModificacion(request.getUsuarioModificacion());
            aseguradoRepository.save(aModel);
        }

    }

    @Override
    public CollectionResponse<InspeccionView> findByAsegurado(String insured) {
        Pageable pageable = new PageRequest(Constantes.PAGINA_DEFAULT, Constantes.ELEMENTOS_DEFAULT, Sort.Direction.DESC, "id");
        Page<InspeccionView> page = inspeccionRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.and(cb.equal(root.get("idCtMotivo"), "TMII005")));
            if (Util.isNotNull(insured)) {
                String param = insured.replaceAll("\\_", " ");
                predicates.add(cb.or(cb.equal(root.get("nombreApellido"), param),
                        cb.equal(root.get("contratanteRazonSocial"), param),
                        cb.equal(root.get("contratanteNombre"), param)
                ));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
        return new CollectionResponse<>(page.getContent(), page.getTotalElements(),
                page.getNumber() + 1, page.getTotalPages());
    }

    private void sincronizarFirebase(Integer idInformeActual, Map<String, Object> map) throws ExecutionException, InterruptedException, IOException {
        logger.info("[INSPECCION]: Sincronizando reprogramacion en FIREBASE...");
        FirebaseConfig firebaseConfig = new FirebaseConfig();
        ApiFuture<QuerySnapshot> query = firebaseConfig.getFirestoreConnection().collection(Constantes.COLECCION_INSPECCION).get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        documents.stream().filter(x -> Integer.parseInt(Objects.requireNonNull(x.get("informe_id")).toString()) == idInformeActual).
                collect(Collectors.toList()).forEach(data -> {
            firebaseConfig.getFirestoreConnection().collection("inspections")
                    .document(data.getId()).update(map);
            logger.info("[FIREBASE]: User {}", data.getId());
            logger.info("[FIREBASE]: brand_name {}", data.get("brand_name"));
            logger.info("[FIREBASE]: informe {}", data.get("informe_id"));
        });
        firebaseConfig.closeConection();
    }
}
