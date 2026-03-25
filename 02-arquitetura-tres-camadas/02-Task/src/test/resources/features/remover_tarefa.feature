# language: pt
Funcionalidade: Remover tarefa

  @remover-tarefa
  Cenário: Remoção de tarefa existente
    Dado que existe uma tarefa com título "Tarefa Para Remover", descrição "Descrição", data "2026-04-01" e status "TODO"
    Quando envio uma requisição DELETE para "/tasks/{id}" com o id da tarefa criada
    Então o código de resposta deve ser 204

  @remover-inexistente
  Cenário: Remoção de tarefa inexistente
    Dado que não existe nenhuma tarefa cadastrada
    Quando envio uma requisição DELETE para "/tasks/9999"
    Então o código de resposta deve ser 404
