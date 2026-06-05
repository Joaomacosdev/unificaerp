# 🚀 Unifica ERP

Sistema de Gestão Empresarial (ERP) desenvolvido com Java e Spring Boot, com foco em escalabilidade, organização e boas práticas de desenvolvimento.

O projeto está em fase inicial de desenvolvimento e tem como objetivo centralizar processos empresariais como gestão de usuários, produtos, estoque, clientes, pedidos e futuras funcionalidades de CRM, Dashboard e Atendimento.

---

## 📋 Sobre o Projeto

O **Unifica ERP** é uma aplicação backend que busca fornecer uma base sólida para sistemas corporativos modernos.

O projeto está sendo desenvolvido utilizando arquitetura em camadas, APIs REST e banco de dados relacional, seguindo padrões utilizados pelo mercado.

### Funcionalidades planejadas

- 🔐 Autenticação e autorização
- 👥 Gestão de usuários
- 📦 Gestão de produtos
- 📊 Controle de estoque
- 🛒 Gestão de pedidos
- 👤 Cadastro de clientes
- 📈 Dashboard gerencial
- 💬 Atendimento e chamados
- 🏢 Multiempresa
- ☁️ Estrutura preparada para SaaS

---

## 🛠️ Tecnologias Utilizadas

<div align="center">

<img src="https://skillicons.dev/icons?i=java,spring,postgresql,docker,maven,git,github,idea" />

</div>

### Backend

- Java 25
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security

### Banco de Dados

- PostgreSQL

### DevOps

- Docker
- Docker Compose

### Build

- Maven

### Controle de Versão

- Git
- GitHub

---

## 📂 Estrutura do Projeto

```text
src
 ├── controller
 ├── service
 ├── repository
 ├── model
 ├── dto
 ├── security
 ├── config
 └── exception
```

---

# ⚙️ Como Executar Localmente

## Pré-requisitos

- Java 25
- Maven 3.9+
- Docker
- Docker Compose
- Git

---

## 1️⃣ Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/unificaerp.git

cd unificaerp
```

---

## 2️⃣ Configurar Variáveis de Ambiente

Crie um arquivo `.env`

```env
DB_NAME=unificaerp
DB_USER=postgres
DB_PASSWORD=postgres
DB_PORT=5432
```

---

## 3️⃣ Subir o PostgreSQL

```bash
docker compose up -d
```

Verifique se o container está rodando:

```bash
docker ps
```

---

## 4️⃣ Executar a Aplicação

### Linux/Mac

```bash
./mvnw spring-boot:run
```

### Windows

```powershell
mvnw.cmd spring-boot:run
```

---

## 🌐 Acesso

API:

```text
http://localhost:8080
```

---

## 🐳 Executando com Docker

Gerar imagem:

```bash
docker build -t unificaerp .
```

Executar:

```bash
docker run -p 8080:8080 unificaerp
```

---

## 📸 Screenshots

### Em desenvolvimento

Adicione imagens conforme o projeto evoluir.

```markdown
![Login](docs/images/login.png)

![Dashboard](docs/images/dashboard.png)

![Produtos](docs/images/produtos.png)
```

Ou GIF:

```markdown
![Demo](docs/images/demo.gif)
```

---

## 🚀 Deploy

Ainda não disponível.

Quando o deploy estiver pronto, adicione:

```text
https://app.unificaerp.com.br
```

---

## 🎯 Roadmap

### Fase 1

- [x] Estrutura inicial Spring Boot
- [x] Configuração PostgreSQL
- [x] Docker Compose
- [ ] Entidade Usuário
- [ ] Spring Security
- [ ] JWT

### Fase 2

- [ ] Produtos
- [ ] Categorias
- [ ] Estoque

### Fase 3

- [ ] Clientes
- [ ] Pedidos
- [ ] Dashboard

### Fase 4

- [ ] Atendimento
- [ ] WebSocket
- [ ] SaaS Multiempresa

---

## 💡 Desafios Enfrentados

### Integração entre Docker e PostgreSQL

**Problema:**

Configuração inicial do container PostgreSQL e persistência dos dados.

**Solução:**

Utilização de Docker Compose com volumes persistentes e Health Checks para garantir a disponibilidade do banco antes da aplicação iniciar.

---

### Compatibilidade com Java 25

**Problema:**

Necessidade de utilizar versões compatíveis das dependências.

**Solução:**

Atualização para Spring Boot 4 e utilização do Eclipse Temurin 25 nas imagens Docker.

---

### Organização da Arquitetura

**Problema:**

Estruturar o projeto de forma escalável desde o início.

**Solução:**

Separação em camadas:

- Controller
- Service
- Repository
- DTO
- Security
- Config

---

## 👨‍💻 Autor

### João Marcos

Desenvolvedor Java e Spring Boot.

- GitHub: https://github.com/Joaomacosdev
- LinkedIn: https://www.linkedin.com/in/jo%C3%A3o-marcos-aragao/

---

## 📜 Licença

Este projeto está sob a licença MIT.

```
Apache License © João Marcos
```

---
⭐ Se este projeto te ajudar, considere deixar uma estrela no repositório.