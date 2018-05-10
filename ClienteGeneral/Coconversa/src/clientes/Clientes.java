/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;


import datos.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Dogo
 */
public class Clientes {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        //Vista Conexion
        Scanner cin = new Scanner(System.in);
        String ipServidor = "192.168.84.96";
        //String ipServidor = cin.nextLine();
        //
        
        Socket clienteSocket =  null;
        try {
            clienteSocket = new Socket(ipServidor,1000);
            //ObjectOutputStream OOS =(ObjectOutputStream) clienteSocket.getOutputStream();
        } catch (IOException ex) {
        
        }
        //ClienteHiloEscritura Escritura = new ClienteHiloEscritura(clienteSocket);
//        Escritura.solicitarConexion(ipServidor);
        ObjectInputStream OIS = null;
        try {
            OIS = new ObjectInputStream(clienteSocket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error: "+ ex.getMessage());
        }
        Mensaje men = new Mensaje();
        Socket clienteS =  null;
        try {
            men = (Mensaje)OIS.readObject();
            if(men.getOperacion().equals("SOLICITAR_CONEXION") && men.isEstado()){
             clienteSocket.close();
             clienteS = new Socket(ipServidor,Integer.parseInt(men.getMensaje()));
            }else{
                //Error no respuesta del servidor reiniciar proceso
            }
        } catch (ClassNotFoundException ex) {
        } catch (IOException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Conectado :D a "+clienteS.getPort());
        Thread hiloLectura = null;
        /*try {
            hiloLectura = new Thread(new ClienteHiloLectura(clienteS));
        } catch (IOException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        hiloLectura.start();
        //Formulario
        //ClienteHiloEscritura Esc = new ClienteHiloEscritura(clienteSocket);
//        Escritura.solicitarConexion(ipServidor);
        //
        //
    }
    
}
