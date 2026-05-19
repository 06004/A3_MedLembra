package model;

import java.util.ArrayList;

/**
 * Representa um cuidador no sistema.
 * Herda de Pessoa e possui uma lista de idosos sob cuidado.
 */
public class Cuidador extends Pessoa {
    private ArrayList<Idoso> idososSobCuidado;

    public Cuidador(String nome, int idade, String telefone) {
        super(nome, idade, telefone);
        this.idososSobCuidado = new ArrayList<>();
    }

    public void adicionarIdoso(Idoso idoso) {
        idososSobCuidado.add(idoso);
    }

    public ArrayList<Idoso> getIdosos() {
        return idososSobCuidado;
    }

    @Override
    public String exibirPerfil() {
        return "Cuidador: " + getNome() + " | Idosos sob cuidado: " + idososSobCuidado.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" | Idosos: ");

        if (idososSobCuidado.isEmpty()) {
            sb.append("nenhum");
        } else {
            idososSobCuidado.forEach(i -> sb.append(i.getNome()).append(" "));
        }
        return sb.toString();
    }
}
