/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.tcswpractica04;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

/**
 *
 * @author christopher
 */
public class DAOEmpleado implements IDAOGeneral<Empleados, Long> {

    @Override
    public boolean guardar(Empleados empleado) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction t = session.beginTransaction();
            session.save(empleado);
            t.commit();
            Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Empleado guardado exitosamente");
            return true;
        } catch (Exception e) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Error al guardar: ", e);
            return false;
        }
    }

    @Override
    public Empleados eliminar(Long id) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction t = session.beginTransaction();
            Empleados emp = session.get(Empleados.class, id);
            if (emp != null) {
                session.delete(emp);
                t.commit();
                Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Empelado eliminado exitosamente.");
                return emp;
            } else {
                Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "No se encontro al empleado.");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Error al eliminar: ", e);
            return null;
        }
    }

    @Override
    public Empleados modificar(Empleados pojo, Long id) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction t = session.beginTransaction();
            Empleados emp = session.get(Empleados.class, id);
            if (emp != null) {
                emp.setNombre(pojo.getNombre());
                emp.setDireccion(pojo.getDireccion());
                emp.setTelefono(pojo.getTelefono());
                emp.setDepartamento(pojo.getDepartamento());
                session.update(emp);
                t.commit();
                Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Se modifico el empleado exitosamente");
                return emp;
            } else {
                Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "No se encontro al empleado.");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Error, no se encontro al empleado: ", e);
            return null;
        }
    }

    @Override
    public Empleados findByID(Long id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(Empleados.class, id);
        } catch (Exception e) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Error al buscar empleado: ", e);
            return null;
        }
    }

    @Override
    public List<Empleados> findAll() {
        try (Session session = HibernateUtils.getSession()) {
            Query<Empleados> query = session.createQuery("FROM Empleados", Empleados.class);
            return query.list();
        } catch (Exception e) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Error al obtener la lista de empleados. ", e);
            return null;
        }
    }
    
    public List<String> findEmpleadosByDepartamento(String nombreDepartamento) {
        try (Session session = HibernateUtils.getSession()) {
            Query<String> query = session.createQuery(
                "SELECT e.nombre FROM Empleados e WHERE e.departamento = :departamento", 
                String.class);
            query.setParameter("departamento", nombreDepartamento);
            return query.list();
        } catch (Exception e) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Error al obtener empleados por departamento: ", e);
            return null;
        }
    }
    
    public boolean asignarEmpleadoADepartamento(Long empleadoId, String nombreDepartamento) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction t = session.beginTransaction();
            Empleados empleado = session.get(Empleados.class, empleadoId);
            if (empleado != null) {
                empleado.setDepartamento(nombreDepartamento);
                session.update(empleado);
                t.commit();
                Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Empleado asignado al departamento exitosamente");
                return true;
            } else {
                Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Empleado no encontrado");
                return false;
            }
        } catch (Exception e) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Error al asignar empleado a departamento: ", e);
            return false;
        }
    }
}
