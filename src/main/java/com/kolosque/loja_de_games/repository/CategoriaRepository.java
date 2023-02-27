package com.kolosque.loja_de_games.repository;

import com.kolosque.loja_de_games.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    public List<Categoria>findAllByMarcaContainingIgnoreCase(@Param("marca") String marca);
}
