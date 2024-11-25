package com.redesocial.ui;

import com.redesocial.exception.ValidacaoException;
import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Usuario;
import com.redesocial.util.Validador;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.Scanner;

public class MenuPrincipal {
    private final GerenciadorUsuarios gerenciadorUsuarios;
    private final GerenciadorPosts gerenciadorPosts;
    private final Scanner scanner;

    public MenuPrincipal() {
        gerenciadorUsuarios = new GerenciadorUsuarios();
        gerenciadorPosts = new GerenciadorPosts(gerenciadorUsuarios);
        scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Cadastrar \n2. Fazer Login \n3. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> fazerLogin();
                case 3 -> continuar = false;
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private void cadastrarUsuario() {
        System.out.println("=== Cadastrar ===");

        Validador<String> validadorNome = nome -> {
            if (nome == null || nome.trim().isEmpty()) {
                throw new ValidacaoException("Nome não pode ser vazio.");
            }
        };

        Validador<String> validadorUsername = username -> {
            if (username == null || username.trim().isEmpty()) {
                throw new ValidacaoException("Username não pode ser vazio.");
            }
        };

        Validador<String> validadorEmail = email -> {
            if (email == null || !email.contains("@")) {
                throw new ValidacaoException("Email inválido.");
            }
        };

        Validador<String> validadorSenha = senha -> {
            if (senha == null || senha.length() < 6) {
                throw new ValidacaoException("Senha deve ter pelo menos 6 caracteres.");
            }
        };

        String nome = obterEntradaValida("Digite seu nome: ", validadorNome);
        String username = obterEntradaValida("Digite seu username: ", validadorUsername);
        String email = obterEntradaValida("Digite seu email: ", validadorEmail);
        String senha = obterEntradaValida("Digite sua senha: ", validadorSenha);

        String senhaCodificada = Base64.getEncoder().encodeToString(senha.getBytes());

        Usuario usuario = new Usuario(nome, username, email, senhaCodificada, LocalDateTime.now());

        try {
            gerenciadorUsuarios.cadastrar(usuario);
            System.out.println("Usuário cadastrado com sucesso!");
            exibirMenuLogado(usuario);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    private String obterEntradaValida(String mensagem, Validador<String> validador) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();
            try {
                validador.validar(entrada);
                return entrada;
            } catch (ValidacaoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void fazerLogin() {
        System.out.println("=== Login ===");
        boolean logado = false;
        while (!logado) {
            try {
                String username = obterEntradaValida("Digite seu username: ", validadorUsername());
                String senha = obterEntradaValida("Digite sua senha: ", validadorSenha());
                Usuario usuario = autenticar(username, senha);
                logado = true;
                exibirMenuLogado(usuario);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("1. Tentar novamente \n2. Voltar");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao == 2) {
                    logado = true;
                }
            }
        }
    }

    private Validador<String> validadorUsername() {
        return username -> {
            if (username == null || username.trim().isEmpty()) {
                throw new ValidacaoException("Username não pode ser vazio.");
            }
        };
    }

    private Validador<String> validadorSenha() {
        return senha -> {
            if (senha == null || senha.length() < 6) {
                throw new ValidacaoException("Senha deve ter pelo menos 6 caracteres.");
            }
        };
    }

    private void exibirMenuLogado(Usuario usuario) {
        MenuUsuario menu = new MenuUsuario(usuario, gerenciadorUsuarios, gerenciadorPosts);
        menu.exibirMenu();
    }

    private Usuario autenticar(String username, String senha) {
        Optional<Usuario> usuarioEncontrado = Optional.ofNullable(gerenciadorUsuarios.buscarPorUsername(username));

        if (usuarioEncontrado.isEmpty()) {
            throw new ValidacaoException("Usuário com username " + username + " não existe");
        }

        Usuario usuario = usuarioEncontrado.get();

        String senhaArmazenada = usuario.getSenha();
        String senhaDecodificada = new String(Base64.getDecoder().decode(senhaArmazenada));

        if (!senhaDecodificada.equals(senha)) {
            throw new ValidacaoException("Senha incorreta");
        }

        return usuario;
    }
}