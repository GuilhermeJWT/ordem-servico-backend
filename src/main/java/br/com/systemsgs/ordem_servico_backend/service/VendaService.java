package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.ModelVendasDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelVendas;

public interface VendaService {

    ModelVendas salvarVenda(ModelVendasDTO modelVendasDTO);

}
