package com.redesocial.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private Integer id;
    private Usuario autor;
    private String conteudo;
    private LocalDateTime dataPublicacao;
    private List<Usuario> curtidas;
    private List<Comentario> comentarios;

    public Post(Integer id, Usuario autor, String conteudo, LocalDateTime dataPublicacao, List<Usuario> curtidas, List<Comentario> comentarios) {
        this.id = id;
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
        this.curtidas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    public void adicionarCurtida(Usuario usuario) {
        if(curtidas != null && !curtidas.contains(usuario)) {
            curtidas.add(usuario);
        }
    }
    public void removerCurtida(Usuario usuario) {
        if(curtidas.contains(usuario)) {
            curtidas.remove(usuario);
        }
    }
    public void adicionarComentario(Comentario comentario) {
        if(comentarios != null && !comentarios.contains(comentario)) {
            comentarios.add(comentario);
        }
    }

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

    @Override
    public String toString() {
        return String.format(
                "Post { ID: %d | Autor: %s | Conteúdo: '%s' | Data de Publicação: %s | Curtidas: %d | Comentários: %d }",
                id,
                autor != null ? autor.getUsername() : "Autor desconhecido",
                conteudo != null ? conteudo : "Conteúdo vazio",
                dataPublicacao != null ? dataPublicacao.toString() : "N/A",
                curtidas != null ? curtidas.size() : 0,
                comentarios != null ? comentarios.size() : 0
        );
    }
}
