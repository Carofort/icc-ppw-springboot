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

## Evidencias:

#### 1.- Aplicación Docker Desktop:

![Software de Docker funcionando](assets/05-docker.png)

#### 2.- Verificación en PostgreSQL:

![Comando para visualizar al usuario](assets/05-confirmacion.png)

### Creación de clases en ```\products```:

## Evidencias:

#### 1.- Visualización de los 5 productos:

![5 Productos](assets/05-productos.png)

**Descripción:** Se insertaron (PUT) 5 registros de productos a través de BRUNO.

#### 2.- Verificación en PostgreSQL:

![Producto en específico](assets/05-producto-especifico.png)


#### 3.- Verificación en PostgreSQL:

![Comando para visualizar los productos](assets/05-lista-productos.png)


#### 4.- Explicar el flujo:

**Desde API REST hacia PostgreSQL:**

- El cliente realiza una petición HTTP enviando un JSON, el ProductsController recibe un objeto de transferencia de datos (CreateProductDto). Este DTO se pasa al ProductServiceImpl, cual utiliza el componente ProductMapper para convertir los datos de entrada al ProductModel y luego a ProductEntity. El servicio invoca al método ```.save()``` de ProductRepository, lo que hace que Hibernate traduzca la entidad en una sentencia SQL INSERT que se ejecuta en el contenedor de PostgreSQL. Durante este guardado, los interceptores de la clase abstracta BaseEntity (como ```@PrePersist```) se encargan de asignar automáticamente los valores de auditoría, como la fecha de creación en createdAt y el estado lógico de eliminación.

**De PostgreSQL hacia la API REST:**

- Tras la inserción, PostgreSQL genera el IDENTITY. Hibernate mapea este registro resultante de vuelta a un objeto ProductEntity. El flujo regresa a la capa de servicio, donde se vuelve a pasar por el ProductMapper para transformar la entidad en un ProductResponseDto. Este DTO final es el que retorna el controlador al cliente en formato JSON, consumiendo las propiedades internas de la base de datos y mostrando solo la información requerida.


# Práctica 6 (Spring Boot): Validación de DTOs y Control de Datos de Entrada

## Evidencias:

#### 1.- Validación POST:

![Validacion POST en Bruno](assets/06-post-invalido.png)

#### 2.- Validación PATCH:

![Validacion PATCH en Bruno](assets/06-patch-invalido.png)
