package com.interrapidisimo.sistema_logistico.dto;

import com.interrapidisimo.sistema_logistico.model.Conductor;

public class ConductorDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String licencia;
    private String telefono;
    private String contraseña;

    public ConductorDTO(){}

    public ConductorDTO(Long id,String nombre, String apellido, String licencia, String telefono, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.licencia = licencia;
        this.telefono = telefono;
        this.contraseña = contraseña;
    }

    public static ConductorDTO fromEntity(Conductor conductor){
        return new ConductorDTO(
                conductor.getId(),
                conductor.getNombre(),
                conductor.getApellido(),
                conductor.getLicencia(),
                conductor.getTelefono(),
                conductor.getContraseña()
        );
    }

    public Conductor toEntity(){
        return new Conductor(this.nombre,this.apellido,this.licencia,this.telefono,this.contraseña);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
