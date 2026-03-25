# language: pt
Funcionalidade: Listar tarefas

  @listar-tarefas
  Cenário: Listagem quando existem tarefas cadastradas
    Dado que existem 3 tarefas cadastradas
    Quando envio uma requisição GET para "/tasks"
    Então o código de resposta deve ser 200
    E o corpo deve ser uma lista com 3 itens

  @listar-vazia
  Cenário: Listagem quando não há tarefas cadastradas
    Dado que não existe nenhuma tarefa cadastrada
    Quando envio uma requisição GET para "/tasks"
    Então o código de resposta deve ser 204
