package com.redesocial.gerenciador;

import com.redesocial.exception.UsuarioException;
import com.redesocial.exception.ValidacaoException;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável pela gestão de usuários dentro da rede social.
 * Ela permite cadastrar, buscar, atualizar, excluir usuários, além de gerenciar amizades e posts.
 */
public class GerenciadorUsuarios {
    private List<Usuario> usuarios;
    private int proximoId;

    /**
     * Construtor da classe, que inicializa a lista de usuários e configura o ID inicial.
     */
    public GerenciadorUsuarios() {
        usuarios = new ArrayList<>();
        proximoId = 1;
    }

    /**
     * Cadastra um novo usuário, validando suas informações e verificando se o email e username são únicos.
     * @param usuario O usuário a ser cadastrado.
     * @throws ValidacaoException Se o email ou username já estiverem em uso, ou se os dados do usuário forem inválidos.
     */
    public void cadastrar(Usuario usuario) {
        validarUsuario(usuario);
        usuario.setId(proximoId++);

        // Verifica se já existe um usuário com o mesmo email
        if (usuarios.stream().anyMatch(u -> u.getEmail().equals(usuario.getEmail()))) {
            throw new UsuarioException("Já existe um usuário com este email.");
        }

        // Verifica se já existe um usuário com o mesmo username
        if (usuarios.stream().anyMatch(u -> u.getUsername().equals(usuario.getUsername()))) {
            throw new UsuarioException("Já existe um usuário com este username.");
        }

        usuarios.add(usuario);
    }

    /**
     * Busca um usuário pelo seu ID.
     * @param id O ID do usuário a ser buscado.
     * @return O usuário correspondente ao ID.
     * @throws UsuarioException Se o usuário não for encontrado.
     */
    public Usuario buscarPorId(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UsuarioException("Usuário com ID " + id + " não encontrado."));
    }

    /**
     * Busca um usuário pelo seu nome de usuário (username).
     * @param username O username do usuário a ser buscado.
     * @return O usuário correspondente ao username.
     * @throws UsuarioException Se o usuário não for encontrado.
     */
    public Usuario buscarPorUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidacaoException("Username não pode ser vazio.");
        }

        // Realiza a busca insensível a maiúsculas/minúsculas e retorna o único usuário que corresponde exatamente ao username
        return usuarios.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username)) // Comparação exata
                .findFirst() // Retorna o primeiro (único) encontrado
                .orElseThrow(() -> new UsuarioException("Usuário com username '" + username + "' não encontrado.")); // Lança exceção caso não encontre
    }

    /**
     * Busca usuários pelo nome, ignorando maiúsculas/minúsculas e permitindo pesquisa parcial.
     * @param nome O nome a ser pesquisado.
     * @return A lista de usuários cujo nome contenha o nome pesquisado.
     * @throws ValidacaoException Se o nome for vazio ou nulo.
     */
    public List<Usuario> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Nome não deve ser vazio.");
        }

        return usuarios.stream()
                .filter(usuario -> usuario.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Atualiza as informações de um usuário existente.
     * @param usuario O usuário com as novas informações.
     * @return true se a atualização for bem-sucedida, false caso contrário.
     * @throws UsuarioException Se o usuário não for encontrado ou se houver algum conflito de dados (email ou username duplicados).
     */
    public boolean atualizar(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new UsuarioException("Usuário ou ID não pode ser nulo.");
        }

        Usuario usuarioExistente = usuarios.stream()
                .filter(u -> u.getId().equals(usuario.getId()))
                .findFirst()
                .orElse(null);

        if (usuarioExistente == null) {
            return false;
        }

        // Verifica se já existe outro usuário com o mesmo email ou username
        if (usuarios.stream().anyMatch(u -> u.getEmail().equals(usuario.getEmail()) && !u.getId().equals(usuario.getId()))) {
            throw new UsuarioException("Já existe um usuário com este email.");
        }

        if (usuarios.stream().anyMatch(u -> u.getUsername().equals(usuario.getUsername()) && !u.getId().equals(usuario.getId()))) {
            throw new UsuarioException("Já existe um usuário com este username.");
        }

        // Atualiza os dados do usuário
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setSenha(usuario.getSenha());
        usuarioExistente.setDataCadastro(usuario.getDataCadastro());
        usuarioExistente.setAmigos(usuario.getAmigos());
        usuarioExistente.setPosts(usuario.getPosts());

        return true;
    }

    /**
     * Exclui um usuário pelo seu ID.
     * @param id O ID do usuário a ser excluído.
     * @return true se a exclusão for bem-sucedida, false caso contrário.
     * @throws UsuarioException Se o usuário não for encontrado.
     */
    public boolean deletar(int id) {
        if (id <= 0) {
            throw new UsuarioException("ID inválido.");
        }

        Usuario usuarioExistente = usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (usuarioExistente == null) {
            return false;
        }

        usuarios.remove(usuarioExistente);
        return true;
    }

    /**
     * Adiciona um amigo a um usuário, criando uma relação de amizade mútua.
     * @param idUsuario1 O ID do primeiro usuário.
     * @param idUsuario2 O ID do segundo usuário.
     * @throws UsuarioException Se algum dos usuários não for encontrado ou se os IDs forem inválidos.
     */
    public void adicionarAmizade(int idUsuario1, int idUsuario2) {
        if (idUsuario1 <= 0 || idUsuario2 <= 0) {
            throw new UsuarioException("ID inválido.");
        }

        Usuario usuario1 = usuarios.stream()
                .filter(u -> u.getId() == idUsuario1)
                .findFirst()
                .orElse(null);

        Usuario usuario2 = usuarios.stream()
                .filter(u -> u.getId() == idUsuario2)
                .findFirst()
                .orElse(null);

        if (usuario1 == null || usuario2 == null) {
            throw new UsuarioException("Um ou ambos os usuários não foram encontrados.");
        }

        // Adiciona a amizade mútua
        usuario1.adicionarAmigo(usuario2);
        usuario2.adicionarAmigo(usuario1);
    }

    /**
     * Remove uma amizade entre dois usuários, desfazendo a relação mútua de amizade.
     * @param idUsuario1 O ID do primeiro usuário.
     * @param idUsuario2 O ID do segundo usuário.
     * @throws UsuarioException Se algum dos usuários não for encontrado ou se os IDs forem inválidos.
     */
    public void removerAmizade(int idUsuario1, int idUsuario2) {
        if (idUsuario1 <= 0 || idUsuario2 <= 0) {
            throw new UsuarioException("ID inválido.");
        }

        Usuario usuario1 = usuarios.stream()
                .filter(u -> u.getId() == idUsuario1)
                .findFirst()
                .orElse(null);

        Usuario usuario2 = usuarios.stream()
                .filter(u -> u.getId() == idUsuario2)
                .findFirst()
                .orElse(null);

        if (usuario1 == null || usuario2 == null) {
            throw new UsuarioException("Um ou ambos os usuários não foram encontrados.");
        }

        // Remove a amizade mútua
        usuario1.removerAmigo(usuario2);
        usuario2.removerAmigo(usuario1);
    }

    /**
     * Lista todos os usuários registrados.
     * @return Uma lista de todos os usuários.
     */
    public List<Usuario> listarUsuarios(){
        return usuarios;
    }

    /**
     * Valida as informações de um usuário antes de cadastrá-lo.
     * @param usuario O usuário a ser validado.
     * @throws ValidacaoException Se os dados do usuário forem inválidos.
     */
    private void validarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new ValidacaoException("O nome não pode ser vazio.");
        }

        // Verifica se o username já existe
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getUsername().equals(usuario.getUsername())) {
                throw new ValidacaoException("Usuário " + usuario.getUsername() + " já existe.");
            }
        }

        // Verifica se o email é válido
        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new ValidacaoException("Email inválido.");
        }

        // Verifica se a senha tem pelo menos 6 caracteres
        if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
            throw new ValidacaoException("A senha precisa conter no mínimo 6 caracteres.");
        }
    }

    /**
     * Adiciona um post a um usuário.
     * @param usuario O usuário ao qual o post será adicionado.
     * @param post O post a ser adicionado.
     * @throws ValidacaoException Se o usuário ou post forem inválidos.
     */
    public void adicionarPost(Usuario usuario, Post post) {
        if (usuario != null && post != null) {
            usuario.adicionarPost(post);
        } else {
            throw new ValidacaoException("Usuário ou Post inválido.");
        }
    }
}