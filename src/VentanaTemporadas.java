import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class VentanaTemporadas extends JFrame implements FocusListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTemporadas;
	private JLabel lblInfo;
	private JLabel lblInfoEquipos;
	private JLabel lblInfoTemporada;
	private JLabel lblLog;
	private JLabel lblPanelEquipos;
	private JLabel lblPanelEquipos2;
	private JButton btnAtras;
	private JTextField txtTemporada;
	private JButton btnAñadir;
	private JButton btnSiguiente;
	private JButton btnBorrar;
	private JButton btnDerecha;
	private JButton btnIzquierda;
	private JButton btnUsuarios;
	private JButton btnEquipos;
	private JButton btnJugadores;
	private JButton btnXML;
	
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JList<String> JListEquipos;
	private DefaultListModel<String> dlmListaEquipos = new DefaultListModel<>();
	private JList<String> JListEquiposSeleccionados;
	private DefaultListModel<String> dlmListaSeleccionados = new DefaultListModel<>();
	private List<Temporada> listaTemporadas;
	private JList<String> JlistTemporadas;
	public static Temporada temporadaSeleccionada;
	private DefaultListModel<String> dlmListaTemporadas = new DefaultListModel<>();
	public List<Equipo> listaEquipos;
	public List<Equipo> listaEquiposSeleccionados = new ArrayList<>();
	

	private Connection conexion;
	private Statement st;
	private ResultSet rs;
	
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTemporadas frame = new VentanaTemporadas();
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
	public VentanaTemporadas() {
		
		//establecemos título e icono de la aplicación
		setTitle("Real Federación EspaÑola de Balonmano");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaTemporadas.class.getResource("/img/Logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		

		/*-----------------------------------------------BASE DE DATOS MYSQL---------------------------------------------------------------------*/
		
		//me intento conectar a la base de datos mysql para coger los datos de temporadas y equipos de la base de datos mysql
		try {
			
			//me conecto a la base de datos como root
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");


			//CONSULTA PARA RECOGER LOS DATOS DE TODAS LAS TEMPORADAS
			//creo el Statement para coger las temporadas que haya en la base de datos
			Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			//como es una query, creo un objeto ResultSet 
			ResultSet rs = st.executeQuery("SELECT * FROM balonmano.temporadas;");

			listaTemporadas = new ArrayList<Temporada>();
			
			while (rs.next()) {
				
				if (!rs.getString("Num_Temp").equals("0")) {
					
				// creo una nueva tempoarada por cada registro
				String Fecha = rs.getString("Num_Temp");
				String Estado = rs.getString("Estado");
				List<Equipo> listaEquiposTemporadas = new ArrayList<Equipo>();
				
				//CONSULTA PARA RECOGER LOS DATOS DE TODOS LOS EQUIPOS DE LA TEMPORADA ACTUAL
				Statement st2 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs2 = st2.executeQuery("SELECT * FROM balonmano.equipos WHERE Num_Temp="+rs.getString("Num_Temp")+";");
				
				
				while (rs2.next()) {
					
					//creo variables de todos los resultados por cada equipo para poder manipular los datos mejor
					String Nombre = rs2.getString("Nom_Equipo");
					String Iniciales = Nombre.substring(0, Math.min(Nombre.length(), 3));
					int ID = Integer.parseInt(rs2.getString("ID_Equipo"));
					int Temporada = Integer.parseInt(rs2.getString("Num_Temp"));
					String Escudo = rs2.getString("Escudo");
					String Estadio = rs2.getString("Estadio");
					String Equipacion = rs2.getString("Equipacion");
					List<Jugador> listaJugadoresEquipo = new ArrayList<Jugador>();
					
					//CONSULTA PARA RECOGER LOS DATOS DE TODOS LOS JUGADORES DEL EQUIPO ACTUAL
					Statement st3 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet rs3 = st3.executeQuery("SELECT * FROM balonmano.jugadores WHERE ID_Equipo="+ID+";");
					
					
					while (rs3.next()) {
						
						int idJ = Integer.parseInt(rs3.getString("ID_Jugador"));
						String NombreJ = rs3.getString("Nombre");
						String Imagen = rs3.getString("Imagen");
						String Posicion = rs3.getString("Posicion");
						String Localidad = rs3.getString("Localidad");
						int nacimiento = Integer.parseInt(rs3.getString("Año_Nacimiento"));
						int idEquipo = ID;
						String capitan = rs3.getString("Capitan");
						
						Jugador j = new Jugador (idJ, NombreJ, Imagen, Posicion, Localidad, nacimiento, idEquipo, capitan);
						listaJugadoresEquipo.add(j);
						
					}
					
					rs3.close();
					st3.close();
					
					// creo un nuevo Equipo por cada registro
					Equipo e = new Equipo (Nombre,Iniciales,ID,Temporada,Escudo,Estadio,Equipacion, listaJugadoresEquipo);
					listaEquiposTemporadas.add(e);
					
					
					
				}
				
				rs2.close();
				st2.close();
				
				
				Temporada t = new Temporada (Fecha,Estado,listaEquiposTemporadas.get(0),listaEquiposTemporadas.get(1),listaEquiposTemporadas.get(2),listaEquiposTemporadas.get(3),listaEquiposTemporadas.get(4),listaEquiposTemporadas.get(5));
				
				//lo añado a la lista donde están todas las temporadas
				
				
				listaTemporadas.add(t);
				


				
				
				}
				
				
			}
			
			
			TemporadasBase();
			
			for (Equipo e : listaEquipos) {
				dlmListaEquipos.addElement(e.getNombre());
			}
			
			
			
			//comprobamos si la lista está vacía o si no existe y si lo está añadimos una temporada por defecto
			if (listaTemporadas.isEmpty()) {
				
				//inicializamos tanto la lista donde guardamos las temporadas como el defaultlistmodel
				listaTemporadas = new ArrayList<Temporada>();
				dlmListaTemporadas = new DefaultListModel<String>();
				
				//creamos una temporada por defecto y la añadimos a la lista
				Temporada t = new Temporada ("2023",new Equipo(listaEquipos.get(0)),new Equipo(listaEquipos.get(1)),new Equipo(listaEquipos.get(2)),new Equipo(listaEquipos.get(3)),new Equipo(listaEquipos.get(4)),new Equipo(listaEquipos.get(5)));
				añadirTemporada(t);
				
			}
			
			else {
			//recorremos la lista y vamos añadiendo todas las temporadas al defaultlistmodel
			for (Temporada t : listaTemporadas) {
				
				dlmListaTemporadas.addElement(t.getFecha()+" - "+t.getEstado());
				
			}
			}
			
			
		}
	
			catch (SQLException e) {
				// si se produce una excepción SQL
				int errorcode = e.getErrorCode();
				
				//si se produce cualquier error sql
				 System.out.println("Error SQL Numero "+e.getErrorCode()+":"+e.getMessage());
				
				
			}
		
		
		/*-------------------------------------------------------------------------------------------------------------------------------------*/


		//ubicación y tamaño de la ventana
		setBounds(100, 100, 650, 600);
		setLocationRelativeTo(null);
		
		//quita el redimensionado de la ventana
		setResizable(false);
		
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		//creamos y añadimos un panel que contendrá a todos los equipos para seleccionarlos a la hora de elegir los equipos
        JListEquipos = new JList<>(dlmListaEquipos);
        contentPane.add(JListEquipos);
        
        //propiedades del panel
        JListEquipos.setBackground(Color.WHITE);
        JListEquipos.setBorder(new LineBorder(new Color(0, 0, 0)));
        JListEquipos.setBounds(38, 400, 359, 137);
        JListEquipos.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
 
        

	       
        //creamos y añadimos un scrollPane donde pondremos el panel de los equipos disponibles
        scrollPane1 = new JScrollPane(JListEquipos);
	    contentPane.add(scrollPane1);
	    
	    //propiedades del scrollPanel
	    scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane1.setBounds(38, 400, 220, 137);
	  	
	  	
	  		
	  	//creamos y añadimos al scrollpanel un panel donde saldrán los equipos seleccionados
	  	JListEquiposSeleccionados = new JList<>(dlmListaSeleccionados);
	  	contentPane.add(JListEquiposSeleccionados);
	  		
	  	//propiedades del panel
	  	JListEquiposSeleccionados.setBorder(new LineBorder(new Color(0, 0, 0)));
	  	JListEquiposSeleccionados.setBackground(Color.WHITE);
	  	JListEquiposSeleccionados.setBounds(250, 400, 359, 137);
	  	JListEquiposSeleccionados.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
	    
	  	
	  	
	 	//creamos y añadimos un panel donde pondremos la lista de los equipos seleccionados
	  	scrollPane2 = new JScrollPane(JListEquiposSeleccionados);
	  	contentPane.add(scrollPane2);
	  		
	  	//propiedades del scrollPane
	  	scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	  	scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	  	scrollPane2.setBounds(333, 400, 220, 137);
		
		//creamos y añadimos un Jlabel para el título de inicio
		lblTemporadas= new JLabel("Temporadas");
		contentPane.add(lblTemporadas);
		
		//propiedades del JLabel
		lblTemporadas.setForeground(new Color(0, 0, 0));
		lblTemporadas.setFont(new Font("Arial Black", Font.BOLD, 30));
		lblTemporadas.setBounds(211, 60, 213, 30);
		lblTemporadas.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		
		
		//creamos y añadimos un Jlabel para marcar la temporada
		lblInfo = new JLabel("Selecciona la temporada a la cual quieres acceder.");
		contentPane.add(lblInfo);
		
		//propiedades del JLabel
		lblInfo.setForeground(new Color(0, 0, 0));
		lblInfo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblInfo.setBounds(38, 156, 343, 18);
		lblInfo.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		
		
		
		//creamos y añadimos un JLabel con información para el usuario para la inserción de equipos en una temporada
		lblInfoEquipos = new JLabel("Selecciona los equipos que competiran en la temporada (6 equipos).");
		contentPane.add(lblInfoEquipos);
		
		//propiedades del JLabel
		lblInfoEquipos.setForeground(new Color (0,0,0));
		lblInfoEquipos.setFont(new Font("Arial", Font.PLAIN, 15));
		lblInfoEquipos.setBounds(38, 355, 464, 18);
		
		
		
		//creamos y añadimos un JLabel con información de como introducir los datos para añadir una temporada nueva
		lblInfoTemporada = new JLabel ("Introduzca el año de comienzo de la temporada.");
		contentPane.add(lblInfoTemporada);
		
		//propiedades del JLabel
		lblInfoTemporada.setForeground(new Color (0,0,0));
		lblInfoTemporada.setFont(new Font("Arial", Font.PLAIN, 12));
		lblInfoTemporada.setBounds(290, 242, 359, 18);
		
	
		
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

		
		
		//creamos y añadimos un JTextFiel donde ponemos la temporada que queremos introducir
		txtTemporada = new JTextField();
		contentPane.add(txtTemporada);
		
		//propiedades del JTextField
		txtTemporada.setBounds(290, 270, 85, 19);
		txtTemporada.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null)); 
		txtTemporada.setColumns(10);
		
		//Añadimos los listeners necesarios
		txtTemporada.addFocusListener(this);
		txtTemporada.addActionListener(this);
		
		
		
		//Lista selección temporada
		JlistTemporadas = new JList<>(dlmListaTemporadas);
		JlistTemporadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JlistTemporadas.setFont(new Font("Arial", Font.BOLD, 14));
		JlistTemporadas.setBounds(38, 206, 200, 137);
		JlistTemporadas.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	    contentPane.add(JlistTemporadas);
	
	    
		
		//creamos y añadimos un botón para volver al inicio
		btnAtras = new JButton();
		contentPane.add(btnAtras);
		
		//propiedades del JButton
		btnAtras.setBackground(null);
		btnAtras.setBorder(null);
		btnAtras.setBounds(576, 517, 30, 30);
        btnAtras.setIcon(new ImageIcon("src/img/atras.png"));
		btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		//añadimos los listeners necesarios
		btnAtras.addFocusListener(this);
		btnAtras.addActionListener(this);
		btnAtras.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent me) {
				//cuando se pulsa el ratón encima cerramos la ventana actual y volvemos a la venta de registro
				
				VentanaRegistro vr = new VentanaRegistro();
				//String temporada = temporada
				vr.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnAtras.setBackground(new Color(212,212,212));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnAtras.setBackground(null);
			}
			
		});
		
		//Creamos y añadimos un botón para añadir una nueva temporada introducida en un JTextField
		btnAñadir = new JButton("Añadir");
		contentPane.add(btnAñadir);
		
		//Propiedades del JButton
		btnAñadir.setBounds(290, 210, 85, 21);
		btnAñadir.setForeground(new Color(0, 0, 0));
		btnAñadir.setBackground(new Color(192, 192, 192));
		btnAñadir.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnAñadir.setBorder(null);
		btnAñadir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		//Añadimos los listeners necesarios
		btnAñadir.addActionListener(this);
		btnAñadir.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnAñadir.setBackground(new Color(128,128,128));
				btnAñadir.setForeground(new Color (255, 255, 255));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnAñadir.setBackground(new Color (192, 192, 192));
				btnAñadir.setForeground(new Color (0,0,0));
			}
			
		});
		
		
		
		//Creamos y añadimos un botón que borre la temporada seleccionada en la lista temporadas
		btnBorrar = new JButton("Borrar");
		contentPane.add(btnBorrar);
		
		//Propiedades del JButton
		btnBorrar.setBounds(400, 210, 85, 21);
		btnBorrar.setForeground(new Color(0, 0, 0));
		btnBorrar.setBackground(new Color(192, 192, 192));
		btnBorrar.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnBorrar.setBorder(null);
		btnBorrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		//Añadimos los listeners necesarios
		btnBorrar.addActionListener(this); 
		btnBorrar.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnBorrar.setBackground(new Color(128,128,128));
				btnBorrar.setForeground(new Color (255,255,255));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnBorrar.setBackground(new Color (192, 192, 192));
				btnBorrar.setForeground(new Color (0,0,0));
			}
			
		});
				

	
		//creamos y añadimos un botón para acceder a la temporada siguiente
		btnSiguiente = new JButton ("Acceder a temporada");
		contentPane.add(btnSiguiente);
		
		//propiedades del JButton
		btnSiguiente.setBounds(290,300,269,45);
		btnSiguiente.setForeground(new Color(0, 0, 0));
		btnSiguiente.setBackground(new Color(192, 192, 192));
		btnSiguiente.setFont(new Font("Arial Black", Font.PLAIN, 20));
		btnSiguiente.setBorder(null);
		btnSiguiente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		//añadimos los listeners necesarios
		btnSiguiente.addActionListener(this);
		btnSiguiente.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnSiguiente.setBackground(new Color(128,128,128));
				btnSiguiente.setForeground(new Color (255,255,255));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnSiguiente.setBackground(new Color (192, 192, 192));
				btnSiguiente.setForeground(new Color (0,0,0));
			}
			
			
		});
		
		
		
		//creamos y añadimos un botón para mover los equipos seleccionados en la lista de la izquierda hacia la derecha
		btnDerecha = new JButton ("--->");
		contentPane.add(btnDerecha);
		
		//propiedades del JButton
		btnDerecha.setBounds(280, 430, 30, 20);
		btnDerecha.setForeground(new Color(0, 0, 0));
		btnDerecha.setBackground(new Color(192, 192, 192));
		btnDerecha.setFont(new Font("Arial Black", Font.BOLD, 12));
		btnDerecha.setBorder(null);
		btnDerecha.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		//añadimos los listeners necesarios
		btnDerecha.addActionListener(this);
		btnDerecha.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnDerecha.setBackground(new Color(128,128,128));
				btnDerecha.setForeground(new Color (255,255,255));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnDerecha.setBackground(new Color (192, 192, 192));
				btnDerecha.setForeground(new Color (0,0,0));
			}
			
			
		});
		
		
		//creamos y añadimos un botón para mover los equipos seleccionados en la lista de la derecha hacia la izquierda
		btnIzquierda = new JButton ("<---");
		contentPane.add(btnIzquierda);
		
		//propiedades del JButton
		btnIzquierda.setBounds(280, 480, 30, 20);
		btnIzquierda.setForeground(new Color(0, 0, 0));
		btnIzquierda.setBackground(new Color(192, 192, 192));
		btnIzquierda.setFont(new Font("Arial Black", Font.BOLD, 12));
		btnIzquierda.setBorder(null);
		btnIzquierda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		//añadimos los listeners necesarios
		btnIzquierda.addActionListener(this);
		btnIzquierda.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnIzquierda.setBackground(new Color(128,128,128));
				btnIzquierda.setForeground(new Color (255,255,255));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnIzquierda.setBackground(new Color (192, 192, 192));
				btnIzquierda.setForeground(new Color (0,0,0));
			}
					
					
		});
		

		//creamos y añadimos un botón para exportar los datos a XML
		btnXML = new JButton ("Exportar a XML");
		contentPane.add(btnXML);
				
		//propiedades del JButton
		btnXML.setBounds(10, 35, 100, 20);
		btnXML.setForeground(new Color(0, 0, 0));
		btnXML.setBackground(new Color(192, 192, 192));
		btnXML.setFont(new Font("Arial Black", Font.BOLD, 10));
		btnXML.setBorder(null);
		btnXML.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
		//añadimos los listeners necesarios
		btnXML.addActionListener(this);
		btnXML.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnXML.setBackground(new Color(128,128,128));
				btnXML.setForeground(new Color (255,255,255));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnXML.setBackground(new Color (192, 192, 192));
				btnXML.setForeground(new Color (0,0,0));
			}
		});
		
		
		
		//creamos y añadimos un botón para acceder a la administración de los usuarios
		btnUsuarios = new JButton ("Administrar usuarios");
		contentPane.add(btnUsuarios);
		
		//propiedades del JButton
		btnUsuarios.setBounds(475, 15, 150, 20);
		btnUsuarios.setForeground(new Color(0, 0, 0));
		btnUsuarios.setBackground(new Color(192, 192, 192));
		btnUsuarios.setFont(new Font("Arial Black", Font.BOLD, 10));
		btnUsuarios.setBorder(null);
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		//añadimos los listeners necesarios
		btnUsuarios.addActionListener(this);
		btnUsuarios.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnUsuarios.setBackground(new Color(128,128,128));
				btnUsuarios.setForeground(new Color (255,255,255));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnUsuarios.setBackground(new Color (192, 192, 192));
				btnUsuarios.setForeground(new Color (0,0,0));
			}
					
					
		});
		
		
		//creamos y añadimos un botón para acceder a la administración de los usuarios
		btnEquipos = new JButton ("Administrar equipos");
		contentPane.add(btnEquipos);
				
		//propiedades del JButton
		btnEquipos.setBounds(475, 55, 150, 20);
		btnEquipos.setForeground(new Color(0, 0, 0));
		btnEquipos.setBackground(new Color(192, 192, 192));
		btnEquipos.setFont(new Font("Arial Black", Font.BOLD, 10));
		btnEquipos.setBorder(null);
		btnEquipos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
		//añadimos los listeners necesarios
		btnEquipos.addActionListener(this);
		btnEquipos.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnEquipos.setBackground(new Color(128,128,128));
				btnEquipos.setForeground(new Color (255,255,255));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnEquipos.setBackground(new Color (192, 192, 192));
				btnEquipos.setForeground(new Color (0,0,0));
			}
					
					
		});
				
		
		
		//creamos y añadimos un botón para acceder a la administración de los usuarios
		btnJugadores = new JButton ("Administrar jugadores");
		contentPane.add(btnJugadores);
		
		//propiedades del JButton
		btnJugadores.setBounds(475, 95, 150, 20);
		btnJugadores.setForeground(new Color(0, 0, 0));
		btnJugadores.setBackground(new Color(192, 192, 192));
		btnJugadores.setFont(new Font("Arial Black", Font.BOLD, 10));
		btnJugadores.setBorder(null);
		btnJugadores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
		//añadimos los listeners necesarios
		btnJugadores.addActionListener(this);
		btnJugadores.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnJugadores.setBackground(new Color(128,128,128));
				btnJugadores.setForeground(new Color (255,255,255));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnJugadores.setBackground(new Color (192, 192, 192));
				btnJugadores.setForeground(new Color (0,0,0));
			}
							
							
		});
		
		//creamos un JLabel que indique los equipos
		lblPanelEquipos = new JLabel("Equipos disponibles.");
		contentPane.add(lblPanelEquipos);
		
		//propiedades del JLabel
		lblPanelEquipos.setForeground(Color.BLACK);
		lblPanelEquipos.setFont(new Font("Arial", Font.BOLD, 15));
		lblPanelEquipos.setBounds(38, 378, 160, 18);
		
		
		
		//creamos un JLabel que indique los equipos seleccionados
		lblPanelEquipos2 = new JLabel("Equipos seleccionados.");
		contentPane.add(lblPanelEquipos2);
		
		//propiedades del JLabel
		lblPanelEquipos2.setForeground(Color.BLACK);
		lblPanelEquipos2.setFont(new Font("Arial", Font.BOLD, 15));
		lblPanelEquipos2.setBounds(333, 378, 180, 18);
			
		
		CambiarVentana(VentanaRegistro.NewReg);
		
	}
	

	@Override
	public void focusGained(FocusEvent fe) {
		
		//Se cambia el borde a los objetos que estás seleccionando
		Object o = fe.getSource();
		((JComponent) o).setBorder(new LineBorder(new Color(150,200,240), 2	)); 
		
	}

	@Override
	public void focusLost(FocusEvent fe) {
		
		//Se pone el borde por defecto a los objetos que no estás seleccionando
		Object o = fe.getSource();
		((JComponent) o).setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null)); 
	}
	
	//Funcion de los botones añadir y borrar temporada
	@Override
	public void actionPerformed(ActionEvent e) {
	    Object o = e.getSource();
	    
	    if (o == btnAñadir || o == txtTemporada) { //al pulsar el boton añadir
	    	
	    	//creamos una variable donde almacenamos la fecha introducida en el textfield
	    	String fechanueva = txtTemporada.getText();
	    	
	    	if (txtTemporada.getText().isEmpty() || txtTemporada.getText().equals("0") || txtTemporada.getText().length() > 8) {
	    		
	    		JOptionPane.showMessageDialog(null, "No ha introducido ninguna fecha o ha introducido una fecha inválida.", "Fecha incorrecta", JOptionPane.ERROR_MESSAGE);
	    		
	    	}
	    	
	    	else if (existeTemporada(txtTemporada.getText())){	//si ya existe una temporada con la fecha introducida
	    			
	    		JOptionPane.showMessageDialog(null, "Ya existe una temporada con la fecha introducida.", "Temporada ya existente", JOptionPane.ERROR_MESSAGE);
				
	    }
	    	else if (dlmListaSeleccionados.size()!=6) {
	    		
	    		JOptionPane.showMessageDialog(null, "No se han seleccionados 6 equipos para la creación de temporada.", "Numero de equipos seleccionados incorrecto", JOptionPane.ERROR_MESSAGE);
	    		
	    	}
	    	
	    	
	    	else {		//si no existe ninguna temporada con esa fecha
	    		
	    		
	    		List<Equipo> Seleccionados = new ArrayList<>();
	    		TemporadasBase();
	    		
	    		for (int i = 0; i < dlmListaSeleccionados.size(); i++) {
	    		    
	    		    String nombre = dlmListaSeleccionados.getElementAt(i);
	    		    
	    		    for (Equipo equipo : listaEquipos) {
	    		    	
	    		        if (equipo.getNombre().equals(nombre)) {
	    		        	
	    		        	Seleccionados.add(equipo);
	    		        	break;
	    		        }     
	    		            
	    		}
	    		            
	    		}
	    		
	    		//Creamos una nueva temporada con la fecha y los equipos introducidos
	    		Temporada t = new Temporada(fechanueva,Seleccionados.get(0),Seleccionados.get(1),Seleccionados.get(2),Seleccionados.get(3),Seleccionados.get(4),Seleccionados.get(5));
	    		
	    		
	    		//la añadimos a la lista y al defaultlistmodel
	    		añadirTemporada(t);
	    		
	    		JOptionPane.showMessageDialog(null, "Temporada creada con éxito.", "Temporada añadida", JOptionPane.INFORMATION_MESSAGE,null);
				
	    		
	    	}
	    }
	    
	    else if (o == btnBorrar) {
	    	
	    	int index = JlistTemporadas.getSelectedIndex();
	    	
	    	if (index < 0) {
	    		
	    		JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna temporada.", "Ningun elemento seleccionado", JOptionPane.ERROR_MESSAGE);
	    		
	    	}
	    	
	    	else if (listaTemporadas.get(index).getEstado().equals("Finalizada")) {
	    		
	    		JOptionPane.showMessageDialog(null, "No se puede eliminar una temporada finalizada.", "No se ha podido eliminar", JOptionPane.ERROR_MESSAGE);
	    		
	    	}
	    	
	    	else {
	    		
	    		borrarTemporada(listaTemporadas.get(index));
	    		JOptionPane.showMessageDialog(null, "Temporada eliminada con éxito.", "Temporada eliminada", JOptionPane.INFORMATION_MESSAGE,null);
				
	    	}
	    }
	    
	    else if (o == btnSiguiente) {
	    	
	    	if (JlistTemporadas.getSelectedIndex() == -1)	{			
				JOptionPane.showMessageDialog(this, "Error al seleccionar temporada. No hay ninguna temporada seleccionada.", "Ninguna temporada seleccionada", JOptionPane.ERROR_MESSAGE);
			}	
	    	
	    	else {

		    	int index = JlistTemporadas.getSelectedIndex();
				temporadaSeleccionada = listaTemporadas.get(index);				
				VentanaClasificacion vc = new VentanaClasificacion();
				vc.setVisible(true);
				dispose();	
	    		
	    	}
	    	
	    }
	    
	    else if (o == btnDerecha) {
	    	

	    	MoverEquiposDerecha();
	    	
	    }
	    
	    else if (o == btnIzquierda) {
	    	
	    	MoverEquiposIzquierda();
	    	
	    }
	    
	    else if (o == btnUsuarios) {
	    	
	    	VentanaUsuarios vu = new VentanaUsuarios();
	    	vu.setVisible(true);
	    	dispose();
	    	
	    }
	    
	    else if (o == btnEquipos) {
	    	
	    	VentanaEdicionEquipos ve = new VentanaEdicionEquipos();
	    	ve.setVisible(true);
	    	dispose();
	    	
	    }
	    
	    else if (o == btnJugadores) {
	    	
	    	VentanaEdicionJugadores vj = new VentanaEdicionJugadores();
	    	vj.setVisible(true);
	    	dispose();
	    	
	    }
	    
	    else if (o == btnXML) {
	    	
	    	generarXML();
	    	JOptionPane.showMessageDialog(this, "XML generado correctamente con todos los datos.", "Archivo XML generado", JOptionPane.INFORMATION_MESSAGE,null);
			
	    }
	}
	
	public void TemporadasBase () {
		
		try {
		
		//CONSULTA PARA COGER LOS EQUIPOS
			
		//me conecto a la base de datos como root
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");
		
		//creo el Statement para coger los equipos
		Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		//como es una query, creo un objeto ResultSet 
		ResultSet rs = st.executeQuery("SELECT * FROM balonmano.equipos WHERE Num_Temp="+0+";");
		
		listaEquipos = new ArrayList<Equipo>();
		
		
		while (rs.next()) {
			
			//creo variables de todos los resultados por cada equipo para poder manipular los datos mejor
			String Nombre = rs.getString("Nom_Equipo");
			String Iniciales = Nombre.substring(0, Math.min(Nombre.length(), 3));
			int ID = Integer.parseInt(rs.getString("ID_Equipo"));
			int Temporada = Integer.parseInt(rs.getString("Num_Temp"));
			String Escudo = rs.getString("Escudo");
			String Estadio = rs.getString("Estadio");
			String Equipacion = rs.getString("Equipacion");
			List<Jugador> listaJugadoresEquipo = new ArrayList<Jugador>();
			
			//CONSULTA PARA RECOGER LOS DATOS DE TODOS LOS JUGADORES DEL EQUIPO ACTUAL
			Statement st4 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs4 = st4.executeQuery("SELECT * FROM balonmano.jugadores WHERE ID_Equipo="+ID+";");
			
			
			while (rs4.next()) {
				
				int idJ = Integer.parseInt(rs4.getString("ID_Jugador"));
				String NombreJ = rs4.getString("Nombre");
				String Imagen = rs4.getString("Imagen");
				String Posicion = rs4.getString("Posicion");
				String Localidad = rs4.getString("Localidad");
				int nacimiento = Integer.parseInt(rs4.getString("Año_Nacimiento"));
				int idEquipo = ID;
				String capitan = rs4.getString("Capitan");
				
				Jugador j = new Jugador (idJ, NombreJ, Imagen, Posicion, Localidad, nacimiento, idEquipo, capitan);
				listaJugadoresEquipo.add(j);
				
			}
			
			rs4.close();
			st4.close();
			
			
			// creo un nuevo Equipo por cada registro
			Equipo e = new Equipo (Nombre,Iniciales,ID,Temporada,Escudo,Estadio,Equipacion,listaJugadoresEquipo);

			
			//lo añado a la lista donde están todos los equipos
			
			listaEquipos.add(e);
		
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
	
	//Comprueba si existe la temporada en la lista de temporadas
		public boolean existeTemporada (String fecha) {
			boolean existe = false;
			for (Temporada t : listaTemporadas) {
				existe = t.getFecha().equals(fecha) ? true : false;
				if(existe) {
					break;
				}
			}
			return existe;
		}
		
		//Añade la temporada a la lista de temporadas
		public void añadirTemporada (Temporada temporada) {
			
			CrearJornadas(temporada);
		  	listaTemporadas.add(temporada);
		  	dlmListaTemporadas.addElement(temporada.getFecha()+" - "+temporada.getEstado());
			
		  	
		  //me intento conectar a la base de datos mysql para borrar la temporada seleccionada
			try {
				
				//me conecto a la base de datos como root
				Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");


				//CONSULTA PARA COGER LAS TEMPORADAS
				//creo el Statement para coger las temporadas que haya en la base de datos
				Statement st = conexion.createStatement();
				st.executeUpdate("INSERT INTO balonmano.temporadas VALUES ("+temporada.getFecha()+",'"+temporada.getEstado()+"');");
				
				for (Equipo e : temporada.getListaEquipos()) { //por cada equipo, añado un nuevo equipo a la base de datos	
					
					
					e.setTemporada(Integer.parseInt(temporada.getFecha()));
					e.setID(Integer.parseInt(e.getID()+""+e.getTemporada()));
					
				st.executeUpdate("INSERT INTO balonmano.equipos VALUES ("+e.getID()+","+e.getTemporada()+",'"+e.getNombre()+"',"+e.getPuntos()+",'Himno "+e.getNombre()+"','"+e.getEquipacion()+"','"+e.getEstadio()+"',"+e.getPjugados()+","+e.getPganados()+","+e.getPperdidos()+","+e.getGolesfavor()+","+e.getGolescontra()+",'"+e.getImagenEscudo()+"');");
				st.executeUpdate("INSERT INTO balonmano.participaciones VALUES("+e.getTemporada()+","+e.getID()+");");
				
					for (Jugador j : e.getListaJugadores()) { //por cada jugador dentro del equipo lo inserto en la base de datos
					
						j.setID(Integer.parseInt(j.getID()+""+temporada.getFecha()));
						j.setIdequipo(e.getID());
						
					st.executeUpdate("INSERT INTO balonmano.jugadores VALUES ("+j.getID()+",'"+j.getNombre()+"','"+j.getPosicion()+"','"+j.getLocalidad()+"',"+j.getAño()+","+j.getIdequipo()+",'"+j.getCapitan()+"','"+j.getImagen()+"');");	
					
					}
				
				}
				
				
				//INSERTO TODAS LAS JORNADAS Y LOS PARTIDOS EN LA BASE DE DATOS
				for (Jornada j : temporada.getListaJornadas()) {
					
					st.executeUpdate("INSERT INTO balonmano.jornadas VALUES ("+temporada.getFecha()+","+j.getID()+");");
					
					for (Partido p : j.getListaPartidos()) {

						
						st.executeUpdate("INSERT INTO balonmano.partidos VALUES ("+p.getID()+","+j.getID()+",'"+p.getEquipoLocal().getNombre()+"',"+p.getPtsLocal()+",'"+p.getEquipoVisit().getNombre()+"',"+p.getPtsVisit()+",101);");
						
					}
					
				}
				
				
				//Cierro el statement 
				st.close();
			
				// cierro la conexion
				conexion.close();
				
			}
		
				catch (SQLException e) {
					// si se produce una excepción SQL
					int errorcode = e.getErrorCode();
					if (errorcode == 1062) {
						// si es un error de clave duplicada
						JOptionPane.showMessageDialog(this,"Error Clave Duplicada. Ya existe un registro con esa clave.","Clave duplicada",JOptionPane.ERROR_MESSAGE,null);
					}
					else {
						//si se produce cualquier otro error sql
						JOptionPane.showMessageDialog(this,"Error SQL Numero "+e.getErrorCode()+":"+e.getMessage(),"Clave duplicada",JOptionPane.ERROR_MESSAGE,null);
					}
			}
			
		}
		
		public void borrarTemporada (Temporada temporada) {
			
			listaTemporadas.remove(temporada);
			dlmListaTemporadas.remove(JlistTemporadas.getSelectedIndex());
			
			//me intento conectar a la base de datos mysql para borrar la temporada seleccionada
			try {
				
				//me conecto a la base de datos como root
				Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");


				//CONSULTA PARA COGER LAS TEMPORADAS
				//creo el Statement para coger las temporadas que haya en la base de datos
				Statement st = conexion.createStatement();
				
				
					
				st.execute("DELETE FROM partidos WHERE ID_Jornada IN (SELECT ID_Jornada FROM jornadas WHERE Num_Temp="+temporada.getFecha()+" );");
				
				
				st.execute("DELETE FROM balonmano.jornadas WHERE Num_Temp="+temporada.getFecha());
				
				for (Equipo e : temporada.getListaEquipos()) {
					
					st.executeUpdate("DELETE FROM balonmano.jugadores WHERE ID_Equipo="+e.getID()+";");
					
				}

				st.executeUpdate("DELETE FROM balonmano.participaciones WHERE Num_Temp='"+temporada.getFecha()+"';");
				
				
				st.executeUpdate("DELETE FROM balonmano.equipos WHERE Num_Temp='"+temporada.getFecha()+"';");
				
				st.executeUpdate("DELETE FROM balonmano.temporadas WHERE Num_Temp='"+temporada.getFecha()+"';");
				
				//Cierro el statement 
				st.close();
			
				// cierro la conexion
				conexion.close();
				
				
			}
		
				catch (SQLException e) {
					//si se produce una excepción SQL
					System.out.println("Error SQL Numero "+e.getErrorCode()+":"+e.getMessage());
					
					
				}
			
		}
		
		public void CambiarVentana (Usuario u) {
			
			//Dependiendo los permisos del usuario introducido se le ocultarán o no los botones para editar las temporadas
			if (!u.getPermisos().equals("Admin")) {
				
				btnAñadir.setVisible(false);
				btnBorrar.setVisible(false);
				btnDerecha.setVisible(false);
				btnIzquierda.setVisible(false);
				txtTemporada.setVisible(false);
				lblInfoTemporada.setVisible(false);
				JListEquipos.setVisible(false);
				JListEquiposSeleccionados.setVisible(false);
				lblInfoEquipos.setVisible(false);
				lblPanelEquipos.setVisible(false);
				lblPanelEquipos2.setVisible(false);
				scrollPane1.setVisible(false);
				scrollPane2.setVisible(false);
				btnUsuarios.setVisible(false);
				btnEquipos.setVisible(false);
				btnJugadores.setVisible(false);
				btnXML.setVisible(false);
				
				JlistTemporadas.setBounds(38,206,525,150);
				btnSiguiente.setBounds(38,400,525,100);
				
				
				}
			
				else {
				btnAñadir.setVisible(true);
				btnBorrar.setVisible(true);
				btnDerecha.setVisible(true);
				btnIzquierda.setVisible(true);
				txtTemporada.setVisible(true);
				lblInfoTemporada.setVisible(true);
				JListEquipos.setVisible(true);
				JListEquiposSeleccionados.setVisible(true);
				lblInfoEquipos.setVisible(true);
				lblPanelEquipos.setVisible(true);
				lblPanelEquipos2.setVisible(true);
				scrollPane1.setVisible(true);
				scrollPane2.setVisible(true);
				btnUsuarios.setVisible(true);
				btnEquipos.setVisible(true);
				btnJugadores.setVisible(true);
				btnXML.setVisible(true);
				
				JlistTemporadas.setBounds(38, 206, 200, 137);
				btnSiguiente.setBounds(290,300,269,45);
				
				//Le mostramos un JOptionPane con distintas opciones avisando que puede modificar los jugadores o los equipos
		        String[] opciones = {"Editar temporadas", "Editar Jugadores", "Editar Equipos"};

		        // Mostrar el cuadro de diálogo con las opciones
		        int seleccion = JOptionPane.showOptionDialog(null, "Estás a punto de ser redirigido al panel de creación y borrado de temporadas, "
		        		+ "si no quiere crear una temporada con los valores predeterminados, puede cambiar los jugadores, sus equipos y los equipos existentes. Elija lo que quiere hacer:", "Selección de opciones",
		                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

		        // Verificar qué opción fue seleccionada
		        switch (seleccion) {
		            case 0:
		                break;
		            case 1:
		                // Si se selecciona la opción de editar jugadores
		            	VentanaEdicionJugadores vej = new VentanaEdicionJugadores();
		            	vej.setVisible(true);
		            	dispose();
		                break;
		            case 2:
		                // Si se selecciona la opción de editar equipos
		            	VentanaEdicionEquipos vee = new VentanaEdicionEquipos();
		            	vee.setVisible(true);
		            	dispose();
		                break;
		            default:

		                break;
		        }
				}
			
		}
		
		public void MoverEquiposDerecha() {
			
			List<String> Lista = new ArrayList<>();
			Lista = (JListEquipos.getSelectedValuesList());
			for (String s : Lista) {
				
				dlmListaSeleccionados.addElement(s);
				dlmListaEquipos.removeElement(s);				
			}
		}
		public void MoverEquiposIzquierda() {
			
			List<String> Lista = new ArrayList<>();
			Lista = (JListEquiposSeleccionados.getSelectedValuesList());
			for (String s : Lista) {
				
				dlmListaEquipos.addElement(s);
				dlmListaSeleccionados.removeElement(s);				
			}
		}
		
		public void CrearJornadas(Temporada temporada) {
			
			//Creo una lista donde se guardaran las jornadas
			List<Jornada>listaJornadas = new ArrayList<Jornada>();
			
			//Creamos jornada por jornada con numeros de las listas de equipos predeterminadas, al cambiar los equipos y su orden entre temporadas, las jornadas y los partidos serán aletorios
			
			
			//JORNADA 1
			List<Partido>listaPartidosActual = new ArrayList<Partido>();
			Partido p1j1 = new Partido(Integer.parseInt(temporada.getFecha()+""+1),1,temporada.getListaEquiposT().get(0),0,temporada.getListaEquiposT().get(3),0);
			Partido p2j1 = new Partido(Integer.parseInt(temporada.getFecha()+""+2),2,temporada.getListaEquiposT().get(2),0,temporada.getListaEquiposT().get(5),0);
			Partido p3j1 = new Partido(Integer.parseInt(temporada.getFecha()+""+3),3,temporada.getListaEquiposT().get(4),0,temporada.getListaEquiposT().get(1),0);
			listaPartidosActual.add(p1j1);
			listaPartidosActual.add(p2j1);
			listaPartidosActual.add(p3j1);
			Jornada j1 = new Jornada (Integer.parseInt(temporada.getFecha()+""+1),1,listaPartidosActual);
			listaJornadas.add(j1);
			
			//JORNADA 2
			listaPartidosActual = new ArrayList<Partido>();
			Partido p1j2 = new Partido(Integer.parseInt(temporada.getFecha()+""+4),1,temporada.getListaEquiposT().get(4),0,temporada.getListaEquiposT().get(2),0);
			Partido p2j2 = new Partido(Integer.parseInt(temporada.getFecha()+""+5),2,temporada.getListaEquiposT().get(1),0,temporada.getListaEquiposT().get(0),0);
			Partido p3j2 = new Partido(Integer.parseInt(temporada.getFecha()+""+6),3,temporada.getListaEquiposT().get(5),0,temporada.getListaEquiposT().get(3),0);
			listaPartidosActual.add(p1j2);
			listaPartidosActual.add(p2j2);
			listaPartidosActual.add(p3j2);
			Jornada j2 = new Jornada (Integer.parseInt(temporada.getFecha()+""+2),2,listaPartidosActual);
			listaJornadas.add(j2);
			
			//JORNADA 3
			listaPartidosActual = new ArrayList<Partido>();
			Partido p1j3 = new Partido(Integer.parseInt(temporada.getFecha()+""+7),1,temporada.getListaEquiposT().get(1),0,temporada.getListaEquiposT().get(5),0);
			Partido p2j3 = new Partido(Integer.parseInt(temporada.getFecha()+""+8),2,temporada.getListaEquiposT().get(0),0,temporada.getListaEquiposT().get(4),0);
			Partido p3j3 = new Partido(Integer.parseInt(temporada.getFecha()+""+9),3,temporada.getListaEquiposT().get(2),0,temporada.getListaEquiposT().get(3),0);
			listaPartidosActual.add(p1j3);
			listaPartidosActual.add(p2j3);
			listaPartidosActual.add(p3j3);
			Jornada j3 = new Jornada (Integer.parseInt(temporada.getFecha()+""+3),3,listaPartidosActual);
			listaJornadas.add(j3);
			
			//JORNADA 4
			listaPartidosActual = new ArrayList<Partido>();
			Partido p1j4 = new Partido(Integer.parseInt(temporada.getFecha()+""+10),1,temporada.getListaEquiposT().get(3),0,temporada.getListaEquiposT().get(1),0);
			Partido p2j4 = new Partido(Integer.parseInt(temporada.getFecha()+""+11),2,temporada.getListaEquiposT().get(2),0,temporada.getListaEquiposT().get(0),0);
			Partido p3j4 = new Partido(Integer.parseInt(temporada.getFecha()+""+12),3,temporada.getListaEquiposT().get(5),0,temporada.getListaEquiposT().get(4),0);
			listaPartidosActual.add(p1j4);
			listaPartidosActual.add(p2j4);
			listaPartidosActual.add(p3j4);
			Jornada j4 = new Jornada (Integer.parseInt(temporada.getFecha()+""+4),4,listaPartidosActual);
			listaJornadas.add(j4);
			
			//JORNADA 5
			listaPartidosActual = new ArrayList<Partido>();
			Partido p1j5 = new Partido(Integer.parseInt(temporada.getFecha()+""+13),1,temporada.getListaEquiposT().get(0),0,temporada.getListaEquiposT().get(5),0);
			Partido p2j5 = new Partido(Integer.parseInt(temporada.getFecha()+""+14),2,temporada.getListaEquiposT().get(4),0,temporada.getListaEquiposT().get(3),0);
			Partido p3j5 = new Partido(Integer.parseInt(temporada.getFecha()+""+15),3,temporada.getListaEquiposT().get(1),0,temporada.getListaEquiposT().get(2),0);
			listaPartidosActual.add(p1j5);
			listaPartidosActual.add(p2j5);
			listaPartidosActual.add(p3j5);
			Jornada j5 = new Jornada (Integer.parseInt(temporada.getFecha()+""+5),5,listaPartidosActual);
			listaJornadas.add(j5);
			
			//JORNADA 6
			listaPartidosActual = new ArrayList<Partido>();
			Partido p1j6 = new Partido(Integer.parseInt(temporada.getFecha()+""+16),1,temporada.getListaEquiposT().get(3),0,temporada.getListaEquiposT().get(0),0);
			Partido p2j6 = new Partido(Integer.parseInt(temporada.getFecha()+""+17),2,temporada.getListaEquiposT().get(1),0,temporada.getListaEquiposT().get(4),0);
			Partido p3j6 = new Partido(Integer.parseInt(temporada.getFecha()+""+18),3,temporada.getListaEquiposT().get(5),0,temporada.getListaEquiposT().get(2),0);
			listaPartidosActual.add(p1j6);
			listaPartidosActual.add(p2j6);
			listaPartidosActual.add(p3j6);
			Jornada j6 = new Jornada (Integer.parseInt(temporada.getFecha()+""+6),6,listaPartidosActual);
			listaJornadas.add(j6);
			
			//JORNADA 7
			listaPartidosActual = new ArrayList<Partido>();
			Partido p1j7 = new Partido(Integer.parseInt(temporada.getFecha()+""+19),1,temporada.getListaEquiposT().get(0),0,temporada.getListaEquiposT().get(1),0);
			Partido p2j7 = new Partido(Integer.parseInt(temporada.getFecha()+""+20),2,temporada.getListaEquiposT().get(3),0,temporada.getListaEquiposT().get(5),0);
			Partido p3j7 = new Partido(Integer.parseInt(temporada.getFecha()+""+21),3,temporada.getListaEquiposT().get(2),0,temporada.getListaEquiposT().get(4),0);
			listaPartidosActual.add(p1j7);
			listaPartidosActual.add(p2j7);
			listaPartidosActual.add(p3j7);
			Jornada j7 = new Jornada (Integer.parseInt(temporada.getFecha()+""+7),7,listaPartidosActual);
			listaJornadas.add(j7);
			
			//JORNADA 8
			listaPartidosActual = new ArrayList<Partido>();
			Partido p1j8 = new Partido(Integer.parseInt(temporada.getFecha()+""+22),1,temporada.getListaEquiposT().get(5),0,temporada.getListaEquiposT().get(1),0);
			Partido p2j8 = new Partido(Integer.parseInt(temporada.getFecha()+""+23),2,temporada.getListaEquiposT().get(3),0,temporada.getListaEquiposT().get(2),0);
			Partido p3j8 = new Partido(Integer.parseInt(temporada.getFecha()+""+24),3,temporada.getListaEquiposT().get(4),0,temporada.getListaEquiposT().get(0),0);
			listaPartidosActual.add(p1j8);
			listaPartidosActual.add(p2j8);
			listaPartidosActual.add(p3j8);
			Jornada j8 = new Jornada (Integer.parseInt(temporada.getFecha()+""+8),8,listaPartidosActual);
			listaJornadas.add(j8);
			
			//JORNADA 9
			listaPartidosActual = new ArrayList<Partido>();
			Partido p1j9 = new Partido(Integer.parseInt(temporada.getFecha()+""+25),1,temporada.getListaEquiposT().get(4),0,temporada.getListaEquiposT().get(5),0);
			Partido p2j9 = new Partido(Integer.parseInt(temporada.getFecha()+""+26),2,temporada.getListaEquiposT().get(0),0,temporada.getListaEquiposT().get(2),0);
			Partido p3j9 = new Partido(Integer.parseInt(temporada.getFecha()+""+27),3,temporada.getListaEquiposT().get(1),0,temporada.getListaEquiposT().get(3),0);
			listaPartidosActual.add(p1j9);
			listaPartidosActual.add(p2j9);
			listaPartidosActual.add(p3j9);
			Jornada j9 = new Jornada (Integer.parseInt(temporada.getFecha()+""+9),9,listaPartidosActual);
			listaJornadas.add(j9);
			
			//JORNADA 10
			listaPartidosActual = new ArrayList<Partido>();
			Partido p1j10 = new Partido(Integer.parseInt(temporada.getFecha()+""+28),1,temporada.getListaEquiposT().get(2),0,temporada.getListaEquiposT().get(1),0);
			Partido p2j10 = new Partido(Integer.parseInt(temporada.getFecha()+""+29),2,temporada.getListaEquiposT().get(5),0,temporada.getListaEquiposT().get(0),0);
			Partido p3j10 = new Partido(Integer.parseInt(temporada.getFecha()+""+30),3,temporada.getListaEquiposT().get(3),0,temporada.getListaEquiposT().get(4),0);
			listaPartidosActual.add(p1j10);
			listaPartidosActual.add(p2j10);
			listaPartidosActual.add(p3j10);
			Jornada j10 = new Jornada (Integer.parseInt(temporada.getFecha()+""+10),10,listaPartidosActual);
			listaJornadas.add(j10);
			
			//Una vez creadas todas las jornadas y todos los partidods se lo añadimos a la temporada que hemos introducido
			temporada.setListaJornadas(listaJornadas);
			
		}
		
		public void generarXML () {
			
			
			
			//me intento conectar a la base de datos mysql para coger los datos de todas las temporadas
			try {
				
				
				FileWriter fichero = new FileWriter("C:\\Users\\ik_1DW3A\\Desktop\\datos.xml");
			    PrintWriter pw = new PrintWriter(fichero);
			    BufferedWriter bw = new BufferedWriter(pw);
			    
			    bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			    bw.newLine();
			    bw.write("<?xml-model href=\"datos.xsd\"?> ");
			    bw.newLine();
			    bw.write("<?xml-stylesheet type=\"text/xsl\" href=\"xml/transformacion.xsl\"?>");
			    bw.newLine();
			    bw.write("<datos>");
			    bw.newLine();
			    bw.write("<usuarios>");
			    
			    
			    //me intento conectar a la base de datos orientada a objetos para coger los datos de los usuarios
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:db/balonmano.odb");
				EntityManager em = emf.createEntityManager();
				
				// ejecuto la consulta
				TypedQuery<Usuario> tq1 = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
				List<Usuario> results = tq1.getResultList();
				
				for (Usuario u : results) {

					bw.newLine();
					bw.write("<usuario>");
					bw.newLine();
					bw.write("<nombre>"+u.getNombre()+"</nombre>");
					bw.newLine();
					bw.write("<correo>"+u.getCorreo()+"</correo>");
					bw.newLine();
					bw.write("<contraseña>"+u.getContraseña()+"</contraseña>");
					bw.newLine();
					bw.write("<rol>"+u.getPermisos()+"</rol>");
					bw.newLine();
					bw.write("</usuario>");
					
				}
				
				bw.newLine();
				bw.write("</usuarios>");

				//me conecto a la base de datos como root
				Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");

				//creo el Statement para coger las temporadas que haya en la base de datos
				//CONSULTA PARA COGER TODAS LAS TEMPORADAS
				Statement st = conexion.createStatement();
				ResultSet rsT = st.executeQuery("SELECT * FROM balonmano.temporadas WHERE Num_Temp!= 0;");
				
				while (rsT.next()) {
					
					bw.newLine();
					bw.write("<temporada nombre='"+rsT.getString("Num_Temp")+"'>");
					bw.newLine();
					bw.write("<equipos>");
					
					//CONSULTA PARA COGER TODOS LOS EQUIPOS DE LA TEMPORADA
					Statement st1 = conexion.createStatement();
					ResultSet rsE = st1.executeQuery("SELECT * FROM balonmano.equipos WHERE Num_Temp="+rsT.getString("Num_Temp")+";");
					
					while (rsE.next()) {
						
						bw.newLine();
						bw.write("<equipo nombre='"+rsE.getString("Nom_Equipo")+"' img='"+rsE.getString("Escudo")+"'>");
						
						//CONSULTA PARA COGER TODOS LOS JUGADORES DEL EQUIPO DE LA TEMPORADA
						Statement st2 = conexion.createStatement();
						ResultSet rsJ = st2.executeQuery("SELECT * FROM balonmano.jugadores WHERE ID_Equipo="+rsE.getString("ID_Equipo")+";");
						
						while (rsJ.next()) {

							bw.newLine();
							bw.write("<jugador>");
							bw.newLine();
							bw.write("<nombre>"+rsJ.getString("Nombre")+"</nombre>");
							bw.newLine();
							bw.write("<imagen>"+rsJ.getString("Imagen")+"</imagen>");
							bw.newLine();
							bw.write("</jugador>");
							
						}
						
						rsJ.close();
						st2.close();
						
						bw.newLine();
						bw.write("</equipo>");
						
					}
					
					rsE.close();
					st1.close();
					
					bw.newLine();
					bw.write("</equipos>");
					bw.newLine();
					bw.write("<jornadas>");
					
					//CONSULTA PARA COGER TODAS LAS JORNADAS DE LA TEMPORADA
					Statement st3 = conexion.createStatement();
					ResultSet rsJr = st3.executeQuery("SELECT * FROM balonmano.jornadas WHERE Num_Temp="+rsT.getString("Num_Temp")+";");
					
					//variable que guarda el numero de la jornada
					int i = 0;
					
					while (rsJr.next()) {
						
						//por cada jornada que exista en el resultset, sumo uno a la variable que contabiliza el numero de la jornada
						i++;
						bw.newLine();
						bw.write("<jornada numero='"+i+"'>");
						
						//CONSULTA PARA COGER TODOS LOS PARTIDOS DE LA JORNADA DE LA TEMPORADA
						Statement st4 = conexion.createStatement();
						ResultSet rsP = st4.executeQuery("SELECT * FROM balonmano.partidos WHERE ID_Jornada="+rsJr.getString("ID_Jornada")+";");
						
						while (rsP.next()) {
							
							Equipo EL = new Equipo (rsP.getString("nom_equipo_loc"));
							Equipo EV = new Equipo (rsP.getString("nom_equipo_vis"));
							
							bw.newLine();
							bw.write("<partido>");
							
							Statement st5 = conexion.createStatement();
							ResultSet rsI = st5.executeQuery("SELECT * FROM balonmano.equipos WHERE Num_Temp= "+rsT.getString("Num_Temp")+" AND Nom_Equipo='"+EL.getNombre()+"';");
							while(rsI.next()) {
								
								bw.newLine();
								bw.write("<local>"+rsI.getString("Nom_Equipo")+"</local>");
								
								bw.newLine();
								bw.write("<escudo-local>"+rsI.getString("Escudo")+"</escudo-local>");
							
							}
							
							bw.newLine();
							bw.write("<goles-local>"+rsP.getString("goles_equipo_loc")+"</goles-local>");
							
							rsI = st5.executeQuery("SELECT * FROM balonmano.equipos WHERE Num_Temp= "+rsT.getString("Num_Temp")+" AND Nom_Equipo='"+EV.getNombre()+"';");
							while(rsI.next()) {
								
								bw.newLine();
								bw.write("<visitante>"+rsI.getString("Nom_Equipo")+"</visitante>"); 

								bw.newLine();
								bw.write("<escudo-visitante>"+rsI.getString("Escudo")+"</escudo-visitante>");
								
								}
							rsI.close();
							st5.close();
							
							bw.newLine();
							bw.write("<goles-visitante>"+rsP.getString("goles_equipo_vis")+"</goles-visitante>");
							bw.newLine();
							bw.write("</partido>");
							
						}
						
						rsP.close();
						st4.close();
						
						bw.newLine();
						bw.write("</jornada>");
						
					}
					
					rsJr.close();
					st3.close();
					
					bw.newLine();
					bw.write("</jornadas>");
					
					bw.newLine();
					bw.write("</temporada>");
				}
				
				rsT.close();
				st.close();
				
				bw.newLine();
				bw.write("</datos>");
				
				bw.close();
				pw.close();
				fichero.close();
				
				
			}
		
				catch (SQLException e) {
					//si se produce una excepción SQL
					System.out.println("Error SQL Numero "+e.getErrorCode()+":"+e.getMessage());
					
					
				}
			
				catch (Exception e1) {
			     // TODO: handle exception
				}

		
}
		}


