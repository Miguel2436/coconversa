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

public class FormModificarGrupo extends JFrame implements ActionListener
{
    private JLabel lblModificarGrupo;
    private JTextField txtNombreGrupo;
    private JPanel pnlListaAmigos;
    private JScrollPane scrListaAmigos;
    private JButton btnModificarGrupo;
    private List<Usuario> usuariosGeneral;
    private List<JCheckBox> listaChecks = new ArrayList<>();
    private ObjectOutputStream OOS;
    private String groupName, userName;
    
    public FormModificarGrupo(ObjectOutputStream oos, String userName, String groupName, List<Usuario> usuarios)
    {
        this.usuariosGeneral = usuarios;
        this.OOS = oos;
        this.groupName = groupName;
        this.userName = userName;
        configurar();
        componentes();
    }
    
    public void configurar()
    {
        this.setTitle("Modificar Grupo");
        this.setSize(300,400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    
    public void componentes()
    {
        lblModificarGrupo = new JLabel("Modificar Grupo");
        lblModificarGrupo.setFont(new Font("Calibri (Cuerpo)", Font.BOLD, 18));
        
        txtNombreGrupo = new JTextField(groupName);
        txtNombreGrupo.setEditable(false);
        pnlListaAmigos = new JPanel();
        pnlListaAmigos.setLayout(new BoxLayout(pnlListaAmigos,Y_AXIS));
        
        scrListaAmigos = new JScrollPane(pnlListaAmigos);
        scrListaAmigos.getVerticalScrollBar().setUnitIncrement(10);
        scrListaAmigos.setPreferredSize(new Dimension(250,250));
        scrListaAmigos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrListaAmigos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        btnModificarGrupo = new JButton("Modificar Grupo");
        btnModificarGrupo.addActionListener(this);
        
        pnlListaAmigos.add(Box.createVerticalStrut(10));
        for(int i=0; i<=usuariosGeneral.size(); i++)
        {
            JCheckBox checkBox = new JCheckBox(usuariosGeneral.get(i).getNombre());
            checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            listaChecks.add(checkBox);
            pnlListaAmigos.add(checkBox);
            pnlListaAmigos.add(Box.createVerticalStrut(10));
        }
        
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup
        (
            contentPane.createParallelGroup(CENTER)
                .addComponent(lblModificarGrupo)
                .addComponent(txtNombreGrupo)
                .addComponent(scrListaAmigos)
                .addComponent(btnModificarGrupo)
        );
        contentPane.setVerticalGroup
        (
            contentPane.createSequentialGroup()
                .addComponent(lblModificarGrupo)
                .addComponent(txtNombreGrupo,30,30,30)
                .addComponent(scrListaAmigos)
                .addComponent(btnModificarGrupo)
        );
        this.setLayout(contentPane);
        this.pack();
    }
            
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==btnModificarGrupo)   
        { 
            List<Usuario> listaIntegrantesNueva = new ArrayList<>();
            for (int i = 0; i < listaChecks.size(); i++) {
                JCheckBox amigo = listaChecks.get(i);
                if(amigo.isSelected()) listaIntegrantesNueva.add( new Usuario(amigo.getText(), ""));
            }
            listaIntegrantesNueva.add(new Usuario(userName, ""));
            ClienteHiloEscritura mGrupo = new ClienteHiloEscritura(OOS);
            FormErrorGeneral feg;
            if (listaIntegrantesNueva.size() < 3) {
                feg = new FormErrorGeneral("Necesitas mas integrantes");
                feg.setVisible(true);
            }
            else mGrupo.modificarGrupo(groupName, listaIntegrantesNueva);
            this.setVisible(false);
        }
    }
}
