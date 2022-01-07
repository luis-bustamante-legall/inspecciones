package com.farmaciasperuanas.inspector.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReprogramarInspeccionRequest {

	private Long idInforme;
//	private Integer idInspeccion;
//	private Integer idEmpleado;
//	private String idDistritoAtencion;
//	private String direccionAtencion;
	private LocalDateTime fechaProgramada;
	private String usuarioCreacion;
}
