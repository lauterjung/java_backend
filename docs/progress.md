Dia 1
-Definição do produto
-Definição das tecnologias a serem utilizadas
-Criação de roadmap
-Preparo de ambiente

Dia 2
-Preparo de ambiente
-Conexão com o pgSQL usando o DBeaver
-Debug de código na página inicial
-DI / IOC
Notas
-Bastante tempo perdido no marketplace do Eclipse para plugins (não funciona direito)

Dia 3
-Migrações com hibernate
-Migrações com flyway
-Popular dados do db
-Adicionado lombok para o Eclipse
-Alteração do nome Service para JobPostings
-Criação dos repositórios, services e controllers
-Adição do Swagger
Notas: 
-dificuldades com o DBeaver para exibir todos os bancos de dados (não achava o meu banco)
-o EF do C# é mais automático. No Java as migrações precisam ser mais manuais, com scripts SQL
-service não foi uma boa escolha de nome. Conflita com Service do Java e do PostgreeSQL

Dia 4
-Tentativa de implementar OData (desisti)
-Global exception handler
-Logs
-Adição de colunas ENUM
-DTOs e mapeamento
Notas: 
-muita dificuldade para implementar o OData. Muito tempo investido e cheguei a desistir
-precisa ajustar os retornos do swagger

Dia 5
-Ajustes finais nos retornos do swagger
-Adição da configuração de CORS
Notas
-Problemas com os enums no banco. Salvos agora em varchar
