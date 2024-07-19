package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.exception.errors.FornecedorNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelFornecedor;
import br.com.systemsgs.ordem_servico_backend.repository.FornecedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilFornecedores {

    @Autowired
    private FornecedoresRepository fornecedoresRepository;

    public ModelFornecedor pesquisarFornecedorPeloId(Long id){
         ModelFornecedor pesquisaFornecedor = fornecedoresRepository.findById(id).
                 orElseThrow(() -> new FornecedorNaoEncontradoException());

        return pesquisaFornecedor;
    }
}
