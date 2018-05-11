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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Leonardo Martinez
 */
public class HiloLectura extends Thread{

    private Socket socket;
    private Escritura escritura;
    private ObjectOutputStream oos;
    private HashMap<String, ObjectOutputStream> conexiones;
    private Usuario user;
    
    public HiloLectura(Socket socket, HashMap<String, ObjectOutputStream> conexiones, ObjectOutputStream oos) {
        System.out.println("Creando Hilo lectura");
        this.socket = socket;
        escritura = new Escritura();
        this.conexiones = conexiones;
        this.oos = oos;
        System.out.println("yasta creado");
    }
    @Override
    public void run() {
        try {
            Socket lectura = socket;
            System.out.println("Conexion especifica con: " + lectura.getInetAddress() + ":" + lectura.getPort() + " hecha desde el puerto: " + lectura.getLocalPort());
            leerSocket(lectura);                                          
        } catch (IOException ex) {
            System.out.println("Error conectando con cliente: " + ex.getMessage());
        }
    }
    public void leerSocket(Socket lectura) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(lectura.getInputStream());
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
                            grupo=new Grupo();
                            grupo.setIdGrupo(Integer.parseInt(mensaje.getMensaje()));
                            mensaje=new Mensaje();
                            mensaje.setOperacion("SOLICITAR_DETALLES_GRUPO");
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
                            destinatario = new Usuario(mensaje.getDestinatario(), "");
                            String ip = "";
                            try {
                                ip = escritura.getIp(destinatario);
                            } catch (SQLException ex) {
                                System.out.println("Error obteniendo ip");
                            }
                            final ObjectOutputStream oosDestinatario = conexiones.get(ip);
                            if (oosDestinatario == null) {
                                mensaje.setEstado(false);
                            }
                            else{
                                System.out.println("Mensaje de " + mensaje.getRemitente() + " para " + mensaje.getDestinatario() + " : " + mensaje.getMensaje());
                                mensaje.setOperacion("MENSAJE_NUEVO");
                                synchronized (oosDestinatario) {
                                    System.out.println("Escribiendo a destinatario");
                                    oosDestinatario.writeObject(mensaje);
                                    System.out.println("Mensaje enviado");
                                }
                                //funcion de guardar mensaje en archivo
                                mensaje.setOperacion("MENSAJE");
                                mensaje.setEstado(true);
                                mensaje.setMensaje("[" + Time.from(Instant.now()).toString() + "]" + mensaje.getMensaje());
                                destinatario = new Usuario(mensaje.getDestinatario(), "");
                                remitente = new Usuario(mensaje.getRemitente(), "");
                                //Log.getInstancia().addMensaje(destinatario, remitente, mensaje.getMensaje());
                                System.out.println("Guardado en log");
                                
                            }
                            break;
                        case "MENSAJE_NUEVO":
                            oos.writeObject(mensaje);
                            break;
                        case "MENSAJE_A_GRUPO":
                            if (mensaje.getNombre() == null){
                                mensaje.setEstado(false);
                                break;
                            }
                            grupo = new Grupo(0, mensaje.getNombre());
                            List<Usuario> integrantes;
                            mensaje.setEstado(true);
                            try{
                                integrantes = escritura.detallesGrupo(grupo);
                                Iterator<Usuario> itr = integrantes.iterator();
                                while(itr.hasNext()) {
                                    Usuario us = itr.next();
                                    ObjectOutputStream oosDestino = conexiones.get(us.getNombre());
                                    synchronized (oosDestino){
                                        oosDestino.writeObject(mensaje);
                                    }
                                }
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
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
                            mensaje.setListaMensajes(Log.getInstancia().getMensajesGrupo(grupo));
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
}
