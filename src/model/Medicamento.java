package model;

public class Medicamento {
    private String nomeMedicamento;
    private String horario;

    public Medicamento(String nomeMedicamento, String horario) {
        this.nomeMedicamento = nomeMedicamento;
        this.horario = horario;
    }

    public String getNome() {
        return nomeMedicamento;
    }

    public void setNome(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return nomeMedicamento + " (" + horario + ")";
    }
}
