package br.com.systemsgs.ordem_servico_backend;

import br.com.systemsgs.ordem_servico_backend.dto.ModelUserDTO;
import br.com.systemsgs.ordem_servico_backend.enums.Status;
import br.com.systemsgs.ordem_servico_backend.model.ModelClientes;
import br.com.systemsgs.ordem_servico_backend.model.ModelOrdemServico;
import br.com.systemsgs.ordem_servico_backend.model.ModelProdutos;
import br.com.systemsgs.ordem_servico_backend.model.ModelTecnicoResponsavel;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class ConfigDadosEstaticosEntidades {

    public List<String> mensagemErro(){
        List<String> msgErro = new ArrayList<>();

        String clienteNaoEncontrado = "Cliente não Encontrado!";
        String recursoNaoEncontrado = "Recurso não Encontrado!";

        msgErro.add(clienteNaoEncontrado);
        msgErro.add(recursoNaoEncontrado);

        return msgErro;
    }

    public ModelClientes dadosClientes(){
        ModelClientes clienteResponse = new ModelClientes();

        clienteResponse.setId(1L);
        clienteResponse.setNome("Guilherme Santos");
        clienteResponse.setCpf("819.945.180-73"); //gerado no GERADOR DE CPF
        clienteResponse.setCelular("19 99999999");
        clienteResponse.setEmail("guilherme@gmail.com");
        clienteResponse.setEndereco("Rua 1");
        clienteResponse.setCidade("Caconde");
        clienteResponse.setEstado("SP");
        clienteResponse.setCep("13770-000");
        clienteResponse.setOrdemServicos(new ArrayList<>());

       return clienteResponse;
    }

    public ModelProdutos dadosProdutos(){
        ModelProdutos produtoResponse = new ModelProdutos();

        produtoResponse.setId(1L);
        produtoResponse.setDescricao("Notebook Gamer");
        produtoResponse.setQuantidade(5);
        produtoResponse.setQuantidade_minima(1);
        produtoResponse.setPreco_compra(BigDecimal.valueOf(1000L));
        produtoResponse.setPreco_venda(BigDecimal.valueOf(2000L));
        produtoResponse.setCodigo_barras("789835741123");

        return produtoResponse;
    }

    public ModelTecnicoResponsavel dadosTecnicoResponsavel(){
        ModelTecnicoResponsavel tecnicoResponse = new ModelTecnicoResponsavel();

        tecnicoResponse.setId(1L);
        tecnicoResponse.setNome("Guilherme Técnico");

        return tecnicoResponse;
    }

    public ModelOrdemServico dadosOrdemServico(){
        ModelOrdemServico ordemServicoResponse = new ModelOrdemServico();

        ordemServicoResponse.setId(1L);
        ordemServicoResponse.setDefeito("Trocar a tela");
        ordemServicoResponse.setDescricao("Descricao OS");
        ordemServicoResponse.setLaudo_tecnico("Precisa trocar a tela");
        ordemServicoResponse.setStatus(Status.ORCAMENTO);
        ordemServicoResponse.setData_inicial(new Date("2024/06/21"));
        ordemServicoResponse.setData_final(new Date("2024/06/22"));
        ordemServicoResponse.setCliente(dadosClientes());
        ordemServicoResponse.setTecnicoResponsavel(dadosTecnicoResponsavel());

        return ordemServicoResponse;
    }

    public ModelUserDTO dadosUsuariosDTO(){
        ModelUserDTO userResponse = new ModelUserDTO();

        userResponse.setId(1L);
        userResponse.setNome("Guilherme");
        userResponse.setEmail("guilherme@teste.com.br");
        userResponse.setPassword("12345678");
        userResponse.setEndereco("Rua 1");
        userResponse.setCidade("Caconde");
        userResponse.setEstado("SP");
        userResponse.setCep("13770-000");

        return userResponse;
    }



}
