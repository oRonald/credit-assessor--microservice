# Credit Assessor Microservice

O Credit Assessor é o responsável pela análise de crédito e pela liberação de um limite para o cliente.
Neste microserviço implementei tecnologias como o RabbitMQ e o OpenFeign, responsáveis pela comunicação síncrona e assíncrona
entre este microserviço, o de clientes e o de cartões.

## Comunicação Síncrona com OpenFeign

O OpenFeign é excelente para tarefas que utilizam comunicações síncronas, sendo ótimo para operações como validações e queries.
Em contrapartida, para a comunicação síncrona funcionar o microserviço alvo precisará estar ativo e funcionando corretamente.
<hr/>
<img width="620" height="137" alt="image" src="https://github.com/user-attachments/assets/7c6ea134-1885-4a60-9158-c0f5124f2414" />
<hr/>

A partir da assinatura @FeignClient em uma interface, é possível acessar o endpoint de Clients para conseguir realizar uma busca via CPF.
Os dados de retorno da query instanciará uma classe template da classe Client do outro microserviço.
Essa classe template possui os mesmos atributos de Client, mas não é uma Entidade.

## Comunicação Assícrona com RabbitMQ

Neste projeto implementei um sistema de mensageria chamado RabbitMQ, que realiza comunicações assíncronas entre microserviços.
Sendo a Mensageria um dos pilares mais importantes da Arquitetura de Microserviços e que diferente do OpenFeign, o microserviço alvo não
precisa necessariamente estar ativo no momento, indicando que as comunicações possam ser feitas em um momento posterior.

<hr/>
<img width="222" height="69" alt="image" src="https://github.com/user-attachments/assets/7c6d841a-28dc-43ba-ab54-abf56c54fbef" />
<hr/>

Nessa configurações foi definida a Queue na qual o Credit Asssessor enviará a requisição para o microserviço Cards. Onde ele solicitará a criação
um novo cartão com os dados do Client aprovado e validado anteriormente.

<hr/>
<img width="897" height="118" alt="image" src="https://github.com/user-attachments/assets/867c9e73-757a-4afe-b2db-16a52f4011ce" />
<hr/>

Na interface do RabbitMQ foi criada uma queue por onde será passada as requisições do Credit-Assessor para o microsserviço de Cards.

### Como enviar e receber requisições atraves de uma queue no RabbitMQ?
<img width="785" height="411" alt="image" src="https://github.com/user-attachments/assets/f659fdcb-0b9e-4ab5-bd7b-b9bf8630d540" />

<p>No Credit-Assessor é criado uma classe componente chamada Publisher, que será o responsável por enviar a solicitação de um novo cartão com os dados do Client,
todos convetidos como JSON.</p>

<img width="677" height="559" alt="image" src="https://github.com/user-attachments/assets/35d9a6bf-b5c5-4752-9574-dd3e60af7c42" />

<p>Já no microserviço de cartões, será criada um outro componente que escutará a mensagem vinda através do RabbitMQ com os dados do Client a partir da assinatura
@RabbitListener, que em seguida converterá o JSON em uma classe template com os dados do Cliente e do Cartão</p>

## Tecnologias
- Java 17
- Spring Boot 3
- Spring Web
- Eureka Discovery Client
- Spring Cloud OpenFeign
- Spring AMQP
- Spring Boot Actuator
- Springdoc Openapi (Swagger)
- Lombok
