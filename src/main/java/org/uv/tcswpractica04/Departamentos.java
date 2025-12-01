/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.tcswpractica04;

import java.util.List;
import javax.persistence.*;

/**
 *
 * @author christopher
 */
@Entity
@Table (name = "departamentos")
public class Departamentos {
    
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "dep_clave_seq")
    @SequenceGenerator (name = "dep_clave_seq",
            sequenceName = "dep_clave_seq",
            initialValue = 1,
            allocationSize = 1)
    
    @Column
    private Long clave_depto;
    
    @Column
    private String nombre;
    
    @Transient  // No se persiste en la base de datos, se calcula dinámicamente
    private List<String> nombresEmpleados;

    public Long getClave_depto() {
        return clave_depto;
    }

    public void setClave_depto(Long clave_depto) {
        this.clave_depto = clave_depto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getNombresEmpleados() {
        return nombresEmpleados;
    }

    public void setNombresEmpleados(List<String> nombresEmpleados) {
        this.nombresEmpleados = nombresEmpleados;
    } 
    
}
