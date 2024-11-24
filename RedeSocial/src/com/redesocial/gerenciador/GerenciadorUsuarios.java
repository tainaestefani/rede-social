package com.redesocial.gerenciador;

import com.redesocial.exception.UsuarioException;
import com.redesocial.exception.ValidacaoException;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorUsuarios {
    private List<Usuario> usuarios;
    private int proximoId;

    public GerenciadorUsuarios() {
        usuarios = new ArrayList<>();
        proximoId = 1;
    }

    public void cadastrar(Usuario usuario) {
        validarUsuario(usuario);
        usuario.setId(proximoId++);

        if (usuarios.stream().anyMatch(u -> u.getEmail().equals(usuario.getEmail()))) {
            throw new UsuarioException("Já existe um usuário com este email.");
        }

        if (usuarios.stream().anyMatch(u -> u.getUsername().equals(usuario.getUsername()))) {
            throw new UsuarioException("Já existe um usuário com este username.");
        }

        usuarios.add(usuario);
    }

    public Usuario buscarPorId(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UsuarioException("Usuário com ID " + id + " não encontrado."));
    }

    public Usuario buscarPorUsername(String username) {
        return usuarios.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsuarioException("Usuário com username " + username + " não encontrado."));
    }

    public List<Usuario> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
                throw new ValidacaoException("Nome não deve ser vazio.");
        }

        return usuarios.stream()
                .filter(usuario -> usuario.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

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

        if (usuarios.stream().anyMatch(u -> u.getEmail().equals(usuario.getEmail()) && !u.getId().equals(usuario.getId()))) {
            throw new UsuarioException("Já existe um usuário com este email.");
        }

        if (usuarios.stream().anyMatch(u -> u.getUsername().equals(usuario.getUsername()) && !u.getId().equals(usuario.getId()))) {
            throw new UsuarioException("Já existe um usuário com este username.");
        }

        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setSenha(usuario.getSenha());
        usuarioExistente.setDataCadastro(usuario.getDataCadastro());
        usuarioExistente.setAmigos(usuario.getAmigos());
        usuarioExistente.setPosts(usuario.getPosts());

        return true;
    }

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

        usuario1.adicionarAmigo(usuario2);
        usuario2.adicionarAmigo(usuario1);
    }

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

        usuario1.removerAmigo(usuario2);
        usuario2.removerAmigo(usuario1);
    }


    private void validarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new ValidacaoException("O nome não pode ser vazio.");
        }

        for (Usuario usuario1 : usuarios) {
            if (usuario1.getUsername().equals(usuario.getUsername())) {
                throw new ValidacaoException("Usuário " + usuario.getUsername() + " já existe.");
            }
        }
        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new ValidacaoException("Email inválido.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
            throw new ValidacaoException("A senha precisa conter no mínimo 6 caracteres.");
        }
    }

    public void adicionarPost(Usuario usuario, Post post) {
        if (usuario != null && post != null) {
            usuario.adicionarPost(post);
        } else {
            throw new ValidacaoException("Usuário ou Post inválido.");
        }
    }
}