package com.redesocial.ui;

import com.redesocial.exception.UsuarioException;
import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Comentario;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Classe responsável por exibir o menu do usuário e gerenciar suas interações com o sistema.
 * Inclui funcionalidades como criação de posts, gerenciamento de amizades, e visualização do feed de notícias.
 */
public class MenuUsuario {
    private Usuario usuario;
    private final GerenciadorUsuarios gerenciadorUsuarios;
    private final GerenciadorPosts gerenciadorPosts;
    private final Scanner scanner;

    /**
     * Construtor da classe MenuUsuario.
     * @param usuario Usuário logado no sistema.
     * @param gerenciadorUsuarios Instância do gerenciador de usuários.
     * @param gerenciadorPosts Instância do gerenciador de postagens.
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
            System.out.println("\n=== Menu do Usuário ===");
            System.out.println("Bem-vindo, " + usuario.getNome());
            System.out.println("1. Criar Post");
            System.out.println("2. Ver Perfil");
            System.out.println("3. Buscar Usuários");
            System.out.println("4. Gerenciar Amigos");
            System.out.println("5. Ver Feed de Notícias");
            System.out.println("6. Ver Posts por Usuário");
            System.out.println("7. Logout");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt(); // Lê a opção escolhida pelo usuário
            scanner.nextLine(); // Limpa o buffer do scanner

            // Executa a funcionalidade correspondente à opção escolhida
            switch (opcao) {
                case 1 -> criarPost();
                case 2 -> verPerfil();
                case 3 -> buscarUsuarios();
                case 4 -> gerenciarAmizades();
                case 5 -> verFeedNoticias();
                case 6 -> listarPorUsuario();
                case 7 -> {
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
     * Exibe o perfil do usuário logado com informações detalhadas,
     * como nome, username, email, data de cadastro, número de amigos e posts.
     * Após a exibição, permite que o usuário escolha ações relacionadas ao perfil.
     */
    private void verPerfil() {
        System.out.println("\n=== Meu Perfil ===");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Username: " + usuario.getUsername());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Data de Cadastro: " + usuario.getDataCadastro());
        System.out.println("Número de Amigos: " + usuario.getAmigos().size());
        System.out.println("Número de Posts: " + usuario.getPosts().size());

        System.out.println("\n1. Editar perfil");
        System.out.println("2. Excluir conta");
        System.out.println("3. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = new java.util.Scanner(System.in).nextInt();

        switch (opcao) {
            case 1 -> editarPerfil();
            case 2 -> excluirConta();
            default -> System.out.println("Voltando ao menu principal...");
        }
    }

    /**
     * Permite ao usuário editar seu perfil, incluindo nome, username, email e senha.
     * O usuário pode optar por manter os valores atuais pressionando ENTER.
     */
    private void editarPerfil() {
        System.out.println("\n=== Editar Perfil ===");
        System.out.println("Digite os novos dados (ou pressione ENTER para manter o valor atual):");

        try {
            // Nome
            System.out.print("Nome [" + usuario.getNome() + "]: ");
            String nome = new java.util.Scanner(System.in).nextLine();
            if (!nome.trim().isEmpty()) {
                usuario.setNome(nome);
            }

            // Username
            System.out.print("Username [" + usuario.getUsername() + "]: ");
            String username = new java.util.Scanner(System.in).nextLine();
            if (!username.trim().isEmpty()) {
                usuario.setUsername(username);
            }

            // Email
            System.out.print("Email [" + usuario.getEmail() + "]: ");
            String email = new java.util.Scanner(System.in).nextLine();
            if (!email.trim().isEmpty()) {
                usuario.setEmail(email);
            }

            // Senha
            System.out.print("Senha [" + usuario.getSenha() + "]: ");
            String senha = new java.util.Scanner(System.in).nextLine();
            if (!senha.trim().isEmpty()) {
                usuario.setSenha(senha);
            }

            // Atualiza o perfil
            if (gerenciadorUsuarios.atualizar(usuario)) {
                System.out.println("Perfil atualizado com sucesso!");
            } else {
                System.out.println("Não foi possível atualizar o perfil.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o perfil: " + e.getMessage());
        }
    }

    /**
     * Permite ao usuário buscar outros usuários pelo nome ou parte do nome.
     */
    private void buscarUsuarios() {
        System.out.println("Digite o nome de usuário ou parte do nome para buscar:");
        String busca = scanner.nextLine(); // Lê o nome ou parte do nome para busca

        // Busca usuários pelo nome (parcial)
        List<Usuario> usuariosEncontrados = gerenciadorUsuarios.buscarPorNome(busca);

        // Busca usuários pelo username (parcial) dentro do mesmo método
        List<Usuario> usuariosPorUsername = gerenciadorUsuarios.listarUsuarios().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(busca.toLowerCase()))
                .collect(Collectors.toList());

        // Combina os resultados da busca por nome e username
        usuariosEncontrados.addAll(usuariosPorUsername);

        // Exibe os resultados
        if (!usuariosEncontrados.isEmpty()) {
            System.out.println("Usuários encontrados:");
            // Para evitar duplicação de usuários, podemos filtrar os repetidos (caso um usuário seja encontrado por nome e username)
            usuariosEncontrados.stream()
                    .distinct()
                    .forEach(u -> System.out.println(u.getNome() + " (" + u.getUsername() + ")"));
        } else {
            System.out.println("Nenhum usuário encontrado.");
        }
    }

    /**
     * Permite ao usuário gerenciar sua lista de amigos.
     */
    private void gerenciarAmizades() {
        System.out.println("\n=== Gerenciamento de Amigos ===");
        System.out.println("1. Adicionar Amigo");
        System.out.println("2. Remover Amigo");
        System.out.println("3. Listar Amigos");
        System.out.println("4. Voltar");

        int opcao = scanner.nextInt(); // Lê a opção escolhida
        scanner.nextLine(); // Consome a quebra de linha

        // Executa a funcionalidade correspondente à opção
        switch (opcao) {
            case 1 -> adicionarAmigo();
            case 2 -> removerAmigo();
            case 3 -> listarAmigos();
            case 4 -> System.out.println("Voltando...");
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
     * Exibe a lista de amigos do usuário logado.
     */
    private void listarAmigos() {
    // Verifica se o usuário tem amigos
    if (usuario.getAmigos().isEmpty()) {
        System.out.println("Você ainda não tem amigos.");
    } else {
        System.out.println("Seus amigos:");
        // Percorre a lista de amigos e imprime informações de forma legível
        for (Usuario amigo : usuario.getAmigos()) {
            System.out.println("- " + amigo.getNome() + " (" + amigo.getUsername() + ")");
        }
    }

    /**
     * Exibe o feed de notícias do usuário, incluindo posts de amigos e do próprio usuário.
     */
    private void verFeedNoticias() {
        try {
            System.out.println("\n=== Feed de Notícias ===");

            // Filtra os posts de amigos ou do próprio usuário, ordenados pela data de publicação
            List<Post> posts = gerenciadorPosts.listarPosts().stream()
                    .filter(post -> usuario.getAmigos().contains(post.getAutor()) || post.getAutor().equals(usuario))
                    .sorted((p1, p2) -> p2.getDataPublicacao().compareTo(p1.getDataPublicacao()))
                    .toList();

            if (!posts.isEmpty()) {
                posts.forEach(post -> System.out.println(post)); // Exibe os posts do feed
                System.out.println("Digite o número do post para interagir ou 0 para voltar: ");
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
     * Lista os posts de um usuário com base no username fornecido.
     * Permite buscar posts ou retornar ao menu anterior digitando "0".
     */
    private void listarPorUsuario() {
        while (true) {
            try {
                System.out.print("Digite o username do usuário (ou 0 para voltar): ");
                String username = new java.util.Scanner(System.in).nextLine();

                if (username.equals("0")) {
                    System.out.println("Voltando ao menu principal...");
                    break;
                }

                Usuario usuario = gerenciadorUsuarios.buscarPorUsername(username);
                if (usuario != null) {
                    List<Post> posts = gerenciadorPosts.listarPorUsuario(usuario.getId());
                    if (!posts.isEmpty()) {
                        for (Post post : posts) {
                            System.out.println(post);
                        }
                    } else {
                        System.out.println("Este usuário não possui posts.");
                    }
                } else {
                    System.out.println("Username inválido. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage() + ". Tente novamente.");
            }
        }
    }

    /**
     * Permite ao usuário interagir com uma postagem específica (curtir ou comentar).
     * @param id Identificador do post a ser interagido.
     */
    private void interagirPost(int id) {
        try {
            Post post = gerenciadorPosts.buscarPorId(id); // Busca o post pelo ID
            System.out.println("Interagindo com o post #" + id);
            System.out.println("1. Curtir\n2. Descurtir\n3. Comentar\n4. Voltar");
            int opcao = scanner.nextInt(); // Lê a opção escolhida
            scanner.nextLine();

            // Verifica se o usuário já curtiu o post
            boolean usuarioCurtiu = post.getCurtidas().contains(usuario);

            // Executa a funcionalidade correspondente à interação
            switch (opcao) {
                case 1 -> {
                    if (usuarioCurtiu) {
                        System.out.println("Você já curtiu este post.");
                    } else {
                        post.adicionarCurtida(usuario); // Adiciona uma curtida
                        System.out.println("Você curtiu o post!");
                    }
                }
                case 2 -> {
                    if (!usuarioCurtiu) {
                        System.out.println("Você ainda não curtiu este post.");
                    } else {
                        post.removerCurtida(usuario); // Remove a curtida, se existir
                        System.out.println("Você removeu sua curtida do post.");
                    }
                }
                case 3 -> {
                    System.out.print("Digite seu comentário: ");
                    String conteudoComentario = scanner.nextLine(); // Lê o comentário
                    Comentario comentario = new Comentario(usuario, conteudoComentario, post); // Cria um novo comentário
                    gerenciadorPosts.comentar(comentario); // Adiciona o comentário
                    System.out.println("Comentário adicionado com sucesso!");
                }
                case 4 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Realiza a exclusão da conta do usuário logado.
     * Solicita confirmação antes de proceder com a exclusão e informa o resultado da operação.
     */
    private void excluirConta() {
        System.out.println("\n=== Excluir Conta ===");
        System.out.print("Tem certeza que deseja excluir sua conta? (1 - Sim / 2 - Não): ");
        int opcao = new java.util.Scanner(System.in).nextInt();

        if (opcao == 1) {
            boolean contaExcluida = gerenciadorUsuarios.deletar(usuario.getId());
            if (contaExcluida) {
                usuario = null; // Remove a referência ao usuário
                System.out.println("Conta excluída com sucesso!");
            } else {
                System.out.println("Erro ao excluir conta.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
