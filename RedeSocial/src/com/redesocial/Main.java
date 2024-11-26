package com.redesocial;

import com.redesocial.ui.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        System.out.println("----- Rede Social -----"); // Exibe o título do sistema

        try {
            // Instancia o menu principal
            MenuPrincipal menu = new MenuPrincipal();

            // Exibe o menu principal ao usuário
            menu.exibirMenu();
        } catch (Exception e) {
            // Captura e exibe mensagens de erro
            System.out.println("Erro ao inicializar o sistema: " + e.getMessage());
        }
    }
}