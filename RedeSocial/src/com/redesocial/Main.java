package com.redesocial;

import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Comentario;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;
import com.redesocial.ui.MenuPrincipal;

import java.time.LocalDateTime;

/**
 * Classe principal do sistema de Rede Social.
 * Responsável por inicializar o programa, configurar dados simulados
 * e exibir o menu principal.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("===== Rede Social =====\n"); // Exibe o título do sistema

        try {
            // Instancia os gerenciadores de usuários e postagens
            GerenciadorUsuarios gerenciadorUsuarios = new GerenciadorUsuarios();
            GerenciadorPosts gerenciadorPosts = new GerenciadorPosts(gerenciadorUsuarios);

            // Instancia o menu principal e inicializa o sistema com dados simulados
            MenuPrincipal menu = new MenuPrincipal();
            inicializarSistema(gerenciadorPosts, gerenciadorUsuarios);

            // Exibe o menu principal ao usuário
            menu.exibirMenu();
        } catch (Exception e) {
            // Captura e exibe mensagens de erro relacionadas à inicialização do sistema
            System.out.println("Erro ao inicializar o sistema: " + e.getMessage());
        }
    }

    /**
     * Método responsável por configurar dados iniciais no sistema.
     * Realiza operações como cadastro de usuários, criação de postagens e adição de interações.
     *
     * @param gerenciadorPosts    Gerenciador de postagens.
     * @param gerenciadorUsuarios Gerenciador de usuários.
     */
    private static void inicializarSistema(GerenciadorPosts gerenciadorPosts, GerenciadorUsuarios gerenciadorUsuarios) {
        // Criação de usuários fictícios
        Usuario usuario1 = new Usuario("Carlos Mendes", "carlos.m", "carlos.mendes@email.com", "senhaCarlos123", LocalDateTime.now());
        Usuario usuario2 = new Usuario("Fernanda Costa", "fernanda.c", "fernanda.costa@email.com", "senhaFernanda456", LocalDateTime.now());
        Usuario usuario3 = new Usuario("Ricardo Almeida", "ricardo.a", "ricardo.almeida@email.com", "senhaRicardo789", LocalDateTime.now());

        // Cadastro dos usuários no sistema
        gerenciadorUsuarios.cadastrar(usuario1);
        gerenciadorUsuarios.cadastrar(usuario2);
        gerenciadorUsuarios.cadastrar(usuario3);

        // Criação de postagens fictícias
        Post post1 = new Post(null, usuario1, "Hoje é um ótimo dia para aprender algo novo!", LocalDateTime.now(), null, null);
        Post post2 = new Post(null, usuario2, "Fui ao parque e tirei fotos incríveis!", LocalDateTime.now(), null, null);

        // Adição das postagens ao gerenciador
        gerenciadorPosts.criar(post1);
        gerenciadorPosts.criar(post2);

        // Associação das postagens aos respectivos autores
        gerenciadorUsuarios.adicionarPost(usuario1, post1);
        gerenciadorUsuarios.adicionarPost(usuario2, post2);

        // Criação de comentários nas postagens
        Comentario comentario1 = new Comentario(usuario3, "Adorei sua energia, Carlos!", post1);
        Comentario comentario2 = new Comentario(usuario1, "As fotos devem estar lindas, Fernanda!", post2);

        // Adição dos comentários às postagens
        gerenciadorPosts.comentar(comentario1);
        gerenciadorPosts.comentar(comentario2);

        // Adição de curtidas às postagens
        post1.adicionarCurtida(usuario2); // Fernanda curte o post de Carlos
        post1.adicionarCurtida(usuario3); // Ricardo curte o post de Carlos
        post2.adicionarCurtida(usuario1); // Carlos curte o post de Fernanda
    }
}