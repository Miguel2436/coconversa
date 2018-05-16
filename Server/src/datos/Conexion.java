/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;

/**
 * Esta clase es el modelo de la tabla conexion de la BD,
 * util para convertir la informacion a objetos utiles.
 * @author Leonardo Martinez
 */
public class Conexion implements Serializable{
    private int idConexion;
    private String ipAddress;
    private int estado;
    private String usuario;
    /**
     * Obtiene el valor de idConexion
     * @return el valor de idConexion
     */
    public int getIdConexion() {
        return idConexion;
    }
    /**
     * Asigna el valor de idConexion
     * @param idConexion el nuevo valor de idConexion
     */
    public void setIdConexion(int idConexion) {
        this.idConexion = idConexion;
    }
    /**
     * Obtiene el valor de ipAddress
     * @return el valor de ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }
    /**
     * * Asigna el valor de ipAddress
     * @param ipAddress el nuevo valor de ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    /**
     * Obtiene el valor de estado
     * @return el valor de estado
     */
    public int getEstado() {
        return estado;
    }
    /**
     * * Asigna el valor de estado
     * @param estado el nuevo valor de estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }
    /**
     * Obtiene el valor de usuario
     * @return el valor de usuario
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * * Asigna el valor de usuario
     * @param usuario el nuevo valor de usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Conexion() {
    }
    /**
     * Constructor con parametros para la creación y asignación de valores de un objeto de tipo conexion
     * @param idConexion int 
     * @param ipAddress string
     * @param estado int
     * @param usuario string
     */
    public Conexion(int idConexion, String ipAddress, int estado, String usuario) {
        this.idConexion = idConexion;
        this.ipAddress = ipAddress;
        this.estado = estado;
        this.usuario = usuario;
    }
    
}
