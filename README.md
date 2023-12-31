# Teste de case Unifor via Profectum

## Sobre o case:
O case do teste propõe criar um sistema para criação de matrizes curriculares de cursos, contendo disciplinas e semestres,
todos gerenciados pelo sistema. O sistema deve ter usuários, cada um com permissões e responsabilidades definidas por roles: administradores,
coordenadores, professores e alunos:

* Administrador: Incluir, excluir, atualizar e visualizar usuários;
* Coordenador de cursos: Incluir, excluir, atualizar e visualizar os semestres, cursos e disciplinas e realizar a montagem da matriz curricular;
* Professor e aluno: Visualizar a matriz curricular.

O projeto consiste em etapas de front e back-end.

## Regras de negócio aplicadas:
Para o teste foi proposta uma regra de negócio simples onde:
* Várias matrizes curriculares podem ter vários cursos;
* Vários cursos podem ter vários semestres;
* Vários semestres podem ter várias disciplinas;

Dentro das classes, existem comentários do tipo Javadoc explicando um pouco das regras de negócio e decisões tomadas.

Utilizando o Spring Data com JPA e Hibernate, foi usada a anotação @ManyToMany para fazer as associações com chave estrangeiras. Para um projeto comercial,
o ideal é montar a base de dados diretamente na mão, planejando e ajustando cada chave primária e estrangeira, afim de ter um controle maior e mais segurança
nos dados.

## Base de dados:
O Banco de Dados utilizado no projeto é o H2 (Banco de dados em memória), afim de facilitar a execução para teste.
Para visualizar o console da  base de dados, acesse: http://localhost:8080/h2-console.

Obs.: O banco MySQL também pode ser utilizado, uma vez que esta dependênica está instalada no projeto.

## Tecnologias utilizadas:
* Java 17 (JDK)
* Maven
* H2 Memory Database
* Spring Framework 3.2.0 - SNAPSHOT
* Spring Boot DevTools
* Spring Data (JPA + Hibernate)
* Spring REST API

## Como executar o projeto:
Dependendo da IDE utilizada, você deve abrir como um projeto existente. No caso do eclipse, Importe como um projeto Maven e aguarde as dependências serem baixadas.
Com o clique do botão direito em cima da pasta do projeto, execute como Spring Boot App, e aguarde a aplicação subir. Logo após a aplicação subir, os endpoints já podem sem utilizados, já que o banco de dados é em memória (uma vez o projeto sendo reiniciado ou parado, o banco de dados é apagado).

Já no IntelliJ, abra como um projeto existente e marque a opção Trust in authors, caso apareça. Espere as dependências do Maven baixarem, e logo em seguida, com o botão direito em cima da pasta do projeto execute-o. Logo após a aplicação subir, os endpoints já podem sem utilizados, já que o banco de dados é em memória (uma vez o projeto sendo reiniciado ou parado, o banco de dados é apagado).
