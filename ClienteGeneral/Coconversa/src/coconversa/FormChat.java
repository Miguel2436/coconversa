package coconversa;

import clientes.ClienteHiloEscritura;
import datos.Mensaje;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.X_AXIS;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.LineBorder;

public class FormChat extends JFrame implements ActionListener
{
    public List<chat> ChatsAbiertos; 
    //---------PANEL CONVERSACIÓN----------------------------------------------
    private JTextField txtMensajesChat;
    private JButton btnEnviarChat,btnSalirChat;
    private JTabbedPane tabMensajesChat;
    private JPanel panelConversacionChat,panelScrConversacionChat;
    private JList listaConversacionChat;
     private JList listaConversacionChat2;
    private JScrollPane scrConversacionChat;
    
    // Esto de abajo lo añadió Alan gg //
    //////////////////////////////////////////////
    public JLabel lblCoconversa, lblUsuarioChat;
    ////////////////////////////////////////////
    private JPanel pnlUsuarioBoton;
    
    //-----PANEL AMIGOS-------------------------------------------------------
    private JTextField txtBuscarAmigosChat;
    private JButton btnBuscarAmigosChat,btnMensajeAmigoChat,btnEliminarAmigoConectadoChat,btnEliminarAmigoDesconectadoChat;
    private JTabbedPane tabAmigosChat;
    private JPanel panelAmigosChat,panelListaAmigosConectadosChat,panelListaAmigosDesconectadosChat;
    private JScrollPane scrAmigosConectadosChat,scrAmigosDesconectadosChat;
    private JLabel lblAmigosChat,lblAmigosConectadosChat,lblAmigosDesconectadosChat;
    public   DefaultListModel listModel = new DefaultListModel();
    public   DefaultListModel listModel2 = new DefaultListModel();
    private JList listaAmigosConectadosChat,listaAmigosDesconectadosChat;
    
    //------PANEL GRUPOS------------------------------------------------------
    private JTabbedPane tabGruposChat;
    private JButton btnCrearGruposChat,btnModificarGruposChat,btnEliminarGruposChat,btnSalirGruposChat,btnMensajeGruposChat;
    private JPanel panelGruposChat,panelMostrarGruposChat;
    private JScrollPane scrGruposChat;
    private JLabel lblGruposChat,lblNombreGrupoChat;
    private JPanel panelScrollGruposChat;
    
    private JPanel panelListaGruposChat;
    private JScrollPane scrListaGruposChat;
            
    public String Usuario;
    
    
    public FormChat(String NombreUsuario)
    {
        Usuario = NombreUsuario;
        configurar();
        componentes();
    }
    
    public void configurar()
    {
        ChatsAbiertos = new ArrayList(); 
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
        btnSalirChat.addActionListener(this);
        
        pnlUsuarioBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 3));
        pnlUsuarioBoton.setBorder(new LineBorder(Color.BLACK));
        pnlUsuarioBoton.add(lblUsuarioChat);
        pnlUsuarioBoton.add(btnSalirChat);
       
        tabMensajesChat=new JTabbedPane();
        panelConversacionChat=new JPanel();
        //listaConversacionChat=new JList(arregConversacionChat);
        //getContentPane().add(tabMensajesChat);
        txtMensajesChat = new JTextField();
        btnEnviarChat = new JButton("Enviar");
        
        scrConversacionChat = new JScrollPane(listaConversacionChat);
        scrConversacionChat.getVerticalScrollBar().setUnitIncrement(10);
        scrConversacionChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrConversacionChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       
        
      /*
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
        );*/
        //////////////////////////////////
        //tabMensajesChat.addTab(Amigo, panelConversacionChat);
      

        /////////////////////////////////////////////////////////////////////////////
        //----------PANEL DE AMIGOS-----------------
        lblAmigosChat=new JLabel("Amigos");
//        lblAmigosConectadosChat=new JLabel(AmigosConectados);
        btnBuscarAmigosChat= new JButton("Buscar");
        btnMensajeAmigoChat= new JButton("Mensaje");
        btnEliminarAmigoConectadoChat= new JButton("Eliminar");
        btnEliminarAmigoConectadoChat.addActionListener(this);
        btnEliminarAmigoDesconectadoChat= new JButton("Eliminar");
        btnEliminarAmigoDesconectadoChat.addActionListener(this);
        txtBuscarAmigosChat=new JTextField();
        
        //-------------AMIGOS CONECTADOS-----------------------------------------------------
        //BORRAR
        listModel.addElement("Dogo");
        listModel.addElement("Jose");
         listaAmigosConectadosChat= new JList(listModel);
        panelListaAmigosConectadosChat=new JPanel();
        scrAmigosConectadosChat = new JScrollPane(panelListaAmigosConectadosChat);
        scrAmigosConectadosChat.getVerticalScrollBar().setUnitIncrement(10);
        panelListaAmigosConectadosChat.setLayout(new BoxLayout(panelListaAmigosConectadosChat,X_AXIS));
        scrAmigosConectadosChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrAmigosConectadosChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        //---------------------AMIGOS DESCONECTADOS----------------------------------------------------------
        listaAmigosDesconectadosChat= new JList(listModel2);
        panelListaAmigosDesconectadosChat=new JPanel();
        scrAmigosDesconectadosChat = new JScrollPane(panelListaAmigosDesconectadosChat);
        scrAmigosDesconectadosChat.getVerticalScrollBar().setUnitIncrement(10);
        panelListaAmigosDesconectadosChat.setLayout(new BoxLayout(panelListaAmigosDesconectadosChat,X_AXIS));
        scrAmigosDesconectadosChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrAmigosDesconectadosChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
 
        tabAmigosChat=new JTabbedPane();
       
        //-------------------------AMIGOS CONECTADOS-----------------------------------------------------
        GroupLayout listasAmigosConectadosChat = new GroupLayout(panelListaAmigosConectadosChat);
        btnMensajeAmigoChat.addActionListener(this);
        listasAmigosConectadosChat.setAutoCreateContainerGaps(true);
        listasAmigosConectadosChat.setAutoCreateGaps(true);
        listasAmigosConectadosChat.setHorizontalGroup
        (
            listasAmigosConectadosChat.createParallelGroup()
                    .addGroup
            (
                listasAmigosConectadosChat.createSequentialGroup()
                .addComponent(btnMensajeAmigoChat)
                .addComponent(btnEliminarAmigoConectadoChat)
            )
            .addComponent(listaAmigosConectadosChat)
        );
        
        listasAmigosConectadosChat.setVerticalGroup
        (
            listasAmigosConectadosChat.createSequentialGroup()
                    .addGroup
            (
                listasAmigosConectadosChat.createParallelGroup()
                .addComponent(btnMensajeAmigoChat)
                .addComponent(btnEliminarAmigoConectadoChat)
            )
            .addComponent(listaAmigosConectadosChat)
        );
        panelListaAmigosConectadosChat.setLayout(listasAmigosConectadosChat);
        
       
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
        
        //-------------------------AMIGOS DESCONECTADOS----------------------------------------
        JPanel panel3=new JPanel();
        GroupLayout listasAmigosDesconectadosChat = new GroupLayout(panelListaAmigosDesconectadosChat);
        listasAmigosDesconectadosChat.setAutoCreateContainerGaps(true);
        listasAmigosDesconectadosChat.setAutoCreateGaps(true);
        listasAmigosDesconectadosChat.setHorizontalGroup
        (
            listasAmigosDesconectadosChat.createParallelGroup()
                    .addGroup
            (
                listasAmigosDesconectadosChat.createSequentialGroup()
                .addComponent(btnEliminarAmigoDesconectadoChat)
            )
            .addComponent(listaAmigosDesconectadosChat)
        );
        
        listasAmigosDesconectadosChat.setVerticalGroup
        (
            listasAmigosDesconectadosChat.createSequentialGroup()
                    .addGroup
            (
                listasAmigosDesconectadosChat.createParallelGroup()
                .addComponent(btnEliminarAmigoDesconectadoChat)
            )
            .addComponent(listaAmigosDesconectadosChat)
        );
        panelListaAmigosDesconectadosChat.setLayout(listasAmigosDesconectadosChat);
        
       
        GroupLayout grupoAmigosDesconectadosChat = new GroupLayout(panel3);
        grupoAmigosDesconectadosChat.setAutoCreateContainerGaps(true);
        grupoAmigosDesconectadosChat.setAutoCreateGaps(true);
     
        grupoAmigosDesconectadosChat.setHorizontalGroup
        (
            grupoAmigosDesconectadosChat.createSequentialGroup()
            .addComponent(scrAmigosDesconectadosChat)
        );
        
        grupoAmigosDesconectadosChat.setVerticalGroup
        (
            grupoAmigosDesconectadosChat.createParallelGroup()
            .addComponent(scrAmigosDesconectadosChat)
        );
        panel3.setLayout(grupoAmigosDesconectadosChat);
        
        tabAmigosChat.addTab("Conectado", panel2);
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
//        panelListaGruposChat.add(new JLabel(Grupo+"   "));
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
    public void chatCreador(String Amigo){
        chat michat = new chat(Amigo);
        ChatsAbiertos.add(michat);
        tabMensajesChat.addTab(michat.nombre, michat.getPanel());
        //michat.AreaChat.append("\n"+Mensaje);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
         if(ae.getSource()== btnSalirChat ){
            /*this.setVisible(false);
            System.exit(0);*/
         }
          if(ae.getSource()== btnBuscarAmigosChat){
             ClienteHiloEscritura CB = new ClienteHiloEscritura();
             String AmigoB= txtBuscarAmigosChat.getText();
             CB.existeUsuario(AmigoB);
        
         }
         if(ae.getSource()==btnEliminarAmigoConectadoChat)
         {
            // System.out.println("Holaaaa");
            if(listaAmigosConectadosChat.getSelectedValue()!=null)
            {
                
                ClienteHiloEscritura CB = new ClienteHiloEscritura();
                CB.eliminarAmigo(lblUsuarioChat.getText(), listaAmigosConectadosChat.getSelectedValue().toString());
            }else
            {
                FormErrorGeneral FEG= new FormErrorGeneral("Seleccione un amigo");
                FEG.setVisible(true);
            }
             
         }
         if(ae.getSource()==btnEliminarAmigoDesconectadoChat)
         {
            if(listaAmigosDesconectadosChat.getSelectedValue()!=null)
            {
                ClienteHiloEscritura CB = new ClienteHiloEscritura();
                CB.eliminarAmigo(lblUsuarioChat.getText(), listaAmigosDesconectadosChat.getSelectedValue().toString());
            }else
            {
                FormErrorGeneral FEG= new FormErrorGeneral("Seleccione un amigo");
                FEG.setVisible(true);
            }
             
         }
         if(ae.getSource() == btnMensajeAmigoChat){
           if(listaAmigosConectadosChat.getSelectedValue()!=null){
             chat michat = new chat(listaAmigosConectadosChat.getSelectedValue().toString());
             ChatsAbiertos.add(michat);
             tabMensajesChat.addTab(michat.nombre, michat.getPanel());  
              ClienteHiloEscritura pedirmensajes = new ClienteHiloEscritura();
              pedirmensajes.getMensajes(Usuario,michat.nombre);
           }else{
               FormErrorGeneral FEG= new FormErrorGeneral("Seleccione un amigo para chatear");
               FEG.setVisible(true);
           }
             
         }
    }
public  class chat{
    public JScrollPane scrConversacionChat;
    public JButton btnEnviar;
    JTextField txtMensajes;
    public String nombre;
    public JTextArea AreaChat;
   
    chat(String nombre){
        this.nombre=nombre;
    }
    
    public JPanel getPanel(){
         //Genra acomodo de componentes
        AreaChat = new JTextArea();
        AreaChat.setEditable(false);
        /*AreaChat.append("Hola");
        
        AreaChat.append("\nGraduado");*/
        
        txtMensajes = new JTextField();
        btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String Mensaje = txtMensajes.getText();
                if(Mensaje.toString().equals("")){
                 FormErrorGeneral error = new FormErrorGeneral("Escribe un mensaje antes de enviarlo");
                 error.setVisible(true);
                }else{
                    ClienteHiloEscritura envio = new ClienteHiloEscritura();
                    //envio.enviarMensajeChat(nombre,Usuario,Mensaje);
                    
                    AreaChat.append("\n" +"[" +Time.from(Instant.now())+"] "+Usuario +" : "+ txtMensajes.getText());
                    txtMensajes.setText("");
                    
                }
            }
        });
        scrConversacionChat = new JScrollPane(AreaChat);
        scrConversacionChat.getVerticalScrollBar().setUnitIncrement(10);
        scrConversacionChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrConversacionChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        
        
        JPanel panelConversacionChat2=new JPanel();
        
        GroupLayout pt2 = new GroupLayout(panelConversacionChat2);
        pt2.setAutoCreateContainerGaps(true);
        pt2.setAutoCreateGaps(true);
        panelConversacionChat2.setLayout(pt2);
        pt2.setHorizontalGroup
        (
            pt2.createParallelGroup()
            .addComponent(scrConversacionChat,530,530,530)
            .addGroup(
                pt2.createSequentialGroup()
                .addComponent(txtMensajes)
                .addComponent(btnEnviar)
            )
        );
        pt2.setVerticalGroup
        (
            pt2.createSequentialGroup()
            .addComponent(scrConversacionChat,300,300,300)
            .addGroup
            (
                pt2.createParallelGroup()
                .addComponent(txtMensajes,30,30,30)
                .addComponent(btnEnviar,30,30,30)
            )
        );
        return panelConversacionChat2;
    }
   
};
}
