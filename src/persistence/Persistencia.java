package persistence;

import controller.PessoaController;

public class Persistencia {
    public static void salvar(PessoaController controller, String caminho) throws Exception {
        controller.salvarDados(caminho);
    }

    public static void carregar(PessoaController controller, String caminho) throws Exception {
        controller.carregarDados(caminho);
    }
}