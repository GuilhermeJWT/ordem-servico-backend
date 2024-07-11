package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.ModelVendasDTO;
import br.com.systemsgs.ordem_servico_backend.dto.VendasResponseDTO;
import br.com.systemsgs.ordem_servico_backend.model.ModelItensVendas;
import br.com.systemsgs.ordem_servico_backend.model.ModelVendas;
import br.com.systemsgs.ordem_servico_backend.repository.ItensVendaRepository;
import br.com.systemsgs.ordem_servico_backend.repository.VendasRepository;
import br.com.systemsgs.ordem_servico_backend.service.VendaService;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import br.com.systemsgs.ordem_servico_backend.util.UtilProdutos;
import br.com.systemsgs.ordem_servico_backend.util.UtilTecnicoResponsavel;
import br.com.systemsgs.ordem_servico_backend.util.UtilVendas;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendasServiceImpl implements VendaService {

    @Autowired
    private VendasRepository vendasRepository;

    @Autowired
    private ItensVendaRepository itensVendaRepository;

    @Autowired
    private UtilVendas utilVendas;

    @Autowired
    private UtilTecnicoResponsavel utilTecnicoResponsavel;

    @Autowired
    private UtilClientes utilClientes;

    @Autowired
    private UtilProdutos utilProdutos;

    /*Salva uma Venda, pegando o Cliente, Técnico Responsavel, os Produtos e calculando o Valor total + Itens do Pedido*/
    @Transactional
    @Override
    public ModelVendas salvarVenda(ModelVendasDTO modelVendasDTO) {
        ModelVendas modelVendas = new ModelVendas();
        ModelItensVendas modelItensVendas = new ModelItensVendas();

        var cliente = utilClientes.pesquisarClientePeloId(modelVendasDTO.getIdCliente());
        var tecnico = utilTecnicoResponsavel.pesquisarTecnicoPeloId(modelVendasDTO.getIdTecnicoResponsavel());

        var ids = modelVendasDTO.getItens().stream().map(p -> p.getId_produto()).collect(Collectors.toList());
        var produtos = utilProdutos.pesquisaListaProdutosPorIds(ids);

        modelVendas.setCliente(cliente);
        modelVendas.setTecnicoResponsavel(tecnico);
        modelVendas.setDesconto(modelVendasDTO.getDesconto());
        modelVendas.setTotalVenda(calculaTotalVenda(modelVendasDTO));
        modelVendas.setTotalItens(calculaTotalItens(modelVendasDTO));
        modelVendas.setItens(itensVenda(modelVendasDTO));

        var vendaSalva = vendasRepository.save(modelVendas);

        modelItensVendas.setIdVenda(vendaSalva.getIdVenda());
        modelItensVendas.setProduto(produtos.stream().map(p -> p.getId()).collect(Collectors.toList()));
        modelItensVendas.setQuantidade(produtos.stream().map(p -> p.getQuantidade()).collect(Collectors.toList()));
        modelItensVendas.setValorProduto(produtos.stream().map(p -> p.getPreco_venda()).collect(Collectors.toList()));

        itensVendaRepository.save(modelItensVendas);

        return vendaSalva;
    }

    @Override
    public VendasResponseDTO pesquisaVendaPorId(Long id) {
        VendasResponseDTO vendasResponseDTO = new VendasResponseDTO();

        var pesquisaVenda = utilVendas.pesquisarVendaPeloId(id);

        vendasResponseDTO.setIdVenda(pesquisaVenda.getIdVenda());
        vendasResponseDTO.setTotalItens(pesquisaVenda.getTotalItens());
        vendasResponseDTO.setTotalVenda(pesquisaVenda.getTotalVenda());
        vendasResponseDTO.setDataVenda(pesquisaVenda.getDataVenda());
        vendasResponseDTO.setDesconto(pesquisaVenda.getDesconto());
        vendasResponseDTO.setNomeCliente(pesquisaVenda.getCliente().getNome());
        vendasResponseDTO.setNomeTecnicoResponsavel(pesquisaVenda.getTecnicoResponsavel().getNome());
        vendasResponseDTO.setDescricaoProdutos(pegaDescricaoPedidos(pesquisaVenda));

        return vendasResponseDTO;
    }

    /*Multiplicando o Preço com a Quantidade para gerar o Valor Total do Pedido - Caso não tenha retorna 0*/
    private BigDecimal calculaTotalVenda(ModelVendasDTO modelVendasDTO) {
        return modelVendasDTO.getItens().stream().
                map(itens -> itens.getValorProduto().multiply(BigDecimal.valueOf(itens.getQuantidade())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /*Multiplicando o Total de Itens no Pedido de Venda 0*/
    private Integer calculaTotalItens(ModelVendasDTO modelVendasDTO) {
        return modelVendasDTO.getItens().stream().mapToInt(p -> p.getQuantidade()).sum();
    }

    /*Pega os Itens da Venda*/
    private List<ModelItensVendas> itensVenda(ModelVendasDTO modelVendasDTO){
        var quantidade = modelVendasDTO.getItens().stream().map(q -> q.getQuantidade()).collect(Collectors.toList());
        var valorProduto = modelVendasDTO.getItens().stream().map(v -> v.getValorProduto()).collect(Collectors.toList());
        var produto = modelVendasDTO.getItens().stream().map(p -> p.getId_produto()).collect(Collectors.toList());

        List<Long> listaIds = produto;
        List<ModelItensVendas> itensVendas = Arrays.asList(new ModelItensVendas(quantidade, valorProduto, listaIds));

        return itensVendas;
    }

    /*Pega a Descrição dos Produtos de uma Venda*/
    private List<String> pegaDescricaoPedidos(ModelVendas modelVendas){
        var idsProdutos = modelVendas.getItens().stream().map(p -> p.getProduto());

        List<Long> listIds = idsProdutos.reduce(new ArrayList<>() , (integer, longs) -> longs);

        return utilProdutos.pesquisaDescricaoProdutosPorIds(listIds);
    }
}
