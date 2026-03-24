# language: pt
Funcionalidade: Contagem de tarefas por status

  @contagem-status
  Cenário: Retorna contagem para todos os status
    Dado que existem 3 tarefas cadastradas
    Quando envio uma requisição GET para "/tasks/count-by-status"
    Então o código de resposta deve ser 200
    E o corpo deve conter contagem para os 3 status
