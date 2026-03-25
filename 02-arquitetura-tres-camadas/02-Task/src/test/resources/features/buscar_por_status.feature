# language: pt
Funcionalidade: Buscar tarefas por status

  @buscar-status
  Cenário: Busca com status existente e tarefas encontradas
    Dado que existe uma tarefa com título "Tarefa TODO", descrição "Descrição", data "2026-04-01" e status "TODO"
    Quando envio uma requisição GET para "/tasks/status/TODO"
    Então o código de resposta deve ser 200
    E o corpo deve ser uma lista com 1 itens

  @buscar-status-vazio
  Cenário: Busca com status sem tarefas
    Dado que não existe nenhuma tarefa cadastrada
    Quando envio uma requisição GET para "/tasks/status/DONE"
    Então o código de resposta deve ser 204

  @buscar-status-invalido
  Cenário: Busca com status inválido
    Dado que não existe nenhuma tarefa cadastrada
    Quando envio uma requisição GET para "/tasks/status/INVALIDO"
    Então o código de resposta deve ser 400
