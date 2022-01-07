package com.farmaciasperuanas.inspector.app.service;

import com.farmaciasperuanas.inspector.app.model.InspeccionView;
import com.farmaciasperuanas.inspector.dto.request.FinalizarInspeccionRequest;
import com.farmaciasperuanas.inspector.dto.request.InspeccionFiltroRequest;
import com.farmaciasperuanas.inspector.dto.request.ReprogramarInspeccionRequest;
import com.farmaciasperuanas.inspector.dto.response.BaseResponse;
import com.farmaciasperuanas.inspector.dto.response.CollectionResponse;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface InspeccionService {

	CollectionResponse<InspeccionView> findAllInspeccion(InspeccionFiltroRequest request, Integer pagina, Integer size);
	BaseResponse<InspeccionView> findInspeccion(Integer id);
	void updateEstado(Integer id, String estado);
	void reprogramarInspeccion(ReprogramarInspeccionRequest request) throws IOException, ExecutionException, InterruptedException;
	void finalizarInspeccion(FinalizarInspeccionRequest request);
	CollectionResponse<InspeccionView> findByAsegurado(String insured);
}
