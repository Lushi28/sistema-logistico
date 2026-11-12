package com.interrapidisimo.sistema_logistico.service;

import com.interrapidisimo.sistema_logistico.model.Conductor;
import com.interrapidisimo.sistema_logistico.model.Envio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConductorService {
Page<Conductor> findAll(Pageable pageable);
Conductor findById(Long id);
Conductor save(Conductor Conductor);
Conductor update(Long id, Conductor Conductor);
void deleteById(Long id);
Page<Conductor> findByEstado(String estado, Pageable pageable);


}
