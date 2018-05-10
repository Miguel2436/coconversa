/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
/**
 *
 * @author Usuario
 */
public class Log {
    private static Log log;
    private static Gson gson;
    
    
    public Log() {
        gson = new Gson();
    }
    public static synchronized Log getInstancia() {
        if (log == null) log = new Log();
        return log;
    }
    public synchronized void crearLogConversacion(Usuario destinatario, Usuario remitente, String mensaje) throws FileNotFoundException, IOException {
        File logUsuario = new File(destinatario.getNombre() + ".txt");  //Se crea el archivo con el nombre del usuario
        FileOutputStream fos = new FileOutputStream(logUsuario);      
        List<LogConversacion> logConversaciones = new ArrayList<>();
        
        LogConversacion logConversacion = new LogConversacion(remitente);
        logConversacion.addMensaje(mensaje);
        
        logConversaciones.add(logConversacion);
        
        String cadenaJson = gson.toJson(logConversaciones, List.class);
        fos.write(cadenaJson.getBytes("utf-8"));
    }
    public synchronized  List<LogConversacion> getLogConversacion(Usuario usuario) {
        File archivo = new File(usuario.getNombre() + ".txt");  //Se crea el archivo con el nombre del usuario
        if (!archivo.exists()) return null;                     //si ya existe se retorna null
        List<LogConversacion> LogConversaciones;
        String cadenaJson = "";
        try {
            FileInputStream fis = new FileInputStream(archivo);             
            byte[] stringBytes = new byte[100000000];            
            if (fis.read(stringBytes) >= 0) {                   //Se lee el archivo, si se lee algo, se convierte a cadena json
                cadenaJson = new String(stringBytes, "utf-8");
            }
            else return null;                                   //Si no, se retorna null
            LogConversaciones = gson.fromJson(cadenaJson, List.class);  //Se convierte a lista la cadena json del archivo,
              
        } catch (FileNotFoundException ex) {
            return null;                                        //En  caso de cualquier error
        } catch (IOException ex) {                              //Se retorna nulo
            return null;
        } catch (JsonSyntaxException ex) {
            return null;
        }
        return LogConversaciones;
    }
    public synchronized void crearLogGrupo(Grupo grupo, List<Usuario> integrantes) throws FileNotFoundException, IOException {
        File archivo = new File(grupo.getNombre() + ".txt");    //Se crea el archivo con el nombre del usuario
        if (archivo.createNewFile()) {                          //Si el archivo no existe se procede
            FileOutputStream fos = new FileOutputStream(archivo);   
            LogGrupo logGrupo = new LogGrupo(grupo, integrantes);   //Se crea  el objeto log con la informacion principal

            String cadenaJson = gson.toJson(logGrupo, LogGrupo.class);  //Se guarda el objeto en json
            fos.write(cadenaJson.getBytes("utf-8"));                    //Se le escribe al archivo vacion
        } else throw new IOException("");                       //Si el archivo ya existe se lanza excepcion para hacerselo saber al invocador
        
    }
    public synchronized LogGrupo getLogGrupo(Grupo grupo) {
        File archivo = new File(grupo.getNombre() + ".txt");    //Se crea el archivo con el nombre de grupo
        if (!archivo.exists()) return null;                     //Si existe se retorna un null
        LogGrupo logGrupo;
        String cadenaJson = "";
        try {
            FileInputStream fis = new FileInputStream(archivo);             
            byte[] stringBytes = new byte[100000000];            
            if (fis.read(stringBytes) >= 0) {                   //Si se lee algo del archivo se convierte a json
                cadenaJson = new String(stringBytes, "utf-8");
            }
            logGrupo = gson.fromJson(cadenaJson, LogGrupo.class);  //Se convierte al objeto log de grupo la cadena json del archivo,
              
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {                              //Cualquier error retorna null
            return null;
        } catch (JsonSyntaxException ex) {
            return null;
        }
        return logGrupo;
    }
    public synchronized void addMensaje(Usuario destinatario, Usuario remitente, String mensaje) throws IOException {
        List<LogConversacion> logConversaciones = getLogConversacion(destinatario);             //Se obtiene todo el log del usuario
        if (logConversaciones == null) crearLogConversacion(destinatario, remitente, mensaje);  //Si se obtiene null se crea y agrega el primer mensaje
        else {
            Iterator<LogConversacion> iteradorLog = logConversaciones.iterator();
            LogConversacion logConversacion = null;
            while (iteradorLog.hasNext() && logConversacion == null) {                          //Se itera por todos los remitentes y 
                LogConversacion logConTemp = iteradorLog.next();                                //si se encuentra el requerido
                if (remitente.getNombre().equals(logConTemp.getRemitente().getNombre())) {      //se le agrega el mensaje
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
            archivo = new File(destinatario.getNombre() + ".txt");
            FileOutputStream fos = new FileOutputStream(archivo);
            String cadenaJson = gson.toJson(logConversaciones, List.class);                     //y se sobreescribe por el mismo contenido
            fos.write(cadenaJson.getBytes("utf-8"));                                            //con los cambios ya hechos
        }
    }
    public synchronized List<String> getMensajes(Usuario destinatario, Usuario remitente){
        List<String> mensajes = new ArrayList<>();
        List<LogConversacion> logConversaciones = getLogConversacion(destinatario);
        if (logConversaciones == null) return null;
        Iterator<LogConversacion> iteratorLog = logConversaciones.iterator();
        while (iteratorLog.hasNext()) {            
            LogConversacion logConversacion = iteratorLog.next();
            if (remitente.getNombre().equals(logConversacion.getRemitente().getNombre())){
                mensajes = logConversacion.getMensajes();
                break;
            }
        }
        if (mensajes.size() > 5) return mensajes.subList(mensajes.size() - 5, mensajes.size());
        return mensajes;        
    }
    
    public synchronized void addMensajeGrupo(Grupo destinatario, Usuario remitente, String mensaje) throws IOException {
        LogGrupo logGrupo = getLogGrupo(destinatario);
        logGrupo.addMensaje(remitente, mensaje);
        File archivo = new File(destinatario.getNombre() + ".txt");
        archivo.delete();
        archivo = new File(destinatario.getNombre() + ".txt");
        FileOutputStream fos = new FileOutputStream(archivo);
        String cadenaJson = gson.toJson(logGrupo, LogGrupo.class);
        fos.write(cadenaJson.getBytes("utf-8"));    
    }
    public synchronized List<String> getMensajesGrupo(Grupo grupo){
        List<String> mensajes = new ArrayList<>();
        LogGrupo logGrupo = getLogGrupo(grupo);
        if (logGrupo == null) return null;
        mensajes = logGrupo.getMensajes();
        return mensajes;        
    }
    public synchronized void actualizarIntegrantesGrupo(Grupo grupo, List<Usuario> integrantes) throws IOException {
        LogGrupo logGrupo = getLogGrupo(grupo);
        logGrupo.setUsuarios(integrantes);
        File archivo = new File(grupo.getNombre() + ".txt");
        archivo.delete();
        archivo = new File(grupo.getNombre() + ".txt");
        FileOutputStream fos = new FileOutputStream(archivo);
        String cadenaJson = gson.toJson(logGrupo, LogGrupo.class);
        fos.write(cadenaJson.getBytes("utf-8"));    
    }
   
}
