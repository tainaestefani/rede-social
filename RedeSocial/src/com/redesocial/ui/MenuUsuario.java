package com.redesocial.ui;

import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Comentario;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por exibir o menu do usuário e gerenciar suas interações com o sistema.
 * Inclui funcionalidades como criação de posts, gerenciamento de amizades, e visualização do feed de notícias.
 */
public class MenuUsuario {
    private final Usuario usuario; // Usuário logado
    private final GerenciadorUsuarios gerenciadorUsuarios; // Gerenciador para operações relacionadas aos usuários
    private final GerenciadorPosts gerenciadorPosts; // Gerenciador para operações relacionadas às postagens
    private final Scanner scanner; // Scanner para entrada de dados do usuário

    /**
     * Construtor da classe MenuUsuario.
     *
     * @param usuario            Usuário logado no sistema.
     * @param gerenciadorUsuarios Instância do gerenciador de usuários.
     * @param gerenciadorPosts    Instância do gerenciador de postagens.
     */
    public MenuUsuario(Usuario usuario, GerenciadorUsuarios gerenciadorUsuarios, GerenciadorPosts gerenciadorPosts) {
        this.usuario = usuario;
        this.gerenciadorUsuarios = gerenciadorUsuarios;
        this.gerenciadorPosts = gerenciadorPosts;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Exibe o menu principal do usuário e gerencia as opções escolhidas.
     */
    public void exibirMenu() {
        boolean continuar = true;

        while (continuar) {
            // Exibe opções do menu principal
            System.out.println("=== Menu do Usuário ===");
            System.out.println("Bem-vindo, " + usuario.getNome());
            System.out.println("1. Criar Post");
            System.out.println("2. Ver Perfil");
            System.out.println("3. Buscar Usuários");
            System.out.println("4. Gerenciar Amigos");
            System.out.println("5. Ver Feed de Notícias");
            System.out.println("6. Logout");

            int opcao = scanner.nextInt(); // Lê a opção escolhida pelo usuário
            scanner.nextLine(); // Consome a quebra de linha

            // Executa a funcionalidade correspondente à opção escolhida
            switch (opcao) {
                case 1 -> criarPost();
                case 2 -> verPerfil();
                case 3 -> buscarUsuarios();
                case 4 -> gerenciarAmizades();
                case 5 -> verFeedNoticias();
                case 6 -> {
                    System.out.println("Desconectando...");
                    continuar = false;
                }
                default -> System.out.println("Opção inválida");
            }
        }
    }

    /**
     * Permite ao usuário criar uma nova postagem.
     */
    private void criarPost() {
        System.out.print("Digite o conteúdo do seu post: ");
        String conteudo = scanner.nextLine(); // Lê o conteúdo do post

        // Cria uma nova instância de Post
        Post novoPost = new Post(null, usuario, conteudo, LocalDateTime.now(), null, null);

        try {
            gerenciadorPosts.criar(novoPost); // Adiciona o post ao gerenciador
            System.out.println("Post criado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar post: " + e.getMessage());
        }
    }

    /**
     * Exibe o perfil do usuário logado, com informações como username e estatísticas.
     */
    private void verPerfil() {
        System.out.println("=== Perfil de " + usuario.getNome() + " ===");
        System.out.println("Username: " + usuario.getUsername());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Data de Cadastro: " + usuario.getDataCadastro());
        System.out.println("Número de Amigos: " + usuario.getAmigos().size());
        System.out.println("Número de Posts: " + usuario.getPosts().size());
    }

    /**
     * Permite ao usuário buscar outros usuários pelo nome ou parte do nome.
     */
    private void buscarUsuarios() {
        System.out.println("Digite o nome de usuário ou parte do nome para buscar:");
        String nomeBusca = scanner.nextLine(); // Lê o nome ou parte do nome para busca

        List<Usuario> usuariosEncontrados = gerenciadorUsuarios.buscarPorNome(nomeBusca); // Busca usuários

        if (!usuariosEncontrados.isEmpty()) {
            System.out.println("Usuários encontrados:");
            usuariosEncontrados.forEach(u -> System.out.println(u.getNome() + " (" + u.getUsername() + ")"));
        } else {
            System.out.println("Nenhum usuário encontrado.");
        }
    }

    /**
     * Permite ao usuário gerenciar sua lista de amigos.
     */
    private void gerenciarAmizades() {
        System.out.println("=== Gerenciamento de Amigos ===");
        System.out.println("1. Adicionar Amigo");
        System.out.println("2. Remover Amigo");
        System.out.println("3. Voltar");

        int opcao = scanner.nextInt(); // Lê a opção escolhida
        scanner.nextLine(); // Consome a quebra de linha

        // Executa a funcionalidade correspondente à opção
        switch (opcao) {
            case 1 -> adicionarAmigo();
            case 2 -> removerAmigo();
            case 3 -> System.out.println("Voltando...");
            default -> System.out.println("Opção inválida.");
        }
    }

    /**
     * Permite ao usuário adicionar um amigo à sua lista.
     */
    private void adicionarAmigo() {
        System.out.println("Digite o nome de usuário para adicionar como amigo:");
        String nomeUsuario = scanner.nextLine(); // Lê o username do amigo a ser adicionado
        Usuario amigo = gerenciadorUsuarios.buscarPorUsername(nomeUsuario); // Busca o usuário pelo username

        if (amigo != null && !usuario.getAmigos().contains(amigo)) {
            gerenciadorUsuarios.adicionarAmizade(usuario.getId(), amigo.getId()); // Adiciona a amizade
            System.out.println("Você agora é amigo de " + amigo.getNome());
        } else {
            System.out.println("Usuário não encontrado ou já é seu amigo.");
        }
    }

    /**
     * Permite ao usuário remover um amigo de sua lista.
     */
    private void removerAmigo() {
        System.out.println("Digite o nome de usuário para remover da lista de amigos:");
        String nomeUsuario = scanner.nextLine(); // Lê o username do amigo a ser removido
        Usuario amigo = gerenciadorUsuarios.buscarPorUsername(nomeUsuario); // Busca o usuário pelo username

        if (amigo != null && usuario.getAmigos().contains(amigo)) {
            gerenciadorUsuarios.removerAmizade(usuario.getId(), amigo.getId()); // Remove a amizade
            System.out.println("Amigo removido com sucesso.");
        } else {
            System.out.println("Usuário não encontrado ou não é seu amigo.");
        }
    }

    /**
     * Exibe o feed de notícias do usuário, incluindo posts de amigos e do próprio usuário.
     */
    private void verFeedNoticias() {
        try {
            System.out.println("=== Feed de Notícias ===");

            // Filtra os posts de amigos ou do próprio usuário, ordenados pela data de publicação
            List<Post> posts = gerenciadorPosts.listarPosts().stream()
                    .filter(post -> usuario.getAmigos().contains(post.getAutor()) || post.getAutor().equals(usuario))
                    .sorted((p1, p2) -> p2.getDataPublicacao().compareTo(p1.getDataPublicacao()))
                    .toList();

            if (!posts.isEmpty()) {
                posts.forEach(post -> System.out.println(post)); // Exibe os posts do feed
                System.out.println("Digite o número do post para interagir ou 0 para voltar.");
                int opcao = scanner.nextInt(); // Lê a interação do usuário
                scanner.nextLine();

                if (opcao != 0) {
                    interagirPost(opcao); // Chama o método para interagir com um post
                }
            } else {
                System.out.println("Não há posts no feed de notícias.");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Permite ao usuário interagir com uma postagem específica (curtir ou comentar).
     *
     * @param id Identificador do post a ser interagido.
     */
    private void interagirPost(int id) {
        try {
            Post post = gerenciadorPosts.buscarPorId(id); // Busca o post pelo ID
            System.out.println("Interagindo com o post #" + id);
            System.out.println("1. Curtir\n2. Comentar\n3. Voltar");
            int opcao = scanner.nextInt(); // Lê a opção escolhida
            scanner.nextLine();

            // Executa a funcionalidade correspondente à interação
            switch (opcao) {
                case 1 -> {
                    gerenciadorPosts.curtir(post.getId(), usuario.getId()); // Adiciona uma curtida
                    System.out.println("Você curtiu o post!");
                }
                case 2 -> {
                    System.out.print("Digite seu comentário: ");
                    String conteudoComentario = scanner.nextLine(); // Lê o comentário
                    Comentario comentario = new Comentario(usuario, conteudoComentario, post); // Cria um novo comentário
                    gerenciadorPosts.comentar(comentario); // Adiciona o comentário
                    System.out.println("Comentário adicionado com sucesso!");
                }
                case 3 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}