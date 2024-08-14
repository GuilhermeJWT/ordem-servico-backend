package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.exception.errors.VendaNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelVendas;
import br.com.systemsgs.ordem_servico_backend.repository.VendasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class UtilVendas {

    private final VendasRepository vendasRepository;

    @Autowired
    public UtilVendas(VendasRepository vendasRepository) {
        this.vendasRepository = vendasRepository;
    }

    public ModelVendas pesquisarVendaPeloId(Long id){
        ModelVendas pesquisaVenda = vendasRepository.findById(id).
                orElseThrow(() -> new VendaNaoEncontradaException());

        return pesquisaVenda;
    }

    public Optional<BigDecimal> calculaTotalVendas(){
       return vendasRepository.calculaTotalVendasTodoPeriodo();
    }

    public Optional<Integer> somaTotalItensVendidosTodoPeriodo(){
        return vendasRepository.calculaTotalItensVendidosTodoPeriodo();
    }

}
