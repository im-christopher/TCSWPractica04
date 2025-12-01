package org.uv.tcswpractica04;

/**
 * Clase de prueba para verificar la funcionalidad de agregar departamentos
 * @author christopher
 */
public class PruebaDepartamentos {
    
    public static void main(String[] args) {
        System.out.println("=== Prueba de Agregar Departamentos ===");
        
        DAODepartamentos dao = new DAODepartamentos();
        
        // Crear un departamento de prueba
        Departamentos nuevo = new Departamentos();
        nuevo.setNombre("Departamento de Prueba " + System.currentTimeMillis());
        
        System.out.println("Intentando agregar departamento: " + nuevo.getNombre());
        
        boolean resultado = dao.guardar(nuevo);
        
        if (resultado) {
            System.out.println("✓ Departamento agregado exitosamente!");
            
            // Verificar que se agregó listando todos
            System.out.println("\nListando todos los departamentos:");
            var departamentos = dao.findAll();
            if (departamentos != null) {
                for (Departamentos dept : departamentos) {
                    System.out.println("- ID: " + dept.getClave_depto() + ", Nombre: " + dept.getNombre());
                }
            }
        } else {
            System.out.println("✗ Error al agregar departamento");
        }
        
        // Cerrar Hibernate
        HibernateUtils.shutdown();
    }
}
