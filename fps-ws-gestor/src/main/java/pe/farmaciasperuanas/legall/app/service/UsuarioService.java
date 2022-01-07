package pe.farmaciasperuanas.legall.app.service;

import java.util.List;

import pe.farmaciasperuanas.legall.app.model.UsuarioModel;
import pe.farmaciasperuanas.legall.dto.request.ValidacionRequest;

public interface UsuarioService {

	List<UsuarioModel> listarUsuario();

	UsuarioModel validarUsuario(String usuarip, String password);
}
