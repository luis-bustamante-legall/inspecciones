package pe.farmaciasperuanas.legall.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "modelo", schema = "core")
public class ModeloModel {

    @Id
    @Column(name = "id_modelo")
    private Long idModelo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "id_marca")
    private Long idMarca;

}
