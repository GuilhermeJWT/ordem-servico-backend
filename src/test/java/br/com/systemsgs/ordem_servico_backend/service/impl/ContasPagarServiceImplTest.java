package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasPagarDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasPagarResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarReceberNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;
import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import br.com.systemsgs.ordem_servico_backend.util.UtilFornecedores;
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
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class ContasPagarServiceImplTest extends ConfigDadosEstaticosEntidades {

    private ModelContasPagar modelContasPagar;
    private ContasPagarResponse contasPagarResponse;
    private ModelContasPagarDTO modelContasPagarDTO;

    @InjectMocks
    private ContasPagarServiceImpl contasPagarService;

    @Mock
    private ContasPagarRepository contasPagarRepository;

    @Mock
    private UtilFornecedores utilFornecedores;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startContasPagar();
    }

    @DisplayName("Teste para pesquisar uma Conta a Pagar pelo Id")
    @Test
    void testPesquisaContasPagarPorId() {
        when(contasPagarRepository.findById(modelContasPagar.getId())).thenReturn(Optional.of(modelContasPagar));
        when(modelMapper.map(modelContasPagar, ContasPagarResponse.class)).thenReturn(contasPagarResponse);

        ContasPagarResponse response = contasPagarService.pesquisaPorId(modelContasPagar.getId());

        assertNotNull(response);
        assertEquals(dadosContasPagar().getId(), response.getId());

        verify(contasPagarRepository, times(1)).findById(1L);
    }

    private void startContasPagar(){
        modelContasPagar = new ModelContasPagar(
                dadosContasPagar().getId(),
                dadosContasPagar().getData_vencimento(),
                dadosContasPagar().getData_emissao(),
                dadosContasPagar().getValor(),
                dadosContasPagar().getObservacao(),
                dadosContasPagar().getFormaPagamento(),
                dadosContasPagar().getStatusContas(),
                dadosFornecedores()
        );
        modelContasPagarDTO = new ModelContasPagarDTO(
                dadosContasPagar().getId(),
                dadosContasPagar().getData_vencimento(),
                dadosContasPagar().getValor(),
                dadosContasPagar().getObservacao(),
                dadosContasPagar().getFormaPagamento(),
                dadosContasPagar().getStatusContas(),
                dadosFornecedores().getId()
        );
        contasPagarResponse = new ContasPagarResponse(
                dadosContasPagar().getId(),
                dadosContasPagar().getData_vencimento(),
                dadosContasPagar().getValor(),
                dadosContasPagar().getObservacao(),
                dadosContasPagar().getFormaPagamento().name(),
                dadosContasPagar().getStatusContas().name(),
                dadosFornecedores().getNome()
        );
    }
}