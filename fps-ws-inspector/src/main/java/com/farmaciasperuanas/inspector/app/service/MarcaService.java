package com.farmaciasperuanas.inspector.app.service;

import com.farmaciasperuanas.inspector.app.model.Marca;
import com.farmaciasperuanas.inspector.dto.response.BaseResponse;
import com.farmaciasperuanas.inspector.dto.response.CollectionResponse;

public interface MarcaService {
    CollectionResponse<Marca> find();
    BaseResponse<Marca> findById(Long id);
}
