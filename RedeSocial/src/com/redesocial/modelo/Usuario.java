package com.redesocial.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private Integer id;
    private String nome;
    private String username;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;
    private List<Usuario> amigos;
    private List<Post> posts;

    public Usuario(String nome, String username, String email, String senha, LocalDateTime dataCadastro, List<Usuario> amigos, List<Post> posts) {
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
        this.amigos = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public void adicionarAmigo(Usuario amigo) {
        if(amigos != null && !amigos.contains(amigo)) {
            amigos.add(amigo);
            amigo.adicionarAmigo(this); // Adiciona reciprocamente
        }
    }
    public void removerAmigo(Usuario amigo) {
        if(amigos.contains(amigo)) {
            amigos.remove(amigo);
            amigo.removerAmigo(this); // Remove reciprocamente
        }
    }
    public void adicionarPost(Post post) {
        if(post != null && !posts.contains(post)) {
            posts.add(post);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<Usuario> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Usuario> amigos) {
        this.amigos = amigos;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Nome: %s | Username: %s | Email: %s | Data de Cadastro: %s | Amigos: %d | Posts: %d",
                id, nome, username, email,
                dataCadastro != null ? dataCadastro.toString() : "N/A",
                amigos != null ? amigos.size() : 0,
                posts != null ? posts.size() : 0
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
