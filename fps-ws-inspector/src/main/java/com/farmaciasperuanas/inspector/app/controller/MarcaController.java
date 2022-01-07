package com.farmaciasperuanas.inspector.app.controller;

import com.farmaciasperuanas.inspector.app.model.Marca;
import com.farmaciasperuanas.inspector.app.service.MarcaService;
import com.farmaciasperuanas.inspector.dto.response.BaseResponse;
import com.farmaciasperuanas.inspector.dto.response.CollectionResponse;
import com.farmaciasperuanas.inspector.util.Constantes;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@RequestScoped
@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaController {

	@Inject
	MarcaService marcaService;
	
	@GET
	@RolesAllowed({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
	public CollectionResponse<Marca> find(){
		return marcaService.find();
	}
	
	@GET
	@Path("/{id}")
	@RolesAllowed({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
	public BaseResponse<Marca> findById(@PathParam("id") Long id){
		return marcaService.findById(id);
	}
	
}
