package coconversa;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FormUsuarioEncontrado extends JFrame implements ActionListener
{
    private JLabel lblUsuarioEncontrado, lblAgregarUsuario;
    private JButton btnSiAgregarUsuario, btnNoAgregarUsuario;
    
    public FormUsuarioEncontrado()
    {
        configurar();
        componentes();
    }
    
    public void configurar()
    {
        this.setTitle("Buscar Amigo");
        this.setSize(350,250);
        setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    }
    
    public void componentes()
    {
        lblUsuarioEncontrado = new JLabel("¡El usuario ha sido encontrado!");
        lblUsuarioEncontrado.setFont(new Font("Calibri (Cuerpo)", Font.BOLD ,18));
        
        lblAgregarUsuario = new JLabel("¿Quieres Agregarlo?");
        
        btnSiAgregarUsuario = new JButton("Si");
        btnNoAgregarUsuario = new JButton("No");

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
        }
        if (ae.getSource()==btnNoAgregarUsuario)
        {
        }
    }
    
}