# API REST para Gerenciamento de Pessoas - Desafio SCCON Geospatial

## 📋 Resumo do Projeto

Esta aplicação Spring Boot implementa uma API REST completa para gerenciamento de pessoas, desenvolvida conforme as especificações do desafio SCCON Geospatial. A implementação segue os princípios SOLID, utiliza ConcurrentHashMap para thread-safety e inclui validações robustas para evitar erros 500.

### ✅ Pontos Implementados

- **Documentação bem elaborada**: Todas as classes, métodos e endpoints possuem documentação JavaDoc detalhada
- **Uso de SOLID**: Separação clara de responsabilidades com interfaces, serviços especializados e injeção de dependência
- **Implementação correta dos cálculos**: Cálculos de idade e salário implementados conforme especificação exata
- **ConcurrentHashMap**: Utilizado para implementação thread-safe do mapa de pessoas em memória
- **Separação de camadas**: Arquitetura bem estruturada com controller, service, repository e DTOs
- **Validações robustas**: Validação de entrada com mensagens detalhadas para evitar dados nulos
- **Tratamento de erros**: Handler global com mensagens detalhadas e códigos HTTP apropriados
- **Testes unitários**: Cobertura de testes para serviços críticos com valores determinísticos

## 🚀 Como Executar o Projeto

### Pré-requisitos

- **Java 17** ou superior
- **Maven 3.6+** (ou use o wrapper incluído)
- **Git** (para clonar o repositório)

### 1. Clonar o Repositório

```bash
git clone <url-do-repositorio>
cd geocore
```

### 2. Executar a Aplicação

#### Opção A: Usando Maven Wrapper (Recomendado)
```bash
# Windows
./mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### Opção B: Usando Maven (se instalado)
```bash
mvn spring-boot:run
```

#### Opção C: Compilar e Executar JAR
```bash
# Compilar
./mvnw clean package

# Executar
java -jar target/geocore-0.0.1-SNAPSHOT.jar
```

### 3. Verificar se a Aplicação Está Rodando

A aplicação estará disponível em: `http://localhost:8080`

Para verificar se está funcionando, acesse: `http://localhost:8080/people`

## 📚 Documentação da API

### Endpoints Disponíveis

#### 1. **GET /people** - Listar todas as pessoas
- **Descrição**: Retorna todas as pessoas ordenadas alfabeticamente por nome
- **Resposta**: Array de objetos Person
- **Exemplo**: `GET http://localhost:8080/people`

#### 2. **GET /people/{id}** - Buscar pessoa por ID
- **Descrição**: Retorna uma pessoa específica
- **Parâmetros**: `id` (Long) - ID da pessoa
- **Exemplo**: `GET http://localhost:8080/people/1`

#### 3. **POST /people** - Criar nova pessoa
- **Descrição**: Cria uma nova pessoa (ID gerado automaticamente)
- **Body**: JSON com nome, dataNascimento, dataAdmissao
- **Exemplo**:
```json
POST http://localhost:8080/people
{
  "name": "João Silva",
  "birthDate": "1990-01-01",
  "admissionDate": "2020-01-01"
}
```

#### 4. **POST /people/{id}** - Criar pessoa com ID específico
- **Descrição**: Cria uma nova pessoa com ID específico
- **Parâmetros**: `id` (Long) - ID desejado
- **Body**: JSON com nome, dataNascimento, dataAdmissao
- **Exemplo**: `POST http://localhost:8080/people/5`

#### 5. **PUT /people/{id}** - Atualizar pessoa completamente
- **Descrição**: Atualiza todos os dados de uma pessoa
- **Parâmetros**: `id` (Long) - ID da pessoa
- **Body**: JSON com todos os campos
- **Exemplo**: `PUT http://localhost:8080/people/1`

#### 6. **PATCH /people/{id}** - Atualizar pessoa parcialmente
- **Descrição**: Atualiza apenas campos específicos
- **Parâmetros**: `id` (Long) - ID da pessoa
- **Body**: JSON com campos opcionais
- **Exemplo**:
```json
PATCH http://localhost:8080/people/1
{
  "name": "João Silva Atualizado"
}
```

#### 7. **DELETE /people/{id}** - Remover pessoa
- **Descrição**: Remove uma pessoa do sistema
- **Parâmetros**: `id` (Long) - ID da pessoa
- **Exemplo**: `DELETE http://localhost:8080/people/1`

#### 8. **GET /people/{id}/age?output={days|months|years}** - Calcular idade
- **Descrição**: Calcula a idade da pessoa
- **Parâmetros**: 
  - `id` (Long) - ID da pessoa
  - `output` (String) - Tipo de saída: "days", "months" ou "years"
- **Exemplo**: `GET http://localhost:8080/people/1/age?output=years`

#### 9. **GET /people/{id}/salary?output={full|min}** - Calcular salário
- **Descrição**: Calcula o salário da pessoa
- **Parâmetros**:
  - `id` (Long) - ID da pessoa
  - `output` (String) - Tipo de saída: "full" (reais) ou "min" (salários mínimos)
- **Exemplo**: `GET http://localhost:8080/people/1/salary?output=full`

### Cálculo de Salário

O salário é calculado conforme a especificação:
- **Salário inicial**: R$ 1.558,00
- **Aumento anual**: 18% do valor atual + R$ 500,00
- **Salário mínimo**: R$ 1.302,00 (fevereiro 2023)
- **Arredondamento**: Sempre para cima com 2 casas decimais

### Dados de Exemplo

A aplicação inicia com 3 pessoas pré-cadastradas:

1. **José da Silva** (ID: 1)
   - Nascimento: 06/04/2000
   - Admissão: 10/05/2020

2. **Maria Santos** (ID: 2)
   - Nascimento: 15/08/1995
   - Admissão: 20/03/2019

3. **João Oliveira** (ID: 3)
   - Nascimento: 03/12/1988
   - Admissão: 15/01/2021

## 🧪 Executar Testes

```bash
# Executar todos os testes
./mvnw test

```

## 📁 Estrutura do Projeto

```
geocore/
├── src/
│   ├── main/
│   │   ├── java/com/sccon/geocore/
│   │   │   ├── config/          # Configurações do Spring
│   │   │   ├── controller/      # Controllers REST
│   │   │   ├── model/           # Entidades do domínio
│   │   │   ├── repository/      # Interfaces e implementações de repositório
│   │   │   ├── service/         # Lógica de negócio
│   │   │   └── web/            # DTOs, mappers e tratamento de erros
│   │   └── resources/
│   │       └── application.properties
│   └── test/                   # Testes unitários e de integração
├── pom.xml                     # Configuração Maven
└── README.md                   # README
```

## 🔍 Validações Implementadas

- **Nome**: Obrigatório e não pode ser vazio
- **Data de nascimento**: Obrigatória e deve ser no passado
- **Data de admissão**: Obrigatória e deve ser no passado ou presente
- **ID**: Deve ser positivo (quando fornecido)
- **Parâmetros de saída**: Validados para age (days/months/years) e salary (full/min)

## ⚠️ Tratamento de Erros

A aplicação possui tratamento centralizado de erros com:

- **404 Not Found**: Pessoa não encontrada
- **400 Bad Request**: Dados inválidos ou parâmetros incorretos
- **409 Conflict**: Tentativa de criar pessoa com ID já existente
- **500 Internal Server Error**: Erros inesperados

Todas as respostas de erro incluem:
- Código de status HTTP apropriado
- Mensagem descritiva do erro
- Código de erro interno
- Detalhes específicos do problema

## 🎯 Exemplos de Uso

### Testar Cálculo de Idade (José da Silva)
```bash
curl ""
curl "http://localhost:8080/person/1/age?output=months"
curl "http://localhost:8080/person/1/age?output=years"
```

### Testar Cálculo de Salário (José da Silva)
```bash
curl "http://localhost:8080/person/1/salary?output=full"
curl "http://localhost:8080/person/1/salary?output=min"
```

### Criar Nova Pessoa
```bash
curl -X POST "http://localhost:8080/person" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ana Costa",
    "birthDate": "1992-05-15",
    "admissionDate": "2021-03-10"
  }'
```

## 📊 Tecnologias Utilizadas

- **Spring Boot 3.5.6**: Framework principal
- **Java 17**: Linguagem de programação
- **Maven**: Gerenciamento de dependências
- **Jakarta Validation**: Validação de dados
- **JUnit 5**: Testes unitários
- **MockMvc**: Testes de integração

## 👨‍💻 Bruno Cícero Rodrigues de Souza

Desenvolvido para o **Desafio SCCON Geospatial** - Avaliação Java

---

