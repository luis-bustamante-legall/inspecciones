package pe.farmaciasperuanas.legall.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculoAsegurado implements Serializable {

	private static final long serialVersionUID = 1L;

	public String marca;
	public String modelo;
	public int anyo;
	public String color;
	public String placa;
	public String motor;

}
