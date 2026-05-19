package model;

import java.util.ArrayList;

/**
 * Representa um idoso no sistema.
 * Herda de Pessoa e possui uma lista de medicamentos.
 */
public class Idoso extends Pessoa {
    private ArrayList<Medicamento> medicamentos;

    public Idoso(String nome, int idade, String telefone) {
        super(nome, idade, telefone);
        this.medicamentos = new ArrayList<>();
    }

    /**
     * Sobrecarga de construtor para telefone não informado.
     */
    public Idoso(String nome, int idade) {
        this(nome, idade, "Não informado");
    }

    public void adicionarMedicamento(Medicamento m) {
        medicamentos.add(m);
    }

    /**
     * Sobrecarga: cria medicamento a partir de String.
     */
    public void adicionarMedicamento(String nomeMed, String horario) {
        medicamentos.add(new Medicamento(nomeMed, horario));
    }

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void removerMedicamento(String nomeMed) {
        medicamentos.removeIf(m -> m.getNome().equalsIgnoreCase(nomeMed));
    }

    @Override
    public String exibirPerfil() {
        return "Idoso: " + getNome() + " | " + getIdade() + " anos | Tel: " + getTelefone()
                + " | Medicamentos: " + medicamentos.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" | Medicamentos: ");

        if (medicamentos.isEmpty()) {
            sb.append("nenhum");
        } else {
            medicamentos.forEach(m -> sb.append(m.getNome()).append(" "));
        }
        return sb.toString();
    }
}
