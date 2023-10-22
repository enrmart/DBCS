package com.uva.users.model;

import jakarta.persistence.*;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "Vino")
/*@NamedQueries({
        @NamedQuery(name = "Vino.findByDenominacionOrdenadoNombreDesc",
                query = "SELECT v FROM Vino v WHERE v.denominacion = ?1 ORDER BY v.nombre_comercial DESC"),
        @NamedQuery(name = "Vino.findByDenominacionYCategoria",
                query = "SELECT v FROM Vino v WHERE v.categoria = ?1 AND v.denominacion = ?2")})*/
public class Vino implements Serializable {

    @Id
    @GeneratedValue
    private Integer Id;
    @Size(max = 50) // si no se pone esta anotación lo creo por defecto con size=255
    @Column(name = "nombre_comercial")
    private String nombreComercial;
    private String denominacion;
    private String categoria;
    @Column(nullable = false)
    private Float precio;

    private Integer bodega_id;

    public Vino() { }

    public Vino(String nombreComercial, String denominacion, String categoria, Float precio, Integer bodega) {
        this.nombreComercial = nombreComercial;
        this.denominacion = denominacion;
        this.categoria = categoria;
        this.precio = precio;
        this.bodega_id = bodega;
    }

    public Integer getId() {
        return this.Id;
    }
    public void setId(Integer Id) {
        this.Id = Id;
    }
    public String getNombre_comercial() {return this.nombreComercial;}
    public void setNombre_comercial(String nombre_comercial) {this.nombreComercial = nombre_comercial;}
    public String getDenominacion() {return this.denominacion;}
    public void setDenominacion(String denominacion) {this.denominacion = denominacion;}
    public String getCategoria() {return this.categoria;}
    public void setCategoria(String categoria) {this.categoria = categoria;}
    public Float getPrecio() {return this.precio;}
    public void setPrecio(Float precio) {this.precio = precio;}
    public Integer getBodega_id() {return this.bodega_id;}
    public void setBodega_id(Integer bodega_id) {this.bodega_id = bodega_id;}
}