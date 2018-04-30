/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import datos.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Leonardo Martinez
 */
public class HiloLectura extends Thread{

    ServerSocket socket;
    public HiloLectura(ServerSocket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            Socket lectura = socket.accept();
            leerSocket(lectura);                                          
        } catch (IOException ex) {
            System.out.println("Error conectando con cliente: " + ex.getMessage());
        }
    }
    public void leerSocket(Socket lectura) {
        
        Mensaje mensaje;
        while (1 == 1) {
            try {
                ObjectInputStream ois = new ObjectInputStream(lectura.getInputStream());
                mensaje = (Mensaje) ois.readObject();
                switch (mensaje.getOperacion()) {
                    case "LOGIN":
                        break;
                    case "SIGNUP":
                        break;
                    case "EXISTEUSUARIO":
                        break;
                    case "AGREGARAMIGO":
                        break;
                    case "ELIMINARAMIGO":
                        break;
                    case "CREARGRUPO":
                        break;
                    case "ELIMINARGRUPO":
                        break;
                    case "MODIFICARGRUPO":
                        break;
                    case "SOLICITARAMIGOS":
                        break;
                    case "SOLICITARGRUPOS":
                        break;
                    case "ACEPTARAMIGO":
                        break;
                    case "CERRARSESION":
                        break;
                    case "":
                        break;
                    default:
                        break;
                }
            } catch (IOException ex) {
                System.out.println("Error leyendo: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al encontrar la clase:" + ex.getMessage());
            }
        }
    }
    
}
