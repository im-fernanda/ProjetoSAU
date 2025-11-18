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
  Sistema de gerenciamento de equipamentos e locais do SAU - MPRN
</p>

<p align="center">
  <a href="#started">Utilização</a> • 
  <a href="#routes">API Endpoints</a> 
</p>

<h2 id="started">Utilização</h2>

<h3>📋 Pré-requisitos</h3>

Antes de começar, você precisa ter instalado em seu computador:

- **Docker Desktop** - [Download Docker Desktop](https://www.docker.com/products/docker-desktop/)
  - O Docker instala e configura tudo automaticamente: Java, PostgreSQL e a aplicação

<h3>🚀 Como Rodar a Aplicação</h3>

O Docker instala e configura tudo automaticamente.

**Passo 1:** Certifique-se de que o Docker Desktop está instalado e rodando em seu computador.

**Passo 2:** Abra o terminal/prompt de comando na pasta do projeto.

**Passo 3:** Execute o comando:

```bash
docker-compose up -d
```

**Passo 4:** Aguarde alguns minutos enquanto o Docker:
- Faz o download das imagens necessárias
- Configura o banco de dados PostgreSQL
- Compila e inicia a aplicação

**Passo 5:** Abra seu navegador e acesse:

```
http://localhost:8080
```

**Pronto!** A aplicação estará rodando! 🎉

**Para parar a aplicação:**
```bash
docker-compose down
```

**Para ver os logs (útil se algo der errado):**
```bash
docker-compose logs -f app
```

**💡 Dica: Primeira vez pode demorar mais**
- A primeira vez que você rodar `docker-compose up -d`, pode demorar alguns minutos porque:
  - O Docker precisa baixar as imagens do PostgreSQL e Maven
  - Precisa compilar a aplicação Java pela primeira vez
  - Precisa baixar todas as dependências do Maven
- **Nas próximas vezes será muito mais rápido** porque o Docker reutiliza o que já foi baixado e compilado
- Se você fizer mudanças no código, use `docker-compose build app` para recompilar apenas a aplicação

**Mais informações:** Consulte o arquivo `README-DOCKER.md` para mais detalhes sobre Docker.

---

<h3>🆘 Precisa de Ajuda?</h3>

Se algo não estiver funcionando, aqui estão algumas soluções comuns:

**Problema: "docker-compose: comando não encontrado"**
- **Solução:** Certifique-se de que o Docker Desktop está instalado e rodando. Reinicie o Docker Desktop e tente novamente.

**Problema: "Porta 8080 já está em uso"**
- **Solução:** Outra aplicação está usando a porta 8080. Você pode:
  - Parar a outra aplicação
  - Ou alterar a porta no `docker-compose.yml` (altere `8080:8080` para `8081:8080`, por exemplo)

**Problema: "Não consigo acessar http://localhost:8080"**
- **Solução:** 
  - Verifique se os containers estão rodando: `docker-compose ps`
  - Veja os logs para identificar o problema: `docker-compose logs -f app`
  - Aguarde alguns minutos - o primeiro start pode demorar (aplicação precisa compilar e iniciar)
  - Se estiver demorando muito, veja os logs em tempo real: `docker-compose logs -f app` para acompanhar o progresso

**Problema: "Erro de conexão com banco de dados"**
- **Solução:**
  - Certifique-se de que o container do PostgreSQL está rodando: `docker-compose ps`
  - Veja os logs do PostgreSQL: `docker-compose logs -f postgres`
  - Aguarde alguns segundos para o banco de dados inicializar completamente

**Problema: "Build está demorando muito"**
- **Solução:**
  - Na primeira vez é normal demorar (5-10 minutos) - o Docker está baixando e compilando tudo
  - Use `docker-compose build --parallel` para acelerar builds futuros (usa múltiplos cores)
  - Se mudou apenas o código Java, reconstrua apenas: `docker-compose build app && docker-compose up -d`
  - Verifique os recursos do Docker Desktop - aumente CPU/Memória nas configurações se possível

**Ainda com problemas?**
- Veja os logs detalhados: `docker-compose logs -f`
- Consulte o arquivo `README-DOCKER.md` para mais informações sobre Docker
- Certifique-se de que o Docker Desktop está rodando corretamente

---

<h2 id="routes">📍 API Endpoints</h2>


<h3>Locais</h3>

| rota             | descrição                                       
|----------------------|-----------------------------------------------------
| <kbd> GET /listarLocais</kbd>     | acesso à listagem de todos os locais (detalhes das unidades)
| <kbd> POST /cadastrarRegional</kbd> | acesso à cadastro de regionais
| <kbd> POST /cadastrarComarca</kbd> | acesso à cadastro de comarcas (necessário associar a um regional)
| <kbd> POST /cadastrarUnidade</kbd> | acesso à cadastro de unidades (necessário associar a uma comarca)
| <kbd> PATCH /editarUnidade</kbd> | acesso à página de edição do NOME da unidade
| <kbd> DELETE /deletarUnidade</kbd> | deletar unidade permanentemente
<h3>Equipamentos</h3>

| rota             | descrição                                       
|----------------------|-----------------------------------------------------
| <kbd> GET /listarEquipamentos</kbd>     | acesso à homepage do site com acesso à listagem dos equipamentos
| <kbd> POST /cadastrarEquipamento</kbd> | acesso à cadastro de equipamento (necessário associar a um local)
| <kbd> POST /editarEquipamento</kbd> | acesso à página de edição dos detalhes de um equipamento já cadastrado (NÃO utilizar para transferência de locais)
| <kbd> POST /deletarEquipamento</kbd> | deletar permanentemente o equipamento do banco de dados
| <kbd> GET /cadastrarTransferencia/{id}</kbd> | acesso à página de transferência de equipamento para outra unidade
| <kbd> POST /processTransferencia</kbd> | processa a transferência de equipamento para outra unidade
| <kbd> GET /listarPorTombo</kbd> | formulário para buscar equipamento por tombo
| <kbd> GET /listarPorTombo/{tombo}</kbd> | acesso aos detalhes do equipamento pelo tombo
| <kbd> GET /listarPorNome</kbd> | formulário para buscar equipamentos por nome
| <kbd> GET /listarPorNome/resultado</kbd> | listagem de equipamentos que possuem o mesmo nome (ex.: computador, impressora)
| <kbd> GET /listarPorLocal/{unidadeId}</kbd> | acesso à listagem de equipamentos disponíveis em uma unidade específica (clique no nome da unidade)


