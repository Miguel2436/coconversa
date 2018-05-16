/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;

/**
 * Esta clase contiene las propiedades y metodos necesarios para igualar la
 * tabla Amistad de la BD con objetos utiles. 
 * @author Leonardo Martinez
 */
public class Amistad implements Serializable{    
    private int idAmistad;
    private String solicitante;
    private String solicitado;
    private int estado;
    /**
     * Obtiene el valor de idAmistad
     *
     * @return el valor de idAmistad
     */
    public int getIdAmistad() {
        return idAmistad;
    }

    /**
     * Asigna el valor de idAmistad
     *
     * @param idAmistad el nuevo valor de idAmistad
     */
    public void setIdAmistad(int idAmistad) {
        this.idAmistad = idAmistad;
    }
    /**
     * Obtiene el valor de solicitante
     * @return el valor de solicitante
     */
    public String getSolicitante() {
        return solicitante;
    }
    /**
     * Asigna el valor de solicitante
     * @param solicitante el nuevo valor de solicitante
     */
    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }
    /**
     * Obtiene el valor de solicitado
     * @return el valor de solicitado
     */
    public String getSolicitado() {
        return solicitado;
    }
    /**
     * Asigna el valor de solicitado
     * @param solicitado el nuevo valor de solicitado
     */
    public void setSolicitado(String solicitado) {
        this.solicitado = solicitado;
    }
    /**
     * Obtiene el valor de estado
     * @return el valor de estado
     */
    public int getEstado() {
        return estado;
    }
    /**
     * Asigna el valor de estado
     * @param estado el nuevo valor de estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }
    /*
        Constructor vacio
    */
    public Amistad() {
    }
/**
 * Constructor con parametros para la creación y asignación de valores de un objeto de tipo Amistad
 * @param idAmistad int
 * @param solicitante string
 * @param solicitado string
 * @param estado int
 */
    public Amistad(int idAmistad, String solicitante, String solicitado, int estado) {
        this.idAmistad = idAmistad;
        this.solicitante = solicitante;
        this.solicitado = solicitado;
        this.estado = estado;
    }
    
}
