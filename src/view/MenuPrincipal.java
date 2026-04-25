package view;

import controller.PessoaController;
import java.util.Scanner;
import model.Medicamento;
import model.Pessoa;

public class MenuPrincipal {
    private final PessoaController pessoaController;
    private final Scanner sc;
    
    public MenuPrincipal() {
        this.pessoaController = new PessoaController();
        this.sc = new Scanner(System.in);
    }
    
    // Método para exibir o menu principal
    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== MEDLEMBRA ===");
            System.out.println("1 - Cadastrar pessoa");
            System.out.println("2 - Listar pessoas");
            System.out.println("3 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();
            
            switch (opcao) {
                case 1 -> cadastrarPessoa();
                case 2 -> listarPessoas();
                case 3 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 3);
    }
    
    // Métodos para cada opção do MENU
        //MENUPRINCIAL 1: submenu para cadastrar pessoa e medicamentos
            private void cadastrarPessoa() {
                int opcao;
                do {
                    System.out.println("\n=== Paciente ===");
                    System.out.println("1 - Dados do Paciente");
                    System.out.println("2 - Medicamentos");
                    System.out.println("3 - Listar Medicamentos");
                    System.out.println("4 - Sair");
                    System.out.print("Escolha: ");
                    opcao = sc.nextInt();
                    sc.nextLine();

                    switch (opcao) {
                        case 1 -> dadosDoPaciente();
                        case 2 -> cadastrarMedicamento();
                        case 3 -> listarMedicamentos();
                        case 4 -> System.out.println("Saindo...");
                        default -> System.out.println("Opção inválida!");
                    }
                } while (opcao != 4);
            }


            // Métodos para cada opção do SUBMENU
                //SUBMENU 1: Cadastrar dados do paciente
                    private void dadosDoPaciente() {
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Idade: ");
                        int idade = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Telefone: ");
                        String telefone = sc.nextLine();
        
                        Pessoa pessoa = new Pessoa(nome, idade, telefone);
                        pessoaController.adicionarPessoa(pessoa);
                        System.out.println("Pessoa cadastrada com sucesso!");
                    }

                //SUBMENU 2: Cadastrar medicamento
                    private void cadastrarMedicamento() {
                        System.out.print("Nome da pessoa: ");
                        String nomePessoa = sc.nextLine();

                        System.out.print("Nome do medicamento: ");
                        String nome = sc.nextLine();

                        System.out.print("Horário: ");
                        String horario = sc.nextLine();

                        Medicamento medicamento = new Medicamento(nome, horario);

                        boolean sucesso = pessoaController.adicionarMedicamento(nomePessoa, medicamento);

                        if (sucesso) {
                            System.out.println("Medicamento adicionado com sucesso!");
                        } else {
                            System.out.println("Pessoa não encontrada!");
                        }           
                    }

                //SUBMENU 3: Listar medicamentos
                    private void listarMedicamentos() {
                        System.out.println("\n=== LISTA DE MEDICAMENTOS ===");

                        for (Pessoa p : pessoaController.listarPessoas()) {
                            System.out.println("\nPaciente: " + p.getNome());

                            if (p.getMedicamentos().isEmpty()) {
                                System.out.println("Nenhum medicamento cadastrado.");
                            } else {
                                for (Medicamento m : p.getMedicamentos()) {
                                    System.out.println("- " + m.getNome() + " | " + m.getHorario());
                                }
                            }   
                        }
                    }


        // MENUPRINCIPAL 2: Método para listar pessoas
            private void listarPessoas() {
                System.out.println("\n=== LISTA DE PESSOAS ===");
                for (Pessoa p : pessoaController.listarPessoas()) {
                    System.out.println("- " + p.getNome() + " | " + p.getIdade() + " anos | " + p.getTelefone());
                }
            }
}