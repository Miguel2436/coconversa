package coconversa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.X_AXIS;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class FormChat extends JFrame implements ActionListener
{
   //---------PANEL CONVERSACIÓN----------------------------------------------
    private JTextField txtMensajesChat;
    private JButton btnEnviarChat,btnSalirChat;
    private JTabbedPane tabMensajesChat;
    private JPanel panelConversacionChat,panelScrConversacionChat;
    private JList listaConversacionChat;
    private JScrollPane scrConversacionChat;
    // Esto de abajo lo añadió Alan gg //
    private JLabel lblCoconversa, lblUsuarioChat;
    private JPanel pnlUsuarioBoton;
    
    //-----PANEL AMIGOS-------------------------------------------------------
    private JTextField txtBuscarAmigosChat;
    private JButton btnBuscarAmigosChat,btnMensajeAmigoChat,btnEliminarAmigoChat;
    private JTabbedPane tabAmigosChat;
    private JPanel panelAmigosChat,panelListaAmigosChat;
    private JScrollPane scrAmigosConectadosChat,scrAmigosDesconectadosChat;
    private JLabel lblAmigosChat,lblAmigosConectadosChat;
    
    //------PANEL GRUPOS------------------------------------------------------
    private JTabbedPane tabGruposChat;
    private JButton btnCrearGruposChat,btnModificarGruposChat,btnEliminarGruposChat,btnSalirGruposChat,btnMensajeGruposChat;
    private JPanel panelGruposChat,panelMostrarGruposChat;
    private JScrollPane scrGruposChat;
    private JLabel lblGruposChat,lblNombreGrupoChat;
    private JPanel panelScrollGruposChat;
    
    private JPanel panelListaGruposChat;
    private JScrollPane scrListaGruposChat;
            
    String Amigo="Fer";
    String Usuario="Miguel";
    String []arregConversacionChat={Amigo,Usuario};
    String AmigosConectados=Amigo;
    String Grupo="Grupo";
    
    public FormChat()
    {
        configurar();
        componentes();
    }
    
    public void configurar()
    {
        this.setTitle("Coconversa");
        this.setSize(1100,450);
        this.setMinimumSize(new Dimension(1100,450));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void componentes()
    {
        //--------PANEL DE LA CONVERSACIÓN---------------
        lblCoconversa = new JLabel("Nuestro Super Mega Uper Duper Chat: COCONVERSA");
        lblCoconversa.setFont(new Font ("Calibri (Cuerpo)", Font.BOLD ,18));
        
        lblUsuarioChat=new JLabel(Usuario);
        btnSalirChat = new JButton("Salir");
        pnlUsuarioBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 3));
        pnlUsuarioBoton.setBorder(new LineBorder(Color.BLACK));
        pnlUsuarioBoton.add(lblUsuarioChat);
        pnlUsuarioBoton.add(btnSalirChat);
        
        tabMensajesChat=new JTabbedPane();
        panelConversacionChat=new JPanel();
        listaConversacionChat=new JList(arregConversacionChat);
        getContentPane().add(tabMensajesChat);
        txtMensajesChat=new JTextField();
        btnEnviarChat = new JButton("Enviar");
        scrConversacionChat = new JScrollPane(listaConversacionChat);
        scrConversacionChat.getVerticalScrollBar().setUnitIncrement(10);
        scrConversacionChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrConversacionChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
 
        for(int k=1;k<30;k++)
        {
            listaConversacionChat=new JList(arregConversacionChat);
        }
        JPanel panelConversacionChat=new JPanel();
        GroupLayout pt = new GroupLayout(panelConversacionChat);
        pt.setAutoCreateContainerGaps(true);
        pt.setAutoCreateGaps(true);
        panelConversacionChat.setLayout(pt);
        pt.setHorizontalGroup
        (
            pt.createParallelGroup()
            .addComponent(scrConversacionChat)
            .addGroup
            (
                pt.createSequentialGroup()
                .addComponent(txtMensajesChat)
                .addComponent(btnEnviarChat)
            )
        );
        pt.setVerticalGroup
        (
            pt.createSequentialGroup()
            .addComponent(scrConversacionChat,300,300,300)
            .addGroup
            (
                pt.createParallelGroup()
                .addComponent(txtMensajesChat,30,30,30)
                .addComponent(btnEnviarChat,30,30,30)
            )
        );
        tabMensajesChat.addTab(Amigo, panelConversacionChat);
        
        //----------PANEL DE AMIGOS-----------------
        lblAmigosChat=new JLabel("Amigos");
        lblAmigosConectadosChat=new JLabel(AmigosConectados);
        btnBuscarAmigosChat= new JButton("Buscar");
        btnMensajeAmigoChat= new JButton("Mensaje");
        btnEliminarAmigoChat= new JButton("Eliminar");
        txtBuscarAmigosChat=new JTextField();
        panelListaAmigosChat=new JPanel();
        scrAmigosConectadosChat = new JScrollPane(panelListaAmigosChat);
        scrAmigosConectadosChat.getVerticalScrollBar().setUnitIncrement(10);
        panelListaAmigosChat.setLayout(new BoxLayout(panelListaAmigosChat,X_AXIS));
        scrAmigosConectadosChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrAmigosConectadosChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
 
        tabAmigosChat=new JTabbedPane();
       
        panelListaAmigosChat.add(Box.createHorizontalStrut(5));
        panelListaAmigosChat.add(new JLabel(Amigo));
        panelListaAmigosChat.add(Box.createHorizontalStrut(20));
        panelListaAmigosChat.add(btnMensajeAmigoChat);
        panelListaAmigosChat.add(Box.createHorizontalStrut(10));
        panelListaAmigosChat.add(btnEliminarAmigoChat);
       
        JPanel panel2=new JPanel();
        
        GroupLayout grupoAmigosConectadosChat = new GroupLayout(panel2);
        grupoAmigosConectadosChat.setAutoCreateContainerGaps(true);
        grupoAmigosConectadosChat.setAutoCreateGaps(true);
     
        grupoAmigosConectadosChat.setHorizontalGroup
        (
            grupoAmigosConectadosChat.createSequentialGroup()
            .addComponent(scrAmigosConectadosChat)
        );
        
        grupoAmigosConectadosChat.setVerticalGroup
        (
            grupoAmigosConectadosChat.createParallelGroup()
            .addComponent(scrAmigosConectadosChat)
        );
        panel2.setLayout(grupoAmigosConectadosChat);
        
        tabAmigosChat.addTab("Conectado", panel2);
        JPanel panel3=new JPanel();
        tabAmigosChat.addTab("No Conectado", panel3);
        
        panelAmigosChat=new JPanel();
        panelAmigosChat.setBorder(new LineBorder(Color.BLUE));
        
        GroupLayout grupoAmigosChat = new GroupLayout(panelAmigosChat);
        grupoAmigosChat.setAutoCreateContainerGaps(true);
        grupoAmigosChat.setAutoCreateGaps(true);
     
        grupoAmigosChat.setHorizontalGroup
        (
            grupoAmigosChat.createParallelGroup()
            .addGroup
            (
                grupoAmigosChat.createSequentialGroup()
                .addComponent(lblAmigosChat)
                .addComponent(txtBuscarAmigosChat)
                .addComponent(btnBuscarAmigosChat)
            )
            .addComponent(tabAmigosChat)
        );
        
        grupoAmigosChat.setVerticalGroup
        (
            grupoAmigosChat.createSequentialGroup()
            .addGroup
            (
                grupoAmigosChat.createParallelGroup()
                .addComponent(lblAmigosChat,25,25,25)
                .addComponent(txtBuscarAmigosChat,30,30,30)
                .addComponent(btnBuscarAmigosChat,30,30,30)
            )
            .addComponent(tabAmigosChat,150,150,150)
        );
        panelAmigosChat.setLayout(grupoAmigosChat);
        
        //-----------------PANEL DE GRUPOS----------------
        lblGruposChat = new JLabel("GRUPOS");
        btnCrearGruposChat = new JButton("Crear Grupo");
        btnMensajeGruposChat=new JButton("Mensaje");
        btnModificarGruposChat=new JButton("Modificar");
        btnSalirGruposChat=new JButton("Salir");
        btnEliminarGruposChat=new JButton("Eliminar");
        panelListaGruposChat = new JPanel();
        scrListaGruposChat = new JScrollPane(panelListaGruposChat);
        scrListaGruposChat.getVerticalScrollBar().setUnitIncrement(10);
        panelListaGruposChat.setLayout(new BoxLayout(panelListaGruposChat,X_AXIS));
        scrListaGruposChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrListaGruposChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       
        panelListaGruposChat.add(Box.createHorizontalStrut(5));    
        panelListaGruposChat.add(new JLabel(Grupo+"   "));
        panelListaGruposChat.add(Box.createHorizontalStrut(20));
        panelListaGruposChat.add(btnMensajeGruposChat);
        panelListaGruposChat.add(Box.createHorizontalStrut(10));
        panelListaGruposChat.add(btnModificarGruposChat);
        panelListaGruposChat.add(Box.createHorizontalStrut(10));
        panelListaGruposChat.add(btnSalirGruposChat);
        panelListaGruposChat.add(Box.createHorizontalStrut(10));
        panelListaGruposChat.add(btnEliminarGruposChat); 
          
        panelGruposChat=new JPanel();
        panelGruposChat.setBorder(new LineBorder(Color.BLUE));
       
        GroupLayout grupoGruposChat = new GroupLayout(panelGruposChat);
        grupoGruposChat.setAutoCreateContainerGaps(true);
        grupoGruposChat.setAutoCreateGaps(true);
     
        grupoGruposChat.setHorizontalGroup
        (
            grupoGruposChat.createParallelGroup()
            .addGroup
            (
                grupoGruposChat.createSequentialGroup()
                .addComponent(lblGruposChat)
                .addComponent(btnCrearGruposChat)
            )
            .addComponent(scrListaGruposChat)
        );
        
        grupoGruposChat.setVerticalGroup
        (
            grupoGruposChat.createSequentialGroup()
            .addGroup
            (
                grupoGruposChat.createParallelGroup()
                .addComponent(lblGruposChat,25,25,25)
                .addComponent(btnCrearGruposChat)
            )
            .addComponent(scrListaGruposChat,150,150,150)
        );
        panelGruposChat.setLayout(grupoGruposChat);
        
        //--------------ACOMODO DEL PANEL GENERAL-------------------
        GroupLayout contentPane = new GroupLayout(this.getContentPane());
        contentPane.setAutoCreateContainerGaps(true);
        contentPane.setAutoCreateGaps(true);
        contentPane.setHorizontalGroup //Columnas
        (     
            contentPane.createParallelGroup(CENTER)
            .addComponent(lblCoconversa)
            .addGroup
            (
                contentPane.createSequentialGroup()
                .addComponent(tabMensajesChat)  
                .addGroup
                (
                    contentPane.createParallelGroup()
                    .addComponent(pnlUsuarioBoton,500,500,500)
                    .addComponent(panelAmigosChat,500,500,500)
                    .addComponent(panelGruposChat,500,500,500)
                )
            )          
        );
        contentPane.setVerticalGroup //Filas
        (
            contentPane.createSequentialGroup()
            .addComponent(lblCoconversa,30,30,30)
            .addGroup
            (
                contentPane.createParallelGroup()
                .addComponent(tabMensajesChat)
                .addGroup
                (
                    contentPane.createSequentialGroup()      
                    .addComponent(pnlUsuarioBoton)
                    .addComponent(panelAmigosChat,200,200,200)
                    .addComponent(panelGruposChat,200,200,200)
                )
            )      
        );
        this.setLayout(contentPane);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
         if(ae.getSource()== btnSalirChat){
             this.setVisible(false);
             System.exit(0);
         }
    }
}