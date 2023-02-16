package com.kolosque.loja_de_games.repository;

import com.kolosque.loja_de_games.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriasRepository extends JpaRepository<Categorias, Long> {

    public List<Categorias>findAllByMarcaContainingIgnoreCase(@Param("marca") String marca);
}
