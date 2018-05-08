/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class LogGrupo {
    private Grupo grupo;
    private List<Usuario> usuarios;
    private List<String> mensajes;  

    public LogGrupo() {
    }

    public LogGrupo(Grupo grupo, List<Usuario> usuarios, List<String> mensajes) {
        this.grupo = grupo;
        this.usuarios = usuarios;
        this.mensajes = new ArrayList<String>();
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
    
    
    
}
