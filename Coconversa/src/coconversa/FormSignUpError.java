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
    
    /**
     * Inicializa el Form mandando a llamar la función "configurar" y "componentes".
     * Este Form se manda a llamar cuando las contraseñas en el Form "SignUp" no coinciden entre sí.
     */
    public FormSignUpError()
    {
        configurar();
        componentes();
    }
    
    /**
     * En esta función se configura el nombre y tamaño de la ventana, se centra la venatana en la pantalla y se inhabilita la opción de mover manualmente su tamaño.
     */
    public void configurar()
    {
        this.setTitle("SignUp");
        this.setSize(340,110);
        setLocationRelativeTo(null);
        this.setResizable(false); 
    }
    
    /**
     * En esta función se inicializan todos los componentes y se acomodan en sus respectivos grupos para generar el diseño de la ventana.
     */
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
