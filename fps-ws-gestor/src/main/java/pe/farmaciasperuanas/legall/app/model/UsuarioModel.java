package pe.farmaciasperuanas.legall.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "usuario", schema = "public")
public class UsuarioModel {

	@Id
	@Column(name = "username")
	private String username ;

	@Column(name = "password")
	private String password;

	@Column(name = "activo")
	private String activo;

	@Column(name = "id_ct_rol")
	private String idCtRol;

	@Column(name = "password_encrypt")
	private String passwordEncrypt;
}
