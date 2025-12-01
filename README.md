# Sistema de Gestión de Empleados y Departamentos

## 📋 Descripción
Sistema de gestión empresarial desarrollado en Java con interfaz gráfica que permite administrar empleados y departamentos usando Hibernate y PostgreSQL.

## 🚀 Características

### ✅ Funcionalidades Principales:
- **Gestión de Departamentos:** Crear, modificar, eliminar y consultar departamentos
- **Gestión de Empleados:** Crear, modificar, eliminar y consultar empleados
- **Asignación:** Asignar empleados a departamentos
- **Interfaz Gráfica:** Interfaz amigable desarrollada con Java Swing
- **Persistencia:** Base de datos PostgreSQL con Hibernate ORM

### 🏗️ Arquitectura:
- **Patrón DAO:** Data Access Object para operaciones CRUD
- **Relación Simplificada:** Empleados tienen departamento como String (nombre)
- **Interfaz Gráfica:** Separada en clase `InterfazGrafica.java`
- **Configuración:** Hibernate con archivo `hibernate.cfg.xml`

## 🛠️ Tecnologías Utilizadas
- **Java 11+**
- **Maven** (Gestión de dependencias)
- **Hibernate 5.6.15** (ORM)
- **PostgreSQL** (Base de datos)
- **Java Swing** (Interfaz gráfica)

## 📦 Dependencias
```xml
<dependencies>
    <!-- PostgreSQL Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.7</version>
    </dependency>
    
    <!-- Hibernate Core -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.6.15.Final</version>
    </dependency>
</dependencies>
```

## 🗃️ Estructura de Base de Datos

### Tabla: `departamentos`
```sql
CREATE TABLE departamentos (
    clave_depto BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);
```

### Tabla: `empleados`
```sql
CREATE TABLE empleados (
    clave BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(500),
    telefono VARCHAR(20),
    departamento VARCHAR(255)
);
```

## ⚙️ Configuración

### 1. Base de Datos PostgreSQL
```bash
# Crear base de datos
createdb -U postgres empleados

# Ejecutar script de configuración
psql -U chris -d empleados -f database_setup.sql
```

### 2. Configuración de Hibernate
Archivo: `src/main/resources/hibernate.cfg.xml`
```xml
<property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/empleados</property>
<property name="hibernate.connection.username">chris</property>
<property name="hibernate.connection.password">Chris2005</property>
```

## 🚀 Ejecución

### Opción 1: Con Maven
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="org.uv.tcswpractica04.TCSWPractica04"
```

### Opción 2: Compilación JAR
```bash
mvn clean package
java -jar target/TCSWPractica04-1.0-SNAPSHOT.jar
```

## 📱 Uso de la Interfaz Gráfica

### 🏢 Pestaña de Departamentos:
1. **Agregar:** Completar nombre y hacer clic en "Agregar"
2. **Modificar:** Seleccionar departamento de la tabla, editar y hacer clic en "Modificar"
3. **Eliminar:** Seleccionar departamento y hacer clic en "Eliminar"
4. **Consultar:** Ver lista completa con número de empleados asignados

### 👥 Pestaña de Empleados:
1. **Agregar:** Completar todos los campos y seleccionar departamento
2. **Modificar:** Seleccionar empleado de la tabla, editar y hacer clic en "Modificar"
3. **Eliminar:** Seleccionar empleado y hacer clic en "Eliminar"
4. **Consultar:** Ver lista completa con información de departamento

### 🔄 Funciones Adicionales:
- **Refrescar:** Actualizar datos de las tablas
- **Limpiar:** Borrar formularios
- **Selección:** Hacer clic en tabla para cargar datos en formulario

## 📁 Estructura del Proyecto
```
src/
├── main/
│   ├── java/org/uv/tcswpractica04/
│   │   ├── TCSWPractica04.java          # Clase principal
│   │   ├── InterfazGrafica.java         # Interfaz gráfica
│   │   ├── Empleados.java               # Entidad Empleado
│   │   ├── Departamentos.java           # Entidad Departamento
│   │   ├── DAOEmpleado.java             # DAO para Empleados
│   │   ├── DAODepartamentos.java        # DAO para Departamentos
│   │   ├── IDAOGeneral.java             # Interfaz DAO genérica
│   │   └── HibernateUtils.java          # Utilidades Hibernate
│   └── resources/
│       └── hibernate.cfg.xml            # Configuración Hibernate
├── database_setup.sql                   # Script de configuración DB
└── pom.xml                             # Configuración Maven
```

## 🔧 Solución de Problemas

### Error: "Could not locate cfg.xml resource"
**Solución:** Verificar que `hibernate.cfg.xml` esté en `src/main/resources/`

### Error: "Connection refused"
**Solución:** 
1. Verificar que PostgreSQL esté ejecutándose: `sudo systemctl status postgresql`
2. Verificar credenciales en `hibernate.cfg.xml`

### Error: "Table doesn't exist"
**Solución:** Ejecutar el script `database_setup.sql`

## 👨‍💻 Autor
- **Nombre:** Christopher
- **Curso:** Taller de Construcción de Software
- **Práctica:** 04

## 📄 Licencia
Proyecto académico - Uso educativo

---
**Nota:** La aplicación incluye datos de ejemplo que se cargan automáticamente al ejecutar el script de base de datos.
