/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.tcswpractica04;

import java.util.List;

/**
 *
 * @author christopher
 */
public interface IDAOGeneral <T, ID> {
    //Aqui se hacen las operaciones
    public boolean guardar(T pojo);
    public T eliminar(ID id);
    public T modificar(T pojo, ID id);
    public T findByID(Long id);
    public List<T> findAll();
    
}
