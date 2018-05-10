/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import datos.Mensaje;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author Leonardo Martinez
 */
public class HiloLecturaGeneral extends Thread{
    private ServerSocket serverSocket;
    private int socket;
    private HashMap<String, ObjectOutputStream> conexiones;
    
    public HiloLecturaGeneral (int socket, HashMap<String, ObjectOutputStream> conexiones) throws IOException{
        this.socket = socket;
        this.conexiones = conexiones;
        serverSocket = new ServerSocket(socket);
    }

    @Override
    public void run(){        
        Socket lectura = null;
        InetAddress direccionCliente = null;        
        int puertoCliente = 0;
        while (1 == 1) {
            try {
                lectura = serverSocket.accept();
                direccionCliente = lectura.getInetAddress();
                puertoCliente = lectura.getPort();
                System.out.println("Cliente conectado desde: " + direccionCliente+ ":" + puertoCliente);
            } catch (IOException ex) {
                System.out.println("Error conectando: " + ex.getMessage());
            }
            try {
                leerSocket(direccionCliente.toString(), lectura);
            } catch (IOException ex) {
                System.out.println("Error leyendo: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al encontrar la clase: " + ex.getMessage());
            }
            try {
                lectura.close();
                System.out.println("Conexion con " + direccionCliente.toString() + ":" + puertoCliente + " finalizada");
            } catch (IOException ex) {
                System.out.println("Error al cerrar el socket: " + ex.getMessage());
            }
        }
    }
    
    public void leerSocket(String direccionCliente, Socket lectura) throws IOException, ClassNotFoundException{
        System.out.println("Leyendo...");
        ObjectOutputStream oos = new ObjectOutputStream(lectura.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(lectura.getInputStream());
        Mensaje mensaje;
        mensaje = (Mensaje) ois.readObject();    
        System.out.println("Operacion leida: " + mensaje.getOperacion());
        if (mensaje.getOperacion().equals("SOLICITAR_CONEXION")){            
            ServerSocket puerto = new ServerSocket(0);
            Socket socket;
            mensaje.setEstado(true);
            Integer puertoLocal = puerto.getLocalPort();            
            mensaje.setMensaje(puertoLocal.toString());
            oos.writeObject(mensaje);
            socket = puerto.accept();
            ObjectOutputStream oosTemp = new ObjectOutputStream(socket.getOutputStream());
            synchronized (conexiones) {
                conexiones.put(direccionCliente, oosTemp);
            }
            Thread nuevoHiloLectura = new HiloLectura(socket, conexiones, oosTemp);
            nuevoHiloLectura.start();
        }
    }
}
