package view;

/**
* Enum que define as opções do menu principal.
* Evita números mágicos e centraliza as descrições.
*/
public enum OpcaoMenu {
    GERENCIAR_IDOSOS(1, "Gerenciar Idosos"),
    GERENCIAR_CUIDADORES(2, "Gerenciar Cuidadores"),
    MEDICAMENTOS(3, "Medicamentos"),
    ASSOCIAR(4, "Associar Cuidador a Idoso"),
    LISTAR_TODOS(5, "Listar todas as pessoas"),
    REMOVER(6, "Remover pessoa"),
    SALVAR_DADOS(7, "Salvar dados em arquivo"),
    SAIR(8, "Sair");

    private final int codigo;
    private final String descricao;

    OpcaoMenu(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static OpcaoMenu fromCodigo(int codigo) {
        for (OpcaoMenu op : values()) {
            if (op.codigo == codigo) return op;
        }
        return null;
    }
}