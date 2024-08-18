package br.com.systemsgs.ordem_servico_backend.service;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasReceberDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasPagarResponse;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasReceberResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContasReceberService {

    ContasReceberResponse pesquisaPorId(Long id);

    List<ContasReceberResponse> listarContasReceber();

    ContasReceberResponse cadastrarContasReceber(ModelContasReceberDTO modelContasReceberDTO);

    void deletarContasReceber(Long id);

    ContasReceberResponse alterarContasReceber(Long id, ModelContasReceberDTO modelContasReceberDTO);

    Page<ContasReceberResponse> listarContasReceberPaginada(int page, int size);
}
