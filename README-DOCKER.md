doc# Containerização do ProjetoSAU

Este projeto está containerizado usando Docker e Docker Compose.

## Pré-requisitos

- Docker instalado (versão 20.10 ou superior)
- Docker Compose instalado (versão 2.0 ou superior)

## Como executar

### Opção 1: Docker Compose (Recomendado)

Execute o seguinte comando na raiz do projeto:

```bash
docker-compose up -d
```

Para visualizar os logs:

```bash
docker-compose logs -f app
```

Para parar os serviços:

```bash
docker-compose down
```

Para parar e remover volumes (apaga o banco de dados):

```bash
docker-compose down -v
```

### Opção 2: Build manual e execução

1. Construir a imagem:

```bash
docker build -t projetosau-app .
```

2. Executar o PostgreSQL:

```bash
docker run -d \
  --name projetosau-postgres \
  -e POSTGRES_DB=gerenciador_sau \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=2005 \
  -p 5432:5432 \
  postgres:16-alpine
```

3. Executar a aplicação:

```bash
docker run -d \
  --name projetosau-app \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/gerenciador_sau \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=2005 \
  -p 8080:8080 \
  projetosau-app
```

## Configurações de ambiente

Você pode personalizar as configurações através de variáveis de ambiente ou criando um arquivo `.env` na raiz do projeto:

```env
POSTGRES_PASSWORD=sua_senha_segura
POSTGRES_PORT=5432
APP_PORT=8080
```

## Acessar a aplicação

Após iniciar os containers, a aplicação estará disponível em:

- **Aplicação**: http://localhost:8080
- **PostgreSQL**: localhost:5432

## Estrutura dos containers

- **postgres**: Container do PostgreSQL 16 (Alpine)
- **app**: Container da aplicação Spring Boot

## Volumes

O Docker Compose cria um volume persistente para o banco de dados PostgreSQL, garantindo que os dados não sejam perdidos ao parar os containers.

## Health Checks

Ambos os containers possuem health checks configurados:
- PostgreSQL: Verifica se o serviço está pronto para receber conexões
- App: Verifica o endpoint `/actuator/health` (requer Spring Boot Actuator)

## Troubleshooting

### Ver logs dos containers

```bash
docker-compose logs -f
```

### Verificar status dos containers

```bash
docker-compose ps
```

### Reconstruir a aplicação após mudanças no código

```bash
docker-compose build app
docker-compose up -d
```

### Entrar no container do PostgreSQL

```bash
docker-compose exec postgres psql -U postgres -d gerenciador_sau
```

### Limpar tudo e começar do zero

```bash
docker-compose down -v
docker-compose build --no-cache
docker-compose up -d
```

