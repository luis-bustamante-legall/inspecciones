package com.farmaciasperuanas.inspector.app.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "informe", schema = "inspeccion")

public class InformeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informe")
    private Long idInforme;

    @Column(name = "id_inspeccion")
    private Integer idInspeccion;

    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "id_distrito_atencion")
    private String idDistritoAtencion;

    @Column(name = "direccion_atencion")
    private String direccionAtencion;

    @Column(name = "id_ct_motivo")
    private String idCtMotivo;

    @Column(name = "persona_contacto")
    private String personaContacto;

    @Column(name = "fecha_visita")
    private LocalDateTime fechaProgramada;

    @Column(name = "detalle_informe")
    private String detalleInforme   ;

    @Column(name = "usuario_creacion", updatable = false)
    private String usuarioCreacion;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;

    @Column(name = "fecha_modificacion", updatable = false)
    private LocalDateTime fechaModificacion;

}
