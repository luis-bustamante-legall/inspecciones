package com.farmaciasperuanas.inspector.app.service.impl;

import com.farmaciasperuanas.inspector.app.model.Marca;
import com.farmaciasperuanas.inspector.app.repository.MarcaRepository;
import com.farmaciasperuanas.inspector.app.service.MarcaService;
import com.farmaciasperuanas.inspector.dto.response.BaseResponse;
import com.farmaciasperuanas.inspector.dto.response.CollectionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;


public class MarcaServiceImpl implements MarcaService {

	private static final Logger logger = LoggerFactory.getLogger(MarcaServiceImpl.class);

	@Inject
	private MarcaRepository marcaRepository;

	@Override
	public CollectionResponse<Marca> find() {
		logger.info("[MARCA]: Iniciando lista...");
		return new CollectionResponse<>(marcaRepository.listar());
	}

	@Override
	public BaseResponse<Marca> findById(Long id) {
		logger.info("[MARCA]: Iniciando busqueda por id...");
		return new BaseResponse<>(marcaRepository.findBy(id));
	}
}
