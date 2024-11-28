package com.redesocial.gerenciador;

import com.redesocial.exception.PostException;
import com.redesocial.exception.ValidacaoException;
import com.redesocial.modelo.Comentario;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pela gestão de posts dentro da rede social.
 * Ela permite a criação, busca, listagem, curtidas, descurtidas, comentários e exclusão de posts.
 */
public class GerenciadorPosts {
    private List<Post> posts;
    private int proximoId;
    private int idComentario;
    private final GerenciadorUsuarios gerenciadorUsuarios;

    /**
     * Construtor da classe, que inicializa a lista de posts e configura o ID inicial.
     * @param gerenciadorUsuarios Instância do gerenciador de usuários, necessário para validar usuários nas interações.
     */
    public GerenciadorPosts(GerenciadorUsuarios gerenciadorUsuarios){
        posts = new ArrayList<>();
        proximoId = 1;
        idComentario = 1;
        this.gerenciadorUsuarios = gerenciadorUsuarios;
    }

    /**
     * Cria um novo post e o adiciona à lista de posts.
     * @param post O post a ser criado.
     * @throws ValidacaoException Se o post for nulo.
     */
    public void criar(Post post) {
        if (post == null) {
            throw new ValidacaoException("Post não pode ser nulo.");
        }
        post.setId(proximoId++);
        posts.add(post);
    }

    /**
     * Lista todos os posts registrados.
     * @return Uma lista de todos os posts.
     */
    public List<Post> listarPosts(){
        return posts;
    }

    /**
     * Busca um post pelo seu ID.
     * @param id O ID do post a ser buscado.
     * @return O post correspondente ao ID.
     * @throws PostException Se o post não for encontrado.
     */
    public Post buscarPorId(int id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst()
                .orElseThrow(() -> new PostException("Post com ID " + id + " não encontrado."));
    }

    /**
     * Lista os posts de um usuário específico, identificando pelo ID do usuário.
     * @param idUsuario O ID do usuário cujos posts serão listados.
     * @return A lista de posts do usuário.
     * @throws PostException Se o usuário não tiver posts ou o ID for inválido.
     */
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

    /**
     * Permite que um usuário curta um post, validando a ação.
     * @param idPost O ID do post a ser curtido.
     * @param idUsuario O ID do usuário que está curtindo o post.
     * @throws PostException Se o post ou o usuário forem inválidos, ou se o usuário já tiver curtido o post.
     */
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

    /**
     * Permite que um usuário descurta um post.
     * @param idPost O ID do post a ser descurtido.
     * @param idUsuario O ID do usuário que está descurtindo o post.
     * @throws PostException Se o post ou o usuário forem inválidos, ou se o usuário não tiver curtido o post.
     */
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

    /**
     * Permite adicionar um comentário a um post.
     * @param comentario O comentário a ser adicionado.
     * @throws ValidacaoException Se o comentário for nulo.
     * @throws PostException Se o post relacionado ao comentário for inválido.
     */
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

    /**
     * Exclui um post pelo seu ID.
     * @param id O ID do post a ser excluído.
     * @return True se o post foi excluído com sucesso, ou False caso contrário.
     * @throws PostException Se o post não for encontrado para exclusão.
     */
    public boolean deletar(int id) {
        Post post = buscarPorId(id);

        if (post != null) {
            posts.remove(post);
            return true;
        }

        throw new PostException("Post com ID " + id + " não encontrado para exclusão.");
    }

    /**
     * Método utilizado para validar um post antes de ser criado.
     * @param post O post a ser validado.
     * @throws ValidacaoException Se o post não passar nas validações.
     */
    public void validarPost(Post post) {
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