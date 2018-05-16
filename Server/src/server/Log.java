/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import datos.Grupo;
import datos.LogConversacion;
import datos.LogGrupo;
import datos.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.*;
/**
 *
 * @author Usuario
 */
public class Log {
    private static Log log;    
    
    public Log() {
    }
    /**
     * 
     * @return devuelve un objeto del tipo log para hacer que se instancie solo
     */
    public static synchronized Log getInstancia() {
        if (log == null) log = new Log();
        return log;
    }
    /**
     * Se utiliza para verificar que exista la carpeta del remitente y si no crearla
     * @param destinatario Es el usuario que va a recibir el mensaje y se utiliza para crear su carpeta
     * @param remitente Es el usuario que esta enviando el mensaje 
     * @param mensaje Es el mensaje que se envió
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public synchronized void crearLogConversacion(Usuario destinatario, Usuario remitente, String mensaje) throws FileNotFoundException, IOException {
        System.out.println("Creando log de " + destinatario.getNombre());
        File logUsuario = new File(destinatario.getNombre() + ".txt");  //Se crea el archivo con el nombre del usuario
        FileOutputStream fos = new FileOutputStream(logUsuario);      
        List<LogConversacion> logConversaciones = new ArrayList<>();
        
        LogConversacion logConversacion = new LogConversacion(remitente);
        logConversacion.addMensaje(mensaje);
        
        logConversaciones.add(logConversacion);
        JSONArray jO = new JSONArray(logConversaciones);
        String cadenaJson = jO.toString();
        fos.write(cadenaJson.getBytes("utf-8"));
        System.out.println("Archivo creado en: " + logUsuario.getPath());
    }
    /**
     * Sirve para devolver las conversaciones guardadas en el log de un usuario
     * @param usuario Es el usuario del que se desea obtener el log
     * @return devuelve una lista del tipo logConversacion, en donde estan guardados los mensajes y si no encuentra ningun mensaje retorna null
     */
    public synchronized  List<LogConversacion> getLogConversacion(Usuario usuario) {
        File archivo = new File(usuario.getNombre() + ".txt");  //Se crea el archivo con el nombre del usuario
        List<LogConversacion> LogConversaciones = null;
        if (archivo.exists()) {
            LogConversaciones = new ArrayList<>();
            String cadenaJson = "";
            try {
                FileInputStream fis = new FileInputStream(archivo);
                byte[] stringBytes = new byte[100000000];
                fis.read(stringBytes);
                cadenaJson = new String(stringBytes, "utf-8");
                JSONArray jA = new JSONArray(cadenaJson);
                for (int i = 0; i < jA.length(); i++) {
                    LogConversacion temp = new LogConversacion ();
                    List<String> mensajesParaAgregar = new ArrayList<>();
                    JSONObject jO = jA.getJSONObject(i);
                    temp.setRemitente(new Usuario(jO.getString("remitente"), ""));
                    JSONArray mensajesDeUsuario = jO.getJSONArray("mensajes");
                    for (int j = 0; j < mensajesDeUsuario.length(); j++) {
                        mensajesParaAgregar.add(mensajesDeUsuario.getString(j));
                    }
                    temp.setMensajes(mensajesParaAgregar);
                    LogConversaciones.add(temp);                       //Se convierte a lista la cadena json del archivo,
                }                

            } catch (FileNotFoundException ex) {
                System.out.println("Error encontrando archivo: " + ex.toString());
                return null;                                        //En  caso de cualquier error
            } catch (IOException ex) { 
                System.out.println("Error leyendo archivo: " + ex.toString());
            }
        } else {
            return null;
        }         
        
        return LogConversaciones;
    }
    /**
     * Se utiliza para crear el archivo de un grupo en donde se guardara el log de las conversaciones del mismo
     * @param grupo Se recibe el nombre del grupo del que se hara el archivo, para darle el nombre del grupo
     * @param integrantes Es una lista con los usuarios pertenecientes al grupo
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public synchronized void crearLogGrupo(Grupo grupo, List<Usuario> integrantes) throws FileNotFoundException, IOException {
        System.out.println("Creando archivo de grupo " + grupo.getNombre());
        File archivo = new File(grupo.getNombre() + ".txt");    //Se crea el archivo con el nombre del usuario
        if (archivo.createNewFile()) {                          //Si el archivo no existe se procede
            FileOutputStream fos = new FileOutputStream(archivo);
            List<String> listaIntegrantes = new ArrayList<>();
            Iterator<Usuario> itrUsuario = integrantes.iterator();
            while (itrUsuario.hasNext()){
                listaIntegrantes.add(itrUsuario.next().getNombre());
            }
            LogGrupo logGrupo = new LogGrupo(grupo, listaIntegrantes);   //Se crea  el objeto log con la informacion principal
            JSONObject jO = new JSONObject(logGrupo);
            String cadenaJson = jO.toString();  //Se guarda el objeto en json
            fos.write(cadenaJson.getBytes("utf-8"));                    //Se le escribe al archivo vacion
        } else throw new IOException("");                       //Si el archivo ya existe se lanza excepcion para hacerselo saber al invocador
        System.out.println("Archivo creado en: " + archivo.getPath());
    }
    /**
     * Sirve para devolver los mensajes que se tengan en el archivo de log de determinado grupo
     * @param grupo Es el grupo del que se desea obtener el log
     * @return 
     */
    public synchronized LogGrupo getLogGrupo(Grupo grupo) {
        File archivo = new File(grupo.getNombre() + ".txt");    //Se crea el archivo con el nombre de grupo
        LogGrupo logGrupo = new LogGrupo();
        String cadenaJson = "";
        if (archivo.exists()) {
            try {
                FileInputStream fis = new FileInputStream(archivo);             
                byte[] stringBytes = new byte[100000000];      
                fis.read(stringBytes);
                List<String> usuariosParaAgregar = new ArrayList<>();
                List<String> mensajesParaAgregar = new ArrayList<>();
                cadenaJson = new String(stringBytes, "utf-8");
                JSONObject jO = new JSONObject(cadenaJson);                
                JSONArray mensajesLeidos = jO.getJSONArray("mensajes");
                JSONArray usuariosLeidos = jO.getJSONArray("usuarios");
                for (int i = 0; i < mensajesLeidos.length(); i++) {
                    mensajesParaAgregar.add(mensajesLeidos.getString(i));
                }
                for (int i = 0; i < usuariosLeidos.length(); i++) {
                    usuariosParaAgregar.add(usuariosLeidos.getString(i));
                }
                logGrupo.setGrupo(new Grupo(0, jO.getString("grupo")));
                logGrupo.setMensajes(mensajesParaAgregar);
                logGrupo.setUsuarios(usuariosParaAgregar);
            } catch (FileNotFoundException ex) {
                return null;
            } catch (IOException ex) {                              //Cualquier error retorna null
                return null;
            } 
        }                     //Si no existe se retorna un null
        else return null;
        
        return logGrupo;
    }
    /**
     * Se utiliza para añadir un mensaje al archivo del log, si este no esta creado manda a la funcion para crearlo
     * @param destinatario Es el usuario que va a recibir el mensaje
     * @param remitente Es el usuario que esta enviando el mensaje
     * @param mensaje   El texto que el usuario mandó
     * @throws IOException 
     */
    public synchronized void addMensaje(Usuario destinatario, Usuario remitente, String mensaje) throws IOException {
        List<LogConversacion> logConversaciones = getLogConversacion(destinatario);             //Se obtiene todo el log del usuario
        if (logConversaciones == null) crearLogConversacion(destinatario, remitente, mensaje);  //Si se obtiene null se crea y agrega el primer mensaje
        else {
            Iterator<LogConversacion> iteradorLog = logConversaciones.iterator();
            LogConversacion logConversacion = null;
            while (iteradorLog.hasNext() && logConversacion == null) {                          //Se itera por todos los remitentes y 
                LogConversacion logConTemp = iteradorLog.next();                                //si se encuentra el requerido
                if (remitente.getNombre().equals(logConTemp.getRemitente())) {      //se le agrega el mensaje
                    logConTemp.addMensaje(mensaje);
                    logConversacion = logConTemp;                    
                }
            }
            if (logConversacion == null){                                                       //Si no se encuentra el requerido
                logConversacion = new LogConversacion();                                        //se crea con el primer mensaje
                logConversacion.setRemitente(remitente);                                                    
                logConversacion.addMensaje(mensaje);
                logConversaciones.add(logConversacion);
            }
            File archivo = new File(destinatario.getNombre() + ".txt");                             
            archivo.delete();                                                                   //Se borra el archivo existente
            archivo.createNewFile();
            FileOutputStream fos = new FileOutputStream(archivo);
            JSONArray jA = new JSONArray(logConversaciones);
            String cadenaJson = jA.toString();                     //y se sobreescribe por el mismo contenido
            fos.write(cadenaJson.getBytes("utf-8"));               //con los cambios ya hechos
        }
    }
    /**
     * Sirve para devolver los mensajes de una conversacion que se tengan en el archivo log de un usuario.
     * @param destinatario Es el usuario del que se esta solicitando la conversación.
     * @param remitente Es el usuario que pide la conversacion.
     * @return Devuelve en una lista todos los mensajes que se tegan de esa conversacion en el archivo log
     */
    public synchronized List<String> getMensajes(Usuario destinatario, Usuario remitente){
        List<String> mensajes = new ArrayList<>();
        List<LogConversacion> logConversaciones = getLogConversacion(destinatario);
        if (logConversaciones == null) return null;
        Iterator<LogConversacion> iteratorLog = logConversaciones.iterator();
        while (iteratorLog.hasNext()) {            
            LogConversacion logConversacion = iteratorLog.next();
            if (remitente.getNombre().equals(logConversacion.getRemitente())){
                mensajes = logConversacion.getMensajes();
                break;
            }
        }
        if (mensajes.size() > 5) return mensajes.subList(mensajes.size() - 5, mensajes.size());
        return mensajes;        
    }
    /**
     * Se utiliza cuando se manda un nuevo mensaje a un grupo para guardarlo en el archivo de log
     * @param destinatario Es el grupo al que se envio el mensaje
     * @param remitente Es el usuario que envio el mensaje
     * @param mensaje Es el texto del mensaje que se envio
     */
    public synchronized void addMensajeGrupo(Grupo destinatario, Usuario remitente, String mensaje){
        LogGrupo logGrupo = getLogGrupo(destinatario);
        logGrupo.addMensaje(remitente, mensaje);
        File archivo = new File(destinatario.getNombre() + ".txt");
        archivo.delete();
        try {
        archivo.createNewFile();
        FileOutputStream fos = new FileOutputStream(archivo);
        JSONObject jO = new JSONObject(logGrupo);
        String cadenaJson = jO.toString();
        fos.write(cadenaJson.getBytes("utf-8"));   
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Se utiliza para obtener todos los mensajes de un grupo que se encuentren guardados en el log.
     * @param grupo El grupo del que se desean obtener los mensajes.
     * @return Devuelve una lista con todos los mensajes que se encuentren en el log
     */
    public synchronized List<String> getMensajesGrupo(Grupo grupo){
        List<String> mensajes = new ArrayList<>();
        LogGrupo logGrupo = getLogGrupo(grupo);
        if (logGrupo == null) return null;
        mensajes = logGrupo.getMensajes();
        return mensajes;        
    }
    /**
     * Se utiliza cuando los integrantes del grupo cambiaron, sirve para actualizar los integrantes en el archivo log
     * @param grupo El grupo al cual se le realizaron cambios
     * @param integrantes   La lista de los usuarios actuales del grupo
     * @throws IOException 
     */
    public synchronized void actualizarIntegrantesGrupo(Grupo grupo, List<Usuario> integrantes) throws IOException {
        LogGrupo logGrupo = getLogGrupo(grupo);
        List<String> usuariosParaAgregar = new ArrayList<>();
        Iterator<Usuario> itrUsuario = integrantes.iterator();
        while (itrUsuario.hasNext()) {
            usuariosParaAgregar.add(itrUsuario.next().getNombre());
        }
        logGrupo.setUsuarios(usuariosParaAgregar);
        File archivo = new File(grupo.getNombre() + ".txt");
        archivo.delete();
        archivo.createNewFile();
        FileOutputStream fos = new FileOutputStream(archivo);
        JSONObject jO = new JSONObject(logGrupo);
        String cadenaJson = jO.toString();
        fos.write(cadenaJson.getBytes("utf-8"));    
    }
   
}
