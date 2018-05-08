/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public List<Usuario> SolicitarAmigos(Usuario u) throws SQLException
    {
        List<Usuario> lista = new ArrayList<>();
      
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
        return lista;
    }
    public List<Grupo> SolicitarGrupos (Usuario u) throws SQLException
    {
        List<Grupo> lista = new ArrayList<>();
       
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
        return lista;
    }
    public void AceptarAmigo (Usuario x, Usuario y) throws SQLException
    {
        sql= conexion.prepareStatement("UPDATE amistad SET Estado = true WHERE"
                + "amistad.Solicitante = '" + x.getNombre() +
                "' AND amistad.Solicitado = '" + y.getNombre() + "'");     
    }
    public List<Usuario> SolicitarAmigosConectados (Usuario u) throws SQLException
    {
        List<Usuario> lista = new ArrayList<>();
       
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
                Usuario x = new Usuario (rs.getString("Nombre"), "");
                lista.add(x);
            } while (rs.next());  
        return lista;
    }
    
    public List<Usuario> SolicitarAmigosDesconectados (Usuario u) throws SQLException
    {
        List<Usuario> lista = new ArrayList<>();
       
        sql = conexion.prepareStatement
           ("SELECT usuario.Nombre, usuario.Password FROM usuario, conexion, amistad WHERE (" +
                "usuario.Nombre = (" +
                "SELECT amistad.Solicitado WHERE amistad.Solicitante = '" + u.getNombre() + 
                "' AND amistad.Solicitado = (" +
                "SELECT amistad.Solicitado WHERE amistad.Solicitado = conexion.Usuario "
                + "AND conexion.Estado = false)" +
                "AND amistad.Estado = true)" +
                "OR usuario.Nombre = (" +
                "SELECT amistad.Solicitante WHERE amistad.Solicitado = '" + u.getNombre() +
                "' AND amistad.Solicitante = (" +
                "SELECT amistad.Solicitante WHERE amistad.Solicitante = conexion.Usuario "
                + "AND conexion.Estado = false) " +
                "AND amistad.Estado = true))");
            ResultSet rs;
            rs = sql.executeQuery();
            rs.first();
            do 
            {
                Usuario x = new Usuario (rs.getString("Nombre"), "");
                lista.add(x);
            } while (rs.next());  
        return lista;
    }
    /**
     * Función para solicitar la entrda al sistema por 
     * medio de la validación de los datos de usuario(nombre y contraseña)
     * @param u
     * @return
     * @throws SQLException 
     */
    public boolean logIn(Usuario u, Conexion c) throws SQLException
    {
        sql=conexion.prepareStatement("SELECT password FROM usuario Where Nombre='"+u.getNombre()+"'");
        rs=sql.executeQuery();
        while(rs.next()==true)
        {
            if(u.getPassword().equals(rs.getString("Password")))
            {                
                sql.executeUpdate("UPDATE Conexion SET Estado = 1, IPaddress = '" + c.getIpAddress() + "' WHERE Usuario = '" + u.getNombre() + "';");
                return true;
            }else
            {
               return false;
            }      
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
            sql=conexion.prepareStatement("INSERT into grupo (nombre) VALUES('"+g.getNombre()+"')");
            sql.executeUpdate();
            
        //TOMAR ID GRUPO  
            sql=conexion.prepareStatement("SELECT idGrupo FROM grupo ORDER BY idGrupo DESC LIMIT 1");
            rs=sql.executeQuery();
            while(rs.next()==true)
            {
                idGrupo=rs.getInt("idGrupo");
                System.out.println(rs.getInt("idGrupo"));
            }
        //AGREGAR INTEGRANTES GRUPO CON BASE A LA LISTA RECIBIDA
        
        Iterator<Usuario> itr = integrantesGrupo.iterator();
        while(itr.hasNext())
        {
            intGrupo=itr.next();
            System.out.println(intGrupo.getNombre());
            
                sql = conexion.prepareStatement("INSERT into integrantesGrupo (Usuario, Grupo) VALUES('"+intGrupo.getNombre()+"',"+idGrupo+")");
                sql.executeUpdate();
        }    
    }
    /**
     * útil para eliminar un grupo de la BD.
     * @param g objeto de tipo grupo
     */
   public void eliminarGrupo(Grupo g) throws SQLException
    {
        //ELIMINAR DATOS DE LA TABLA GRUPO
            sql = conexion.prepareStatement("DELETE FROM grupo WHERE idGrupo=('"+g.getIdGrupo()+"')");
            sql.executeUpdate();
        //ELIMINAR DATOS DE TABLA INTEGRANTESGRUPO
            sql = conexion.prepareStatement("DELETE FROM integrantesGrupo WHERE Grupo=('"+g.getIdGrupo()+"')");
            sql.executeUpdate();     
    }
   /**
    * Muestra los usuarios pertenecientes a determinado grupo
    * @param g objeto de tipo grupo
    * @return 
    */
   public List<Usuario> detallesGrupo(Grupo g) throws SQLException
   {       
       List<Usuario> l=new ArrayList<Usuario>();
       Usuario uu=new Usuario();
       sql = conexion.prepareStatement("SELECT Usuario FROM integrantesGrupo WHERE Grupo='"+g.getIdGrupo()+"'");
       rs=sql.executeQuery();
       while(rs.next()==true)
       {
            uu.setNombre(rs.getString("Usuario"));
            uu.setPassword("");
            l.add(uu);
            System.out.println(rs.getString("Usuario"));
        }
        return l;   
   }
   /**
    * Elimina los usuarios actuales pertenecientes al grupo y los sustituye por la  lista de usuarios recibida
    * @param l lista de objetos de tipo usuario
    * @param g objeto de tipoo grupo
    */
   public void modificarGrupo(List<Usuario> l, Grupo g) throws SQLException
   {
       Usuario intGrupo = new Usuario();
       sql = conexion.prepareStatement("DELETE FROM integrantesGrupo WHERE Grupo='"+g.getIdGrupo()+"'");
       sql.executeUpdate();
       Iterator<Usuario> itr = l.iterator();
        while(itr.hasNext())
        {
            intGrupo=itr.next();
            System.out.println(intGrupo.getNombre());
                sql = conexion.prepareStatement("INSERT into integrantesGrupo (Usuario, Grupo) VALUES('"+g.getIdGrupo()+"','"+intGrupo.getNombre()+"'");
                sql.executeUpdate();
        }    
   }
   public boolean ExisteUsuario(Usuario u) throws SQLException {
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
        return false;
    }
    public void AgregarAmigo(Usuario u1, Usuario u2) throws SQLException {
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
    }
    
    public void AceptarAmigo(Amistad a) throws SQLException {
        sql = conexion.prepareStatement("UPDATE amistad SET amistad.Estado= " + 1 + ", "
            + "WHERE amistad.Solicitante = '" + a.getSolicitante() + "' AND amistad.Solicitado"
            + "= '" + a.getSolicitado() +  "'");
        sql.executeUpdate(); 
    }
    
    public void EliminarAmigo(Usuario u1, Usuario u2) throws SQLException {
        sql = conexion.prepareStatement("DELETE FROM amistad"
            + "WHERE amistad.Solicitante = '" + u1.getNombre() + "' AND amistad.Solicitado"
            + "= '" + u2.getNombre() +  "'");
        sql.executeUpdate();           
    }
    
    public void cerrarSesion(Usuario usuario) throws SQLException {
        sql.executeUpdate("UPDATE conexion SET Estado = 0 WHERE Usuario = '" + usuario.getNombre() + "';");
    }
    
    public List<Usuario> notificacionesAmistad (Usuario u) throws SQLException
    {
        List<Usuario> lista = new ArrayList();
        
        sql = conexion.prepareStatement ("SELECT amistad.Solicitante FROM amistad WHERE amistad.Estado = 0 AND "
                + "amistad.Solicitado = '" + u.getNombre() + "';");
        ResultSet rs;
        rs = sql.executeQuery();
        rs.first();
         do 
            {
                Usuario x = new Usuario (rs.getString("Solicitante"), "");
                lista.add(x);
            } while (rs.next()); 
        return lista;
    }
    
}

    



 

