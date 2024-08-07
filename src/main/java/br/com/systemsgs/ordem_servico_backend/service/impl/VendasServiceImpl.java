package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.request.ModelVendasDTO;
import br.com.systemsgs.ordem_servico_backend.dto.response.VendasResponse;
import br.com.systemsgs.ordem_servico_backend.model.ModelItensVendas;
import br.com.systemsgs.ordem_servico_backend.model.ModelVendas;
import br.com.systemsgs.ordem_servico_backend.repository.VendasRepository;
import br.com.systemsgs.ordem_servico_backend.service.VendaService;
import br.com.systemsgs.ordem_servico_backend.util.UtilClientes;
import br.com.systemsgs.ordem_servico_backend.util.UtilProdutos;
import br.com.systemsgs.ordem_servico_backend.util.UtilTecnicoResponsavel;
import br.com.systemsgs.ordem_servico_backend.util.UtilVendas;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "vendas")
@Service
public class VendasServiceImpl implements VendaService {

    @Autowired
    private VendasRepository vendasRepository;

    @Autowired
    private UtilVendas utilVendas;

    @Autowired
    private UtilTecnicoResponsavel utilTecnicoResponsavel;

    @Autowired
    private UtilClientes utilClientes;

    @Autowired
    private UtilProdutos utilProdutos;

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
        modelItensVendas.setProduto(produtos.stream().map(p -> p.getId()).collect(Collectors.toList()));
        modelItensVendas.setQuantidade(produtos.stream().map(p -> p.getQuantidade()).collect(Collectors.toList()));
        modelItensVendas.setValorProduto(produtos.stream().map(p -> p.getPreco_venda()).collect(Collectors.toList()));

        var vendaSalva = vendasRepository.save(modelVendas);

        return vendaSalva;
    }

    @Cacheable(value = "vendas", key = "#id")
    @Override
    public VendasResponse pesquisaVendaPorId(Long id) {
        VendasResponse vendasResponse = new VendasResponse();

        var pesquisaVenda = utilVendas.pesquisarVendaPeloId(id);

        vendasResponse.setIdVenda(pesquisaVenda.getIdVenda());
        vendasResponse.setTotalItens(pesquisaVenda.getTotalItens());
        vendasResponse.setTotalVenda(pesquisaVenda.getTotalVenda());
        vendasResponse.setDataVenda(pesquisaVenda.getDataVenda());
        vendasResponse.setDesconto(pesquisaVenda.getDesconto());
        vendasResponse.setNomeCliente(pesquisaVenda.getCliente().getNome());
        vendasResponse.setNomeTecnicoResponsavel(pesquisaVenda.getTecnicoResponsavel().getNome());
        vendasResponse.setDescricaoProdutos(pegaDescricaoPedidos(pesquisaVenda));

        return vendasResponse;
    }

    private BigDecimal calculaTotalVenda(ModelVendasDTO modelVendasDTO) {
        return modelVendasDTO.getItens().stream().
                map(itens -> itens.getValorProduto().multiply(BigDecimal.valueOf(itens.getQuantidade())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private Integer calculaTotalItens(ModelVendasDTO modelVendasDTO) {
        return modelVendasDTO.getItens().stream().mapToInt(p -> p.getQuantidade()).sum();
    }

    private List<ModelItensVendas> itensVenda(ModelVendasDTO modelVendasDTO){
        var quantidade = modelVendasDTO.getItens().stream().map(q -> q.getQuantidade()).collect(Collectors.toList());
        var valorProduto = modelVendasDTO.getItens().stream().map(v -> v.getValorProduto()).collect(Collectors.toList());
        var produto = modelVendasDTO.getItens().stream().map(p -> p.getId_produto()).collect(Collectors.toList());

        List<Long> listaIds = produto;
        List<ModelItensVendas> itensVendas = Arrays.asList(new ModelItensVendas(quantidade, valorProduto, listaIds));

        return itensVendas;
    }

    private List<String> pegaDescricaoPedidos(ModelVendas modelVendas){
        var idsProdutos = modelVendas.getItens().stream().map(p -> p.getProduto());

        List<Long> listIds = idsProdutos.reduce(new ArrayList<>() , (integer, longs) -> longs);

        return utilProdutos.pesquisaDescricaoProdutosPorIds(listIds);
    }
}
