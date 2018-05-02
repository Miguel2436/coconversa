/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

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
    
    /*@Override
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
    }**/
    public  void solicitarConexion(String serverIp)
    {
        Mensaje conexion= new Mensaje();
        conexion.setOperacion("SOLICITAR_CONEXION");
        conexion.setDestinatario(serverIp); //¿Cambiar a Destinatario?
        try{
           
            OOS.writeObject(conexion);
           
        }catch (IOException ex) {
           
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
            log.setRemitente(localIp);
            OOS.writeObject(log);
        } catch (UnknownHostException ex){
           //Como mandar error vista?
        }catch (IOException ex) {
           //¿Como mandar error a vista?
        }
    }
    public synchronized void signUp(String Username,String Pass){
        Mensaje sign = new Mensaje();
        sign.setOperacion("SIGNUP");
        sign.setNombre(Username);
        sign.setMensaje(Pass);
        try{
            OOS.writeObject(sign);
        }catch (IOException ex) {
           //¿Como mandar error a vista?
        }
    }
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
        
    }
   
      //Método de aagregarAmigo que se lleva Nombre y Remitente (Ya hay una función pre-existente arriba por Dogo)
   public void respuestaUsuario(String Username,String Remiente,boolean respuesta)
    {
        Mensaje add = new Mensaje();
        add.setOperacion("AGREGAR_AMIGO");
        add.setNombre(Username);
        add.setRemitente(Remiente);
        add.setEstado(respuesta);
        try
        {
            OOS.writeObject(add);
        }catch(IOException ex){}
    }
     
     //Método de eliminarAmigo que se lleva Nombre
    public void eliminarAmigo(String Username)
    {
      Mensaje remove = new Mensaje();
      remove.setOperacion("ELIMINAR_AMIGO");
      remove.setNombre("Username");
      
      try
        {
            OOS.writeObject(remove);
        }catch(IOException ex){}
    }
    
        //Método de crearGrupo que se lleva Nombre y setListaAmistades
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
}
