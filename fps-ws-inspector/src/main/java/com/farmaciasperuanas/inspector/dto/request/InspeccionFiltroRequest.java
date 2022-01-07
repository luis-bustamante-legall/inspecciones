package com.farmaciasperuanas.inspector.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class InspeccionFiltroRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String placa;
	private String codigoInspeccionLegall;
	private LocalDateTime fechaDesde;
	private LocalDateTime fechaHasta;
	private String estado;
}
