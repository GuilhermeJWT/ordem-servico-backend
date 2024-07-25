package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasPagarDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasPagarResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarReceberNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import br.com.systemsgs.ordem_servico_backend.service.ContasPagarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ActiveProfiles(value = "test")
@SpringBootTest
class ContasPagarControllerTest extends ConfigDadosEstaticosEntidades {

    private ModelContasPagarDTO modelContasPagarDTO;
    private ContasPagarResponse contasPagarResponse;

    @InjectMocks
    private ContasPagarController contasPagarController;

    @Mock
    private ContasPagarService contasPagarService;

    @Mock
    private ContasPagarRepository contasPagarRepository;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startContasPagar();
    }

    @DisplayName("Teste para retornar a lista de Contas a Pagar - retorna 200")
    @Test
    void listarContasPagar() {
        when(contasPagarService.listarContasPagar()).thenReturn(List.of(contasPagarResponse));
        when(mapper.map(any(), any())).thenReturn(contasPagarResponse);

        ResponseEntity<List<ContasPagarResponse>> response = contasPagarController.listarContasPagar();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ContasPagarResponse.class, response.getBody().get(0).getClass());

        assertEquals(dadosContasPagar().getId(), response.getBody().get(0).getId());
        assertEquals(dadosContasPagar().getData_vencimento(), response.getBody().get(0).getData_vencimento());
        assertEquals(dadosContasPagar().getValor(), response.getBody().get(0).getValor());
        assertEquals(dadosContasPagar().getObservacao(), response.getBody().get(0).getObservacao());
        assertEquals(dadosContasPagar().getFormaPagamento().name(), response.getBody().get(0).getFormaPagamento());
        assertEquals(dadosContasPagar().getStatusContas().name(), response.getBody().get(0).getStatusContas());
        assertEquals(dadosContasPagar().getFornecedor().getNome(), response.getBody().get(0).getNomeFornecedor());
    }

    @DisplayName("Pesquisa uma Conta a Pagar pelo ID e testa o Retorno do Body - retorna 200")
    @Test
    void pesquisarContaPagarPorId() {
        when(contasPagarService.pesquisaPorId(dadosContasPagar().getId())).thenReturn(contasPagarResponse);
        when(mapper.map(any(), any())).thenReturn(contasPagarResponse);

        ResponseEntity<ContasPagarResponse> response =
                contasPagarController.pesquisarPorId(dadosContasPagar().getId());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ContasPagarResponse.class, response.getBody().getClass());

        assertEquals(dadosContasPagar().getId(), response.getBody().getId());
        assertEquals(dadosContasPagar().getData_vencimento(), response.getBody().getData_vencimento());
        assertEquals(dadosContasPagar().getValor(), response.getBody().getValor());
        assertEquals(dadosContasPagar().getObservacao(), response.getBody().getObservacao());
        assertEquals(dadosContasPagar().getFormaPagamento().name(), response.getBody().getFormaPagamento());
        assertEquals(dadosContasPagar().getStatusContas().name(), response.getBody().getStatusContas());
        assertEquals(dadosContasPagar().getFornecedor().getNome(), response.getBody().getNomeFornecedor());
    }

    @DisplayName("Pesquisa uma Conta a Pagar inexistente e retorna 404")
    @Test
    void pesquisaContaPagarInexistenteRetorna404(){
        when(contasPagarRepository.findById(dadosContasPagar().getId())).thenThrow(new ContasPagarReceberNaoEncontradaException());

        try{
            contasPagarService.pesquisaPorId(dadosContasPagar().getId());
        }catch (Exception exception){
            assertEquals(ContasPagarReceberNaoEncontradaException.class, exception.getClass());
            assertEquals(mensagemErro().get(5), exception.getMessage());
        }
    }

    @DisplayName("Salva uma Conta a Pagar e retorna 201")
    @Test
    void salvarContasPagar() {
        when(contasPagarService.cadastrarContasPagar(modelContasPagarDTO)).thenReturn(contasPagarResponse);

        ResponseEntity<ContasPagarResponse> response =
                contasPagarController.salvarContasPagar(modelContasPagarDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @DisplayName("Atualiza uma Conta a Pagar e retorna 200")
    @Test
    void atualizarContasPagar() {
        when(contasPagarService.alterarContasPagar(dadosContasPagar().getId(), modelContasPagarDTO))
                .thenReturn(contasPagarResponse);

        ResponseEntity<ContasPagarResponse> response = contasPagarController
                .atualizarContasPagar(dadosContasPagar().getId(), modelContasPagarDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ContasPagarResponse.class, response.getBody().getClass());

        assertEquals(dadosContasPagar().getId(), response.getBody().getId());
        assertEquals(dadosContasPagar().getData_vencimento(), response.getBody().getData_vencimento());
        assertEquals(dadosContasPagar().getValor(), response.getBody().getValor());
        assertEquals(dadosContasPagar().getObservacao(), response.getBody().getObservacao());
        assertEquals(dadosContasPagar().getFormaPagamento().name(), response.getBody().getFormaPagamento());
        assertEquals(dadosContasPagar().getStatusContas().name(), response.getBody().getStatusContas());
        assertEquals(dadosContasPagar().getFornecedor().getNome(), response.getBody().getNomeFornecedor());
    }

    @DisplayName("Deleta uma Conta a Pagar e retorna 204")
    @Test
    void delete() {
        doNothing().when(contasPagarService).deletarContasPagar(dadosContasPagar().getId());

        ResponseEntity<ModelContasPagarDTO> response = contasPagarController.delete(dadosContasPagar().getId());

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(contasPagarService, times(1)).deletarContasPagar(dadosContasPagar().getId());
    }

    private void startContasPagar(){
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