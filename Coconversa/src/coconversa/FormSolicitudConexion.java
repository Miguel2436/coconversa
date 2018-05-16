package coconversa;

import clientes.ClienteHiloEscritura;
import clientes.ClienteHiloLectura;
import clientes.Clientes;
import datos.Mensaje;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
    private ObjectOutputStream  OOS;
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
        txtIPsolicitudConexion.setHorizontalAlignment(JTextField.CENTER);
        btnEnviarSolicitudConexion = new JButton("Acceder");
        btnEnviarSolicitudConexion.addActionListener(this);
        txtIPsolicitudConexion.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode()== KeyEvent.VK_ENTER)
                {
                   enviar(); 
                }
                else {
                    if(ke.getKeyCode()== KeyEvent.VK_BACK_SPACE)
                    {
                        txtIPsolicitudConexion.setText(txtIPsolicitudConexion.getText().substring(0,txtIPsolicitudConexion.getText().length()));
                    }
                    else {
                        txtIPsolicitudConexion.setText(txtIPsolicitudConexion.getText()+ke.getKeyChar());
                    }
                }
                //wait(10);
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                
            }
        });
        
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
            enviar();
        }
    }
    public void enviar() {
        if(txtIPsolicitudConexion.getText().equals(""))
            {
                FormLlenarCamposError LlenarCamposError = new FormLlenarCamposError();
                LlenarCamposError.setVisible(true);
            }
            else
            {
                String IP = txtIPsolicitudConexion.getText();
                String[] partesIP = IP.split(Pattern.quote("."));
                int[] numeros = new int[partesIP.length];
                if(partesIP.length==4)
                {
                    for(int i=0; i<4; i++) numeros[i] = Integer.parseInt(partesIP[i]);
                    if(numeros[0]>0 && numeros[0]<256 && numeros[1]>0 && numeros[1]<256 && numeros[2]>0 && numeros[2]<256 && numeros[3]>0 && numeros[3]<256)
                    {
                        
                    //System.out.println("Conectado al Servidor...");
                        String ipServidor = txtIPsolicitudConexion.getText();
                        Socket clienteSocket =  null;
                        try {
                            clienteSocket = new Socket(ipServidor,1000);
                            ClienteHiloEscritura Escritura = new ClienteHiloEscritura(new ObjectOutputStream (clienteSocket.getOutputStream()));
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
                                FormErrorGeneral error = new FormErrorGeneral("Error proceso Conexion IOException");
                                error.setVisible(true);
                            }
                            Thread hiloLectura = null;
                            try {
                                OOS = new ObjectOutputStream(clienteS.getOutputStream());
                            } catch (IOException ex) {
                            }
                            FormChat Chat = new FormChat(OOS,"Usuario");
                            try {
                                hiloLectura = new Thread(new ClienteHiloLectura(OOS,clienteS,Chat));
                            } catch (IOException ex) {
                                FormErrorGeneral error = new FormErrorGeneral("Error: "+ex.getMessage());
                                error.setVisible(true);
                            } 
                            hiloLectura.start();
                            FormLogIn LogIn = new FormLogIn(OOS);
                            LogIn.setVisible(true);
                            this.setVisible(false); 
                        }  
                    }
                    else
                    {
                        FormErrorGeneral Error = new FormErrorGeneral("Por favor, escriba la IP correctamente");
                        Error.setVisible(true);
                    }
                }
                else
                {
                    FormErrorGeneral Error = new FormErrorGeneral("Por favor, escriba la IP correctamente");
                    Error.setVisible(true);
                }
            }
        
    }
}
