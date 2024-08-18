package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasReceberDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasReceberResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarReceberNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import br.com.systemsgs.ordem_servico_backend.service.ContasReceberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ActiveProfiles(value = "test")
@SpringBootTest
class ContasReceberControllerTest extends ConfigDadosEstaticosEntidades {

    private ModelContasReceberDTO modelContasReceberDTO;
    private ContasReceberResponse contasReceberResponse;

    @InjectMocks
    private ContasReceberController contasReceberController;

    @Mock
    private ContasReceberService contasReceberService;

    @Mock
    private ContasReceberRepository contasReceberRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        contasReceberController = new ContasReceberController(contasReceberService);
        startContasReceber();
    }

    @DisplayName("Teste para retornar a lista de Contas a Receber - retorna 200")
    @Test
    void listarContasReceber() {
        when(contasReceberService.listarContasReceber()).thenReturn(List.of(contasReceberResponse));

        ResponseEntity<List<ContasReceberResponse>> response = contasReceberController.listarContasReceber();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ContasReceberResponse.class, response.getBody().get(0).getClass());

        assertEquals(dadosContasReceber().getId(), response.getBody().get(0).getId());
        assertEquals(dadosContasReceber().getData_vencimento(), response.getBody().get(0).getData_vencimento());
        assertEquals(dadosContasReceber().getValor(), response.getBody().get(0).getValor());
        assertEquals(dadosContasReceber().getObservacao(), response.getBody().get(0).getObservacao());
        assertEquals(dadosContasReceber().getFormaPagamento().name(), response.getBody().get(0).getFormaPagamento());
        assertEquals(dadosContasReceber().getStatusContasReceber().name(), response.getBody().get(0).getStatusContasReceber());
        assertEquals(dadosContasReceber().getCliente().getNome(), response.getBody().get(0).getNomeCliente());
    }

    @DisplayName("Teste para retornar a Paginação de Contas a Receber")
    @Test
    void testDeveRetornarPaginacaoDeContasReceber() {
        ContasReceberResponse contasReceberResponse1 = contasReceberResponse;
        ContasReceberResponse contasReceberResponse2 = contasReceberResponse;

        List<ContasReceberResponse> contasReceberResponseList = Arrays.asList(contasReceberResponse1, contasReceberResponse2);
        Page<ContasReceberResponse> pageContasReceber = new PageImpl<>(contasReceberResponseList,
                PageRequest.of(0, 10), contasReceberResponseList.size());

        when(contasReceberService.listarContasReceberPaginada(0, 10)).thenReturn(pageContasReceber);

        Page<ContasReceberResponse> response = contasReceberController.listarContasReceberPaginada(0, 10);

        assertNotNull(response);
        assertNotNull(response.getContent());
        assertEquals(PageImpl.class, response.getClass());
        assertEquals(ContasReceberResponse.class, response.getContent().get(0).getClass());

        assertEquals(dadosContasReceber().getId(), response.getContent().get(0).getId());
        assertEquals(dadosContasReceber().getData_vencimento(), response.getContent().get(0).getData_vencimento());
        assertEquals(dadosContasReceber().getValor(), response.getContent().get(0).getValor());
        assertEquals(dadosContasReceber().getObservacao(), response.getContent().get(0).getObservacao());
        assertEquals(dadosContasReceber().getFormaPagamento().name(), response.getContent().get(0).getFormaPagamento());
        assertEquals(dadosContasReceber().getStatusContasReceber().name(), response.getContent().get(0).getStatusContasReceber());
        assertEquals(dadosContasReceber().getCliente().getNome(), response.getContent().get(0).getNomeCliente());
    }

    @DisplayName("Teste lista Contas Receber Paginada Vazia")
    @Test
    void listarContasReceberPaginadasComPaginaVazia() {
        Page<ContasReceberResponse> contasReceberResponsePage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);

        when(contasReceberService.listarContasReceberPaginada(0, 10)).thenReturn(contasReceberResponsePage);

        Page<ContasReceberResponse> response = contasReceberController.listarContasReceberPaginada(0, 10);

        assertThat(response.getContent()).isNotNull();
        assertThat(response.getContent()).isEmpty();
    }

    @DisplayName("Pesquisa uma Conta a Receber pelo ID e testa o Retorno do Body - retorna 200")
    @Test
    void pesquisarContaReceberPorId() {
        when(contasReceberService.pesquisaPorId(dadosContasReceber().getId())).thenReturn(contasReceberResponse);

        ResponseEntity<ContasReceberResponse> response =
                contasReceberController.pesquisarPorId(dadosContasReceber().getId());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ContasReceberResponse.class, response.getBody().getClass());

        assertEquals(dadosContasReceber().getId(), response.getBody().getId());
        assertEquals(dadosContasReceber().getData_vencimento(), response.getBody().getData_vencimento());
        assertEquals(dadosContasReceber().getValor(), response.getBody().getValor());
        assertEquals(dadosContasReceber().getObservacao(), response.getBody().getObservacao());
        assertEquals(dadosContasReceber().getFormaPagamento().name(), response.getBody().getFormaPagamento());
        assertEquals(dadosContasReceber().getStatusContasReceber().name(), response.getBody().getStatusContasReceber());
        assertEquals(dadosContasReceber().getCliente().getNome(), response.getBody().getNomeCliente());
    }

    @DisplayName("Pesquisa uma Conta a Receber Inexistente e retorna 404")
    @Test
    void pesquisaContaReceberInexistenteRetorna404(){
       when(contasReceberRepository.findById(dadosContasReceber().getId())).
               thenThrow(new ContasPagarReceberNaoEncontradaException());

        try{
            contasReceberService.pesquisaPorId(dadosContasReceber().getId());
        }catch (Exception exception){
            assertEquals(ContasPagarReceberNaoEncontradaException.class, exception.getClass());
            assertEquals(mensagemErro().get(5), exception.getMessage());
        }
    }

    @DisplayName("Salva uma Conta a Receber retorna 201")
    @Test
    void salvarContasReceber() {
        when(contasReceberService.cadastrarContasReceber(modelContasReceberDTO))
                .thenReturn(contasReceberResponse);

        ResponseEntity<ContasReceberResponse> response =
                contasReceberController.salvarContasReceber(modelContasReceberDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @DisplayName("Atualiza uma Conta a Receber e retorna 200")
    @Test
    void atualizarContasReceber() {
        when(contasReceberService.alterarContasReceber(dadosContasReceber().getId(), modelContasReceberDTO))
                .thenReturn(contasReceberResponse);

        ResponseEntity<ContasReceberResponse> response = contasReceberController
                .atualizarContasReceber(dadosContasReceber().getId(), modelContasReceberDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ContasReceberResponse.class, response.getBody().getClass());

        assertEquals(dadosContasReceber().getId(), response.getBody().getId());
        assertEquals(dadosContasReceber().getData_vencimento(), response.getBody().getData_vencimento());
        assertEquals(dadosContasReceber().getValor(), response.getBody().getValor());
        assertEquals(dadosContasReceber().getObservacao(), response.getBody().getObservacao());
        assertEquals(dadosContasReceber().getFormaPagamento().name(), response.getBody().getFormaPagamento());
        assertEquals(dadosContasReceber().getStatusContasReceber().name(), response.getBody().getStatusContasReceber());
        assertEquals(dadosContasReceber().getCliente().getNome(), response.getBody().getNomeCliente());
    }

    @DisplayName("Deleta uma Conta a Receber e retorna 204")
    @Test
    void delete() {
        doNothing().when(contasReceberService).deletarContasReceber(dadosContasReceber().getId());

        ResponseEntity<ContasReceberResponse> response = contasReceberController.delete(dadosContasReceber().getId());

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(contasReceberService, times(1)).deletarContasReceber(dadosContasReceber().getId());
    }

    private void startContasReceber(){
        modelContasReceberDTO = new ModelContasReceberDTO(
            dadosContasReceber().getId(),
            dadosContasReceber().getData_vencimento(),
            dadosContasReceber().getValor(),
            dadosContasReceber().getObservacao(),
            dadosContasReceber().getFormaPagamento(),
            dadosContasReceber().getStatusContasReceber(),
            dadosClientes().getId()
        );
        contasReceberResponse = new ContasReceberResponse(
            dadosContasReceber().getId(),
            dadosContasReceber().getData_vencimento(),
            dadosContasReceber().getValor(),
            dadosContasReceber().getObservacao(),
            dadosContasReceber().getFormaPagamento().name(),
            dadosContasReceber().getStatusContasReceber().name(),
            dadosClientes().getNome()
        );
    }
}