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

    public LogGrupo(Grupo grupo, List<String> usuarios) {
        this.grupo = grupo.getNombre();
        this.usuarios = usuarios;
        this.mensajes = new ArrayList<String>();
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo.getNombre();
    }

    public List<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
    public void addMensaje(Usuario remitente, String mensaje){
        mensajes.add(mensaje);
    }
    
    
}
