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
    
    /**
     * Manda a llamar las funciones "configurar" y "componentes".
     * Este Form muestra un error cuando algún JTextField está vacío.
     */
    public FormLlenarCamposError()
    {
        configurar();
        componentes();
    }
    
    /**
     * En esta función se configura el nombre y tamaño de la ventana, se centra la venatana en la pantalla y se inhabilita la opción de mover manualmente su tamaño.
     */
    public void configurar()
    {
        this.setTitle("Error");
        this.setSize(310,80);
        setLocationRelativeTo(null);
        this.setResizable(false); 
    }
    
    /**
     * En esta función se inicializan todos los componentes y se acomodan en sus respectivos grupos para generar el diseño de la ventana.
     */
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
    
    /**
     * Esta función es mandada a llamar cuando se presiona un botón y dependiendo de cual haya sido presionado se ejecutará una parte de código u otra.
     * @param ae 
     * Es el "identificador" del botón que fue presionado. Con "ae" se evalúa cual botón mandó a llamar la función para ejecutar cierto código.
     */
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource()==btnVale)
        {
            this.setVisible(false);
        }
    }
}
