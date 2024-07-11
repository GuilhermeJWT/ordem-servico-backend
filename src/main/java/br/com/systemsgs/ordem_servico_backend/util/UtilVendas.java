package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.exception.VendaNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelVendas;
import br.com.systemsgs.ordem_servico_backend.repository.VendasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilVendas {

    @Autowired
    private VendasRepository vendasRepository;

    public ModelVendas pesquisarVendaPeloId(Long id){
        ModelVendas pesquisaVenda = vendasRepository.findById(id).
                orElseThrow(() -> new VendaNaoEncontradaException());

        return pesquisaVenda;
    }
}
