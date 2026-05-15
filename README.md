# MedLembra - Sistema de Lembrete
## Descrição
O MedLembra é um sistema desenvolvido em Java para ajudar idosos a lembrarem de tomar seus medicamentos nos horários corretos, com suporte de cuidadores.

## Funcionalidades
- Cadastro, listagem e remoção de idosos
- Cadastro, listagem e remoção de cuidadores
- Cadastro e listagem de medicamentos por idoso
- Associação de cuidador a idoso
- Listagem de todas as pessoas cadastradas
- Validações de entrada (nome, idade, horário)
- Tratamento de exceções customizadas
- Salvamento dos dados em arquivo CSV

## Tecnologias utilizadas
- Java 21
- ArrayList para armazenamento em memória
- ```do/while``` e ```switch/case``` para criação de um menu interativo
- ```if/else``` para controle de entrada de variáveis
- **Herança:** Idoso e Cuidador herdam de Pessoa (classe abstrata)
- **Polimorfismo:** Método abstrato ```exibirPerfil()``` sobrescrito nas subclasses
- **Encapsulamento:** Atributos privados com ```getters/setters```
- **Sobrecarga:** Construtores e métodos de adicionar medicamento

## Como executar
1. Clone o repositório: "git clone https://github.com/06004/A3_MedLembra.git"
2. Compile: "javac src/*.java"
3. Execute: "java src/Main"

## Autores
- Sara Luiza Alves da Silva  - RA: 12524236684
- Thiago Passari Santos  - RA: 12526163002

## Projeto de A3 Universidade Anhembi Morumbi
