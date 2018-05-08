package coconversa;

import clientes.ClienteHiloEscritura;
import clientes.ClienteHiloLectura;
import clientes.Clientes;
import datos.Mensaje;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FormSolicitudConexion extends JFrame implements ActionListener
{    
    private JLabel lblIPSolicitudConexion;
    private JTextField txtIPsolicitudConexion;
    private JButton btnEnviarSolicitudConexion;
    
    public FormSolicitudConexion()
    {
        configurar();
        componentes();    
    }
    
    public void configurar()
    {
        this.setTitle("Coconversa");
        this.setSize(305,150);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void componentes()
    {
        lblIPSolicitudConexion = new JLabel("Escriba la dirección IP del server");
        lblIPSolicitudConexion.setFont(new Font ("Calibri (Cuerpo)", Font.BOLD ,18));
        txtIPsolicitudConexion = new JTextField();
        btnEnviarSolicitudConexion = new JButton("Acceder");
        btnEnviarSolicitudConexion.addActionListener(this);
        
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (
            contentPane.createParallelGroup(CENTER)
                .addComponent(lblIPSolicitudConexion)
                .addComponent(txtIPsolicitudConexion)
                .addComponent(btnEnviarSolicitudConexion)
        );
        contentPane.setVerticalGroup
        (
            contentPane.createSequentialGroup()
                .addComponent(lblIPSolicitudConexion)
                .addComponent(txtIPsolicitudConexion,30,30,30)
                .addComponent(btnEnviarSolicitudConexion)
        );
        this.setLayout(contentPane);
        this.pack();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
       if(e.getSource()==btnEnviarSolicitudConexion)
       {
           if(txtIPsolicitudConexion.getText().equals(""))
            {
                FormLlenarCamposError LlenarCamposError = new FormLlenarCamposError();
                LlenarCamposError.setVisible(true);
            }
           else{
                    FormLogIn LogIn = new FormLogIn();
                    LogIn.setVisible(true);
                    this.setVisible(false);
           }
           //COMENTAR
           /*
            else{
                //System.out.println("Conectando al Servidor...");
                String ipServidor = txtIPsolicitudConexion.getText();
                Socket clienteSocket =  null;
                try {
                    clienteSocket = new Socket(ipServidor,1000);
                    ClienteHiloEscritura Escritura = new ClienteHiloEscritura(clienteSocket);
                    Escritura.solicitarConexion(ipServidor);
                } catch (IOException ex) {
                    FormErrorGeneral error = new FormErrorGeneral("Error conexion a "+ ipServidor);
                    error.setVisible(true);
                    //this.setVisible(false);
                }
                if(clienteSocket == null){
                    
                }else{
                    ObjectInputStream OIS = null;
                    try {    
                        OIS = new ObjectInputStream(clienteSocket.getInputStream());
                    }catch (IOException ex) {
                        FormErrorGeneral error = new FormErrorGeneral("Error: "+ ex.getMessage());
                        error.setVisible(true);
                        this.setVisible(false);
                    }
                    Mensaje men = new Mensaje();
                    Socket clienteS =  null;
                    try {
                        men = (Mensaje)OIS.readObject();
                        if(men.getOperacion().equals("SOLICITAR_CONEXION") && men.isEstado()){
                         clienteSocket.close();
                         clienteS = new Socket(ipServidor,Integer.parseInt(men.getMensaje()));
                        }else{
                            FormErrorGeneral error = new FormErrorGeneral("No fue recibida respuesta del servidor");
                            error.setVisible(true);
                        }
                    } catch (ClassNotFoundException ex) {
                        FormErrorGeneral error = new FormErrorGeneral("Error proceso Conexion");
                        error.setVisible(true);
                    } catch (IOException ex) {
                        FormErrorGeneral error = new FormErrorGeneral("Error proceso Conexion");
                        error.setVisible(true);
                    }
                    Thread hiloLectura = null;
                    try {
                        hiloLectura = new Thread(new ClienteHiloLectura(clienteS));
                    } catch (IOException ex) {
                        FormErrorGeneral error = new FormErrorGeneral("Error: "+ex.getMessage());
                        error.setVisible(true);
                    }
                    hiloLectura.start();
                    FormLogIn LogIn = new FormLogIn();
                    LogIn.setVisible(true);
                    this.setVisible(false);
                }   
            }*/
           //COMENTAR
        }
    }
}
