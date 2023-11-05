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

| Serviço AWS          | Nome do objeto criado           | Identificador na AWS                                                            |
|----------------------|---------------------------------|---------------------------------------------------------------------------------|
| API Gateway          | my-tech-parking-mater           | 37q1cuzrei                                                                      |
| VPC                  | cluster-fiap-vpc                | vpc-08037dd0619599761                                                           |
| EC2 - Load balancers | ecs-parking-meter-load-balancer | DNS:<br/>internal-eca-events-load-balance-503500598.us-east-1.elb.amazonaws.com |
| ECS Service          | mytechparkingmeter-cluter-vpc   | arn:aws:ecs:us-east-1:503004629953:cluster/mytechparkingmeter-cluter-vpc        |
| DocumentDB           | docdb-my-tech-parking-meter     | vpc-08037dd0619599761                                                                                |

## Amazon API Gateway
É um serviço gerenciado para permitir a criação, publicação, manutenção, monitoração e proteção das APIs, com os
benecios da escabilidade. O API Gateway dá suporte a cargas de trabalho conteinerizadas e sem servidor, além de
rodar a aplicação do My Techpaking na WEB.

## VPC
O Amazon Virtual Private Cloud (Amazon VPC) permite provisionar uma seção logicamente isolada da Nuvem AWS onde é
possível executar recursos da AWS em uma rede virtual definida (vide tabela cita em [Infraestrutura](#infraestrutura)).

### VPC Sub Redes
Para proteger a rede dos clusteres foram criadas as seguintes sub redes na VPC:

* Duas redes públicas: No momento sem uso, pois atualmente não existem recursos da rede que possam ser acessados
  diretamente de fora. A infra deixa disponível para futuros usos, como: Demonstrações, testes de POC.
  * cluster-fiap-subnet-public1-us-east-1a
  * cluster-fiap-subnet-public1-us-east-1b
* Duas redes privadas: para trabalhar com os clusteres e banco de dados de forma isolda de qualquer acesso externo.
  * cluster-fiap-subnet-private1-us-east-1a
  * cluster-fiap-subnet-private1-us-east-1b
* Um subrede provada para o banco de dados:
  * database-security-group-fiap (vpc-08037dd0619599761)
    * us-east-1a (subnet-09e02071cc909a052)
    * us-east-1b (subnet-09bc05a26e60ef2f4)

![AWS_Mapa_Rede.PNG](src%2Fmain%2Fresources%2Fdocumentation%2FAWS_Mapa_Rede.PNG "Mada da rede na AWS")

### VPC Security Groups
Para controlar a regras de acesso a cada tipo de rede foram criados os seguintes grupos de segunraça (security groups):
* balancers-secuity-group-fiap (sg-03b491f878f16ee14)
  * Permite acesso externo, e por isso é o responsável por hospedar o load balancer. Protege a rede interna de
    tanto para permitir acessos indevidos quando retornar dados indevidos.
  * Regras de entrada: HTTP -> TCP -> 80
  * Regras de saída: HTTP -> TCP -> 80
* cluster-security-group-fiap (sg-0a0ea4b5efbb7f7e5)
  * Utilizado para hopedar recursos internos da rede como os clusteres e o banco de dados. Tem por objetivo controlar o
  tipo dado que pode ser exposto para outros serviços da AWS.
  * Regras de entrada:
    * HTTP -> TCP -> 80: Para comunicação das APIs WEB.
    * TCP -> TCP -> 3000: Para acesso aos serviços de Banco de dados.
    * TCP -> TCP -> 27017: Para acesso aos serviços de Banco de dados.
  * Regras de saída: Todos protocolos, para entrega a subnet pública, mas sem acesso rede fora da VPC.

## EC2 - Load balancer

O Amazon Elastic Load Balancer (ELB) é um serviço de balanceamento de carga que distribui automaticamente o tráfego de
entrada entre vários destinos e dispositivos virtuais em uma ou mais zonas de disponibilidade.

Existem três tipos de balanceadores de carga na AWS:
  * balanceador de carga clássico;
  * o balanceador de carga da aplicação;
  * e o balanceador de carga da rede.

Para a infra desta aplicação foi escolhido o `balanceador de carga da rede`, para poder distribuir a carga de
requisições as clustes através do fluxo de rede entre as sub redes privadas criadas geograficamente separadas.

| Load balancer                   | VPC                   | Zona de disp. | Subnet                                  |
|---------------------------------|-----------------------|---------------|-----------------------------------------|
| ecs-parking-meter-load-balancer | vpc-08037dd0619599761 | us-east-1a    | cluster-fiap-subnet-private1-us-east-1a |
| ->                              | ->                    | us-east-1b    | cluster-fiap-subnet-private1-us-east-1b |

## ECS Service

O Amazon Elastic Container Service (ECS) é um serviço totalmente gerenciado de orquestração de contêineres que ajuda a
implantar, gerenciar e escalar aplicações em contêineres de maneira mais eficiente. Desta forma, nos horários de pico
quando ocorre o maior número de checkins e chekouts o ECS poderá escalonar de forma dinámica da quantidade instâncias.

Para esta fase inicial do projeto foi optado uma instância do tipo FARGATE, que apresar de ser mais cara poderá indicar
o quanto de recurso computacional é necessário e assim no futuro auxiliar na configuração de um EC2, com maior controle
da infra necessária.

Configuração da ´Definição da tarefa´ (no formato JSON):
```
{
    "taskDefinitionArn": "arn:aws:ecs:us-east-1:503004629953:task-definition/api-my-tech-parking-meter:1",
    "containerDefinitions": [
        {
            "name": "container-my-tech-parking-meter",
            "image": "503004629953.dkr.ecr.us-east-1.amazonaws.com/my-tech-parking-meter:latest",
            "cpu": 0,
            "portMappings": [
                {
                    "name": "container-my-tech-parking-meter-80-tcp",
                    "containerPort": 80,
                    "hostPort": 80,
                    "protocol": "tcp",
                    "appProtocol": "http"
                }
            ],
            "essential": true,
            "environment": [],
            "environmentFiles": [],
            "mountPoints": [],
            "volumesFrom": [],
            "ulimits": [],
            "logConfiguration": {
                "logDriver": "awslogs",
                "options": {
                    "awslogs-create-group": "true",
                    "awslogs-group": "/ecs/api-my-tech-parking-meter",
                    "awslogs-region": "us-east-1",
                    "awslogs-stream-prefix": "ecs"
                },
                "secretOptions": []
            }
        }
    ],
    "family": "api-my-tech-parking-meter",
    "executionRoleArn": "arn:aws:iam::503004629953:role/ecsTaskExecutionRole",
    "networkMode": "awsvpc",
    "revision": 1,
    "volumes": [],
    "status": "ACTIVE",
    "requiresAttributes": [
        {
            "name": "com.amazonaws.ecs.capability.logging-driver.awslogs"
        },
        {
            "name": "ecs.capability.execution-role-awslogs"
        },
        {
            "name": "com.amazonaws.ecs.capability.ecr-auth"
        },
        {
            "name": "com.amazonaws.ecs.capability.docker-remote-api.1.19"
        },
        {
            "name": "ecs.capability.execution-role-ecr-pull"
        },
        {
            "name": "com.amazonaws.ecs.capability.docker-remote-api.1.18"
        },
        {
            "name": "ecs.capability.task-eni"
        },
        {
            "name": "com.amazonaws.ecs.capability.docker-remote-api.1.29"
        }
    ],
    "placementConstraints": [],
    "compatibilities": [
        "EC2",
        "FARGATE"
    ],
    "requiresCompatibilities": [
        "FARGATE"
    ],
    "cpu": "1024",
    "memory": "2048",
    "runtimePlatform": {
        "cpuArchitecture": "X86_64",
        "operatingSystemFamily": "WINDOWS_SERVER_2019_CORE"
    },
    "registeredAt": "2023-10-24T02:50:01.565Z",
    "registeredBy": "arn:aws:iam::503004629953:user/Vinicius_FIAP_2023",
    "tags": []
}
```

## DocumentDB

Este projeto optou utilizar o MongoDB como banco de dados devido a caracterisicas que pontencializam o seu uso em
computação em nuvem, como:
* É escalável, rápido e confiável, podendo lidar com grandes volumes de dados e cargas de trabalho variáveis.
* Por ser no SQL, oferece vangatens como:
  * Escalabilidade: O MongoDB é altamente escalável e pode lidar com grandes volumes de dados e cargas de trabalho
    variáveis. Ele usa uma arquitetura de escala horizontal que permite adicionar mais servidores para aumentar a
    capacidade de armazenamento e processamento de dados.
  * Desempenho: O MongoDB é projetado para oferecer desempenho superior em comparação com os bancos de dados
    relacionais. Ele usa um modelo de dados flexível que permite consultas rápidas e eficientes, mesmo em grandes
    conjuntos de dados.
  * Flexibilidade: O MongoDB é altamente flexível e pode armazenar dados em formato JSON. Isso significa que ele pode
    lidar com dados estruturados e não estruturados, o que o torna ideal para aplicativos modernos que precisam lidar
    com dados complexos.
  * Facilidade de uso: O MongoDB é fácil de usar e oferece uma ampla variedade de ferramentas e drivers para várias
  linguagens de programação e plataformas. Ele também é compatível com o Amazon DocumentDB, um serviço de banco de
  dados totalmente gerenciado na nuvem.
* Suporta consultas flexíveis, índices, agregações e transações.
* Oferece drivers e ferramentas para diversas linguagens de programação e plataformas.

Para hospedar na AWS, este projeto da arquitetura adotou o Amazon DocumentDB (com compatibilidade com o MongoDB) é um
serviço de banco de dados rápido, confiável e totalmente gerenciado. O Amazon DocumentDB facilita a configuração, a
operação e a escalabilidade de bancos de dados compatíveis com o MongoDB na nuvem. 