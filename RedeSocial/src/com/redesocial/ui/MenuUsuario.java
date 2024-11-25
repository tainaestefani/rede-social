package com.redesocial.ui;

import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Comentario;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MenuUsuario {
    private final Usuario usuario;
    private final GerenciadorUsuarios gerenciadorUsuarios;
    private final GerenciadorPosts gerenciadorPosts;
    private final Scanner scanner;

    public MenuUsuario(Usuario usuario, GerenciadorUsuarios gerenciadorUsuarios, GerenciadorPosts gerenciadorPosts) {
        this.usuario = usuario;
        this.gerenciadorUsuarios = gerenciadorUsuarios;
        this.gerenciadorPosts = gerenciadorPosts;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("=== Menu do Usuário ===");
            System.out.println("Bem-vindo, " + usuario.getNome());
            System.out.println("1. Criar Post");
            System.out.println("2. Ver Perfil");
            System.out.println("3. Buscar Usuários");
            System.out.println("4. Gerenciar Amigos");
            System.out.println("5. Ver Feed de Notícias");
            System.out.println("6. Logout");

            int opcao = scanner.nextInt();
            scanner.nextLine();

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

    private void criarPost() {
        System.out.print("Digite o conteúdo do seu post: ");
        String conteudo = scanner.nextLine();

        Post novoPost = new Post(null, usuario, conteudo, LocalDateTime.now(), null, null);

        try {
            gerenciadorPosts.criar(novoPost);
            System.out.println("Post criado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar post: " + e.getMessage());
        }
    }

    private void verPerfil() {
        System.out.println("=== Perfil de " + usuario.getNome() + " ===");
        System.out.println("Username: " + usuario.getUsername());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Data de Cadastro: " + usuario.getDataCadastro());
        System.out.println("Número de Amigos: " + usuario.getAmigos().size());
        System.out.println("Número de Posts: " + usuario.getPosts().size());
    }

    private void buscarUsuarios() {
        System.out.println("Digite o nome de usuário ou parte do nome para buscar:");
        String nomeBusca = scanner.nextLine();

        List<Usuario> usuariosEncontrados = gerenciadorUsuarios.buscarPorNome(nomeBusca);

        if (!usuariosEncontrados.isEmpty()) {
            System.out.println("Usuários encontrados:");
            usuariosEncontrados.forEach(u -> System.out.println(u.getNome() + " (" + u.getUsername() + ")"));
        } else {
            System.out.println("Nenhum usuário encontrado.");
        }
    }

    private void gerenciarAmizades() {
        System.out.println("=== Gerenciamento de Amigos ===");
        System.out.println("1. Adicionar Amigo");
        System.out.println("2. Remover Amigo");
        System.out.println("3. Voltar");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> adicionarAmigo();
            case 2 -> removerAmigo();
            case 3 -> System.out.println("Voltando...");
            default -> System.out.println("Opção inválida.");
        }
    }

    private void adicionarAmigo() {
        System.out.println("Digite o nome de usuário para adicionar como amigo:");
        String nomeUsuario = scanner.nextLine();
        Usuario amigo = gerenciadorUsuarios.buscarPorUsername(nomeUsuario);

        if (amigo != null && !usuario.getAmigos().contains(amigo)) {
            gerenciadorUsuarios.adicionarAmizade(usuario.getId(), amigo.getId());
            System.out.println("Você agora é amigo de " + amigo.getNome());
        } else {
            System.out.println("Usuário não encontrado ou já é seu amigo.");
        }
    }

    private void removerAmigo() {
        System.out.println("Digite o nome de usuário para remover da lista de amigos:");
        String nomeUsuario = scanner.nextLine();
        Usuario amigo = gerenciadorUsuarios.buscarPorUsername(nomeUsuario);

        if (amigo != null && usuario.getAmigos().contains(amigo)) {
            gerenciadorUsuarios.removerAmizade(usuario.getId(), amigo.getId());
            System.out.println("Amigo removido com sucesso.");
        } else {
            System.out.println("Usuário não encontrado ou não é seu amigo.");
        }
    }

    private void interagirPost(int id) {
        try {
            Post post = gerenciadorPosts.buscarPorId(id);
            System.out.println("Interagindo com o post #" + id);
            System.out.println("1. Curtir\n2. Comentar\n3. Voltar");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> {
                    gerenciadorPosts.curtir(post.getId(), usuario.getId());
                    System.out.println("Você curtiu o post!");
                }
                case 2 -> {
                    System.out.print("Digite seu comentário: ");
                    String conteudoComentario = scanner.nextLine();
                    Comentario comentario = new Comentario(usuario, conteudoComentario, post);
                    gerenciadorPosts.comentar(comentario);
                    System.out.println("Comentário adicionado com sucesso!");
                }
                case 3 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void verFeedNoticias() {
        try {
            System.out.println("=== Feed de Notícias ===");

            List<Post> posts = gerenciadorPosts.listarPosts().stream()
                    .filter(post -> usuario.getAmigos().contains(post.getAutor()) || post.getAutor().equals(usuario))
                    .sorted((p1, p2) -> p2.getDataPublicacao().compareTo(p1.getDataPublicacao()))
                    .toList();

            if (!posts.isEmpty()) {
                posts.forEach(post -> System.out.println(post));
                System.out.println("Digite o número do post para interagir ou 0 para voltar.");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao != 0) {
                    interagirPost(opcao);
                }
            } else {
                System.out.println("Não há posts no feed de notícias.");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}