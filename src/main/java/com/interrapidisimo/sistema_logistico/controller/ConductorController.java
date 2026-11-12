package com.interrapidisimo.sistema_logistico.controller;

import com.interrapidisimo.sistema_logistico.service.EnvioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/envios")
public class ConductorController {
    private final ConductorService conductorService;
}
