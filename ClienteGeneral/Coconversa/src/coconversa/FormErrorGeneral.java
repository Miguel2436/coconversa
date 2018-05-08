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

public class FormErrorGeneral extends JFrame implements ActionListener
{
    private JLabel lblError;
    private JButton btnCerrar;
    
    public FormErrorGeneral(String Info)
    {
        configurar();
        componentes(Info);
    }
    
    public void configurar()
    {
        this.setTitle("ERROR");
        this.setSize(350,250);
        setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
    
    public void componentes(String Info)
    {
        lblError = new JLabel(Info);
        lblError.setFont(new Font ("Calibri (Cuerpo)", Font.BOLD ,18));
                
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(this);
        
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (
            contentPane.createParallelGroup(CENTER)
                .addComponent(lblError)
                .addComponent(btnCerrar)    
        );
        contentPane.setVerticalGroup
        ( 
            contentPane.createSequentialGroup()
                .addComponent(lblError,30,30,30)
                .addComponent(btnCerrar)        
        );
        this.setLayout(contentPane);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource()==btnCerrar)
        {
            this.setVisible(false);
        }
    }  
}