/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import datos.Mensaje;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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
           escribiendo(texto);
           //logIn(Username,Pass);
           //signUp(Username,Pass);
        }while(texto.compareTo("adios")!=0);
    }
    
    
    public synchronized void escribiendo(String texto)
    {
        Mensaje men = new Mensaje();
        men.setOperacion("MENSAJE");
        men.setMensaje(texto);
        try
        {
            synchronized(OOS){
                OOS.writeObject(men);
            }
        } catch (IOException ex) 
        {
            System.out.println("Fallo en Escritura");  
        }
    }
    public void logIn(String Username,String Pass){
        Mensaje log = new Mensaje();
        String localIp;
        log.setOperacion("LOGIN");
        log.setNombre(Username);
        log.setMensaje(Pass);
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex){
           localIp = "";
        }
        log.setRemitente(localIp);
    }
    public void signUp(String Username,String Pass){
        Mensaje sign = new Mensaje();
        sign.setOperacion("SIGNUP");
        sign.setNombre(Username);
        sign.setMensaje(Pass);
        try{
           synchronized (OOS){
            OOS.writeObject(OOS);
           }
        }catch (IOException ex) {
           //¿Como mandar error a vista?
        }
    }
    
}
