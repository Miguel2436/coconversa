package coconversa;

import clientes.ClienteHiloEscritura;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FormSignUp extends JFrame implements ActionListener
{   
    private JLabel lblRegistro, lblNombre, lblContraseña, lblConfContraseña;
    private JPanel pnlRegistrarse, pnlBoton;
    private JTextField txtNombre;
    private JPasswordField txtContraseña, txtConfContraseña;
    private JButton btnIniciarSesion, btnRegistrarse;   
    
    public FormSignUp()
    {
        configurar();
        componentes();
    }
    
    public void configurar()
    {
        this.setTitle("SignUp");
        this.setSize(500,290);
        this.setPreferredSize(new Dimension (500,290));
        setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
    
    public void componentes()
    {
        lblRegistro = new JLabel("Registrarse en Coconversa");
        lblRegistro.setFont(new Font ("Calibri (Cuerpo)", Font.BOLD ,18));
        lblRegistro.setHorizontalAlignment(JLabel.CENTER);
        pnlRegistrarse = new JPanel(new GridLayout(1,1));
        pnlRegistrarse.add(lblRegistro);
        
        lblNombre = new JLabel("Nombre");
        txtNombre = new JTextField();
        lblContraseña = new JLabel("Contraseña");
        txtContraseña = new JPasswordField();
        lblConfContraseña = new JLabel("Confirma Contraseña"); 
        txtConfContraseña = new JPasswordField();
        
        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.addActionListener(this);
        
        btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.addActionListener(this);
        
        pnlBoton = new JPanel(new FlowLayout());
        pnlBoton.add(btnIniciarSesion);
        pnlBoton.add(btnRegistrarse);
        
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (
            contentPane.createParallelGroup()
                .addComponent(pnlRegistrarse)
                .addComponent(lblNombre)
                .addComponent(txtNombre)
                .addComponent(lblContraseña)
                .addComponent(txtContraseña)
                .addComponent(lblConfContraseña)
                .addComponent(txtConfContraseña)
                .addComponent(pnlBoton)
        );
        contentPane.setVerticalGroup
        (
            contentPane.createSequentialGroup()
                .addComponent(pnlRegistrarse)
                .addComponent(lblNombre)
                .addComponent(txtNombre,30,30,30)
                .addComponent(lblContraseña)
                .addComponent(txtContraseña,30,30,30)
                .addComponent(lblConfContraseña)
                .addComponent(txtConfContraseña,30,30,30)
                .addComponent(pnlBoton)
        );
        this.setLayout(contentPane);
        this.pack();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if(ae.getSource()==btnIniciarSesion)
        {
            this.setVisible(false);
            FormLogIn LogIn = new FormLogIn();
            LogIn.setVisible(true);
        }
        if(ae.getSource()==btnRegistrarse)
        {
            String Contraseña1 = Arrays.toString(txtContraseña.getPassword());
            String Contraseña2 = Arrays.toString(txtConfContraseña.getPassword()); 
            
            if(txtNombre.getText().equals("") || Contraseña1.equals("") || Contraseña2.equals(""))
            {
                FormLlenarCamposError LlenarCamposError = new FormLlenarCamposError();
                LlenarCamposError.setVisible(true);
            }
            else
            {
                if(Contraseña1.equals(Contraseña2) && Contraseña1.equals("[]"))
                {
                    FormLlenarCamposError LlenarCamposError = new FormLlenarCamposError();
                    LlenarCamposError.setVisible(true);
                }
                else
                {
                    if(Contraseña1.equals(Contraseña2))
                    {
                        
                        this.setVisible(false);
                        ClienteHiloEscritura Escribir = new ClienteHiloEscritura();
                        Escribir.signUp(txtNombre.getText(),Contraseña2);
                    }
                    else
                    {
                        System.out.println("Las contraseñas no coinciden");
                        FormSignUpError SignUpError = new FormSignUpError();
                        SignUpError.setVisible(true);
                    }
                }
            }
        }
    }
}