package coconversa;

import clientes.ClienteHiloEscritura;
import java.awt.Color;
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

public class FormSolicitudAmigo extends JFrame implements ActionListener
{
   private JLabel lblSolicitudAmistad;
    private JLabel lblAmigoSolicitante;
    private JLabel lblMensajeAmigoSolicitante;
    private JButton btnAceptarAmigo;
    private JButton btnRechazarAmigo;
    private String userD;
    private String userR;
    private ObjectOutputStream OOS;
    
    /**
     * Inicializa el Form mandando a llamar la función "configurar" y "componentes".
     * Este Form se manda a llamar cuando un usuario recibe una solicitud de amistad.
     * @param OOS
     * Es el objeto por el cual se escribe al socket.
     * @param Destinatario
     * Es un String con el nombre de usuario de destino.
     * @param Remitente 
     * Es un String con el nombre de usuario de origen.
     */
    public FormSolicitudAmigo(ObjectOutputStream OOS, String Destinatario, String Remitente )
    {
        this.OOS = OOS;
        this.userD = Destinatario;
        this.userR = Remitente;
        configurar();
        componentes();
    }
    
    /**
     * En esta función se configura el nombre y tamaño de la ventana, se centra la venatana en la pantalla y se inhabilita la opción de mover manualmente su tamaño.
     */
    public void configurar()
    {
        this.setTitle("Solicitud de Amistad");
        this.setSize(340,150);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    
    /**
     * En esta función se inicializan todos los componentes y se acomodan en sus respectivos grupos para generar el diseño de la ventana.
     */
    public void componentes()
    {
        lblSolicitudAmistad = new JLabel("¡Tienes una solicitud de amistad!");
        lblSolicitudAmistad.setFont(new Font("Calibri (Cuerpo)", Font.BOLD, 18));
        
        lblAmigoSolicitante = new JLabel(userD);
        lblAmigoSolicitante.setForeground(Color.red);
        lblMensajeAmigoSolicitante = new JLabel(userR + " quiere ser tu amig@ en Coconversa");
        
        btnAceptarAmigo = new JButton("Aceptar");
        btnAceptarAmigo.addActionListener(this);
        btnRechazarAmigo = new JButton("Rechazar");
        btnRechazarAmigo.addActionListener(this);
        
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (
            contentPane.createParallelGroup(CENTER)
                .addComponent(lblSolicitudAmistad)
                .addComponent(lblMensajeAmigoSolicitante)
                .addGroup
                (
                    contentPane.createSequentialGroup()
                        .addComponent(btnAceptarAmigo)
                        .addComponent(btnRechazarAmigo)
                )
        );
        contentPane.setVerticalGroup
        (
            contentPane.createSequentialGroup()
                .addComponent(lblSolicitudAmistad)
                .addComponent(lblMensajeAmigoSolicitante,30,30,30)
                .addGroup
                (
                    contentPane.createParallelGroup(CENTER)
                        .addComponent(btnAceptarAmigo)
                        .addComponent(btnRechazarAmigo)
                )
        );
        this.setLayout(contentPane);
        this.pack();
    }
       
    /**
     * Esta función es mandada a llamar cuando se presiona un botón y dependiendo de cual haya sido presionado se ejecutará una parte de código u otra.
     * @param e
     * Es el "identificador" del botón que fue presionado. Con "e" se evalúa cual botón mandó a llamar la función para ejecutar cierto código.
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    { if(e.getSource()==btnAceptarAmigo)   
        { 
          ClienteHiloEscritura aceptarAmigo = new ClienteHiloEscritura(OOS);
          aceptarAmigo.respuestaAmigo(userD, userR, true);
          aceptarAmigo.amigosConectados(userD);
          aceptarAmigo.amigosDesconectados(userD);
          this.setVisible(false);
        }
        if(e.getSource()==btnRechazarAmigo)   
        { 
          ClienteHiloEscritura aceptarAmigo = new ClienteHiloEscritura(OOS);
          aceptarAmigo.eliminarAmigo(userD,userR);
          this.setVisible(false);
        }
    }
}
