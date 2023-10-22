package com.uva.users.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "bodega")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Bodega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "CIF")
    private String cif;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @OneToMany(mappedBy = "bodegaId", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<VinoConRelacion> vinoCollection;

    public Bodega(){ }

    public Bodega (String nombre, String cif, String direccion, List<VinoConRelacion> vinos){
        this.nombre=nombre;
        this.cif=cif;
        this.direccion=direccion;
        this.vinoCollection=vinos;

    }

    public void setId(Integer id) {this.id = id;}
    public Integer getId() {return id;}
    public List<VinoConRelacion> getVinoCollection() {return vinoCollection;}
    public String getCif() {return cif;}
    public String getDireccion() {return direccion;}
    public String getNombre() {return nombre;}
    public void setCif(String cif) {this.cif = cif;}
    public void setDireccion(String direccion) {this.direccion = direccion;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setVinoCollection(List<VinoConRelacion> vinoCollection) {this.vinoCollection = vinoCollection;}
}