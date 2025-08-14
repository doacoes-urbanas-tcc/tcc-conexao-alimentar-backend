Entendi — vou ajustar o README para já deixar **espaços marcados para imagens** da **logo** e da **landing page**, assim você só troca o link quando for colocar no GitHub.
Também vou deixar o texto já preparado para ficar bonito no repositório.

Segue versão revisada:

---

```markdown
# 🍏 Conexão Alimentar - Backend

<p align="center">
  <img src="https://res.cloudinary.com/du9zmknbe/image/upload/v1754259522/logo.png" alt="Logo Conexão Alimentar" width="250"/>
</p>

O **Conexão Alimentar** é um sistema desenvolvido como parte do Trabalho de Conclusão de Curso (TCC) para conectar doadores de alimentos (produtores rurais, comércios e pessoas físicas) a Organizações Não Governamentais (ONGs) que irão distribuir essas doações à sociedade.  
O projeto visa reduzir o desperdício de alimentos e facilitar a logística de doações, contando também com voluntários e administradores para manter o sistema em funcionamento.

Este repositório contém **apenas o backend**, desenvolvido em **Java com Spring Boot**.

---

## 👥 Integrantes do Grupo
> *(Substitua pelos nomes reais)*

- Nome 1  
- Nome 2  
- Nome 3  
- Nome 4  


## 📋 Funcionalidades

- **Cadastro de usuários**:
  - Produtores Rurais
  - Comércios
  - Pessoas Físicas (doadores)
  - ONGs
  - Voluntários
  - Administradores
- **Segurança e autenticação** com Spring Security (JWT).
- **Gestão de doações**:
  - Cadastro de doações
  - Reserva de doações
  - Confirmação por **QR Code** (gerado automaticamente)
- **Geolocalização**:
  - Cálculo de distância entre ONG e doador
  - Exibição no mapa
- **Upload de relatórios** em **PDF** e armazenamento seguro.
- **Armazenamento em nuvem** (Cloudinary Service) para imagens e QR Codes.
- **Geração de relatórios mensais** (PDF/CSV) para administradores.
- **Sistema de avaliação** de doações.
- **Futuro**: voluntários de transporte para logística das entregas.

---

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Maven**
- **Cloudinary** (armazenamento em nuvem)
- **Lombok**
- **MapStruct**
- **iText / Apache PDFBox** (geração de relatórios PDF)
- **Leaflet API** (mapas e distância)
- **QRCode Generator** (ZXing)
- **OpenCSV** (exportação CSV)

---

## 📂 Estrutura do Projeto

```

src
├── main
│    ├── java
│    │    └── com.seuprojeto.conexaoalimentar
│    │         ├── controller
│    │         ├── service
│    │         ├── repository
│    │         ├── model
│    │         └── security
│    └── resources
│         ├── application.properties
│         └── static
└── test

````

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.8+
- PostgreSQL

### Passos
1. **Clonar o repositório**:
   ```bash
   git clone https://github.com/seuusuario/conexao-alimentar-backend.git
   cd conexao-alimentar-backend
````

2. **Configurar o banco de dados** no arquivo `application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/conexao_alimentar
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Configurar variáveis do Cloudinary**:

   ```properties
   cloudinary.cloud_name=SEU_CLOUD_NAME
   cloudinary.api_key=SEU_API_KEY
   cloudinary.api_secret=SEU_API_SECRET
   ```

4. **Instalar dependências e executar**:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

---

## 🔑 Autenticação

O sistema utiliza **JWT (JSON Web Token)**.
Para acessar endpoints protegidos:

1. Faça login com usuário e senha.
2. Receba o token JWT.
3. Envie-o no header `Authorization`:

   ```
   Authorization: Bearer SEU_TOKEN_AQUI
   ```

---

## 📜 Endpoints Principais

| Método | Rota                     | Descrição                        | Autenticação |
| ------ | ------------------------ | -------------------------------- | ------------ |
| POST   | `/auth/login`            | Login e geração de token         | ❌            |
| POST   | `/usuarios`              | Cadastro de usuário              | ❌            |
| GET    | `/doacoes`               | Lista todas as doações           | ✅            |
| POST   | `/doacoes`               | Cria nova doação                 | ✅            |
| POST   | `/doacoes/{id}/reservar` | Reserva uma doação               | ✅            |
| GET    | `/relatorios/mensal`     | Gera relatório mensal em PDF/CSV | ✅ (Admin)    |

---

## 📌 Objetivo do Projeto

O **Conexão Alimentar** busca:

* **Diminuir o desperdício** de alimentos
* **Aproximar doadores e ONGs**
* **Facilitar a logística** de entrega
* **Aumentar o alcance** de doações através de tecnologia

---

## 📄 Licença

Este projeto está sob a licença MIT.
Sinta-se livre para utilizar, modificar e distribuir.
