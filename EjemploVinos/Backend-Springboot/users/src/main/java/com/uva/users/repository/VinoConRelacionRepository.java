package com.uva.users.repository;
import com.uva.users.model.VinoConRelacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VinoConRelacionRepository extends JpaRepository<VinoConRelacion, Integer>{
    List<VinoConRelacion> findByDenominacionYBodega(String denominacion, String bodega);
}
