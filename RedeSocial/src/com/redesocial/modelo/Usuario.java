package com.redesocial.modelo;

import com.redesocial.exception.UsuarioException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe que representa um usuário na rede social.
 * Armazena informações como nome, username, email, senha, data de cadastro,
 * lista de amigos e lista de posts.
 */
public class Usuario {
    private Integer id;
    private String nome;
    private String username;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;
    private List<Usuario> amigos;
    private List<Post> posts;

    /**
     * Construtor da classe.
     * Inicializa os atributos obrigatórios e valida suas condições.
     *
     * @param nome          Nome do usuário.
     * @param username      Nome de usuário único.
     * @param email         Email do usuário.
     * @param senha         Senha do usuário.
     * @param dataCadastro  Data e hora de cadastro.
     * @throws UsuarioException Se qualquer atributo obrigatório for nulo ou inválido.
     */
    public Usuario(String nome, String username, String email, String senha, LocalDateTime dataCadastro) {
        if (nome == null || nome.isEmpty()) {
            throw new UsuarioException("O nome não pode ser nulo ou vazio.");
        }
        if (username == null || username.isEmpty()) {
            throw new UsuarioException("O username não pode ser nulo ou vazio.");
        }
        if (email == null || email.isEmpty()) {
            throw new UsuarioException("O email não pode ser nulo ou vazio.");
        }
        if (senha == null || senha.isEmpty()) {
            throw new UsuarioException("A senha não pode ser nula ou vazia.");
        }
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
        this.amigos = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    /**
     * Adiciona um amigo à lista de amigos do usuário.
     * A adição é recíproca (o outro usuário também adiciona este como amigo).
     *
     * @param amigo Usuário a ser adicionado como amigo.
     */
    public void adicionarAmigo(Usuario amigo) {
        if (amigo != null && !this.equals(amigo) && !amigos.contains(amigo)) {
            amigos.add(amigo);
            if (!amigo.getAmigos().contains(this)) {
                amigo.adicionarAmigo(this); // Adiciona reciprocamente
            }
        }
    }

    /**
     * Remove um amigo da lista de amigos do usuário.
     * A remoção é recíproca (o outro usuário também remove este como amigo).
     *
     * @param amigo Usuário a ser removido da lista de amigos.
     */
    public void removerAmigo(Usuario amigo) {
        if (amigo != null && amigos.contains(amigo)) {
            amigos.remove(amigo);
            if (amigo.getAmigos().contains(this)) {
                amigo.removerAmigo(this); // Remove reciprocamente
            }
        }
    }

    /**
     * Adiciona um post à lista de posts do usuário.
     * Verifica se o post já não está na lista antes de adicioná-lo.
     *
     * @param post Post a ser adicionado.
     */
    public void adicionarPost(Post post) {
        if (post != null && !posts.contains(post)) {
            posts.add(post);
        }
    }

    // Métodos getter e setter para manipulação dos atributos

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

    /**
     * Retorna uma representação em texto do objeto.
     * Inclui informações básicas e o número de amigos e posts.
     *
     * @return String representando o objeto.
     */
    @Override
    public String toString() {
        String nomesAmigos = amigos.stream()
                .map(Usuario::getNome)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Nenhum amigo");
        return String.format(
                "ID: %d | Nome: %s | Username: %s | Email: %s | Data de Cadastro: %s | Amigos: [%s] | Posts: %d",
                id, nome, username, email,
                dataCadastro != null ? dataCadastro.toString() : "N/A",
                nomesAmigos,
                posts != null ? posts.size() : 0
        );
    }

    /**
     * Compara dois objetos `Usuario` baseando-se no ID.
     *
     * @param o Objeto a ser comparado.
     * @return `true` se os IDs forem iguais, caso contrário `false`.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id.equals(usuario.id);
    }

    /**
     * Retorna o hash code do objeto, baseado no ID.
     *
     * @return Valor do hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}