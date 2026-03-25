# language: pt
Funcionalidade: Atualizar tarefa

  @atualizar-tarefa
  Cenário: Atualização com dados válidos
    Dado que existe uma tarefa com título "Tarefa Original", descrição "Descrição original", data "2026-04-01" e status "TODO"
    Quando envio uma requisição PUT para "/tasks/{id}" com título "Tarefa Atualizada", descrição "Descrição nova", data "2026-05-01" e status "DONE"
    Então o código de resposta deve ser 200
    E o corpo deve conter título "Tarefa Atualizada"
    E o corpo deve conter status "DONE"

  @atualizar-inexistente
  Cenário: Atualização de tarefa inexistente
    Dado que não existe nenhuma tarefa cadastrada
    Quando envio uma requisição PUT para "/tasks/9999" com título "Qualquer", descrição "Descrição", data "2026-04-01" e status "TODO"
    Então o código de resposta deve ser 404

  @atualizar-duplicado
  Cenário: Atualização com título duplicado de outra tarefa
    Dado que existe uma tarefa com título "Tarefa A", descrição "Descrição A", data "2026-04-01" e status "TODO"
    E existe outra tarefa com título "Tarefa B", descrição "Descrição B", data "2026-04-01" e status "DOING"
    Quando envio uma requisição PUT para "/tasks/{id}" com título "Tarefa B", descrição "Tentativa", data "2026-04-01" e status "TODO"
    Então o código de resposta deve ser 409

  @atualizar-invalido
  Cenário: Atualização com payload inválido
    Dado que existe uma tarefa com título "Tarefa Válida", descrição "Descrição", data "2026-04-01" e status "TODO"
    Quando envio uma requisição PUT para "/tasks/{id}" com título "", descrição "   ", data "2026-04-01" e status "TODO"
    Então o código de resposta deve ser 400
