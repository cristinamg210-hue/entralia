# Entralia — Plataforma de gestión de eventos y entradas

Este proyecto es una aplicación web hecha en Java con Spring Boot. La idea es gestionar eventos, tipos de entrada, compras y asientos. También tiene login de usuarios y un sistema completo para comprar entradas.

---

## Tecnologías utilizadas

- Java 
- Spring Boot  
- Spring MVC  
- Spring Data JPA  
- MySQL  
- Thymeleaf  
- HTML y CSS  
- Maven  

---

## Estructura del proyecto

src/
├── main/
│   ├── java/com/entralia/entralia
│   │   ├── controller/   → Controladores
│   │   ├── model/        → Entidades
│   │   ├── repository/   → Repositorios
│   │   └── service/      → Servicios
│   └── resources/
│       ├── static/    → CSS, imágenes, JS
│       └── templates/       → Vistas HTML
docs/
└── memoria.md            → Memoria del proyecto


---

## Cómo ejecutarlo

### 1. Clonar el repositorio
git clone https://github.com/cristinamg210-hue/entralia.git

### 2. Importar en el IDE

El proyecto usa Maven, así que se importa sin problemas.

### 3. Configurar la base de datos

Crear la base de datos:
CREATE DATABASE entralia;

Configurar `application.properties`:

### 4. Ejecutar               
mvn spring-boot:run

La aplicación se abre en:

http://localhost:8080

---

## Documentación

Los diagramas y la memoria están en la carpeta:

docs/

Incluye:

- Diagrama ER  
- Diagrama de clases  
- Memoria completa
  
---

## Autora

Proyecto desarrollado por **Cristina M.G.**  
DAW — Desarrollo de Aplicaciones Web

