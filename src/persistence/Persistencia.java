package persistence;

import controller.PessoaController;

/**
 * Classe de persistência que delega operações ao controller.
 * Pode ser expandida para incluir lógica adicional de serialização.
 */
public class Persistencia {
    public static void salvar(PessoaController controller, String caminho) throws Exception {
        controller.salvarDados(caminho);
    }

    public static void carregar(PessoaController controller, String caminho) throws Exception {
        controller.carregarDados(caminho);
    }
}
