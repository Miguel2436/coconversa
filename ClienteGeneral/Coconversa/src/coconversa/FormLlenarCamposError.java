package coconversa;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FormLlenarCamposError extends JFrame implements ActionListener
{       
    private JLabel lblLleneLosCampos;
    private JButton btnVale;
    
    public FormLlenarCamposError()
    {
        configurar();
        componentes();
    }
    
    public void configurar()
    {
        this.setTitle("Error");
        this.setSize(310,80);
        setLocationRelativeTo(null);
        this.setResizable(false); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void componentes()
    {
        lblLleneLosCampos = new JLabel("Por favor, llene todos los campos");
        lblLleneLosCampos.setFont(new Font ("Calibri (Cuerpo)", Font.BOLD ,18));
                
        btnVale = new JButton("Vale");
        btnVale.addActionListener(this);
        
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (    
            contentPane.createParallelGroup(CENTER)
                .addComponent(lblLleneLosCampos)
                .addComponent(btnVale)    
        );
        contentPane.setVerticalGroup
        ( 
            contentPane.createSequentialGroup()
                .addComponent(lblLleneLosCampos)
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