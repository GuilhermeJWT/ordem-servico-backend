package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelFornecedorDTO;
import br.com.systemsgs.ordem_servico_backend.exception.errors.FornecedorNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelFornecedor;
import br.com.systemsgs.ordem_servico_backend.repository.FornecedoresRepository;
import br.com.systemsgs.ordem_servico_backend.service.FornecedorService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class FornecedorControllerTest extends ConfigDadosEstaticosEntidades {

    private ModelFornecedor modelFornecedor;
    private ModelFornecedorDTO modelFornecedorDTO;

    @InjectMocks
    private FornecedorController fornecedorController;

    @Mock
    private FornecedorService fornecedorService;

    @Mock
    private FornecedoresRepository fornecedoresRepository;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startFornecedor();
    }

    @DisplayName("Teste para retornar a lista de Fornecedores - retorna 200")
    @Test
    void listarFornecedores() {
        when(fornecedorService.listarFornecedores()).thenReturn(List.of(modelFornecedor));
        when(mapper.map(any(), any())).thenReturn(modelFornecedorDTO);

        ResponseEntity<List<ModelFornecedorDTO>> response = fornecedorController.listarFornecedores();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(ModelFornecedorDTO.class, response.getBody().get(0).getClass());

        assertEquals(dadosFornecedores().getId(), response.getBody().get(0).getId());
        assertEquals(dadosFornecedores().getNome(), response.getBody().get(0).getNome());
        assertEquals(dadosFornecedores().getNomeFantasia(), response.getBody().get(0).getNomeFantasia());
        assertEquals(dadosFornecedores().getTipoPessoa(), response.getBody().get(0).getTipoPessoa());
        assertEquals(dadosFornecedores().getCnpj(), response.getBody().get(0).getCnpj());

        assertEquals(dadosFornecedores().getEndereco().getEndereco(), response.getBody().get(0).getEndereco().getEndereco());
        assertEquals(dadosFornecedores().getEndereco().getComplemento(), response.getBody().get(0).getEndereco().getComplemento());
        assertEquals(dadosFornecedores().getEndereco().getCidade(), response.getBody().get(0).getEndereco().getCidade());
        assertEquals(dadosFornecedores().getEndereco().getEstado(), response.getBody().get(0).getEndereco().getEstado());
        assertEquals(dadosFornecedores().getEndereco().getCep(), response.getBody().get(0).getEndereco().getCep());
    }

    @DisplayName("Pesquisa um Fornecedor pelo ID e testa o Retorno do Body - retorna 200")
    @Test
    void pesquisarFornecedorPorId() {
        when(fornecedorService.pesquisaPorId(anyLong())).thenReturn(modelFornecedor);
        when(mapper.map(any(), any())).thenReturn(modelFornecedorDTO);

        ResponseEntity<ModelFornecedorDTO> response =
                fornecedorController.pesquisarPorId(dadosFornecedores().getId());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelFornecedorDTO.class, response.getBody().getClass());

        assertEquals(dadosFornecedores().getId(), response.getBody().getId());
        assertEquals(dadosFornecedores().getNome(), response.getBody().getNome());
        assertEquals(dadosFornecedores().getNomeFantasia(), response.getBody().getNomeFantasia());
        assertEquals(dadosFornecedores().getTipoPessoa(), response.getBody().getTipoPessoa());
        assertEquals(dadosFornecedores().getCnpj(), response.getBody().getCnpj());

        assertEquals(dadosFornecedores().getEndereco().getEndereco(), response.getBody().getEndereco().getEndereco());
        assertEquals(dadosFornecedores().getEndereco().getComplemento(), response.getBody().getEndereco().getComplemento());
        assertEquals(dadosFornecedores().getEndereco().getCidade(), response.getBody().getEndereco().getCidade());
        assertEquals(dadosFornecedores().getEndereco().getEstado(), response.getBody().getEndereco().getEstado());
        assertEquals(dadosFornecedores().getEndereco().getCep(), response.getBody().getEndereco().getCep());
    }

    @DisplayName("Pesquisa um Fornecedor inexistente e retorna 404")
    @Test
    void pesquisaFornecedorInexistenteRetorna404(){
        when(fornecedoresRepository.findById(anyLong())).thenThrow(new FornecedorNaoEncontradoException());

        try{
            fornecedorService.pesquisaPorId(dadosFornecedores().getId());
        }catch (Exception exception){
            assertEquals(FornecedorNaoEncontradoException.class, exception.getClass());
            assertEquals(mensagemErro().get(6), exception.getMessage());
        }
    }

    @DisplayName("Salva um Fornecedor e retorna 201")
    @Test
    void salvarFornecedores() {
        when(fornecedorService.salvarFornecedor(modelFornecedorDTO)).thenReturn(modelFornecedor);

        ResponseEntity<ModelFornecedorDTO> response =
                fornecedorController.salvarFornecedores(modelFornecedorDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @DisplayName("Atualiza um Fornecedor e retorna 200")
    @Test
    void atualizarFornecedores() {
        when(fornecedorService.updateFornecedor(dadosFornecedores().getId(),modelFornecedorDTO))
                .thenReturn(modelFornecedor);

        when(mapper.map(any(), any())).thenReturn(modelFornecedorDTO);

        ResponseEntity<ModelFornecedorDTO> response =
                fornecedorController.atualizarFornecedores(dadosFornecedores().getId(), modelFornecedorDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ModelFornecedorDTO.class, response.getBody().getClass());

        assertEquals(dadosFornecedores().getId(), response.getBody().getId());
        assertEquals(dadosFornecedores().getNome(), response.getBody().getNome());
        assertEquals(dadosFornecedores().getNomeFantasia(), response.getBody().getNomeFantasia());
        assertEquals(dadosFornecedores().getTipoPessoa(), response.getBody().getTipoPessoa());
        assertEquals(dadosFornecedores().getCnpj(), response.getBody().getCnpj());

        assertEquals(dadosFornecedores().getEndereco().getEndereco(), response.getBody().getEndereco().getEndereco());
        assertEquals(dadosFornecedores().getEndereco().getComplemento(), response.getBody().getEndereco().getComplemento());
        assertEquals(dadosFornecedores().getEndereco().getCidade(), response.getBody().getEndereco().getCidade());
        assertEquals(dadosFornecedores().getEndereco().getEstado(), response.getBody().getEndereco().getEstado());
        assertEquals(dadosFornecedores().getEndereco().getCep(), response.getBody().getEndereco().getCep());
    }

    @DisplayName("Deleta um Fornecedor e retorna 204")
    @Test
    void delete() {
        doNothing().when(fornecedorService).deletarFornecedor(dadosFornecedores().getId());

        ResponseEntity<ModelFornecedorDTO> response = fornecedorController.delete(dadosFornecedores().getId());

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(fornecedorService, times(1)).deletarFornecedor(dadosFornecedores().getId());
    }

    private void startFornecedor(){
        modelFornecedor = new ModelFornecedor(
          dadosFornecedores().getId(),
          dadosFornecedores().getNome(),
          dadosFornecedores().getNomeFantasia(),
          dadosFornecedores().getTipoPessoa(),
          dadosFornecedores().getCnpj(),
          dadosFornecedores().getEndereco()
        );
        modelFornecedorDTO = new ModelFornecedorDTO(
                dadosFornecedores().getId(),
                dadosFornecedores().getNome(),
                dadosFornecedores().getNomeFantasia(),
                dadosFornecedores().getTipoPessoa(),
                dadosFornecedores().getCnpj(),
                dadosFornecedores().getEndereco()
        );
    }
}