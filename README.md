# MutantDetectorApi
_Api Rest para detección de ADN Mutante para Magento_

## Construido con 
 - [JAVA JDK 14.0.2](https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html)
 - [IntelliJ IDEA](https://www.jetbrains.com/help/idea/sharing-your-ide-settings.html)
 - [Maven](https://maven.apache.org): 
 - [Spring Boot](https://spring.io/projects/spring-boot)
 - [JUnit](https://junit.org/junit5/)
 - [JaCoCo](https://www.eclemma.org/jacoco/)
 - [AWS](https://aws.amazon.com)
 - [DynamoDB](https://aws.amazon.com/es/dynamodb/)
 - [Redis](https://redis.io)

## Modo de Uso
- Documentacion Api: http://ec2-18-222-172-215.us-east-2.compute.amazonaws.com:8080/swagger-ui.html#/

## Generacion de Paquete y Test Coverage
 desde el directorio de la aplicacion ejecutar
```mvnw clean package``` en windows o ```mvn clean package``` en linux 
esto genera el archivo ```/target/adn-0.0.1-SNAPSHOT.jar``` y el archivo ```/target/site/jacoco/index.html``` con el resultado de la cobertura de la prueba utilizando JaCoCo
Tambien se puede ejecutar desde intelliJ IDEA con la configuración "All In Dna" 

De los test unitarios se excluyeron los siguientes packages:
 - ```com.magneto.dna.config``` (clases de configuración)
 - ```com.magneto.dna.entity``` (entidades)
 - ```com.magneto.dna.dto``` (dtos de la APi)
 Estos fueron excluidos ya que no sumaba valor realizar test unitarios de las clases que componen dicho paquete

para intelliJ IDEA hay dos configuraciones disponibles:
 - DnaApplication: Aplicacion Java
 - All in Dna: Test Unitarios

## Ambiente Local (IntelliJ IDEA)
1. Abrir o Importar Archvo\
 ![menu importar archivo](/images/import_001.png)
2. Seleccionar el archivo pom.xlm que se encuantra el el directorio del proyecto\
 ![menu_seleccionar_pom](/images/import_002.png)
3. Abrir como Proyecto\
 ![menu_abrir_proyecto](/images/import_003.png)

4. El proyecto dispone de 2 configuraciones:
 4.1 ```DnaApplication``` (Aplicacion Java)
 4.2 ```All in Dna``` (Test Unitarios)
 ![menu_configuraciones](/images/config_001.png\
 ![menu_configuraciones](/images/config_002.png)\

## Implementación Actual
- Proveedor: AWS
- Url Base Api: http://ec2-18-222-172-215.us-east-2.compute.amazonaws.com:8080
- Documentacion Api: http://ec2-18-222-172-215.us-east-2.compute.amazonaws.com:8080/swagger-ui.html#/
- Base de Datos: DymamoDB (Base de datos documental de AWS para almacenar las muestras de ADN)
- Container EC2 con tomcat ejecutando la aplicacion JAVA
- Container EC2 ejecuntando una instancia de REDIS (BD para guardar las estadisticas)

## Autor
Fernando Acevedo ([drmkace@yahoo.com.ar](mailto:drmkace@yahoo.com.ar))
