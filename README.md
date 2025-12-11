# HelpDeskU Project: Sistema de Gestión de Tickets 💻

## Descripción
**HelpDeskU** es un sistema de gestión de tickets de soporte desarrollado en Java. Su funcionalidad principal es recibir problemas o solicitudes de usuarios y asignarlos a departamentos específicos.

El sistema incorpora un **módulo de análisis de texto** que clasifica automáticamente la descripción de cada ticket, determinando:
1. **Categoría Técnica**: Área del problema 
2. **Categoría Emocional**: Estado de ánimo percibido del usuario

## Estructura del Proyecto

El proyecto sigue una arquitectura organizada por capas, enfocándose en la separación de responsabilidades:

- 📦 **`bl.entities`**: Entidades fundamentales (POJOs) como `Usuario`, `Ticket` y `Departamento`.
- ⚙️ **`bl.util`**: Herramientas de soporte, como la configuración (`ConfigPropertiesReader`), encriptación (`PasswordEncrypt`), y enumeraciones (`ListaRoles`, `EstadoTicket`).
- 🧠 **`bl.analytics`**: Módulo de Inteligencia de Negocio para el procesamiento y clasificación de texto de los tickets.
- 🧱 **`dl` (Data Layer)**: Objetos de Acceso a Datos (DAOs) para la comunicación directa con la base de datos.
- 💼 **`bl.logic`**: Clases Gestoras que encapsulan la lógica de negocio y coordinan las operaciones entre las otras capas.

## Tecnologías Utilizadas
- **Lenguaje**: Java
- **Base de Datos**: MySQL (o compatible con JDBC)
- **Persistencia**: JDBC (con librería de acceso a datos personalizada)
- **Seguridad**: Encriptación de contraseñas con **BCrypt** (librería `at.favre.lib.crypto.bcrypt`).

## Configuración y Ejecución 🛠️

Para ejecutar el proyecto localmente, es necesario configurar la base de datos y sus credenciales.

### 1. Base de Datos
Debe crear el esquema SQL con todas las tablas requeridas por los DAOs (`usuarios`, `departamentos`, `tiquetes`, `palabras_tecnicas`, `palabras_emociones`, etc.).

### 2. Archivo de Propiedades

Configure las credenciales de la base de datos en el archivo **`src/cr/ac/ucenfotec/sortiz0640/config.properties`**.
