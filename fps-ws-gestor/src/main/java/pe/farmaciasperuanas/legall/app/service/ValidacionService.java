package pe.farmaciasperuanas.legall.app.service;

import pe.farmaciasperuanas.legall.dto.request.Asegurado;
import pe.farmaciasperuanas.legall.dto.request.Broker;
import pe.farmaciasperuanas.legall.dto.request.Contratante;
import pe.farmaciasperuanas.legall.dto.request.GestorRequest;

public interface ValidacionService {
   
    Integer saveUpdateContratante(Contratante contratante, GestorRequest request);
    Integer saveUpdateBroker(Broker broker, GestorRequest request);
    Integer saveUpdateAsegurado(Asegurado asegurado, GestorRequest request);
}
