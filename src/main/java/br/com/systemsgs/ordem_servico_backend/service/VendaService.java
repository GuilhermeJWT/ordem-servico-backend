package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelVendasDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.VendasResponse;
import br.com.systemsgs.ordem_servico_backend.model.ModelVendas;

public interface VendaService {

    ModelVendas salvarVenda(ModelVendasDTO modelVendasDTO);

    VendasResponse pesquisaVendaPorId(Long id);

}
