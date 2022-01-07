package com.farmaciasperuanas.inspector.dto.response;

public class BaseResponse<T> {

	private String apiMensaje;
	private T response;

	public BaseResponse(String apiMensaje, T response) {
		super();
		this.apiMensaje = apiMensaje;
		this.response = response;
	}
	
	public BaseResponse(T response) {
		this.response = response;
	}
	
	public BaseResponse(String apiMensaje) {
		super();
		this.apiMensaje = apiMensaje;
	}

	public String getApiMensaje() {
		return apiMensaje;
	}

	public void setApiMensaje(String apiMensaje) {
		this.apiMensaje = apiMensaje;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

}
