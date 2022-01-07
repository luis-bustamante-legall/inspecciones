package com.farmaciasperuanas.inspector.app.model;

import com.farmaciasperuanas.inspector.util.Constantes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vw_inspecciones", schema = "public")
public class InspeccionView {

    @Id
    @Column(name = "id_informe")
    private Long id;

    @Column(name = "id_inspeccion")
    private Long idInspeccion;

    @Column(name = "codigo_inspeccion")
    private String codigoInspeccion;

    @Column(name = "codigo_inspeccion_legall")
    private String codigoInspeccionLegall;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "informe_fecha_programada")
    private LocalDateTime fechaProgramada;

    @Column(name = "id_distrito")
    private String idDistrito;

    @Column(name = "id_empleado_inspector")
    private String idEmpleadoInspector;

    @Column(name = "usuario_creacion")
    private String usuarioCreacion;

    @Column(name = "id_tramite")
    private Integer idTramite;

    @Column(name = "nro_tramite_compania_seguro")
    private String numeroTramite;

    @Column(name = "contacto")
    private String contacto;

    @Column(name = "placa")
    private String placa;

    @Column(name = "id_ct_motivo")
    private String idCtMotivo;

    @Column(name = "id_estado")
    private String idEstado;

    @Column(name = "estado")
    private String estado;

    @Column(name = "contratante_nombre")
    private String contratanteNombre;

    @Column(name = "contratante_razon_social")
    private String contratanteRazonSocial;

    @Column(name = "asegurado_nombre")
    private String nombreApellido;

    @Column(name = "telefono_fijo")
    private String telefonoFijo;

    @Column(name = "telefono_movil")
    private String telefono;

    @Column(name = "correo")
    private String correo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "id_marca")
    private String idMarca;

    @Column(name = "marca")
    private String marca;

    @Column(name = "id_modelo")
    private String idModelo;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "id_vehiculo_asegurado")
    private String idVehiculoAsegurado;

    @Column(name = "id_asegurado")
    private String idAsegurado;

    public String getEstado() {
        switch (estado) {
            case "Pendiente":
                return Constantes.ON_HOLD;
            case "Digitado":
                return Constantes.AVAILABLE;
            case "Terminado":
                return Constantes.COMPLETE;
            default:
                return estado;
        }
    }

    public String getCorreo() {
        return (correo != null) ? correo : "";
    }

    public String getContratanteNombre() {
        return (contratanteNombre.trim().equals("")) ? getNombreApellido() : contratanteNombre;
    }

    public String getNombreApellido() {
        return (nombreApellido.trim().equals("")) ? contratanteRazonSocial : nombreApellido;
    }

    public String getTelefono() {
        return (telefono != null) ? getTelefono() : getTelefonoFijo();
    }

    public String getDireccion() {
        return (direccion != null) ? direccion : "";
    }
}
