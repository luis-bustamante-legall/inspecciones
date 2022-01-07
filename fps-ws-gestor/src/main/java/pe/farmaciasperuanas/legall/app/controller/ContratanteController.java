package pe.farmaciasperuanas.legall.app.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pe.farmaciasperuanas.legall.app.model.ContratanteModel;
import pe.farmaciasperuanas.legall.app.repository.ContratanteRepository;

@RequestScoped
@Path("/contratantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContratanteController {

	@Inject
	ContratanteRepository contratanteRepository;
	
	@GET
	public List<ContratanteModel> lista(){
		return contratanteRepository.findAll();
	}
}
