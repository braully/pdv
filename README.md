# pdv
Sistema de ERP web desenvolvido em Java com Spring Framework.

Live demo: https://pdv-br.herokuapp.com
usuário: gerente
senha 123

# Recursos
- Cadastro produtos/clientes/fornecedor
- Controle de estoque
- Gerenciar comandas
- Realizar venda
- Controle de fluxo de caixa
- Controle de pagar e receber
- Venda com cartões
- Gerenciar permissões de usuários por grupos
- Cadastrar novas formas de pagamentos
- Relatórios

# Instalação para teste

Para executar a versão de teste, basta compilar e executar.

## Compilação

```bash
./mvnw install
```

Alternativamente


```bash
./sh/compile.sh
```

## Execução

```bash
./mvnw spring-boot:run
```

Alternativamente

```bash
./sh/run-dev.sh
```

## Banco de dados
O Banco de dados de Teste é h2, que irá gerar dois arquivo na raiz do projeto, 
que pode ser deletado, toda inicialização caso não exista será gerada um novo banco de dados,
conforme configuração em application-dev.properties

### Esquema do banco e dados
O modo de desenvolvimento, usa o hibernate no modo update, o que irá gerar as tabelas e fazer alterações
conforme o modelo, não sendo necessário gerar nenhum script extra.
Os scripts na pasta migration-schema são rodados antes do hibernate-update.
Os scripts na pasta migrantio-data são rodados depois do hibernate-update, 
sendo local ideal para colocar dados de teste.


## Logando no sistema
Para logar no sistema, acesse http://localhost:8080 e use o usuário "gerente" e a senha "123".


# Instalação para produção
Para instalar o sistema, você deve criar o banco de dado "pdv" no mysql e configurar o arquivo application.properties
com os dados do seu usuário root do mysql e rodar o projeto pelo Eclipse ou gerar o jar do mesmo e execultar.

# Tecnologias utilizadas
- Spring Framework 5
- Thymeleaf 3
- MySQL
- Hibernate
- FlyWay

