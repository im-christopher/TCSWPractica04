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
public class DAODepartamentos implements IDAOGeneral<Departamentos, Long> {

    @Override
    public boolean guardar(Departamentos departamento) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction t = session.beginTransaction();
            session.save(departamento);
            t.commit();
            Logger.getLogger(DAODepartamentos.class.getName()).log(Logger.Level.INFO, "Departamento guardado exitosamente");
            return true;
        } catch (Exception e) {
            Logger.getLogger(DAODepartamentos.class.getName()).log(Logger.Level.ERROR, "Error al guardar departamento: ", e);
            return false;
        }
    }

    @Override
    public Departamentos eliminar(Long id) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction t = session.beginTransaction();
            Departamentos dep = session.get(Departamentos.class, id);
            if (dep != null) {
                session.delete(dep);
                t.commit();
                Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Departamento eliminado exitosamente.");
                return dep;
            } else {
                Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "No se encontro al Departamento.");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Error al eliminar: ", e);
            return null;
        }

    }

    @Override
    public Departamentos modificar(Departamentos pojo, Long id) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction t = session.beginTransaction();
            Departamentos dep = session.get(Departamentos.class, id);
            if (dep != null) {
                dep.setClave_depto(pojo.getClave_depto());
                dep.setNombre(pojo.getNombre());

                t.commit();
                Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Se modifico el departamento exitosamente");
                return dep;
            } else {
                Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "No se encontro al departamento.");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Error, no se encontro al departamento: ", e);
            return null;
        }
    }

    @Override
    public Departamentos findByID(Long id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(Departamentos.class, id);
        } catch (Exception e) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Logger.Level.INFO, "Error al buscar Departamento: ", e);
            return null;
        }
    }

    @Override
    public List<Departamentos> findAll() {
        try (Session session = HibernateUtils.getSession()) {
            Query<Departamentos> query = session.createQuery("FROM Departamentos", Departamentos.class);
            List<Departamentos> departamentos = query.list();
            
            // Para cada departamento, cargar la lista de empleados
            DAOEmpleado daoEmpleado = new DAOEmpleado();
            for (Departamentos dept : departamentos) {
                List<String> nombresEmpleados = daoEmpleado.findEmpleadosByDepartamento(dept.getNombre());
                dept.setNombresEmpleados(nombresEmpleados);
            }
            
            return departamentos;
        } catch (Exception e) {
            Logger.getLogger(DAODepartamentos.class.getName()).log(Logger.Level.INFO, "Error al obtener la lista de departamentos. ", e);
            return null;
        }
    }
    
    public Departamentos findByNombre(String nombre) {
        try (Session session = HibernateUtils.getSession()) {
            Query<Departamentos> query = session.createQuery("FROM Departamentos WHERE nombre = :nombre", Departamentos.class);
            query.setParameter("nombre", nombre);
            Departamentos departamento = query.uniqueResult();
            
            if (departamento != null) {
                // Cargar empleados del departamento
                DAOEmpleado daoEmpleado = new DAOEmpleado();
                List<String> nombresEmpleados = daoEmpleado.findEmpleadosByDepartamento(nombre);
                departamento.setNombresEmpleados(nombresEmpleados);
            }
            
            return departamento;
        } catch (Exception e) {
            Logger.getLogger(DAODepartamentos.class.getName()).log(Logger.Level.INFO, "Error al buscar departamento por nombre: ", e);
            return null;
        }
    }

}
