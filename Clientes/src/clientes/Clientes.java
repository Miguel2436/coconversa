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
        Scanner cin = new Scanner(System.in);
        String ipServidor="192.168.77.200";
        Socket clienteSocket = new Socket(ipServidor,1234);
        Thread hiloEscritura, hiloLectura;
        ObjectOutputStream OOS =(ObjectOutputStream) clienteSocket.getOutputStream();
        hiloEscritura = new Thread(new ClienteHiloEscritura(clienteSocket,OOS));
        hiloLectura = new Thread(new ClienteHiloLectura(clienteSocket));
        
        hiloEscritura.start();
        hiloLectura.start();
        // TODO code application logic here
    }
    
}
