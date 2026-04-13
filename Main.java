import java.util.List;

public class Main {
    private static final LivroService service = new LivroService();

    public static void main(String[] args) {
        String menu = """
                ===== SysBiblioteca =====

                1. Cadastrar Livro
                2. Listar Livros
                3. Pesquisar por Título
                4. Pesquisar por Autor
                5. Pesquisar por Ano
                6. Editar Livro
                7. Remover Livro
                8. Remover TODOS os livros
                0. Sair
                """;

        int opcao;
        do {
            IO.println(menu);
            opcao = Input.scanInt("Digite uma opção: ");
            try {
                switch (opcao) {
                    case 1 -> cadastrarLivro();
                    case 2 -> listarLivros();
                    case 3 -> pesquisarPorTitulo();
                    case 4 -> pesquisarPorAutor();
                    case 5 -> pesquisarPorAno();
                    case 6 -> editarLivro();
                    case 7 -> removerLivro();
                    case 8 -> removerTodosLivros();
                    case 0 -> IO.println("Até logo!");
                    default -> IO.println("Opção inválida! Tente novamente.");
                }
            } catch (Exception e) {
                IO.println("Erro: " + e.getMessage());
            }
            IO.readln("Pressione Enter para continuar...");
        } while (opcao != 0);
    }

    private static void cadastrarLivro() throws Exception {
        String titulo = Input.scanString("Digite o título do livro: ");
        String autor = Input.scanString("Digite o autor do livro: ");
        int anoPublicacao = Input.scanInt("Digite o ano de publicação do livro: ");
        int numeroPaginas = Input.scanInt("Digite o número de páginas do livro: ");

        Livro novoLivro = new Livro(titulo, autor, anoPublicacao, numeroPaginas);
        service.cadastrarLivro(novoLivro);
        IO.println("Livro cadastrado com sucesso!");
    }

    private static void listarLivros() {
        List<Livro> livros = service.listar();
        imprimirLista(livros);
    }

    private static void pesquisarPorTitulo() {
        String pesquisa = Input.scanString("Digite parte do título do livro: ");
        List<Livro> livros = service.pesquisarPorTitulo(pesquisa);
        imprimirLista(livros);
    }

    private static void pesquisarPorAutor() {
        String autor = Input.scanString("Digite parte do nome do autor: ");
        List<Livro> livros = service.pesquisarPorAutor(autor);
        imprimirLista(livros);
    }

    private static void pesquisarPorAno() {
        int ano = Input.scanInt("Digite o ano de publicação: ");
        List<Livro> livros = service.pesquisarPorAnoPublicacao(ano);
        imprimirLista(livros);
    }

    private static void editarLivro() throws Exception {
        List<Livro> livros = service.listar();
        if (livros.isEmpty()) {
            IO.println("Nenhum livro cadastrado para editar.");
            return;
        }

        imprimirLista(livros);
        int numero = Input.scanInt("Digite o número do livro que deseja editar: ");
        int indice = numero - 1;

        String titulo = Input.scanString("Digite o novo título do livro: ");
        String autor = Input.scanString("Digite o novo autor do livro: ");
        int anoPublicacao = Input.scanInt("Digite o novo ano de publicação do livro: ");
        int numeroPaginas = Input.scanInt("Digite o novo número de páginas do livro: ");

        Livro livroAtualizado = new Livro(titulo, autor, anoPublicacao, numeroPaginas);
        service.editarLivro(indice, livroAtualizado);
        IO.println("Livro atualizado com sucesso!");
    }

    private static void removerLivro() throws Exception {
        List<Livro> livros = service.listar();
        if (livros.isEmpty()) {
            IO.println("Nenhum livro cadastrado para remover.");
            return;
        }

        imprimirLista(livros);
        int numero = Input.scanInt("Digite o número do livro que deseja remover: ");
        int indice = numero - 1;
        service.removerLivro(indice);
        IO.println("Livro removido com sucesso!");
    }

    private static void removerTodosLivros() {
        if (service.listar().isEmpty()) {
            IO.println("Não há livros para remover.");
            return;
        }
        service.removerTodosLivros();
        IO.println("Todos os livros foram removidos do acervo.");
    }

    private static void imprimirLista(List<Livro> livros) {
        if (livros.isEmpty()) {
            IO.println("Nenhum livro encontrado.");
            return;
        }

        int i = 1;
        for (Livro livro : livros) {
            IO.println(i++ + " - " + livro);
        }
    }
}

