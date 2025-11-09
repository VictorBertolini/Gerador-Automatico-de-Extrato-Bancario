# Gerador Automático de Extrato Bancário 

Um projeto em **Java 17** que lê extratos bancários, formata e processa os dados, e gera automaticamente uma planilha Excel profissional com o **fluxo de caixa completo**.

Construído com base nos princípios da **Clean Architecture**, o sistema é totalmente desacoplado para facilitar a adição de novos bancos e tipos de saída (como TXT, CSV, Excel ou banco de dados) sem alterar o núcleo do sistema.

---

## 🎯 Objetivo

Automatizar o processo de leitura e consolidação de extratos bancários, sejam eles pequenos (pessoais) ou grandes (empresariais), transformando dados brutos em relatórios financeiros claros e bem formatados de forma rápida, segura e simples.

A aplicação:
- Lê o extrato bancário fornecido.
- Formata e interpreta os dados (datas, descrições, valores, etc).
- Converte para entidades de domínio puras.
- Gera uma planilha Excel com **estilo profissional**, cores, bordas e cálculos automáticos de entrada/saída.

---

## ⚙️ Arquitetura

O sistema segue os princípios da **Clean Architecture**, garantindo separação de responsabilidades e facilidade de manutenção.

### 🧱 Camadas principais

| Camada | Responsabilidade | Exemplos |
|--------|------------------|-----------|
| **Core (Domínio)** | Entidades e casos de uso principais | `Transaction`, `TransactionSet`, `SaveAllTransactionsCase` |
| **Adapters** | Conexão entre domínio e mundo externo | `ReaderController`, `DataFormatter`, `ExcelRepository` |
| **Infrastructure** | Detalhes técnicos concretos | `ExcelSheetStyler`, `CellDecorator` |
| **App** | Configuração e orquestração da aplicação | `AppConfig`, `Main` |

### 🔄 Fluxo de dados
Reader → Formatter → Mapper → Domain → Repository → Excel

1. O **Reader** lê o extrato bancário bruto (CSV).  
2. O **Formatter** transforma as linhas em dados padronizados (DTO).  
3. O **Mapper** converte para entidades do domínio.  
4. O **Repository** salva as transações formatadas em um arquivo Excel.

---

## 🧠 Princípios aplicados

- **Clean Architecture:** independência entre camadas e desacoplamento total.
- **SOLID:** cada classe possui uma responsabilidade única e clara.
- **Open/Closed Principle:** novos bancos e formatos podem ser adicionados sem alterar o núcleo.
- **Dependency Inversion:** o domínio depende apenas de abstrações.
- **DTO + Mapper Pattern:** conversão limpa entre camadas.
- **Service Layer:** coordenação das operações complexas (`BankIntegrationService`).

---

## 🚀 Como rodar o projeto

### Pré-requisitos
- ☕ **Java 17+**
- 📘 **Apache POI** (para manipulação de planilhas Excel)
- 🧱 **Lombok** habilitado na IDE

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/seuusuario/cashflow-generator.git```
   
2. Abra o projeto em sua IDE preferida (IntelliJ, Eclipse ou VSCode com extensão Java).

3. Coloque o extrato CSV dentro da pasta:
   ```bash
  /data/```

Exemplo: relatorio.csv

4. Execute a classe principal:
com.bertolini.app.Main

5. O arquivo Excel será gerado automaticamente na raiz do projeto:
   InfinitePay_CashFlow.xlsx

---

📁 Estrutura de pastas
src/
 └── main/java/com/bertolini/
      ├── core/                 # Entidades e casos de uso do domínio
      │    ├── domain/          # Transaction, TransactionSet, etc.
      │    └── useCases/        # CreateTransactionCase, SaveAllTransactionsCase, etc.
      │
      ├── adapters/             # Adaptadores entre domínio e infraestrutura
      │    ├── readers/         # Leitores de extratos bancários (InfinitePay, etc.)
      │    ├── formatting/      # Formatadores de dados bancários brutos
      │    ├── repositories/    # Repositórios concretos (Excel, TXT, etc.)
      │    ├── controllers/     # Controladores que orquestram os casos de uso
      │    ├── services/        # Serviços como o BankIntegrationService
      │    └── dto/             # Objetos de transferência de dados (TransactionDTO)
      │
      ├── infrastructure/       # Implementações técnicas (Apache POI)
      │    └── excel/           # Estilização, formatação e escrita em Excel
      │
      └── app/                  # Configuração e ponto de entrada
           ├── AppConfig.java
           └── Main.java

---

🧩 Tecnologias utilizadas
| Tecnologia                                    | Função                                      |
| --------------------------------------------- | ------------------------------------------- |
| ☕ **Java 17**                                 | Linguagem base                              |
| 📘 **Apache POI**                             | Manipulação de planilhas Excel              |
| 🧱 **Lombok**                                 | Simplificação de código (getters, builders) |
| 🧼 **Arquitetura Limpa (Clean Architecture)** | Organização modular e desacoplada           |
| 🧠 **Padrões de projeto**                     | DTO, Mapper, Service Layer                  |



---
## 🔮 Próximos passos

- [ ] Implementar testes unitários.
- [ ] Melhorar tratamento de erros e logs.
- [ ] Adicionar suporte para novos bancos e formatos.
- [ ] Integrar com interface em **JavaFX**.
- [ ] Implementar agrupamento inteligente de transações (por mês, tipo, categoria).


## 🧔 Sobre o autor

Desenvolvido por **Victor Bertolini de Sousa**, estudante de Ciência da Computação e entusiasta de arquitetura de software e inteligência artificial.  
Apaixonado por transformar ideias complexas em código limpo, modular e escalável.


## 🪪 Licença

Este projeto está sob a licença MIT — sinta-se livre para usar e modificar.













