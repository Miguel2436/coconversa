/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import datos.Conexion;
import datos.Grupo;
import datos.Mensaje;
import datos.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo Martinez
 */
public class HiloLectura extends Thread{

    private Escritura escritura;
    private ServerSocket serverSocket;
    private HashMap<String, HiloLectura> conexiones;
    private Usuario user;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public HiloLectura(HashMap<String, HiloLectura> conexiones, ServerSocket serverSocket) {
        System.out.println("Creando Hilo lectura");
        escritura = new Escritura();
        this.conexiones = conexiones;
        this.serverSocket = serverSocket;
    }
    @Override
    /**
     * Función que crea un 'canal' dedicado a cada cliente después de haber establecido la comunicación con el server en el hilo de lectura general.
     */
    public void run() {
        try {
            Socket lectura = serverSocket.accept();
            oos = new ObjectOutputStream(lectura.getOutputStream());
            ois = new ObjectInputStream(lectura.getInputStream());
            System.out.println("Conexion especifica con: " + lectura.getInetAddress() + ":" + lectura.getPort() + " hecha desde el puerto: " + lectura.getLocalPort());
            leerSocket(lectura);                                          
        } catch (IOException ex) {
            System.out.println("Error conectando con cliente: " + ex.getMessage());
        }
    }
    /**
     * Función que manda a llamar a las operaciones en la BD según las solicitudes del cliente
     * @param lectura socket del cliente que solicita la operacion en la BD y que se utiliza para crear una nueva conexión
     * @throws IOException 
     */
    public void leerSocket(Socket lectura) throws IOException {        
        boolean condicion = true;
        synchronized (oos) {
            Mensaje mensaje;
            while (condicion) {
                try {     
                    mensaje = (Mensaje) ois.readObject();
                    Usuario usuario;
                    Conexion conexion;
                    Usuario usuario2;
                    Usuario destinatario;
                    Usuario remitente;
                    Grupo grupo;
                    System.out.println("Operacion leida: " + mensaje.getOperacion());
                    switch (mensaje.getOperacion()) {
                        case "LOGIN":
                            if (mensaje.getNombre() == null || mensaje.getMensaje() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            System.out.println(mensaje.getNombre() + "||" + mensaje.getMensaje());
                            usuario = new Usuario(mensaje.getNombre(), mensaje.getMensaje()); 
                            user = usuario;
                            conexion = new Conexion(0, lectura.getInetAddress().toString(), 1, usuario.getNombre());
                            try {                                
                                mensaje.setEstado(escritura.logIn(usuario, conexion));
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "SIGNUP":
                            if (mensaje.getNombre() == null || mensaje.getMensaje() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            usuario = new Usuario(mensaje.getNombre(), mensaje.getMensaje());
                            user = usuario;
                            conexion = new Conexion(0, lectura.getInetAddress().toString(), 1, usuario.getNombre());
                            try {
                                escritura.RegistroUsuario(usuario, conexion);
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "EXISTE_USUARIO":
                            if (mensaje.getNombre() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            usuario = new Usuario(mensaje.getNombre(), "");
                            try {                             
                                mensaje.setEstado(escritura.ExisteUsuario(usuario));                               
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "AGREGAR_AMIGO":
                            usuario = new Usuario(mensaje.getRemitente(), "");
                            usuario2 = new Usuario(mensaje.getDestinatario(), "");
                            try {
                                escritura.AgregarAmigo(usuario, usuario2);
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "ELIMINAR_AMIGO":
                            usuario = new Usuario(mensaje.getRemitente(), "");
                            usuario2 = new Usuario(mensaje.getDestinatario(), "");
                            System.out.println("Solicitante = " + usuario.getNombre() + " Solicitado = " + usuario2.getNombre());
                            try {
                                escritura.EliminarAmigo(usuario, usuario2);
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                System.out.println(ex.toString());
                                mensaje.setEstado(false);
                            }
                            break;
                        case "CREAR_GRUPO":
                            if (mensaje.getNombre() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            grupo = new Grupo(0, mensaje.getNombre());
                            try {
                                escritura.crearGrupo(grupo, mensaje.getListaUsuarios());
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                System.out.println("Error escribiendo grupo a bd");
                                mensaje.setEstado(false);
                            }
                            break;
                        case "ELIMINAR_GRUPO":
                            if (mensaje.getNombre() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            grupo = new Grupo(0, mensaje.getNombre());
                            try {
                                escritura.eliminarGrupo(grupo);
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "MODIFICAR_GRUPO":
                            if (mensaje.getNombre() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            grupo = new Grupo(0, mensaje.getNombre());
                            try {
                                escritura.modificarGrupo(mensaje.getListaUsuarios(), grupo);
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "SOLICITAR_DETALLES_GRUPO":
                            grupo=new Grupo(0, mensaje.getNombre());
                            try {
                                mensaje.setListaUsuarios(escritura.detallesGrupo(grupo));
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "SOLICITAR_AMIGOS":
                            if (mensaje.getNombre() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            usuario = new Usuario(mensaje.getNombre(), "");
                            try {
                                mensaje.setListaUsuarios(escritura.SolicitarAmigos(usuario));
                                mensaje.setEstado(true);                                
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "AMIGOS_CONECTADOS":
                            if (mensaje.getNombre() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            usuario = new Usuario(mensaje.getNombre(), "");
                            try {
                                mensaje.setListaUsuarios(escritura.SolicitarAmigosConectados(usuario));                                
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "AMIGOS_DESCONECTADOS":
                            if (mensaje.getNombre() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            usuario = new Usuario(mensaje.getNombre(), "");
                            try {
                                mensaje.setListaUsuarios(escritura.SolicitarAmigosDesconectados(usuario));                                
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "SOLICITAR_GRUPOS":
                            if (mensaje.getNombre() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            usuario = new Usuario(mensaje.getNombre(), "");
                            try {
                                mensaje.setListaGrupos(escritura.SolicitarGrupos(usuario));
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "ACEPTAR_AMIGO":
                            usuario = new Usuario(mensaje.getRemitente(), "");
                            usuario2 = new Usuario(mensaje.getDestinatario(), "");
                            try {
                                escritura.AceptarAmigo(usuario, usuario2);
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "CERRAR_SESION":
                            if (mensaje.getNombre() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            usuario = new Usuario(mensaje.getNombre(), "");
                            try {
                                escritura.cerrarSesion(usuario);    //===============================> Crear funcion en esctritura que pase conexion.estado a falso
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "MENSAJE":                            
                            if(mensaje.getDestinatario() == null || mensaje.getRemitente() == null || mensaje.getMensaje() == null) {
                                mensaje.setEstado(false);
                            }
                            else{
                                System.out.println("Mensaje de " + mensaje.getRemitente() + " para " + mensaje.getDestinatario() + " : " + mensaje.getMensaje());
                                mensaje.setMensaje("[" + Time.from(Instant.now()) + "]" + mensaje.getRemitente()+ " : " + mensaje.getMensaje());
                                destinatario = new Usuario(mensaje.getDestinatario(), "");
                                String ip = "";
                                try {
                                    ip = escritura.getIp(destinatario);
                                } catch (SQLException ex) {
                                    System.out.println("Error obteniendo ip");
                                    mensaje.setEstado(false);
                                }
                                if (enviarMensaje(mensaje, ip)) mensaje.setEstado(true);
                                else mensaje.setEstado(false);                                
                            }
                            break;
                        case "MENSAJE_NUEVO":
                            //oos.writeObject(mensaje);
                            break;
                        case "MENSAJE_A_GRUPO":
                            if (mensaje.getDestinatario()== null || mensaje.getRemitente() == null || mensaje.getMensaje() == null) {
                                System.out.println("Propiedad nula");
                                mensaje.setEstado(false);
                            }else {
                                try {        
                                    System.out.println("Mensaje de " + mensaje.getRemitente() + " para " + mensaje.getDestinatario() + " : " + mensaje.getMensaje());
                                    mensaje.setMensaje("[" + Time.from(Instant.now()) + "]" + mensaje.getRemitente()+ " : " + mensaje.getMensaje());
                                    grupo = new Grupo(0, mensaje.getDestinatario());                                    
                                    List<String> ipIntegrantes = new ArrayList<>();
                                    Iterator<Usuario> itrUsuario = escritura.detallesGrupo(grupo).iterator();                                    
                                    while (itrUsuario.hasNext()){
                                        Usuario temp = itrUsuario.next();
                                        System.out.println("Sacando ip de: " + temp.getNombre());
                                        String ipUsuario = escritura.getIp(temp);
                                        System.out.println("Ip de: " + temp.getNombre() + " = " + ipUsuario);
                                        if (ipUsuario != null && !temp.getNombre().equals(mensaje.getRemitente())) ipIntegrantes.add(ipUsuario);
                                    }                                    
                                    enviarMensajeGrupo(mensaje, ipIntegrantes);
                                    mensaje.setEstado(true);
                                } catch (SQLException ex) {
                                    System.out.println("Error al leer integrantes: " + ex.toString());
                                    mensaje.setEstado(false);
                                }
                            }
                            break;
                        case "GET_MENSAJES":
                            if (mensaje.getDestinatario()== null){
                                mensaje.setEstado(false);
                                break;
                            }
                            destinatario = new Usuario(mensaje.getDestinatario(), "");
                            remitente = new Usuario(mensaje.getRemitente(), "");
                            mensaje.setListaMensajes(Log.getInstancia().getMensajes(destinatario, remitente));
                            break;
                        case "GET_MENSAJES_GRUPO":
                            if (mensaje.getNombre() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            grupo = new Grupo(0, mensaje.getNombre());
                            mensaje.setEstado(false);
                            //mensaje.setListaMensajes(Log.getInstancia().getMensajesGrupo(grupo));
                            break;
                        case "NOTIFICACIONES":
                            usuario = new Usuario(mensaje.getNombre(),"");
                                try {
                                    mensaje.setListaUsuarios(escritura.notificacionesAmistad(usuario));                                    
                                    if(mensaje.getListaUsuarios() == null) mensaje.setEstado(false);
                                    else mensaje.setEstado(true);
                                } catch (SQLException ex) {
                                    System.out.println(ex.toString());
                                    mensaje.setEstado(false);
                                }    
                            break;
                        default:
                            mensaje.setOperacion("OPERACION_DESCONOCIDA");
                            break;
                    }
                    try {
                        oos.writeObject(mensaje);
                        oos.flush();
                    } catch (IOException ex){
                        System.out.println("Error escribiendo el objeto: " + ex.toString());
                    }
                    
                } catch (IOException ex) {
                    System.out.println("Error leyendo: " + ex.getMessage());
                    System.out.println("Termina conexion especifica con: " + lectura.getInetAddress() + ":" + lectura.getPort());
                    try {
                        escritura.cerrarSesion(user);
                    } catch (SQLException e) {
                        System.out.println("Error cerrando sesion: " + ex.toString());
                    }
                    lectura.close();  
                    condicion = false;
                } catch (ClassNotFoundException e) {
                    System.out.println("Error al encontrar la clase:" + e.getMessage());
                }
                System.out.println("Leyendo....");
            }
        }
    }
    /**
     * Función que envia un mensaje recibido de un cliente a otro
     * @param mensaje objeto de tipo mensaje en el que se tiene toda la información del mensaje a enviar
     * @param ip variable tipo string que contiene la ip del destinatario, con la que se obtiene posteriormente el hilo de lectura del destinatario
     * @return retorna true si se envió correctamente, false si no fue así
     */
    public synchronized boolean enviarMensaje(Mensaje mensaje, String ip) {
        boolean res;
        HiloLectura hiloDestinatario;
        synchronized (conexiones) {
            hiloDestinatario = conexiones.get(ip);
        }
                                synchronized (hiloDestinatario) {
                                    System.out.println("Escribiendo a destinatario");
                                    res = hiloDestinatario.mensajeNuevo(mensaje);
                                    System.out.println("Mensaje enviado");
                                }
                                //funcion de guardar mensaje en archivo
//                                mensaje.setMensaje("[" + Time.from(Instant.now()).toString() + "]" + mensaje.getMensaje());
//                                destinatario = new Usuario(mensaje.getDestinatario(), "");
//                                remitente = new Usuario(mensaje.getRemitente(), "");
//                                Log.getInstancia().addMensaje(destinatario, remitente, mensaje.getMensaje());
//                                System.out.println("Guardado en log");
    return res;
    }
    /**
     * Función que recibe los mensajes nuevos del cliente y se los envia
     * @param mensaje objeto tipo mensaje
     * @return 
     */
    public synchronized boolean mensajeNuevo(Mensaje mensaje) {
        boolean res;
        try {
            mensaje.setOperacion("MENSAJE_NUEVO");
            
            oos.writeObject(mensaje);
            res = true;
        } catch (IOException ex) {
            System.out.println("Error escribiendo mensaje: " + ex.toString());
            res = false;
        }
        return res;
    }
    /**
     * Función para enviar mensajes a un gupo
     * @param mensaje objeto de tipo mensaje que contiene la información del mensaje a enviar
     * @param ipIntegrantes lista de tipo strings con las ip de todos los integrantes del grupo
     */
    public synchronized void enviarMensajeGrupo(Mensaje mensaje, List<String> ipIntegrantes) {
        mensaje.setNombre("GRUPO");
        synchronized (conexiones) {
            Iterator<String> itrIps = ipIntegrantes.iterator();
            while(itrIps.hasNext()) {
                String ip = itrIps.next();
                HiloLectura hiloDestinatario = conexiones.get(ip);
                System.out.println(ip);
                if (hiloDestinatario == null) System.out.println("Hilo nulo");
                else {
                    synchronized (hiloDestinatario){
                        if (hiloDestinatario.mensajeNuevo(mensaje)) {
                            System.out.println("Mensaje enviado a: " + hiloDestinatario.user.getNombre());
                        } else System.out.println("Error enviando a cliente");
                    }
                }
            }
        }        
    }
    
}
