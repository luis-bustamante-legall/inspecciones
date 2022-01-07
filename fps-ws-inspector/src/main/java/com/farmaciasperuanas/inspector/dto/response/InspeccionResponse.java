package com.farmaciasperuanas.inspector.dto.response;

public class InspeccionResponse {

	private Long idAsegurado;
	private String numeroTramite;
	private String placa;
	private String contacto;
	private String nombreApellido;
	private String telefono;
	private String fecha;
	private String estado;
	
	public InspeccionResponse() {}

	public InspeccionResponse(Long idAsegurado, String numeroTramite, String placa, String contacto,
			String nombreApellido, String telefono, String fecha, String estado) {
		super();
		this.idAsegurado = idAsegurado;
		this.numeroTramite = numeroTramite;
		this.placa = placa;
		this.contacto = contacto;
		this.nombreApellido = nombreApellido;
		this.telefono = telefono;
		this.fecha = fecha;
		this.estado = estado;
	}

	public Long getIdAsegurado() {
		return idAsegurado;
	}

	public void setIdAsegurado(Long idAsegurado) {
		this.idAsegurado = idAsegurado;
	}

	public String getNumeroTramite() {
		return numeroTramite;
	}

	public void setNumeroTramite(String numeroTramite) {
		this.numeroTramite = numeroTramite;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getNombreApellido() {
		return nombreApellido;
	}

	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
