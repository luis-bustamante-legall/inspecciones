package pe.farmaciasperuanas.legall.app.service;

import pe.farmaciasperuanas.legall.dto.request.GestorRequest;
import pe.farmaciasperuanas.legall.dto.response.BaseResponse;

public interface GestorService {
    
    BaseResponse<String> save(GestorRequest request);

}
