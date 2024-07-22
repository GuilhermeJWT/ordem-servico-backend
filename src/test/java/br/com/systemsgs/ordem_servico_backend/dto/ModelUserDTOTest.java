package br.com.systemsgs.ordem_servico_backend.dto;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelUserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class ModelUserDTOTest extends ConfigDadosEstaticosEntidades{

    ModelUserDTO modelUserDTO = new ModelUserDTO();
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @DisplayName("Testa Campos da entidade ModelUserDTO - JSON")
    @Test
    void testaDadosUsuariosJson() throws JsonProcessingException {

        String json = "{\n" +
                "    \"id\" : 1,\n" +
                "    \"nome\" : \"Guilherme\",\n" +
                "    \"email\" : \"guilherme@teste.com.br\",\n" +
                "    \"password\" : \"12345678\",\n" +
                "    \"endereco\" : \"Rua 1\",\n" +
                "    \"cidade\" : \"Caconde\",\n" +
                "    \"estado\" : \"SP\",\n" +
                "    \"cep\" : \"13770-000\"\n" +
                "}";

        ModelUserDTO dtoResponse = objectMapper.readValue(json, ModelUserDTO.class);

        assertNotNull(dtoResponse);

        assertEquals(dtoResponse.getId(), dadosUsuariosDTO().getId());
        assertEquals(dtoResponse.getNome(), dadosUsuariosDTO().getNome());
        assertEquals(dtoResponse.getEmail(), dadosUsuariosDTO().getEmail());
        assertEquals(dtoResponse.getPassword(), dadosUsuariosDTO().getPassword());
        assertEquals(dtoResponse.getEndereco(), dadosUsuariosDTO().getEndereco());
        assertEquals(dtoResponse.getCidade(), dadosUsuariosDTO().getCidade());
        assertEquals(dtoResponse.getEstado(), dadosUsuariosDTO().getEstado());
        assertEquals(dtoResponse.getCep(), dadosUsuariosDTO().getCep());
    }

    @DisplayName("Testa se existe os campos da entidade ModelUserDTO no Json")
    @Test
    void testCamposNoCorpoJson() throws JsonProcessingException {

        ModelUserDTO response = dadosUsuariosDTO();
        String json = objectMapper.writeValueAsString(response);

        assertNotNull(json);

        assertTrue(json.contains(dadosUsuariosDTO().getId().toString()));
        assertTrue(json.contains(dadosUsuariosDTO().getNome()));
        assertTrue(json.contains(dadosUsuariosDTO().getEmail()));
        assertTrue(json.contains(dadosUsuariosDTO().getPassword()));
        assertTrue(json.contains(dadosUsuariosDTO().getEndereco()));
        assertTrue(json.contains(dadosUsuariosDTO().getCidade()));
        assertTrue(json.contains(dadosUsuariosDTO().getEstado()));
        assertTrue(json.contains(dadosUsuariosDTO().getCep()));

    }

    private void startUser(){
        modelUserDTO = new ModelUserDTO(
                dadosUsuariosDTO().getId(),
                dadosUsuariosDTO().getNome(),
                dadosUsuariosDTO().getEmail(),
                dadosUsuariosDTO().getPassword(),
                dadosUsuariosDTO().getEndereco(),
                dadosUsuariosDTO().getCidade(),
                dadosUsuariosDTO().getEstado(),
                dadosUsuariosDTO().getCep()
        );
    }
}