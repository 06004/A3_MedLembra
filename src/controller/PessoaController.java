package controller;

import exception.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import model.*;
import service.AlarmeService;
import service.IPessoaService;

/**
 * Controlador principal que implementa IPessoaService.
 * Utiliza SINGLETON para garantir uma única instância.
 * Usa Optional para evitar null.
 */
public class PessoaController implements IPessoaService {
    private static PessoaController instancia;
    private final List<Pessoa> pessoas;
    private final AlarmeService alarmeService = new AlarmeService();

    // MELHORIA: Histórico de alarmes disparados
    private final List<String> historicoAlarmes = new ArrayList<>();

    // Construtor privado (Singleton)
    private PessoaController() {
        this.pessoas = new ArrayList<>();
    }

    // Ponto de acesso global - thread-safe
    public static synchronized PessoaController getInstancia() {
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

        // MELHORIA: Impede cadastro duplicado por nome
        boolean jaExiste = pessoas.stream()
                .anyMatch(x -> x.getNome().equalsIgnoreCase(p.getNome())
                        && x.getClass().equals(p.getClass()));
        if (jaExiste) {
            throw new ValidacaoException("Já existe um(a) "
                    + p.getClass().getSimpleName() + " com o nome '" + p.getNome() + "'.");
        }

        pessoas.add(p);
    }

    @Override
    public List<Pessoa> listarPessoas() {
        return Collections.unmodifiableList(pessoas);
    }

    @Override
    public Pessoa buscarPorNome(String nome) throws PessoaNaoEncontradaException {
        // MELHORIA: Busca por nome parcial (contém)
        Optional<Pessoa> resultado = pessoas.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome)
                        || p.getNome().toLowerCase().contains(nome.toLowerCase()))
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

    // MELHORIA: Contador de pessoas
    public int totalIdosos() {
        return (int) pessoas.stream().filter(p -> p instanceof Idoso).count();
    }

    public int totalCuidadores() {
        return (int) pessoas.stream().filter(p -> p instanceof Cuidador).count();
    }

    @Override
    public void adicionarMedicamentoAoIdoso(String nomeIdoso, Medicamento m)
            throws PessoaNaoEncontradaException, ValidacaoException {
        if (m.getNome() == null || m.getNome().isBlank()) {
            throw new ValidacaoException("Nome do medicamento inválido.");
        }

        Pessoa p = buscarPorNome(nomeIdoso);

        if (p instanceof Idoso idoso) {
            idoso.adicionarMedicamento(m);
            String idAlarme = idoso.getNome() + "-" + m.getNome();

            // MELHORIA: Agenda alarme e registra no histórico quando disparar
            alarmeService.agendarMedicamento(idAlarme, m, idoso.getNome(), historicoAlarmes);
        } else {
            throw new PessoaNaoEncontradaException(nomeIdoso + " não é um idoso.");
        }
    }

    public void removerMedicamento(String nomeIdoso, String nomeMedicamento)
            throws PessoaNaoEncontradaException {
        Pessoa p = buscarPorNome(nomeIdoso);

        if (p instanceof Idoso idoso) {
            idoso.getMedicamentos().removeIf(m -> m.getNome().equalsIgnoreCase(nomeMedicamento));
            String idAlarme = idoso.getNome() + "-" + nomeMedicamento;
            alarmeService.cancelarAlarme(idAlarme);
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

    // MELHORIA: Retorna histórico de alarmes
    public List<String> getHistoricoAlarmes() {
        return Collections.unmodifiableList(historicoAlarmes);
    }

    @Override
    public void salvarDados(String caminho) throws Exception {
        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {
            for (Pessoa p : pessoas) {
                pw.print(p.getClass().getSimpleName() + ";" + p.getNome() + ";"
                        + p.getIdade() + ";" + p.getTelefone());

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
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            throw new FileNotFoundException("Arquivo não encontrado: " + caminho);
        }

        pessoas.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length < 4) continue;

                String tipo = partes[0];
                String nome = partes[1];
                int idade = Integer.parseInt(partes[2]);
                String telefone = partes[3];

                if (tipo.equals("Idoso")) {
                    Idoso idoso = new Idoso(nome, idade, telefone);
                    for (int i = 4; i < partes.length; i++) {
                        String[] medPartes = partes[i].split(",");
                        if (medPartes.length == 2) {
                            Medicamento m = new Medicamento(medPartes[0], medPartes[1]);
                            idoso.adicionarMedicamento(m);
                            String idAlarme = nome + "-" + m.getNome();
                            alarmeService.agendarMedicamento(idAlarme, m, nome, historicoAlarmes);
                        }
                    }
                    pessoas.add(idoso);
                } else if (tipo.equals("Cuidador")) {
                    Cuidador cuidador = new Cuidador(nome, idade, telefone);
                    pessoas.add(cuidador);
                }
            }
        }
    }
}
