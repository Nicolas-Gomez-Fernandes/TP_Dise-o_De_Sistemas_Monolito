# TP Diseño De Sistemas - Monolito

## 📋 Descripción del Proyecto

Este es un proyecto monolítico desarrollado en **Java 21** y **Spring Boot 3.3.0** para el Trabajo Práctico de Diseño de Sistemas (DDSI 2025).

### ¿Por qué Monolito?

A diferencia de una arquitectura de microservicios (que requiere múltiples aplicaciones, bases de datos y comunicación por red), este proyecto adopta un enfoque monolítico por simplicidad:

- **Una sola aplicación**: Todo el código está en un único proyecto Java
- **Una sola base de datos**: `metamapa_db` centralizada
- **Sin comunicación por red**: Los servicios se llaman directamente entre sí (métodos Java)
- **Seguridad centralizada**: Manejada en un único punto (`SecurityConfig`) con sesiones HTTP

## ⚠️ IMPORTANTE: Problema Conocido

**Si obtienes errores de compilación** relacionados con `cannot find symbol: method getTitulo()` o similares, consulta **[LOMBOK_FIX.md](LOMBOK_FIX.md)** para soluciones.

## 🚀 Requisitos Previos

### Java Development Kit (JDK) 21

Este proyecto **requiere Java 21 LTS** para compilar y ejecutar.

#### Instalación en Windows

**Opción 1: Con winget (recomendado)**
```powershell
winget install --id Eclipse.Temurin.21.JDK -e
```

**Opción 2: Descarga manual**
- Descargar desde [Adoptium Temurin](https://adoptium.net/temurin/releases/?version=21)
- Instalar y configurar `JAVA_HOME`

#### Verificar instalación
```bash
java -version
# Debe mostrar: openjdk version "21.x.x"
```

### Apache Maven

**Instalación en Windows**
```powershell
# Con Scoop
scoop install maven

# O con winget
winget install Apache.Maven
```

**Verificar instalación**
```bash
mvn -v
# Debe mostrar Maven 3.x.x y Java version: 21.x.x
```

## 🔧 Configuración del Proyecto

### Base de Datos MySQL

1. Crear la base de datos:
```sql
CREATE DATABASE metamapa_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Configurar credenciales en `src/main/resources/application.properties`

### Compilar y Ejecutar

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 📦 Tecnologías Principales

- **Java 21** (LTS)
- **Spring Boot 3.3.0**
- **Spring Data JPA** (persistencia)
- **Spring Security** (autenticación y autorización)
- **Thymeleaf** (motor de plantillas)
- **MySQL** (base de datos)
- **WebFlux** (cliente HTTP reactivo para fuentes externas)

## 🏗️ Estructura del Proyecto

```
src/
└── main/
    ├── java/
    │   └── ar/edu/utn/frba/ddsi/metamapa/
    │       ├── MetamapaMonolitoApplication.java  # 🚀 Archivo main
    │       ├── config/
    │       │   └── SecurityConfig.java           # Configuración Spring Security
    │       ├── controllers/                      # Controladores REST y MVC
    │       │   ├── AdminController.java          # Panel de administración (E5)
    │       │   ├── ColeccionController.java      # Gestión de colecciones
    │       │   └── LoginController.java          # Autenticación
    │       ├── entities/                         # Entidades JPA (modelo de dominio)
    │       │   ├── Coleccion.java                # Colección de hechos (E1)
    │       │   ├── Hecho.java                    # Hecho individual (E1)
    │       │   ├── SolicitudEliminacion.java     # Solicitudes de borrado (E5)
    │       │   └── Usuario.java                  # Usuarios del sistema (E5)
    │       ├── repositories/                     # Repositorios Spring Data JPA
    │       │   ├── ColeccionRepository.java
    │       │   ├── HechoRepository.java
    │       │   ├── SolicitudEliminacionRepository.java
    │       │   └── UsuarioRepository.java
    │       └── services/                         # Lógica de negocio
    │           ├── ColeccionService.java         # Gestión de colecciones (E1-E4)
    │           ├── CustomUserDetailsService.java # Autenticación (E5)
    │           ├── FuenteEstaticaService.java    # Fuente CSV (E2)
    │           └── FuenteProxyService.java       # Fuente HTTP (E3)
    └── resources/
        ├── application.properties                # Configuración de la app
        └── templates/                            # Vistas Thymeleaf
            ├── colecciones.html                  # Lista de colecciones
            ├── login.html                        # Página de login (E5)
            └── admin/
                └── solicitudes.html              # Panel admin (E5)
```

**Paquete base**: `ar.edu.utn.frba.ddsi.metamapa`

Todos los componentes Spring (controladores, servicios, repositorios, configuración) están dentro de este paquete para que `@SpringBootApplication` los detecte automáticamente mediante component scanning.

## 🔐 Seguridad (E5)

El proyecto implementa Spring Security con:
- Autenticación basada en sesiones HTTP
- Control de acceso por roles (ADMIN, USER)
- Configuración centralizada en `SecurityConfig`

## 🧪 Testing

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con cobertura
mvn verify
```

## 📝 Notas del Desarrollo

- **Migración desde microservicios**: Este proyecto es una simplificación del diseño original de microservicios
- **Java 21**: Aprovecha las últimas características LTS de Java (Virtual Threads, Pattern Matching, etc.)
- **Spring Boot 3.3**: Compatible con Java 21 y Jakarta EE 10
