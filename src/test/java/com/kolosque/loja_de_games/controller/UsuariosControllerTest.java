package com.kolosque.loja_de_games.controller;

import com.kolosque.loja_de_games.Service.UsuarioService;
import com.kolosque.loja_de_games.model.Usuario;
import com.kolosque.loja_de_games.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuariosControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuariosRepository;

    @BeforeAll
    void start(){
        usuariosRepository.deleteAll();

        usuarioService.cadastrarUsuario(new Usuario(0L,"Root","root@root.com","rootroot"," "));

    }
    //cadastrar um usuario
    @Test
    @DisplayName("Cadastrar um usuário")
    public void deveCriarUmUsuario(){

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
                "Paulo antunes", "paulo_antunes@email.com.br", "12345678", "https://i.imgur.com/JR7kufu.jpg"));
        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(),corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getEmail(),corpoResposta.getBody().getEmail());

    }

    //não deve cadastrar duplicado
    @Test
    @DisplayName("Não deve permitir duplicação de usuário")
    public void naoDeveDuplicarUsuario(){

        usuarioService.cadastrarUsuario(new Usuario(0L,"Maria silva", "maria_silva@email.com.br","12345678","http://i.imgur.com/yDRVeK7.jpg"));
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,"Maria silva", "maria_silva@email.com.br","12345678","http://i.imgur.com/yDRVeK7.jpg"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    //deve atualizar um usuario
    @Test
    @DisplayName("Atualizar um Usuário")
    public void deveAtualizarUmUsuario(){
        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,
                "Juliana Andrews", "juliana_ramos@email.com.br", "juliana1234","http://i.imgur.com/yDRVeK7.jpg"));

        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId()
                ,"Juliana Andrews Ramos", "juliana_ramos@email.com.br", "juliana1234","http://i.imgur.com/yDRVeK7.jpg");

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(),corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getEmail(), corpoResposta.getBody().getEmail());

    }

    // listar todos os usuários
    @Test
    @DisplayName("Listar todos os Usuários")
    public void deveMostarTodosUsuarios(){
        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Sabrina sanches", "sabrina_sanches@email.com.br", "sabrina123","http://i.imgur.com/yDRVeK7.jpg"));
        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Ricardo Marques", "ricardo_marques@email.com", "ricardo123","http://i.imgur.com/yDRVeK7.jpg"));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());

    }



}
