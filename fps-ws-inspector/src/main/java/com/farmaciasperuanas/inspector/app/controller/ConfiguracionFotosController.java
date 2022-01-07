package com.farmaciasperuanas.inspector.app.controller;

import com.farmaciasperuanas.inspector.app.model.ConfiguracionFotos;
import com.farmaciasperuanas.inspector.app.service.ConfiguracionFotosService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfiguracionFotosController {

    @Inject
    ConfiguracionFotosService documentoAdjuntoService;

    @GET
    @Path("/detail-upload")
    public List<ConfiguracionFotos> lista(){
        return documentoAdjuntoService.lista();
    }
}
