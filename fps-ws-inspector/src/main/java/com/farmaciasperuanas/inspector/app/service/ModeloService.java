package com.farmaciasperuanas.inspector.app.service;

import com.farmaciasperuanas.inspector.app.model.Modelo;
import com.farmaciasperuanas.inspector.dto.response.BaseResponse;
import com.farmaciasperuanas.inspector.dto.response.CollectionResponse;

import java.util.List;

public interface ModeloService {

	CollectionResponse<Modelo> find();
	BaseResponse<Modelo> findById(Long id);
	List<Modelo> buscarModelo(Integer idMarca);
}
