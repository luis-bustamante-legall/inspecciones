package pe.farmaciasperuanas.legall.app.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pe.farmaciasperuanas.legall.app.model.UsuarioModel;
import pe.farmaciasperuanas.legall.app.service.GestorService;
import pe.farmaciasperuanas.legall.app.service.UsuarioService;
import pe.farmaciasperuanas.legall.core.util.Constantes;
import pe.farmaciasperuanas.legall.core.util.Util;
import pe.farmaciasperuanas.legall.dto.request.GestorRequest;
import pe.farmaciasperuanas.legall.dto.response.BaseResponse;

@RequestScoped
@Path("/inspeccion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GestorController {

	@Inject
	GestorService inspeccionService;

	@Inject
	UsuarioService usuarioService;
	
	@POST
	public BaseResponse<String>save(GestorRequest request){
		if(Util.isNotNull(usuarioService.validarUsuario(request.getUsuario(), request.getPassword()))){
			return inspeccionService.save(request);
		}
		else{
			return new BaseResponse<>(Constantes.ESTADO_ERROR, "Usuario o clave incorrecta",
					"Por favor revise las credenciales en la base de datos");
		}
	}
}
