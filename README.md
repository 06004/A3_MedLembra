<<<<<<< HEAD
# MedLembra - Sistema de Lembrete de Medicamentos

Sistema desenvolvido em Java para ajudar idosos a lembrarem de tomar seus medicamentos nos horários corretos, com suporte de cuidadores.
=======
# MedLembra - Sistema de Lembrete

O MedLembra é um sistema desenvolvido em Java para ajudar idosos a lembrarem de tomar seus medicamentos nos horários corretos, com suporte de cuidadores.
>>>>>>> 8922515417833d73c54c24c71809dd61c8b251bd

## 👥 Integrantes

- **Sara Luiza Alves da Silva** - RA: 12524236684
- **Thiago Passari Santos** - RA: 12526163002

<<<<<<< HEAD
## 🏫 Instituição

Universidade Anhembi Morumbi  
Disciplina: Algoritmos e Programação + Programação de Soluções Computacionais  
Professores: Cassia Assis e Jader de Amorim
=======
>>>>>>> 8922515417833d73c54c24c71809dd61c8b251bd

## ✨ Funcionalidades

Cadastro, listagem e remoção de idosos
- Cadastro, listagem e remoção de cuidadores
- Cadastro e listagem de medicamentos por idoso
- Associação de cuidador a idoso
- Listagem de todas as pessoas cadastradas
- Validações de entrada (nome, idade, horário, telefone)
- Tratamento de exceções customizadas
- Salvamento dos dados em arquivo CSV
- Carregamento dos dados do arquivo CSV
- Alarme que avisa quando é hora de tomar o medicamento

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **ArrayList** para armazenamento em memória
- **do/while** e **switch/case** para menu interativo
- **if/else** para controle de entrada
- **Herança:** Idoso e Cuidador herdam de Pessoa (classe abstrata)
- **Polimorfismo:** Método abstrato `exibirPerfil()` sobrescrito nas subclasses
- **Encapsulamento:** Atributos privados com getters/setters
- **Sobrecarga:** Construtores e métodos de adicionar medicamento
- **Interface:** `IPessoaService` para inversão de dependência
- **Singleton:** `PessoaController` garante instância única
- **Enum:** `OpcaoMenu` elimina números mágicos
- **Streams + Optional:** Código moderno e funcional
- **Exceções Customizadas:** `ValidacaoException` e `PessoaNaoEncontradaException`
- LocalTime

## 🏫 Instituição

Universidade Anhembi Morumbi  
Disciplina: Algoritmos e Programação + Programação Orientada a Objetos + Programação de Soluções Computacionais
Professores: Cassia Assis e Jader de Amorim

## 📁 Estrutura de Pacotes

```
src/
├── Main.java
├── model/
│   ├── Pessoa.java (abstrata)
│   ├── Idoso.java
│   ├── Cuidador.java
│   └── Medicamento.java
├── service/
│   └── AlarmeService.java
│   └── IPessoaService.java (interface)
├── controller/
│   └── PessoaController.java (Singleton)
├── exception/
│   ├── ValidacaoException.java
│   └── PessoaNaoEncontradaException.java
├── util/
│   └── FormatadorAlarme.java
│   └── Validador.java
├── view/
│   ├── OpcaoMenu.java (enum)
│   └── MenuPrincipal.java
└── persistence/
    └── Persistencia.java
```

## 🚀 Como Executar

### Opção 1: Compilar manualmente (terminal)

```bash
# Clone o repositório
git clone https://github.com/06004/A3_MedLembra.git
cd A3_MedLembra

# Compile todos os arquivos .java
javac -d out src/model/*.java src/controller/*.java src/service/*.java src/view/*.java src/exception/*.java src/util/*.java src/persistence/*.java src/Main.java

# Execute
java -cp out Main
```

### Opção 2: Usando IDE (recomendado)

Importe o projeto em **Eclipse**, **IntelliJ IDEA** ou **VS Code** e execute a classe `Main.java`.

## 📊 Diagrama de Classes

O diagrama de classes está disponível no repositório e reflete a arquitetura MVC do sistema com:
- Herança (Pessoa → Idoso/Cuidador)
- Composição (Idoso → Medicamento)
- Associação (Cuidador → Idoso)
- Realização de interface (PessoaController → IPessoaService)
- Dependências (MenuPrincipal → PessoaController, Validador e PessoaController → AlarmeService → FormatadorAlarme)

## 📝 Notas

- O sistema utiliza persistência em CSV para salvar dados entre execuções
- A opção de carregar dados foi implementada para restaurar o estado anterior
- Todas as validações são centralizadas na classe `Validador`
