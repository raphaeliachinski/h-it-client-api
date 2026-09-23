# client-api

API RESTful em Java 21 + Spring Boot 4 para gestão de **clientes** e seus **documentos**,
com CRUD completo, autenticação JWT e banco H2 em memória versionado por Liquibase.


### Reproduzir o ambiente

Pré-requisitos: Docker, Ollama e OpenCode instalalados, com o servidor do Ollama e execução (`ollama serve`)


```bash
# 1. baixar o modelo base
ollama pull qwen3:14b

# 2. criar o modelo derivado usado pelos agents
ollama create qwen3-dev -f infra/ollama/Modelfile

# 3. conferir que os parametros foram aplicados (num_ctx 32768, temperature 0.3)
ollama show qwen3-dev
```


O passo 2 é **obrigatório**: o [`opencode.json`](opencode.json) aponta para o modelo
`ollama/qwen3-dev`, e sem ele o `opencode run` falha com modelo inexistente. O
[`infra/ollama/Modelfile`](infra/ollama/Modelfile) existe porque o padrão do Ollama é uma janela de
contexto de 4096 tokens — insuficiente para o loop de agents, onde as definições de ferramentas mais
os arquivos de código estouram a janela. Ele eleva `num_ctx` para 32768 e reduz a temperatura para
0.3, favorecendo saída determinística de código.

Para trocar o modelo, altere a linha `FROM` do `Modelfile` e rode o passo 2 de novo, reaproveitando o
mesmo nome `qwen3-dev` — nenhuma outra configuração precisa mudar.

Os agents disponíveis (`architect`, `backend`, `tester`, `reviewer`) estão definidos em
[`.opencode/agent/`](.opencode/agent/) e todos leem o contrato do projeto em [`AGENTS.md`](AGENTS.md).


Docker tem que estar préviamente instalado
```
chmod +x deploy.sh
./deploy.sh
```
A aplicação estará disponível em http://localhost:8080/, após disponível poderá testar utilizando o script:

```
chmod +x test-flow.sh
./test-flow.sh
```
ou acessando a url do Swagger http://localhost:8080/swagger-ui/index.html


### Conclusão 

Desde o início do projeto o modelo falhou em todas as tarefas, sendo necessário algumas vezes refinar o prompt com o auxilio de outros modelos, e mesmo com grau de detalhamento elevado 
foi necessário ajustes manuais para finalizar as tarefas, no prompt abaixo o modelo não informava o pacote corretamente e insistia em retornar as entidades JPA para o controller
ao invés dos DTO e também ao invés de retornar a exception ClientNotFoundException criada anteriormente retornava uma RuntimeException:



```
Leia AGENTS.md primeiro, então reescreva ClientService e somente ele, seguindo os requisitos, não altere mais nenhum arquivo e siga estas observações adicionais:
- Metodo save deve salvar os documentos.
- Metodo update deve substituir todos os documentos.
- O Service irá receber o ClientRequest
- Retorne ClientResponse, entidades JPA não devem ser expostas para o controller
- Metodo delete deve deletar os documentos  e deve ser transacional
- ClientRequest e ClientResponse são DTOs, use ClientMapper para converter.
- Leia o ClientMapper para entender como é feita a conversão.
-  DTOs e Mapper estão dentro da pasta controller

Não precisamos de código tipo o abaixo porque save e update já irão salvar documentos

@Transactional
public Client createClientWithDocuments(Client client, List<Document> documents) {
client.setDocuments(documents);
return clientRepository.save(client);
}

@Transactional
public Client updateClientWithDocuments(Client client, List<Document> documents) {
client.setDocuments(documents);
return clientRepository.save(client);
}



ClientService deve possuir 6 métodos:
- save
- update
- findAll
- findById
- deleteById
- deleteAll
```