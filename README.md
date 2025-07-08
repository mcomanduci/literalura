# 📚 Literalura

Um catálogo de livros interativo desenvolvido em Java que consome a API Gutendex para buscar e armazenar informações sobre livros e autores.

## 👨‍💻 Autor

**Marcelo Comanduci**  
Projeto desenvolvido durante o curso de Java Backend da [Oracle Next Education (ONE)](https://www.oracle.com/br/education/oracle-next-education/), ministrado pela [Alura](https://www.alura.com.br/).

## 📋 Sobre o Projeto

O Literalura é uma aplicação console que permite aos usuários:
- Buscar livros pelo título através da API Gutendex
- Armazenar informações de livros e autores em banco de dados
- Consultar o catálogo local com diversas opções de filtros
- Explorar informações detalhadas sobre autores e suas obras

## 🚀 Funcionalidades

### 📖 Menu Principal
1. **Buscar livro pelo título** - Busca livros na API Gutendex e salva no banco de dados
2. **Listar livros registrados** - Exibe todos os livros salvos localmente
3. **Listar autores registrados** - Mostra todos os autores cadastrados
4. **Listar autores vivos em um determinado ano** - Filtra autores por período de vida
5. **Listar livros em um determinado idioma** - Filtra livros por idioma
6. **Buscar livros por autor** - Encontra livros de um autor específico

### 🔍 Recursos Avançados
- **Busca inteligente**: Busca exata e por similaridade
- **Prevenção de duplicatas**: Evita cadastrar o mesmo autor/livro múltiplas vezes
- **Relacionamentos**: Mantém vínculos entre autores e suas obras
- **Validações**: Tratamento de erros e validação de entrada
- **Interface amigável**: Menu intuitivo com opções numeradas

## 🛠️ Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.3** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados
- **Jackson** - Serialização/Deserialização JSON
- **Maven** - Gerenciamento de dependências

## 🗄️ API Externa

O projeto consome a [**Gutendx API**](https://gutendx.com/), uma API gratuita que fornece acesso ao catálogo do Projeto Gutenberg com mais de 70.000 livros de domínio público.

## 📊 Estrutura do Banco de Dados

### Entidade Autor
- ID (chave primária)
- Nome (único, obrigatório)
- Ano de nascimento
- Ano de morte
- Lista de livros (relacionamento One-to-Many)

### Entidade Livro
- ID (chave primária)
- Título (único, obrigatório)
- Autor (relacionamento Many-to-One)
- Idioma
- Número de downloads

## 🚦 Como Executar

### Pré-requisitos
- Java 21+
- PostgreSQL instalado e configurado
- Maven 3.6+

### Passos para execução

1. **Clone o repositório**
```bash
git clone [URL_DO_REPOSITORIO]
cd literalura
```

2. **Configure o banco de dados**
   
   Crie um banco PostgreSQL e configure as credenciais em `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true
```

3. **Execute a aplicação**
```bash
mvn spring-boot:run
```

4. **Interaja com o menu**
   
   A aplicação iniciará no console e exibirá o menu principal para navegação.

## 📁 Estrutura do Projeto

```
src/main/java/com/alura/literalura/
├── client/           # Consumo de APIs externas
│   ├── ConsumoApi.java
│   ├── ConverteDados.java
│   └── IConverteDados.java
├── model/            # Entidades e DTOs
│   ├── Autor.java
│   ├── Livro.java
│   ├── DadosAutor.java
│   ├── DadosLivro.java
│   └── DadosResposta.java
├── repository/       # Interfaces de acesso a dados
│   ├── AutorRepository.java
│   └── LivroRepository.java
├── principal/        # Lógica principal e menu
│   └── Principal.java
└── LiteraluraApplication.java  # Classe principal
```

## 🎯 Conceitos Aplicados

- **Programação Orientada a Objetos**: Encapsulamento, herança e polimorfismo
- **Spring Framework**: Injeção de dependência, configuração automática
- **JPA/Hibernate**: Mapeamento objeto-relacional, relacionamentos entre entidades
- **API REST**: Consumo de APIs externas com tratamento de JSON
- **Padrão Repository**: Abstração de acesso a dados
- **Records**: Uso de records para DTOs (Data Transfer Objects)
- **Stream API**: Manipulação funcional de coleções
- **Exception Handling**: Tratamento adequado de exceções

## 📚 Aprendizados

Durante o desenvolvimento deste projeto, foram aplicados e consolidados conhecimentos sobre:

- Consumo de APIs REST
- Persistência com JPA/Hibernate
- Relacionamentos entre entidades
- Tratamento de JSON com Jackson
- Padrões de projeto (Repository, DTO)
- Configuração de aplicações Spring Boot
- Manipulação de dados com Stream API

## 📝 Licença

Este projeto foi desenvolvido para fins educacionais como parte do programa Oracle Next Education (ONE) em parceria com a Alura.

---

⭐ **Se este projeto foi útil para você, deixe uma estrela!**

Desenvolvido com ☕ e 💙 por **Marcelo Comanduci**
