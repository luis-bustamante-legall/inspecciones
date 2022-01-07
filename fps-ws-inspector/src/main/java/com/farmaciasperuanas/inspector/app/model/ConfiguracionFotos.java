package com.farmaciasperuanas.inspector.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "configuracion_fotos")
public class ConfiguracionFotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "es_requerido")
    private Boolean requerido;

    @Column(name = "detalle")
    private String detalle;

}
