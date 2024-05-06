import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;

public class VentanaJornadas extends JFrame implements FocusListener, ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblLog;
    private JButton btnAtras;
    private JLabel lblTemporada;
	private JTabbedPane tabbedPane;
	private JPanel panelJornada;
	private JLabel lblJornada;
	private JLabel escudoLocal;
	private JLabel escudoVisit;	
	private JLabel lblLineaResultado;	
	private JLabel lblPtsLocal;
	private JLabel lblPtsVisit;
	private JButton btnEditar;
	List<Jornada> listaJornadasJ= new ArrayList<Jornada>();
	List<Partido> listaPartidosJ= new ArrayList<Partido>();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaJornadas frame = new VentanaJornadas();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public VentanaJornadas() {

        //establecemos título e icono de la aplicación
        setTitle("Real Federación EspaÑola de Balonmano");
        setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaJornadas.class.getResource("img/logo.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //ubicación y tamaño de la ventana
        setBounds(100, 100, 650, 600);
        setLocationRelativeTo(null);

        //quita el redimensionado de la ventana
        setResizable(false);

        //creamos dos paneles distintos, uno para el menú de los equipos y otro para el apartado del equipo seleccionado y ponemos por defecto el panel del menú
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setForeground(new Color(0, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        // asignamos el panel del menú como predeterminado
        setContentPane(contentPane);
       
      	//Creamos y añadimos el tabbedpane para poder añadir las jornadas en él
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFont(new Font("Dialog", Font.PLAIN, 10));
		tabbedPane.setBackground(Color.WHITE);
		UIManager.put("TabbedPane.selected", Color.WHITE);
		UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
		tabbedPane.setFocusable(false);
		tabbedPane.setBounds(-1, 10, 638, 480);
		contentPane.add(tabbedPane);
        
		//llamamos a la función que recoge todas las jornadas y las añade al tabbed panel
        mostrarJornadas();

        //creamos y añadimos un Jlabel para marcar la temporada
        lblTemporada = new JLabel();
        contentPane.add(lblTemporada);

        //propiedades del JLabel
        lblTemporada.setForeground(new Color(0, 0, 0));
        lblTemporada.setFont(new Font("Arial Black", Font.PLAIN, 20));
        lblTemporada.setBounds(260, 85, 115, 23);
        lblTemporada.setHorizontalAlignment(SwingConstants.CENTER);
        lblTemporada.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

        //Asignamos la temporada 
        lblTemporada.setText("" + VentanaTemporadas.temporadaSeleccionada.getFecha());
        
        //creamos y añadimos un JLabel para mostrar con qué usuario ha iniciado sesión
      	lblLog = new JLabel("");
      	contentPane.add(lblLog);
      		
      	//propiedades del JLabel
      	lblLog.setForeground(new Color (0,0,0));
      	lblLog.setFont(new Font("Arial", Font.PLAIN, 14));
      	lblLog.setBounds(10, 15, 450, 13);
      	lblLog.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
      		
      	//añadimos el texto que queremos a la JLabel
      	lblLog.setText("Has iniciado sesión como: " + VentanaRegistro.getNombre() + ".");
        
        
        //creamos y añadimos un botón para volver al inicio
        btnAtras = new JButton("");
        contentPane.add(btnAtras);

        //propiedades del JButton
        btnAtras.setBackground(null);
        btnAtras.setIcon(new ImageIcon("src/img/atras.png"));
        btnAtras.setBorder(null);
        btnAtras.setBounds(576, 517, 30, 30);

        //añadimos los listeners necesarios
        btnAtras.addFocusListener(this);
        btnAtras.addActionListener(this);
        btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAtras.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                //cuando se pulsa el ratón encima cerramos la ventana actual y volvemos a la ventana de inicio

                VentanaInicio vi = new VentanaInicio();
                vi.setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                //cuando de pasa el ratón por encima
                btnAtras.setBackground(new Color(212, 212, 212));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //Cuando el raton no esta por encima
                btnAtras.setBackground(null);
            }

        });

        //creamos y añadimos un botón para ir a la ventana de resultados donde editaremos estos mismos
        btnEditar = new JButton("Editar resultados");
        contentPane.add(btnEditar);

        //propiedades del JButton
        btnEditar.setBackground(new Color(192,192,192));
        btnEditar.setBorder(null);
        btnEditar.setBounds(20, 517, 150, 30);
        btnEditar.setFont(new Font("Arial Black", Font.PLAIN, 12));

        //añadimos los listeners necesarios
        btnEditar.addFocusListener(this);
        btnEditar.addActionListener(this);
        btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEditar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                //cuando se pulsa el ratón encima cerramos la ventana actual y volvemos a la ventana de inicio

                VentanaResultados vr = new VentanaResultados();
                vr.setVisible(true);
        		vr.setLocation(100, 100);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                //cuando de pasa el ratón por encima
                btnEditar.setBackground(new Color(212, 212, 212));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //Cuando el raton no esta por encima
                btnEditar.setBackground(new Color(192,192,192));
            }

        });
        
        //hacemos una comprobación de si el usuario que ha iniciado sesion es admin o no para dar la opcion de editar los resultados o no
        if (!VentanaRegistro.NewReg.getPermisos().equals("Admin")) {
        	
        	btnEditar.setVisible(false);
        	
        }
        
        else {
        	
        	btnEditar.setVisible(true);
        	
        }
    }

    @Override
    public void focusGained(FocusEvent fe) {

        //Se cambia el borde a los objetos que estás seleccionando
        Object o = fe.getSource();
        ((JComponent) o).setBorder(new LineBorder(new Color(150, 200, 240), 2));

    }

    @Override
    public void focusLost(FocusEvent fe) {

        //Se pone el borde por defecto a los objetos que no estás seleccionando
        Object o = fe.getSource();
        ((JComponent) o).setBorder(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void mostrarJornadas() {
    	
    	obtenerJornadas();
    	
    	//Recorre todas las jornadas
    	for (Jornada jornada : listaJornadasJ) {
    				
    		//Panel de la Jornada
    		panelJornada = new JPanel();
    		tabbedPane.addTab("Jornada " + jornada.getNumero(), null, panelJornada, null);
    		panelJornada.setBackground(Color.WHITE);
    		panelJornada.setLayout(null);
    				
    		//Titulo de la Jornada
    		lblJornada = new JLabel("Jornada " + jornada.getNumero());
    		lblJornada.setHorizontalAlignment(SwingConstants.CENTER);
    		lblJornada.setForeground(new Color(105,105,105));
    		lblJornada.setFont(new Font("Dialog", Font.BOLD, 35));
    		lblJornada.setBounds(218, 10, 195, 48);
    		panelJornada.add(lblJornada);			

    		//Coordenadas para ir posicionando los resultados
    		int resultadoY = 94;
    		int escudoLocalX = 73;
    		int escudoVisitX = 457;
    		int guionX = 296;
    		int setsLocalX = 217;
    		int setsVisitX = 375;
    				
    		//Recorre los resultados de la jornada
    		for (Partido partido: jornada.getListaPartidos()) {
    			
    			//Escudo del equipo local
    			escudoLocal = new JLabel("");
    			escudoLocal.setBorder(null);
    			escudoLocal.setBounds(escudoLocalX, resultadoY, 100, 100);
    			escudoLocal.setIcon(new ImageIcon("src/img/"+partido.getEquipoLocal().getImagenEscudo()));
    			panelJornada.add(escudoLocal);
    					
    			//Goles del equipo local
    			lblPtsLocal = new JLabel(""+partido.getPtsLocal());
    			lblPtsLocal.setHorizontalAlignment(SwingConstants.CENTER);
    			lblPtsLocal.setForeground(UIManager.getColor("Button.darkShadow"));
    			lblPtsLocal.setFont(new Font("Dialog", Font.BOLD, 35));
    			lblPtsLocal.setBounds(setsLocalX, resultadoY, 38, 100);
    			panelJornada.add(lblPtsLocal);
    					
    			//Guion del resultado
    			lblLineaResultado = new JLabel("-");
    			lblLineaResultado.setHorizontalAlignment(SwingConstants.CENTER);
    			lblLineaResultado.setForeground(UIManager.getColor("Button.darkShadow"));
    			lblLineaResultado.setFont(new Font("Dialog", Font.BOLD, 35));
    			lblLineaResultado.setBounds(guionX, resultadoY, 38, 100);
    			panelJornada.add(lblLineaResultado);
    					
    			//Goles del equipo visitante
    			lblPtsVisit = new JLabel(""+partido.getPtsVisit());
    			lblPtsVisit.setHorizontalAlignment(SwingConstants.CENTER);
    			lblPtsVisit.setForeground(UIManager.getColor("Button.darkShadow"));
    			lblPtsVisit.setFont(new Font("Dialog", Font.BOLD, 35));
    			lblPtsVisit.setBounds(setsVisitX, resultadoY, 38, 100);
    			panelJornada.add(lblPtsVisit);
    					
    			//Escudo del equipo visitante
    			escudoVisit = new JLabel("");
    			escudoVisit.setBorder(null);
    			escudoVisit.setBounds(escudoVisitX, resultadoY, 100, 100);
    			escudoVisit.setIcon(new ImageIcon("src/img/"+partido.getEquipoVisit().getImagenEscudo()));
    			panelJornada.add(escudoVisit);
    									
    			//Añade más distancia para que se vayan dibujando hacia abajo
    			resultadoY += 113;
    		}
    	}
    	
    }
    
    public void obtenerJornadas() {
    	
    	try {
			
    		//CONSULTA PARA COGER LAS JORNADAS Y SUS PARTIDOS
    			
    		//me conecto a la base de datos como root
    		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");
    		
    		//creo el Statement para coger los equipos
    		Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		
    		//como es una query, creo un objeto ResultSet 
    		ResultSet rs = st.executeQuery("SELECT * FROM balonmano.jornadas WHERE Num_Temp="+VentanaTemporadas.temporadaSeleccionada.getFecha()+";");
    		
    		listaJornadasJ = new ArrayList<Jornada>();
    		
    		int i = 0;
    		
    		while (rs.next()) {
    			
    			//creo variables de todos los resultados por cada jornada para poder manipular los datos mejor
    			int ID = Integer.parseInt(rs.getString("ID_Jornada"));
    			int Temporada = Integer.parseInt(rs.getString("Num_Temp"));
    			i++;
    			listaPartidosJ = new ArrayList<Partido>();
    			
    			//CONSULTA PARA RECOGER LOS DATOS DE TODOS LOS PARTIDOS DE LA JORNADA ACTUAL
    			Statement st2 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    			ResultSet rs2 = st2.executeQuery("SELECT * FROM balonmano.partidos WHERE ID_Jornada="+ID+";");
    			
    			
    			while (rs2.next()) {
    				
    				int idP = Integer.parseInt(rs2.getString("cod_partido"));
    				Equipo EL = new Equipo (rs2.getString("nom_equipo_loc"));
    				int GL = Integer.parseInt(rs2.getString("goles_equipo_loc"));
    				Equipo EV = new Equipo (rs2.getString("nom_equipo_vis"));
    				int GV = Integer.parseInt(rs2.getString("goles_equipo_vis"));
    				
    				//CONSULTA PARA RECOGER EL ESCUDO DEL EQUIPO LOCAL DEL PARTIDO ACTUAL
        			Statement st3 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        			ResultSet rs3 = st3.executeQuery("SELECT * FROM balonmano.equipos WHERE Num_Temp= "+Temporada+" AND Nom_Equipo='"+EL.getNombre()+"';");
        			
        			while (rs3.next()) {
        				
        				EL.setImagenEscudo(rs3.getString("Escudo"));
        				
        			}
        			
        			//CONSULTA PARA RECOGER EL ESCUDO DEL EQUIPO VISITANTE DEL PARTIDO ACTUAL
        			rs3 = st3.executeQuery("SELECT * FROM balonmano.equipos WHERE Num_Temp= "+Temporada+" AND Nom_Equipo='"+EV.getNombre()+"';");
        			
        			while (rs3.next()) {
        				
        				EV.setImagenEscudo(rs3.getString("Escudo"));
        				
        			}
    				
    				Partido p = new Partido (idP, EL, GL, EV, GV);
    				listaPartidosJ.add(p);
    				
    			}
    			
    			rs2.close();
    			st2.close();
    			
    			
    			// creo una nueva jornada por cada registro
    			Jornada j = new Jornada (ID,i,listaPartidosJ);

    			
    			//lo añado a la lista donde están todas las jornadas
    			
    			listaJornadasJ.add(j);
    		
    		}
    		
    		rs.close();
    		st.close();
    		
    		}
    		
    		catch (SQLException e) {
    			// si se produce una excepción SQL
    			int errorcode = e.getErrorCode();
    			
    			//si se produce cualquier error sql
    			 System.out.println("Error SQL Numero "+e.getErrorCode()+":"+e.getMessage());
    			
    			
    		}
    	
    }
    
    
}
