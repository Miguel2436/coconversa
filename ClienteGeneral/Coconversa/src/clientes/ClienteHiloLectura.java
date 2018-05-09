/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import coconversa.FormChat;
import coconversa.FormErrorGeneral;
import coconversa.FormExitosoGeneral;
import coconversa.FormLogIn;
import coconversa.FormSolicitudAmigo;
import coconversa.FormUsuarioEncontrado;
import coconversa.FormUsuarioNoEncontrado;
import coconversa.FormUsuarioRegistrado;
import datos.Mensaje;
import datos.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
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
                        FormSolicitudAmigo x = new FormSolicitudAmigo(Paquete.getNombre(),Chat.lblUsuarioChat.getText());
                        x.setVisible(true);
                    break;
                    case"ELIMINAR_GRUPO":
                    break;
                    case"MODIFICAR_GRUPO":
                    break;
                    case"MENSAJE_NUEVO":
                        
                    break;
                    case"GET_MENSAJES":
                        //Regresa los 5 mensajes
                    break;
                    case"GET_MENSAJES_GRUPO":
                        //Regresa los anteriores mensajes
                    break;
                    case"ELIMINAR_AMIGO":
                        if(Paquete.isEstado())
                        {
                            FormExitosoGeneral exito= new FormExitosoGeneral("Registro Eliminado");
                            exito.setVisible(true);
                            ClienteHiloEscritura CE= new ClienteHiloEscritura();
                            CE.amigosConectados();
                            CE.amigosDesconectados();
                        }
                    break;
                    case"AMIGOS_CONECTADOS":
                        if(Paquete.isEstado())
                        {
                            Chat.listModel.clear();
                            List<Usuario> y ;
                            y=Paquete.getListaUsuarios();
                            for(int i = 0; i<y.size(); i++)
                            {
                                Chat.listModel.addElement(y.get(i).getNombre());
                            }
                        }
                    break;
                    case"AMIGOS_DESCONECTADOS":
                        if(Paquete.isEstado())
                        {
                            Chat.listModel.clear();
                            List<Usuario> y ;
                            y=Paquete.getListaUsuarios();
                            for(int i = 0; i<y.size(); i++)
                            {
                                Chat.listModel2.addElement(y.get(i).getNombre());
                            }
                        }
                    break;
                    case"":
                        FormErrorGeneral errorx = new FormErrorGeneral("Dato extraño del Servidor");
                        errorx.setVisible(true);
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
