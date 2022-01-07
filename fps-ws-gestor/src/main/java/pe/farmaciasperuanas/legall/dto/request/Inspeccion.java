package pe.farmaciasperuanas.legall.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Inspeccion implements Serializable {

	private static final long serialVersionUID = 1L;

	public String codigo_inspeccion;
//	public String codigo_inspeccion_legall;
	public String direccion_inspeccion;
	public String id_distrito;
	public VehiculoAsegurado vehiculoAsegurado;

}
