package model;

import java.util.ArrayList;

public class Pessoa {
    private String nome;
    private int idade;
    private String telefone;
    private ArrayList<Medicamento> medicamentos;
    
    public Pessoa(String nome, int idade, String telefone) {
        this.nome = nome;
        this.idade = idade;
        this.telefone = telefone;
        this.medicamentos = new ArrayList<>();
    }

    // Adicionar medicamento
    public void adicionarMedicamento(Medicamento medicamento) {
        medicamentos.add(medicamento);
    }

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    // Método toString para exibir informações da pessoa
    @Override
    public String toString() {
    return "Nome: " + nome +
           " | Idade: " + idade +
           " | Telefone: " + telefone +
           " | Medicamentos: " + medicamentos;
}
}