# account-old - Micro service para transações de conta (versão "antiga")
2023-07-27

Usar http://localhost:8079/swagger-ui.html

## Conceito

Foi desenvolvida essa API, para simular o uso do kafka, partindo do pressuposto que há uma API rodando atualmente, com banco de dados relacional 
e que a mesma será atualizada para uma nova versão, utilizando banco de dados NoSQL (Mongo) e que a atualização dos dados será feita por *event stream* (Kafka)

Para executar é necessário ter o docker na máquina

```
./run
```

## Postgres

Acesse o pgadmin: http://localhost:8083

Usuário: user@gmail.com
Senha: useruser

Banco de dados utilizado:

* Host: db
* Nome do banco: account
* Usuário: root
* Senha: root