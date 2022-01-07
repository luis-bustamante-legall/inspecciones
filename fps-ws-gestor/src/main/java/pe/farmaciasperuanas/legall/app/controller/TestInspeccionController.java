package pe.farmaciasperuanas.legall.app.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pe.farmaciasperuanas.legall.app.service.InspeccionService;
import pe.farmaciasperuanas.legall.dto.request.VehiculoAsegurado;

@RequestScoped
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestInspeccionController {

	@Inject
	InspeccionService inspeccionService;
	
	@POST
	public Integer saveUpdateVehiculo(VehiculoAsegurado request) {
		return inspeccionService.saveUpdateVehiculo(request);
	}
}
