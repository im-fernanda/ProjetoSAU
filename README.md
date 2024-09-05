[JAVA_BADGE]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRINGBOOT_BADGE]: https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white
[THYMELEAF_BADGE]: https://img.shields.io/badge/thymeleaf-%236DB33F.svg?style=for-the-badge&logo=thymeleaf&logoColor=white
[POSTGRES_BADGE]: https://img.shields.io/badge/postgresql-4169e1?style=for-the-badge&logo=postgresql&logoColor=white

<h1 align="center" style="font-weight: bold;">Gerenciador do SAU 💻</h1>

![java][JAVA_BADGE]
![spring][SPRINGBOOT_BADGE]
![thymeleaf][THYMELEAF_BADGE]
![postgres][POSTGRES_BADGE]

<p align="center">
  <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a> 
</p>

<h2 id="started">🚀 Getting started</h2>

1. Crie um Banco de Dados no seu administrador de desenvolvimento para o PostgreSQL ou diretamente no contêiner Docker, criando assim o Banco de Dados inicial para testes. Com isso feito, siga os passos abaixo.
2. Clone o repositório e acesse a pasta do projeto:

       $ git clone im-fernanda/ProjetoSAU
       $ cd nome-da-sua-pasta

3. Crie um arquivo application.propertiers na raiz do projeto e insira suas credencias. Utilize como exemplo:
  ```yaml
  spring.datasource.url=jdbc:postgresql://localhost:5432/nome-do-seu-banco-de-dados
  spring.datasource.username=usuario-do-seu-postgres
  spring.datasource.password=senha-do-seu-postgres
  
  spring.datasource.driver-class-name=org.postgresql.Driver
  spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
  spring.jpa.hibernate.ddl-auto=update
  spring.servlet.multipart.enabled=true
  ```
5. Execute o projeto e abra localhost:8080;

<h2 id="routes">📍 API Endpoints</h2>
​
<h3>Equipamentos</h3>

| rota             | descrição                                       
|----------------------|-----------------------------------------------------
| <kbd> GET /listarEquipamentos</kbd>     | acesso à homepage do site com acesso à listagem dos equipamentos --- NOT READY
| <kbd> POST /cadastrarEquipamento</kbd> | acesso à cadastro de equipamento (necessário associar a um local) --- NOT READY
| <kbd> POST /editarEquipamento</kbd> | acesso à página de edição dos detalhes de um equipamento já cadastrado (NÃO utilizar para transferência de locais) --- NOT READY
| <kbd> POST /cadastrarTransferencia</kbd> | acesso à registro de transfêrencia de equipamento para outro local  --- NOT READY
| <kbd> GET /listarPorTombo</kbd> | acesso aos detalhes do equipamento (detalhes e histórico de transferência) --- NOT READY
| <kbd> GET /listarPorNome</kbd> | acesso à listagem de equipamentos que possuem o mesmo nome (ex.: computador, impressora) --- NOT READY
| <kbd> GET /listarPorLocal</kbd> | acesso à listagem de equipamentos disponíveis em um local especifico --- NOT READY


<h3>Locais</h3>

| rota             | descrição                                       
|----------------------|-----------------------------------------------------
| <kbd> GET /listarLocais</kbd>     | acesso à listagem de todos os locais (detalhes das unidades)
| <kbd> POST /cadastrarRegional</kbd> | acesso à cadastro de regionais
| <kbd> POST /cadastrarComarca</kbd> | acesso à cadastro de comarcas (necessário associar a um regional)
| <kbd> POST /cadastrarUnidade</kbd> | acesso à cadastro de unidades (necessário associar a uma comarca)
| <kbd> PATCH /editarRegional</kbd> | acesso à página de edição do NOME do regional
| <kbd> PATCH /editarComarca</kbd> | acesso à página de edição do NOME da comarda
| <kbd> PATCH /editarUnidade</kbd> | acesso à página de edição do NOME da unidade 
| <kbd> DELETE /deletarRegional</kbd> | deletar regional, comarcas e unidades a ela associada
| <kbd> DELETE /deletarComarca</kbd> | deletar comarcas e unidades a ela associada
| <kbd> DELETE /deletarUnidade</kbd> | deletar unidade



