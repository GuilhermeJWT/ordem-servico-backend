package br.com.systemsgs.ordem_servico_backend;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelItensVendasDTO;
import br.com.systemsgs.ordem_servico_backend.dto.request.ModelUserDTO;
import br.com.systemsgs.ordem_servico_backend.enums.FormaPagamento;
import br.com.systemsgs.ordem_servico_backend.enums.Status;
import br.com.systemsgs.ordem_servico_backend.enums.StatusContas;
import br.com.systemsgs.ordem_servico_backend.enums.TipoPessoa;
import br.com.systemsgs.ordem_servico_backend.model.*;
import lombok.Getter;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ActiveProfiles(value = "test")
@Getter
public class ConfigDadosEstaticosEntidades {

    public List<String> mensagemErro(){
        List<String> msgErro = new ArrayList<>();

        String clienteNaoEncontrado = "Cliente não Encontrado!";
        String recursoNaoEncontrado = "Recurso não Encontrado!";
        String vendaNaoEncontrada = "Venda não Encontrada!";
        String tecnicoNaoEncontrado = "Técnico Responsavel não Encontrado!";
        String camposDuplicados = "Campos já cadastrados na base de dados, Por Favor, Informe outros!";
        String contaNaoEncontrada = "Conta não Encontrada!";
        String fornecedorNaoEncontrado = "Fornecedor não Encontrado!";
        String metodoHttpNaoSuportado = "Tipo de solicitação HTTP incorreta, reveja qual o tipo correto: 'GET' 'POST' 'PUT' 'DELETE' ou outro!";

        msgErro.add(clienteNaoEncontrado);
        msgErro.add(recursoNaoEncontrado);
        msgErro.add(vendaNaoEncontrada);
        msgErro.add(tecnicoNaoEncontrado);
        msgErro.add(camposDuplicados);
        msgErro.add(contaNaoEncontrada);
        msgErro.add(fornecedorNaoEncontrado);
        msgErro.add(metodoHttpNaoSuportado);

        return msgErro;
    }

    public ModelVendas dadosVenda(){
        ModelVendas vendasResponse = new ModelVendas();

        vendasResponse.setIdVenda(1L);
        vendasResponse.setDataVenda(LocalDateTime.of(2024, Month.JULY, 16, 12, 57, 48, 123456789));
        vendasResponse.setTotalVenda(BigDecimal.valueOf(1000));
        vendasResponse.setDesconto(BigDecimal.valueOf(100));
        vendasResponse.setTotalItens(10);
        vendasResponse.setCliente(dadosClientes());
        vendasResponse.setTecnicoResponsavel(dadosTecnicoResponsavel());
        vendasResponse.setItens(dadosItensVendas());

        return vendasResponse;
    }

    public List<ModelItensVendas> dadosItensVendas(){
        List<Integer> quantidade = Arrays.asList(1, 25, 39);
        List<BigDecimal> valorProduto = Arrays.asList(BigDecimal.valueOf(125), BigDecimal.valueOf(358));
        List<Long> produto = Arrays.asList(1L, 2L, 3L);

        List<ModelItensVendas> responseItensVendas =
                Arrays.asList(new ModelItensVendas(quantidade, valorProduto, produto));

        return responseItensVendas;
    }

    public List<ModelItensVendasDTO> dadosItensVendasDTO(){
        Integer quantidade = 5;
        BigDecimal valorProduto = BigDecimal.valueOf(100);
        Long produto = 1L;

        List<ModelItensVendasDTO> responseItensVendas =
                Arrays.asList(new ModelItensVendasDTO(quantidade, valorProduto, produto));

        return responseItensVendas;
    }

    public ModelContasPagar dadosContasPagar(){
        ModelContasPagar contasPagarResponse = new ModelContasPagar();

        contasPagarResponse.setId(1L);
        contasPagarResponse.setData_vencimento(new Date("2024/11/25"));
        contasPagarResponse.setData_emissao(new Date("24/25/07"));
        contasPagarResponse.setValor(new BigDecimal(100));
        contasPagarResponse.setObservacao("Pagar o Fornecedor Guilherme");
        contasPagarResponse.setFormaPagamento(FormaPagamento.BOLETO);
        contasPagarResponse.setStatusContas(StatusContas.EM_ABERTO);
        contasPagarResponse.setFornecedor(dadosFornecedores());

        return contasPagarResponse;
    }

    public ModelContasReceber dadosContasReceber(){
        ModelContasReceber contasReceberResponse = new ModelContasReceber();

        contasReceberResponse.setId(1L);
        contasReceberResponse.setData_vencimento(new Date("2024/11/25"));
        contasReceberResponse.setValor(new BigDecimal(100));
        contasReceberResponse.setData_emissao(new Date("24/25/07"));
        contasReceberResponse.setObservacao("Receber uma compra de Equipamentos do Cliente Guilherme");
        contasReceberResponse.setFormaPagamento(FormaPagamento.CARTAO_CREDITO);
        contasReceberResponse.setStatusContasReceber(StatusContas.ATRASADA);
        contasReceberResponse.setCliente(dadosClientes());

        return contasReceberResponse;
    }

    public ModelFornecedor dadosFornecedores(){
        ModelFornecedor fornecedorResponse = new ModelFornecedor();

        fornecedorResponse.setId(1L);
        fornecedorResponse.setNome("Fornecedor Guilherme");
        fornecedorResponse.setNomeFantasia("Fornecedor Guilherme LTDA");
        fornecedorResponse.setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);
        fornecedorResponse.setCnpj("58.472.837/0001-13"); // gerado no site: Gerador de CNPJ
        fornecedorResponse.setEndereco(dadosEndereco());

        return fornecedorResponse;
    }

    public ModelClientes dadosClientes(){
        ModelClientes clienteResponse = new ModelClientes();

        clienteResponse.setId(1L);
        clienteResponse.setNome("Guilherme Santos");
        clienteResponse.setCpf("819.945.180-73"); //gerado no site: GERADOR DE CPF
        clienteResponse.setCelular("19 99999999");
        clienteResponse.setEmail("guilherme@gmail.com");
        clienteResponse.setEndereco(dadosEndereco());
        clienteResponse.setOrdemServicos(new ArrayList<>());

       return clienteResponse;
    }

    public ModelEndereco dadosEndereco(){
        ModelEndereco enderecoResponse = new ModelEndereco();

        enderecoResponse.setEndereco("Rua 1");
        enderecoResponse.setComplemento("Perto do Centro");
        enderecoResponse.setEstado("SP");
        enderecoResponse.setCidade("Caconde");
        enderecoResponse.setCep("13770-000");

        return enderecoResponse;
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

    public List<String> dadosDescricaoProdutos(){
        List<String> descricaoProdutosResponse =
                Arrays.asList("Notebook Gamer", "Capa para Celular Galaxy A55", "Carregador de Celular");

        return descricaoProdutosResponse;
    }
}
