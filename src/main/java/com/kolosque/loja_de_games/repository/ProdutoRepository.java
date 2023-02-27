package com.kolosque.loja_de_games.repository;

import com.kolosque.loja_de_games.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

   List<Produto> findAllByAnoContainingIgnoreCase(@Param("ano") String ano);
}
