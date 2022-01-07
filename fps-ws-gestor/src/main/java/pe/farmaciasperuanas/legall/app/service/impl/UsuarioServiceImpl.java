package pe.farmaciasperuanas.legall.app.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.slf4j.Logger;

import pe.farmaciasperuanas.legall.app.model.UsuarioModel;
import pe.farmaciasperuanas.legall.app.repository.UsuarioRepository;
import pe.farmaciasperuanas.legall.app.service.UsuarioService;
import pe.farmaciasperuanas.legall.core.util.Constantes;
import pe.farmaciasperuanas.legall.dto.request.ValidacionRequest;

public class UsuarioServiceImpl implements UsuarioService {

	@Inject
	UsuarioRepository usuarioRepository;

	@Inject
	Logger logger;

	@Override
	public List<UsuarioModel> listarUsuario() {
		logger.info("[INFO]-[USUARIOS]: Iniciando lista de usuarios...");
		return usuarioRepository.findAll();
	}

	@Override
	public UsuarioModel validarUsuario(String usuario, String password) {
		logger.info("[INFO]-[USUARIOS]: Validando usuario...");
		Optional<UsuarioModel> x = usuarioRepository.findByUsernameAndPasswordEncrypt(usuario,
				password);
		return (x.isPresent()) ? x.get() : x.orElse(null);
	}

}
