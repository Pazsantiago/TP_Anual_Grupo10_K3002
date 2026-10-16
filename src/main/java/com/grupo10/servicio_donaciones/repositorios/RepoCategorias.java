package com.grupo10.servicio_donaciones.repositorios;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoCategorias extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
}
