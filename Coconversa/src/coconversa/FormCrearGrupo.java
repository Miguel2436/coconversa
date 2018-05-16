package coconversa;

import clientes.ClienteHiloEscritura;
import datos.Usuario;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class FormCrearGrupo extends JFrame implements ActionListener
{
    private JLabel lblCrearGrupo;
    private JTextField txtNombreGrupo;
    private JPanel pnlListaAmigos;
    private JScrollPane scrListaAmigos;
    private JButton btnCrearGrupo;
    private List<JCheckBox> listaChecks = new ArrayList<>();
    private List <Usuario> usuariosGeneral; //Lista para traer a los usuarios
    private ObjectOutputStream OOS;
    private String nombreUsuario;
    
    /**
     * Es el constructor de la clase. Recibe tres parámetros y los guarda en los objetos de la propia clase.
     * Manda a llamar la función "configurar" y "componentes".
     * El formulario sirve para crear un grupo escribiendo el nombre del grupo y seleccionando los integrantes que
     * estarán en él.
     * @param oos
     * Es el objeto por el cual se escribe al socket.
     * @param nombreUsuario
     * Es el nombre del usuario que tiene la sesión iniciada.
     * @param usuarios 
     * Es una lista la cual almacena todos los amigos que tiene el usuario con la sesión iniciada.
     */
    public FormCrearGrupo(ObjectOutputStream oos, String nombreUsuario, List<Usuario> usuarios)
    {
       this.usuariosGeneral = usuarios;
       this.OOS = oos;
       this.nombreUsuario = nombreUsuario;
       configurar();
       componentes();
    }
    
    /**
     * En esta función se configura el nombre y tamaño de la ventana, se centra la venatana en la pantalla y se
     * inhabilita la opción de mover manualmente su tamaño.
     */
    public void configurar()
    {
        this.setTitle("Crear Grupo");
        this.setSize(300,400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    
    /**
     * En esta función se inicializan todos los componentes y se acomodan en sus respectivos grupos para generar
     * el diseño de la ventana.
     */
    public void componentes()
    {
        lblCrearGrupo = new JLabel("Crear Grupo");
        lblCrearGrupo.setFont(new Font("Calibri (Cuerpo)", Font.BOLD, 18));
        
        txtNombreGrupo = new JTextField("//Nombre de Grupo//");
        
        pnlListaAmigos = new JPanel();
        pnlListaAmigos.setLayout(new BoxLayout(pnlListaAmigos,Y_AXIS));
        
        scrListaAmigos = new JScrollPane(pnlListaAmigos);
        scrListaAmigos.getVerticalScrollBar().setUnitIncrement(10);
        scrListaAmigos.setPreferredSize(new Dimension(250,250));
        scrListaAmigos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrListaAmigos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        btnCrearGrupo = new JButton("Crear Grupo");
        btnCrearGrupo.addActionListener(this);
 
        pnlListaAmigos.add(Box.createVerticalStrut(10));
        for(int i=0; i<usuariosGeneral.size(); i++)
        {            
            JCheckBox amigo = new JCheckBox(usuariosGeneral.get(i).getNombre());
            amigo.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnlListaAmigos.add(amigo);
            listaChecks.add(amigo);
            pnlListaAmigos.add(Box.createVerticalStrut(10));
        }
        
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (
            contentPane.createParallelGroup(CENTER)
                .addComponent(lblCrearGrupo)
                .addComponent(txtNombreGrupo)
                .addComponent(scrListaAmigos)
                .addComponent(btnCrearGrupo)
        );
        contentPane.setVerticalGroup
        (
            contentPane.createSequentialGroup()
                .addComponent(lblCrearGrupo)
                .addComponent(txtNombreGrupo,30,30,30)
                .addComponent(scrListaAmigos)
                .addComponent(btnCrearGrupo)
        );
        this.setLayout(contentPane);
        this.pack();
    }
    
    /**
     * Esta función es mandada a llamar cuando se presiona un botón y dependiendo de cual haya sido presionado
     * se ejecutará una parte de código u otra.
     * @param e 
     * Es el "identificador" del botón que fue presionado. Con "e" se evalúa cual botón mandó a llamar la
     * función para ejecutar cierto código.
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==btnCrearGrupo)   
        { 
            List<Usuario> amigosSeleccionados = new ArrayList<>();
            ClienteHiloEscritura crearGrupo = new ClienteHiloEscritura(OOS);  
            for (int i = 0; i < listaChecks.size(); i++)
            {
                try
                {
                    JCheckBox temp = (JCheckBox) listaChecks.get(i);
                    if(temp.isSelected()) amigosSeleccionados.add(new Usuario(temp.getText(), ""));
                }
                catch (ClassCastException ex) 
                {    
                }                
            }
            amigosSeleccionados.add(new Usuario(nombreUsuario, ""));
            FormErrorGeneral feg;
            if(amigosSeleccionados.size() < 3) 
            {
                feg = new FormErrorGeneral("Necesitas mas integrantes");
                feg.setVisible(true);
            }
            else crearGrupo.crearGrupo(txtNombreGrupo.getText(), amigosSeleccionados);
            this.setVisible(false);
        }
    }
}
