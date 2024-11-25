package com.redesocial.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um post na rede social.
 * Armazena informações como autor, conteúdo, data de publicação, curtidas e comentários.
 */
public class Post {
    private Integer id;
    private Usuario autor;
    private String conteudo;
    private LocalDateTime dataPublicacao;
    private List<Usuario> curtidas;
    private List<Comentario> comentarios;

    /**
     * Construtor da classe.
     * Inicializa os atributos obrigatórios e as listas de curtidas e comentários.
     * @param id Identificador único do post.
     * @param autor Usuário autor do post.
     * @param conteudo Conteúdo textual do post.
     * @param dataPublicacao Data e hora da publicação.
     * @param curtidas Lista de usuários que curtiram o post (inicialmente vazia).
     * @param comentarios Lista de comentários no post (inicialmente vazia).
     */
    public Post(Integer id, Usuario autor, String conteudo, LocalDateTime dataPublicacao, List<Usuario> curtidas, List<Comentario> comentarios) {
        this.id = id;
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
        this.curtidas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    /**
     * Adiciona uma curtida ao post.
     * Verifica se o usuário ainda não curtiu antes de adicionar.
     * @param usuario Usuário que curtiu o post.
     */
    public void adicionarCurtida(Usuario usuario) {
        if (curtidas != null && !curtidas.contains(usuario)) {
            curtidas.add(usuario);
        }
    }

    /**
     * Remove uma curtida do post.
     * Verifica se o usuário já curtiu antes de remover.
     * @param usuario Usuário que deseja remover a curtida.
     */
    public void removerCurtida(Usuario usuario) {
        if (curtidas.contains(usuario)) {
            curtidas.remove(usuario);
        }
    }

    /**
     * Adiciona um comentário ao post.
     * Verifica se o comentário ainda não está presente antes de adicioná-lo.
     * @param comentario Comentário a ser adicionado.
     */
    public void adicionarComentario(Comentario comentario) {
        if (comentarios != null && !comentarios.contains(comentario)) {
            comentarios.add(comentario);
        }
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

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public List<Usuario> getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(List<Usuario> curtidas) {
        this.curtidas = curtidas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Retorna uma representação textual do objeto `Post`.
     * Inclui informações sobre o autor, conteúdo, data de publicação, número de curtidas e comentários.
     * @return String representando o post.
     */
    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Autor: " + autor.getNome() + " (" + autor.getUsername() + ")\n" +
                "Conteúdo: " + conteudo + "\n" +
                "Data de Publicação: " + dataPublicacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n" +
                "Curtidas: " + (curtidas != null ? curtidas.size() : 0) + "\n" +
                "Comentários: " + (comentarios != null ? comentarios.size() : 0) + "\n";
    }
}