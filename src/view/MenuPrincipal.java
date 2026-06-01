package view;

import controller.PessoaController;
import exception.*;
import java.util.List;
import java.util.Scanner;
import model.*;
import util.Validador;

/**
 * Classe responsável pela interação com o usuário via console.
 * Utiliza enum OpcaoMenu, Validador e tratamento de exceções.
 */
public class MenuPrincipal {
    private final PessoaController controller;
    private final Scanner sc;

    public MenuPrincipal() {
        this.controller = PessoaController.getInstancia();
        this.sc = new Scanner(System.in);
    }

    public void exibirMenu() {
        int codigo;

        do {
            System.out.println("\n=== MEDLEMBRA ===");
            System.out.println("Sistema de Lembretes de Medicamentos");

            // MELHORIA: Contador de pessoas no menu principal
            System.out.println("👤 Idosos: " + controller.totalIdosos()
                    + " | 🧑‍⚕️ Cuidadores: " + controller.totalCuidadores() + "\n");

            for (OpcaoMenu op : OpcaoMenu.values()) {
                System.out.println(op.getCodigo() + " - " + op.getDescricao());
            }

            System.out.print("\nEscolha uma opção: ");
            codigo = lerInteiro();
            sc.nextLine();

            OpcaoMenu opcao = OpcaoMenu.fromCodigo(codigo);

            if (opcao == null) {
                System.out.println("❌ Opção inválida!");
                continue;
            }

            try {
                switch (opcao) {
                    case GERENCIAR_IDOSOS      -> menuIdosos();
                    case GERENCIAR_CUIDADORES  -> menuCuidadores();
                    case MEDICAMENTOS          -> menuMedicamentos();
                    case ASSOCIAR              -> associarCuidadorIdoso();
                    case LISTAR_TODOS          -> listarTodasPessoas();
                    case REMOVER               -> removerPessoa();
                    case HISTORICO_ALARMES     -> verHistoricoAlarmes();
                    case SALVAR_DADOS          -> salvarDados();
                    case CARREGAR_DADOS        -> carregarDados();
                    case SAIR                  -> System.out.println("👋 Saindo do MedLembra...");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro: " + e.getMessage());
            }
        } while (codigo != OpcaoMenu.SAIR.getCodigo());
    }

    // ==================== IDOSOS ====================
    private void menuIdosos() {
        System.out.println("\n--- Gerenciamento de Idosos ---");
        System.out.println("1 - Cadastrar idoso");
        System.out.println("2 - Listar idosos");
        System.out.print("Escolha: ");
        int op = lerInteiro();
        sc.nextLine();

        switch (op) {
            case 1 -> cadastrarIdoso();
            case 2 -> listarIdosos();
            default -> System.out.println("Opção inválida.");
        }
    }

    private void cadastrarIdoso() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            if (!Validador.isNotEmpty(nome)) throw new ValidacaoException("Nome inválido.");

            System.out.print("Idade: ");
            int idade = sc.nextInt(); sc.nextLine();

            if (!Validador.isIdadeValida(idade)) throw new ValidacaoException("Idade deve ser entre 1 e 130 anos.");

            System.out.print("Telefone (ou Enter para omitir): ");
            String tel = sc.nextLine();

            Idoso idoso;
            if (tel.isBlank()) {
                idoso = new Idoso(nome, idade);
            } else {
                if (!Validador.isTelefoneValido(tel)) {
                    System.out.println("⚠️ Telefone parece inválido, mas será cadastrado mesmo assim.");
                }
                idoso = new Idoso(nome, idade, tel);
            }

            controller.adicionarPessoa(idoso);
            System.out.println("✅ Idoso cadastrado com sucesso!");
        } catch (ValidacaoException e) {
            System.out.println("❌ Erro de validação: " + e.getMessage());
        }
    }

    private void listarIdosos() {
        System.out.println("\n--- LISTA DE IDOSOS ---");
        var idosos = controller.listarIdosos();
        if (idosos.isEmpty()) {
            System.out.println("Nenhum idoso cadastrado.");
        } else {
            idosos.forEach(i -> System.out.println("• " + i.exibirPerfil()));
        }
    }

    // ==================== CUIDADORES ====================
    private void menuCuidadores() {
        System.out.println("\n--- Gerenciamento de Cuidadores ---");
        System.out.println("1 - Cadastrar cuidador");
        System.out.println("2 - Listar cuidadores");
        System.out.print("Escolha: ");
        int op = lerInteiro(); sc.nextLine();

        switch (op) {
            case 1 -> cadastrarCuidador();
            case 2 -> listarCuidadores();
            default -> System.out.println("Opção inválida.");
        }
    }

    private void cadastrarCuidador() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            if (!Validador.isNotEmpty(nome)) throw new ValidacaoException("Nome inválido.");

            System.out.print("Idade: ");
            int idade = sc.nextInt(); sc.nextLine();

            if (!Validador.isIdadeValida(idade)) throw new ValidacaoException("Idade deve ser entre 1 e 130 anos.");

            System.out.print("Telefone: ");
            String tel = sc.nextLine();

            if (tel.isBlank()) tel = "Não informado";

            controller.adicionarPessoa(new Cuidador(nome, idade, tel));
            System.out.println("✅ Cuidador cadastrado com sucesso!");
        } catch (ValidacaoException e) {
            System.out.println("❌ Erro de validação: " + e.getMessage());
        }
    }

    private void listarCuidadores() {
        System.out.println("\n--- LISTA DE CUIDADORES ---");
        var cuidadores = controller.listarCuidadores();
        if (cuidadores.isEmpty()) {
            System.out.println("Nenhum cuidador cadastrado.");
        } else {
            cuidadores.forEach(c -> System.out.println("• " + c.exibirPerfil()));
        }
    }

    // ==================== MEDICAMENTOS ====================
    private void menuMedicamentos() {
        System.out.println("\n--- Gerenciamento de Medicamentos ---");
        System.out.println("1 - Adicionar medicamento a um idoso");
        System.out.println("2 - Listar medicamentos de um idoso");
        System.out.println("3 - Remover medicamento de um idoso");  // MELHORIA
        System.out.print("Escolha: ");
        int op = lerInteiro(); sc.nextLine();

        switch (op) {
            case 1 -> adicionarMedicamento();
            case 2 -> listarMedicamentosPorIdoso();
            case 3 -> removerMedicamento();  // MELHORIA
            default -> System.out.println("Opção inválida.");
        }
    }

    private void adicionarMedicamento() {
        try {
            System.out.print("Nome do idoso: ");
            String nomeIdoso = sc.nextLine();
            System.out.print("Nome do medicamento: ");
            String nomeMed = sc.nextLine();

            if (!Validador.isNotEmpty(nomeMed)) throw new ValidacaoException("Nome do medicamento inválido.");

            System.out.print("Horário (HH:MM): ");
            String horario = sc.nextLine();

            if (!Validador.isHorarioValido(horario)) throw new ValidacaoException("Horário inválido. Use formato HH:MM (ex: 08:30).");

            controller.adicionarMedicamentoAoIdoso(nomeIdoso, new Medicamento(nomeMed, horario));
            System.out.println("✅ Medicamento adicionado com sucesso!");
        } catch (ValidacaoException | PessoaNaoEncontradaException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void listarMedicamentosPorIdoso() {
        try {
            System.out.print("Nome do idoso: ");
            String nome = sc.nextLine();
            Pessoa p = controller.buscarPorNome(nome);

            if (p instanceof Idoso idoso) {
                System.out.println("\n💊 Medicamentos de " + idoso.getNome() + ":");
                if (idoso.getMedicamentos().isEmpty()) {
                    System.out.println("   Nenhum medicamento cadastrado.");
                } else {
                    idoso.getMedicamentos().forEach(m -> System.out.println("   • " + m));
                }
            } else {
                System.out.println("❌ A pessoa encontrada não é um idoso.");
            }
        } catch (PessoaNaoEncontradaException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    // MELHORIA: Remover medicamento
    private void removerMedicamento() {
        try {
            System.out.print("Nome do idoso: ");
            String nomeIdoso = sc.nextLine();
            System.out.print("Nome do medicamento a remover: ");
            String nomeMed = sc.nextLine();
            controller.removerMedicamento(nomeIdoso, nomeMed);
            System.out.println("✅ Medicamento removido com sucesso!");
        } catch (PessoaNaoEncontradaException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    // ==================== ASSOCIAR ====================
    private void associarCuidadorIdoso() {
        try {
            System.out.print("Nome do cuidador: ");
            String nomeC = sc.nextLine();
            System.out.print("Nome do idoso: ");
            String nomeI = sc.nextLine();
            controller.associarCuidadorIdoso(nomeC, nomeI);
            System.out.println("✅ Cuidador associado ao idoso com sucesso!");
        } catch (PessoaNaoEncontradaException | ValidacaoException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    // ==================== LISTAR TODOS ====================
    private void listarTodasPessoas() {
        System.out.println("\n--- TODAS AS PESSOAS CADASTRADAS ---");
        var pessoas = controller.listarPessoas();
        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
        } else {
            pessoas.forEach(p -> System.out.println("• " + p.exibirPerfil()));
        }
    }

    // ==================== REMOVER ====================
    private void removerPessoa() {
        try {
            System.out.print("Nome da pessoa a remover: ");
            String nome = sc.nextLine();

            // MELHORIA: Confirmação antes de remover
            Pessoa p = controller.buscarPorNome(nome);
            System.out.println("⚠️  Deseja remover " + p.getNome() + "? (s/n): ");
            String confirmacao = sc.nextLine().trim();

            if (!confirmacao.equalsIgnoreCase("s")) {
                System.out.println("ℹ️  Remoção cancelada.");
                return;
            }

            controller.removerPessoa(nome);
            System.out.println("✅ Pessoa removida com sucesso.");
        } catch (PessoaNaoEncontradaException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    // MELHORIA: Histórico de alarmes disparados
    private void verHistoricoAlarmes() {
        System.out.println("\n--- HISTÓRICO DE ALARMES DISPARADOS ---");
        List<String> historico = controller.getHistoricoAlarmes();
        if (historico.isEmpty()) {
            System.out.println("Nenhum alarme disparado ainda.");
        } else {
            historico.forEach(h -> System.out.println("• " + h));
        }
    }

    // ==================== PERSISTÊNCIA ====================
    private void salvarDados() {
        try {
            System.out.print("Caminho do arquivo para salvar (ex: dados.csv): ");
            String caminho = sc.nextLine();
            controller.salvarDados(caminho);
            System.out.println("✅ Dados salvos em: " + caminho);
        } catch (Exception e) {
            System.out.println("❌ Erro ao salvar: " + e.getMessage());
        }
    }

    private void carregarDados() {
        try {
            System.out.print("Caminho do arquivo para carregar (ex: dados.csv): ");
            String caminho = sc.nextLine();
            controller.carregarDados(caminho);
            System.out.println("✅ Dados carregados de: " + caminho);
        } catch (Exception e) {
            System.out.println("❌ Erro ao carregar: " + e.getMessage());
        }
    }

    // ==================== UTILITÁRIOS ====================
    private int lerInteiro() {
        while (!sc.hasNextInt()) {
            System.out.print("⚠️ Digite um número válido: ");
            sc.next();
        }
        return sc.nextInt();
    }
}
