package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasReceberDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasReceberResponse;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class ContasReceberServiceImplTest extends ConfigDadosEstaticosEntidades {

    private ModelContasReceber modelContasReceber;
    private Optional<ModelContasReceber> modelContasReceberOptional;
    private ModelContasReceberDTO modelContasReceberDTO;
    private ContasReceberResponse contasReceberResponse;

    @InjectMocks
    private ContasReceberServiceImpl contasReceberService;

    @Mock
    private ContasReceberRepository contasReceberRepository;

    @Mock
    private UtilClientes utilClientes;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startContasReceber();
    }

    @DisplayName("Teste para pesquisar uma Conta a Receber por ID")
    @Test
    void testPesquisaContasReceberPorId() {
        ModelContasReceber modelContasReceber = dadosContasReceber();
        ContasReceberResponse clienteResponse = contasReceberResponse;

        when(contasReceberRepository.findById(modelContasReceber.getId())).thenReturn(Optional.of(modelContasReceber));
        when(mapper.map(modelContasReceber, ContasReceberResponse.class)).thenReturn(clienteResponse);

        ContasReceberResponse response = contasReceberService.pesquisaPorId(modelContasReceber.getId());

        assertNotNull(response);

        verify(contasReceberRepository, times(1)).findById(modelContasReceber.getId());
        verify(mapper, times(1)).map(modelContasReceber, ContasReceberResponse.class);
    }

    private void startContasReceber(){
        modelContasReceber = new ModelContasReceber(
                dadosContasReceber().getId(),
                dadosContasReceber().getData_vencimento(),
                dadosContasReceber().getValor(),
                dadosContasReceber().getData_emissao(),
                dadosContasReceber().getObservacao(),
                dadosContasReceber().getFormaPagamento(),
                dadosContasReceber().getStatusContasReceber(),
                dadosContasReceber().getCliente()
        );
        modelContasReceberDTO = new ModelContasReceberDTO(
                dadosContasReceber().getId(),
                dadosContasReceber().getData_vencimento(),
                dadosContasReceber().getValor(),
                dadosContasReceber().getObservacao(),
                dadosContasReceber().getFormaPagamento(),
                dadosContasReceber().getStatusContasReceber(),
                dadosContasReceber().getCliente().getId()
        );
        contasReceberResponse = new ContasReceberResponse(
                dadosContasReceber().getId(),
                dadosContasReceber().getData_vencimento(),
                dadosContasReceber().getValor(),
                dadosContasReceber().getObservacao(),
                dadosContasReceber().getFormaPagamento().name(),
                dadosContasReceber().getStatusContasReceber().name(),
                dadosContasReceber().getCliente().getNome()
        );
        modelContasReceberOptional = Optional.of(new ModelContasReceber(
                dadosContasReceber().getId(),
                dadosContasReceber().getData_vencimento(),
                dadosContasReceber().getValor(),
                dadosContasReceber().getData_emissao(),
                dadosContasReceber().getObservacao(),
                dadosContasReceber().getFormaPagamento(),
                dadosContasReceber().getStatusContasReceber(),
                dadosContasReceber().getCliente()
        ));
    }
}