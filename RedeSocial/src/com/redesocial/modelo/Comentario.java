package com.redesocial.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa um comentário em um post da rede social.
 * Contém informações sobre o autor, conteúdo, data do comentário e o post relacionado.
 */
public class Comentario {

    private Integer id; // Identificador único do comentário
    private Usuario autor; // Autor do comentário
    private String conteudo; // Conteúdo textual do comentário
    private LocalDateTime dataComentario; // Data e hora do comentário
    private Post post; // Post ao qual o comentário está associado

    /**
     * Construtor da classe Comentario.
     * Inicializa os atributos obrigatórios e define a data do comentário como o momento atual.
     *
     * @param autor    Usuário autor do comentário.
     * @param conteudo Conteúdo textual do comentário.
     * @param post     Post associado ao comentário.
     */
    public Comentario(Usuario autor, String conteudo, Post post) {
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataComentario = LocalDateTime.now();
        this.post = post;
    }

    // Métodos getter e setter para manipulação dos atributos

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(LocalDateTime dataComentario) {
        this.dataComentario = dataComentario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Retorna uma representação textual do objeto `Comentario`.
     * Inclui informações sobre o autor, conteúdo, data e hora do comentário.
     *
     * @return String representando o comentário.
     */
    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Autor: " + autor.getNome() + " (" + autor.getUsername() + ")\n" +
                "Conteúdo: " + conteudo + "\n" +
                "Data do Comentário: " + dataComentario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n";
    }
}