# 📊 Estado del Proyecto - TP Diseño de Sistemas Monolito

**Última actualización**: Enero 2025

## ✅ Tareas Completadas

### 1. Upgrade a Java 21 LTS ✅
- **Estado**: COMPLETADO
- **Detalles**:
  - `pom.xml` actualizado con `<java.version>21</java.version>`
  - Maven configurado con `<release>21</release>`
  - Spring Boot 3.3.0 (compatible con Java 21)
  - Todas las dependencias verificadas para compatibilidad con Java 21

### 2. Reestructuración a Maven Standard Layout ✅
- **Estado**: COMPLETADO
- **Detalles**:
  - ✅ 17 archivos Java movidos a `src/main/java/ar/edu/utn/frba/ddsi/metamapa/`
  - ✅ Estructura de paquetes correcta:
    ```
    src/main/java/ar/edu/utn/frba/ddsi/metamapa/
    ├── MetamapaMonolitoApplication.java (main)
    ├── config/
    │   └── SecurityConfig.java
    ├── controllers/
    │   ├── AdminController.java
    │   ├── ColeccionController.java
    │   └── LoginController.java
    ├── entities/
    │   ├── Coleccion.java
    │   ├── Hecho.java
    │   ├── SolicitudEliminacion.java
    │   └── Usuario.java
    ├── repositories/
    │   ├── ColeccionRepository.java
    │   ├── HechoRepository.java
    │   ├── SolicitudEliminacionRepository.java
    │   └── UsuarioRepository.java
    └── services/
        ├── ColeccionService.java
        ├── CustomUserDetailsService.java
        ├── FuenteEstaticaService.java
        └── FuenteProxyService.java
    ```

### 3. Corrección de Errores de Sintaxis ✅
- **Estado**: COMPLETADO
- **Detalles**:
  - ✅ `FuenteEstaticaService.java`: Corregido campo CSV con espacios
    - Problema: `public String "Fecha del hecho"` (sintaxis inválida)
    - Solución: `@CsvBindByName(column = "Fecha del hecho") private String fechaDelHecho`

### 4. Aplicación Principal Creada ✅
- **Estado**: COMPLETADO
- **Archivo**: `MetamapaMonolitoApplication.java`
- **Contenido**:
  ```java
  @SpringBootApplication
  public class MetamapaMonolitoApplication {
      public static void main(String[] args) {
          SpringApplication.run(MetamapaMonolitoApplication.class, args);
      }
  }
  ```

### 5. Documentación Actualizada ✅
- **Estado**: COMPLETADO
- **Archivos**:
  - ✅ `README.md`: Guía completa de instalación y configuración
  - ✅ `LOMBOK_FIX.md`: Soluciones al problema de Lombok
  - ✅ `STATUS.md`: Este archivo

## ❌ Problemas Pendientes

### 1. Problema de Compilación: Lombok ❌
- **Estado**: BLOQUEADOR
- **Error**: 
  ```
  [ERROR] cannot find symbol: method setTitulo(java.lang.String)
  [ERROR] cannot find symbol: method getTitulo()
  ```
- **Causa**: Las anotaciones `@Getter` y `@Setter` de Lombok no están siendo procesadas durante la compilación
- **Impacto**: 36 errores de compilación - **EL PROYECTO NO COMPILA**
- **Solución**: Ver **[LOMBOK_FIX.md](LOMBOK_FIX.md)**

### 2. Configuración Intentadas (Sin Éxito)
Se intentaron las siguientes configuraciones en `pom.xml`:

1. ❌ Agregar `annotationProcessorPaths` a `maven-compiler-plugin`
   - Resultado: Error fatal `java.lang.ExceptionInInitializerError`
   
2. ❌ Cambiar versión del plugin: `3.11.0` → `3.13.0`
   - Resultado: Sin cambios en el error
   
3. ❌ Cambiar scope de Lombok: `optional` → `provided`
   - Resultado: Sin cambios en el error
   
4. ❌ Eliminar configuración personalizada del plugin
   - Resultado: Sin cambios en el error

## 🎯 Próximos Pasos

### Prioridad 1: Resolver Problema de Lombok

**Opciones** (elige una):

1. **Instalar extensión de Lombok en VS Code** (RECOMENDADO)
   - Extension: "Lombok Annotations Support for VS Code"
   - Autor: Gabriel Basilio Brito
   
2. **Usar IntelliJ IDEA** (tiene mejor soporte Lombok)
   - Instalar plugin Lombok
   - Habilitar "Enable annotation processing"
   
3. **Actualizar Spring Boot** a 3.4.0
   - Cambiar `<version>3.3.0</version>` → `<version>3.4.0</version>`
   
4. **Generar getters/setters manualmente** (temporal)
   - Usar "Source Action" → "Generate Getters and Setters" en VS Code
   - Aplicar a: `Hecho.java`, `Usuario.java`, `Coleccion.java`, `SolicitudEliminacion.java`

### Prioridad 2: Verificar Compilación

```bash
mvn clean compile
```

**Resultado esperado**:
```
[INFO] BUILD SUCCESS
```

### Prioridad 3: Configurar Base de Datos

Una vez que compile, configurar MySQL en `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/metamapa_db
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
```

### Prioridad 4: Ejecutar Aplicación

```bash
mvn spring-boot:run
```

**URL esperada**: http://localhost:8080

## 📦 Dependencias del Proyecto

| Dependencia | Versión | Propósito |
|------------|---------|-----------|
| Java | 21 LTS | Runtime |
| Spring Boot | 3.3.0 | Framework principal |
| Spring Data JPA | (parent) | Persistencia |
| Spring Security | (parent) | Autenticación/Autorización |
| Spring Web | (parent) | REST APIs |
| Thymeleaf | (parent) | Motor de plantillas |
| MySQL Connector | (runtime) | Driver base de datos |
| Lombok | 1.18.32 | Reducción boilerplate |
| OpenCSV | 5.9 | Lectura archivos CSV |
| WebFlux | (parent) | Cliente HTTP reactivo |

## 🏗️ Arquitectura

**Tipo**: Monolito
- Una aplicación
- Una base de datos (`metamapa_db`)
- Comunicación directa entre capas (sin HTTP)

**Capas**:
1. **Controllers** (`@RestController`): Endpoints HTTP
2. **Services** (`@Service`): Lógica de negocio
3. **Repositories** (`@Repository`): Acceso a datos (JPA)
4. **Entities** (`@Entity`): Modelos de datos

## 🛠️ Comandos Útiles

```bash
# Compilar (sin tests)
mvn clean compile

# Compilar con tests
mvn clean install

# Ejecutar aplicación
mvn spring-boot:run

# Ver dependencias
mvn dependency:tree

# Limpiar target/
mvn clean
```

## 📝 Notas del Desarrollo

- **Fecha inicio upgrade**: Enero 2025
- **Herramientas usadas**: GitHub Copilot, Maven
- **Problemas encontrados**: 
  - Lombok no compatible con configuración actual
  - Espacios en nombres de campos CSV
- **Aprendizajes**:
  - Usar `@CsvBindByName` para columnas CSV con espacios
  - Lombok requiere configuración especial en Java 21 en algunos entornos
  - Maven Standard Layout es crítico para Spring Boot

## 🆘 Soporte

Si tienes problemas:
1. Revisar **[LOMBOK_FIX.md](LOMBOK_FIX.md)**
2. Verificar Java 21: `java -version`
3. Verificar Maven: `mvn -v`
4. Revisar logs en terminal

---

**Estado general**: 🟡 PARCIALMENTE COMPLETADO (bloqueado por Lombok)
