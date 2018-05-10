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
    private String User;
    private ObjectOutputStream OOS;
    public FormUsuarioEncontrado(ObjectOutputStream OOS,String Usuario)
    {
        this.OOS = OOS;
        this.User = Usuario;
        configurar();
        componentes(Usuario);
    }
    
    public void configurar()
    {
        this.setTitle("Buscar Amigo");
        this.setSize(350,250);
        setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    }
    
    public void componentes(String Usuario)
    {
        lblUsuarioEncontrado = new JLabel("¡El usuario" + User + " ha sido encontrado!");
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

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource()==btnSiAgregarUsuario)
        {
          ClienteHiloEscritura findFriend = new ClienteHiloEscritura(OOS);
          findFriend.agregarAmigo(User);
          this.setVisible(false);
          
        }
        if (ae.getSource()==btnNoAgregarUsuario)
        {
            this.setVisible(false);
        }
    }
    
}