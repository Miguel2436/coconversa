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
import java.util.HashMap;

/**
 *
 * @author Leonardo Martinez
 */
public class HiloLectura extends Thread{

    ServerSocket socket;
    Escritura escritura;
    ObjectOutputStream oos;
    String nombreUsuario;
    private HashMap<String, ObjectOutputStream> conexiones;
    public HiloLectura(ServerSocket socket, HashMap<String, ObjectOutputStream> conexiones, ObjectOutputStream oos) {
        this.socket = socket;
        escritura = new Escritura();
        this.conexiones = conexiones;
        this.oos = oos;
    }
    @Override
    public void run() {
        try {
            Socket lectura = socket.accept();
            System.out.println("Conexion especifica con: " + lectura.getInetAddress() + ":" + lectura.getPort() + " hecha desde el puerto: " + lectura.getLocalPort());
            leerSocket(lectura);                                          
        } catch (IOException ex) {
            System.out.println("Error conectando con cliente: " + ex.getMessage());
        }
    }
    public void leerSocket(Socket lectura) throws IOException {
        synchronized (oos) {
            Mensaje mensaje;
            while (!lectura.isClosed()) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(lectura.getInputStream());
                    mensaje = (Mensaje) ois.readObject();
                    Usuario usuario;
                    Conexion conexion;
                    Usuario usuario2;
                    Grupo grupo;
                    switch (mensaje.getOperacion()) {
                        case "LOGIN":
                            usuario = new Usuario(mensaje.getNombre(), mensaje.getMensaje());  
                            conexion = new Conexion(0, lectura.getInetAddress().toString(), 1, usuario.getNombre());
                            nombreUsuario = mensaje.getNombre();
                            mensaje.setOperacion("LOGIN");
                            try {
                                escritura.logIn(usuario, conexion);
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "SIGNUP":
                            usuario = new Usuario(mensaje.getNombre(), mensaje.getMensaje());
                            conexion = new Conexion(0, lectura.getInetAddress().toString(), 1, usuario.getNombre());
                            nombreUsuario = mensaje.getNombre();
                            try {
                                escritura.RegistroUsuario(usuario, conexion);
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "EXISTE_USUARIO":
                            usuario = new Usuario(mensaje.getNombre(), "");
                            try {
                                if (escritura.ExisteUsuario(usuario)) {                                    
                                    mensaje.setEstado(true);
                                }
                                else mensaje.setEstado(false);                                
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
                            try {
                                escritura.EliminarAmigo(usuario, usuario2);
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "CREAR_GRUPO":
                            grupo = new Grupo(0, mensaje.getNombre());
                            try {
                                escritura.crearGrupo(grupo, mensaje.getListaUsuarios());
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "ELIMINAR_GRUPO":
                            grupo = new Grupo(0, mensaje.getNombre());
                            try {
                                escritura.eliminarGrupo(grupo);
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "MODIFICAR_GRUPO":
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
                            usuario = new Usuario(mensaje.getNombre(), "");
                            try {
                                mensaje.setListaUsuarios(escritura.SolicitarAmigos(usuario));
                                mensaje.setEstado(true);                                
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "SOLICITAR_AMIGOS_CONECTADOS":
                            usuario = new Usuario(mensaje.getNombre(), "");
                            try {
                                mensaje.setListaUsuarios(escritura.SolicitarAmigosConectados(usuario));                                
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "SOLICITAR_AMIGOS_DESCONECTADOS":
                            usuario = new Usuario(mensaje.getNombre(), "");
                            try {
                                mensaje.setListaUsuarios(escritura.SolicitarAmigosDesconectados(usuario));                                
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "SOLICITAR_GRUPOS":
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
                                escritura.AceptarAmigo(usuario2, usuario);
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "CERRAR_SESION":
                            usuario = new Usuario(mensaje.getNombre(), "");
                            try {
                                escritura.cerrarSesion(usuario);    //===============================> Crear funcion en esctritura que pase conexion.estado a falso
                                mensaje.setEstado(true);
                            } catch (SQLException ex) {
                                mensaje.setEstado(false);
                            }
                            break;
                        case "MENSAJE":
                            ObjectOutputStream oosDestinatario = conexiones.get(mensaje.getDestinatario());
                            mensaje.setOperacion("MENSAJE_NUEVO");
                            synchronized (oosDestinatario) {
                                oosDestinatario.writeObject(mensaje);
                            }
                            //funcion de guardar mensaje en archivo
                            mensaje.setOperacion("MENSAJE");
                            mensaje.setEstado(true);
                            oos.writeObject(mensaje);
                            break;
                        case "MENSAJE_NUEVO":
                            oos.writeObject(mensaje);
                            break;
                        case "MENSAJE_A_GRUPO":
                            break;
                        case "GET_MENSAJES":
                            break;
                        case "GET_MENSAJES_GRUPO":
                            break;
                        default:
                            break;
                    }
                    usuario = new Usuario(nombreUsuario, "");
                    try {     
                        escritura.notificacionesAmistad(usuario);
                    } 
                    catch(SQLException ex){
                        mensaje.setEstado(false);
                    }
                    oos.writeObject(mensaje);
                } catch (IOException ex) {
                    System.out.println("Error leyendo: " + ex.getMessage());
                    System.out.println("Termina conexion especifica con: " + lectura.getInetAddress() + ":" + lectura.getPort());
                    lectura.close();                
                } catch (ClassNotFoundException ex) {
                    System.out.println("Error al encontrar la clase:" + ex.getMessage());
                }
            }
        }
    }
}

            
        
       

