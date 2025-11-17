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