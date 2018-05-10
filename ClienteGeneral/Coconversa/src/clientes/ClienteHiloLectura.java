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
import datos.Grupo;
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
                    case"CREAR_GRUPO":
                      if(Paquete.isEstado())
                        {
                            Chat.listModelGrupo.clear();
                            List<Grupo> y ;
                            y=Paquete.getListaGrupos();
                            for(int i = 0; i<y.size(); i++)
                            {
                                Chat.listModelGrupo.addElement(y.get(i).getNombre());
                            }
                        }
                    break;
                    case"ELIMINAR_GRUPO":
                    if(Paquete.isEstado())
                        {
                            Chat.listModelGrupo.clear();
                            FormExitosoGeneral exito= new FormExitosoGeneral("Grupo Eliminado");
                            exito.setVisible(true);
                            ClienteHiloEscritura CE= new ClienteHiloEscritura();
                            CE.verGrupos(Chat.lblUsuarioChat.getText());
                        }
                    break;
                    case"SALIR_GRUPO":
                    if(Paquete.isEstado())
                        {
                            Chat.listModelGrupo.clear();
                            FormExitosoGeneral exito = new FormExitosoGeneral("Saliste del grupo");
                            exito.setVisible(true);
                            ClienteHiloEscritura CE= new ClienteHiloEscritura();
                            CE.verGrupos(Chat.lblUsuarioChat.getText());
                        }
                    break;
                    case"MODIFICAR_GRUPO":
                    if(Paquete.isEstado())
                        {
                            Chat.listModelGrupo.clear();
                            FormExitosoGeneral exito = new FormExitosoGeneral("Grupo Modificado");
                            exito.setVisible(true);
                            ClienteHiloEscritura CE= new ClienteHiloEscritura();
                            CE.verGrupos(Chat.lblUsuarioChat.getText());
                        }
                    break;
                    case"SOLICITAR_AMIGOS":
                        Chat.ListUsuario = Paquete.getListaUsuarios();
                    break;
                    case"MENSAJE_NUEVO":
                        boolean Creado = false;
                        int k = 0;
                        for(k=0; k<Chat.ChatsAbiertos.size();k++){
                            if(Chat.ChatsAbiertos.get(k).nombre.equals(Paquete.getRemitente())){
                                Chat.ChatsAbiertos.get(k).AreaChat.append("\n"+Paquete.getMensaje());
                                Creado = true;
                            }
                        }
                        if(!Creado){
                            Chat.chatCreador(Paquete.getRemitente(),false);
                            Chat.ChatsAbiertos.get(k).AreaChat.append("\n"+Paquete.getMensaje());
                        }
                        
                    break;
                    case"MENSAJE_A_GRUPO":
                        boolean CreadoG1 = false;
                        int n = 0;
                        for(n=0; n<Chat.ChatsGruposAbiertos.size();n++){
                            if(Chat.ChatsGruposAbiertos.get(n).nombre.equals(Paquete.getRemitente())){
                                Chat.ChatsGruposAbiertos.get(n).AreaChat.append("\n"+Paquete.getMensaje());
                                CreadoG1 = true;
                            }
                        }
                        if(!CreadoG1){
                            Chat.chatCreador(Paquete.getRemitente(),false);
                            Chat.ChatsGruposAbiertos.get(n).AreaChat.append("\n"+Paquete.getMensaje());
                        }
                    break;
                    case"GET_MENSAJES":       
                        boolean Creado2 = false;
                        int j = 0;
                        String R2 = Paquete.getRemitente();
                        List<String> Mensajes = Paquete.getListMensajes();
                        for(j = 0; j<Chat.ChatsAbiertos.size();j++){
                            if(Chat.ChatsAbiertos.get(j).nombre.equals(R2)){
                                for(int m = 0; m< Mensajes.size();m++){
                                   Chat.ChatsAbiertos.get(j).AreaChat.append("\n"+ Mensajes.get(m));   
                                }  
                                Creado2 = true;
                            }                
                        }
                        if(!Creado2){
                            Chat.chatCreador(Paquete.getRemitente(),false);
                            for(int m = 0; m< Mensajes.size();m++){
                                Chat.ChatsAbiertos.get(j).AreaChat.append("\n"+ Mensajes.get(m));   
                            }  
                        }
                    break;
                    case"GET_MENSAJES_GRUPO":
                        boolean CreadoG = false;
                        int j2 = 0;
                        String R2g = Paquete.getRemitente();
                        List<String> MensajesG = Paquete.getListMensajes();
                        for(j2 = 0; j2<Chat.ChatsGruposAbiertos.size();j2++){
                            if(Chat.ChatsGruposAbiertos.get(j2).nombre.equals(R2g)){
                                for(int m = 0; m< MensajesG.size();m++){
                                   Chat.ChatsGruposAbiertos.get(j2).AreaChat.append("\n"+ MensajesG.get(m));   
                                }  
                                CreadoG = true;
                            }                
                        }
                        if(!CreadoG){
                            Chat.chatCreador(Paquete.getRemitente(),true);
                            for(int m = 0; m< MensajesG.size();m++){
                                Chat.ChatsGruposAbiertos.get(j2).AreaChat.append("\n"+ MensajesG.get(m));   
                            }  
                        }
                    break;
                    case"ELIMINAR_AMIGO":
                        if(Paquete.isEstado())
                        {
                            FormExitosoGeneral exito= new FormExitosoGeneral("Registro Eliminado");
                            exito.setVisible(true);
                            ClienteHiloEscritura CE= new ClienteHiloEscritura();
                            CE.amigosConectados(Chat.Usuario);
                            CE.amigosDesconectados(Chat.Usuario);
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
