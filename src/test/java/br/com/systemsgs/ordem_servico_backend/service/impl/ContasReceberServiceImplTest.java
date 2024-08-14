package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasReceberDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasReceberResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarReceberNaoEncontradaException;
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

import java.util.Collections;
import java.util.List;
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
        contasReceberService = new ContasReceberServiceImpl(contasReceberRepository, utilClientes, mapper);
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

    @DisplayName("Teste pesquisa Conta a Receber Inexistente - 404")
    @Test
    void testPesquisaContaReceberInexistente() {
        when(contasReceberRepository.findById(modelContasReceber.getId()))
                .thenThrow(new ContasPagarReceberNaoEncontradaException());

        try{
            contasReceberService.pesquisaPorId(modelContasReceber.getId());
        }catch (Exception exception){
            assertEquals(ContasPagarReceberNaoEncontradaException.class, exception.getClass());
            assertEquals(mensagemErro().get(5), exception.getMessage());
        }
    }

    @DisplayName("Teste para listar Contas a Receber Vazia - 0")
    @Test
    void testListarContasReceberSemContas() {
        when(contasReceberRepository.findAll()).thenReturn(Collections.emptyList());

        List<ContasReceberResponse> response = contasReceberService.listarContasReceber();

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(contasReceberRepository, times(1)).findAll();
    }

    @DisplayName("Teste para salvar uma Conta a Receber")
    @Test
    void testCadastrarContasReceber() {
        when(utilClientes.pesquisarClientePeloId(1L)).thenReturn(dadosClientes());
        when(contasReceberRepository.save(any(ModelContasReceber.class))).thenReturn(modelContasReceber);
        when(mapper.map(modelContasReceber, ContasReceberResponse.class)).thenReturn(contasReceberResponse);

        ContasReceberResponse response = contasReceberService.cadastrarContasReceber(modelContasReceberDTO);

        assertNotNull(response);

        assertEquals(dadosContasReceber().getId(), response.getId());
        assertEquals(dadosContasReceber().getData_vencimento(), response.getData_vencimento());
        assertEquals(dadosContasReceber().getValor(), response.getValor());
        assertEquals(dadosContasReceber().getObservacao(), response.getObservacao());
        assertEquals(dadosContasReceber().getFormaPagamento().name(), response.getFormaPagamento());
        assertEquals(dadosContasReceber().getStatusContasReceber().name(), response.getStatusContasReceber());
        assertEquals(dadosContasReceber().getCliente().getNome(), response.getNomeCliente());

        verify(utilClientes, times(1)).pesquisarClientePeloId(1L);
        verify(contasReceberRepository, times(1)).save(any(ModelContasReceber.class));
        verify(mapper, times(1)).map(modelContasReceber, ContasReceberResponse.class);
    }

    @DisplayName("Teste para alterar uma Conta a Receber")
    @Test
    void testAlterarContasReceber() {
        when(contasReceberRepository.findById(modelContasReceber.getId())).thenReturn(Optional.of(modelContasReceber));
        when(contasReceberRepository.save(modelContasReceber)).thenReturn(modelContasReceber);
        when(mapper.map(modelContasReceberDTO, ModelContasReceber.class)).thenReturn(modelContasReceber);
        when(mapper.map(modelContasReceber, ContasReceberResponse.class)).thenReturn(contasReceberResponse);

        ContasReceberResponse response = contasReceberService.alterarContasReceber(modelContasReceber.getId(), modelContasReceberDTO);

        assertNotNull(response);

        assertEquals(dadosContasReceber().getId(), response.getId());
        assertEquals(dadosContasReceber().getData_vencimento(), response.getData_vencimento());
        assertEquals(dadosContasReceber().getValor(), response.getValor());
        assertEquals(dadosContasReceber().getObservacao(), response.getObservacao());
        assertEquals(dadosContasReceber().getFormaPagamento().name(), response.getFormaPagamento());
        assertEquals(dadosContasReceber().getStatusContasReceber().name(), response.getStatusContasReceber());
        assertEquals(dadosContasReceber().getCliente().getNome(), response.getNomeCliente());

        verify(contasReceberRepository, times(1)).findById(modelContasReceber.getId());
        verify(contasReceberRepository, times(1)).save(modelContasReceber);
        verify(mapper, times(1)).map(modelContasReceberDTO, ModelContasReceber.class);
        verify(mapper, times(1)).map(modelContasReceber, ContasReceberResponse.class);
    }

    @DisplayName("Deleta Conta a Receber")
    @Test
    void testDeletarContaReceber() {
        doNothing().when(contasReceberRepository).deleteById(modelContasReceber.getId());

        contasReceberService.deletarContasReceber(modelContasReceber.getId());
        verify(contasReceberRepository, times(1)).deleteById(anyLong());
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