package com.uva.users.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uva.users.model.Bodega;

public interface BodegaRepository extends JpaRepository<Bodega, Integer>{
}