package pe.farmaciasperuanas.legall.app.service;

import java.util.List;

import pe.farmaciasperuanas.legall.dto.request.GestorRequest;
import pe.farmaciasperuanas.legall.dto.request.Inspeccion;
import pe.farmaciasperuanas.legall.dto.request.VehiculoAsegurado;

public interface InspeccionService {

    Boolean saveInspecction(List<Inspeccion> insp, Integer idTramite, String usuario);
    Integer saveUpdateVehiculo(VehiculoAsegurado vehiculo);

}
