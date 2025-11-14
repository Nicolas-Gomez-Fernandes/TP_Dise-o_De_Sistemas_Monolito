# 🚨 PROBLEMA DE COMPILACIÓN: Lombok No Detectado

## ❌ Error Actual

Maven no está procesando las anotaciones de Lombok (`@Getter`, `@Setter`), causando errores de compilación:

```
cannot find symbol: method setTitulo(java.lang.String)
cannot find symbol: method getTitulo()
```

## 🔍 Causa

Java 21 + Maven tiene problemas conocidos con el procesamiento de anotaciones de Lombok en algunos entornos.

## ✅ SOLUCIONES (Elige una)

### Solución 1: Instalar Extension de Lombok en VS Code (RECOMENDADO)

1. **Instalar extensión**:
   - Abre VS Code
   - Ve a Extensions (Ctrl+Shift+X)
   - Busca "Lombok Annotations Support for VS Code"
   - Instala la extensión de Gabriel Basilio Brito

2. **Reiniciar VS Code**

3. **Recompilar**:
   ```bash
   mvn clean compile
   ```

### Solución 2: Usar IntelliJ IDEA

IntelliJ tiene mejor soporte para Lombok out-of-the-box:

1. Abrir proyecto en IntelliJ IDEA
2. Instalar plugin "Lombok" (si no está instalado)
3. Enable Annotation Processing:
   - Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - Marcar "Enable annotation processing"
4. Rebuild project

### Solución 3: Generar Getters/Setters Manualmente (Temporal)

Si necesitas compilar **YA** sin instalar nada:

```bash
# Ejecutar este script para generar getters/setters automáticamente
# (Requiere crear el script primero)
```

**O** usar VS Code para generarlos:
1. Abre cada archivo de entidad (`Hecho.java`, `Usuario.java`, etc.)
2. Click derecho → "Source Action" → "Generate Getters and Setters"
3. Seleccionar todos los campos

### Solución 4: Actualizar a Spring Boot 3.4.0 (Última Versión)

Spring Boot 3.4.0 tiene mejor compatibilidad con Java 21:

**En `pom.xml`, cambiar**:
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.4.0</version>  <!-- Actualizar de 3.3.0 -->
    <relativePath/>
</parent>
```

Luego:
```bash
mvn clean compile
```

## 🎯 Verificar Que Funciona

Después de aplicar cualquier solución:

```bash
mvn clean compile
```

Deberías ver:
```
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  X.XXX s
```

## 📚 Información Adicional

### ¿Qué Hace Lombok?

Lombok genera automáticamente en **compile-time**:
- `getTitulo()`, `setTitulo(String titulo)` desde `@Getter` y `@Setter`
- Constructores, `toString()`, `equals()`, `hashCode()` con otras anotaciones

### Alternativa: Eliminar Lombok

Si nada funciona, puedes eliminar Lombok completamente:

1. Quitar la dependencia de `pom.xml`
2. Generar manualmente todos los getters/setters en las entidades
3. Recompilar

Esto funcionará pero es más trabajo manual.

## ⚠️ Nota Importante

**Las entidades JPA NECESITAN getters y setters** para funcionar correctamente con:
- Spring Data JPA
- Hibernate
- Jackson (serialización JSON)
- Thymeleaf (acceso desde plantillas)

Sin ellos, el proyecto **no compilará ni funcionará**.

## 🆘 Si Nada Funciona

Contacta al equipo o pide ayuda mostrando:
1. Versión de Java: `java -version`
2. Versión de Maven: `mvn -v`
3. Sistema operativo
4. IDE que estás usando
