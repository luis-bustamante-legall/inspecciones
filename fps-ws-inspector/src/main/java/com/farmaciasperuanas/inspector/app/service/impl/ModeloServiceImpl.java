package com.farmaciasperuanas.inspector.app.service.impl;

import com.farmaciasperuanas.inspector.app.model.Modelo;
import com.farmaciasperuanas.inspector.app.repository.ModeloRepository;
import com.farmaciasperuanas.inspector.app.service.ModeloService;
import com.farmaciasperuanas.inspector.dto.response.BaseResponse;
import com.farmaciasperuanas.inspector.dto.response.CollectionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class ModeloServiceImpl implements ModeloService {
	
	private static final Logger logger = LoggerFactory.getLogger(ModeloServiceImpl.class);

	@Inject
	private ModeloRepository modeloRepository;
	
	@Override
	public CollectionResponse<Modelo> find() {
		logger.info("[MODELO]: Iniciando lista...");
		return new CollectionResponse<>(modeloRepository.listar());
	}

	@Override
	public BaseResponse<Modelo> findById(Long id) {
		logger.info("[MODELO]: Iniciando busqueda por id...");
		return new BaseResponse<>(modeloRepository.findBy(id));
	}

	@Override
	public List<Modelo> buscarModelo(Integer idMarca) {
		return modeloRepository.findByIdMarca(idMarca);
	}
}