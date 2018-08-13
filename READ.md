Requisitos: JRE 1.8 para execução do servidor;
			JDK 1.8 para compilação do projeto;

Fazer pull do projeto e executar o comando: mvn install

Para executar o projeto no diretório atual, Executar o comando mvn spring-boot:run

Para executar o projeto no Tomcat(que use JRE1.8), navegar a pasta target e mover o arquivo papafeed.war para a pasta webapps do Tomcat e aguardar o deploy.

O serviço ficará disponivel em http://localhost:8080/feed

Implementado diretamente como webservice;
Autentitação foi implementada com Spring Security.
