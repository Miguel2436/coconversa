/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import coconversa.FormErrorGeneral;
import coconversa.FormLogIn;
import coconversa.FormSignUp;
import coconversa.FormSolicitudConexion;
import datos.Amistad;
import datos.IntegrantesGrupo;
import datos.Mensaje;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Dogo
 */
public class ClienteHiloEscritura{

   private Socket clientesSocketEscritura; 
   private ObjectOutputStream OOS;
   public ClienteHiloEscritura (){
   
   }
   
   public ClienteHiloEscritura (Socket socketParametro){
        this.clientesSocketEscritura=socketParametro;
       try {
           this.OOS = new ObjectOutputStream(clientesSocketEscritura.getOutputStream());
       } catch (IOException ex) {
       }
   }
    
   
   
   ////Funciones de conexion
   /**
    * Funcion para conectarse al servidor y que el servidor genere un socket específico
    * para la conexion de este cliente
    * @param serverIp Ip del servidor
    */
    public  void solicitarConexion(String serverIp)
    {
        Mensaje conexion= new Mensaje();
        conexion.setOperacion("SOLICITAR_CONEXION");
        conexion.setDestinatario(serverIp);
        try{
            OOS.writeObject(conexion);
        }catch (IOException ex) {
            try {
                clientesSocketEscritura.close();
                clientesSocketEscritura = null;
            } catch (IOException ex1) {
                clientesSocketEscritura = null;
            }
            FormSolicitudConexion conex = new FormSolicitudConexion();
            conex.setVisible(true);
            FormErrorGeneral error = new FormErrorGeneral("Fallo local solicitando conexion a "+ serverIp);
            error.setVisible(true);
        }
    }
    /**
     * Funcion para ingresar a sistema
     * @param Username Usuario del cliente
     * @param Pass contraseña del cliente
     */
    public void logIn(String Username,String Pass){
        Mensaje log = new Mensaje();
        String localIp;
        log.setOperacion("LOGIN");
        log.setNombre(Username);
        log.setMensaje(Pass);
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
            log.setRemitente(localIp);
            OOS.writeObject(log);
        } catch (UnknownHostException ex){
            FormLogIn log1 = new FormLogIn();
            log1.setVisible(true);
            FormErrorGeneral error = new FormErrorGeneral("Fallo Obtencion Ip Local");
            error.setVisible(true);
        }catch (IOException ex) {
           FormLogIn log2 = new FormLogIn();
           log2.setVisible(true);
           FormErrorGeneral error = new FormErrorGeneral("Fallo Envio credenciales, verifique su conexion");
           error.setVisible(true);
        }
    }
    /**
     * Funcion para registrarse en el sistema
     * @param Username Usuario del sistema
     * @param Pass Contraseña del usuario
     */
    public void signUp(String Username,String Pass){
        Mensaje sign = new Mensaje();
        sign.setOperacion("SIGNUP");
        sign.setNombre(Username);
        sign.setMensaje(Pass);
        try{
            OOS.writeObject(sign);
        }catch (IOException ex) {
            FormErrorGeneral error = new FormErrorGeneral("Fallo Envio credenciales, verifique su conexion");
           error.setVisible(true);
           FormSignUp form = new FormSignUp();
           form.setVisible(true);
        }
    }
    /////////////////////////
    ///Funciones de Usuario//
    /////////////////////////
    /**
     * Genera una solicitud de amistad al usuario que se le envie.
     * @param Username Nombre de usuario al que se desea agregar.
     */
    public  void agregarAmigo(String Username)
    {
        Mensaje agregar = new Mensaje();
        agregar.setOperacion("AGREGAR_AMIGO");
        agregar.setNombre(Username);
        try{ 
            
            OOS.writeObject(agregar);
           
        }catch (IOException ex) {
         
        }
        
    }
    /**
     * Funcion para aceptar o recharzar una solicitud de amistad previamente realizada
     * por otro cliente
     * @param username Nombre de Usuario
     * @param amigo Nombre del usuario que espera la respuesta
     * @param respuesta Respuesta de amistad True = Aceptado, False = No Aceptado
     */
    public void respuestaAmigo(String username,String amigo,boolean respuesta)
    {
        Mensaje add = new Mensaje();
        add.setOperacion("RESPUESTA_AMIGO");
        add.setRemitente(username);
        add.setDestinatario(amigo);
        add.setEstado(respuesta);
        try
        {
            OOS.writeObject(add);
        }catch(IOException ex){}
    }
     
    /**
     * Elimina de tu lista de amigos el usuario que sea ingresado
     * @param amigo usuario a eliminar de amistad
     * @param Usuario usuario que desea eliminar algun amigo
     */
    public void eliminarAmigo(String Usuario,String amigo)
    {
      Mensaje remove = new Mensaje();
      remove.setOperacion("ELIMINAR_AMIGO");
      remove.setNombre(amigo);
      remove.setRemitente(Usuario);
      
      try
        {
            OOS.writeObject(remove);
        }catch(IOException ex){}
    }
    /**
     * Manda solicitud para que el servidor responda con la lista de amigos que
     * el usuario
     * conectados y amigos desconectados.
     * @param Usuario usuario que desea ver su lista de amigos
     */
    public void verAmigos(String Usuario)
    {
      Mensaje remove = new Mensaje();
      remove.setOperacion("VER_AMIGOS");      
      try
        {
            OOS.writeObject(remove);
        }catch(IOException ex){}
    }
    
    ///////////////////////
    //Funciones de Grupo//
    //////////////////////
    /**
     * Funcion que crea un Grupo de chat
     * @param Name Nombre del grupo a crear
     * @param Amigos Lista de amigos que serán agregados al grupo
     */
    public void crearGrupo(String Name, List<Amistad> Amigos)
    {
      Mensaje cGroup = new Mensaje();
      cGroup.setOperacion("CREAR_GRUPO");
      cGroup.setNombre(Name);
      cGroup.setListaAmistades(Amigos);
      try
        {
            OOS.writeObject(cGroup);
        }catch(IOException ex){
        }
    }
    /**
     * Modifica los integrantes del grupo enviado
     * @param Groupname Nombre de grupo a modificar
     * @param listaIntegrantesGrupo Lista de Integrantes de grupo actualizada
     */
    public void modificarGrupo(String Groupname, List<IntegrantesGrupo> listaIntegrantesGrupo)
    {
        Mensaje modificar = new Mensaje();
        modificar.setOperacion("MODIFICAR_GRUPO");
        modificar.setNombre(Groupname);
        modificar.setListaIntegrantesGrupo(listaIntegrantesGrupo);
        try{
            
            OOS.writeObject(modificar);
          
        }catch (IOException ex) {
         
        }
        
    }/**
     * Elimina grupo específico
     * @param Groupname  Nombre de grupo a eliminar
     */
    public  void eliminarGrupo(String Groupname)
    {
        Mensaje eliminar = new Mensaje();
        eliminar.setOperacion("ELIMINAR_GRUPO");
        eliminar.setNombre(Groupname);
        try{
        
            OOS.writeObject(eliminar);
           
        }catch (IOException ex) {
         
        }
        
    }
    /**
     * Pide al servidor que retorne la lista de usuarios de un grupo específico
     * @param GroupName Nombre de grupo a pedir integrantes de grupo
     * @param Usuario Nombre usuario que desea ver integrantes grupo
     */
    public void verUsuariosGrupo(String GroupName, String Usuario){
        Mensaje SGrupo= new Mensaje();
        SGrupo.setOperacion("VER_USUARIOS_GRUPO");
        SGrupo.setNombre(GroupName);
        SGrupo.setRemitente(Usuario);
                
        try{
            OOS.writeObject(SGrupo);
        }catch(IOException ex){
        }
    }
    
    /**
     * Pide al servidor que le liste los grupos a los que pertenece el usuario
     * @param Usuario Usuario que desea ver a que grupos pertenece.
     */
    public void verGrupos(String Usuario){
        Mensaje SGrupo= new Mensaje();
        SGrupo.setOperacion("VER_GRUPOS");
        try{
            OOS.writeObject(SGrupo);
        }catch(IOException ex){
        }
    }
    
    //////////////////////
    //Funciones Busqueda//
    /////////////////////
    public  void existeUsuario(String Username)
    {
        Mensaje existe = new Mensaje();
        existe.setOperacion("EXISTE_USUARIO");
        existe.setNombre(Username);
        try{
           
            OOS.writeObject(existe);
           
        }catch (IOException ex) {
        }
        
    }
}
