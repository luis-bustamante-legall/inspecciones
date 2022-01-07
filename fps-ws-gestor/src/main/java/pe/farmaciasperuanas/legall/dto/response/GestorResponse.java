package pe.farmaciasperuanas.legall.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GestorResponse {

	private String usuario;
	private String password;
	private String nro_tramite_compania_seguro;
	private String codigo_tramite_legall;
	private Long fecha_asignacion_compania_seguro;
	private String observacion_programacion;
}
