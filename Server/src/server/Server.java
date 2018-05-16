/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import java.io.IOException;
import java.util.HashMap;
/**
 *
 * @author Leonardo Martinez
 */
public class Server {
    public static HashMap<String, HiloLectura> conexiones = new HashMap<>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int SocketGeneral = 1000;
        Thread HiloLecturaGeneral;
        try {
            HiloLecturaGeneral = new HiloLecturaGeneral(1000, conexiones);
            HiloLecturaGeneral.start();
        } catch (IOException ex) {
            System.out.println("Error abriendo puerto general: " + ex.getMessage());
        }
        }
}
