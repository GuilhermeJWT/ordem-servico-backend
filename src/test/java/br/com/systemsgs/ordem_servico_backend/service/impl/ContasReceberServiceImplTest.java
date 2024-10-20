package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelContasReceberDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.ContasReceberResponse;
import br.com.systemsgs.ordem_servico_backend.exception.errors.ContasPagarReceberNaoEncontradaException;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contasReceberService = new ContasReceberServiceImpl(contasReceberRepository, utilClientes, mapper);
        startContasReceber();
    }

    @DisplayName("Teste para pesquisar uma Conta a Receber por ID")
    @Test
    void testPesquisaContasReceberPorId() {
        when(contasReceberRepository.findById(modelContasReceber.getId())).thenReturn(Optional.of(modelContasReceber));
        when(mapper.map(modelContasReceber, ContasReceberResponse.class)).thenReturn(contasReceberResponse);

        ContasReceberResponse response = contasReceberService.pesquisaPorId(modelContasReceber.getId());

        assertNotNull(response);

        verify(contasReceberRepository, times(1)).findById(modelContasReceber.getId());
        verify(mapper, times(1)).map(modelContasReceber, ContasReceberResponse.class);
    }

    @DisplayName("Teste para retornar Contas a Receber Paginados")
    @Test
    void testListarContasReceberPaginada() {
        List<ModelContasReceber> contasReceberList = Arrays.asList(modelContasReceber, modelContasReceber);
        Page<ModelContasReceber> contasReceberPage = new PageImpl<>(contasReceberList, PageRequest.of(0, 10), contasReceberList.size());

        when(contasReceberRepository.findAll(PageRequest.of(0, 10))).thenReturn(contasReceberPage);

        when(mapper.map(modelContasReceber, ContasReceberResponse.class)).thenReturn(contasReceberResponse);
        when(mapper.map(modelContasReceber, ContasReceberResponse.class)).thenReturn(contasReceberResponse);

        Page<ContasReceberResponse> response = contasReceberService.listarContasReceberPaginada(0, 10);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent().get(0)).isEqualTo(contasReceberResponse);
        assertThat(response.getContent().get(1)).isEqualTo(contasReceberResponse);

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
        assertEquals(dadosContasReceber().getDataVencimento(), response.getDataVencimento());
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
        assertEquals(dadosContasReceber().getDataVencimento(), response.getDataVencimento());
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

    @DisplayName("Teste para gerar um relatório de Contas a Receber")
    @Test
    void deveGerarRelatorioExcelComSucesso() throws IOException {
        when(contasReceberRepository.findAll()).thenReturn(List.of(modelContasReceber));

        ResponseEntity<byte[]> responseEntity = contasReceberService.gerarRelatorioExcel(response);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("attachment; filename=relatorio-contas-receber.xlsx", responseEntity.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertNotNull(responseEntity.getBody());

        verify(contasReceberRepository, times(1)).findAll();
    }

    @DisplayName("Teste para gerar um relatório vazio")
    @Test
    void deveGerarRelatorioExcelComListaVazia() throws IOException {
        when(contasReceberRepository.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<byte[]> responseEntity = contasReceberService.gerarRelatorioExcel(response);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("attachment; filename=relatorio-contas-receber.xlsx", responseEntity.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().length > 0);

        verify(contasReceberRepository, times(1)).findAll();
    }

    @DisplayName("Teste para configurar o cabeçalho do relatório")
    @Test
    void deveConfigurarCabecalhoCorretamente() throws IOException {
        when(contasReceberRepository.findAll()).thenReturn(List.of(modelContasReceber));

        ResponseEntity<byte[]> responseEntity = contasReceberService.gerarRelatorioExcel(response);

        HttpHeaders headers = responseEntity.getHeaders();
        assertEquals("attachment; filename=relatorio-contas-receber.xlsx", headers.getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @DisplayName("Teste para gerar um relatório Pdf")
    @Test
    void deveGerarRelatorioPdfComSucesso() throws IOException {
        when(contasReceberRepository.findAll()).thenReturn(List.of(modelContasReceber));

        byte[] pdfData = contasReceberService.gerarRelatorioPdf();

        assertNotNull(pdfData);
        assertTrue(pdfData.length > 0);

        verify(contasReceberRepository, times(1)).findAll();
    }

    @DisplayName("Teste oara gerar relatório com lista vazia")
    @Test
    void deveGerarRelatorioPdfComListaVazia() throws IOException {
        when(contasReceberRepository.findAll()).thenReturn(Arrays.asList());

        byte[] pdfData = contasReceberService.gerarRelatorioPdf();

        assertNotNull(pdfData);
        assertTrue(pdfData.length > 0);

        verify(contasReceberRepository, times(1)).findAll();
    }

    private void startContasReceber(){
        modelContasReceber = new ModelContasReceber(
                dadosContasReceber().getId(),
                dadosContasReceber().getDataVencimento(),
                dadosContasReceber().getValor(),
                dadosContasReceber().getDataEmissao(),
                dadosContasReceber().getObservacao(),
                dadosContasReceber().getFormaPagamento(),
                dadosContasReceber().getStatusContasReceber(),
                dadosContasReceber().getCliente()
        );
        modelContasReceberDTO = new ModelContasReceberDTO(
                dadosContasReceber().getId(),
                dadosContasReceber().getDataVencimento(),
                dadosContasReceber().getValor(),
                dadosContasReceber().getObservacao(),
                dadosContasReceber().getFormaPagamento(),
                dadosContasReceber().getStatusContasReceber(),
                dadosContasReceber().getCliente().getId()
        );
        contasReceberResponse = new ContasReceberResponse(
                dadosContasReceber().getId(),
                dadosContasReceber().getDataVencimento(),
                dadosContasReceber().getValor(),
                dadosContasReceber().getObservacao(),
                dadosContasReceber().getFormaPagamento().name(),
                dadosContasReceber().getStatusContasReceber().name(),
                dadosContasReceber().getCliente().getNome()
        );
        modelContasReceberOptional = Optional.of(new ModelContasReceber(
                dadosContasReceber().getId(),
                dadosContasReceber().getDataVencimento(),
                dadosContasReceber().getValor(),
                dadosContasReceber().getDataEmissao(),
                dadosContasReceber().getObservacao(),
                dadosContasReceber().getFormaPagamento(),
                dadosContasReceber().getStatusContasReceber(),
                dadosContasReceber().getCliente()
        ));
    }
}