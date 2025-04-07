# Desafio Backend - Requisitos

## 1. Validações

Você deve ajustar as entidades (model e sql) de acordo com as regras abaixo: 

- `Product.name` é obrigatório, não pode ser vazio e deve ter no máximo 100 caracteres.
- `Product.description` é opcional e pode ter no máximo 255 caracteres.
- `Product.price` é obrigatório deve ser > 0.
- `Product.status` é obrigatório.
- `Product.category` é obrigatório.
- `Category.name` deve ter no máximo 100 caracteres.
- `Category.description` é opcional e pode ter no máximo 255 caracteres.

## 2. Otimização de Performance
- Analisar consultas para identificar possíveis gargalos.
- Utilizar índices e restrições de unicidade quando necessário.
- Implementar paginação nos endpoints para garantir a escala conforme o volume de dados crescer.
- Utilizar cache com `Redis` para o endpoint `/auth/context`, garantindo que a invalidação seja feita em caso de alteração dos dados.

## 3. Logging
- Registrar logs em arquivos utilizando um formato estruturado (ex.: JSON).
- Implementar níveis de log: DEBUG, INFO, WARNING, ERROR, CRITICAL.
- Utilizar logging assíncrono.
- Definir estratégias de retenção e compressão dos logs.

## 4. Refatoração
- Atualizar a entidade `Product`:
  - Alterar o atributo `code` para o tipo inteiro.
- Versionamento da API:
  - Manter o endpoint atual (v1) em `/api/products` com os códigos iniciados por `PROD-`.
  - Criar uma nova versão (v2) em `/api/v2/products` onde `code` é inteiro.

## 5. Integração com Swagger
- Documentar todos os endpoints com:
  - Descrições detalhadas.
  - Exemplos de JSON para requisições e respostas.
  - Listagem de códigos HTTP e mensagens de erro.

## 6. Autenticação e Gerenciamento de Usuários
- Criar a tabela `users` com as colunas:
  - `id` (chave primária com incremento automático)
  - `name` (obrigatório)
  - `email` (obrigatório, único e com formato válido)
  - `password` (obrigatório)
  - `role` (obrigatório e com valores permitidos: `admin` ou `user`)
- Inserir um usuário admin inicial:
  - Email: `contato@simplesdental.com`
  - Password: `KMbT%5wT*R!46i@@YHqx`
- Endpoints:
  - `POST /auth/login` - Realiza login.
  - `POST /auth/register` - Registra novos usuários (se permitido).
  - `GET /auth/context` - Retorna `id`, `email` e `role` do usuário autenticado.
  - `PUT /users/password` - Atualiza a senha do usuário autenticado.

## 7. Permissões e Controle de Acesso
- Usuários com `role` admin podem criar, alterar, consultar e excluir produtos, categorias e outros usuários.
- Usuários com `role` user podem:
  - Consultar produtos e categorias.
  - Atualizar apenas sua própria senha.
  - Não acessar ou alterar dados de outros usuários.

## 8. Testes
- Desenvolver testes unitários para os módulos de autenticação, autorização e operações CRUD.

---

# Perguntas

1. **Se tivesse a oportunidade de criar o projeto do zero ou refatorar o projeto atual, qual arquitetura você utilizaria e por quê?**
  
    Utilizaria arquitetura hexagonal ou a arquitetura clean, porque elas são focadas em manter um código menos acoplado,
  isolando melhor as regras de negócios e dominio, da implementação concreta da infraestrutura. Facilitando a escalabilidade e futuras migrações de tecnologia.
2. **Qual é a melhor estratégia para garantir a escalabilidade do código mantendo o projeto organizado?**
    
    Numa visão mais geral, a melhor estratégia para escalabilidade e organização de um projeto, seria microsserviços,
   mas pensando num projeto monolíto, uma opção seria utilizar o padrão de package-by-feature (ou domain), facilitando a
   organização, testabilidade, manutenção e até possível separação em módulos ou serviços.
3. **Quais estratégias poderiam ser utilizadas para implementar multitenancy no projeto?**

    Temos três estratégias mais comuns: 
   1. *Schema por tenant* - Oferece um bom isolamento
   2. *Banco por tenant* - Oferece isolamento total
   3. *Identificação por coluna* - Depende muito das regras de negócios e código para garantir o isolamento.
4. **Como garantir a resiliência e alta disponibilidade da API durante picos de tráfego e falhas de componentes?**

    Podemos implementar *circuit breakers* para adicionar fallbacks em casos de falhas, adicionar *retry* e *timeout* para
    em casos de falhas tentar novamente ou em caso de lentidão não travar. Também podemos usar estrátegias como *load balancing* e 
    *autoscaling* para não sobrecarregar nenhum componente ou aumentar a disponibilidade em caso de necessidade, e também
    *caching* para diminuir o processamento de informações que não se alteram com frequência.
5. **Quais práticas de segurança essenciais você implementaria para prevenir vulnerabilidades como injeção de SQL e XSS?**
    
    Sempre utilizar *ORM*s para operações no banco de dados e passando os parametros apenas pelo *data binding* dos *ORM*s,
    sem concatenar queries com valores não tratados.
6. **Qual a abordagem mais eficaz para estruturar o tratamento de exceções de negócio, garantindo um fluxo contínuo desde sua ocorrência até o retorno da API?**
   
    Utilizar *Exception Handlers* e *Controller Advices* para capturar essas exceções que ocorrem no fluxo da requisições,
    tratando, criando logs e retornando uma resposta amigável.
7. **Considerando uma aplicação composta por múltiplos serviços, quais componentes você considera essenciais para assegurar sua robustez e eficiência?**

   1. API Gateway - Para centralizar o ponto de entrada para todos serviços e fazer o *load balacing*
   2. Service Discovery - Para tirar a necessidade de cada serviço "conhecer" o endereço de todos serviços
   3. Config Server - Para centralizar a configuração de todos serviços e poder alterar configurações de algum serviço sem necessidade de redeploy
   4. Message Broker - Para lidar com comunicação assincrona entre serviços, e também garante a comunicação entre serviços mesmo com um estando fora
   5. Observabilidade - Permite a monitoração de perfomance, falhas e gargalos, facilitando uma ação corretiva ou preventiva de forma mais rápida.
   6. Logging Centralizado - Centraliza o log de todos serviços, permitindo investigar incidentes, encontrar bugs ou possiveis falhas.
   7. Auth Server - Centraliza o serviço de autenticação, evitando duplicação de lógica de autenticação entre os serviços.
8. **Como você estruturaria uma pipeline de CI/CD para automação de testes e deploy, assegurando entregas contínuas e confiáveis?**
   
    Build > Testes (unitários e de integração) > Quality gates (SonarQube, fortify, etc...) > Containerização (Docker)  > Deploy automatizado

Obs: Forneça apenas respostas textuais; não é necessário implementar as perguntas acima.

