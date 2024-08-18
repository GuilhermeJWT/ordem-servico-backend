package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelFornecedorDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.FornecedorNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelFornecedor;
import br.com.systemsgs.ordem_servico_backend.repository.FornecedoresRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class FornecedorServiceImplTest extends ConfigDadosEstaticosEntidades {

    private ModelFornecedor modelFornecedor;
    private ModelFornecedorDTO modelFornecedorDTO;
    private Optional<ModelFornecedor> modelFornecedorOptional;

    @InjectMocks
    private FornecedorServiceImpl fornecedorServiceImpl;

    @Mock
    private FornecedoresRepository fornecedoresRepository;

    @Mock
    private UtilFornecedores utilFornecedores;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fornecedorServiceImpl = new FornecedorServiceImpl(fornecedoresRepository, utilFornecedores, mapper);
        startFornecedor();
    }

    @DisplayName("Pesquisa um Fornecedor por ID")
    @Test
    void pesquisaFornecedorPorId() {
        when(fornecedoresRepository.findById(modelFornecedor.getId())).thenReturn(modelFornecedorOptional);

        ModelFornecedor response = fornecedorServiceImpl.pesquisaPorId(modelFornecedor.getId());

        assertNotNull(response);

        assertEquals(dadosFornecedores().getId(), response.getId());
        assertEquals(dadosFornecedores().getNome(), response.getNome());
        assertEquals(dadosFornecedores().getNomeFantasia(), response.getNomeFantasia());
        assertEquals(dadosFornecedores().getTipoPessoa(), response.getTipoPessoa());
        assertEquals(dadosFornecedores().getCnpj(), response.getCnpj());
        assertEquals(dadosFornecedores().getEndereco().getEndereco(), response.getEndereco().getEndereco());
        assertEquals(dadosFornecedores().getEndereco().getComplemento(), response.getEndereco().getComplemento());
        assertEquals(dadosFornecedores().getEndereco().getCidade(), response.getEndereco().getCidade());
        assertEquals(dadosFornecedores().getEndereco().getEstado(), response.getEndereco().getEstado());
        assertEquals(dadosFornecedores().getEndereco().getCep(), response.getEndereco().getCep());
    }

    @DisplayName("Teste para retornar os Fornecedores Paginados")
    @Test
    void testListarFornecedoresPaginado() {
        var fornecedorList = Arrays.asList(modelFornecedor, modelFornecedor);
        var fornecedoresPage = new PageImpl<>(fornecedorList, PageRequest.of(0, 10), fornecedorList.size());

        when(fornecedoresRepository.findAll(PageRequest.of(0, 10))).thenReturn(fornecedoresPage);

        when(mapper.map(modelFornecedor, ModelFornecedorDTO.class)).thenReturn(modelFornecedorDTO);
        when(mapper.map(modelFornecedor, ModelFornecedorDTO.class)).thenReturn(modelFornecedorDTO);

        Page<ModelFornecedorDTO> response = fornecedorServiceImpl.listarFornecedoresPaginados(0, 10);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent().get(0)).isEqualTo(modelFornecedorDTO);
        assertThat(response.getContent().get(1)).isEqualTo(modelFornecedorDTO);

        assertEquals(dadosFornecedores().getId(), response.getContent().get(0).getId());
        assertEquals(dadosFornecedores().getNome(), response.getContent().get(0).getNome());
        assertEquals(dadosFornecedores().getNomeFantasia(), response.getContent().get(0).getNomeFantasia());
        assertEquals(dadosFornecedores().getTipoPessoa(), response.getContent().get(0).getTipoPessoa());
        assertEquals(dadosFornecedores().getCnpj(), response.getContent().get(0).getCnpj());

        assertEquals(dadosFornecedores().getEndereco().getEndereco(), response.getContent().get(0).getEndereco().getEndereco());
        assertEquals(dadosFornecedores().getEndereco().getComplemento(), response.getContent().get(0).getEndereco().getComplemento());
        assertEquals(dadosFornecedores().getEndereco().getCidade(), response.getContent().get(0).getEndereco().getCidade());
        assertEquals(dadosFornecedores().getEndereco().getEstado(), response.getContent().get(0).getEndereco().getEstado());
        assertEquals(dadosFornecedores().getEndereco().getCep(), response.getContent().get(0).getEndereco().getCep());
    }

    @DisplayName("Teste para Paginação de Fornecedores vazia - Paginação sem dados")
    @Test
    void listarFornecedoresPaginadoComPaginaVazia() {
        Page<ModelFornecedor> fornecedoresPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);

        when(fornecedoresRepository.findAll(PageRequest.of(0, 10))).thenReturn(fornecedoresPage);

        Page<ModelFornecedorDTO> response = fornecedorServiceImpl.listarFornecedoresPaginados(0, 10);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).isEmpty();
    }

    @DisplayName("Pesquisa um Fornecedor Inexistente por ID - retorna 404")
    @Test
    void pesquisaFornecedorInexistenteRetornaNotFound(){
        when(fornecedoresRepository.findById(dadosFornecedores().getId())).thenThrow(new FornecedorNaoEncontradoException());

        try{
            fornecedorServiceImpl.pesquisaPorId(dadosFornecedores().getId());
        }catch (Exception exception){
            assertEquals(FornecedorNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(6), exception.getMessage());
        }
    }

    @DisplayName("Retorna uma lista de Fornecedores")
    @Test
    void listarFornecedores() {
        when(fornecedoresRepository.findAll()).thenReturn(List.of(modelFornecedor));

        List<ModelFornecedor> response = fornecedorServiceImpl.listarFornecedores();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ModelFornecedor.class, response.get(0).getClass());

        assertEquals(dadosFornecedores().getId(), response.get(0).getId());
        assertEquals(dadosFornecedores().getNome(), response.get(0).getNome());
        assertEquals(dadosFornecedores().getNomeFantasia(), response.get(0).getNomeFantasia());
        assertEquals(dadosFornecedores().getTipoPessoa(), response.get(0).getTipoPessoa());
        assertEquals(dadosFornecedores().getCnpj(), response.get(0).getCnpj());
        assertEquals(dadosFornecedores().getEndereco().getEndereco(), response.get(0).getEndereco().getEndereco());
        assertEquals(dadosFornecedores().getEndereco().getComplemento(), response.get(0).getEndereco().getComplemento());
        assertEquals(dadosFornecedores().getEndereco().getCidade(), response.get(0).getEndereco().getCidade());
        assertEquals(dadosFornecedores().getEndereco().getEstado(), response.get(0).getEndereco().getEstado());
        assertEquals(dadosFornecedores().getEndereco().getCep(), response.get(0).getEndereco().getCep());
    }

    @DisplayName("Salva um Fornecedor")
    @Test
    void salvarFornecedor() {
        when(fornecedoresRepository.save(modelFornecedor)).thenReturn(modelFornecedor);
        when(mapper.map(modelFornecedorDTO, ModelFornecedor.class)).thenReturn(modelFornecedor);

        ModelFornecedor response = fornecedorServiceImpl.salvarFornecedor(modelFornecedorDTO);

        assertNotNull(response);

        assertEquals(dadosFornecedores().getId(), response.getId());
        assertEquals(dadosFornecedores().getNome(), response.getNome());
        assertEquals(dadosFornecedores().getNomeFantasia(), response.getNomeFantasia());
        assertEquals(dadosFornecedores().getTipoPessoa(), response.getTipoPessoa());
        assertEquals(dadosFornecedores().getCnpj(), response.getCnpj());
        assertEquals(dadosFornecedores().getEndereco().getEndereco(), response.getEndereco().getEndereco());
        assertEquals(dadosFornecedores().getEndereco().getComplemento(), response.getEndereco().getComplemento());
        assertEquals(dadosFornecedores().getEndereco().getCidade(), response.getEndereco().getCidade());
        assertEquals(dadosFornecedores().getEndereco().getEstado(), response.getEndereco().getEstado());
        assertEquals(dadosFornecedores().getEndereco().getCep(), response.getEndereco().getCep());
    }

    @DisplayName("Deleta um Fornecedor por ID")
    @Test
    void deletarFornecedor() {
        doNothing().when(fornecedoresRepository).deleteById(dadosFornecedores().getId());

        fornecedorServiceImpl.deletarFornecedor(dadosFornecedores().getId());
        verify(fornecedoresRepository, times(1)).deleteById(dadosFornecedores().getId());
    }

    @DisplayName("Atualiza um Fornecedor pelo ID e Entidade")
    @Test
    void updateFornecedor() {
        when(fornecedoresRepository.save(modelFornecedor)).thenReturn(modelFornecedor);
        when(utilFornecedores.pesquisarFornecedorPeloId(dadosFornecedores().getId())).thenReturn(modelFornecedor);

        ModelFornecedor response = fornecedorServiceImpl
                .updateFornecedor(dadosFornecedores().getId(), modelFornecedorDTO);

        assertNotNull(response);

        assertEquals(dadosFornecedores().getId(), response.getId());
        assertEquals(dadosFornecedores().getNome(), response.getNome());
        assertEquals(dadosFornecedores().getNomeFantasia(), response.getNomeFantasia());
        assertEquals(dadosFornecedores().getTipoPessoa(), response.getTipoPessoa());
        assertEquals(dadosFornecedores().getCnpj(), response.getCnpj());
        assertEquals(dadosFornecedores().getEndereco().getEndereco(), response.getEndereco().getEndereco());
        assertEquals(dadosFornecedores().getEndereco().getComplemento(), response.getEndereco().getComplemento());
        assertEquals(dadosFornecedores().getEndereco().getCidade(), response.getEndereco().getCidade());
        assertEquals(dadosFornecedores().getEndereco().getEstado(), response.getEndereco().getEstado());
        assertEquals(dadosFornecedores().getEndereco().getCep(), response.getEndereco().getCep());
    }

    private void startFornecedor(){
        modelFornecedor = new ModelFornecedor(
            dadosFornecedores().getId(),
            dadosFornecedores().getNome(),
            dadosFornecedores().getNomeFantasia(),
            dadosFornecedores().getTipoPessoa(),
            dadosFornecedores().getCnpj(),
            dadosEndereco()
        );
        modelFornecedorDTO = new ModelFornecedorDTO(
                dadosFornecedores().getId(),
                dadosFornecedores().getNome(),
                dadosFornecedores().getNomeFantasia(),
                dadosFornecedores().getTipoPessoa(),
                dadosFornecedores().getCnpj(),
                dadosEndereco()
        );
        modelFornecedorOptional = Optional.of(new ModelFornecedor(
                dadosFornecedores().getId(),
                dadosFornecedores().getNome(),
                dadosFornecedores().getNomeFantasia(),
                dadosFornecedores().getTipoPessoa(),
                dadosFornecedores().getCnpj(),
                dadosEndereco()
        ));
    }
}