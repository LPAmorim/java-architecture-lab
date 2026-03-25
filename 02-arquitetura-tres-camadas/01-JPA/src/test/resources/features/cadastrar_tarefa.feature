# language: pt
Funcionalidade: Cadastrar tarefa

  @cadastrar-tarefa
  Cenário: Cadastro com dados válidos
    Dado que não existe nenhuma tarefa cadastrada
    Quando envio uma requisição POST para "/tasks" com título "Implementar login", descrição "Criar tela de autenticação", data "2026-04-01" e status "TODO"
    Então o código de resposta deve ser 201
    E o corpo deve conter id gerado
    E o corpo deve conter título "Implementar login"
    E o corpo deve conter status "TODO"

  @cadastrar-titulo-branco
  Cenário: Cadastro com título em branco
    Dado que não existe nenhuma tarefa cadastrada
    Quando envio uma requisição POST para "/tasks" com título "", descrição "Alguma descrição", data "2026-04-01" e status "TODO"
    Então o código de resposta deve ser 400
    E o corpo deve conter o campo de erro "title"

  @cadastrar-descricao-branca
  Cenário: Cadastro com descrição em branco
    Dado que não existe nenhuma tarefa cadastrada
    Quando envio uma requisição POST para "/tasks" com título "Título válido", descrição "   ", data "2026-04-01" e status "TODO"
    Então o código de resposta deve ser 400
    E o corpo deve conter o campo de erro "description"

  @cadastrar-titulo-longo
  Cenário: Cadastro com título acima de 100 caracteres
    Dado que não existe nenhuma tarefa cadastrada
    Quando envio uma requisição POST para "/tasks" com título "Título com mais de cem caracteres para testar a validação do campo titulo que deve falhar aqui mesmo ok!!", descrição "Descrição válida", data "2026-04-01" e status "TODO"
    Então o código de resposta deve ser 400
    E o corpo deve conter o campo de erro "title"

  @cadastrar-duplicado
  Cenário: Cadastro com título duplicado
    Dado que existe uma tarefa com título "Tarefa Duplicada", descrição "Descrição", data "2026-04-01" e status "TODO"
    Quando envio uma requisição POST para "/tasks" com título "Tarefa Duplicada", descrição "Outra descrição", data "2026-05-01" e status "DOING"
    Então o código de resposta deve ser 409
