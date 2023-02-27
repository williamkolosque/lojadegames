package com.kolosque.loja_de_games.Service;

import com.kolosque.loja_de_games.model.Usuario;
import com.kolosque.loja_de_games.model.UsuarioLogin;
import com.kolosque.loja_de_games.repository.UsuarioRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;


import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> cadastrarUsuario(Usuario usuario){

        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
            return Optional.empty();

        usuario.setSenha(criptografarSenha(usuario.getSenha()));

        return Optional.of(usuarioRepository.save(usuario));

    }


    public Optional<Usuario> atualizarUsuario(Usuario usuario){
        if (usuarioRepository.findById(usuario.getId()).isPresent()){
            Optional<Usuario> buscarUsuario = usuarioRepository.findByEmail(usuario.getEmail());

            if((buscarUsuario.isPresent()) && (buscarUsuario.get().getId() != usuario.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário já existe", null);

            usuario.setSenha(criptografarSenha(usuario.getSenha()));

            return Optional.ofNullable(usuarioRepository.save(usuario));
        }

        return Optional.empty();
    }



    public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin){

        Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioLogin.get().getEmail());

        if(usuario.isPresent()){

            if(compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())){

                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getEmail(), usuarioLogin.get().getSenha()));
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
        return encoder.matches(senhaDigitada,senhaBanco);

    }


    private String gerarBasicToken(String email, String senha) {

        String token = email + ":" + senha;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        return "Basic "+ new String(tokenBase64);
    }

}