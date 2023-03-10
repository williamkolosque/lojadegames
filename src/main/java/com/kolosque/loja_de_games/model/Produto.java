package com.kolosque.loja_de_games.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = ("tb_produto"))
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2)
    private String nome;

    @NotNull
    @Size(min=2,max = 4)
    private String ano;

    @NotNull
    private Double preco;

    @NotNull
    @Size(min=1)
    private Integer jogadores;

    @NotNull
    @Size(min=1, max = 3)
    private String online;

    @NotNull
    private String idioma;

    @ManyToOne
    @JsonIgnoreProperties("produtos")
    private Categoria categoria;

    @ManyToOne
    @JsonIgnoreProperties("produtos")
    private Usuario usuario;

    public Usuario getUsuarios() {
        return usuario;
    }

    public void setUsuarios(Usuario usuarios) {
        this.usuario = usuarios;
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

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getJogadores() {
        return jogadores;
    }

    public void setJogadores(Integer jogadores) {
        this.jogadores = jogadores;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Categoria getCategorias() {
        return categoria;
    }

    public void setCategorias(Categoria categoria) {
        this.categoria = categoria;
    }
}
