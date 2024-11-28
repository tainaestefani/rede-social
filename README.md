# 🌐 Sistema de Rede Social

## Descrição
Este projeto é uma rede social básica desenvolvida em Java, permitindo que usuários se cadastrem, façam login, publiquem posts, adicionem e removam amigos, curtam e comentem nas publicações. A interação com o sistema ocorre por meio de um menu no console.

### Funcionalidades do Sistema

1. **Gerenciamento de Usuários:**
   - Cadastro, atualização e exclusão de contas.
   - Busca de usuários por nome ou username.
   - Gerenciamento de amizades: adicionar ou remover amigos.

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
   - Logout

4. **Menu de Usuários:**
   - Editar Perfil
   - Excluir Conta
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
![image](https://github.com/user-attachments/assets/39a2e35e-9045-4d5a-aeb0-f7ce006533e4)

#### Adicionar Amigo
![image](https://github.com/user-attachments/assets/9fc07172-3ab2-4576-a3e3-0f0327eae98a)

#### Criar Post
![image](https://github.com/user-attachments/assets/7e1edf95-780a-4cbe-a247-292b31fbf997)

#### Buscar por Usuário
![image](https://github.com/user-attachments/assets/36fe2258-25fb-4664-ab7d-fece8cea06db)

#### Ver Feed de Notícias
![image](https://github.com/user-attachments/assets/f33d1e36-7729-4902-9e89-70c90b459926)


## Decisões de Implementação

1. **Exceções Personalizadas:**
   - Classes como `PostException`, `UsuarioException` e `ValidacaoException` ajudam a tornar os erros mais claros e específicos.

2. **Validação Centralizada:**
   - Validações foram encapsuladas para evitar redundâncias e melhorar a manutenção.

3. **Modularidade:**
   - Cada classe possui uma responsabilidade clara, como gerenciamento de usuários (`GerenciadorUsuarios`) e de posts (`GerenciadorPosts`).

4. **Uso de Streams:**
   - Streams foram utilizados para busca e filtragem eficiente de dados em listas.

5. **Uso de Interface Funcional:**
   - A interface `Validador` foi implementada para realizar validações de forma modular e configurável, promovendo flexibilidade no código.

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
