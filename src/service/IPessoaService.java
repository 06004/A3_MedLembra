package service;

import exception.*;
import java.util.List;
import model.*;

/**
 * Interface que define o contrato de serviços para gerenciamento de pessoas.
 * Permite inversão de dependência e desacoplamento do controller.
 */
public interface IPessoaService {
    void adicionarPessoa(Pessoa p) throws ValidacaoException;
    List<Pessoa> listarPessoas();
    Pessoa buscarPorNome(String nome) throws PessoaNaoEncontradaException;
    void removerPessoa(String nome) throws PessoaNaoEncontradaException;
    List<Idoso> listarIdosos();
    List<Cuidador> listarCuidadores();
    void adicionarMedicamentoAoIdoso(String nomeIdoso, Medicamento m)
            throws PessoaNaoEncontradaException, ValidacaoException;
    void associarCuidadorIdoso(String nomeCuidador, String nomeIdoso)
            throws PessoaNaoEncontradaException, ValidacaoException;
    void salvarDados(String caminho) throws Exception;
    void carregarDados(String caminho) throws Exception;
}
