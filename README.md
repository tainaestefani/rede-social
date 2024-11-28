# 🌐 Sistema de Rede Social

## Descrição
Este projeto é uma rede social básica desenvolvida em Java, projetada para oferecer funcionalidades essenciais de interação entre usuários. O sistema permite que os usuários realizem cadastro, login, publiquem posts, gerenciem amizades (adicionando ou removendo amigos), e interajam com posts por meio de curtidas, descurtidas e comentários. Toda a interação com o sistema é realizada por meio de um menu intuitivo exibido no console.

### Funcionalidades do Sistema

1. **Gerenciamento de Usuários:**
   - Cadastro, atualização e exclusão de contas.
   - Busca de usuários por nome ou username.
   - Gerenciamento de amizades: adicionar, remover e listar amigos.

2. **Gerenciamento de Posts:**
   - Criação, edição e exclusão de posts.
   - Interações: curtir, descurtir e comentar posts.
   - Listagem de posts por usuário.

3. **Menu Principal:**
   - Criar Post
   - Ver Perfil
   - Buscar Usuários
   - Gerenciar Amigos
   - Ver Feed de Notícias
   - Ver Posts por Usuário
   - Lista de Usuários
   - Logout

4. **Menu de Usuários:**
   - Editar Perfil
   - Excluir Conta
   - Excluir Post
   - Voltar

## Estrutura do Projeto
Organizado em pacotes para modularidade e clareza:
```
  com.redesocial/
     ├── modelo/            # Classes de modelo/entidades
     ├── gerenciador/       # Classes de gerenciamento de dados
     ├── ui/                # Interface com usuário (console)
     ├── util/              # Classes utilitárias
     └── exception/         # Exceções personalizadas
 ```

## Instruções de Execução

1. **Clone o repositório:**
   ```
   git clone https://github.com/usuario/rede-social.git
   ```
   
2. **Abra o projeto na sua IDE favorita:**
   - Utilize IDEs como IntelliJ IDEA ou Eclipse.

3. **Configurar o JDK:**
   - Certifique-se de ter o JDK 11 ou superior instalado.
   - Configure o JDK na sua IDE:
      - IntelliJ IDEA: File -> Project Structure -> Project -> Selecione o JDK.
      - Eclipse: Window -> Preferences -> Java -> Installed JREs -> Selecione o JDK.

3. **Compile e execute o projeto:**
   - Configure a classe `Main` como ponto de entrada e execute.
   - Ou use o terminal:
       ```
       java com.redesocial.Main
       ```

## Exemplos de Uso

#### Cadastrar Usuário
![image](https://github.com/user-attachments/assets/0e3f5b0d-e877-4765-9c99-99b54b7525c2)

#### Ver Perfil
![image](https://github.com/user-attachments/assets/07b90124-be82-4684-94eb-6a36c245432e)

#### Adicionar Amigo
![image](https://github.com/user-attachments/assets/0385b81d-fa25-4655-979b-cf5457e68ead)

#### Criar Post
![image](https://github.com/user-attachments/assets/e51c92cb-47da-448f-af06-8fa16e1280e8)

#### Buscar por Usuário
![image](https://github.com/user-attachments/assets/07e4ae37-ba2c-4eab-a46f-919f6fbc2d66)

#### Ver Feed de Notícias
![image](https://github.com/user-attachments/assets/dc20a5f2-5f2f-445e-a96c-ddd56790e125)

#### Ver Posts por Usuário
![image](https://github.com/user-attachments/assets/1916f0a7-eaf8-4690-9fd4-64e436fdf17d)

#### Listar Usuários
![image](https://github.com/user-attachments/assets/d194489c-39ec-4ee9-a5f0-e5d9003d713d)


### Decisões de Implementação

1. **Exceções Personalizadas**
   - Foram criadas classes como `PostException`, `UsuarioException` e `ValidacaoException` para identificar e tratar erros específicos de cada funcionalidade do sistema. Essa abordagem ajuda a isolar problemas e facilita o diagnóstico e a manutenção do código.

2. **Validação Centralizada**
   - Todas as validações críticas foram encapsuladas dentro de métodos ou classes dedicadas, eliminando redundâncias e assegurando que regras de negócio, como consistência dos dados e requisitos obrigatórios, sejam aplicadas de maneira uniforme.

3. **Modularidade**
   - O sistema foi projetado com uma arquitetura modular. Cada classe possui uma única responsabilidade bem definida, como o gerenciamento de usuários (`GerenciadorUsuarios`) e de posts (`GerenciadorPosts`). Isso promove organização, clareza e facilita futuras expansões ou correções no sistema.

4. **Uso de Streams**
   - A API de Streams do Java foi amplamente utilizada para buscas e filtragens em coleções. Essa abordagem permitiu um código mais conciso, eficiente e legível, especialmente em operações como busca por usuários e listagem de posts.

5. **Uso de Interface Funcional**
   - A interface `Validador` foi implementada para criar validações de forma flexível e modular. Isso permite configurar diferentes validações sem necessidade de alterar o código principal, promovendo uma estrutura extensível e adaptável.

6. **Organização do Código**
   - Cada classe, exceção e interface está organizada em pacotes específicos (`modelo`, `gerenciador`, `ui`, `exception` e `util`), promovendo separação de responsabilidades e facilitando o entendimento geral da estrutura do projeto.

7. **Manutenibilidade e Extensibilidade**
   - A implementação foi feita com foco em facilitar a manutenção e futuras implementações. O uso de exceções personalizadas e validações modulares reduz o impacto de mudanças no sistema, tornando o código mais preparado para evoluir de acordo com novas necessidades.

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
