# Guía de Usuario - Sistema de Gestión de Empleados y Departamentos

## Descripción General
Esta aplicación permite gestionar empleados y departamentos a través de una interfaz gráfica intuitiva con pestañas separadas para cada tipo de entidad.

## Cómo Ejecutar la Aplicación
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="org.uv.tcswpractica04.TCSWPractica04"
```

## Funcionalidades Disponibles

### 🏢 Pestaña de Departamentos

La pestaña "Departamentos" te permite:

#### ➕ **Agregar Departamentos**
1. Haz clic en la pestaña "Departamentos"
2. Ingresa el nombre del departamento en el campo "Nombre del Departamento"
3. Haz clic en el botón "Agregar"
4. El departamento aparecerá inmediatamente en la tabla

#### ✏️ **Modificar Departamentos**
1. Selecciona un departamento de la tabla haciendo clic en la fila
2. El nombre aparecerá automáticamente en el campo de texto
3. Modifica el nombre según necesites
4. Haz clic en el botón "Modificar"

#### 🗑️ **Eliminar Departamentos**
1. Selecciona un departamento de la tabla
2. Haz clic en el botón "Eliminar"
3. Confirma la eliminación en el diálogo que aparece
4. **Nota:** No se pueden eliminar departamentos que tengan empleados asignados

#### 🔄 **Otras Funciones**
- **Limpiar:** Limpia el formulario y deselecciona la tabla
- **Refrescar:** Actualiza la tabla con los datos más recientes

### 👥 Pestaña de Empleados

La pestaña "Empleados" te permite:

#### ➕ **Agregar Empleados**
1. Haz clic en la pestaña "Empleados"
2. Completa los campos obligatorios:
   - **Nombre:** Campo requerido
   - **Dirección:** Opcional
   - **Teléfono:** Opcional
   - **Departamento:** Selecciona del menú desplegable
3. Haz clic en el botón "Agregar"

#### ✏️ **Modificar Empleados**
1. Selecciona un empleado de la tabla haciendo clic en la fila
2. Los datos aparecerán automáticamente en el formulario
3. Modifica los campos que necesites
4. Haz clic en el botón "Modificar"

#### 🗑️ **Eliminar Empleados**
1. Selecciona un empleado de la tabla
2. Haz clic en el botón "Eliminar"
3. Confirma la eliminación en el diálogo que aparece

#### 🔄 **Otras Funciones**
- **Limpiar:** Limpia el formulario y deselecciona la tabla
- **Refrescar:** Actualiza la tabla con los datos más recientes

## Características Especiales

### 🔄 **Sincronización Automática**
- Cuando agregas/eliminas empleados, el contador de empleados en departamentos se actualiza automáticamente
- Cuando agregas/modificas departamentos, el menú desplegable de departamentos en empleados se actualiza

### 🛡️ **Validaciones Implementadas**
- No se puede agregar un empleado sin nombre
- No se pueden eliminar departamentos con empleados asignados
- Se confirma antes de eliminar cualquier registro

### 📊 **Información Mostrada**
- **Tabla de Empleados:** ID, Nombre, Dirección, Teléfono, Departamento
- **Tabla de Departamentos:** ID, Nombre, Número de Empleados

### 🎯 **Selección Inteligente**
- Al hacer clic en una fila de cualquier tabla, los datos se cargan automáticamente en el formulario
- Esto facilita la modificación de registros existentes

## Solución de Problemas

### ❌ Error al Agregar Departamentos
Si obtienes un error de "llave duplicada", las secuencias de la base de datos necesitan sincronización:
```bash
PGPASSWORD=tu_password psql -h localhost -U tu_usuario -d empleados -c "SELECT setval('dep_clave_seq', (SELECT MAX(clave_depto) FROM departamentos) + 1);"
```

### ❌ Error al Agregar Empleados
Si obtienes un error similar con empleados:
```bash
PGPASSWORD=tu_password psql -h localhost -U tu_usuario -d empleados -c "SELECT setval('empleados_clave_seq', (SELECT MAX(clave) FROM empleados) + 1);"
```

## Estado Actual de la Base de Datos

Según las pruebas realizadas, tu sistema tiene:
- ✅ 4 empleados registrados
- ✅ 5 departamentos disponibles
- ✅ Todas las operaciones CRUD funcionando correctamente
- ✅ Secuencias de base de datos sincronizadas

## Capturas de Pantalla

Tu interfaz actual muestra:
- Pestaña "Empleados" con formulario completo
- Tabla con 4 empleados: Juan Pérez, María García, Carlos López, Ana Martínez
- Departamentos asignados: Tecnología, Recursos Humanos, Ventas, Contabilidad
- Botones funcionales: Agregar, Modificar, Eliminar, Limpiar, Refrescar

**¡La pestaña de Departamentos ya está disponible en tu interfaz! Solo haz clic en "Departamentos" para acceder a todas las funciones de gestión de departamentos.**
