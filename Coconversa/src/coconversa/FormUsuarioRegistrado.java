package coconversa;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FormUsuarioRegistrado extends JFrame implements ActionListener
{       
    private JLabel lblUsuarioRegistrado, lblExitosamente;
    private JButton btnIniciarSesion;
    
    public FormUsuarioRegistrado()
    {
        configurar();
        componentes();
    }
    
    public void configurar()
    {
        this.setTitle("SignUp");
        this.setSize(200,150);
        setLocationRelativeTo(null);
        this.setResizable(false); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void componentes()
    {
        lblUsuarioRegistrado = new JLabel("Usuario registrado de manera");
        lblUsuarioRegistrado.setFont(new Font ("Calibri (Cuerpo)", Font.BOLD ,18));
        
        lblExitosamente = new JLabel("EXISTOSA");
        lblExitosamente.setFont(new Font ("Calibri (Cuerpo)", Font.BOLD ,18));
        
        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.addActionListener(this);
        
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (    
            contentPane.createParallelGroup(CENTER)
                .addComponent(lblUsuarioRegistrado)
                .addComponent(lblExitosamente)
                .addComponent(btnIniciarSesion)    
        );
        contentPane.setVerticalGroup
        ( 
            contentPane.createSequentialGroup()
                .addComponent(lblUsuarioRegistrado)
                .addComponent(lblExitosamente,30,30,30)
                .addComponent(btnIniciarSesion)        
        );
        this.setLayout(contentPane);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource()==btnIniciarSesion)
        {
            this.setVisible(false);
            FormLogIn LogIn = new FormLogIn();
            LogIn.setVisible(true);
        }
    }
}