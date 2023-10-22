package com.uva.users.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import javax.validation.constraints.Size;

@Entity
@Table(name = "VinoConRelacion")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class,property = "id")
@NamedQuery(name = "VinoConRelacion.findByDenominacionYBodega", query = "SELECT v FROM VinoConRelacion v WHERE v.denominacion = ?1 AND v.bodegaId.nombre = ?2")
public class VinoConRelacion {
    @Id
    @GeneratedValue
    private Integer Id;
    @Size(max = 50)
    @Column(name = "nombre_comercial")
    private String nombreComercial;
    private String denominacion;
    private String categoria;
    @Column(nullable = false)
    private Float precio;
    @JoinColumn(name="Bodega_Id", referencedColumnName="ID")
    @ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
    private Bodega bodegaId;

    public VinoConRelacion(){ }

    public VinoConRelacion(String nombreComercial, String denominacion, String categoria, Float precio, Bodega bodega) {
        this.nombreComercial = nombreComercial;
        this.denominacion = denominacion;
        this.categoria = categoria;
        this.precio = precio;
        this.bodegaId = bodega;
    }

    public Integer getId() {return this.Id;}
    public void setId(Integer id) {this.Id = id;}
    public String getDenominacion() {return this.denominacion;}
    public String getNombreComercial() {return this.nombreComercial;}
    public void setDenominacion(String denominacion) {this.denominacion = denominacion;}
    public void setNombreComercial(String nombreComercial) {this.nombreComercial = nombreComercial;}
    public Bodega getBodegaId() {return bodegaId;}
    public Float getPrecio() {return precio;}
    public String getCategoria() {return categoria;}
    public void setBodegaId(Bodega bodegaId) {this.bodegaId = bodegaId;}
    public void setCategoria(String categoria) {this.categoria = categoria;}
    public void setPrecio(Float precio) {this.precio = precio;}
}
