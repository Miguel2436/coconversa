package coconversa;

import clientes.ClienteHiloEscritura;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
    private ObjectOutputStream OOS;
    
    /**
     * Manda a llamar la función "configurar" y "componentes".
     * El Form sirve para que un usuario se dé de alta por primera vez en el sistema.
     * @param OOS 
     * Es el objeto por el cual se escribe al socket.
     */
    public FormSignUp(ObjectOutputStream OOS)
    {
        this.OOS = OOS;
        configurar();
        componentes();
    }
    
    /**
     * En esta función se configura el nombre y tamaño de la ventana, se centra la venatana en la pantalla y se inhabilita la opción de mover manualmente su tamaño.
     */
    public void configurar()
    {
        this.setTitle("SignUp");
        this.setSize(500,290);
        this.setPreferredSize(new Dimension (500,290));
        setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
    
    /**
     * En esta función se inicializan todos los componentes y se acomodan en sus respectivos grupos para generar el diseño de la ventana.
     */
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
    
    /**
     * Esta función es mandada a llamar cuando se presiona un botón y dependiendo de cual haya sido presionado se ejecutará una parte de código u otra.
     * @param ae
     * Es el "identificador" del botón que fue presionado. Con "ae" se evalúa cual botón mandó a llamar la función para ejecutar cierto código.
     */
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if(ae.getSource()==btnIniciarSesion)
        {
            this.setVisible(false);
            FormLogIn LogIn = new FormLogIn(OOS);
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
                        ClienteHiloEscritura Escribir = new ClienteHiloEscritura(OOS);
                        Escribir.signUp(txtNombre.getText(),Arrays.toString(txtConfContraseña.getPassword()));
                    }
                    else
                    {
                        //System.out.println("Las contraseñas no coinciden");
                        FormSignUpError SignUpError = new FormSignUpError();
                        SignUpError.setVisible(true);
                    }
                }
            }
        }
    }
}
