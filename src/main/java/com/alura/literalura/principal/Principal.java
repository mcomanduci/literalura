package com.alura.literalura.principal;

import com.alura.literalura.client.ConsumoApi;
import com.alura.literalura.client.ConverteDados;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DadosResposta;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;

import java.util.Scanner;

public class Principal {
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private final Scanner sc = new Scanner(System.in);
    private final LivroRepository repositorioLivro;
    private final AutorRepository repositorioAutor;

    public Principal(LivroRepository repositorioLivro, AutorRepository repositorioAutor) {
        this.repositorioLivro = repositorioLivro;
        this.repositorioAutor = repositorioAutor;
    }

    public void exibeMenu() {
        String menu = """
                Escolha o número de sua opção:
                1 - Buscar livro pelo título
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em um determinado ano
                5 - Listar livros em um determinado idioma
                6 - Buscar livros por autor
                0 - Sair
                """;
        String opcao = "-1";

        while (!opcao.equals("0")) {
            System.out.println(menu);
            opcao = sc.nextLine();

            switch (opcao) {
                case "1" -> buscaLivro();
                case "2" -> listarLivros();
                case "3" -> listarAutores();
                case "4" -> listarAutoresVivosEmAno();
                case "5" -> listarLivrosPorIdioma();
                case "6" -> buscarLivrosPorAutor();
                case "0" -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        }

    }

    public void buscaLivro() {
        try {
            System.out.println("Digite o nome do livro: ");
            String nomeLivro = sc.nextLine().trim().replace(" ", "%20");

            var respostaJson = consumo.obterDados(ENDERECO + nomeLivro);
            var dadosResposta = conversor.obterDados(respostaJson, DadosResposta.class);

            if (dadosResposta.resultados().isEmpty()) {
                System.out.println("Nenhum livro encontrado com esse título.");
                return;
            }

            var dadosLivro = dadosResposta.resultados().getFirst();

            if (dadosLivro.autores().isEmpty()) {
                System.out.println("Livro encontrado, mas não possui autor registrado.");
                return;
            }

            String nomeAutor = dadosLivro.autores().getFirst().nome();

            Autor autor = repositorioAutor.findByNome(nomeAutor).orElseGet(() -> {
                Autor novoAutor = new Autor(dadosLivro);
                return repositorioAutor.save(novoAutor);
            });

            Livro livro = new Livro(dadosLivro);
            livro.setAutor(autor);

            repositorioLivro.save(livro);

            System.out.println("Livro salvo com sucesso!");
            System.out.println(livro);

        } catch (Exception e) {
            System.err.println("Erro ao processar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listarLivros() {
        var livros = repositorioLivro.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    public void listarAutores() {
        var autores = repositorioAutor.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    public void listarAutoresVivosEmAno() {
        System.out.println("Digite o ano para verificar quais autores estavam vivos: ");
        try {
            int ano = Integer.parseInt(sc.nextLine());
            var autores = repositorioAutor.findAutoresVivosNoAno(ano);

            if (autores.isEmpty()) {
                System.out.println("Nenhum autor estava vivo em " + ano);
            } else {
                System.out.println("\n===== AUTORES VIVOS EM " + ano + " =====");
                autores.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido. Digite apenas números.");
        }
    }

    public void listarLivrosPorIdioma() {
        String menuIdiomas = """
                Escolha o idioma:
                en - Inglês
                pt - Português
                fr - Francês
                es - Espanhol
                """;
        System.out.println(menuIdiomas);
        String idiomaSelecionado = sc.nextLine().toLowerCase();

        String idiomaCompleto = switch (idiomaSelecionado) {
            case "en" -> "Inglês";
            case "pt" -> "Português";
            case "fr" -> "Francês";
            case "es" -> "Espanhol";
            default -> {
                System.out.println("Idioma não suportado.");
                yield null;
            }
        };

        if (idiomaCompleto != null) {
            var livros = repositorioLivro.findByIdioma(idiomaCompleto);

            if (livros.isEmpty()) {
                System.out.println("Nenhum livro encontrado em " + idiomaCompleto);
            } else {
                System.out.println("\n===== LIVROS EM " + idiomaCompleto.toUpperCase() + " =====");
                livros.forEach(System.out::println);
            }
        }
    }

    private void buscarLivrosPorAutor() {
        var autoresComLivros = repositorioAutor.findAutoresComLivros();

        if (autoresComLivros.isEmpty()) {
            System.out.println("Nenhum autor com livros cadastrados foi encontrado.");
            System.out.println("Dica: Busque alguns livros primeiro para cadastrar autores.");
            return;
        }

        System.out.println("\n===== AUTORES DISPONÍVEIS =====");
        for (int i = 0; i < autoresComLivros.size(); i++) {
            Autor autor = autoresComLivros.get(i);
            System.out.printf("%d - %s (%d livro(s))%n",
                    i + 1,
                    autor.getNome(),
                    autor.getLivros().size());
        }

        System.out.println("0 - Voltar");
        System.out.println("\nEscolha o número do autor: ");

        try {
            int escolha = Integer.parseInt(sc.nextLine());

            if (escolha == 0) {
                return;
            }

            if (escolha < 1 || escolha > autoresComLivros.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            Autor autorSelecionado = autoresComLivros.get(escolha - 1);
            exibirLivrosDoAutor(autorSelecionado);

        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite apenas números.");
        }
    }

    private void exibirLivrosDoAutor(Autor autor) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("AUTOR: " + autor.getNome());
        if (autor.getAnoDeNascimento() != null) {
            System.out.print("Nascimento: " + autor.getAnoDeNascimento());
            if (autor.getAnoDeMorte() != null) {
                System.out.println(" - Morte: " + autor.getAnoDeMorte());
            } else {
                System.out.println(" - Ainda vivo");
            }
        }
        System.out.println("=".repeat(50));

        var livros = autor.getLivros();
        System.out.println("LIVROS (" + livros.size() + " encontrado(s)):");
        System.out.println();

        for (int i = 0; i < livros.size(); i++) {
            var livro = livros.get(i);
            System.out.printf("%d. %s%n", i + 1, livro.getTitulo());
            System.out.printf("   Idioma: %s%n", livro.getIdiomas());
            System.out.printf("   Downloads: %s%n",
                    livro.getNumeroDownloads() != null ? String.format("%,d", livro.getNumeroDownloads()) : "N/A");

            if (i < livros.size() - 1) {
                System.out.println("   " + "-".repeat(30));
            }
        }
        System.out.println("=".repeat(50));
    }
}
