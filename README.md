<H1>Ordem Serviço Backend</h1>
<p>Projeto simples contendo 36 endpoints para a Assistência Técnica:</p>
<ol>
  <li>Realizar vendas dos produtos da Assistência Técnica</li>
  <li>Cadastrar Ordens de Serviço</li>
  <li>Cadastro de Clientes e Produtos da Assistência Técnica</li>
  <li>Módulo de cadastro de Contas a Pagar e Receber</li>
</ol>
<H2>Tecnologias envolvidas no Projeto</h2>
<ol>
  <li>Java 17 - Spring Boot 3 - Porta 8081</li>
  <li>Pipeline CI/CD - Jenkins</li>
  <li>Qualidade do Código - SonarQube</li>
  <li>Cobertura de Testes - Junit/Mockito</li>
  <li>Observabilidade - Prometheus/Grafana e Spring Admin</li>
  <li>Deploy da imagem no Docker Hub e AWS EC2 + RDS</li>
  <li>Banco de Dados - PostgreSql</li>
  <li>Clone o projeto e acesse a documentação dos endpoints com Swagger: <a href="http://localhost:8081/swagger-ui/index.html#/">http://localhost:8081/swagger-ui/index.html#/</a></li>
</ol>
<H2>Versão antiga da Pipeline - Jenkins</h2>
<p>No diretório raiz do projeto, possui o arquivo: <b>Jenkinsfile</b>, contendo todos os Stages da Pipeline</p>
<img src="https://github.com/GuilhermeJWT/ordem-servico-backend/assets/63434009/7c7ccfef-7449-445c-9e7c-f0e02852571f)](https://github.com/GuilhermeJWT/ordem-servico-backend/assets/63434009/7c7ccfef-7449-445c-9e7c-f0e02852571f">
<H2>Monitoramento e Métricas: Prometheus/Grafana</H2>
<p>No arquivo <b>prometheus.yml</b> contem as configurações de conexão do Prometheus com a aplicação, em seguida o Grafana se conecta e monta o Dashboard: </p>
<ol>
  <li>Serviços rodando no Docker: <b>docker-compose.yml</b></li>
</ol>
<img src="https://github.com/GuilhermeJWT/ordem-servico-backend/assets/63434009/ca9fbdd9-5d3d-4c32-9a00-2a8fa6d9db6f">
<H2>Análise do Código com SonarQube:</H2>
<p>Foram realizados 2 testes, pré e pós a Cobertura de Testes:</p>
<ol>
  <li>Testes Unitários: Junit</li>
  <li>Mock de Integração: Mockito</li>
  <li>Analise de Cobertura: Jacoco</li>
</ol>
<img src="https://github.com/GuilhermeJWT/ordem-servico-backend/assets/63434009/7a494949-a264-4c13-b238-b88fc2cebb35">
