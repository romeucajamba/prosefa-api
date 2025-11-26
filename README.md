## Programa de Selos Fiscais de Alta Segurança (PROSEFA)

Esta API simula um cenário real da plataforma: a **gestão e validação de Selos Fiscais de Alta Segurança**.

---

## 🎯 Objetivo

 Desenvolver uma **API RESTful** para permitir:
- Registro de empresas autorizadas.
- Solicitação de selos fiscais.
- Emissão e validação de selos.
- Auditoria das ações realizadas.
  
#### Este backend segue boas práticas de desenvolvimento Java, segurança, modelagem de domínio e arquitetura limpa.
# INSTRUCOES - PROSEFA

## Requisitos
- Java 17+
- Maven ou Gradle
- Docker (opcional, recomendado)

## 🧱 Tecnologias Usadas
- **Java 17+**
- **Spring Boot**
- JPA/Hibernate
- Banco de dados (PostgreSQL ou H2)
- Maven ou Gradle
- Testes (JUnit, Mockito)
- Autenticação (JWT)

## Rodando com Docker (Postgres)
1. docker-compose up -d
2. Ajuste src/main/resources/application.properties se necessário (usuario/senha/porta).

## Executando a aplicação
mvn spring-boot:run
ou
./mvnw spring-boot:run

## Endpoints principais
- POST /api/empresas
  body: { "nome":"X", "nif":"123", "tipo":"FABRICANTE" }
- POST /api/selos/solicitar
  body: { "empresaId":"uuid", "produto":"Produto" }
- POST /api/selos/validar/{codigo}

## Exemplo curl
Criar empresa:
curl -X POST http://localhost:8080/api/empresas -H "Content-Type: application/json" -d '{"nome":"ACME","nif":"12345678","tipo":"FABRICANTE"}'

Solicitar selo:
curl -X POST http://localhost:8080/api/selos/solicitar -H "Content-Type: application/json" -H "X-User: admin" -d '{"empresaId":"<uuid>","produto":"Produto X"}'

Validar selo:
curl -X POST http://localhost:8080/api/selos/validar/PROSEFA-2025-000001 -H "X-User: auditor"

##ENVS:
#######################################
# AMBIENTE DA APLICAÇÃO
#######################################
SPRING_PROFILES_ACTIVE=
SERVER_PORT=

#######################################
# BANCO DE DADOS (POSTGRES EXEMPLO)
#######################################
DB_HOST=
DB_PORT=
SPRING_APP_NAME=
SERVER_PORT=
DB_NAME=
DB_USERNAME=
DB_PASSWORD=

JWT_SECRET=
JWT_EXPIRATION=


SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
SPRING_JPA_HIBERNATE_DDL_AUTO=
SPRING_JPA_SHOW_SQL=

#######################################
# JWT (SEGURANÇA)
#######################################
JWT_SECRET=
JWT_EXPIRATION=

#######################################
# USUÁRIO ADMIN PADRÃO
#######################################
ADMIN_DEFAULT_EMAIL=
ADMIN_DEFAULT_PASSWORD=

#######################################
# SWAGGER CONFIG
#######################################
SWAGGER_API_TITLE=
SWAGGER_API_DESCRIPTION=
SWAGGER_API_VERSION=

#######################################
# LOGGING / DEBUG
#######################################
LOGGING_LEVEL_ROOT=
LOGGING_LEVEL_COM_PROFESA=

#######################################
# SPRING DOC (SWAGGER 3)
#######################################
SPRINGDOC_API_DOCS_ENABLED=
SPRINGDOC_SWAGGER_UI_ENABLED=
SPRINGDOC_SWAGGER_UI_PATH=
SPRINGDOC_API_DOCS_PATH=

#######################################
# CORS
#######################################
CORS_ALLOWED_ORIGINS=
CORS_ALLOWED_METHODS=
CORS_ALLOWED_HEADERS=
