package com.interrapidisimo.sistema_logistico.service;

import com.interrapidisimo.sistema_logistico.model.Conductor;
import com.interrapidisimo.sistema_logistico.repository.AsignacionEnvioRepository;
import com.interrapidisimo.sistema_logistico.repository.ConductorRepository;
import com.interrapidisimo.sistema_logistico.repository.EnvioRepository;
import jakarta.persistence.criteria.From;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ConductorServiceJPA implements ConductorService{

    private final EnvioRepository envioRepository;
    private final ConductorRepository conductorRepository;
    private final AsignacionEnvioRepository asignacionEnvioRepository;

    @Autowired
    public ConductorServiceJPA(EnvioRepository envioRepository, ConductorRepository conductorRepository, AsignacionEnvioRepository asignacionEnvioRepository) {
        this.envioRepository = envioRepository;
        this.conductorRepository = conductorRepository;
        this.asignacionEnvioRepository = asignacionEnvioRepository;
    }

    @Override
    public Page<Conductor> findAll(Pageable pageable) {return conductorRepository.findAll(pageable);}

    @Override
    public Conductor findById(Long id) {
        return conductorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID:" + id));
    }

    @Override
    @Transactional
    public Conductor save(Conductor conductor) {
        // verificar si hay alguna logica de mas para agregar un conductor
        return conductorRepository.save(conductor);
    }

    @Override
    @Transactional
    public Conductor update(Long id, Conductor conductorActualizado) {
        Conductor existente = conductorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));

        // Validar transición SOLO si el estado cambió
        // == (me va a dar la direccion en memorio - equals si compara lo que hay en la lista
        // listaAsignaciones(bogota) nuevaLista(bogota) equals
        if (existente.getAsignaciones().equals(conductorActualizado.getAsignaciones())){
            new RuntimeException("La lista de envios es igual");
        }
        existente.setAsignaciones(conductorActualizado.getAsignaciones());
        return conductorRepository.save(conductorActualizado);
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public Page<Conductor> findByEstado(String estado, Pageable pageable) {
        return null;
    }
}
