package com.redesocial.gerenciador;

import com.redesocial.exception.UsuarioException;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
}