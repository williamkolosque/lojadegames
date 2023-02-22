package com.kolosque.loja_de_games.service;

import com.kolosque.loja_de_games.model.UsuarioLogin;
import com.kolosque.loja_de_games.model.Usuarios;
import com.kolosque.loja_de_games.repository.UsuariosRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    public Optional<Usuarios> cadastrarUsuario(Usuarios usuario) {

        if (usuariosRepository.findByEmail(usuario.getEmail()).isPresent())
            return Optional.empty();

        usuario.setSenha(criptografarSenha(usuario.getSenha()));

        return Optional.of(usuariosRepository.save(usuario));

    }

    public Optional<Usuarios> atualizarUsuario(Usuarios usuario) {

        if(usuariosRepository.findById(usuario.getId()).isPresent()) {

            Optional<Usuarios> buscaUsuario = usuariosRepository.findByEmail(usuario.getEmail());

            if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != usuario.getId()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            usuario.setSenha(criptografarSenha(usuario.getSenha()));

            return Optional.ofNullable(usuariosRepository.save(usuario));

        }

        return Optional.empty();

    }

    public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

        Optional<Usuarios> usuario = usuariosRepository.findByEmail(usuarioLogin.get().getEmail());

        if (usuario.isPresent()) {

            if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getEmail(),        usuarioLogin.get().getSenha()));
                usuarioLogin.get().setSenha(usuario.get().getSenha());

                return usuarioLogin;

            }
        }

        return Optional.empty();

    }

    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(senha);

    }

    private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(senhaDigitada, senhaBanco);

    }

    private String gerarBasicToken(String usuario, String senha) {

        String token = usuario + ":" + senha;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(tokenBase64);

    }


}