# #####| Tech-Parquimetro |############################

* **Grupo:** 60
* **Turma:** 1ADJT - 2023
* **Integrantes**
  ```
    Guilherme Franco: oguilhermefranco@gmail.com
    Vinicius M. de Menezes: paravinicius@gmail.com
    Lucca Brito Gesteira: luccabritogt@gmail.com
    Eric Da Silva Moraes: ersmoraes@outlook.com
  ```

# Sobre o projeto

Este projeto é produto do Tech Challenge, proposto na fase 3 - Spring Data, Deploy e Serverless, do curso
Arquiterura Java, da FIAP, turma 2023 (1ADJT).

O objetivo é o desenvolvimento de um sistema de parquímero conforme a seguinte especificação:
```
Desenvolver o novo sistema de parquímetro que deve ser desenvolvido para atender às 
necessidades de uma cidade turística, que possui uma população de 300.000 habitantes, mas experimenta um 
aumento significativo durante a alta temporada, recebendo 250.000 visitantes adicionais, em média, com a mesma 
quantidade de veículos. Este sistema substituirá o sistema antigo, que é lento, não escalável e não confiável.

O novo sistema de parquímetro foi projetado para lidar com a demanda crescente de estacionamento na 
cidade. Ele oferece funcionalidades tais, como registro de condutores e veículos, controle de tempo estacionado, 
opções flexíveis de pagamento e emissão de recibos
```


# Funcionalidades Principais

1. Registro de Condutores e Veículos
2. Registro de Forma de Pagamento
3. Controle de Tempo Estacionado
4. Alertas de Tempo Estacionado
5. Opções de Pagamento
6. Emissão de Recibos

# Infraestrutura

Para tender ao desafio proposto de atender a demanda crescente de estacionamento e oferecer uma infra que possua
escabilidade nos horários de pico dos checkins e checkout das vagas, o time optou par levar toda a solução para um
serviço de núvem da Amazon Web Services - AWS.

Para garantir um ambiente de alta segurança a alida a um ambiente clusterizado de alta escabilidade, a infra na AWS foi
montada com a seguinte estrutura:

![Infra_AWS_my-tech-parking-meter.png](src%2Fmain%2Fresources%2Fdocumentation%2FInfra_AWS_my-tech-parking-meter.png "Digrama da Infra na AWS")

| Serviço AWS    | Nome do objeto criado                   | Identificador na AWS   |
|----------------|-----------------------------------------|------------------------|
| API Gateway    | my-tech-parking-mater                   | 37q1cuzrei             |
| VPC            | cluster-fiap-vpc                        | vpc-08037dd0619599761  |
| Private Subnet | cluster-fiap-subnet-private1-us-east-1a |                        |
| -              | cluster-fiap-subnet-private1-us-east-1b |                        |
|                |                                         |                        |

## Amazon API Gateway
É um serviço gerenciado para permitir a criação, publicação, manutenção, monitoração e proteção das APIs, com os
benecios da escabilidade. O API Gateway dá suporte a cargas de trabalho conteinerizadas e sem servidor, além de
rodar a aplicação do My Techpaking na WEB.

## VPC
O Amazon Virtual Private Cloud (Amazon VPC) permite provisionar uma seção logicamente isolada da Nuvem AWS onde é
possível executar recursos da AWS em uma rede virtual definida (vide tabela cita em [Infraestrutura](#infraestrutura)).

Para proteger a rede dos clusteres foram criadas as seguintes sub redes:

* Duas redes públicas: para trabalhar o load balancer, dando visibilidade no acesso externo e craindo uma camada para
chamar os recursos da rede interna.
  * cluster-fiap-subnet-public1-us-east-1a
  * cluster-fiap-subnet-public1-us-east-1b
* Duas redes privadas: para trabalhar com os clusteres e banco de dados de forma isolda de qualquer acesso externo.
  * cluster-fiap-subnet-public1-us-east-1a
  * cluster-fiap-subnet-public1-us-east-1b

![AWS_Mapa_Rede.PNG](src%2Fmain%2Fresources%2Fdocumentation%2FAWS_Mapa_Rede.PNG "Mada da rede na AWS")
