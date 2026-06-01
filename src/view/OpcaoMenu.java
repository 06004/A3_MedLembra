package view;

/**
 * Enum que representa as opções do menu principal.
 * Elimina números mágicos e melhora legibilidade.
 */
public enum OpcaoMenu {
    GERENCIAR_IDOSOS(1, "Gerenciar Idosos"),
    GERENCIAR_CUIDADORES(2, "Gerenciar Cuidadores"),
    MEDICAMENTOS(3, "Medicamentos"),
    ASSOCIAR(4, "Associar Cuidador a Idoso"),
    LISTAR_TODOS(5, "Listar todas as pessoas"),
    REMOVER(6, "Remover pessoa"),
    HISTORICO_ALARMES(7, "Ver histórico de alarmes"),
    SALVAR_DADOS(8, "Salvar dados em arquivo"),
    CARREGAR_DADOS(9, "Carregar dados do arquivo"),
    SAIR(10, "Sair");

    private final int codigo;
    private final String descricao;

    OpcaoMenu(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static OpcaoMenu fromCodigo(int codigo) {
        for (OpcaoMenu op : values()) {
            if (op.codigo == codigo) return op;
        }
        return null;
    }
}
