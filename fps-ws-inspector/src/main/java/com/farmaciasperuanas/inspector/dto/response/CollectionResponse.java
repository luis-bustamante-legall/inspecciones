package com.farmaciasperuanas.inspector.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonbPropertyOrder(value = {"list","total","pagina_actual","total_paginas"})
public class CollectionResponse<T> {

	private List<T> list;
	private Long total;
	private Integer pagina_actual;
	private Integer total_paginas;


	public CollectionResponse(List<T> list) {
		super();
		this.list = list;
	}

}
