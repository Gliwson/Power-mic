[![patreon](https://c5.patreon.com/external/logo/become_a_patron_button.png)](https://www.patreon.com/bePatron?u=12280211)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

# Keycloak REST Admin API Demonstration

This project demonstrates how a third-party application can communicate and manage Keycloak resources via API.

## Instructions

1.) Korzystając z Spring STS IDE, utwórz nowy projekt za pomocą szablonu Spring Starter Project.
2.) Pamiętaj o dodaniu następujących zależności: keycloak-spring-boot-starter i spring-boot-starter-security.
3.) Musimy rozszerzyć klasę KeycloakWebSecurityConfigurerAdapter, jak określono w poniższej dokumentacji zabezpieczającej aplikacje keycloak. Zobacz poniższy kod. 
4.) Z powodu niektórych problemów Keycloak musimy rozszerzyć klasę KeycloakSpringBootConfigResolver. 
5.) Utworzyłem klasę narzędzia, która pomoże nam zainicjować klasę Keycloak, której możemy użyć do komunikacji i zarządzania instancją Keycloak - KeycloakAdminClientUtils. 
6.) Potrzebujemy następnie usługi, aby uzyskać lub zarządzać informacjami z Keycloak w zależności od roli użytkownika. Na przykład użytkownik, którego używam, pełni rolę zarządzania, co oznacza, że ​​mogę wywołać prawie wszystkie interfejsy API dostarczone przez Keycloak. W moim przykładzie zwracam rolę użytkownika oraz jego profil. Zobacz klasę KeycloakAdminClientService. 7.) Tworzę klasę kontrolera REST, aby korzystać z usługi # 6 w wersji demonstracyjnej. Zobacz KeycloakController. 
8.) Nie zapomnij podać konfiguracji Keycloak w application.properties.

## Run as Standalone

1.) Pobierz i uruchom keycloak. 
2.) Utwórz nową dziedzinę i użytkowników, importując plik config / balambgarden-realm.json. 
3.) Jeśli używasz IDE, upewnij się, że ustawiłeś zmienną środowiskową keycloak.secret = 332e78cb-0487-46a8-949d-7c2a09cd380c. Jest to używane podczas wywoływania interfejsu API getProfile. 
4.) Teraz jesteśmy gotowi do przeprowadzenia testów w kolekcji listonosza.

## Run as a Dockerized Container

You must have docker installed on your local machine.

1.) Make sure to change the value of keycloak.auth-server-url in application.properties to where you will be running docker-compose.
2.) Build the application. Whether by using mvn in command line or in your IDE.
3.) Open your terminal.
4.) Navigate to the root folder of the project.
5.) Enter: "docker-compose up --build" without the " and press enter.
6.) Now we're ready to run the tests inside the postman collection.

If will take a while during the first time as it will download Keycloak.

## Run Keycloak as a Atandalone Container

docker run -d --name=keycloak10 -p 8081:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=kerri jboss/keycloak:10.0.1

## Testing

1.) Install Postman 
2.) Import the postman environment settings deployment/czetsuya-course.postman_environment.json 
and make sure to update the value of keycloak_url and api_url to where you deploy keycloak and running this project.
3.) Import the collection deployment/keycloak-admin-api.postman_collection.json.
4.) Run the Login test first so that the access_token will be initialized.

## References
 
 - https://czetsuya-tech.blogspot.com/2020/03/keycloak-admin-rest-api-in-spring-boot.html
 - https://czetsuya-tech.blogspot.com/2019/10/docker-and-kubernetes.html
 - https://hub.docker.com/r/jboss/keycloak/
 - https://spring.io/guides/gs/spring-boot-docker/
 - https://www.keycloak.org/documentation
 - https://www.keycloak.org/docs-api/10.0/rest-api/index.html

## Authors

 * **Edward P. Legaspi** - *Java Architect* - [czetsuya](https://github.com/czetsuya)
