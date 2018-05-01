/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import datos.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    
    private Socket clientesSocketLectura; 
    private String Valor;
    private ObjectInputStream OIS;
    private Mensaje Paquete;
    public ClienteHiloLectura (){
   }
   
   public ClienteHiloLectura (Socket socketParametro) throws IOException{
        this.clientesSocketLectura = socketParametro;
        this.OIS = (ObjectInputStream)socketParametro.getInputStream();
        Paquete = new Mensaje();
   }

    @Override
    public void run() 
    {
        while(clientesSocketLectura.isConnected()){
            try {
                Paquete = (Mensaje)OIS.readObject();
            } catch (IOException ex) {
                //Fallo conexion
            } catch (ClassNotFoundException ex) {
                //Error de codigo
            }
            Valor = Paquete.getOperacion();
            switch(Valor){
                case"SOLICITAR CONEXION":
                    cambioPuerto(Paquete.getMensaje());
                break;
                case"LOGIN":
                break;
                case"SIGNUP":
                break;
                case"EXISTE USUARIO":
                break;
                case"AGREGAR AMIGO":
                break;
                case"ELIMINAR GRUPO":
                break;
                case"MODIFICAR GRUPO":
                break;
            }
        }
    }
    void cambioPuerto(String Port){
        int Puerto = Integer.parseInt(Port);
        
    }
    /*public synchronized void Leyendo()
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
    }*/
}
