/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.uv.tcswpractica04;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal del Sistema de Gestión de Empleados y Departamentos
 * Ahora utiliza una interfaz gráfica en lugar de consola
 * @author christopher
 */
public class TCSWPractica04 {

    public static void main(String[] args) {
        System.out.println("=== Sistema de Gestión de Empleados y Departamentos ===");
        System.out.println("Iniciando interfaz gráfica...");
        
        // Configurar el Look and Feel antes de crear la interfaz
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo configurar el Look and Feel: " + e.getMessage());
        }
        
        // Ejecutar la interfaz gráfica en el EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                InterfazGrafica interfaz = new InterfazGrafica();
                interfaz.mostrar();
                System.out.println("Interfaz gráfica iniciada correctamente.");
            } catch (Exception e) {
                System.err.println("Error al iniciar la interfaz gráfica: " + e.getMessage());
                e.printStackTrace();
                
                // Si hay error con la interfaz gráfica, cerrar Hibernate
                HibernateUtils.shutdown();
                System.exit(1);
            }
        });
        
        // Agregar shutdown hook para cerrar Hibernate cuando se cierre la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Cerrando conexiones de base de datos...");
            HibernateUtils.shutdown();
            System.out.println("¡Gracias por usar el sistema!");
        }));
    }
}
