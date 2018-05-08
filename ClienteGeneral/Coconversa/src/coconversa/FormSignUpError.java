package coconversa;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FormSignUpError extends JFrame implements ActionListener
{       
    private JLabel lblContraseñasNoCoinciden, lblVolverIntentar;
    private JButton btnVale;
    
    public FormSignUpError()
    {
        configurar();
        componentes();
    }
    
    public void configurar()
    {
        this.setTitle("SignUp");
        this.setSize(340,110);
        setLocationRelativeTo(null);
        this.setResizable(false); 
    }
    
    public void componentes()
    {
        lblContraseñasNoCoinciden = new JLabel("¡Ups! Las contraseñas no coinciden");
        lblContraseñasNoCoinciden.setFont(new Font ("Calibri (Cuerpo)", Font.BOLD ,18));
        
        lblVolverIntentar = new JLabel("Escribe correctamente ambas contraseñas");
        
        btnVale = new JButton("Vale");
        btnVale.addActionListener(this);
        
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (    
            contentPane.createParallelGroup(CENTER)
                .addComponent(lblContraseñasNoCoinciden)
                .addComponent(lblVolverIntentar)
                .addComponent(btnVale)    
        );
        contentPane.setVerticalGroup
        ( 
            contentPane.createSequentialGroup()
                .addComponent(lblContraseñasNoCoinciden)
                .addComponent(lblVolverIntentar,30,30,30)
                .addComponent(btnVale)        
        );
        this.setLayout(contentPane);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource()==btnVale)
        {
            this.setVisible(false);
        }
    }
}