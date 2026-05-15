package controller;

import exception.*;
import java.util.*;
import java.util.stream.Collectors;
import model.*;
import service.IPessoaService;

/**
* Controlador principal que implementa IPessoaService.
* Utiliza SINGLETON para garantir uma única instância.
* Usa OPTIONAL para evitar null.
*/
public class PessoaController implements IPessoaService {
    private static PessoaController instancia;
    private final List<Pessoa> pessoas;

    // Construtor privado (Singleton)
    private PessoaController() {
        this.pessoas = new ArrayList<>();
    }

    // Ponto de acesso global
    public static PessoaController getInstancia() {
        if (instancia == null) {
            instancia = new PessoaController();
        }
        return instancia;
    }

    @Override
    public void adicionarPessoa(Pessoa p) throws ValidacaoException {
        if (p.getNome() == null || p.getNome().isBlank()) {
            throw new ValidacaoException("Nome da pessoa não pode ser vazio.");
        }
        pessoas.add(p);
    }

    @Override
    public List<Pessoa> listarPessoas() {
        return Collections.unmodifiableList(pessoas);
    }

    @Override
    public Pessoa buscarPorNome(String nome) throws PessoaNaoEncontradaException {
        Optional<Pessoa> resultado = pessoas.stream()
        .filter(p -> p.getNome().equalsIgnoreCase(nome))
        .findFirst();
        return resultado.orElseThrow(() -> new PessoaNaoEncontradaException(nome));
    }

    @Override
    public void removerPessoa(String nome) throws PessoaNaoEncontradaException {
        Pessoa p = buscarPorNome(nome);
        pessoas.remove(p);
    }

    @Override
    public List<Idoso> listarIdosos() {
        return pessoas.stream()
        .filter(p -> p instanceof Idoso)
        .map(p -> (Idoso) p)
        .collect(Collectors.toList());
    }

    @Override
    public List<Cuidador> listarCuidadores() {
        return pessoas.stream()
        .filter(p -> p instanceof Cuidador)
        .map(p -> (Cuidador) p)
        .collect(Collectors.toList());
    }

    @Override
    public void adicionarMedicamentoAoIdoso(String nomeIdoso, Medicamento m)
    throws PessoaNaoEncontradaException, ValidacaoException {
        if (m.getNome() == null || m.getNome().isBlank()) {
            throw new ValidacaoException("Nome do medicamento inválido.");
        }

        Pessoa p = buscarPorNome(nomeIdoso);

        if (p instanceof Idoso) {
            ((Idoso) p).adicionarMedicamento(m);
        } else {
            throw new PessoaNaoEncontradaException(nomeIdoso + " não é um idoso.");
        }
    }

    @Override
    public void associarCuidadorIdoso(String nomeCuidador, String nomeIdoso)
    throws PessoaNaoEncontradaException, ValidacaoException {
        Pessoa c = buscarPorNome(nomeCuidador);
        Pessoa i = buscarPorNome(nomeIdoso);
        if (!(c instanceof Cuidador)) {
            throw new ValidacaoException(nomeCuidador + " não é um cuidador.");
        }

        if (!(i instanceof Idoso)) {
            throw new ValidacaoException(nomeIdoso + " não é um idoso.");
        }

        ((Cuidador) c).adicionarIdoso((Idoso) i);
    }

    // PERSISTÊNCIA SIMPLES EM CSV
    @Override
    public void salvarDados(String caminho) throws Exception {
        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(caminho))) {
            for (Pessoa p : pessoas) {
                pw.print(p.getClass().getSimpleName() + ";" + p.getNome() + ";" +
                p.getIdade() + ";" + p.getTelefone());
                
                if (p instanceof Idoso) {
                    for (Medicamento m : ((Idoso) p).getMedicamentos()) {
                        pw.print(";" + m.getNome() + "," + m.getHorario());
                    }
                } else if (p instanceof Cuidador) {
                    for (Idoso i : ((Cuidador) p).getIdosos()) {
                        pw.print(";" + i.getNome());
                }
                }
                pw.println();
            }
        }
    }

    @Override
    public void carregarDados(String caminho) throws Exception {
        // Implementação futura para leitura do CSV
        throw new UnsupportedOperationException("Carregamento ainda não implementado.");
    }
}