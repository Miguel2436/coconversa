/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.corba.se.impl.io.IIOPOutputStream;
import datos.Conexion;
import datos.Mensaje;
import datos.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo Martinez
 */
public class HiloLectura extends Thread{

    ServerSocket socket;
    Escritura escritura;
    private HashMap<String, Socket> conexiones;
    public HiloLectura(ServerSocket socket, HashMap<String, Socket> conexiones) {
        this.socket = socket;
        escritura = new Escritura();
        this.conexiones = conexiones;
    }
    @Override
    public void run() {
        try {
            Socket lectura = socket.accept();
            System.out.println("Conexion especifica con: " + lectura.getInetAddress() + ":" + lectura.getPort() + " hecha desde el puerto: " + lectura.getLocalPort());
            leerSocket(lectura);                                          
        } catch (IOException ex) {
            System.out.println("Error conectando con cliente: " + ex.getMessage());
        }
    }
    public void leerSocket(Socket lectura) throws IOException {
        
        Mensaje mensaje;
        while (!lectura.isClosed()) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(lectura.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(lectura.getInputStream());
                mensaje = (Mensaje) ois.readObject();
                Usuario usuario;
                switch (mensaje.getOperacion()) {
                    case "LOGIN":
                        usuario = new Usuario(mensaje.getNombre(), mensaje.getMensaje());      
                        mensaje = new Mensaje();
                        mensaje.setOperacion("LOGIN");
                        try {
                            escritura.logIn(usuario);
                            mensaje.setEstado(true);
                        } catch (SQLException ex) {
                            mensaje.setEstado(false);
                        }
                        oos.writeObject(mensaje);
                        break;
                    case "SIGNUP":
                        usuario = new Usuario(mensaje.getNombre(), mensaje.getMensaje());
                        Conexion conexion = new Conexion(0, lectura.getInetAddress().toString(), 1, usuario.getNombre());                
                        mensaje = new Mensaje();
                        mensaje.setOperacion("SIGNUP");
                        try {
                            escritura.RegistroUsuario(usuario, conexion);
                            mensaje.setEstado(true);
                        } catch (SQLException ex) {
                            mensaje.setEstado(false);
                        }
                        oos.writeObject(mensaje);
                        break;
                    case "EXISTE_USUARIO":
                        break;
                    case "AGREGAR_AMIGO":
                        break;
                    case "ELIMINAR_AMIGO":
                        break;
                    case "CREAR_GRUPO":
                        break;
                    case "ELIMINAR_GRUPO":
                        break;
                    case "MODIFICAR_GRUPO":
                        break;
                    case "SOLICITAR_AMIGOS":
                        break;
                    case "SOLICITAR_GRUPOS":
                        break;
                    case "ACEPTAR_AMIGO":
                        break;
                    case "CERRAR_SESION":
                        break;
                    case "MENSAJE":
                        Socket SD = conexiones.get(mensaje.getDestinatario());
                        synchronized (SD) {
                            ObjectOutputStream oosSD = new ObjectOutputStream(SD.getOutputStream());
                            
                        }
                        break;
                    case "MENSAJE_A_GRUPO":
                        break;
                    case "GET_MENSAJES":
                        break;
                    case "GET_MENSAJES_GRUPO":
                        break;
                    default:
                        
                        break;
                }
            } catch (IOException ex) {
                System.out.println("Error leyendo: " + ex.getMessage());
                System.out.println("Termina conexion especifica con: " + lectura.getInetAddress() + ":" + lectura.getPort());
                lectura.close();                
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al encontrar la clase:" + ex.getMessage());
            }
        }
    }
    
}
