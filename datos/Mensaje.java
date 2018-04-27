/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.List;

/**
 * Esta clase representa el intercambio de informacion entre cliente y servidor,
 * posee todas las posibles propiedades necesarias para la comunicacion entre cliente
 * y servidor.
 * @author Leonardo Martinez
 */
public class Mensaje implements Serializable{
   private String operacion;
   private String nombre;
   private String remitente;
   private String destinatario;
   private boolean estado;
   private String mensaje;
   private List<Amistad> listaAmistades;
   private List<Conexion> listaConexiones;
   private List<Grupo> listaGrupos;
   private List<IntegrantesGrupo> listaIntegrantesGrupo;
   private List<Usuario> listaUsuarios;
    /**
     * Obtiene el valor de operacion
     * Este indica al receptor la operacion a realizar con
     * las propiedades disponibles
     * @return 
     */
    public String getOperacion() {
        return operacion;
    }
    /**
     * Asigna el valor de operacion.
     * Este indica al receptor la operacion a realizar con
     * las propiedades disponibles
     * @param operacion La operacion a realizar, siempre en mayúsculas y 
     * debe coincidir con las operaciones reconocidas por el receptor.
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }
    /**
     * Obtiene el valor de nombre.
     * Aqui se puede enviar el nombre de usuario
     * que se quiere utilizar o revisar, o bien, el
     * nombre de grupo u otras opciones.
     * @return el valor de nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Asigna el valor de nombre
     * Aqui se puede enviar el nombre de usuario
     * que se quiere utilizar o revisar, o bien, el
     * nombre de grupo u otras opciones.
     * @param nombre el nuevo valor de nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Se obtiene el valor de remitente.
     * Esta propiedad siempre representa el
     * nombre de usuario que envia el mensaje.
     * @return el valor de remitente.
     */
    public String getRemitente() {
        return remitente;
    }
    /**
     * Asigna el valor de remitente.
     * Esta propiedad siempre representa el
     * nombre de usuario que envia el mensaje.
     * @param remitente el valor de remitente
     */
    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }
    /**
     * Obtiene el valor de destinatario.
     * Esta propiedad siempre representa el
     * nombre de usuario al que va dirigido el mensaje.
     * @return el valor de destinatario.
     */
    public String getDestinatario() {
        return destinatario;
    }
    /**
     * Asigna el valor de destinatario
     * Esta propiedad siempre representa el
     * nombre de usuario al que va dirigido el mensaje.
     * @param destinatario el nuevo valor de destinatario
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
    /**
     * Obtiene el valor de estado
     * Esta propiedad puede contener respuestas
     * booleanas del servidor o del cliente.
     * @return el valor de estado
     */
    public boolean isEstado() {
        return estado;
    }
    /**
     * Asigna el valor de estado
     * Esta propiedad puede contener respuestas
     * booleanas del servidor o del cliente.
     * @param estado el nuevo valor de estado
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    /**
     * Obtiene el valor de mensaje
     * Esta propiedad contiene el mensaje
     * que se envia, puede ser de una conversacion o
     * algun otro parametro
     * @return el valor de mensaje.
     */
    public String getMensaje() {
        return mensaje;
    }
    /**
     * Asigna el valor de mensaje.
     * Esta propiedad contiene el mensaje
     * que se envia, puede ser de una conversacion o
     * algun otro parametro
     * @param mensaje el valor de mensaje
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    /**
     * Obtiene la lista de Amistades.
     * Esta lista contiene objetos de tipo amistad,
     * obtenidos de la BD
     * @return la lista de amistades
     */
    public List<Amistad> getListaAmistades() {
        return listaAmistades;
    }
    /**
     * Asigna la lista de amistades
     * Esta lista contiene objetos de tipo amistad.
     * @param listaAmistades la lista de amistades a asignar
     */
    public void setListaAmistades(List<Amistad> listaAmistades) {
        this.listaAmistades = listaAmistades;
    }
    /**
     * Obtiene la lista de conexiones.
     * Util para conocer el estado de conexion de los usuarios y
     * sus direcciones ip. 
     * @return lista de conexiones
     */
    public List<Conexion> getListaConexiones() {
        return listaConexiones;
    }
    /**
     * Asigna la lista de conexiones
     * Util para conocer el estado de conexion de los usuarios y
     * sus direcciones ip. 
     * @param listaConexiones lista de conexiones a asignar
     */
    public void setListaConexiones(List<Conexion> listaConexiones) {
        this.listaConexiones = listaConexiones;
    }
    /**
     * Obtiene la lista de grupos.
     * Util para conocer nombres de grupo.
     * @return lista de grupos.
     */
    public List<Grupo> getListaGrupos() {
        return listaGrupos;
    }
    /**
     * Asigna la lista de grupos.
     * Util para conocer nombres de grupo.
     * @param listaGrupos lista de grupos a asignar
     */
    public void setListaGrupos(List<Grupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }
    /**
     * Obtiene la lista de Integrantes de grupos.
     * Util para conocer Integrantes de grupos.
     * @return lista de Integrantes de grupos.
     */
    public List<IntegrantesGrupo> getListaIntegrantesGrupo() {
        return listaIntegrantesGrupo;
    }
    /**
     * Asigna la lista Integrantes de grupos.
     * Util para conocer integrantes de grupos.
     * @param listaIntegrantesGrupo  lista de integrantes de grupos a asignar
     */
    public void setListaIntegrantesGrupo(List<IntegrantesGrupo> listaIntegrantesGrupo) {
        this.listaIntegrantesGrupo = listaIntegrantesGrupo;
    }
     /**
     * Obtiene la lista de usuarios.
     * Util para conocer propiedades de usuarios.
     * @return lista de usuarios.
     */
    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    /**
     * Asigna la lista de usuarios.
     * Util para conocer propiedades de usuarios.
     * @param listaUsuarios  lista de usuarios a asignar
     */
    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Mensaje() {
    }
    /**
     * Este constructor inicia un mensaje util para las operaciones entre
     * clientes, como la asignacion de amigos o conversaciones.
     * @param operacion la operacion a realizar
     * @param remitente el usuario remitente
     * @param destinatario el usuario destinatario
     * @param mensaje el mensaje o parametro a enviar
     */
    public Mensaje(String operacion, String remitente, String destinatario, String mensaje) {
        this.operacion = operacion;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.mensaje = mensaje;
    }
    
}
