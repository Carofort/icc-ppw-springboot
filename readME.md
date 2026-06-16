# Práctica 1 (Spring Boot): Instalación, Configuración Inicial y Primer Endpoint

### Carolina Fortmann

## Evidencias de la práctica 01

#### 1.- Verificación de Java:

![Versión de Java](assets/01-javaVersion.png)

#### 2.- Servidor Spring Boot ejecutándose:

![Versión de Java](assets/01-bootRun.png)

#### 3.- Endpoint /api/status funcionando en el navegador:

![Versión de Java](assets/01-endPoint.png)

#### 4.- Comando de verifiación de archivo:

```ls ./src/main/java/ec/edu/ups/icc/fundamentos01/controllers/```

![Versión de Java](assets/01-comando.png)

#### 5.- Responder:

- **¿Qué entendió sobre el funcionamiento del endpoint?**  

El EndPoint funciona como una puerta de enlace cuando se entra a la URL, el servidor recibe una petición GET, activando el método dentro del controlador gracias a ``@RestController`` y ``@GetMapping``.  Luego devuelve de manera automática un objeto JSON.

- **La función general de Spring Boot en la creación del servidor**

La función principal es crear un Backend más sencillo sin necesidad de instalar un servidor aparte. Esto permite que la aplicación sea autónoma, configure todo lo necesario de manera interna y arranque el servidor web con un solo comando.