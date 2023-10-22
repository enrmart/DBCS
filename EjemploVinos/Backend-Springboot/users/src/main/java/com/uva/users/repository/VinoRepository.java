package com.uva.users.repository;

import com.uva.users.model.Vino;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VinoRepository extends JpaRepository<Vino, Integer> {
    Optional<Vino> findByNombreComercial(String nombre);
    List<Vino> findByPrecioBetween(Float precio1, Float precio2);
    boolean existsVinoByDenominacionAndCategoria(String denomiacion,String categoria);
    @Transactional
    //Long deleteByDenominacionAndCategoria(String denominacion,String categoria);
    List<Vino> deleteByDenominacionAndCategoria(String denominacion,String categoria);
    Long countByDenominacion(String denominacion);
    Long countByDenominacionNot(String denominacion);
    //List<Vino> findByDenomincacionOrdenadoNombreDesc(String denominacion);
    //List<Vino> findByDenominacionYCategoria(String denominacion, String categoria);
    /* Otra Opcion de persistencia
    @Query("SELECT v FROM Vino v WHERE v.denominacion = ?1 ORDER BY v.nombre_comercial DESC")
    List<Vino> findByDenomincacionOrdenadoNombreDesc(String denominacion);
    @Query("SELECT * FROM Vino WHERE categoria = ?1 AND denominacion = ?2", nativeQuery=true)
    List<Vino> findByDenominacionYCategoria(String denominacion, String categoria);
    */


}
