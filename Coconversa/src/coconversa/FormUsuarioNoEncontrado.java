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
    
    /**
     * Inicializa el Form mandando a llamar la función "configurar" y "componentes".
     * Este Form se desea mandar solicitud de amistad a un usuario pero este no se encuentra en el sistema.
     */
    public FormUsuarioNoEncontrado()
    {
        configurar();
        componentes();
    }
    
    /**
     * En esta función se configura el nombre y tamaño de la ventana, se centra la venatana en la pantalla y se inhabilita la opción de mover manualmente su tamaño.
     */
    public void configurar()
    {
        this.setTitle("Buscar Amigo");
        this.setSize(350,250);
        setLocationRelativeTo(null);
        this.setResizable(false);
    }
    
    /**
     * En esta función se inicializan todos los componentes y se acomodan en sus respectivos grupos para generar el diseño de la ventana.
     */
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

    /**
     * Esta función es mandada a llamar cuando se presiona un botón y dependiendo de cual haya sido presionado se ejecutará una parte de código u otra.
     * @param ae
     * Es el "identificador" del botón que fue presionado. Con "ae" se evalúa cual botón mandó a llamar la función para ejecutar cierto código.
     */
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource()==btnBuscarDeNuevo)
        {
            this.setVisible(false);
        }
    }  
}
