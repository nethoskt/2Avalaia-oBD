package br.senac.pi.livrobd2prova.EstruturaBD;

/**
 * Created by Aluno on 20/11/2015.
 */
public class Livro {

    private long id;
    private String livro;
    private String editora;

    public Livro(){}


    public Livro(long id, String livro,String editora) {
        this.id = id;
        this.livro = livro;
        this.editora = editora;
    }

    public String getLivro() {
        return livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

