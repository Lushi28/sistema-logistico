package com.interrapidisimo.sistema_logistico.dto;

import com.interrapidisimo.sistema_logistico.model.AsignacionEnvio;
import com.interrapidisimo.sistema_logistico.model.Conductor;
import com.interrapidisimo.sistema_logistico.model.Envio;

import java.time.LocalDate;

public class AsignacionEnvioDTO {
    private Long id;
    private Long envioId;
    private Long conductorId;
    private LocalDate fechaAsignacion;
    private String responsable;
    private String observaciones;

    public AsignacionEnvioDTO(){}

    public AsignacionEnvioDTO(Long id, Long envioId, Long conductorId, LocalDate fechaAsignacion, String responsable, String observaciones) {
        this.id = id;
        this.envioId = envioId;
        this.conductorId = conductorId;
        this.fechaAsignacion = fechaAsignacion;
        this.responsable = responsable;
        this.observaciones = observaciones;
    }

    public static AsignacionEnvioDTO fromEntity(AsignacionEnvio asignacionEnvio ){
        return new AsignacionEnvioDTO(
                asignacionEnvio.getId(),
                asignacionEnvio.getEnvio().getId(),
                asignacionEnvio.getConductor().getId(),
                asignacionEnvio.getFechaAsignacion(),
                asignacionEnvio.getResponsable(),
                asignacionEnvio.getObservaciones()
                );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnvioId() {
        return envioId;
    }

    public void setEnvioId(Long envioId) {
        this.envioId = envioId;
    }

    public Long getConductorId() {
        return conductorId;
    }

    public void setConductorId(Long conductorId) {
        this.conductorId = conductorId;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
