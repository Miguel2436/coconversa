/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Log {
    private Log log;
    File miArchivo;
    FileOutputStream fos;
    FileInputStream fis;
    Gson gson = new Gson();
    
    
    public Log() throws FileNotFoundException {
        miArchivo = new File("Log.txt");
        fos = new FileOutputStream(miArchivo);
        fis = new FileInputStream(miArchivo);
        
    }
    public Log getInstancia() {
        if (log == null) try {
            log = new Log();
        } catch (FileNotFoundException ex) {
            
        }
        return log;
    }
    
   
}
