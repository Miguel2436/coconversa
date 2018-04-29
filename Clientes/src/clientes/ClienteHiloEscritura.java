/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import datos.Mensaje;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
    ObjectOutputStream OOS;
   public ClienteHiloEscritura (){
   
   }
   
   public ClienteHiloEscritura (Socket socketParametro, ObjectOutputStream OOS){
        this.clientesSocketEscritura=socketParametro;
        this.OOS = OOS;
   
   }
    
    @Override
    public void run()
    {
        String texto = "";
        do
        {
           Scanner cin = new Scanner(System.in);     
           texto = cin.nextLine();
           Escribiendo(texto);
        }while(texto.compareTo("adios")!=0);
    }
    
    
    public synchronized void Escribiendo(String texto)
    {
        Mensaje men = new Mensaje();
        men.setOperacion("Mensaje");
        men.setMensaje(texto);
        try
        {
            OOS = (ObjectOutputStream) clientesSocketEscritura.getOutputStream();
            OOS.writeObject(men);
        } catch (IOException ex) 
        {
            System.out.println("Fallo en Escritura");  
        }
    }
    
    
}
