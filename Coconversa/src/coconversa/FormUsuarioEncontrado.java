package coconversa;

import clientes.ClienteHiloEscritura;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FormUsuarioEncontrado extends JFrame implements ActionListener
{
    private JLabel lblUsuarioEncontrado, lblAgregarUsuario;
    private JButton btnSiAgregarUsuario, btnNoAgregarUsuario;
    private String User,Amigo;
    private ObjectOutputStream OOS;
    
    /**
     * Inicializa el Form mandando a llamar la función "configurar" y "componentes".
     * Este Form se manda a llamar cuando un usuario ha sido encontrado y se le quiere mandar solicitud de amistad.
     * @param OOS
     * Es el objeto por el cual se escribe al socket.
     * @param Usuario
     * Es un String con el nombre del usuario que va a mandar la solicitud.
     * @param amigo 
     * Es un String con el nombre del usuario al que le llegará la solicitud de amistad.
     */
    public FormUsuarioEncontrado(ObjectOutputStream OOS, String Usuario, String amigo)
    {
        this.OOS = OOS;
        this.User = Usuario;
        this.Amigo = amigo;
        configurar();
        componentes(Usuario);
    }
    
    /**
     * En esta función se configura el nombre y tamaño de la ventana, se centra la venatana en la pantalla y se inhabilita la opción de mover manualmente su tamaño.
     */
    public void configurar()
    {
        this.setTitle("Buscar Amigo");
        this.setSize(350,250);
        setLocationRelativeTo(null);
        this.setResizable(false);   
    }
    
    /**
     * En esta función se inicializan todos los componentes y se acomodan en sus respectivos grupos para generar el diseño de la ventana.
     * @param Usuario
     * Es un String con el nombre del usuario encontrado para mostrarse en un JLabel.
     */
    public void componentes(String Usuario)
    {
        lblUsuarioEncontrado = new JLabel("¡El usuario " + User + " ha sido encontrado!");
        lblUsuarioEncontrado.setFont(new Font("Calibri (Cuerpo)", Font.BOLD ,18));
        
        lblAgregarUsuario = new JLabel("¿Quieres Agregarlo?");
        
        btnSiAgregarUsuario = new JButton("Si");
        btnSiAgregarUsuario.addActionListener(this);
        btnNoAgregarUsuario = new JButton("No");
        btnNoAgregarUsuario.addActionListener(this);
    

        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (
            
            contentPane.createParallelGroup(CENTER)
            
                .addComponent(lblUsuarioEncontrado)
                .addComponent(lblAgregarUsuario)
                .addGroup
                (
                    contentPane.createSequentialGroup()
                         .addComponent(btnSiAgregarUsuario)
                         .addComponent(btnNoAgregarUsuario)
                )
        );
        contentPane.setVerticalGroup
        ( 
            contentPane.createSequentialGroup()
                .addComponent(lblUsuarioEncontrado)
                .addComponent(lblAgregarUsuario,30,30,30)
                .addGroup
                (
                    contentPane.createParallelGroup(CENTER)
                        .addComponent(btnSiAgregarUsuario)
                        .addComponent(btnNoAgregarUsuario)
                )        
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
        if (ae.getSource()==btnSiAgregarUsuario)
        {
          ClienteHiloEscritura findFriend = new ClienteHiloEscritura(OOS);
          findFriend.agregarAmigo(User,Amigo);
          this.setVisible(false);
        }
        if (ae.getSource()==btnNoAgregarUsuario)
        {
            this.setVisible(false);
        }
    }
}
