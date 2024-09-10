<H1>Código Fonte - Ordem de Serviço Backend</h1>
<p>Desenvolvimento de toda a parte de regra de negócio do Sistema para uma Assistência Técnica. <br/><br/>A parte visual será desenvolvida por um colega, todo o projeto será implantado para um cliente, foquei em todas as partes, desde o desenvolvimento até a implantação do projeto no servidor VPS e futuramente na AWS EC2 + RDS, o projeto está quase finalizado, contendo 39 endpoints para o estabelecimento, conforme foi solicitado pelo cliente, foquei muito nos testes para garantir a qualidade e manutenção do código futuramente.</p>
<H2>Funcionalidades e necessidades do projeto:</H2>
<ol>
  <li>Realizar Vendas dos produtos da Assistência Técnica.</li>
  <li>Cadastrar Ordens de Serviço.</li>
  <li>Cadastro de Clientes,Produtos, Fornecedores e Estoque da Assistência Técnica.</li>
  <li>Módulo de cadastro de Contas a Pagar e Receber.</li>
  <li>Notifica por E-mail Contas a Pagar e Receber vencidas.</li>
</ol>
<H2>Tecnologias e ferramentas envolvidas no Projeto</h2>
<ol>
  <li>Java 17 - Spring Boot 3.</li>
  <li>Pipeline CI/CD - Jenkins.</li>
  <li>Qualidade do Código - SonarQube.</li>
  <li>Cobertura de Testes - Junit/Mockito - 161 Testes.</li>
  <li>Observabilidade - Prometheus/Grafana (apenas nos primeiros meses)</li>
  <li>Deploy da imagem no Docker Hub e AWS EC2 + RDS.</li>
  <li>Banco de Dados - PostgreSql.</li>
  <li>Migrations - Flyway.</li>
</ol>
<H2>Passos para rodar o projeto</H2>
1º Clone o projeto no seu diretório local:

```
git clone git@github.com:GuilhermeJWT/ordem-servico-backend.git
```

2º Execute o projeto na sua IDE preferida, apenas configure um banco de dados para testar a aplicação, ou se preferir pode executar o docker compose que tudo já será configurado:

```
docker compose up -d
```
3º Acessar a documentação do Swagger:

```
http://localhost:8085/swagger-ui/index.html#/
```
4º Execute o Maven:
```
mvn clean install
```

5º No diretório target/ execute o seguinte comando para executar o ambiente de <strong>dev</strong>:

```
java -jar ordem-servico-backend.jar --spring.profiles.active=dev
```
Caso você queira testar os envios de E-mails de Contas a Pagar e Receber, você precisa criar uma senha no <a href="https://myaccount.google.com/u/0/signinoptions/twosv?pli=1&rapt=AEjHL4NTauyBmX88fhYXcX6aXS7sf7dIgXQsp6wsJzCQo-IeI8wv5jFkmG1nYJJumpBcuG9XmCB-EfS6txHP7deN4WrR0YjjjJ5i-BiIIws4mxHDD1_INwg" target="_blank">Google Account</a>, e alterar no <strong>application.properties.dev:</strong>

```
Substitua isso:
spring.mail.username=no-reply@gmail.com
spring.mail.password=${PASSWORD_EMAIL_SENDER}

Para seu e-mail e senha gerada do Google:
spring.mail.username=seu email
spring.mail.password=sua senha gerada, possui um formato como esse: XXXX XXXX XXXX XXXX
```

<H2>Pipeline CI/CD - Jenkins</h2>
<p>No diretório raiz do projeto, possui o arquivo: <b>Jenkinsfile</b>, contendo todos os Stages da Pipeline.</p>
<img src="https://github.com/user-attachments/assets/ec6c3aca-8d97-4573-8c9f-b7914212811d">
<H2>Explicando todos os Stages da Pipeline:</h2>
<ol>
  <li><strong>Declarative: Checkout SCM</strong>: Clone do projeto no Github.</li>
  <li><strong>Compile Backend</strong>: Verifica a compilação do projeto.</li>
  <li><strong>Unit Test</strong>: Executa todos os testes do projeto, para ver se novas funcionalidades foram mantidas, sem comprometer alguma funcionalidade.</li>
  <li><strong>OWASP Check Vulnerabilities</strong>: Executa uma analise de segurança no código, antes de subir o código para produção, preciso garantir que ele não possui vulnerabilidades.</li>
  <li><strong>Sonarqube Analysis</strong>: Executa uma analise completa do código, verificando possiveis code smells (código ruim), além de verificar boas práticas de desenvolvimento e manutenção do código.</li>
  <li><strong>Quality Gate</strong>: Verifica se o Quality Gate aprovou o código, se o projeto tem mais de 80% de cobertura de testes, e poucos códigos duplicados.</li>
  <li><strong>Build Backend</strong>: Após todos os stages passarem, é hora de realizar o Build do projeto, para prepara-lo para deploy.</li>
  <li><strong>Build Docker Image</strong>: Executa o build da imagem do projeto, no arquivo <strong>Dockerfile</strong> contém as configurações da imagem.</li>
  <li><strong>Push Docker Hub</strong>: Realiza o push da imagem para o Docker Hub, caso você clone o projeto modifique para a sua conta a nova imagem.</li>
  <li><strong>Transfer Artifact to EC2</strong>: Transferência de alguns recursos para o EC2.</li>
  <li><strong>Deploy to EC2</strong>: Deploy da aplicação no EC2.</li>
  <li><strong>Declarative: Post Actions</strong>: Após a Pipeline executar, ela possui configurações de envio de e-mail para o desenvolvedor(eu), com informações do Build e logs do erro, se o Build passou ele apenas grava um relatório dos testes(Tendência de resultados de teste).</li>
</ol> 
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
