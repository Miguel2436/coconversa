/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import datos.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Escritura {
    
    private Connection conexion;
    PreparedStatement sql;
    ResultSet rs;

    public Escritura() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/coconversabd","root","");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Escritura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Escritura.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    public void RegistroUsuario (Usuario u, Conexion c) throws SQLException 
    {
        sql = conexion.prepareStatement("SELECT COUNT(Nombre) AS 'Existe' from usuario "
                + "WHERE Nombre = '"+u.getNombre()+"'" );
        ResultSet rs;
        rs = sql.executeQuery();
        rs.first();
        if (rs.getInt("Existe")==0)
        {
            System.out.println("Entre");
            sql = conexion.prepareStatement("INSERT INTO usuario VALUES (?,?)");
            sql.setString(1, u.getNombre());
            sql.setString(2, u.getPassword());
            sql.executeUpdate();
            sql = conexion.prepareStatement("INSERT INTO conexion VALUES (null,?,?,?)");
            sql.setString(1, c.getIpAddress() );
            sql.setInt(2, c.getEstado());
            if (u.getNombre().equals(c.getUsuario())) sql.setString(3, c.getUsuario());
            sql.executeUpdate();
        }
        
    }
    public List<Usuario> SolicitarAmigos(Usuario u)
    {
        List<Usuario> lista = new ArrayList<>();
        try {
            sql = conexion.prepareStatement
                ("SELECT DISTINCT usuario.Nombre, usuario.Password FROM usuario, conexion, amistad WHERE (" +
                        "usuario.Nombre = (" +
                        "SELECT amistad.Solicitado WHERE amistad.Solicitante = '" + u.getNombre() +
                        "' AND amistad.Estado = true)" +
                        "OR usuario.Nombre = (" +
                            "SELECT amistad.Solicitante WHERE amistad.Solicitado = '" + u.getNombre() +
                                "' AND amistad.Estado = true))");
            ResultSet rs;
            rs = sql.executeQuery();
            rs.first();
            do 
            {
                Usuario x = new Usuario (rs.getString("Nombre"), rs.getString("Password"));
                lista.add(x);
            } while (rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(Escritura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    public List<Grupo> SolicitarGrupos (Usuario u)
    {
        List<Grupo> lista = new ArrayList<>();
        try {
            sql = conexion.prepareStatement("SELECT grupo.IdGrupo, grupo.Nombre "
                    + "FROM grupo, integrantesgrupo WHERE grupo.IdGrupo = integrantesgrupo.Grupo" +
                    "AND integrantesgrupo.Usuario = '" + u.getNombre() +"'");
            ResultSet rs;
            rs = sql.executeQuery();
            rs.first();
            do 
            {
                Grupo x = new Grupo (rs.getInt("IdGrupo"), rs.getString("Nombre"));
                lista.add(x);
            } while (rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(Escritura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    public void AceptarAmigo (Usuario x, Usuario y)
    {
        try {
            sql= conexion.prepareStatement("UPDATE amistad SET Estado = true WHERE"
                    + "amistad.Solicitante = '" + x.getNombre() +
                    "' AND amistad.Solicitado = '" + y.getNombre() + "'");
        } catch (SQLException ex) {
            Logger.getLogger(Escritura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<Usuario> SolicitarAmigosConectados (Usuario u)
    {
        List<Usuario> lista = new ArrayList<>();
        try {
            sql = conexion.prepareStatement
                ("SELECT usuario.Nombre, usuario.Password FROM usuario, conexion, amistad WHERE (" +
                        "usuario.Nombre = (" +
                        "SELECT amistad.Solicitado WHERE amistad.Solicitante = '" + u.getNombre() + 
                        "' AND amistad.Solicitado = (" +
                            "SELECT amistad.Solicitado WHERE amistad.Solicitado = conexion.Usuario "
                                + "AND conexion.Estado = true)" +
                        "AND amistad.Estado = true)" +
                        "OR usuario.Nombre = (" +
                            "SELECT amistad.Solicitante WHERE amistad.Solicitado = '" + u.getNombre() +
                        "' AND amistad.Solicitante = (" +
                                "SELECT amistad.Solicitante WHERE amistad.Solicitante = conexion.Usuario "
                                + "AND conexion.Estado = true) " +
                                "AND amistad.Estado = true))");
            ResultSet rs;
            rs = sql.executeQuery();
            rs.first();
            do 
            {
                Usuario x = new Usuario (rs.getString("Nombre"), rs.getString("Password"));
                lista.add(x);
            } while (rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(Escritura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    public boolean logIn(Usuario u) throws SQLException
    {
        try {
            sql=conexion.prepareStatement("SELECT password FROM usuario Where Nombre='"+u.getNombre()+"'");
            rs=sql.executeQuery();
            while(rs.next()==true)
            {
                if(u.getPassword().equals(rs.getString("Password")))
                {
                    return true;
                }else
                {
                    return false;
                }      
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        return false;
        
    }
    /**
     * útil para crear un grupo 
     * @param g objeto de tipo grupo
     * @param integrantesGrupo lista de objetos tipo usuario que pertenecerán al grupo a crear
     * @throws SQLException 
     */
    public void crearGrupo(Grupo g, List<Usuario> integrantesGrupo )throws SQLException
    {
        int idGrupo=0;
        
         Usuario intGrupo=new Usuario();
        
        
        //INSERT GRUPO
        try {
            sql=conexion.prepareStatement("INSERT into grupo (nombre) VALUES('"+g.getNombre()+"')");
            sql.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        //TOMAR ID GRUPO  
        try {
            sql=conexion.prepareStatement("SELECT idGrupo FROM grupo ORDER BY idGrupo DESC LIMIT 1");
            rs=sql.executeQuery();
            while(rs.next()==true)
            {
                idGrupo=rs.getInt("idGrupo");
                System.out.println(rs.getInt("idGrupo"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        //AGREGAR INTEGRANTES GRUPO CON BASE A LA LISTA RECIBIDA
        
        Iterator<Usuario> itr = integrantesGrupo.iterator();
        while(itr.hasNext())
        {
            intGrupo=itr.next();
            System.out.println(intGrupo.getNombre());
            
            try {
                sql = conexion.prepareStatement("INSERT into integrantesGrupo (Usuario, Grupo) VALUES('"+intGrupo.getNombre()+"',"+idGrupo+")");
                sql.executeUpdate();
            } catch (SQLException ex) {
                
                System.out.println(ex.getMessage());
            }
            
        }    
    }
    /**
     * útil para eliminar un grupo de la BD.
     * @param g objeto de tipo grupo
     */
   public void eliminarGrupo(Grupo g)
    {
        //ELIMINAR DATOS DE LA TABLA GRUPO
       
        try {
            sql = conexion.prepareStatement("DELETE FROM grupo WHERE idGrupo=('"+g.getIdGrupo()+"')");
            sql.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        //ELIMINAR DATOS DE TABLA INTEGRANTESGRUPO
        try {
            sql = conexion.prepareStatement("DELETE FROM integrantesGrupo WHERE Grupo=('"+g.getIdGrupo()+"')");
            sql.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }       
    }
   /**
    * Muestra los usuarios pertenecientes a determinado grupo
    * @param g objeto de tipo grupo
    * @return 
    */
   public List<Usuario> detallesGrupo(Grupo g)
   {       
       List<Usuario> l=new ArrayList<Usuario>();
       Usuario uu=new Usuario();
       try {
            sql = conexion.prepareStatement("SELECT Usuario FROM integrantesGrupo WHERE Grupo='"+g.getIdGrupo()+"'");
            rs=sql.executeQuery();
            while(rs.next()==true)
            {
               uu.setNombre(rs.getString("Usuario"));
               uu.setPassword("");
                l.add(uu);
                System.out.println(rs.getString("Usuario"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;   
   }
   /**
    * Elimina los usuarios actuales pertenecientes al grupo y los sustituye por la  lista de usuarios recibida
    * @param l lista de objetos de tipo usuario
    * @param g objeto de tipoo grupo
    */
   public void modificarGrupo(List<Usuario> l, Grupo g)
   {
       Usuario intGrupo = new Usuario();
       eliminarGrupo(g);
       
       Iterator<Usuario> itr = l.iterator();
        while(itr.hasNext())
        {
            intGrupo=itr.next();
            System.out.println(intGrupo.getNombre());
            
             try {
                sql = conexion.prepareStatement("INSERT into integrantesGrupo (Usuario, Grupo) VALUES('"+g.getIdGrupo()+"','"+intGrupo.getNombre()+"'");
                sql.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } 
            
        }    
   }
   public boolean ExisteUsuario(Usuario u) {
        try{
            sql= conexion.prepareStatement("SELECT usuario.Nombre FROM Usuario");
            rs = sql.executeQuery();
            rs.first();
            
            while(rs.next()){
                if(rs.getObject("Nombre") == u.getNombre()){
                    return true;
                }else{
                    return false;
                }
            }       
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return false;
    }
    
    public void AgregarAmigo(Usuario u1, Usuario u2) {
        try{
            sql = conexion.prepareStatement("SELECT COUNT(amistad.IdAmistad) AS 'Verifica' "
                    + "FROM amistad WHERE amistad.Solicitante = '" + u1.getNombre() + "' AND amistad.Solicitado"
                    + "= '" + u2.getNombre() +  "'");
            rs = sql.executeQuery();
            rs.first();
            
            if(rs.getInt("Verifica") == 0){
                PreparedStatement sqlInsert = conexion.prepareStatement("INSERT INTO amistad(IdAmistad, Solicitante, "
                    + "Solicitado, Estado) values(null, ?, ?, ?)");
                sqlInsert.setString(1, u1.getNombre());
                sqlInsert.setString(2, u2.getNombre());
                sqlInsert.setInt(3, 0);
                sqlInsert.executeUpdate();
            }else{
                
            }          
             
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
    }
    
    public void AceptarAmigo(Amistad a) {
        try{
            sql = conexion.prepareStatement("UPDATE amistad SET amistad.Estado= " + 1 + ", "
                + "WHERE amistad.Solicitante = '" + a.getSolicitante() + "' AND amistad.Solicitado"
                + "= '" + a.getSolicitado() +  "'");
            sql.executeUpdate(); 
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
    }
    
    public void EliminarAmigo(Usuario u1, Usuario u2) {
        try{
            sql = conexion.prepareStatement("DELETE FROM amistad"
                + "WHERE amistad.Solicitante = '" + u1.getNombre() + "' AND amistad.Solicitado"
                + "= '" + u2.getNombre() +  "'");
            sql.executeUpdate();           
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}

    



 

