package pe.farmaciasperuanas.legall.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "marca", schema = "core")
public class MarcaModel {

	@Id
	@Column(name = "id_marca")
	private Long idMarca;

	@Column(name = "nombre")
	private String nombre;
}
