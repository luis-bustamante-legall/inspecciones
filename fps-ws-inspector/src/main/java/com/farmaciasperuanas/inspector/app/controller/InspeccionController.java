package com.farmaciasperuanas.inspector.app.controller;

import com.farmaciasperuanas.inspector.app.model.InspeccionView;
import com.farmaciasperuanas.inspector.app.service.InspeccionService;
import com.farmaciasperuanas.inspector.dto.request.FinalizarInspeccionRequest;
import com.farmaciasperuanas.inspector.dto.request.InspeccionFiltroRequest;
import com.farmaciasperuanas.inspector.dto.request.ReprogramarInspeccionRequest;
import com.farmaciasperuanas.inspector.dto.response.BaseResponse;
import com.farmaciasperuanas.inspector.dto.response.CollectionResponse;
import com.farmaciasperuanas.inspector.util.Constantes;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RequestScoped
@Path("/inspecciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InspeccionController {

    @Inject
    InspeccionService inspeccionService;

    @POST
    @Path("/pagina/{page}/size/{size}")
    @RolesAllowed({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
    public CollectionResponse<InspeccionView> findAll(InspeccionFiltroRequest request,
                                                      @PathParam("page") Integer page,
                                                      @PathParam("size") Integer size){
        return inspeccionService.findAllInspeccion(request, page, size);
    }

    @PUT
    @Path("{id}/status/{estado}")
    @RolesAllowed({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
    public void updateEstado(@PathParam("id") Integer id, @PathParam("estado") String estado){
        inspeccionService.updateEstado(id, estado);
    }

    @PUT
    @Path("/reprogramar")
    @RolesAllowed({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
    public void reprogramarInspeccion(ReprogramarInspeccionRequest request) throws InterruptedException, ExecutionException, IOException {
        inspeccionService.reprogramarInspeccion(request);
    }

    @GET
    @Path("/id/{id}")
    @RolesAllowed({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
    public BaseResponse<InspeccionView> findInspeccion(@PathParam("id") Integer id) {
        return inspeccionService.findInspeccion(id);
    }

    @PUT
    @Path("/finalizar-inspeccion")
    @RolesAllowed({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
    public void finalizarInspeccion(FinalizarInspeccionRequest request) {
        inspeccionService.finalizarInspeccion(request);
    }

    @GET
    @Path("/find")
    @RolesAllowed({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
    public CollectionResponse<InspeccionView> findInspeccion(@QueryParam("insured") String insured) {
        return inspeccionService.findByAsegurado(insured);
    }

}
