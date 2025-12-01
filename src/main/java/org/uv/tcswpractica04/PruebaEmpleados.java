package org.uv.tcswpractica04;

import java.util.List;

/**
 * Clase de prueba para verificar las operaciones CRUD de empleados
 * @author christopher
 */
public class PruebaEmpleados {
    
    public static void main(String[] args) {
        System.out.println("=== Prueba de Gestión de Empleados ===");
        
        DAOEmpleado daoEmpleado = new DAOEmpleado();
        DAODepartamentos daoDepartamentos = new DAODepartamentos();
        
        // 1. Listar empleados existentes
        System.out.println("\n1. Empleados existentes:");
        List<Empleados> empleadosExistentes = daoEmpleado.findAll();
        if (empleadosExistentes != null && !empleadosExistentes.isEmpty()) {
            for (Empleados emp : empleadosExistentes) {
                System.out.println("- ID: " + emp.getClave() + ", Nombre: " + emp.getNombre() + 
                                 ", Departamento: " + (emp.getDepartamento() != null ? emp.getDepartamento() : "Sin asignar"));
            }
        } else {
            System.out.println("No hay empleados registrados.");
        }
        
        // 2. Obtener un departamento existente para la prueba
        List<Departamentos> departamentos = daoDepartamentos.findAll();
        String departamentoPrueba = null;
        if (departamentos != null && !departamentos.isEmpty()) {
            departamentoPrueba = departamentos.get(0).getNombre();
            System.out.println("\n2. Usando departamento para prueba: " + departamentoPrueba);
        }
        
        // 3. Agregar un nuevo empleado
        System.out.println("\n3. Agregando nuevo empleado...");
        Empleados nuevoEmpleado = new Empleados();
        nuevoEmpleado.setNombre("Empleado Prueba " + System.currentTimeMillis());
        nuevoEmpleado.setDireccion("Dirección de Prueba 123");
        nuevoEmpleado.setTelefono("555-1234");
        if (departamentoPrueba != null) {
            nuevoEmpleado.setDepartamento(departamentoPrueba);
        }
        
        if (daoEmpleado.guardar(nuevoEmpleado)) {
            System.out.println("✓ Empleado agregado exitosamente!");
        } else {
            System.out.println("✗ Error al agregar empleado");
            return;
        }
        
        // 4. Buscar el empleado recién creado
        System.out.println("\n4. Listando empleados después de agregar:");
        List<Empleados> empleadosActualizados = daoEmpleado.findAll();
        Empleados empleadoCreado = null;
        if (empleadosActualizados != null) {
            for (Empleados emp : empleadosActualizados) {
                if (emp.getNombre().contains("Empleado Prueba")) {
                    empleadoCreado = emp;
                    System.out.println("- ID: " + emp.getClave() + ", Nombre: " + emp.getNombre() + 
                                     ", Dirección: " + emp.getDireccion() + ", Teléfono: " + emp.getTelefono() +
                                     ", Departamento: " + (emp.getDepartamento() != null ? emp.getDepartamento() : "Sin asignar"));
                }
            }
        }
        
        if (empleadoCreado == null) {
            System.out.println("✗ No se encontró el empleado creado");
            return;
        }
        
        // 5. Modificar el empleado
        System.out.println("\n5. Modificando empleado...");
        Empleados empleadoModificado = new Empleados();
        empleadoModificado.setNombre(empleadoCreado.getNombre() + " - MODIFICADO");
        empleadoModificado.setDireccion("Nueva Dirección 456");
        empleadoModificado.setTelefono("555-9876");
        empleadoModificado.setDepartamento(empleadoCreado.getDepartamento());
        
        Empleados resultado = daoEmpleado.modificar(empleadoModificado, empleadoCreado.getClave());
        if (resultado != null) {
            System.out.println("✓ Empleado modificado exitosamente!");
            System.out.println("- Nuevo nombre: " + resultado.getNombre());
            System.out.println("- Nueva dirección: " + resultado.getDireccion());
            System.out.println("- Nuevo teléfono: " + resultado.getTelefono());
        } else {
            System.out.println("✗ Error al modificar empleado");
        }
        
        // 6. Buscar empleado por ID
        System.out.println("\n6. Buscando empleado por ID " + empleadoCreado.getClave() + "...");
        Empleados empleadoEncontrado = daoEmpleado.findByID(empleadoCreado.getClave());
        if (empleadoEncontrado != null) {
            System.out.println("✓ Empleado encontrado: " + empleadoEncontrado.getNombre());
        } else {
            System.out.println("✗ Empleado no encontrado por ID");
        }
        
        // 7. Eliminar el empleado de prueba
        System.out.println("\n7. Eliminando empleado de prueba...");
        Empleados empleadoEliminado = daoEmpleado.eliminar(empleadoCreado.getClave());
        if (empleadoEliminado != null) {
            System.out.println("✓ Empleado eliminado exitosamente: " + empleadoEliminado.getNombre());
        } else {
            System.out.println("✗ Error al eliminar empleado");
        }
        
        // 8. Verificar que fue eliminado
        System.out.println("\n8. Verificando eliminación...");
        Empleados empleadoVerificacion = daoEmpleado.findByID(empleadoCreado.getClave());
        if (empleadoVerificacion == null) {
            System.out.println("✓ Empleado eliminado correctamente de la base de datos");
        } else {
            System.out.println("✗ El empleado aún existe en la base de datos");
        }
        
        // 9. Mostrar empleados por departamento
        if (departamentoPrueba != null) {
            System.out.println("\n9. Empleados del departamento '" + departamentoPrueba + "':");
            List<String> empleadosDepartamento = daoEmpleado.findEmpleadosByDepartamento(departamentoPrueba);
            if (empleadosDepartamento != null && !empleadosDepartamento.isEmpty()) {
                for (String nombre : empleadosDepartamento) {
                    System.out.println("- " + nombre);
                }
            } else {
                System.out.println("No hay empleados en este departamento.");
            }
        }
        
        System.out.println("\n=== Prueba de empleados completada ===");
        HibernateUtils.shutdown();
    }
}
