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
public class LogConversacion {
    private Usuario usuario1;
    private Usuario usuario2;
    List<String> mensajes;

    public LogConversacion() {
    }

    public LogConversacion(Usuario usuario1, Usuario usuario2, List<String> mensajes) {
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.mensajes = new ArrayList<>();
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public Usuario getUsuario2() {
        return usuario2;
    }

    public void setUsuario2(Usuario usuario2) {
        this.usuario2 = usuario2;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
    
    
    
}
