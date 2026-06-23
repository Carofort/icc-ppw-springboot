# Práctica 1 (Spring Boot): Instalación, Configuración Inicial y Primer Endpoint

### Carolina Fortmann

## Evidencias de la práctica 01

#### 1.- Verificación de Java:

![Versión de Java](assets/01-javaVersion.png)

#### 2.- Servidor Spring Boot ejecutándose:

![Ejecución del servidor](assets/01-bootRun.png)

#### 3.- Endpoint /api/status funcionando en el navegador:

![EndPoint](assets/01-endPoint.png)

#### 4.- Comando de verifiación de archivo:

```ls ./src/main/java/ec/edu/ups/icc/fundamentos01/controllers/```

![Verificación del archivo](assets/01-comando.png)

#### 5.- Responder:

- **¿Qué entendió sobre el funcionamiento del endpoint?**  

El EndPoint funciona como una puerta de enlace cuando se entra a la URL, el servidor recibe una petición GET, activando el método dentro del controlador gracias a ``@RestController`` y ``@GetMapping``.  Luego devuelve de manera automática un objeto JSON.

- **La función general de Spring Boot en la creación del servidor**

La función principal es crear un Backend más sencillo sin necesidad de instalar un servidor aparte. Esto permite que la aplicación sea autónoma, configure todo lo necesario de manera interna y arranque el servidor web con un solo comando.

# Práctica 3: API Rest
## Capturas 18/06

#### 1.- Localhost del nuevo recurso Students:

![Creacion de Students](assets/01-nuevoRecurso.png)

#### 2.- Students/count:

![Conteo de Students](assets/01-count.png)


# Práctica 5 (Spring Boot): Persistencia real con PostgreSQL, Entidades JPA y Repositorios

#### 1.- Aplicación Docker Desktop:

![Software de Docker funcionando](assets/05-docker.png)

#### 2.- Verificación en PostgreSQL:

![Comando para visualizar al usuario](assets/05-confirmacion.png)
