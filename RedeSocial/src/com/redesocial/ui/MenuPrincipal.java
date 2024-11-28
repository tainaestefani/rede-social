package com.redesocial.ui;

import com.redesocial.exception.ValidacaoException;
import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Usuario;
import com.redesocial.util.Validador;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Classe responsável por exibir o menu principal da aplicação.
 * O menu permite que o usuário se registre, faça login ou encerre a aplicação.
 */
public class MenuPrincipal {
    private final GerenciadorUsuarios gerenciadorUsuarios;
    private final GerenciadorPosts gerenciadorPosts;
    private final Scanner scanner;

    /**
     * Construtor da classe MenuPrincipal.
     * Inicializa os gerenciadores de usuários e posts, e o objeto Scanner.
     */
    public MenuPrincipal() {
        gerenciadorUsuarios = new GerenciadorUsuarios();
        gerenciadorPosts = new GerenciadorPosts(gerenciadorUsuarios);
        scanner = new Scanner(System.in);
    }

    /**
     * Exibe o menu principal para o usuário e processa suas escolhas.
     * Possui as opções de cadastro, login e saída.
     */
    public void exibirMenu() {
        boolean continuar = true; // Controle de loop do menu

        while (continuar) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Cadastrar \n2. Fazer Login \n3. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt(); // Lê a opção escolhida pelo usuário
            scanner.nextLine(); // Limpa o buffer do scanner

            switch (opcao) {
                case 1 -> cadastrarUsuario(); // Chama o método para cadastro de novo usuário
                case 2 -> fazerLogin(); // Chama o método para login do usuário
                case 3 -> continuar = false; // Sai do menu
                default -> System.out.println("Opção inválida"); // Mensagem para entrada inválida
            }
        }
    }

    /**
     * Realiza o processo de cadastro de um novo usuário.
     * Inclui validação de entradas (nome, username, email e senha).
     */
    private void cadastrarUsuario() {
        System.out.println("\n=== Cadastrar ===");

        // Validação para o nome do usuário
        Validador<String> validadorNome = nome -> {
            if (nome == null || nome.trim().isEmpty()) {
                throw new ValidacaoException("Nome não pode ser vazio.");
            }
        };

        // Validação para o username
        Validador<String> validadorUsername = username -> {
            if (username == null || username.trim().isEmpty()) {
                throw new ValidacaoException("Username não pode ser vazio.");
            }
        };

        // Validação para o email
        Validador<String> validadorEmail = email -> {
            if (email == null || !email.contains("@")) {
                throw new ValidacaoException("Email inválido.");
            }
        };

        // Validação para a senha
        Validador<String> validadorSenha = senha -> {
            if (senha == null || senha.length() < 6) {
                throw new ValidacaoException("Senha deve ter pelo menos 6 caracteres.");
            }
        };

        // Obtém entradas válidas do usuário, aplicando as validações
        String nome = obterEntradaValida("Digite seu nome: ", validadorNome);
        String username = obterEntradaValida("Digite seu username: ", validadorUsername);
        String email = obterEntradaValida("Digite seu email: ", validadorEmail);
        String senha = obterEntradaValida("Digite sua senha: ", validadorSenha);

        // Cria um novo objeto do usuário
        Usuario usuario = new Usuario(nome, username, email, senha, LocalDateTime.now());

        try {
            gerenciadorUsuarios.cadastrar(usuario); // Cadastra o usuário no sistema
            System.out.println("Usuário cadastrado com sucesso!");
            exibirMenuLogado(usuario); // Redireciona para o menu do usuário logado
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    /**
     * Lê uma entrada do usuário e aplica a validação fornecida.
     * Continua solicitando até que uma entrada válida seja fornecida.
     * @param mensagem  Mensagem a ser exibida ao usuário.
     * @param validador Validador aplicado à entrada.
     * @return Entrada válida fornecida pelo usuário.
     */
    private String obterEntradaValida(String mensagem, Validador<String> validador) {
        while (true) {
            System.out.print(mensagem); // Exibe a mensagem ao usuário
            String entrada = scanner.nextLine(); // Lê a entrada
            try {
                validador.validar(entrada); // Aplica a validação
                return entrada; // Retorna a entrada se for válida
            } catch (ValidacaoException e) {
                System.out.println(e.getMessage()); // Exibe mensagem de erro
            }
        }
    }

    /**
     * Realiza o processo de login do usuário.
     * Valida as credenciais e, se bem-sucedido, redireciona ao menu logado.
     */
    private void fazerLogin() {
        System.out.println("\n=== Login ===");
        boolean logado = false; // Controle de loop para autenticação

        while (!logado) {
            try {
                // Solicita o username e a senha
                String username = obterEntradaValida("Digite seu username: ", validadorUsername());
                String senha = obterEntradaValida("Digite sua senha: ", validadorSenha());

                // Tenta autenticar o usuário
                Usuario usuario = autenticar(username, senha);
                logado = true; // Define o estado como logado
                exibirMenuLogado(usuario); // Redireciona para o menu logado
            } catch (Exception e) {
                System.out.println(e.getMessage()); // Exibe mensagem de erro
                System.out.println("1. Tentar novamente \n2. Voltar");
                int opcao = scanner.nextInt(); // Lê a escolha do usuário
                scanner.nextLine(); // Limpa o buffer do scanner

                if (opcao == 2) {
                    logado = true; // Sai do loop caso o usuário escolha voltar
                }
            }
        }
    }

    /**
     * Validador para o username.
     * @return Validador configurado para usernames.
     */
    private Validador<String> validadorUsername() {
        return username -> {
            if (username == null || username.trim().isEmpty()) {
                throw new ValidacaoException("Username não pode ser vazio.");
            }
        };
    }

    /**
     * Validador para a senha.
     * @return Validador configurado para senhas.
     */
    private Validador<String> validadorSenha() {
        return senha -> {
            if (senha == null || senha.length() < 6) {
                throw new ValidacaoException("Senha deve ter pelo menos 6 caracteres.");
            }
        };
    }

    /**
     * Redireciona o usuário autenticado para o menu de usuário logado.
     * @param usuario Usuário autenticado.
     */
    private void exibirMenuLogado(Usuario usuario) {
        MenuUsuario menu = new MenuUsuario(usuario, gerenciadorUsuarios, gerenciadorPosts);
        menu.exibirMenu();
    }

    /**
     * Realiza a autenticação do usuário verificando suas credenciais.
     * @param username Username fornecido.
     * @param senha Senha fornecida.
     * @return Usuário autenticado.
     * @throws ValidacaoException Se o username ou senha forem inválidos.
     */
    private Usuario autenticar(String username, String senha) {
        // Busca o usuário pelo username
        Usuario usuario = gerenciadorUsuarios.buscarPorUsername(username); // Não usa Optional aqui, pois o método retorna Usuario ou null

        // Verifica se o usuário foi encontrado
        if (usuario == null) {
            throw new ValidacaoException("Usuário com username " + username + " não existe");
        }

        // Verifica se a senha fornecida corresponde à senha armazenada
        if (!usuario.getSenha().equals(senha)) {
            throw new ValidacaoException("Senha incorreta");
        }

        return usuario; // Retorna o usuário autenticado
    }
}