package pe.farmaciasperuanas.legall.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "broker", schema = "core")
public class BrokerModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_broker")
	private Long id_broker;

	@Column(name = "razon_social")
	private String razonSocial;

	@Column(name = "activo")
	private String activo;
}
