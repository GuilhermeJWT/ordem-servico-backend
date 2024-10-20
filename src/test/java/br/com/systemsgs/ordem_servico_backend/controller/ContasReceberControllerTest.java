package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasReceberDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasReceberResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarReceberNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import br.com.systemsgs.ordem_servico_backend.service.ContasReceberService;
import br.com.systemsgs.ordem_servico_backend.service.GerarRelatorioService;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
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
    private GerarRelatorioService gerarRelatorioService;

    @Mock
    private ContasReceberRepository contasReceberRepository;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        contasReceberController = new ContasReceberController(contasReceberService, gerarRelatorioService);
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
        assertEquals(dadosContasReceber().getDataVencimento(), response.getBody().get(0).getDataVencimento());
        assertEquals(dadosContasReceber().getValor(), response.getBody().get(0).getValor());
        assertEquals(dadosContasReceber().getObservacao(), response.getBody().get(0).getObservacao());
        assertEquals(dadosContasReceber().getFormaPagamento().name(), response.getBody().get(0).getFormaPagamento());
        assertEquals(dadosContasReceber().getStatusContasReceber().name(), response.getBody().get(0).getStatusContasReceber());
        assertEquals(dadosContasReceber().getCliente().getNome(), response.getBody().get(0).getNomeCliente());
    }

    @DisplayName("Teste para retornar a Paginação de Contas a Receber")
    @Test
    void testDeveRetornarPaginacaoDeContasReceber() {
        List<ContasReceberResponse> contasReceberResponseList = Arrays.asList(contasReceberResponse, contasReceberResponse);
        Page<ContasReceberResponse> pageContasReceber = new PageImpl<>(contasReceberResponseList,
                PageRequest.of(0, 10), contasReceberResponseList.size());

        when(contasReceberService.listarContasReceberPaginada(0, 10)).thenReturn(pageContasReceber);

        Page<ContasReceberResponse> response = contasReceberController.listarContasReceberPaginada(0, 10);

        assertNotNull(response);
        assertNotNull(response.getContent());
        assertEquals(PageImpl.class, response.getClass());
        assertEquals(ContasReceberResponse.class, response.getContent().get(0).getClass());

        assertEquals(dadosContasReceber().getId(), response.getContent().get(0).getId());
        assertEquals(dadosContasReceber().getDataVencimento(), response.getContent().get(0).getDataVencimento());
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
        assertEquals(dadosContasReceber().getDataVencimento(), response.getBody().getDataVencimento());
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
        assertEquals(dadosContasReceber().getDataVencimento(), response.getBody().getDataVencimento());
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

    @DisplayName("Teste para gerar um relatório")
    @Test
    void deveGerarRelatorioExcelComSucesso() throws IOException {
        byte[] excelBytes = new byte[]{1, 2, 3};
        when(gerarRelatorioService.gerarRelatorioExcel(response)).thenReturn(new ResponseEntity<>(excelBytes, HttpStatus.OK));

        ResponseEntity<byte[]> resultado = contasReceberController.gerarRelatorioExcel(response);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertArrayEquals(excelBytes, resultado.getBody());
        verify(gerarRelatorioService, times(1)).gerarRelatorioExcel(response);
    }

    @DisplayName("Teste para erro no relatório")
    @Test
    void deveRetornarErroAoGerarRelatorioExcel() throws IOException {
        when(gerarRelatorioService.gerarRelatorioExcel(response)).thenThrow(new IOException("Erro ao tentar gerar o Arquivo/Relatório."));

        assertThrows(IOException.class, () -> {contasReceberController.gerarRelatorioExcel(response);});

        verify(gerarRelatorioService, times(1)).gerarRelatorioExcel(response);
    }

    @DisplayName("Teste para gerar relatório de Pdf")
    @Test
    void deveGerarRelatorioPdfComSucesso() throws IOException {
        byte[] pdfBytes = new byte[]{10, 20, 30};
        when(gerarRelatorioService.gerarRelatorioPdf()).thenReturn(pdfBytes);

        ResponseEntity<byte[]> resultado = contasReceberController.gerarRelatorioPdf(response);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertArrayEquals(pdfBytes, resultado.getBody());

        HttpHeaders headers = resultado.getHeaders();
        assertEquals("attachment; filename=relatorio-contas-receber.pdf", headers.getFirst("Content-Disposition"));

        verify(gerarRelatorioService, times(1)).gerarRelatorioPdf();
    }

    private void startContasReceber(){
        modelContasReceberDTO = new ModelContasReceberDTO(
            dadosContasReceber().getId(),
            dadosContasReceber().getDataVencimento(),
            dadosContasReceber().getValor(),
            dadosContasReceber().getObservacao(),
            dadosContasReceber().getFormaPagamento(),
            dadosContasReceber().getStatusContasReceber(),
            dadosClientes().getId()
        );
        contasReceberResponse = new ContasReceberResponse(
            dadosContasReceber().getId(),
            dadosContasReceber().getDataVencimento(),
            dadosContasReceber().getValor(),
            dadosContasReceber().getObservacao(),
            dadosContasReceber().getFormaPagamento().name(),
            dadosContasReceber().getStatusContasReceber().name(),
            dadosClientes().getNome()
        );
    }
}