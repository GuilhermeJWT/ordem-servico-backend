<H1>Ordem Serviço Backend</h1>
<p>Projeto simples contendo 39 endpoints para a Assistência Técnica:</p>
<ol>
  <li>Realizar Vendas dos produtos da Assistência Técnica</li>
  <li>Cadastrar Ordens de Serviço</li>
  <li>Cadastro de Clientes,Produtos, Fornecedores e Estoque da Assistência Técnica</li>
  <li>Módulo de cadastro de Contas a Pagar e Receber</li>
  <li>Notifica por E-mail Contas a Pagar e Receber vencidas</li>
</ol>
<H2>Tecnologias envolvidas no Projeto</h2>
<ol>
  <li>Java 17 - Spring Boot 3 - Porta 8081</li>
  <li>Pipeline CI/CD - Jenkins</li>
  <li>Qualidade do Código - SonarQube</li>
  <li>Cobertura de Testes - Junit/Mockito - 161 Testes</li>
  <li>Observabilidade - Prometheus/Grafana e Spring Admin</li>
  <li>Deploy da imagem no Docker Hub e AWS EC2 + RDS</li>
  <li>Banco de Dados - PostgreSql</li>
  <li>Clone o projeto e acesse a documentação dos endpoints com Swagger: <a href="http://localhost:8081/swagger-ui/index.html#/">http://localhost:8081/swagger-ui/index.html#/</a></li>
</ol>
<H2>Pipeline - Jenkins</h2>
<p>No diretório raiz do projeto, possui o arquivo: <b>Jenkinsfile</b>, contendo todos os Stages da Pipeline</p>
<img src="https://github.com/user-attachments/assets/440f5f55-f028-4b58-baf8-24c29f692f19">
<H2>Análise do Código com SonarQube & Quality Gate:</H2>
<ol>
  <li>Testes Unitários: Junit</li>
  <li>Mock de Integração: (Mocks) - Mockito</li>
  <li>Analise de Cobertura: Jacoco</li>
</ol>
<img src="https://github.com/user-attachments/assets/91d6f740-ae43-4c78-af29-3fecdf7d40ec">
<h3>Quality Gate:</h3>
<img src="https://github.com/user-attachments/assets/dec17218-6884-45b0-b394-3f037033df19">
<H2>Monitoramento e Métricas: Prometheus/Grafana</H2>
<p>No arquivo <b>prometheus.yml</b> contem as configurações de conexão do Prometheus com a aplicação, em seguida o Grafana se conecta e monta o Dashboard: </p>
<ol>
  <li>Serviços rodando no Docker: <b>docker-compose.yml</b></li>
</ol>
<img src="https://github.com/user-attachments/assets/2cfbec09-1ca0-415a-88ad-929e5466640b">
