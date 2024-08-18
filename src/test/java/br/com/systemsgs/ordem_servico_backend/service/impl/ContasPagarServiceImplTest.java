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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        contasPagarService = new ContasPagarServiceImpl(contasPagarRepository, utilFornecedores, modelMapper);
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
        assertEquals(dadosContasPagar().getData_vencimento(), response.getData_vencimento());
        assertEquals(dadosContasPagar().getValor(), response.getValor());
        assertEquals(dadosContasPagar().getObservacao(), response.getObservacao());
        assertEquals(dadosContasPagar().getFormaPagamento().name(), response.getFormaPagamento());
        assertEquals(dadosContasPagar().getStatusContas().name(), response.getStatusContas());
        assertEquals(dadosContasPagar().getFornecedor().getNome(), response.getNomeFornecedor());

        verify(contasPagarRepository, times(1)).findById(1L);
    }

    @DisplayName("Teste para pesquisar uma Conta a Pagar Inexistente")
    @Test
    void testPesquisaPorIdContaNaoEncontrada() {
        when(contasPagarRepository.findById(modelContasPagar.getId())).thenReturn(Optional.empty());

        assertThrows(ContasPagarReceberNaoEncontradaException.class, () -> contasPagarService.pesquisaPorId(1L));

        verify(contasPagarRepository, times(1)).findById(modelContasPagar.getId());
    }

    @DisplayName("Teste para retornar Contas a Pagar Paginados")
    @Test
    void testListarContasPagarPaginada() {
        var contasPagarList = Arrays.asList(modelContasPagar, modelContasPagar);
        Page<ModelContasPagar> contasPagarPage = new PageImpl<>(contasPagarList, PageRequest.of(0, 10), contasPagarList.size());

        when(contasPagarRepository.findAll(PageRequest.of(0, 10))).thenReturn(contasPagarPage);
        when(modelMapper.map(modelContasPagar, ContasPagarResponse.class)).thenReturn(contasPagarResponse);
        when(modelMapper.map(modelContasPagar, ContasPagarResponse.class)).thenReturn(contasPagarResponse);

        Page<ContasPagarResponse> response = contasPagarService.listarContasPagarPaginada(0, 10);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent().get(0)).isEqualTo(contasPagarResponse);
        assertThat(response.getContent().get(1)).isEqualTo(contasPagarResponse);

        assertEquals(dadosContasPagar().getId(), response.getContent().get(0).getId());
        assertEquals(dadosContasPagar().getData_vencimento(), response.getContent().get(0).getData_vencimento());
        assertEquals(dadosContasPagar().getValor(), response.getContent().get(0).getValor());
        assertEquals(dadosContasPagar().getObservacao(), response.getContent().get(0).getObservacao());
        assertEquals(dadosContasPagar().getFormaPagamento().name(), response.getContent().get(0).getFormaPagamento());
        assertEquals(dadosContasPagar().getStatusContas().name(), response.getContent().get(0).getStatusContas());
        assertEquals(dadosContasPagar().getFornecedor().getNome(), response.getContent().get(0).getNomeFornecedor());
    }

    @DisplayName("Teste para listar Contas a Pagar")
    @Test
    void testListarContasPagarSucesso() {
        when(contasPagarRepository.findAll()).thenReturn(List.of(modelContasPagar));
        when(modelMapper.map(any(ModelContasPagar.class), eq(ContasPagarResponse.class))).thenReturn(contasPagarResponse);

        List<ContasPagarResponse> response = contasPagarService.listarContasPagar();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ContasPagarResponse.class, response.get(0).getClass());

        assertEquals(dadosContasPagar().getId(), response.get(0).getId());
        assertEquals(dadosContasPagar().getData_vencimento(), response.get(0).getData_vencimento());
        assertEquals(dadosContasPagar().getValor(), response.get(0).getValor());
        assertEquals(dadosContasPagar().getObservacao(), response.get(0).getObservacao());
        assertEquals(dadosContasPagar().getFormaPagamento().name(), response.get(0).getFormaPagamento());
        assertEquals(dadosContasPagar().getStatusContas().name(), response.get(0).getStatusContas());
        assertEquals(dadosContasPagar().getFornecedor().getNome(), response.get(0).getNomeFornecedor());

        verify(contasPagarRepository, times(1)).findAll();
    }

    @DisplayName("Teste para salvar uma Conta a Pagar")
    @Test
    void testCadastrarContasPagar() {
        when(utilFornecedores.pesquisarFornecedorPeloId(dadosFornecedores().getId())).thenReturn(dadosFornecedores());
        when(contasPagarRepository.save(any(ModelContasPagar.class))).thenReturn(modelContasPagar);
        when(modelMapper.map(modelContasPagar, ContasPagarResponse.class)).thenReturn(contasPagarResponse);

        ContasPagarResponse response = contasPagarService.cadastrarContasPagar(modelContasPagarDTO);

        assertNotNull(response);
        assertEquals(dadosContasPagar().getId(), response.getId());
        assertEquals(dadosContasPagar().getData_vencimento(), response.getData_vencimento());
        assertEquals(dadosContasPagar().getValor(), response.getValor());
        assertEquals(dadosContasPagar().getObservacao(), response.getObservacao());
        assertEquals(dadosContasPagar().getFormaPagamento().name(), response.getFormaPagamento());
        assertEquals(dadosContasPagar().getStatusContas().name(), response.getStatusContas());
        assertEquals(dadosContasPagar().getFornecedor().getNome(), response.getNomeFornecedor());

        verify(contasPagarRepository, times(1)).save(any(ModelContasPagar.class));
    }

    @DisplayName("Teste para deletar uma Conta a Pagar pelo ID")
    @Test
    void testDeletarContasPagar() {
        doNothing().when(contasPagarRepository).deleteById(modelContasPagar.getId());

        contasPagarService.deletarContasPagar(modelContasPagar.getId());

        verify(contasPagarRepository, times(1)).deleteById(modelContasPagar.getId());
    }

    @DisplayName("Teste para alterar uma Conta a Pagar, pelo ID e Entidade DTO")
    @Test
    void testAlterarContasPagarSucesso() {
        when(contasPagarRepository.findById(modelContasPagar.getId())).thenReturn(Optional.of(modelContasPagar));
        when(contasPagarRepository.save(any(ModelContasPagar.class))).thenReturn(modelContasPagar);
        when(modelMapper.map(modelContasPagar, ContasPagarResponse.class)).thenReturn(contasPagarResponse);

        ContasPagarResponse response = contasPagarService.alterarContasPagar(modelContasPagar.getId(), modelContasPagarDTO);

        assertNotNull(response);
        assertEquals(dadosContasPagar().getId(), response.getId());
        assertEquals(dadosContasPagar().getData_vencimento(), response.getData_vencimento());
        assertEquals(dadosContasPagar().getValor(), response.getValor());
        assertEquals(dadosContasPagar().getObservacao(), response.getObservacao());
        assertEquals(dadosContasPagar().getFormaPagamento().name(), response.getFormaPagamento());
        assertEquals(dadosContasPagar().getStatusContas().name(), response.getStatusContas());
        assertEquals(dadosContasPagar().getFornecedor().getNome(), response.getNomeFornecedor());

        verify(contasPagarRepository, times(1)).findById(modelContasPagar.getId());
        verify(contasPagarRepository, times(1)).save(any(ModelContasPagar.class));
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