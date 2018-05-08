/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import coconversa.FormChat;
import coconversa.FormErrorGeneral;
import coconversa.FormLogIn;
import coconversa.FormUsuarioEncontrado;
import coconversa.FormUsuarioNoEncontrado;
import coconversa.FormUsuarioRegistrado;
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
    private FormChat Chat = null ;
    public ClienteHiloLectura (){
   }
   
   public ClienteHiloLectura (Socket socketParametro,FormChat Chat) throws IOException{
        this.clientesSocketLectura = socketParametro;
        this.OIS = new  ObjectInputStream (socketParametro.getInputStream());
        Paquete = new Mensaje();
        this.Chat = Chat;
   }    
    @Override
    public void run()    
    {
        while(clientesSocketLectura.isConnected()){
            try {
                Paquete = (Mensaje)OIS.readObject();
                Valor = Paquete.getOperacion();
                switch(Valor){
                    case"LOGIN":
                        if(Paquete.isEstado()){
                            Chat.lblUsuarioChat.setText(Paquete.getNombre());
                            Chat.setVisible(true);
                        }else{
                            FormErrorGeneral error = new FormErrorGeneral("Datos Usuarios Incorrectos");
                            FormLogIn LogIn = new FormLogIn();
                            LogIn.setVisible(true);
                        }
                    break;
                    case"SIGNUP":
                        if(Paquete.isEstado()){
                            FormUsuarioRegistrado UsuarioRegistrado = new FormUsuarioRegistrado();
                            UsuarioRegistrado.setVisible(true);   
                        }else{
                            FormErrorGeneral error = new FormErrorGeneral("Fallo Registro,Cuenta ya existente");
                            error.setVisible(true);
                            FormLogIn login = new FormLogIn();
                            login.setVisible(true);
                        }
                    break;
                    case"EXISTE_USUARIO":
                        if(Paquete.isEstado()){
                            FormUsuarioEncontrado UsuarioEncontrado = new FormUsuarioEncontrado(Paquete.getNombre());
                            UsuarioEncontrado.setVisible(true);
                            }else{
                            FormUsuarioNoEncontrado UsuarioE = new FormUsuarioNoEncontrado();
                            UsuarioE.setVisible(true);
                        }
                    break;
                    case"AGREGAR_AMIGO":
                        if(Paquete.isEstado()){
                            
                        }
                    break;
                    case"ELIMINAR_GRUPO":
                    break;
                    case"MODIFICAR_GRUPO":
                    break;
                }
            } catch (IOException ex) {
                //Fallo conexion Server,mandar mensaje error y reiniciar 
            } catch (ClassNotFoundException ex) {
                //Error de codigo
            }
        }
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
