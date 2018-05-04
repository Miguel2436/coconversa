package coconversa;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            else
            {
                System.out.println("Conectado al Servidor...");
                FormLogIn LogIn = new FormLogIn();
                LogIn.setVisible(true);
                this.setVisible(false);
            }
        }
    }
}
