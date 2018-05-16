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
/**
 * Función que retorna el remitente de los mesajes
 * @return 
 */
    public String getRemitente() {
        return remitente;
    }
/**
 * Asigna el valor del remitente
 * @param remitente 
 */
    public void setRemitente(Usuario remitente) {
        this.remitente = remitente.getNombre();
    }
    /**
     * Obtiene los mensajes enviados por dicho remitente
     * @return 
     */
    public List<String> getMensajes() {
        return mensajes;
    }
/**
 * Asigna valores a los mensajes 
 * @param mensajes 
 */
    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
    /**
     * Agrega un mensaje a la lista de mensajes
     * @param mensaje  string
     */
    public void addMensaje(String mensaje) {
        mensajes.add(mensaje);
    }
    
    
}
