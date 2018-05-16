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
public class LogConversacion {
    private String remitente;
    List<String> mensajes;

    public LogConversacion() {
    }

    public LogConversacion(Usuario remitente) {
        this.remitente = remitente.getNombre();
        this.mensajes = new ArrayList<>();
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente.getNombre();
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
    public void addMensaje(String mensaje) {
        mensajes.add(mensaje);
    }
    
    
}
