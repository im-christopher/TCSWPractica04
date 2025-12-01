package org.uv.tcswpractica04;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Interfaz Gráfica para el Sistema de Gestión de Empleados y Departamentos
 * @author christopher
 */
public class InterfazGrafica extends JFrame {
    
    private DAOEmpleado daoEmpleado;
    private DAODepartamentos daoDepartamentos;
    
    // Componentes principales
    private JTabbedPane tabbedPane;
    private JTable tablaEmpleados;
    private JTable tablaDepartamentos;
    private DefaultTableModel modeloEmpleados;
    private DefaultTableModel modeloDepartamentos;
    
    // Formularios
    private JTextField txtNombreEmpleado, txtDireccionEmpleado, txtTelefonoEmpleado;
    private JComboBox<String> comboDepartamentos;
    private JTextField txtNombreDepartamento;
    
    public InterfazGrafica() {
        // Inicializar DAOs
        daoEmpleado = new DAOEmpleado();
        daoDepartamentos = new DAODepartamentos();
        
        initComponents();
        configurarVentana();
        cargarDatos();
    }
    
    private void initComponents() {
        // Crear el panel con pestañas
        tabbedPane = new JTabbedPane();
        
        // Crear pestañas
        tabbedPane.addTab("Empleados", crearPanelEmpleados());
        tabbedPane.addTab("Departamentos", crearPanelDepartamentos());
        
        add(tabbedPane);
    }
    
    private JPanel crearPanelEmpleados() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel superior con formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Empleados"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Campos del formulario
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombreEmpleado = new JTextField(20);
        panelFormulario.add(txtNombreEmpleado, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        txtDireccionEmpleado = new JTextField(20);
        panelFormulario.add(txtDireccionEmpleado, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefonoEmpleado = new JTextField(20);
        panelFormulario.add(txtTelefonoEmpleado, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Departamento:"), gbc);
        gbc.gridx = 1;
        comboDepartamentos = new JComboBox<>();
        panelFormulario.add(comboDepartamentos, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarEmpleado());
        panelBotones.add(btnAgregar);
        
        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificarEmpleado());
        panelBotones.add(btnModificar);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        panelBotones.add(btnEliminar);
        
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormularioEmpleado());
        panelBotones.add(btnLimpiar);
        
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarEmpleados());
        panelBotones.add(btnRefrescar);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);
        
        // Tabla de empleados
        String[] columnasEmpleados = {"ID", "Nombre", "Dirección", "Teléfono", "Departamento"};
        modeloEmpleados = new DefaultTableModel(columnasEmpleados, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tablaEmpleados = new JTable(modeloEmpleados);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarEmpleadoSeleccionado();
            }
        });
        
        JScrollPane scrollEmpleados = new JScrollPane(tablaEmpleados);
        scrollEmpleados.setPreferredSize(new Dimension(600, 300));
        
        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(scrollEmpleados, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelDepartamentos() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel superior con formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Departamentos"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Campo del formulario
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre del Departamento:"), gbc);
        gbc.gridx = 1;
        txtNombreDepartamento = new JTextField(20);
        panelFormulario.add(txtNombreDepartamento, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarDepartamento());
        panelBotones.add(btnAgregar);
        
        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificarDepartamento());
        panelBotones.add(btnModificar);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarDepartamento());
        panelBotones.add(btnEliminar);
        
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormularioDepartamento());
        panelBotones.add(btnLimpiar);
        
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarDepartamentos());
        panelBotones.add(btnRefrescar);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);
        
        // Tabla de departamentos
        String[] columnasDepartamentos = {"ID", "Nombre", "Número de Empleados"};
        modeloDepartamentos = new DefaultTableModel(columnasDepartamentos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tablaDepartamentos = new JTable(modeloDepartamentos);
        tablaDepartamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDepartamentos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDepartamentoSeleccionado();
            }
        });
        
        JScrollPane scrollDepartamentos = new JScrollPane(tablaDepartamentos);
        scrollDepartamentos.setPreferredSize(new Dimension(600, 300));
        
        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(scrollDepartamentos, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void configurarVentana() {
        setTitle("Sistema de Gestión de Empleados y Departamentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void cargarDatos() {
        cargarEmpleados();
        cargarDepartamentos();
        cargarComboDepartamentos();
    }
    
    private void cargarEmpleados() {
        modeloEmpleados.setRowCount(0);
        List<Empleados> empleados = daoEmpleado.findAll();
        
        if (empleados != null) {
            for (Empleados emp : empleados) {
                Object[] fila = {
                    emp.getClave(),
                    emp.getNombre(),
                    emp.getDireccion(),
                    emp.getTelefono(),
                    emp.getDepartamento() != null ? emp.getDepartamento() : "Sin asignar"
                };
                modeloEmpleados.addRow(fila);
            }
        }
    }
    
    private void cargarDepartamentos() {
        modeloDepartamentos.setRowCount(0);
        List<Departamentos> departamentos = daoDepartamentos.findAll();
        
        if (departamentos != null) {
            for (Departamentos dept : departamentos) {
                List<String> empleados = daoEmpleado.findEmpleadosByDepartamento(dept.getNombre());
                int numEmpleados = empleados != null ? empleados.size() : 0;
                
                Object[] fila = {
                    dept.getClave_depto(),
                    dept.getNombre(),
                    numEmpleados
                };
                modeloDepartamentos.addRow(fila);
            }
        }
    }
    
    private void cargarComboDepartamentos() {
        comboDepartamentos.removeAllItems();
        comboDepartamentos.addItem("Seleccionar...");
        
        List<Departamentos> departamentos = daoDepartamentos.findAll();
        if (departamentos != null) {
            for (Departamentos dept : departamentos) {
                comboDepartamentos.addItem(dept.getNombre());
            }
        }
    }
    
    private void agregarEmpleado() {
        if (validarFormularioEmpleado()) {
            Empleados empleado = new Empleados();
            empleado.setNombre(txtNombreEmpleado.getText().trim());
            empleado.setDireccion(txtDireccionEmpleado.getText().trim());
            empleado.setTelefono(txtTelefonoEmpleado.getText().trim());
            
            String deptoSeleccionado = (String) comboDepartamentos.getSelectedItem();
            if (!"Seleccionar...".equals(deptoSeleccionado)) {
                empleado.setDepartamento(deptoSeleccionado);
            }
            
            if (daoEmpleado.guardar(empleado)) {
                JOptionPane.showMessageDialog(this, "Empleado agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioEmpleado();
                cargarEmpleados();
                cargarDepartamentos(); // Refrescar para actualizar contadores
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar empleado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void modificarEmpleado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (validarFormularioEmpleado()) {
            Long id = (Long) modeloEmpleados.getValueAt(filaSeleccionada, 0);
            
            Empleados empleado = new Empleados();
            empleado.setNombre(txtNombreEmpleado.getText().trim());
            empleado.setDireccion(txtDireccionEmpleado.getText().trim());
            empleado.setTelefono(txtTelefonoEmpleado.getText().trim());
            
            String deptoSeleccionado = (String) comboDepartamentos.getSelectedItem();
            if (!"Seleccionar...".equals(deptoSeleccionado)) {
                empleado.setDepartamento(deptoSeleccionado);
            }
            
            if (daoEmpleado.modificar(empleado, id) != null) {
                JOptionPane.showMessageDialog(this, "Empleado modificado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioEmpleado();
                cargarEmpleados();
                cargarDepartamentos(); // Refrescar para actualizar contadores
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar empleado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarEmpleado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String nombre = (String) modeloEmpleados.getValueAt(filaSeleccionada, 1);
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar al empleado: " + nombre + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            Long id = (Long) modeloEmpleados.getValueAt(filaSeleccionada, 0);
            
            if (daoEmpleado.eliminar(id) != null) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioEmpleado();
                cargarEmpleados();
                cargarDepartamentos(); // Refrescar para actualizar contadores
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar empleado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void agregarDepartamento() {
        String nombre = txtNombreDepartamento.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del departamento es obligatorio", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Departamentos departamento = new Departamentos();
            departamento.setNombre(nombre);
            
            if (daoDepartamentos.guardar(departamento)) {
                JOptionPane.showMessageDialog(this, "Departamento agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioDepartamento();
                cargarDepartamentos();
                cargarComboDepartamentos(); // Refrescar combo
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar departamento - Operación falló", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar departamento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarDepartamento() {
        int filaSeleccionada = tablaDepartamentos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un departamento de la tabla", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String nombre = txtNombreDepartamento.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del departamento es obligatorio", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long id = (Long) modeloDepartamentos.getValueAt(filaSeleccionada, 0);
        
        Departamentos departamento = new Departamentos();
        departamento.setNombre(nombre);
        
        if (daoDepartamentos.modificar(departamento, id) != null) {
            JOptionPane.showMessageDialog(this, "Departamento modificado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormularioDepartamento();
            cargarDepartamentos();
            cargarComboDepartamentos(); // Refrescar combo
            cargarEmpleados(); // Refrescar empleados para mostrar cambios
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar departamento", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarDepartamento() {
        int filaSeleccionada = tablaDepartamentos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un departamento de la tabla", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String nombre = (String) modeloDepartamentos.getValueAt(filaSeleccionada, 1);
        int numEmpleados = (Integer) modeloDepartamentos.getValueAt(filaSeleccionada, 2);
        
        if (numEmpleados > 0) {
            JOptionPane.showMessageDialog(this, 
                "No se puede eliminar el departamento porque tiene " + numEmpleados + " empleado(s) asignado(s)", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar el departamento: " + nombre + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            Long id = (Long) modeloDepartamentos.getValueAt(filaSeleccionada, 0);
            
            if (daoDepartamentos.eliminar(id) != null) {
                JOptionPane.showMessageDialog(this, "Departamento eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioDepartamento();
                cargarDepartamentos();
                cargarComboDepartamentos(); // Refrescar combo
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar departamento", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarEmpleadoSeleccionado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtNombreEmpleado.setText((String) modeloEmpleados.getValueAt(filaSeleccionada, 1));
            txtDireccionEmpleado.setText((String) modeloEmpleados.getValueAt(filaSeleccionada, 2));
            txtTelefonoEmpleado.setText((String) modeloEmpleados.getValueAt(filaSeleccionada, 3));
            
            String departamento = (String) modeloEmpleados.getValueAt(filaSeleccionada, 4);
            if (!"Sin asignar".equals(departamento)) {
                comboDepartamentos.setSelectedItem(departamento);
            } else {
                comboDepartamentos.setSelectedIndex(0);
            }
        }
    }
    
    private void cargarDepartamentoSeleccionado() {
        int filaSeleccionada = tablaDepartamentos.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtNombreDepartamento.setText((String) modeloDepartamentos.getValueAt(filaSeleccionada, 1));
        }
    }
    
    private boolean validarFormularioEmpleado() {
        if (txtNombreEmpleado.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del empleado es obligatorio", "Advertencia", JOptionPane.WARNING_MESSAGE);
            txtNombreEmpleado.requestFocus();
            return false;
        }
        return true;
    }
    
    private void limpiarFormularioEmpleado() {
        txtNombreEmpleado.setText("");
        txtDireccionEmpleado.setText("");
        txtTelefonoEmpleado.setText("");
        comboDepartamentos.setSelectedIndex(0);
        tablaEmpleados.clearSelection();
    }
    
    private void limpiarFormularioDepartamento() {
        txtNombreDepartamento.setText("");
        tablaDepartamentos.clearSelection();
    }
    
    public void mostrar() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
        });
    }
}
