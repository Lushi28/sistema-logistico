package com.interrapidisimo.sistema_logistico.controller;

import com.interrapidisimo.sistema_logistico.dto.EnvioDTO;

import com.interrapidisimo.sistema_logistico.model.Envio;
import com.interrapidisimo.sistema_logistico.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {


    private final EnvioService envioService;

    @Autowired
    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping
    public Page<EnvioDTO> getAllEnvios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection){

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Envio> pagina = envioService.findAll(pageable);
        return pagina.map(EnvioDTO::fromEntity);
    }

    @GetMapping("/estado/{estado}")
    public Page<EnvioDTO> getEnviosByEstado(
            @PathVariable String estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "estado") String sortBy,
            @RequestParam(defaultValue = "ASC")String sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageable = PageRequest.of(page, size,sort);
        Page<Envio> pagina = envioService.findByEstado(estado,pageable);
        return pagina.map(EnvioDTO::fromEntity);
    }


    @GetMapping("/{id}")
    public EnvioDTO getEnvioById(@PathVariable Long id){
        return EnvioDTO.fromEntity(envioService.findById(id));
    }



    @PostMapping
    public EnvioDTO createEnvio(@RequestBody EnvioDTO envioDTO){
        Envio envio = envioDTO.toEntity();
        return EnvioDTO.fromEntity(envioService.save(envio));
    }

    @PutMapping("/{id}")
    public EnvioDTO updateEnvio(@PathVariable Long id, @RequestBody EnvioDTO envioDTO){
        Envio envio = envioDTO.toEntity();
        return EnvioDTO.fromEntity(envioService.update(id,envio));
    }

    @DeleteMapping("/{id}")
    public void deleteEnvio(@PathVariable Long id){
        envioService.deleteById(id);
    }

    @PostMapping("/{envioId}/asignar")
    public void asignarConductor(
            @PathVariable Long envioId,
            @RequestParam Long conductorId,
            @RequestParam String responsable,
            @RequestParam(required = false) String observaciones) {

         envioService.asignarConductorAEnvio(
                envioId,
                conductorId,
                responsable,
                observaciones
        );
    }

}
