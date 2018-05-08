package coconversa;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public FormSolicitudAmigo()
    {
        configurar();
        componentes();
    }
    
    public void configurar()
    {
        this.setTitle("Solicitud de Amistad");
        this.setSize(340,150);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void componentes()
    {
        lblSolicitudAmistad = new JLabel("¡Tienes una solicitud de amistad!");
        lblSolicitudAmistad.setFont(new Font("Calibri (Cuerpo)", Font.BOLD, 18));
        
        lblAmigoSolicitante = new JLabel("Alma Marcela");
        lblAmigoSolicitante.setForeground(Color.red);
        lblMensajeAmigoSolicitante = new JLabel(lblAmigoSolicitante.getText() + " quiere ser tu amiga en Coconversa");
        
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
            
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==btnAceptarAmigo)   
        { 
        }
        if(e.getSource()==btnRechazarAmigo)   
        { 
        }
    }
}