/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class LogGrupo {
    private String grupo;
    private List<String> usuarios;
    private List<String> mensajes;  

    public LogGrupo() {
    }
    /**
     * Crea un objeto de tipo logGrupo e instancia sus valores
     * @param grupo objeto de tipo grupo
     * @param usuarios  lista de strings
     */
    public LogGrupo(Grupo grupo, List<String> usuarios) {
        this.grupo = grupo.getNombre();
        this.usuarios = usuarios;
        this.mensajes = new ArrayList<String>();
    }
    /**
     * Obtiene el grupo
     * @return 
     */
    public String getGrupo() {
        return grupo;
    }
    /**
     * Asigna el grupo
     * @param grupo 
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo.getNombre();
    }
    /**
     * Obteien la lista de usuarios pertenecientes al grupo
     * @return 
     */
    public List<String> getUsuarios() {
        return usuarios;
    }
    /**
     * Asigna la lista de usuarios pertenecientes al grupo
     * @param usuarios 
     */
    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }
    /**
     * Obtiene la  lista de mensajes enviados a determinado grupo
     * @return 
     */
    public List<String> getMensajes() {
        return mensajes;
    }
    /**
     * Asigna la  lista de mensajes enviados a determinado grupo
     * @param mensajes 
     */
    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
    /**
     * Agrega un mensaje a la lista de mensjaes del grupo
     * @param remitente
     * @param mensaje 
     */
    public void addMensaje(Usuario remitente, String mensaje){
        mensajes.add(mensaje);
    }
    
    
}
