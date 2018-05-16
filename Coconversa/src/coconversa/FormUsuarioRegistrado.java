package coconversa;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FormUsuarioRegistrado extends JFrame implements ActionListener
{       
    private JLabel lblUsuarioRegistrado, lblExitosamente;
    private JButton btnIniciarSesion;
    private ObjectOutputStream OOS;
    
    /**
     * Inicializa el Form mandando a llamar la función "configurar" y "componentes".
     * Este Form se manda a llamar si el usuario se ha regitrado correctamente en el sistema en el Form de SignUp.
     * @param OOS
     * Es el objeto por el cual se escribe al socket.
     */
    public FormUsuarioRegistrado(ObjectOutputStream OOS)
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
        this.setSize(200,150);
        setLocationRelativeTo(null);
        this.setResizable(false); 
    }
    
    /**
     * En esta función se inicializan todos los componentes y se acomodan en sus respectivos grupos para generar el diseño de la ventana.
     */
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

    /**
     * Esta función es mandada a llamar cuando se presiona un botón y dependiendo de cual haya sido presionado se ejecutará una parte de código u otra.
     * @param ae
     * Es el "identificador" del botón que fue presionado. Con "ae" se evalúa cual botón mandó a llamar la función para ejecutar cierto código.
     */
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource()==btnIniciarSesion)
        {
            this.setVisible(false);
            FormLogIn LogIn = new FormLogIn(OOS);
            LogIn.setVisible(true);
        }
    }
}
