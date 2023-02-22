package com.kolosque.loja_de_games.repository;

import com.kolosque.loja_de_games.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    public Optional<Usuarios> findByEmail(String email);

    public List<Usuarios> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

}
