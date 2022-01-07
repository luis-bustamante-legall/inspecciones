package pe.farmaciasperuanas.legall.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Asegurado implements Serializable {

	private static final long serialVersionUID = 1L;

	public String direccion;
	public String nombre;
	public String apellido_paterno;
	public String apellido_materno;
	public String numero_documento;
	public String id_distrito;
}