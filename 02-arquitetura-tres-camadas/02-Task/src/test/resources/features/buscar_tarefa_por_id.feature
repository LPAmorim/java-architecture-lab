# language: pt
Funcionalidade: Buscar tarefa por ID

  @buscar-id
  Cenário: Busca com ID existente
    Dado que existe uma tarefa com título "Tarefa Para Buscar", descrição "Descrição", data "2026-04-01" e status "TODO"
    Quando envio uma requisição GET para "/tasks/{id}" com o id da tarefa criada
    Então o código de resposta deve ser 200
    E o corpo deve conter título "Tarefa Para Buscar"

  @buscar-id-inexistente
  Cenário: Busca com ID inexistente
    Dado que não existe nenhuma tarefa cadastrada
    Quando envio uma requisição GET para "/tasks/9999"
    Então o código de resposta deve ser 404
    E o corpo deve conter a mensagem de erro
