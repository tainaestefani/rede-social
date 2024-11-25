package com.redesocial.gerenciador;

import com.redesocial.exception.PostException;
import com.redesocial.exception.ValidacaoException;
import com.redesocial.modelo.Comentario;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorPosts {
    private List<Post> posts;
    private int proximoId;
    private int idComentario;
    private final GerenciadorUsuarios gerenciadorUsuarios;

    public GerenciadorPosts(GerenciadorUsuarios gerenciadorUsuarios){
        posts = new ArrayList<>();
        proximoId = 1;
        idComentario = 1;
        this.gerenciadorUsuarios = gerenciadorUsuarios;
    }

    public void criar(Post post) {
        if (post == null) {
            throw new ValidacaoException("Post não pode ser nulo.");
        }
        post.setId(proximoId++);
        posts.add(post);
    }

    public List<Post> listarPosts(){
        return posts;
    }

    public Post buscarPorId(int id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst()
                .orElseThrow(() -> new PostException("Post com ID " + id + " não encontrado."));
    }

    public List<Post> listarPorUsuario(int idUsuario) {
        List<Post> postsDoUsuario = new ArrayList<>();

        for (Post post : posts) {
            if (post.getAutor().getId() == idUsuario) {
                postsDoUsuario.add(post);
            }
        }

        if (postsDoUsuario.isEmpty()) {
            throw new PostException("Nenhum post encontrado para o usuário com ID " + idUsuario);
        }

        return postsDoUsuario;
    }

    public void curtir(int idPost, int idUsuario) {
        Post post = buscarPorId(idPost);
        if (post == null) {
            throw new PostException("Erro ao curtir post: post inválido.");
        }

        Usuario usuario = gerenciadorUsuarios.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new PostException("Erro ao curtir post: usuário inválido.");
        }

        if (post.getCurtidas().contains(usuario)) {
            throw new PostException("Usuário já curtiu este post.");
        }

        post.adicionarCurtida(usuario);
    }

    public void descurtir(int idPost, int idUsuario) {
        Post post = buscarPorId(idPost);
        if (post == null) {
            throw new PostException("Erro ao descurtir post: post inválido.");
        }

        Usuario usuario = gerenciadorUsuarios.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new PostException("Erro ao descurtir post: usuário inválido.");
        }

        if (!post.getCurtidas().contains(usuario)) {
            throw new PostException("Erro ao descurtir post: o usuário não curtiu este post.");
        }

        post.removerCurtida(usuario);
    }

    public void comentar(Comentario comentario) {
        if (comentario == null) {
            throw new ValidacaoException("Comentário não pode ser nulo.");
        }

        Post post = comentario.getPost();
        if (post == null) {
            throw new PostException("Erro ao comentar: post inválido.");
        }

        Post postExistente = buscarPorId(post.getId());
        if (postExistente == null) {
            throw new PostException("Erro ao comentar: post não encontrado.");
        }

        postExistente.adicionarComentario(comentario);
    }

    public boolean deletar(int id) {
        Post post = buscarPorId(id);

        if (post != null) {
            posts.remove(post);
            return true;
        }

        throw new PostException("Post com ID " + id + " não encontrado para exclusão.");
    }

    private void validarPost(Post post) {
        if (post.getConteudo().trim().isEmpty()) {
            throw new ValidacaoException("Conteúdo do post não pode ser vazio");
        }

        if (post.getAutor() == null || !gerenciadorUsuarios.listarUsuarios().contains(post.getAutor())) {
            throw new ValidacaoException("Autor inválido");
        }

        if (post.getConteudo().length() > 280) {
            throw new ValidacaoException("Limite de caracteres atingido");
        }

        if (post.getDataPublicacao() == null) {
            throw new ValidacaoException("Data de publicação não pode ser nula");
        }

        if (post.getAutor().getId() == null || post.getAutor().getId() <= 0) {
            throw new ValidacaoException("ID do autor inválido");
        }
    }

}