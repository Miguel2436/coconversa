package coconversa;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FormUsuarioNoEncontrado extends JFrame implements ActionListener
{
    private JLabel lblUsuarioNoEncontrado, lblSeLlamaAsi;
    private JButton btnBuscarDeNuevo;
    
    public FormUsuarioNoEncontrado()
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
    }
    
    public void componentes()
    {
        lblUsuarioNoEncontrado = new JLabel("¡Ups! El usuario no ha sido encontrado");
        lblUsuarioNoEncontrado.setFont(new Font ("Calibri (Cuerpo)", Font.BOLD ,18));
        
        lblSeLlamaAsi = new JLabel("¿Estas seguro que se llama asi?");
        
        btnBuscarDeNuevo = new JButton("Buscar de nuevo");
        btnBuscarDeNuevo.addActionListener(this);
        
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (
            contentPane.createParallelGroup(CENTER)

                .addComponent(lblUsuarioNoEncontrado)
                .addComponent(lblSeLlamaAsi)
                .addComponent(btnBuscarDeNuevo)    
        );
        contentPane.setVerticalGroup
        ( 
            contentPane.createSequentialGroup()
                .addComponent(lblUsuarioNoEncontrado)
                .addComponent(lblSeLlamaAsi,30,30,30)
                .addComponent(btnBuscarDeNuevo)        
        );
        this.setLayout(contentPane);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource()==btnBuscarDeNuevo)
        {
            this.setVisible(false);
        }
    }  
}