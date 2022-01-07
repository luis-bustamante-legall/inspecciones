package com.farmaciasperuanas.inspector.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inspeccion", schema = "inspeccion")
public class InspeccionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inspeccion")
    private Long idInspeccion;

    @Column(name = "id_vehiculo_asegurado")
    private Integer idVehiculoAsegurado;

    @Column(name = "codigo_inspeccion")
    private String codigoInspeccion;

    @Column(name = "codigo_inspeccion_legall")
    private String codigoInspeccionLegall;

    @Column(name = "id_ct_estado_inspeccion")
    private String estado;

    @Column(name = "id_tramite")
    private Integer idTramite;

    @Column(name = "id_distrito")
    private String idDistrito;

    @Column(name = "id_empleado_inspector")
    private Integer idEmpleadoInspector;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

	@Column(name = "fecha_termino")
	private LocalDateTime fechaTermino;

    @Column(name = "direccion_inspeccion")
    private String direccionInspeccion;

    @Column(name = "usuario_creacion", updatable = false)
    private String usuarioCreacion;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
}
