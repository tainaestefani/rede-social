# 🌐 Sistema de Rede Social

## Descrição
O sistema é uma rede social funcional, onde usuários podem se conectar, criar posts, comentar, curtir e gerenciar amizades. O projeto foi desenvolvido em **Java**, utilizando uma estrutura modular, boas práticas de programação e exceções personalizadas para validações robustas.

## Funcionalidades
O sistema oferece:

1. **Gerenciamento de Usuários:**
   - Cadastro, atualização e exclusão de usuários.
   - Busca por ID, nome ou username.
   - Gerenciamento de amizades (adicionar/remover amigos).

2. **Gerenciamento de Posts:**
   - Criação, edição e exclusão de posts.
   - Listagem de posts por usuário.
   - Interações: curtir, descurtir e comentar posts.

3. **Validações Robustas:**
   - Controle de duplicidade de email e username.
   - Restrições para posts e comentários (limites de caracteres, autor válido, etc.).

4. **Exceções Personalizadas:**
   - Mensagens claras para erros relacionados a posts, usuários e validações.

## Tecnologias Utilizadas
- **Java 8+**
- **Coleções (List, ArrayList):** Para armazenar usuários, posts e comentários.
- **Exceções Personalizadas:** Para controle de erros específicos.
- **DateTime API:** Para registrar data e hora de ações.

## Estrutura do Projeto
Organizado em pacotes para modularidade e clareza:
```
├── com/
│   └── loja/
│       ├── exception/               # Exceções personalizadas
│       ├── gerenciador/             # Classes de gerenciamento de produtos
│       ├── modelo/                  # Modelo da classe Produto
│       ├── ui/                      # Interface de usuário (MenuProdutos)
│       └── Main                     # Classe principal
 ```

## Instruções de Execução

1. **Clone o repositório:**
   ```
   git clone https://github.com/usuario/rede-social.git
   ```
   
2. **Abra o projeto na sua IDE favorita:**
   - Utilize IDEs como IntelliJ IDEA ou Eclipse.
     
3. **Compile e execute o projeto:**
   - Configure a classe `Main` como ponto de entrada e execute.
   - Ou use o terminal:
       ```
       java com.redesocial.Main
       ```

## Exemplos de Uso

// Em construção 🚧

## Decisões de Implementação

1. **Exceções Personalizadas:**
   - Classes como `PostException`, `UsuarioException` e `ValidacaoException` ajudam a tornar os erros mais claros e específicos.

2. **Validação Centralizada:**
   - Validações foram encapsuladas para evitar redundâncias e melhorar a manutenção.

3. **Modularidade:**
   - Cada classe possui uma responsabilidade clara, como gerenciamento de usuários (`GerenciadorUsuarios`) e de posts (`GerenciadorPosts`).

4. **Uso de Streams:**
   - Streams foram utilizados para busca e filtragem eficiente de dados em listas.

## Autor
<div align="left">
  <a href="https://github.com/tainaestefani">
    <img alt="Tainá Estefani Martins" src="https://avatars.githubusercontent.com/u/154456749?v=4" width="115" style="border-radius:50%">
  </a>
  <br>
  <sub><b>Tainá Estefani Martins</b></sub><br>
  <sub>RA: 1778354</sub><br>
</div>

## Licença
Este projeto é licenciado sob a licença MIT. Consulte o arquivo `LICENSE` para obter mais detalhes.
