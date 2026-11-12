package com.interrapidisimo.sistema_logistico.service;

import com.interrapidisimo.sistema_logistico.dto.AsignacionEnvioDTO;
import com.interrapidisimo.sistema_logistico.model.AsignacionEnvio;
import com.interrapidisimo.sistema_logistico.model.Conductor;
import com.interrapidisimo.sistema_logistico.model.Envio;
import com.interrapidisimo.sistema_logistico.repository.AsignacionEnvioRepository;
import com.interrapidisimo.sistema_logistico.repository.ConductorRepository;
import com.interrapidisimo.sistema_logistico.repository.EnvioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <- Esta es la importacion correcta, no la de jakarta


import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class EnvioServiceJPA implements EnvioService{


    private final EnvioRepository envioRepository;
    private final ConductorRepository conductorRepository;
    private final AsignacionEnvioRepository asignacionEnvioRepository;

    @Autowired
    public EnvioServiceJPA(EnvioRepository envioRepository, ConductorRepository conductorRepository, AsignacionEnvioRepository asignacionEnvioRepository) {
        this.envioRepository = envioRepository;
        this.conductorRepository = conductorRepository;
        this.asignacionEnvioRepository = asignacionEnvioRepository;
    }


    @Override
    public Page<Envio> findAll(Pageable pageable) {
        return envioRepository.findAll(pageable);
    }

    @Override
    public Envio findById(Long id) {
        return envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Envio save(Envio envio) {
        double costo = calcularCostoEnvio(envio.getPesoKg(), envio.getDestino());
        envio.setCosto(costo);
        if (envio.getEstado() == null) {
            envio.setEstado("PENDIENTE");
        }
        if (envio.getPesoKg() > 500) {
            throw new RuntimeException("El envío no puede pesar más de 500 kg");
        }
        return envioRepository.save(envio);
    }

    @Override
    @Transactional
    public Envio update(Long id, Envio envioActualizado) {
        Envio existente = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado con ID: " + id));

        if ("ENTREGADO".equals(existente.getEstado())) {
            throw new RuntimeException("No se puede actualizar un envío ya entregado");
        }

        // Validar transición SOLO si el estado cambió
        if (envioActualizado.getEstado() != null
                && !envioActualizado.getEstado().equals(existente.getEstado())) {
            validarTransicionEstado(existente.getEstado(), envioActualizado.getEstado());
        }

        if (cambiaronPesoODestino(existente, envioActualizado)) {
            double nuevoCosto = calcularCostoEnvio(envioActualizado.getPesoKg(), envioActualizado.getDestino());
            envioActualizado.setCosto(nuevoCosto);
        }

        envioActualizado.setId(id);
        return envioRepository.save(envioActualizado);
    }

    @Override
    public void deleteById(Long id) {
        if (!envioRepository.existsById(id)) {
            throw new RuntimeException("Envío no encontrado con ID: " + id);
        }
        envioRepository.deleteById(id);
    }

    @Override
    public Page<Envio> findByEstado(String estado,Pageable pageable) {
        return envioRepository.findByEstado(estado, pageable);
    }

    @Override
    @Transactional
    public AsignacionEnvio asignarConductorAEnvio(Long envioId,
                                                  Long conductorId,
                                                  String responsable, String observaciones) {

            Envio envio = envioRepository.findById(envioId)
                    .orElseThrow(() -> new RuntimeException("Envío no encontrado: " + envioId));

            Conductor conductor = conductorRepository.findById(conductorId)
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado: " + conductorId));


            //Aca me encargo de generar la fecha actual para el registro y evito errores
            AsignacionEnvio asignacion = new AsignacionEnvio(envio, conductor, LocalDate.now(), responsable, observaciones);

            return asignacionEnvioRepository.save(asignacion);
    }


    @Transactional(readOnly = true)
    public List<AsignacionEnvioDTO> obtenerAsignacionesDeEnvio(String envioId) {
        return asignacionEnvioRepository.findByEnvio_Id(envioId)
                .stream()
                .map(AsignacionEnvioDTO::fromEntity)
                .collect(Collectors.toList());
    }


    private double calcularCostoEnvio(double pesoKg, String destino) {
        double costoBase = 10000;
        if (pesoKg > 50) costoBase *= 1.2;
        if ("Internacional".equalsIgnoreCase(destino)) costoBase *= 1.5;
        return costoBase;
    }

    private void validarTransicionEstado(String actual, String nuevo) {
        boolean permitida = switch (actual) {
            case "PENDIENTE" -> nuevo.equals("EN_RUTA") || nuevo.equals("CANCELADO");
            case "EN_RUTA" -> nuevo.equals("ENTREGADO");
            default -> false;
        };
        if (!permitida) {
            throw new RuntimeException("No se puede cambiar de '" + actual + "' a '" + nuevo + "'");
        }
    }

    private boolean cambiaronPesoODestino(Envio existente, Envio actualizado) {
        return existente.getPesoKg() != actualizado.getPesoKg()
                || !existente.getDestino().equals(actualizado.getDestino());
    }
}
