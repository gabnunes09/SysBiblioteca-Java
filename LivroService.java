import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivroService {
    private final List<Livro> acervo = new ArrayList<>();

    public void cadastrarLivro(Livro novoLivro) throws Exception {
        validarLivro(novoLivro);
        formatarLivro(novoLivro);
        verificarDuplicata(novoLivro, -1);
        acervo.add(novoLivro);
    }

    public List<Livro> listar() {
        return new ArrayList<>(acervo);
    }

    public List<Livro> pesquisarPorTitulo(String titulo) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        if (titulo == null || titulo.isBlank()) {
            return livrosEncontrados;
        }

        String termo = titulo.trim().toUpperCase();
        for (Livro livro : acervo) {
            if (livro.getTitulo().contains(termo)) {
                livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    public List<Livro> pesquisarPorAutor(String autor) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        if (autor == null || autor.isBlank()) {
            return livrosEncontrados;
        }

        String termo = autor.trim().toUpperCase();
        for (Livro livro : acervo) {
            if (livro.getAutor().contains(termo)) {
                livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    public List<Livro> pesquisarPorAnoPublicacao(int ano) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getAnoPublicacao() == ano) {
                livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    public void editarLivro(int index, Livro livroAtualizado) throws Exception {
        if (index < 0 || index >= acervo.size()) {
            throw new Exception("Índice de livro inválido.");
        }

        validarLivro(livroAtualizado);
        formatarLivro(livroAtualizado);
        verificarDuplicata(livroAtualizado, index);

        Livro livroExistente = acervo.get(index);
        livroExistente.setTitulo(livroAtualizado.getTitulo());
        livroExistente.setAutor(livroAtualizado.getAutor());
        livroExistente.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
        livroExistente.setNumeroPaginas(livroAtualizado.getNumeroPaginas());
    }

    public void removerLivro(int index) throws Exception {
        if (index < 0 || index >= acervo.size()) {
            throw new Exception("Índice de livro inválido.");
        }
        acervo.remove(index);
    }

    public void removerTodosLivros() {
        acervo.clear();
    }

    private void validarLivro(Livro livro) throws Exception {
        if (livro == null) {
            throw new Exception("Objeto Nulo!");
        }

        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            throw new Exception("Título inválido!");
        }

        if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
            throw new Exception("Autor inválido!");
        }

        int ano = livro.getAnoPublicacao();
        int anoAtual = LocalDate.now().getYear();
        if (ano < 1900 || ano > anoAtual) {
            throw new Exception("Ano de publicação inválido!");
        }

        if (livro.getNumeroPaginas() <= 0) {
            throw new Exception("Número de páginas inválido!");
        }
    }

    private void formatarLivro(Livro livro) {
        livro.setTitulo(livro.getTitulo().trim().toUpperCase());
        livro.setAutor(livro.getAutor().trim().toUpperCase());
    }

    private void verificarDuplicata(Livro livro, int excluirIndex) throws Exception {
        for (int i = 0; i < acervo.size(); i++) {
            if (i == excluirIndex) {
                continue;
            }
            Livro existente = acervo.get(i);
            if (existente.getTitulo().equals(livro.getTitulo())
                    && existente.getAutor().equals(livro.getAutor())
                    && existente.getAnoPublicacao() == livro.getAnoPublicacao()) {
                throw new Exception("Já existe livro cadastrado com este título, autor e ano de publicação!");
            }
        }
    }
}