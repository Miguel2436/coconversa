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

public class FormExitosoGeneral extends JFrame implements ActionListener
{
    private JLabel lblError;
    private JButton btnCerrar;
    
    /**
     * Inicializa el Form mandando a llamar la función "configurar" y "componentes".
     * Este Form recibe un String en el cual está escrita la acción exitosa que se ha realizado. Se muestra en un JLabel.
     * @param Info
     * Es un String que tiene el mensaje de la acción exitosa que se ha realizado.
     */
    public FormExitosoGeneral(String Info)
    {
        configurar();
        componentes(Info);
    }
    
    /**
     * En esta función se configura el nombre y tamaño de la ventana, se centra la venatana en la pantalla y se inhabilita la opción de mover manualmente su tamaño.
     */
    public void configurar()
    {
        this.setTitle("Exitoso");
        this.setSize(350,250);
        setLocationRelativeTo(null);
        this.setResizable(false);
    }
    
    /**
     * En esta función se inicializan todos los componentes y se acomodan en sus respectivos grupos para generar el diseño de la ventana.
     * @param Info
     * Es un String que contiene el mensaje de acción realizada exitosamente que se ha generado. Se muestra en un JLabel.
     */
    public void componentes(String Info)
    {
        lblError = new JLabel(Info);
        lblError.setFont(new Font ("Calibri (Cuerpo)", Font.BOLD ,18));
                
        btnCerrar = new JButton("Aceptar");
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

    /**
     * Esta función es mandada a llamar cuando se presiona un botón y dependiendo de cual haya sido presionado se ejecutará una parte de código u otra.
     * @param ae
     * Es el "identificador" del botón que fue presionado. Con "ae" se evalúa cual botón mandó a llamar la función para ejecutar cierto código.
     */
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource()==btnCerrar)
        {
            this.setVisible(false);
        }
    }  
}
