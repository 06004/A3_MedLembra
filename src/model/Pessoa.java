package model;

/**
 * Classe abstrata que representa uma pessoa no sistema.
 * Serve como base para Idoso e Cuidador.
 */
public abstract class Pessoa {
    private String nome;
    private int idade;
    private String telefone;

    public Pessoa(String nome, int idade, String telefone) {
        this.nome = nome;
        this.idade = idade;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Método abstrato para exibição de perfil.
     * Cada subclasse implementa de forma específica.
     */
    public abstract String exibirPerfil();

    @Override
    public String toString() {
        return "Nome: " + nome + " | Idade: " + idade + " | Telefone: " + telefone;
    }
}
