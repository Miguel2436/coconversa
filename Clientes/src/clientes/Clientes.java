/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
/**
 *
 * @author Dogo
 */
public class Clientes {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //Vista Conexion
        Scanner cin = new Scanner(System.in);
        String ipServidor="192.168.77.200";
        //
        Socket clienteSocket = new Socket(ipServidor,1000);
        ObjectOutputStream OOS =(ObjectOutputStream) clienteSocket.getOutputStream();
        
        Thread hiloLectura;
        hiloLectura = new Thread(new ClienteHiloLectura(clienteSocket));
        hiloLectura.start();
        
        ClienteHiloEscritura Escritura = new ClienteHiloEscritura(clienteSocket,OOS);
        Escritura.solicitarConexion(ipServidor);
        //
        
        
       
    }
    
}
