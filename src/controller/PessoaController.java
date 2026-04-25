package controller;

import java.util.ArrayList;
import model.Medicamento;
import model.Pessoa;

public class PessoaController {
    private final ArrayList<Pessoa> pessoas;

    
    public PessoaController() {
        this.pessoas = new ArrayList<>();
    }
    
    public void adicionarPessoa(Pessoa pessoa) {
        pessoas.add(pessoa);
    }

    public boolean adicionarMedicamento(String nomePessoa, Medicamento medicamento) {
        Pessoa pessoa = buscarPorNome(nomePessoa);

        if (pessoa != null) {
            pessoa.adicionarMedicamento(medicamento);
            return true;
        }
        return false;
    }

    public ArrayList<Pessoa> listarPessoas() {
        return pessoas;
    }
    
    public Pessoa buscarPorNome(String nome) {
        for (Pessoa p : pessoas) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }
    
    public boolean removerPessoa(String nome) {
        Pessoa pessoa = buscarPorNome(nome);
        if (pessoa != null) {
            pessoas.remove(pessoa);
            return true;
        }
        return false;
    }
}