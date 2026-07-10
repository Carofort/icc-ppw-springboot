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


# Práctica 7 (Spring Boot): Manejo Global de Errores y Excepciones

## Evidencias:

#### 1.- Captura de error por producto inexistente:

![Error de producto no existente](assets/07-inexistente.png)

#### 2.- Captura de error por validación de DTO:

![Error de validación DTO](assets/07-badRequest.png)


# Práctica 8 (Spring Boot): Relaciones ManyToOne, Foreign Keys y Consultas Relacionales

## Captura:

#### 1.- Consulta de productos por categoría:

![GET Productos con owner y category](assets/08-getProducts.png)

#### 2.- Responder:

**¿Cómo se relaciona ProductEntity con UserEntity y CategoryEntity usando @ManyToOne y @JoinColumn?**

Estas anotaciones se implementan en la clase ```ProductEntity```:

```java
@ManyToOne(optional = false, fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private UserEntity owner;
```

- ```@ManyToOne``` significa que muchos productos pueden pertenecer a un mismo usuario. Con **optional = false** indicamos que el producto no puede existir con esta relación. También utiliza el fetch type **LAZY** el cual  indica que la entidad relacionada se carga solo cuando se accede a ella.

- ```@JoinColumn(name = "user_id")``` crea la columna de la clave foránea **user_id** en la tabla products. Con **nullable = false** nos aseguramos que este campo sea obligatorio.

En resumen, usando ```@ManyToOne``` y ```@JoinColumn``` cada registro de products almacena las claves foráneas **user_id** y **category_id**, creando la relación entre un producto, su propietario y su categoría, mientras que un mismo usuario o una misma categoría pueden estar asociados a varios productos.


# Práctica 9 (Spring Boot): Request Parameters, Consultas Relacionadas y Filtrado con JPA

## Capturas:

#### 1.- Consulta con filtros por usuario:

![Consulta filtrada desde el usuario a productos](assets/09-consulta-user.png)

#### 2.- Consulta con filtros por precios:

![Consulta filtrada por precios ](assets/09-consulta-precio.png)

#### 3.- Responder:

- **¿Por qué se usa ProductService y ProductRepository para consultar productos aunque el endpoint esté dentro del contexto /users/{id}/products o /categories/{id}/products?**

Porque en sí el recurso que se está utilizando es Productos, no los datos de Usuarios o Categorías. Toda la lógica de negocio recae en las clases de Productos. Con ProductRepository podemos realizar una consulta explícita sin tener que, por ejemplo a través de Usuarios realizar filtrados para llegar a Productos.

- **¿Qué cambió al pasar de Product N ── 1 Category a Product N ── N Category?**

Su relación pasa a ser ```@ManyToMany```, lo que significa que ambas entidades estan relacionadas entre sí en una tabla a parte, ya no en un atributo dentro de Productos. Ahora las categorías se manejan dentro de una colección.


# Práctica 10 (Spring Boot): Paginación de Productos con Page, Slice y Pageable

## Capturas:

#### 1.- Captura de respuesta con Page:

![Consulta empleando page en productos](assets/10-respuesta-page.png)

#### 2.- Captura de respuesta con Slice:

![Consulta empleando slice en productos](assets/10-respuesta-slice.png)

#### 3.- Captura de error por paginación inválida:

![Consulta empleando page en productos pero con filtración inválida](assets/10-respuesta-error.png)

#### 5.- Captura de endpoint de categoría con Page:

![Consulta empleando page en categorias usando endpoint de productos](assets/10-categoria-paginado.png)

#### 6.- Captura de endpoint de categoría con Slice:

![Consulta empleando slice en categorias usando endpoint de productos](assets/10-categoria-slice.png)

#### 7.- Responder:

- **¿Cuál es la diferencia entre Page y Slice?**

Page devuelve una respuesta de paginación más detallada en contarste a Slice. Mientras Page devuelve información como: número total de registros, total de páginas, página actual y su tamaño, Slice solo devuelve los elementos de la página y la información necesaria para saber si existe una página siguiente, sin calcular el total de registros ni el total de páginas. Por eso realiza menos trabajo y suele ser más eficiente cuando solo se necesita navegar secuencialmente.

- **¿Por qué la paginación debe aplicarse en el repositorio y no después de traer todos los datos en memoria?**

Porque la base de datos puede devolver únicamente los registros que se necesitan para la página solicitada utilizando LIMIT y OFFSET. Si primero se cargaran todos los registros en memoria y luego se aplicara la paginación: se consumiría mucha más memoria y aumentaría el tiempo de respuesta.

En resumen, con el buen manejo de paginación, la base de datos filtrará y limitará los resultados antes de enviarlos a la aplicación.


# Práctica 11 (Spring Boot): Autenticación JWT, Autorización por Roles y Protección de Endpoints

## Capturas:

#### 1.- Captura de registro exitoso:

![POST de registro en AUTH](assets/11-register.png)


#### 2.- Captura de login exitoso:

![POST de login en AUTH](assets/11-register.png)


#### 3.- Captura de endpoint protegido sin token:

![Error 401 en GET products sin Token](assets/11-endpoint-noToken.png)


#### 4.- Captura de endpoint protegido con token:

![Consulta exitosa en products con Token](assets/11-endpoint-Token.png)


# Práctica 12 (Spring Boot): Protección de Endpoints con Roles

## Capturas:

#### 1.- Captura de usuario autenticado:

![Consumo endpoint me](assets/12-endpoint-me.png)

#### 2.- Captura de acceso denegado por rol:

![Error de consumo productos por user](assets/12-products-user.png)


#### 3.- Captura de acceso permitido por rol ADMIN:

![Consumo productos por admin](assets/12-products-admin.png)

#### 4.- Responder:

- **¿Cuál es la diferencia entre autenticación y autorización?**

La autenticación verifica la identidad del usuario, mientras que la autorización determina qué acciones puede realizar ese usuario una vez autenticado.


- **¿Por qué GET /api/products debe ser solo para ADMIN, mientras GET /api/products/page puede ser consumido por cualquier usuario autenticado?**

Porque ```GET /api/products``` devuelve todos los productos sin paginación, lo que puede generar un alto consumo de recursos del servidor. Por ello se restringe a administradores.

Al contrario, ```GET /api/products/page``` devuelve únicamente una parte de los datos mediante paginación, limitando la cantidad de registros enviados en cada solicitud. 

# Práctica 13 (Spring Boot): Validación de Propiedad de Recursos

## Capturas:

#### 1.- Captura de creación de producto con usuario autenticado:

![POST products de User A](assets/13-product-userA.png)

#### 2.- Captura de bloqueo por producto ajeno:

![Error bloqueo](assets/13-products-userB.png)

#### 3.- Captura de eliminación de producto ajeno bloqueada:

![Error delete products](assets/13-delete.png)

#### 4.- Captura de ADMIN modificando producto ajeno:

![ADMIN modifica productos](assets/13-products-admin.png)

#### 5.- Captura de ```/slice```:

![Consumo de slice de User A](assets/13-slice.png)

#### 6.- Captura de ```/page```:

![CConsumo de page](assets/13-page.png)

#### 7.- Responder:


- **¿Qué es ownership?**

Ownership determina qué producto le pertenece a tal usuario en específico y solo usuarios con el rol de ADMIN podrán hacer las modificaciones (a parte del dueño orginial).

- **¿Por qué no es seguro recibir userId en CreateProductDto?**

Porque el cliente podría modificar el userId y crear un producto a nombre de otro usuario. 

- **¿Cuál es la diferencia entre autorización por rol y autorización por ownership?**

La autorización por rol controla qué acciones puede realizar un usuario según su rol. En cambio, la autorización por ownership verifica si el recurso pertenece al usuario autenticado antes de permitir una acción. 