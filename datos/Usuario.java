/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 * Esta clase contiene las propiedades correspondientes a un usuario y
 * los metodos necesarios para inicializarlas, coincide con la tabla de la BD.
 * @author Leoanrdo Martinez
 */
public class Usuario {
    private String nombre;
    private String password;
    /**
     * Obtiene valor del Nombre.
     * @return el valor del Nombre.
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Asigna el valor del nombre.
     * @param nombre el nuevo valor de nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Obtiene el valor de password
     * @return el valor de password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Asigna el valor de password
     * @param password el nuevo valor de password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Constructor vacio
     */
    public Usuario() {
    
    }
    /**
     * Constructor con variables
     * @param nombre usuario a asignar
     * @param password password a asignar
     */
    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }
}
