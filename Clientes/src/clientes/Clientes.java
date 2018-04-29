/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import datos.Conexion;
import java.io.IOException;
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
        String ipServidor="192.168.84.200";
        String mensaje = "";
        Socket clienteSocket= new Socket(ipServidor,1234);
        Thread hiloEscritura, hiloLectura;
        
        hiloEscritura = new Thread(new ClienteHiloEscritura(clienteSocket));
        hiloLectura = new Thread(new ClienteHiloLectura(clienteSocket));
        
        hiloEscritura.start();
        hiloLectura.start();
        // TODO code application logic here
    }
    
}
