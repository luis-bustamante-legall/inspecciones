package com.farmaciasperuanas.inspector.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalizarInspeccionRequest {

    private Integer idInspeccion;
    private String direccion;

    private Integer idVehiculo;
    private Integer idModelo;

    private Integer idAsegurado;
    private String correo;

    private String usuarioModificacion;
    private String estado;

    private Double latitude;
    private Double longitude;
}
