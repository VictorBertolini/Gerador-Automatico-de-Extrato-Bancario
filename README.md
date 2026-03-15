# Gerador de Fluxo de Caixa

### Processador Automático de Extratos Bancários

**Java • Clean Architecture • Apache POI**

Uma aplicação Java que lê extratos bancários em formato CSV, processa e normaliza os dados, e gera automaticamente uma planilha Excel profissional com um relatório completo de fluxo de caixa organizado por mês, com cálculos de entradas, saídas e saldo líquido.

Construído sobre os princípios da **Clean Architecture**, o sistema é totalmente desacoplado, tornando simples a adição de novos bancos, novos formatos de saída (PDF, banco de dados, etc.) ou novos casos de uso sem tocar no domínio central

## Funcionalidades

* **Leitura de CSV:** Lê extratos de qualquer banco com mapeamento de campos configurável
* **Detecção Automática de Datas:** Detecta formatos (yyyy-MM-dd, dd/MM/yyyy, MM-dd-yyyy e mais)
* **Formatação Financeira:** Trata formatação financeira seja separador financeiro `,` ou `.`
* **Divisão por Mês:** Separa transações mensalmente, gerando uma aba no Excel para cada mês (de cada ano)
* **Excel Estilizado:** Gera arquivos com título, cabeçalhos, células coloridas para entradas/saídas e fórmulas automáticas
* **Deduplicação:** Reexecutar o programa nunca insere transações duplicadas (sistema de id)
* **Seguro a múltiplas configurações:** Seguro para rodar múltiplas vezes contra o mesmo arquivo Excel
* **Tema Customizável:** Personalização total do visual do Excel via padrão *Builder* no ExcelTheme + ExcelLayout

## Arquitetura

O projeto segue a **Clean Architecture** com separação estrita de camadas:

| Camada | Responsabilidade | Classes Principais |
| --- | --- | --- |
| **Core — Domain** | Entidades de negócio puras sem dependências | `Transaction`, `TransactionSet` |
| **Core — Use Cases** | Regras de negócio da aplicação | `SaveAllTransactionsCase`, `FilterDuplicateTransactionCase` |
| **Adapters — Controllers** | Orquestram casos de uso, protegem o core de frameworks | `RepositoryController`, `TransactionController` |
| **Adapters — Formatters** | Transformam linhas CSV brutas em DTOs tipados | `CsvFormatter`, `AmountFormatterService` |
| **Adapters — Repository** | Persistem dados de domínio no Excel via Apache POI | `ExcelRepository`, `ExcelTransactionWriter` |
| **App** | Fiação (wiring) e apenas configuração | `AppConfig`, `Main` |

### Fluxo de Dados

`CsvReader` → `CsvFormatter` → `TransactionMapper` → `TransactionSet` → `SplitByMonth` → `FilterDuplicates` → `ExcelRepository`

1. **CsvReader** lê as linhas brutas do arquivo CSV.
2. **CsvFormatter** analisa cada linha em um `TransactionDTO`, normalizando datas e valores.
3. **TransactionMapper** converte DTOs em entidades de domínio `Transaction` puras.
4. **SplitTransactionSetByMonthCase** agrupa transações em objetos `TransactionBatch` mensais.
5. **RepositoryController** verifica IDs existentes por aba e filtra duplicatas.
6. **ExcelRepository** escreve apenas novas transações e salva o arquivo

## Estratégia de Deduplicação

Cada transação gera uma impressão digital **SHA-256** baseada na data, valor e descrição. Este ID é gravado na primeira coluna de cada linha no arquivo Excel

Em cada execução, antes de escrever, o sistema:

1. Busca a aba do Excel correspondente ao mês
2. Lê apenas os IDs daquela aba não os dados completos (eficiente em memória)
3. Filtra as transações recebidas contra o conjunto de IDs existentes
4. Escreve apenas transações cujo ID ainda não esteja presente

Isso significa que rodar o programa 100 vezes com o mesmo CSV produz exatamente o mesmo resultado que rodar uma única vez

## Customização do Excel

### Layout (`ExcelLayout`)

Todas as posições de células, larguras de colunas, regiões mescladas, fórmulas e alvos de borda são declarados como constantes nomeadas:

* `ExcelLayout.TITLE` → linha 0, col 1 (mesclado sobre colunas do cabeçalho)
* `ExcelLayout.HEADER` → linha 2, col 1 (rótulos das colunas)
* `ExcelLayout.INFLOW` → linha 2, col 9 (fórmula SUMIF > 0)
* `ExcelLayout.NET_CASH` → linha 6, col 9 (fórmula SUM, mesclada)

### Tema (`ExcelTheme`)

Propriedades visuais são totalmente configuráveis através de um *Builder*. O tema padrão utiliza cabeçalhos azul escuro com texto branco e indicadores verde/vermelho para entradas/saídas:

```java
ExcelTheme theme = new ExcelTheme.Builder()
    .titleBgColor(IndexedColors.DARK_BLUE)
    .titleFontColor(IndexedColors.WHITE)
    .inflowColor(IndexedColors.DARK_GREEN)
    .outflowColor(IndexedColors.DARK_RED)
    .build();

```

### Tela do Excel
![Excel Result](images/Result%20Excel.png)

---

## Primeiros Passos

### Pré-requisitos

* **Java 17+** (Java 25 utilizado)
* **Maven** (dependências gerenciadas via `pom.xml`)
* **Apache POI** - Manipulação de Excel
* **Lombok** - Redução de código desnecessário

### Passos

1. **Clone o repositório:**
`git clone https://github.com/VictorBertolini/Gerador-Automatico-de-Extrato-Bancario.git`
2. Coloque seu arquivo CSV na pasta `data/`.
3. **Configure o Main.java** — apenas a seção superior precisa de edição:
```java
static final String CSV_FILE          = "MeuExtrato.csv";
static final String XLSX_FILE         = "FluxoDeCaixa";
static final String BANK_NAME         = "MeuBanco";
static final String CSV_SEPARATOR     = ",";
static final boolean HAS_HEADER       = true;
static final boolean COMMA_AS_DECIMAL = true;

// Mapeie colunas do CSV para campos — use null para pular uma coluna
static final ArrayList<String> FIELD_ORDER = new ArrayList<>(
    Arrays.asList("date", "time", "type", "description", null, "amount")
);

```


4. Execute o `Main.java`. O arquivo Excel será gerado em `data/`.

---

## Estrutura do Projeto

```shell
src/main/java/com/bertolini/
│
├── app/
│   ├── AppConfig.java               # Configuração de injeção de dependência
│   └── Main.java                    # Configuração + ponto de entrada
│
├── core/
│   ├── domain/entities/
│   │   ├── Transaction.java         # Entidade de domínio com fingerprint SHA-256
│   │   ├── TransactionSet.java      # Coleção de transações
│   │   └── TransactionBatch.java    # Grupo mensal (rótulo, mês, ano, conjunto)
│   │
│   └── useCases/
│       ├── transactions/
│       │   ├── CreateTransactionCase.java
│       │   ├── RemoveTransactionCase.java
│       │   ├── FilterDuplicateTransactionCase.java
│       │   └── SplitTransactionSetByMonthCase.java
│       │
│       ├── reader/
│       │   ├── BankStatementReader.java      # Interface
│       │   └── GetBankStatementDataCase.java
│       │
│       └── repository/
│           ├── TransactionRepository.java    # Interface
│           └── SaveAllTransactionsCase.java
│
└── adapters/
    ├── controllers/
    │   ├── DataFormatterController.java
    │   ├── ReaderController.java
    │   ├── RepositoryController.java        # Orquestração de deduplicação + salvamento
    │   └── TransactionController.java
    │   
    ├── dto/
    │   └── TransactionDTO.java              # Record: banco, data, hora, descrição, tipo, valor
    │
    ├── formatting/
    │   ├── DataFormatter.java               # Interface
    │   └── CsvFormatter.java                # Manipula campos entre aspas, decimais com vírgula
    │
    ├── mappers/
    │   └── TransactionMapper.java          # Converte Transação DTO em Transação
    │
    ├── readers/
    │   └── CsvReader.java
    │
    ├── services/
    │   ├── BankIntegrationService.java      # Facade: ler → formatar → mapear
    │   │
    │   ├── amount/
    │   │   ├── AmountCleaner.java
    │   │   └── AmountFormatterService.java  # Detecção de decimal com vírgula/ponto
    │   │
    │   └── date/
    │       ├── DateFormatDetector.java      # Detecta formato automaticamente na primeira leitura
    │       └── DateFormatterService.java
    │
    └── repositories/excel/
        ├── ExcelRepository.java             # Implementação do TransactionRepository
        │
        ├── service/
        │   └── LastExcelRowGetter.java      # Encontra a última linha de dados sem metadados
        │
        ├── structure/
        │   ├── Cell.java                    # Record: linha, coluna
        │   └── CellRange.java
        │
        └── style/
            ├── ExcelLayout.java             # Todas as posições, larguras, fórmulas como constantes
            ├── ExcelTheme.java              # Padrão Builder para configuração visual
            ├── CellDecorator.java           # Constrói objetos CellStyle do POI a partir do tema
            ├── ExcelCellWriter.java         # Métodos auxiliares para escrita/estilo seguros de células
            ├── ExcelSheetStyler.java        # Aplica formatação completa da planilha na criação
            └── ExcelTransactionWriter.java  # Escreve linhas de transação na planilha
```

## Tecnologias e Padrões

* **Java 17+:** Linguagem core
* **Apache POI:** Criação e manipulação de arquivos Excel
* **SHA-256 (JDK):** Fingerprinting de transações para deduplicação
* **Clean Architecture:** Separação de camadas e inversão de dependência
* **Padrão Builder:** Configuração visual flexível do `ExcelTheme`
* **Padrão Facade:** `BankIntegrationService` para simplificar o pipeline de importação

## Roadmap

* [ ] Testes unitários e de integração
* [ ] Tratamento de erros aprimorado e logging estruturado
* [ ] Suporte para bancos adicionais
* [ ] Interface gráfica com JavaFX ou Java Swing 
* [ ] Categorização inteligente de transações (por tipo, estabelecimento, palavra-chave)
* [ ] Exportação de relatórios em PDF
* [ ] Adição de Gráficos no Relatório Excel
---

**Autor:** Desenvolvido por **Victor Bertolini de Sousa**, estudante de Ciência da Computação na Universidade Federal de Uberlândia (UFU). Este projeto foi construído para praticar *Clean Architecture*, *Clean Code*, *Design Patterns* e Java em um problema do mundo real

https://www.linkedin.com/in/victor-bertolini-de-sousa-6b8630394/

https://github.com/VictorBertolini