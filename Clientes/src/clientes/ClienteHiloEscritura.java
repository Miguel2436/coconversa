/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dogo
 */
public class ClienteHiloEscritura implements Runnable {

    Socket clientesSocketEscritura; 
  
   public ClienteHiloEscritura (){
   
   }
   
   public ClienteHiloEscritura (Socket socketParametro){
        this.clientesSocketEscritura=socketParametro;
   
   }
    
    @Override
    public void run()
    {
        String mensaje = "";
        do
        {
        Scanner cin = new Scanner(System.in);     
        mensaje = cin.nextLine();
        Escribiendo(mensaje);
        }while(mensaje.compareTo("adios")!=0);
    }
    
    
    public synchronized void Escribiendo(String mensaje)
    {
        try
        {
            clientesSocketEscritura.getOutputStream().write(mensaje.length());
            clientesSocketEscritura.getOutputStream().write(mensaje.getBytes());
        } catch (IOException ex) 
        {
            System.out.println("Fallo en Escritura");  
        }
    }
    
    
}
