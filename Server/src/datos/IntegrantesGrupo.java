/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;

/**
 * Esta clase es el modelo de la tabla IntegrantesGruop de la BD.
 * @author Leonardo Martinez
 */
public class IntegrantesGrupo implements Serializable{
    private int idIntegrantesGrupo;
    private String usuario;
    private int grupo;
    /**
     * Obtiene el valor de idIntegrantesGrupo
     * @return 
     */
    public int getIdIntegrantesGrupo() {
        return idIntegrantesGrupo;
    }
    /**
     * Asigna el valor de idIntegrantesGrupo
     * @param idIntegrantesGrupo el nuevo valor de idIntegrantesGrupo
     */
    public void setIdIntegrantesGrupo(int idIntegrantesGrupo) {
        this.idIntegrantesGrupo = idIntegrantesGrupo;
    }
    /**
     * Obtiene el valor de usuario
     * @return el valor de usuario
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * Asigna el valor de usuario
     * @param usuario el nuevo valor de usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    /**
     * Obtiene el valor de grupo
     * @return el valor de grupo
     */
    public int getGrupo() {
        return grupo;
    }
    /**
     * Asigna el valor de grupo
     * @param grupo el nuevo valor de grupo
     */
    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }
    
    public IntegrantesGrupo() {
    }

    public IntegrantesGrupo(int idIntegrantesGrupo, String usuario, int grupo) {
        this.idIntegrantesGrupo = idIntegrantesGrupo;
        this.usuario = usuario;
        this.grupo = grupo;
    }
    
}
