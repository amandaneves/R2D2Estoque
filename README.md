# R2D2 Estoque

**Projeto feito em 2016 para Trabalho da disciplina Banco de Dados e Projeto Integrador.**

Grupo: Amanda Neves, Camila Alves, Hericlis Martins, Thiago Nascimento e Victor Trevenzoli.

A idéia do grupo foi criar um sistema de gerência de estoque, no qual a entrada e saída de produtos fosse por meio de leitura RFID. 
Então, simulamos a leitura do RFID como um documento que contém os códigos de produtos (documento de teste na raiz do projeto produtos.rfid), e pode ser feito o cadastro de novos produtos, entrada e saída.

Sistema desenvolvido por mim, logo criada por Hericlis Martins.

**Passo a passo para execução do projeto**:
- Rodar o Script r2d2estoque, e depois o admin que estão no package bd.
- Irá conectar no endereço padrão (caso tenha algum dado diferente em seu ambiente é só trocar na classe util/Conexao.java):
  Host: localhost

  Banco: r2d2estoque

  Porta: 3306

  Usuario: root

  Senha: password
- Ao abrir a tela de login, é só logar com o que foi criado pelo script admin. Usuário: admin Senha: admin.

**Java 8, JavaFX, MySQL**.
