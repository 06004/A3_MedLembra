package view;

import controller.PessoaController;
import exception.*;
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

            for (OpcaoMenu op : OpcaoMenu.values()) {
                System.out.println(op.getCodigo() + " - " + op.getDescricao());
            }

            System.out.print("Escolha: ");
            codigo = lerInteiro();
            sc.nextLine();

            OpcaoMenu opcao = OpcaoMenu.fromCodigo(codigo);

            if (opcao == null) {
                System.out.println("Opção inválida!");
                continue;
            }

            try {  
                switch (opcao) {
                    case GERENCIAR_IDOSOS -> menuIdosos();
                    case GERENCIAR_CUIDADORES -> menuCuidadores();
                    case MEDICAMENTOS -> menuMedicamentos();
                    case ASSOCIAR -> associarCuidadorIdoso();
                    case LISTAR_TODOS -> listarTodasPessoas();
                    case REMOVER -> removerPessoa();
                    case SALVAR_DADOS -> salvarDados();
                    case SAIR -> System.out.println("Saindo...");
                }
            } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
        } while (codigo != OpcaoMenu.SAIR.getCodigo());
    }

    private void menuIdosos() {
        System.out.println("\n--- Idosos ---");
        System.out.println("1 - Cadastrar idoso");
        System.out.println("2 - Listar idosos");
        int op = lerInteiro();
        sc.nextLine();

        if (op == 1) cadastrarIdoso();
        else if (op == 2) listarIdosos();
    }

    private void cadastrarIdoso() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            if (!Validador.isNotEmpty(nome)) throw new ValidacaoException("Nome inválido.");

            System.out.print("Idade: ");
            int idade = sc.nextInt(); sc.nextLine();

            if (!Validador.isIdadeValida(idade)) throw new ValidacaoException("Idade inválida.");

            System.out.print("Telefone (ou Enter para omitir): ");
            String tel = sc.nextLine();
            Idoso idoso;

            if (tel.isBlank()) {
                idoso = new Idoso(nome, idade);
            } else {
                idoso = new Idoso(nome, idade, tel);
            }

            controller.adicionarPessoa(idoso);
            System.out.println("Idoso cadastrado com sucesso!");
        } catch (ValidacaoException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        }
    }

    private void listarIdosos() {
        System.out.println("\n--- LISTA DE IDOSOS ---");
        controller.listarIdosos().forEach(i -> System.out.println(i.exibirPerfil()));
    }

    private void menuCuidadores() {
        System.out.println("\n--- Cuidadores ---");
        System.out.println("1 - Cadastrar cuidador");
        System.out.println("2 - Listar cuidadores");
        int op = lerInteiro(); sc.nextLine();

        if (op == 1) cadastrarCuidador();
        else if (op == 2) listarCuidadores();
    }

    private void cadastrarCuidador() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            if (!Validador.isNotEmpty(nome)) throw new ValidacaoException("Nome inválido.");

            System.out.print("Idade: ");
            int idade = sc.nextInt(); sc.nextLine();

            if (!Validador.isIdadeValida(idade)) throw new ValidacaoException("Idade inválida.");

            System.out.print("Telefone: ");
            String tel = sc.nextLine();

            if (tel.isBlank()) tel = "Não informado";

            controller.adicionarPessoa(new Cuidador(nome, idade, tel));
            System.out.println("Cuidador cadastrado com sucesso!");
        } catch (ValidacaoException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        }
    }

    private void listarCuidadores() {
        System.out.println("\n--- LISTA DE CUIDADORES ---");
        controller.listarCuidadores().forEach(c -> System.out.println(c.exibirPerfil()));
    }

    private void menuMedicamentos() {
        System.out.println("\n--- Medicamentos ---");
        System.out.println("1 - Adicionar medicamento a um idoso");
        System.out.println("2 - Listar medicamentos de um idoso");
        int op = lerInteiro(); sc.nextLine();

        if (op == 1) adicionarMedicamento();
        else if (op == 2) listarMedicamentosPorIdoso();
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

            if (!Validador.isHorarioValido(horario)) throw new ValidacaoException("Horário inválido.");

            controller.adicionarMedicamentoAoIdoso(nomeIdoso, new Medicamento(nomeMed, horario));
            System.out.println("Medicamento adicionado!");
        } catch (ValidacaoException | PessoaNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarMedicamentosPorIdoso() {
        try {
            System.out.print("Nome do idoso: ");
            String nome = sc.nextLine();
            Pessoa p = controller.buscarPorNome(nome);

            if (p instanceof Idoso idoso) {
                System.out.println("Medicamentos de " + idoso.getNome() + ":");
                if (idoso.getMedicamentos().isEmpty()) {
                    System.out.println("Nenhum medicamento.");
                } else {
                    idoso.getMedicamentos().forEach(m -> System.out.println("- " + m));
                }
            } else {
                System.out.println("Pessoa encontrada não é um idoso.");
            }
        } catch (PessoaNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void associarCuidadorIdoso() {
        try {
            System.out.print("Nome do cuidador: ");
            String nomeC = sc.nextLine();
            System.out.print("Nome do idoso: ");
            String nomeI = sc.nextLine();
            controller.associarCuidadorIdoso(nomeC, nomeI);
            System.out.println("Associação realizada com sucesso!");
        } catch (PessoaNaoEncontradaException | ValidacaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTodasPessoas() {
        System.out.println("\n--- TODAS AS PESSOAS ---");
        controller.listarPessoas().forEach(p -> System.out.println(p.exibirPerfil()));
    }

    private void removerPessoa() {
        try {
            System.out.print("Nome da pessoa a remover: ");
            String nome = sc.nextLine();
            controller.removerPessoa(nome);
            System.out.println("Pessoa removida com sucesso.");
        } catch (PessoaNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void salvarDados() {
        try {
            System.out.print("Caminho do arquivo para salvar (ex: dados.csv): ");
            String caminho = sc.nextLine();
            controller.salvarDados(caminho);
            System.out.println("Dados salvos em " + caminho);
        } catch (Exception e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    private int lerInteiro() {
        while (!sc.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            sc.next();
        }
        return sc.nextInt();
    }
}