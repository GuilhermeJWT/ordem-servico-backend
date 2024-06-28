package br.com.systemsgs.ordem_servico_backend.dto;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ModelUserDTOTest {

    ModelUserDTO modelUserDTO = new ModelUserDTO();
    ObjectMapper objectMapper = new ObjectMapper();

    ConfigDadosEstaticosEntidades getDadosUser = new ConfigDadosEstaticosEntidades();

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
                "    \"cep\" : \"13770-000\",\n" +
                "    \"roles\" : \"ROLE_ADMIN\" \n" +
                "}";

        ModelUserDTO dtoResponse = objectMapper.readValue(json, ModelUserDTO.class);

        assertNotNull(dtoResponse);

        assertEquals(dtoResponse.getId(), getDadosUser.dadosUsuariosDTO().getId());
        assertEquals(dtoResponse.getNome(), getDadosUser.dadosUsuariosDTO().getNome());
        assertEquals(dtoResponse.getEmail(), getDadosUser.dadosUsuariosDTO().getEmail());
        assertEquals(dtoResponse.getPassword(), getDadosUser.dadosUsuariosDTO().getPassword());
        assertEquals(dtoResponse.getEndereco(), getDadosUser.dadosUsuariosDTO().getEndereco());
        assertEquals(dtoResponse.getCidade(), getDadosUser.dadosUsuariosDTO().getCidade());
        assertEquals(dtoResponse.getEstado(), getDadosUser.dadosUsuariosDTO().getEstado());
        assertEquals(dtoResponse.getCep(), getDadosUser.dadosUsuariosDTO().getCep());
        assertEquals(dtoResponse.getRoles(), getDadosUser.dadosUsuariosDTO().getRoles());
    }

    @DisplayName("Testa se existe os campos da entidade ModelUserDTO no Json")
    @Test
    void testCamposNoCorpoJson() throws JsonProcessingException {

        ModelUserDTO response = getDadosUser.dadosUsuariosDTO();
        String json = objectMapper.writeValueAsString(response);

        assertNotNull(json);

        assertTrue(json.contains(getDadosUser.dadosUsuariosDTO().getId().toString()));
        assertTrue(json.contains(getDadosUser.dadosUsuariosDTO().getNome()));
        assertTrue(json.contains(getDadosUser.dadosUsuariosDTO().getEmail()));
        assertTrue(json.contains(getDadosUser.dadosUsuariosDTO().getPassword()));
        assertTrue(json.contains(getDadosUser.dadosUsuariosDTO().getEndereco()));
        assertTrue(json.contains(getDadosUser.dadosUsuariosDTO().getCidade()));
        assertTrue(json.contains(getDadosUser.dadosUsuariosDTO().getEstado()));
        assertTrue(json.contains(getDadosUser.dadosUsuariosDTO().getCep()));
        assertTrue(json.contains(getDadosUser.dadosUsuariosDTO().getRoles().name()));

    }

    private void startUser(){
        modelUserDTO = new ModelUserDTO(
                getDadosUser.dadosUsuariosDTO().getId(),
                getDadosUser.dadosUsuariosDTO().getNome(),
                getDadosUser.dadosUsuariosDTO().getEmail(),
                getDadosUser.dadosUsuariosDTO().getPassword(),
                getDadosUser.dadosUsuariosDTO().getEndereco(),
                getDadosUser.dadosUsuariosDTO().getCidade(),
                getDadosUser.dadosUsuariosDTO().getEstado(),
                getDadosUser.dadosUsuariosDTO().getCep(),
                getDadosUser.dadosUsuariosDTO().getRoles()
        );
    }
}