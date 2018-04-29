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
public class ClienteHiloLectura implements Runnable
{
    
    Socket clientesSocketLectura; 
    byte []arreglo;
   public ClienteHiloLectura (){
   
   }
   
   public ClienteHiloLectura (Socket socketParametro){
        this.clientesSocketLectura=socketParametro;
   
   }

    @Override
    public void run() 
    {
        int i =1;
        String mensaje = "";
        do
        {
        Leyendo();  
        }while(i<5);
    }
    
    public synchronized void Leyendo()
    {
        try 
        {
            int can = clientesSocketLectura.getInputStream().read();
            arreglo = new byte[can];
            clientesSocketLectura.getInputStream().read(arreglo);
            String F = new String(arreglo,"UTF-8");
            System.out.println("PUTO: " + F);
        } catch (IOException ex) 
        {
            System.out.println("Fallo en Lectura");
        }
    }
}
