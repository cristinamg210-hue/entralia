MEMORIA DEL PROYECTO

ÍNDICE
1. Introducción
2. Objetivos del proyecto
3. Requisitos del sistema
   3.1 Requisitos funcionales
   3.2 Requisitos no funcionales
4. Tecnologías utilizadas
5. Arquitectura del sistema (MVC)
6. Diseño de la base de datos
7. Diseño del software
   7.1 Diagrama de casos de uso
   7.2 Diagrama de clases
   7.3 Diagramas de secuencia
   7.4 Relación con MVC
8. Implementación
9. SEO y aspectos de frontend
10. Entorno de desarrollo y despliegue
11. Control de versiones
12. Pruebas
13. Conclusiones y mejoras futuras
_______________________________________

1. Introducción
Este proyecto es una aplicación web para gestionar y vender entradas de distintos eventos. La idea es que cualquier usuario pueda ver los eventos disponibles, elegir el tipo de entrada y completar la compra de forma sencilla.
El sistema controla el stock y, en los eventos que lo necesitan, pone asientos automáticamente para evitar errores o que se dupliquen.

El desarrollo está hecho con Java y Spring Boot, siguiendo el patrón MVC, y las vistas están construidas con Thymeleaf. La base de datos es MySQL, gestionada con XAMPP.

En esta memoria explico cómo está construido el proyecto, las tecnologías utilizadas, los diagramas principales, las pruebas realizadas y una reflexión final sobre el proceso.

2. Objetivos del proyecto
El objetivo principal es crear una aplicación web funcional que permita gestionar eventos y vender entradas de forma clara y organizada.

Objetivos más importantes:
- Mostrar eventos con su información.
- Permitir seleccionar tipos de entrada y cantidades.
- Controlar el stock de forma automática.
- Asignar asientos cuando el evento lo requiera.
- Registrar compras y permitir que el usuario las consulte.
- Aplicar todo lo que aprendí en el curso (Spring Boot, MVC, MySQL, sesiones…).
- Mantener un código organizado y fácil de ampliar.
- Seguir un flujo de trabajo real: diseño, desarrollo, pruebas y documentación.

3. Requisitos del sistema
  3.1 Requisitos funcionales
   - Ver todos los eventos disponibles.
   - Consultar la información de un evento.
   - Seleccionar tipo de entrada y cantidad.
   - Control de stock. 
   - Asignación automática de asientos.
   - Registrar compras.
   - Ver compras realizadas.
   - Inicio de sesión.
   - Gestión de eventos para administrador.

    3.2 Requisitos no funcionales
    - Interfaz clara y fácil de usar.
    - Buen rendimiento.
    - Seguridad básica (control de sesión, validaciones y manejo correcto de errores).
    - Código mantenible.
    - Compatibilidad con navegadores comunes, al estar construido con HTML, CSS y tecnologías estándar.
  
4. Tecnologías utilizadas
  - Java
  - Spring Boot (MVC, controladores, servicios, repositorios)
  - Thymeleaf para las vistas
  - MySQL como base de datos
  - XAMPP 
  - Visual Studio Code
  - Git para control de versiones

5. Arquitectura del sistema (MVC)
El proyecto sigue el patrón Modelo–Vista–Controlador:
  - Modelos: representan las entidades de la base de datos (Usuario, Evento, TipoEntrada, Compra…).
  - Controladores: reciben las peticiones y gestionan el flujo.
  - Servicios: contienen la lógica (stock, compras, asientos…).
  - Vistas: páginas HTML generadas con Thymeleaf.

6. Diseño de la base de datos
La base de datos está diseñada en MySQL y contiene las tablas principales:
  - USUARIO
  - EVENTO
  - TIPO_ENTRADA
  - COMPRA
  - DETALLE_COMPRA
  - ASIENTO (solo para eventos con asientos)
El diseño permite controlar el stock, registrar compras y mantener la información organizada.

7. Diseño del software
   7.1 Diagrama de casos de uso
   Acciones principales del usuario: ver eventos, comprar entradas, iniciar sesión, ver compras, etc.
   <img width="764" height="414" alt="image" src="https://github.com/user-attachments/assets/9dcd3ed3-2d28-40af-b794-856c5fcac882" />

   7.2 Diagrama de clases
   Muestra las entidades del sistema y sus relaciones: Usuario, Evento, TipoEntrada, Compra, DetalleCompra, Asiento…

   7.3 Diagramas de secuencia
   Explican procesos como:
   - Compra de entradas
   - Inicio de sesión
   - Asignación de asientos
     
   7.4 Relación con MVC
   Los diagramas ayudan a visualizar cómo fluye la información entre controladores, servicios, repositorios y vistas.

8. Implementación
Se ha organizado de forma modular:
  - Los controladores gestionan las peticiones.
  - Los servicios contienen la lógica (stock, compras, asientos…).
  - Los repositorios se encargan de la comunicación con la base de datos.
  - Las vistas muestran la información al usuario.

También añadí funciones extra como:
  - Subida de imágenes
  - Gestión de sesiones
  - Asignación automática de asientos
  - Control de stock

9. SEO y aspectos de fronted
No es un proyecto que publicaré pero apliqué prácticas:
  - Títulos y descripciones en cada página
  - URLs claras (siguen rutas simples)
  - Formularios organizados
  - Mensajes de error visibles
  - Imágenes para mejorar la interfaz
  - Navegación sencilla

10. Entorno de desarrollo y despliegue
  - Visual Studio Code
  - Ubuntu
  - XAMPP para MySQL
  - Spring Boot en http://localhost:8080
  - Git para control de versiones

11. Control de versiones
Aunque no pude usar GitHub durante todo el desarrollo, sí utilicé Git para guardar cambios y organizar el trabajo.

Cuando recuperé la cuenta, subí:
  - El proyecto completo
  - La carpeta docs/ con la memoria y capturas

12. Pruebas
He realizado pruebas de:
  - Navegación general
  - Visualización de eventos
  - Proceso de compra
  - Control de stock
  - Asignación de asientos
  - Inicio de sesión
  - Validaciones y mensajes de error
Durante las pruebas encontré errores que fui corrigiendo hasta que todo funcionó correctamente.

13.   Conclusiones y mejoras futuras
Este proyecto me ha servido para aplicar de forma práctica todo lo aprendido: Spring Boot, MVC, bases de datos, sesiones, vistas dinámicas y organización del código.

Mejoras que me gustaría añadir:
  - Roles avanzados
  - Mejor diseño visual
  - Generación de entradas en PDF
  - Más seguridad
Ha sido un proyecto tanto útil como práctico para entender cómo se construye una aplicación web real.
