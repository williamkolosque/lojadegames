package com.kolosque.loja_de_games.repository;

import com.kolosque.loja_de_games.model.Usuario;
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
    private UsuarioRepository usuariosRepository;

    @BeforeAll
    void start(){
        usuariosRepository.deleteAll();

        usuariosRepository.save(new Usuario(0l,"João da silva","joao@email.com.br","12345678","https://i.imgur.com/FETvs20.jpg"));

        usuariosRepository.save(new Usuario(0l,"Manuela da Silva","manuela@email.com.br","12345678","https://i.imgur.com/FETvs20.jpg"));

        usuariosRepository.save(new Usuario(0l,"adriana melo rego silva","adriana@email.com.br","12345678","https://i.imgur.com/FETvs20.jpg"));

        usuariosRepository.save(new Usuario(0l,"Paula tejano","paula@email.com.br","12345678","https://i.imgur.com/FETvs20.jpg"));

    }

    @Test
    @DisplayName("Retorna 1 usuario")
    public void deveRetornaUmUsuario(){
        Optional<Usuario> usuario = usuariosRepository.findByEmail("joao@email.com.br");
        assertTrue(usuario.get().getEmail().equals("joao@email.com.br"));
    }


    @Test
    @DisplayName("Retorna 3 usuarios")
    public void dveRetornarTresUsuarios(){
        List<Usuario> listaDeUsuarios = usuariosRepository.findAllByNomeContainingIgnoreCase("Silva");
        assertEquals(3, listaDeUsuarios.size());
        assertTrue(listaDeUsuarios.get(0).getNome().equals("João da silva"));
        assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
        assertTrue(listaDeUsuarios.get(2).getNome().equals("adriana melo rego silva"));
    }

    @AfterAll
    public void end(){
        usuariosRepository.deleteAll();
    }

}
