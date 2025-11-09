# Gerador Automático de Extrato Bancário 

Um projeto em **Java** que lê extratos bancários, formata, processa os dados, e gera automaticamente uma planilha Excel profissional com o **fluxo de caixa completo**.

Construído com base nos princípios da **Clean Architecture**, o sistema é totalmente desacoplado para facilitar a adição de novos bancos e tipos de saída (como TXT, CSV, Excel ou banco de dados) sem alterar o núcleo do sistema.

---

## Objetivo

Automatizar o processo de leitura e consolidação de extratos bancários, sejam eles pequenos (pessoais) ou grandes (empresariais), transformando dados brutos em relatórios financeiros claros e bem formatados de forma rápida, simples e segura.

A aplicação:
- Lê o extrato bancário fornecido.
- Formata e interpreta os dados (datas, descrições, valores, etc).
- Converte para entidades de domínio puras.
- Gera uma planilha Excel com **estilo profissional**, cores, bordas e cálculos automáticos de entrada/saída.

---

## Arquitetura

O sistema segue os princípios da **Clean Architecture**, garantindo separação de responsabilidades e facilidade de manutenção.

### Camadas principais

| Camada | Responsabilidade | Exemplos |
|--------|------------------|-----------|
| **Core (Domínio)** | Entidades | `Transaction`, `TransactionSet`, `TransactionBatch` |
| **Use Cases** | Casos de Uso | `GetBankStatementDataCase`, `SaveAllTransactionsCase`, `SplitTransactionSetByMonthCase` |
| **Adapters** | Conexão entre domínio e mundo externo | `ReaderController`, `DataFormatter`, `ExcelRepository` |
| **Infrastructure** | Detalhes técnicos concretos | `ExcelSheetStyler`, `CellDecorator` |
| **App** | Configuração e orquestração da aplicação | `AppConfig`, `Main` |

### Fluxo de dados
Reader → Formatter → Mapper → Domain → Repository → Excel

1. O **Reader** lê o extrato bancário bruto.  
2. O **Formatter** transforma as linhas em dados padronizados (DTO).  
3. O **Mapper** converte para entidades do domínio.  
4. O **Repository** salva as transações formatadas em um arquivo Excel.

---

## Princípios aplicados

- **Clean Architecture:** independência entre camadas e desacoplamento total.
- **SOLID:** cada classe possui uma responsabilidade única e clara.
- **Open/Closed Principle:** novos bancos e formatos podem ser adicionados sem alterar o núcleo.
- **Dependency Inversion:** o domínio depende apenas de abstrações.
- **DTO + Mapper Pattern:** conversão limpa entre camadas.
- **Padrão Facade** coordenação das operações complexas (`BankIntegrationService`).

---

##  Como rodar o projeto

### Pré-requisitos
-  **Java 17+**
-  **Apache POI** (para manipulação de planilhas Excel)
-  **Lombok** 

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/VictorBertolini/Gerador-Automatico-de-Extrato-Bancario.git
   
2. Abra o projeto em sua IDE preferida (IntelliJ, Eclipse ou VSCode com extensão Java).

3. Coloque o extrato CSV dentro da pasta:
   ```bash
     /data/

4. Na classe principal (com.bertolini.app.Main) coloque o nome do arquivo do extrato em FILE_NAME:
   Ex:
   ```java
   String FILE_NAME = "BankStatement.csv";

4. Execute a classe principal

5. O arquivo Excel será gerado automaticamente na raiz do projeto:
   InfinitePay_CashFlow.xlsx

---
## Notas 
- Há um 'BankStatement.csv' com dados fictícios, mas toda a formulação do extrato está de acordo com o gerado pela plataforma de serviços financeiros InfinitePay, o arquivo está para servir de testes e visualização do sistema em funcionamento. 
---
### Resultado no Excel:

![Resultado Excel](https://github.com/VictorBertolini/Gerador-Automatico-de-Extrato-Bancario/blob/main/images/Excel%20Result.png)

---
## Estrutura de pastas

```shell
CASHFLOW 2.0
├── data/                   # Extratos bancários CSV de entrada
└── src/
    └── main/
        └── java/
            └── com/
                └── bertolini/
                     ├── adapters/              # Adaptadores entre domínio e infraestrutura
                     │     ├── controllers/       # Controladores que orquestram os casos de uso
                     │     ├── dto/               # Objetos de transferência de dados (TransactionDTO)
                     │     ├── formatting/        # Formatadores de dados bancários (ex: InfinitePayFormatter)
                     │     ├── mappers/           # Conversão entre DTOs e entidades do domínio
                     │     ├── readers/           # Leitores de extratos bancários (ex: InfinitePayReader)
                     │     │     └── infinitePay/   # Implementação específica para InfinitePay
                     │     ├── repositories/      # Implementações de persistência
                     │     │     └── excel/         # Repositório Excel + classes de estilização (Apache POI)
                     │     └── services/          # Serviços de integração (ex: BankIntegrationService)
                     ├── app/                   # Camada de aplicação (AppConfig e Main)
                     └── core/                  # Núcleo da aplicação (Domínio e Casos de Uso)
                          ├── domain/            # Entidades do domínio (Transaction, TransactionSet, etc.)
                          │    └── entities/      # Classes de domínio puro
                          └── useCases/          # Casos de uso da aplicação
                               ├── reader/        # Leitura de extratos
                               ├── repository/    # Persistência (ex: SaveAllTransactionsCase)
                               └── transactions/  # Manipulação de transações (create/remove)
```

---

## Tecnologias utilizadas
| Tecnologia                                    | Função                                      |
| --------------------------------------------- | ------------------------------------------- |
|  **Java 25**                                | Linguagem base                              |
|  **Apache POI**                             | Manipulação de planilhas Excel              |
|  **Lombok**                                 | Simplificação de código (getters, builders) |
|  **Arquitetura Limpa (Clean Architecture)** | Organização modular e desacoplada           |
|  **Padrões de projeto**                     | DTO, Mapper, Facade                  |



---
## Próximos passos

- [ ] Implementar testes unitários.
- [ ] Melhorar tratamento de erros e logs.
- [ ] Adicionar suporte para novos bancos e formatos.
- [ ] Integrar com interface gráfica (front-end) com **JavaFX**.
- [ ] Implementar agrupamento inteligente de transações (por mês, tipo, categoria).
- [ ] Implementar suporte para adicionar novos dados em uma planilha já existente


## Sobre o autor

Desenvolvido por **Victor Bertolini de Sousa**, estudante de Ciência da Computação na Faculdade Federal de Uberlândia (UFU).
Apaixonado por todas as etapas do desenvolvimento de soluções que auxiliem pessoas em problemas cotidianos.
Iniciou esse projeto com fito de desenvolver suas habilidades de programação em um problema de maior escala, permitindo praticar conhecimentos de Clean Architecture, Clean Code, Design Patters e Java.

[Linkedin](https://www.linkedin.com/in/victor-bertolini-de-sousa-6b8630394/)
[GitHub](https://github.com/VictorBertolini)

## Licença

Este projeto está sob a licença MIT — sinta-se livre para usar e modificar.






