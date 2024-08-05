package br.com.systemsgs.ordem_servico_backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Ordem Serviço Backend",
        description = "Documentação dos endpoints",
        version = "1.0.0"))
public class SwaggerConfiguration {

    public static final String TAG_API_CLIENTES = "Api de Clientes - V1";
    public static final String TAG_API_CONTAS_PAGAR = "Api de Contas a Pagar - V1";
    public static final String TAG_API_CONTAS_RECEBER = "Api de Contas a Receber - V1";
    public static final String TAG_API_DASHBOARD = "Api dados do Dashboard - V1";
    public static final String TAG_API_FORNECEDOR = "Api de Fornecedores - V1";
    public static final String TAG_API_ORDEM_SERVICO = "Api de Ordem de Serviço - V1";
    public static final String TAG_API_PRODUTOS = "Api de Produtos - V1";
    public static final String TAG_API_VENDAS = "Api de Vendas da Assistência Técnica - V1";

}
