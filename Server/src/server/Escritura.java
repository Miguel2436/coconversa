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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 *
 * @author Usuario
 */
public class Escritura {
    
    private Connection conexion;
    PreparedStatement sql;
    ResultSet rs;
/** 
 * Constructor de la clase que realiza la conexion a la BD
 */
    public Escritura() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/coconversabd","root","");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error encontrando clase: " + ex.toString());
        } catch (SQLException ex) {
            System.out.println("Error ingresando a BD: " + ex.toString());
        }
         
    }
    
    /**
     * Función para registrar un usuario. 
     * Valida que el nombre del usuario no se haya registrado anteriormente.
     * Almacena los datos ingresados por el usaurio en la base de datos.
     * Crea la conexión del usuario.
     * 
     * @param u objeto tipo usuario
     * @param c objeto tipo conexion
     * @throws SQLException 
     */
    public void RegistroUsuario (Usuario u, Conexion c) throws SQLException 
    {
        sql = conexion.prepareStatement("SELECT COUNT(Nombre) AS 'Existe' from usuario "
                + "WHERE Nombre = '"+u.getNombre()+"'" );
        ResultSet rs;
        rs = sql.executeQuery();
        rs.first();
        if (rs.getInt("Existe")==0)
        {
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
    
    /**
     * Función que devuelve la lista de amigos
     * Recibe al usuario conectado.
     * Verifica en la BD y devuelve los usuarios que en la tabla amistad tienen el campo "Estado" en 1.
     * Verifica que el valor devuelto corresponda solamente al usuario conectado.
     * 
     * @param u objeto tipo usuario
     * @return lista de usuarios amigos
     * @throws SQLException 
     */
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
            if (rs.first()) {
                do {
                    Usuario x = new Usuario (rs.getString("Nombre"), "");
                    lista.add(x);
                } while (rs.next()); 
            }
        return lista;
    }
    
    /**
     * Funcion que devuelve la lista de grupos en los que el usuario pertenece
     * 
     * @param u objeto tipo usuario
     * @return lista de los grupos en los que el usuario pertenece
     * @throws SQLException 
     */
    public List<Grupo> SolicitarGrupos (Usuario u) throws SQLException
    {
        List<Grupo> lista = new ArrayList<>();
        
            sql = conexion.prepareStatement("SELECT grupo.Nombre FROM grupo, integrantesgrupo WHERE grupo.IdGrupo = integrantesgrupo.Grupo AND integrantesgrupo.Usuario = ?");
            sql.setString(1, u.getNombre());
            ResultSet rs;
            rs = sql.executeQuery();
            if (rs.first()) {
                do {
                    Grupo x = new Grupo (0, rs.getString("Nombre"));
                    lista.add(x);
                } while (rs.next()); 
            }  
        return lista;
    }
    
    /**
     * Funcion que confirma el estado de la solicitud
     * Actualiza el campo "Estado" en 1 cuando el usuario solicitado acepta la petición de amistad del solicitante.
     * 
     * @param solicitado objeti tipo usuario (destinatario)
     * @param solicitante objeto tipo usuario (remitente)
     * @throws SQLException 
     */
    public void AceptarAmigo (Usuario solicitante, Usuario solicitado) throws SQLException
    {
        sql= conexion.prepareStatement("UPDATE amistad SET Estado = true WHERE amistad.Solicitante = ? AND amistad.Solicitado = ?");
        sql.setString(1, solicitante.getNombre());
        sql.setString(2, solicitado.getNombre());
        sql.executeUpdate();
    }
    
    /**
     * Función que devuelve los amigos conectados
     * Verifica que los campos "Estado" tanto de la tabla amistad como de la tabla conexion, se encuentren en 1.
     * Verifica que el valor devuelto corresponda solamente al usuario conectado. 
     * 
     * @param u objeto de tipo usuario
     * @return lista de amigos conectados
     * @throws SQLException 
     */
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
    
    /**
     * Función que devuelve los amigos desconectados
     * Verifica que el campo "Estado" de la tabla amistad este en 1. 
     * Verifica que el campo "Estado" de la tabla conexion, se encuentren en 0.
     * Verifica que el valor devuelto corresponda solamente al usuario conectado. 
     * 
     * @param u objeto de tipo usuario
     * @return lista de amigos desconectados
     * @throws SQLException 
     */
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
     * Función para solicitar la entrada al sistema por 
     * medio de la validación de los datos de usuario(nombre y contraseña)
     * Pide el nombre de usuario y contraseña, los compara con los registros en la base de datos, 
     * y si coinciden, cambia el estado de conexion
     * @param u objeto de tipo usuario
     * @return  si el nombre y la contraseña coinciden con los registros de una fila retorna verdadero
     * si no, retorna falso. 
     * @throws SQLException 
     */
    public boolean logIn(Usuario u, Conexion c) throws SQLException
    {// Debe regresar el nombre del usuario
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
     * Función para crear un grupo 
     * Crea un grupo asignándole un nombre.
     *Recibe una lista de los usuarios que pertenecen a ese grupo, y los inserta en la tabla "integrantesGrupo"
     * @param g objeto de tipo grupo
     * @param integrantesGrupo lista de objetos tipo usuario que pertenecerán al grupo a crear
     * @throws SQLException 
     */
    public void crearGrupo(Grupo g, List<Usuario> integrantesGrupo )throws SQLException
    {
        int idGrupo=0;
         Usuario intGrupo=new Usuario();
         sql = conexion.prepareStatement("Select Nombre from grupo where nombre = ?");
         sql.setString(1, g.getNombre());
         rs = sql.executeQuery();
         if (rs.first()) throw new SQLException("Existe grupo con mismo nombre");
         else {
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
    }
    
    /**
     * Función para eliminar un grupo de la BD.
     *Elimina de la tabla "grupo" el campo que tenga como id el del grupo que se quiera borrar.
     *De igual manera, se elimina de la tabla "integrantesGrupo" los campos de usuario que su "IdGrupo" sea igual
     *al del grupo borrado
     * @param g objeto de tipo grupo
     */
   public void eliminarGrupo(Grupo g) throws SQLException
    {
        //ELIMINAR DATOS DE LA TABLA GRUPO
            sql = conexion.prepareStatement("DELETE FROM grupo WHERE nombre=('"+g.getNombre()+"')");
            sql.executeUpdate();   
    }
   /**
    * Muestra los usuarios pertenecientes a determinado grupo
    *Hace una consulta a la tabla "integrantesGrupo" y va guardando los nombres de intengrantes en una lista, 
    *la cual será retornada.
    * @param g objeto de tipo grupo
    * @return lista con los integrantes de un grupo 
    */
   public List<Usuario> detallesGrupo(Grupo g) throws SQLException
   {       
       List<Usuario> l=new ArrayList<Usuario>();
       
       sql = conexion.prepareStatement("Select idGrupo from grupo where nombre = ?");
       sql.setString(1, g.getNombre());
       rs = sql.executeQuery();
       int idGrupo = -1;
       if (rs.first()){
           idGrupo = rs.getInt("IdGrupo");
       }
       sql = conexion.prepareStatement("SELECT Usuario FROM integrantesGrupo WHERE Grupo='"+ idGrupo +"'");
       rs=sql.executeQuery();
       while(rs.next()==true)
       {
           Usuario uu=new Usuario();
           uu.setNombre(rs.getString("Usuario"));
           uu.setPassword("");
           l.add(uu);
           System.out.println(uu.getNombre());
        }
        return l;   
   }
   /**
    * Elimina los usuarios actuales pertenecientes al grupo y los sustituye por la  lista de usuarios recibida
    *Elimina de la tabla "integrantesGrupo" a los integrantes con la id especificada, y en su lugar pone los nuevos campos
    *ingresados (Usuario, Grupo)
    * @param l lista de objetos de tipo usuario
    * @param g objeto de tipoo grupo
    */
   public void modificarGrupo(List<Usuario> l, Grupo g) throws SQLException
   {       
       sql = conexion.prepareStatement("Select idGrupo from grupo where nombre = ?");
       sql.setString(1, g.getNombre());
       rs = sql.executeQuery();
       int idGrupo = 0;
       if (rs.first()) {
           idGrupo = rs.getInt(1);
        }
       sql = conexion.prepareStatement("DELETE FROM integrantesGrupo WHERE Grupo="+idGrupo);
       sql.executeUpdate();
       Iterator<Usuario> itr = l.iterator();
        while(itr.hasNext())
        {
            Usuario intGrupo = new Usuario();
            intGrupo=itr.next();
            System.out.println(intGrupo.getNombre());
                sql = conexion.prepareStatement("INSERT into integrantesGrupo (Usuario, Grupo) VALUES(?,"+idGrupo+")");
                sql.setString(1, intGrupo.getNombre());
                sql.executeUpdate();
        }    
   }
   
   /**
    * Función que verifica si un usuario ya esta registrado
    *Hace una consulta a la tabla "usuario" y hace una comparación para ver si el "Nombre" recibido
    *coincide con alguno de la tabla. Si es así, retorna verdadero, si no, falso.
    * @param u objeto de tipo usuario
    * @return si el nombre ingresado coincide con el registrado, regresa verdadero, sino, 
    * regresa falso.
    * @throws SQLException 
    */
   public boolean ExisteUsuario(Usuario u) throws SQLException {
            sql= conexion.prepareStatement("SELECT usuario.Nombre FROM Usuario Where Nombre = ?");
            sql.setString(1, u.getNombre());
            rs = sql.executeQuery();
            if (rs.first()) return true;
            else return false;
    }
   
    /**
     * Función para solicitar 
     * Revisa que el campo (la relación de amistad) no exista, si no existe, ingresa los datos con "Estado" en 0,
     * como solicitud pendiente.
     * @param u1 objeto de tipo usuario (remitente)
     * @param u2 objeto de tipo usuario (destinatario)
     * @throws SQLException 
     */
    public void AgregarAmigo(Usuario solicitado, Usuario solicitante) throws SQLException {
        sql = conexion.prepareStatement("SELECT COUNT(amistad.IdAmistad) AS 'Verifica' "
                + "FROM amistad WHERE amistad.Solicitante = '" + solicitante.getNombre() + "' AND amistad.Solicitado"
                + "= '" + solicitado.getNombre() +  "'");
        rs = sql.executeQuery();
        rs.first();
            
        if(rs.getInt("Verifica") == 0){
            PreparedStatement sqlInsert = conexion.prepareStatement("INSERT INTO amistad(IdAmistad, Solicitante, "
                + "Solicitado, Estado) values(null, ?, ?, ?)");
            sqlInsert.setString(1, solicitante.getNombre());
            sqlInsert.setString(2, solicitado.getNombre());
            sqlInsert.setInt(3, 0);
            sqlInsert.executeUpdate();
            }else{
                
            }          
    }
    
    /**
     * 
     * @param a objeto de tipo amistad
     * @throws SQLException 
     */
    /*public void AceptarAmigo(Amistad a) throws SQLException {
        sql = conexion.prepareStatement("UPDATE amistad SET amistad.Estado= " + 1 + ", "
            + "WHERE amistad.Solicitante = '" + a.getSolicitante() + "' AND amistad.Solicitado"
            + "= '" + a.getSolicitado() +  "'");
        sql.executeUpdate(); 
    }*/
    
    /**
     * Función para eliminar a un amigo
     *Recibe dos parámetros, el usuario que quiere eliminar y el usuario que será eliminado.
     *De la tabla "amistad", borra los campos Solicitante y Solicitado que coincidan con los parámetros recibidos.
     * @param u1 objeto de tipo usuario (remitente)
     * @param u2 objeto de tipo usuario (destinatario)
     * @throws SQLException 
     */
    public void EliminarAmigo(Usuario solicitante, Usuario solicitado) throws SQLException {
        sql = conexion.prepareStatement("DELETE FROM amistad WHERE amistad.Solicitante = ? AND amistad.Solicitado = ?");
        sql.setString(1, solicitante.getNombre());
        sql.setString(2, solicitado.getNombre());
        sql.executeUpdate(); 
        sql = conexion.prepareStatement("DELETE FROM amistad WHERE amistad.Solicitante = ? AND amistad.Solicitado = ?");
        sql.setString(1, solicitado.getNombre());
        sql.setString(2, solicitante.getNombre());
        sql.executeUpdate();       
    }
    
    /**
     * Función para cuando se cierra sesión
     * Cambio el campo "Estado" de la tabla "conexion" a 0 si detecta que un usuario se desconectó.
     * @param usuario objeto de tipo usuario
     * @throws SQLException 
     */
    public void cerrarSesion(Usuario usuario) throws SQLException {
        sql.executeUpdate("UPDATE conexion SET Estado = 0 WHERE Usuario = '" + usuario.getNombre() + "';");
    }
    
    /**
     * Función para modificar amistades
     * Hace una consulta a la tabla de "amistad", donde el campo "Estado" sea 0, y "Solicitado" el nombre del usuario.
     * En una lista, se agregan todos los nombres de los "Solicitantes", que son los que hacen la petición de amistad al 
     * "Solicitado".
     * @param u objeto de tipo usuario
     * @return lista con los nombres de los usuarios con el campo "Estado" en 0
     * @throws SQLException 
     */
    public List<Usuario> notificacionesAmistad (Usuario u) throws SQLException
    {
        List<Usuario> lista = new ArrayList();
        
        sql = conexion.prepareStatement ("SELECT amistad.Solicitante FROM amistad WHERE amistad.Estado = 0 AND "
                + "amistad.Solicitado = '" + u.getNombre() + "';");
        ResultSet rs;
        rs = sql.executeQuery();
        if (rs.first()){
         do 
            {
                Usuario x = new Usuario (rs.getString("Solicitante"), "");
                lista.add(x);
            } while (rs.next()); 
        return lista;
        } return null;
    }
    /**
     * Funcion que hace la consulta a la tabla conexion de la BD para conocer la IP de determinado usuario
     * @param usuario objeto de tipo usuario (usuario con el que se va a buscar)
     * @return Retorna la Ip en caso de encontrarla, de no ser así retorna nulo
     * @throws SQLException 
     */
    public String getIp(Usuario usuario) throws SQLException {
        sql = conexion.prepareStatement("Select ipaddress from conexion where usuario = ?");
        sql.setString(1, usuario.getNombre());
        rs = sql.executeQuery();
        if (rs.first()){
            return rs.getString("IPaddress");
        }else return null;
    }
    
}

    



 
