<img align="right"  src="/../images/images/hades.png?raw=true" width="144" height="200"/>
# Hades

_API REST Backend_

__Este projeto é acessado pelo [zeus](https://github.com/qualfacul/zeus)

### Arquitetura

* Framework Spring (Boot, MVC, Data)
* Servlet container Tomcat 8 embedded
* Gerenciador de dependências Gradle

### Requisitos

É necessario ter instalado o [gradle](http://gradle.org/gradle-download/).

### Build & development

Para instalar as dependencias do projeto: ``gradle build``

Inicializar spring boot: ``gradle run``, rodando no endereço ``https://localhost:6660``

_Modo debug:_ ``gradle runDebug``, com debug remoto na porta *9999*
