package com.kolosque.loja_de_games.controller;

import com.kolosque.loja_de_games.model.Usuarios;
import com.kolosque.loja_de_games.repository.UsuariosRepository;
import com.kolosque.loja_de_games.service.UsuariosService;
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
    private UsuariosService usuariosService;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @BeforeAll
    void start(){
        usuariosRepository.deleteAll();

        usuariosService.cadastrarUsuario(new Usuarios(0L,"Root","root@root.com","rootroot"," "));

    }
    //cadastrar um usuario
    @Test
    @DisplayName("Cadastrar um usuário")
    public void deveCriarUmUsuario(){

        HttpEntity<Usuarios> corpoRequisicao = new HttpEntity<Usuarios>(new Usuarios(0L,
                "Paulo antunes", "paulo_antunes@email.com.br", "12345678", "https://i.imgur.com/JR7kufu.jpg"));
        ResponseEntity<Usuarios> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuarios.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(),corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getEmail(),corpoResposta.getBody().getEmail());

    }

    //não deve cadastrar duplicado
    @Test
    @DisplayName("Não deve permitir duplicação de usuário")
    public void naoDeveDuplicarUsuario(){

        usuariosService.cadastrarUsuario(new Usuarios(0L,"Maria silva", "maria_silva@email.com.br","12345678","http://i.imgur.com/yDRVeK7.jpg"));
        HttpEntity<Usuarios> corpoRequisicao = new HttpEntity<Usuarios>(new Usuarios(0L,"Maria silva", "maria_silva@email.com.br","12345678","http://i.imgur.com/yDRVeK7.jpg"));

        ResponseEntity<Usuarios> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuarios.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    //deve atualizar um usuario
    @Test
    @DisplayName("Atualizar um Usuário")
    public void deveAtualizarUmUsuario(){
        Optional<Usuarios> usuarioCadastrado = usuariosService.cadastrarUsuario(new Usuarios(0L,
                "Juliana Andrews", "juliana_ramos@email.com.br", "juliana1234","http://i.imgur.com/yDRVeK7.jpg"));

        Usuarios usuarioUpdate = new Usuarios(usuarioCadastrado.get().getId()
                ,"Juliana Andrews Ramos", "juliana_ramos@email.com.br", "juliana1234","http://i.imgur.com/yDRVeK7.jpg");

        HttpEntity<Usuarios> corpoRequisicao = new HttpEntity<Usuarios>(usuarioUpdate);

        ResponseEntity<Usuarios> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuarios.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(),corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getEmail(), corpoResposta.getBody().getEmail());

    }

    // listar todos os usuários
    @Test
    @DisplayName("Listar todos os Usuários")
    public void deveMostarTodosUsuarios(){
        usuariosService.cadastrarUsuario(new Usuarios(0L,
                "Sabrina sanches", "sabrina_sanches@email.com.br", "sabrina123","http://i.imgur.com/yDRVeK7.jpg"));
        usuariosService.cadastrarUsuario(new Usuarios(0L,
                "Ricardo Marques", "ricardo_marques@email.com", "ricardo123","http://i.imgur.com/yDRVeK7.jpg"));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());

    }



}
