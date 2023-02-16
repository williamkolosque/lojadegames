package com.kolosque.loja_de_games.repository;

import com.kolosque.loja_de_games.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

   List<Produtos> findAllByAnoContainingIgnoreCase(@Param("ano") String ano);
}
