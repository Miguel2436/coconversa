/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;

/**
 * Esta clase es el modelo de la tabla Grupo de la BD.
 * @author Leonardo Martinez
 */
public class Grupo implements Serializable{
    private int idGrupo;
    private String nombre;
    /**
     * Obtiene el valor de idGrupo
     * @return el valor de idGrupo
     */
    public int getIdGrupo() {
        return idGrupo;
    }
    /**
     * Asigna el valor de idGrupo
     * @param idGrupo el nuevo valor de idGrupo
     */
    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }
    /**
     * Obtiene el valor de nombre;
     * @return  el valor de nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Asigna el valor de nombre
     * @param nombre el nuevo valor de nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Grupo() {
    }

    public Grupo(int idGrupo, String nombre) {
        this.idGrupo = idGrupo;
        this.nombre = nombre;
    }
    
}
