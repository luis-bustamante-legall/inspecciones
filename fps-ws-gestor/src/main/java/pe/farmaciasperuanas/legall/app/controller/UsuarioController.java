package pe.farmaciasperuanas.legall.app.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pe.farmaciasperuanas.legall.app.model.UsuarioModel;
import pe.farmaciasperuanas.legall.app.service.UsuarioService;
import pe.farmaciasperuanas.legall.dto.request.ValidacionRequest;

@RequestScoped
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {

	@Inject
	UsuarioService usuarioService;
	
	@GET
	public List<UsuarioModel> listarUsuario(){
		return usuarioService.listarUsuario();
	}
	
}
