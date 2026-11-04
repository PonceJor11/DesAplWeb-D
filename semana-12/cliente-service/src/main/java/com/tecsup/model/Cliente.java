package com.tecsup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(nullable = false, length = 120)
    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    @Column(nullable = false, length = 10)
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO", message = "El estado debe ser ACTIVO o INACTIVO")
    private String estado;

    @JsonProperty("nro_dni")
    @Column(name = "nro_dni", nullable = false, unique = true, length = 8)
    @NotBlank(message = "nro_dni es obligatorio")
    @Pattern(regexp = "\\d{8}", message = "nro_dni debe contener exactamente 8 digitos")
    private String nroDni;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getNroDni() { return nroDni; }
    public void setNroDni(String nroDni) { this.nroDni = nroDni; }
}
