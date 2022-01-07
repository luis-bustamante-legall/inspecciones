package pe.farmaciasperuanas.legall.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contratante implements Serializable {

	private static final long serialVersionUID = 1L;

	public String id_ct_tipo_persona;
	public String direccion;
	public String nombre;
	public String apellido_paterno;
	public String razon_social;
	public String numero_ruc;
	public String numero_documento;
	public String id_distrito;

}