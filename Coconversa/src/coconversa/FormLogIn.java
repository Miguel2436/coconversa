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

public class FormLogIn extends JFrame implements ActionListener
{
    private JLabel lblIngresar, lblNombre, lblContraseña;
    private JPanel pnlIngresar, pnlBotones;
    private JTextField txtNombre;
    private JPasswordField txtContraseña;
    private JButton btnIniciarSesion, btnRegistrar;
    private ObjectOutputStream OOS;
    
    /**
     * Manda a llamar la función "configurar" y "componentes".
     * El Form sirve para que el usuario inicie sesión en el sistema.
     * @param OOS
     * Es el objeto por el cual se escribe al socket.
     */
    public FormLogIn(ObjectOutputStream OOS)
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
        this.setTitle("LogIn");
        this.setSize(500,230);
        this.setPreferredSize(new Dimension (500,230));
        setLocationRelativeTo(null); 
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * En esta función se inicializan todos los componentes y se acomodan en sus respectivos grupos para generar el diseño de la ventana.
     */
    public void componentes()
    {

        lblIngresar = new JLabel("Ingresar a Coconversa");
        lblIngresar.setFont(new Font ("Calibri (Cuerpo)", Font.BOLD ,18));
        lblIngresar.setHorizontalAlignment(JLabel.CENTER);
        pnlIngresar = new JPanel(new GridLayout(1,1));
        pnlIngresar.add(lblIngresar);
        
        lblNombre = new JLabel("Nombre");
        txtNombre = new JTextField();
        lblContraseña = new JLabel("Contraseña"); 
        txtContraseña = new JPasswordField();
        
        btnIniciarSesion = new JButton("Iniciar Sesión"); 
        btnIniciarSesion.addActionListener(this);
        btnRegistrar = new JButton("  Registrate  ");
        btnRegistrar.addActionListener(this);
        pnlBotones = new JPanel(new FlowLayout());
        pnlBotones.add(btnIniciarSesion);
        pnlBotones.add(btnRegistrar);
                
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (
            contentPane.createParallelGroup()
                .addComponent(pnlIngresar) 
                .addComponent(lblNombre)
                .addComponent(txtNombre)
                .addComponent(lblContraseña)
                .addComponent(txtContraseña)
                .addComponent(pnlBotones)                     
        );
        contentPane.setVerticalGroup
        (
            contentPane.createSequentialGroup()
                .addComponent(pnlIngresar)
                .addComponent(lblNombre)
                .addComponent(txtNombre,30,30,30)
                .addComponent(lblContraseña)
                .addComponent(txtContraseña,30,30,30)
                .addComponent(pnlBotones)       
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
            if(txtNombre.getText().equals("") || txtContraseña.getText().equals(""))
            {
                FormLlenarCamposError LlenarCamposError = new FormLlenarCamposError();
                LlenarCamposError.setVisible(true);
            }
            else
            {
                //Comentar
                
                ClienteHiloEscritura log = new ClienteHiloEscritura(OOS);
                log.logIn(txtNombre.getText(),Arrays.toString(txtContraseña.getPassword()));
                //System.out.println(""+Arrays.toString(txtContraseña.getPassword()));
                this.setVisible(false);
                //Comentar
               
            }
        }
        if(ae.getSource()==btnRegistrar)
        {
            this.setVisible(false);
            FormSignUp SignUp = new FormSignUp(OOS);
            SignUp.setVisible(true);
        }
    }
}
