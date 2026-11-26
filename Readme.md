# CoWorkHub – API REST para la gestión de reservas de coworking

**Sistema backend para administrar miembros, membresías, salas, reservas y servicios adicionales de una red de coworking.**

---

## 1. Problema resuelto

Los espacios de coworking necesitan administrar las reservas de sus salas, controlar el consumo de horas de sus miembros y gestionar los pagos asociados a los servicios utilizados.

Cuando estos procesos se realizan mediante hojas de cálculo o registros manuales, pueden presentarse problemas como reservas duplicadas, errores en el cálculo de costos, falta de control sobre las horas disponibles y acceso no autorizado a información de otros clientes.

**CoWorkHub** busca solucionar estas dificultades mediante una API REST desarrollada con Spring Boot, que centraliza la información y automatiza las reglas de negocio para garantizar una gestión organizada, segura y eficiente.

## 2. Descripción del problema

El proyecto parte de una red de coworking que actualmente administra sus operaciones mediante una hoja de cálculo.

El sistema necesita cubrir las siguientes necesidades:

- Administrar diferentes sedes y sus salas disponibles.
- Registrar miembros y asociarlos con planes de membresía.
- Gestionar cuentas de usuario y permisos según su rol.
- Evitar que dos reservas ocupen la misma sala en horarios superpuestos.
- Validar la disponibilidad de horarios y las condiciones de cada reserva.
- Controlar el consumo mensual de horas de los miembros.
- Calcular automáticamente los costos de las salas y los servicios adicionales.
- Aplicar una regla uniforme para el cobro de cancelaciones.
- Proteger la información mediante autenticación y autorización.

## 3. ¿Qué hice?

Desarrollé una API REST con Spring Boot, organizada para separar las responsabilidades de las diferentes capas de la aplicación y facilitar su mantenimiento y crecimiento.

Entre las funcionalidades y componentes trabajados se encuentran:

### Gestión de usuarios y roles

Implementación de un sistema de autenticación y autorización basado en roles:

- **ADMIN:** acceso completo y administración de catálogos.
- **RECEPTIONIST:** gestión de miembros y reservas.
- **MEMBER:** acceso a sus propias reservas, disponibilidad, catálogos y consumo de horas.

Se contempla la relación entre miembros y cuentas de usuario, permitiendo que cada miembro tenga una cuenta asociada para iniciar sesión.

### Gestión de catálogos

Administración de la información principal del coworking:

- Sedes (`Location`).
- Salas (`Room`).
- Equipamientos (`Equipment`).
- Planes de membresía (`MembershipPlan`).
- Servicios adicionales (`AdditionalService`).

### Gestión de miembros

Registro y administración de los clientes del coworking, incluyendo sus datos personales, estado y plan de membresía.

Se contempla la creación de la cuenta de usuario asociada cuando se registra un miembro.

### Gestión de reservas

Implementación de funcionalidades para:

- Crear reservas.
- Consultar reservas.
- Modificar reservas.
- Cancelar reservas.
- Consultar disponibilidad de salas.
- Asociar servicios adicionales a una reserva.

### Reglas de negocio

Implementación de validaciones para garantizar la integridad de las operaciones, incluyendo:

- Validación de horarios y duración de las reservas.
- Prevención de reservas superpuestas.
- Restricción de reservas para miembros suspendidos.
- Control del consumo de horas de membresía.
- Cálculo de costos de reservas y servicios adicionales.
- Aplicación de reglas de cancelación.
- Restricción de eliminación de catálogos que estén siendo utilizados.

### Seguridad

Implementación de mecanismos de seguridad para proteger los endpoints de la API:

- Autenticación mediante JWT.
- Encriptación de contraseñas con BCrypt.
- Control de acceso basado en roles.
- Protección de los recursos según los permisos de cada usuario.

## 4. Modelo de datos

El proyecto utiliza un modelo relacional compuesto por las siguientes entidades:

| Entidad | Responsabilidad |
|---|---|
| `Location` | Administración de sedes. |
| `Room` | Información y disponibilidad de salas. |
| `Equipment` | Equipamiento disponible. |
| `MembershipPlan` | Planes y horas incluidas. |
| `Member` | Información de los clientes. |
| `UserAccount` | Cuentas de acceso al sistema. |
| `Booking` | Administración de reservas. |
| `AdditionalService` | Servicios adicionales. |
| `BookingItem` | Detalle de servicios asociados a una reserva. |

Se utilizan relaciones uno a uno, uno a muchos y muchos a muchos, según las necesidades del dominio.

## 5. Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java | Lenguaje de programación. |
| Spring Boot | Desarrollo de la API REST. |
| Spring Web | Creación de endpoints REST. |
| Spring Data JPA | Persistencia y acceso a datos. |
| Spring Security | Autenticación y autorización. |
| JWT | Gestión de tokens de autenticación. |
| BCrypt | Codificación segura de contraseñas. |
| MySQL | Sistema gestor de base de datos. |
| Hibernate | Mapeo objeto-relacional (ORM). |
| Maven | Gestión de dependencias y construcción. |
| Docker | Contenerización y ejecución del entorno. |
| Swagger / OpenAPI | Documentación y prueba de endpoints. |
| Postman | Pruebas de solicitudes HTTP. |
| IntelliJ IDEA | Entorno de desarrollo. |
| Git y GitHub | Control de versiones y alojamiento del proyecto. |

## 6. Evidencias del proyecto

En esta sección se presentan capturas de pantalla que permiten observar el funcionamiento y las funcionalidades desarrolladas.

### 6.1. Documentación de la API – Swagger UI

Espacio para agregar la captura de Swagger UI mostrando los endpoints disponibles y su documentación.

![Documentación de la API con Swagger](docs/evidencias/swagger.png)

### 6.2. Autenticación y generación de token JWT

Espacio para agregar la evidencia del proceso de autenticación y generación del token de acceso.

![Autenticación JWT](docs/evidencias/login-jwt.png)

### 6.3. Pruebas de endpoints – Postman

Espacio para agregar capturas de las solicitudes HTTP realizadas y sus respectivas respuestas.

![Pruebas de endpoints con Postman](docs/evidencias/postman.png)

### 6.4. Base de datos – MySQL

Espacio para agregar una captura de las tablas y relaciones de la base de datos.

![alt text](image-1.png)

> **Nota:** Las imágenes anteriores son rutas de referencia. Debes guardar tus capturas en la carpeta `docs/evidencias/` con los nombres indicados o modificar las rutas según corresponda.

## 7. ¿Cómo ejecutar el proyecto?

### Requisitos previos

Antes de ejecutar la aplicación, asegúrate de tener instalados:

- Java JDK 25 (o la versión configurada en el proyecto).
- Maven.
- MySQL Server.
- Docker Desktop (si utilizas el entorno configurado con Docker).
- IntelliJ IDEA (opcional).
- Postman o un navegador para acceder a Swagger UI.

### Paso 1. Clonar el repositorio

```bash
git clone URL_DE_TU_REPOSITORIO
```

Ingresar a la carpeta del proyecto:

```bash
cd proyectoCoWorkHub
```

### Paso 2. Configurar la base de datos

Crear la base de datos en MySQL:

```sql
CREATE DATABASE coworkhub_db;
```

Configurar las credenciales de conexión en el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/coworkhub_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Ajusta estas propiedades según la configuración real del proyecto. Si utilizas variables de entorno o Docker Compose, configura los valores correspondientes en lugar de duplicar la configuración.

### Paso 3. Configurar las variables de entorno

Configurar las variables necesarias para la ejecución de la aplicación, especialmente las relacionadas con la conexión a la base de datos y la clave secreta JWT.

No se deben publicar contraseñas, claves secretas ni credenciales reales en el repositorio.

### Paso 4. Ejecutar la aplicación

Desde la terminal, ejecutar:

```bash
mvn spring-boot:run
```

También puedes abrir el proyecto desde IntelliJ IDEA y ejecutar la clase principal que contiene `@SpringBootApplication`.

### Paso 5. Acceder a Swagger UI

Una vez iniciada la aplicación, ingresar desde el navegador a:

```text
http://localhost:8080/swagger-ui/index.html
```

Desde Swagger UI puedes consultar la documentación y probar los endpoints disponibles.

### Paso 6. Probar la API con Postman

También puedes utilizar Postman para realizar solicitudes HTTP.

Para acceder a los endpoints protegidos:

1. Ejecutar el endpoint de inicio de sesión.
2. Obtener el token JWT.
3. Copiar el token generado.
4. Configurarlo en la autorización de Postman utilizando el esquema **Bearer Token**.
5. Ejecutar las solicitudes según los permisos del rol autenticado.

---

## 8. Estructura general del proyecto

La aplicación está organizada por responsabilidades para facilitar la separación de la lógica de negocio, la presentación y el acceso a datos.

![alt text](image.png)

> La estructura es una referencia general. Ajusta los nombres de los paquetes para que coincidan con la organización real de tu proyecto.

## 9. Evidencias:

![alt text](image-2.png)

![alt text](image-3.png)

![alt text](image-4.png)

![alt text](image-5.png)

![alt text](image-6.png)

![alt text](image-7.png)

![alt text](image-8.png)

![alt text](image-9.png)

![alt text](image-10.png)

![alt text](image-11.png)

![alt text](image-12.png)

![alt text](image-13.png)

![alt text](image-14.png)

![alt text](image-15.png)

![alt text](image-16.png)

![alt text](image-17.png)

## 9. Autor

**Sara Colmenares**

Desarrolladora Junior de Software en formación.

- GitHub: [Enlace a mi perfil](https://github.com/)
- LinkedIn: [Enlace a mi perfil](https://www.linkedin.com/)

---

**CoWorkHub – API REST para la gestión de reservas de coworking.**

Proyecto desarrollado como ejercicio práctico de desarrollo backend con Java y Spring Boot.