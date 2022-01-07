package com.farmaciasperuanas.inspector.app.controller;

import com.farmaciasperuanas.inspector.app.model.Modelo;
import com.farmaciasperuanas.inspector.app.service.ModeloService;
import com.farmaciasperuanas.inspector.dto.response.BaseResponse;
import com.farmaciasperuanas.inspector.dto.response.CollectionResponse;
import com.farmaciasperuanas.inspector.util.Constantes;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@Path("/modelos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModeloController {

	@Inject ModeloService modeloService;
	
	@GET
	@RolesAllowed({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
	public CollectionResponse<Modelo> find(){
		return modeloService.find();
	}

	@GET
	@Path("/{id}")
	@RolesAllowed({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
	public BaseResponse<Modelo> findById(@PathParam("id") Long id){
		return modeloService.findById(id);
	}

	@GET
	@Path("/marca/{idMarca}")
	@RolesAllowed({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
	public List<Modelo> findModelo(@PathParam("idMarca") Integer idMarca){
		return modeloService.buscarModelo(idMarca);
	}
}