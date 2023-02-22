package com.kolosque.loja_de_games.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity
@Table(name = ("tb_usuarios"))
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=3)
    private String nome;

    @NotNull
    private String email;

    @NotNull
    @Size(min = 8, message = "A senha deve conter no minimo 8 caracteres")
    private String senha;

    @Size(max = 5000, message = "o link da foto deve conter no maximo 5000 caracteres")
    private String foto;

    @OneToMany(mappedBy = "usuarios", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("usuarios")
    private List<Produtos> produtos;

    public Usuarios(Long id, String nome, String email, String senha, String foto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.foto = foto;

    }

    public Usuarios() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Produtos> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produtos> produtos) {
        this.produtos = produtos;
    }
}
