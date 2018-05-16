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
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
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
    private ObjectOutputStream OOS;
    private ObjectInputStream OIS;
    private Mensaje Paquete;
    private FormChat Chat = null ;
    private ClienteHiloEscritura escribir;
    private Usuario user;
    public ClienteHiloLectura (){
   }
  /**
   * Constructor de la clase para inicializar object output stream y object input stream en el cliente y el socket por el que se va a comunicar con el server
   * @param OOS
   * @param socketParametro
   * @param Chat
   * @throws IOException 
   */ 
   public ClienteHiloLectura (ObjectOutputStream OOS, Socket socketParametro,FormChat Chat) throws IOException{
        this.clientesSocketLectura = socketParametro;
        this.OOS = OOS;
        this.escribir = new ClienteHiloEscritura(this.OOS);
        this.OIS = new  ObjectInputStream (socketParametro.getInputStream());
        Paquete = new Mensaje();
        this.Chat = Chat;
   }
   /**
    * Constantemente lee el socket con el ois(object input stream), después con la operación recibida se manda
    * a llamar a la función de switch
    */
    @Override
    public void run()    
    {
        while(true){
            try {
                    Paquete = (Mensaje)OIS.readObject();
                    Valor = Paquete.getOperacion();
                    System.out.println("Operacion: "+Valor);
                    SWITCH(Valor);
                    Valor = "";
            } catch (IOException ex) {
                //Fallo conexion Server,mandar mensaje error y reiniciar 
            } catch (ClassNotFoundException ex) {
                //Error de codigo
            }
        }
    }
    /**
     * Recibe el paquete del server y de acuerdo a la operación obtiene los datos que requiere del paquete y muestra el form correspondiente
     * @param v 
     */
    public void SWITCH(String v){
        switch(v){
        case"LOGIN":
            if(Paquete.isEstado()){
                user = new Usuario(Paquete.getNombre(), "");
                Chat.lblUsuarioChat.setText(Paquete.getNombre());
                Chat.Usuario = Paquete.getNombre();
                Chat.setVisible(true);
                escribir.notificaciones(Paquete.getNombre());
                escribir.amigosConectados(Paquete.getNombre());
                escribir.amigosDesconectados(Paquete.getNombre());
                escribir.verGrupos(Paquete.getNombre());
            }else{
                FormLogIn LogIn = new FormLogIn(OOS);
                LogIn.setVisible(true);
                FormErrorGeneral error = new FormErrorGeneral("Datos Usuarios Incorrectos");
                error.setVisible(true);
            }
            
        break;

        case"SIGNUP":
            if(Paquete.isEstado()){
                FormUsuarioRegistrado UsuarioRegistrado = new FormUsuarioRegistrado(OOS);
                UsuarioRegistrado.setVisible(true);   
            }else{
                FormErrorGeneral error = new FormErrorGeneral("Fallo Registro,Cuenta ya existente");
                error.setVisible(true);
                FormLogIn login = new FormLogIn(OOS);
                login.setVisible(true);
            }
        break;
        case"EXISTE_USUARIO":
            if(Paquete.isEstado()){
                FormUsuarioEncontrado UsuarioEncontrado = new FormUsuarioEncontrado(OOS,Paquete.getNombre(),Chat.Usuario);
                UsuarioEncontrado.setVisible(true);
                }else{
                FormUsuarioNoEncontrado UsuarioE = new FormUsuarioNoEncontrado();
                UsuarioE.setVisible(true);
            }
        break;
        case"AGREGAR_AMIGO":
            if(!Paquete.isEstado()){
                FormErrorGeneral ERRORG = new FormErrorGeneral("Error envio solicitud");
                ERRORG.setVisible(true);
            }
            /*
            FormSolicitudAmigo x = new FormSolicitudAmigo(OOS,Paquete.getNombre(),Chat.lblUsuarioChat.getText());
            x.setVisible(true);*/
        break;
        case"NOTIFICACIONES":
            //System.out.println("hola");
           if(Paquete.isEstado()){
              for(int p=0; p<Paquete.getListaUsuarios().size();p++){
                Paquete.getListaUsuarios().get(p);
                FormSolicitudAmigo solicitud = new FormSolicitudAmigo(OOS,Chat.Usuario,Paquete.getListaUsuarios().get(p).getNombre());
                solicitud.setVisible(true);
            }
           }else{

           }
        break;
        case"CREAR_GRUPO":
          if(Paquete.isEstado())
            {
                escribir.verGrupos(user.getNombre());
            }else {
              FormErrorGeneral feg = new FormErrorGeneral("Error, intenta usar otro nombre de grupo");
              feg.setVisible(true);
          }
        break;
        case"ELIMINAR_GRUPO":
        if(Paquete.isEstado())
            {
                FormExitosoGeneral exito= new FormExitosoGeneral("Grupo Eliminado");
                exito.setVisible(true);
                ClienteHiloEscritura CE= new ClienteHiloEscritura(OOS);
                CE.verGrupos(Chat.lblUsuarioChat.getText());
            }
        break;
        case"SALIR_GRUPO":
        if(Paquete.isEstado())
            {
                Chat.listModelGrupo.clear();
                FormExitosoGeneral exito = new FormExitosoGeneral("Saliste del grupo");
                exito.setVisible(true);
                ClienteHiloEscritura CE= new ClienteHiloEscritura(OOS);
                CE.verGrupos(Chat.lblUsuarioChat.getText());
            }
        break;
        case"MODIFICAR_GRUPO":
        if(Paquete.isEstado())
            {
                FormExitosoGeneral exito = new FormExitosoGeneral("Grupo Modificado");
                exito.setVisible(true);
                ClienteHiloEscritura CE= new ClienteHiloEscritura(OOS);
                CE.verGrupos(Chat.lblUsuarioChat.getText());
            } else {
            FormErrorGeneral feg = new FormErrorGeneral("Error al modificar grupo");
            feg.setVisible(true);                    
        }
        break;
        case"SOLICITAR_AMIGOS":
            Chat.ListUsuario = Paquete.getListaUsuarios();
        break;
        case"MENSAJE":
            if(Paquete.isEstado()){
                FormErrorGeneral Eenvio = new FormErrorGeneral("Mensaje enviado");
                Eenvio.setVisible(true);
            }else{
                FormErrorGeneral Eenvio = new FormErrorGeneral("Error envio mensajes");
                Eenvio.setVisible(true);
            }
        break;
        case"MENSAJE_NUEVO":
            boolean Creado = false;
            int k = 0;
            if (Paquete.getRemitente().equals(Chat.Usuario)) {
                break;
            }
            if (Paquete.getNombre() != null){
                for(k=0; k<Chat.ChatsGruposAbiertos.size();k++){
                    if(Chat.ChatsGruposAbiertos.get(k).nombre.equals(Paquete.getDestinatario())){
                        Chat.ChatsGruposAbiertos.get(k).AreaChat.append("\n"+Paquete.getMensaje());
                        Creado = true;
                    }               
                }
            } else {
                 for(k=0; k<Chat.ChatsAbiertos.size();k++){    
                    if(Chat.ChatsAbiertos.get(k).nombre.equals(Paquete.getRemitente())){
                        Chat.ChatsAbiertos.get(k).AreaChat.append("\n"+Paquete.getMensaje());
                        Creado = true;
                    }
                 }
            }
            if(!Creado){
                if (Paquete.getNombre() == null) {
                    Chat.chatCreador(Paquete.getRemitente(),false);
                    for (int i = 0; i < Chat.ChatsAbiertos.size(); i++) {
                        if (Chat.ChatsAbiertos.get(i).nombre.equals(Paquete.getRemitente())) Chat.ChatsAbiertos.get(k).AreaChat.append("\n"+Paquete.getMensaje());
                    }
                }else {
                    Chat.chatCreador(Paquete.getDestinatario(),true);
                    for (int i = 0; i < Chat.ChatsGruposAbiertos.size(); i++) {
                        if (Chat.ChatsGruposAbiertos.get(i).nombre.equals(Paquete.getDestinatario())) Chat.ChatsGruposAbiertos.get(k).AreaChat.append("\n"+Paquete.getMensaje());
                    }
                }
            }
            
        break;
        case "MENSAJE_A_GRUPO":
            if (!Paquete.isEstado()) {
                FormErrorGeneral feg = new FormErrorGeneral("Error al enviar mensaje al grupo");
                feg.setVisible(true);
            }
        break;
        case"GET_MENSAJES":       
            boolean Creado2 = false;
            int j = 0;
            String R2 = Paquete.getRemitente();
            List<String> Mensajes = Paquete.getListaMensajes();
            for(j = 0; j<Chat.ChatsAbiertos.size();j++){
                if(Chat.ChatsAbiertos.get(j).nombre.equals(R2)){
                    if(Mensajes != null){
                      for(int m = 0; m<Mensajes.size();m++){
                            Chat.ChatsAbiertos.get(j).AreaChat.append("\n"+ Mensajes.get(m));   
                      }  
                        
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
            if (!Paquete.isEstado()){
                FormErrorGeneral feg = new FormErrorGeneral("Error obtener mensajes de grupo");
                feg.setVisible(true);
            }
        break;
        case"ELIMINAR_AMIGO":
            if(Paquete.isEstado())
            {
                FormExitosoGeneral exito= new FormExitosoGeneral("Registro Eliminado");
                exito.setVisible(true);
                /*
                ClienteHiloEscritura CE= new ClienteHiloEscritura(OOS);
                CE.amigosConectados(Chat.Usuario);
                CE.amigosDesconectados(Chat.Usuario);*/
            }else{
                FormErrorGeneral errorA = new FormErrorGeneral("Error: Eliminando Amigo");
                errorA.setVisible(true);
            }
        break;
        case"AMIGOS_CONECTADOS":
            Chat.listModel.clear();
            if(Paquete.isEstado())
            {
                /*FormErrorGeneral  f = new FormErrorGeneral("Conectados");
                f.setVisible(true);*/
                List<Usuario> y ;
                y=Paquete.getListaUsuarios();
                for(int i = 0; i<y.size(); i++)
                {
                    Chat.listModel.addElement(y.get(i).getNombre());
                }
            }
        break;
        case"AMIGOS_DESCONECTADOS":
            Chat.listModel2.clear();
            if(Paquete.isEstado())
            {
                List<Usuario> y ;
                y=Paquete.getListaUsuarios();
                for(int i = 0; i<y.size(); i++)
                {
                    Chat.listModel2.addElement(y.get(i).getNombre());
                }
            }
        break;
        case"SOLICITAR_GRUPOS":
            
            if(Paquete.isEstado())
            {
                /*FormErrorGeneral  f = new FormErrorGeneral("Conectados");
                f.setVisible(true);*/
                List<Grupo> y ;
                y=Paquete.getListaGrupos();
                if (y.size() > Chat.listModelGrupo.size()) {
                    FormExitosoGeneral feg = new FormExitosoGeneral("Tienes " + (y.size()-Chat.listModelGrupo.size()) + "gruopo(s) nuevo(s)");
                }
                Chat.listModelGrupo.clear();
                for(int i = 0; i<y.size(); i++)
                {
                    Chat.listModelGrupo.addElement(y.get(i).getNombre());                    
                }
            }
        break;
        case "SOLICITAR_DETALLES_GRUPO":
            if (Paquete.isEstado()) {
                Chat.ListUsuario = Paquete.getListaUsuarios();
                
            } else {
                FormErrorGeneral feg = new FormErrorGeneral("Error al obtener integrantes de grupo");
                feg.setVisible(true);
            }
            break;
        case"":
            FormErrorGeneral errorx = new FormErrorGeneral("Dato extraño del Servidor");
            errorx.setVisible(true);
        break;
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
