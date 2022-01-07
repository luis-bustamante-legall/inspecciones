package pe.farmaciasperuanas.legall.dto.request;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GestorRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String usuario;
	private String password;
	private String nro_tramite_compania_seguro;
	private String codigo_tramite_legall;
	private Integer id_empresa;
	private String fecha_asignacion_compania_seguro;
	private String observacion_programacion;
	public Contratante contratante;
	public Broker broker;
	public Asegurado asegurado;
	public List<Inspeccion> inspeccion;
}
