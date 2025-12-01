# 🔧 PROBLEMA SOLUCIONADO: Agregar Departamentos

## ❌ **El Problema:**
No se podían agregar departamentos debido a un **conflicto de secuencias** en PostgreSQL.

**Error específico:**
```
ERROR: llave duplicada viola restricción de unicidad «departamentos_pkey»
Detail: Ya existe la llave (clave_depto)=(2).
```

## ✅ **La Solución:**
Las secuencias de PostgreSQL no estaban sincronizadas con los datos existentes en las tablas.

### **Pasos Realizados:**

1. **Identificación del problema:**
   - Las secuencias `dep_clave_seq` y `empleados_clave_seq` empezaban en 1
   - Pero ya existían registros con IDs 1, 2, 3, 4
   - Hibernate intentaba usar ID 2 que ya existía

2. **Sincronización de secuencias:**
   ```sql
   SELECT setval('dep_clave_seq', (SELECT MAX(clave_depto) FROM departamentos) + 1);
   SELECT setval('empleados_clave_seq', (SELECT MAX(clave) FROM empleados) + 1);
   ```

3. **Actualización del script de base de datos:**
   - El archivo `database_setup.sql` ahora incluye sincronización automática
   - Las secuencias se ajustan automáticamente al ejecutar el script

## 🚀 **Resultado:**
- ✅ **Departamentos se pueden agregar** sin problemas
- ✅ **Empleados se pueden agregar** sin problemas  
- ✅ **Interfaz gráfica funcional** completamente
- ✅ **Base de datos sincronizada** correctamente

## 📋 **Para Usar la Aplicación:**

### **Ejecutar:**
```bash
mvn exec:java -Dexec.mainClass="org.uv.tcswpractica04.TCSWPractica04"
```

### **Agregar Departamentos:**
1. Ir a la pestaña "Departamentos"
2. Escribir nombre del departamento
3. Hacer clic en "Agregar"
4. ✅ Se agregará exitosamente

### **Agregar Empleados:**
1. Ir a la pestaña "Empleados"
2. Llenar todos los campos
3. Seleccionar departamento del combo
4. Hacer clic en "Agregar"
5. ✅ Se agregará exitosamente

## 🔧 **Si Vuelve a Pasar:**
Si experimentas el mismo error en el futuro, ejecuta:

```bash
PGPASSWORD=Chris2005 psql -h localhost -U chris -d empleados -c "
SELECT setval('dep_clave_seq', (SELECT MAX(clave_depto) FROM departamentos) + 1);
SELECT setval('empleados_clave_seq', (SELECT MAX(clave) FROM empleados) + 1);
"
```

---
**✅ PROBLEMA RESUELTO - LA APLICACIÓN FUNCIONA PERFECTAMENTE**
