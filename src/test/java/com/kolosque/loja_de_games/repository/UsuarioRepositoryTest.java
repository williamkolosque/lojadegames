package com.kolosque.loja_de_games.repository;

import com.kolosque.loja_de_games.model.Usuarios;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start(){
        usuarioRepository.deleteAll();

        usuarioRepository.save(new Usuarios(0l,"João da silva","joao@email.com.br","12345678","https://i.imgur.com/FETvs20.jpg"));

        usuarioRepository.save(new Usuarios(0l,"Manuela da Silva","manuela@email.com.br","12345678","https://i.imgur.com/FETvs20.jpg"));

        usuarioRepository.save(new Usuarios(0l,"adriana melo rego silva","adriana@email.com.br","12345678","https://i.imgur.com/FETvs20.jpg"));

        usuarioRepository.save(new Usuarios(0l,"Paula tejano","paula@email.com.br","12345678","https://i.imgur.com/FETvs20.jpg"));

    }

    @Test
    @DisplayName("Retorna 1 usuario")
    public void deveRetornaUmUsuario(){
        Optional<Usuarios> usuario = usuarioRepository.findByUsuarios("joao@email.com.br");
        assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
    }


    @Test
    @DisplayName("Retorna 3 usuarios")
    public void dveRetornarTresUsuarios(){
        List<Usuarios> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
        assertEquals(3, listaDeUsuarios.size());
        assertTrue(listaDeUsuarios.get(0).getNome().equals("João da silva"));
        assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
        assertTrue(listaDeUsuarios.get(2).getNome().equals("adriana melo rego silva"));
    }

    @AfterAll
    public void end(){
        usuarioRepository.deleteAll();
    }

}
