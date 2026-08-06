# Cafeteca Nebula

---

## Tabla de contenidos

- [Resumen](#resumen-ejecutivo)
  - [Descripción](#descripción)
  - [Problema identificado](#problema-identificado)
  - [Solución](#solución)
  - [Arquitectura](#arquitectura)
- [Requerimientos](#requerimientos)
- [Instalación](#instalación)
  - [Ambiente de desarrollo](#ambiente-de-desarrollo)
  - [Ejecución de pruebas](#ejecución-de-pruebas)
  - [Despliegue en producción local](#despliegue-en-producción-local)
- [Configuración](#configuración)
- [Uso](#uso)
  - [Manual del usuario final](#manual-del-usuario-final)
  - [Manual del administrador](#manual-del-administrador)
- [Contribución](#contribución)
- [Roadmap](#roadmap)

---

## Resumen

### Descripción
Cafeteca Nebula es un sistema web integral diseñado para la gestión y control de inventario de libros, registro de clientes y administración de préstamos y devoluciones en un entorno de biblioteca/cafetería.

### Problema identificado
La falta de un sistema centralizado de control provocaba pérdidas de catálogo, dificultades para rastrear préstamos activos, inconsistencias en las fechas de devolución y ausencia de perfiles con roles de seguridad adecuados para administrar la operación.

### Solución
Una plataforma web desarrollada con Spring Boot y MySQL que permite la administración en tiempo real de libros, clientes y préstamos, incorporando seguridad basada en roles, interfaz responsiva y precarga automática de datos de prueba para facilitar el arranque.

### Arquitectura
El sistema implementa un patrón MVC (Modelo-Vista-Controlador):

- Cliente / Navegador Web
- Spring MVC / Controllers (protegido por Spring Security)
- Capa de Servicios / Lógica de Negocio
- Spring Data JPA / Repositorios
- Base de Datos MySQL / XAMPP

---

## Requerimientos

### Servidores y entorno
- Servidor Web / Aplicación: Tomcat embebido (incluido en Spring Boot).
- Servidor de Base de Datos: MySQL Server 8.0+ (compatible con XAMPP / MariaDB).

### Versiones de Software
- Java Development Kit (JDK): Versión 17 o superior.
- Apache Maven: Versión 3.8+ (o utilizar el wrapper ./mvnw).
- IDE Recomendado: IntelliJ IDEA / Eclipse / VS Code.

### Paquetes y dependencias adicionales
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-thymeleaf
- mysql-connector-j

---

## Instalación

### Ambiente de desarrollo

1. Clonar el repositorio:
   git clone https://github.com/alejandrolaram/cafeteca-nebula
   cd cafeteca-nebula

2. Configurar la base de datos local:
   - Inicia XAMPP y arranca el módulo de MySQL.
   - Abre phpMyAdmin (http://localhost/phpmyadmin) o tu cliente MySQL preferido y crea la base de datos:
     CREATE DATABASE cafeteca_nebula_db;

3. Ejecutar la aplicación:
   ./mvnw spring-boot:run
   - La aplicación estará disponible en http://localhost:8080.

---

### Ejecución de pruebas

Para ejecutar el conjunto de pruebas unitarias y de integración manualmente mediante Maven:

./mvnw test

---

### Despliegue en producción local

1. Generar el archivo ejecutable JAR:
   ./mvnw clean package -DskipTests

2. Ejecutar el paquete compilado:
   java -jar target/nebula-0.0.1-SNAPSHOT.jar

---

## Configuración

La configuración del sistema se administra dentro del archivo src/main/resources/application.properties:

### Configuración general
spring.application.name=cafeteca-nebula
server.port=8080

### Conexión a Base de Datos MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/cafeteca_nebula_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=

### Configuración JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

---

## Uso

### Manual del usuario final
- Consulta de catálogo: Acceso a la lista completa de libros disponibles y su estatus (Disponible / Prestado).
- Solicitud de préstamo: Permite asociar un libro disponible con un cliente registrado.
- Devoluciones: Permite marcar libros prestados como devueltos para reincorporarlos al catálogo.

### Manual del administrador
- Credenciales por defecto:
  - Usuario: admin
  - Contraseña: admin123
- Funciones de gestión:
  - Alta, modificación y eliminación en cascada de Libros.
  - Alta, edición y eliminación de Clientes.
  - Monitoreo y gestión global de historial de préstamos.

---

## Contribución

Sigue estos pasos para colaborar en el proyecto:

1. Clonar el repositorio:
   git clone https://github.com/alejandrolaram/cafeteca-nebula

2. Crear una rama para tu funcionalidad:
   git checkout -b feature/nombre-de-tu-feature

3. Realizar los cambios y hacer commit:
   git add .
   git commit -m "feat: descripción clara del cambio"

4. Enviar los cambios a GitHub:
   git push origin feature/nombre-de-tu-feature

5. Crear un Pull Request:
   - Ve al repositorio en GitHub y abre un Pull Request desde tu rama hacia develop.
   - Espera la revisión y ejecución de los tests de integración continua antes de realizar el merge.

---

## Roadmap

Próximas funcionalidades planeadas para futuras versiones:

- Búsqueda avanzada y filtros: Búsqueda por autor, género, ISBN y fechas de préstamo.
- Notificaciones por email: Alertas automáticas para avisar la proximidad de la fecha límite de devolución.
- API RESTful Completa: Exposición de endpoints para integración con aplicaciones móviles.
- Integración de módulo del inventario de la cafetería.
