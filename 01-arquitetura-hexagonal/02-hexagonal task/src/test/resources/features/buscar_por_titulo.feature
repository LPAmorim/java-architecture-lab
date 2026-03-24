# language: pt
Funcionalidade: Buscar tarefas por título

  @buscar-titulo
  Cenário: Busca aproximada que encontra resultados
    Dado que existe uma tarefa com título "Implementar autenticação", descrição "Descrição", data "2026-04-01" e status "TODO"
    Quando envio uma requisição GET para "/tasks/search?keyword=AUTENTIC"
    Então o código de resposta deve ser 200
    E o corpo deve ser uma lista com 1 itens

  @buscar-titulo-vazio
  Cenário: Busca aproximada sem resultados
    Dado que não existe nenhuma tarefa cadastrada
    Quando envio uma requisição GET para "/tasks/search?keyword=xyz"
    Então o código de resposta deve ser 204
