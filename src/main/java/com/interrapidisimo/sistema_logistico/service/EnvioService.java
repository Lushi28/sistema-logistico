package com.interrapidisimo.sistema_logistico.service;

import com.interrapidisimo.sistema_logistico.model.AsignacionEnvio;
import com.interrapidisimo.sistema_logistico.model.Envio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface EnvioService {

    Page<Envio> findAll(Pageable pageable);
    Envio findById(Long id);
    Envio save(Envio envio);
    Envio update(Long id, Envio envio);
    void deleteById(Long id);
    Page<Envio> findByEstado(String estado, Pageable pageable);

    AsignacionEnvio asignarConductorAEnvio(
            Long envioId,
            Long conductorId,
            //LocalDate fechaAsignacion, No se debe pedir porque el service la genera
            String responsable,
            String observaciones);
}
