import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
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
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaResultados extends JFrame implements ActionListener, FocusListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private Vector<Vector<String>> datosTablaPartidos = new Vector<Vector<String>>();
	private Vector<String> fila;
	private JTable tablaPartidos;
	private DefaultTableModel dtmTablaPartidos;
	private JButton btnAtras;
	private JLabel lblEquipoLocal;
	private JTextField txtEquipoLocal;
	private JLabel lblEquipoVisit;
	private JTextField txtEquipoVisit;
	List<Jornada> listaJornadas= new ArrayList<Jornada>();
	private int posicion;
	private int NumeroRegistros;
	List<Partido> listaPartidos = new ArrayList<Partido>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaResultados frame = new VentanaResultados();
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
	public VentanaResultados() {

		// establecemos título e icono de la aplicación
		setTitle("Real Federación EspaÑola de Balonmano");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaTemporadas.class.getResource("/img/Logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ubicación y tamaño de la ventana
		setBounds(100, 100, 650, 600);
		setLocationRelativeTo(null);

		// quita el redimensionado de la ventana
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// creamos y añadimos un Jlabel para el título de Usuarios
		lblTitulo = new JLabel("Resultados");
		contentPane.add(lblTitulo);

		// propiedades del JLabel
		lblTitulo.setForeground(new Color(0, 0, 0));
		lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 30));
		lblTitulo.setBounds(241, 20, 154, 30);
		lblTitulo.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		try {
			
		//CONSULTA PARA COGER LAS JORNADAS Y SUS PARTIDOS
			
		//me conecto a la base de datos como root
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");
		
		//creo el Statement para coger los equipos
		Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		//como es una query, creo un objeto ResultSet 
		ResultSet rs = st.executeQuery("SELECT * FROM balonmano.jornadas WHERE Num_Temp="+VentanaTemporadas.temporadaSeleccionada.getFecha()+";");
		
		listaJornadas = new ArrayList<Jornada>();
		
		while (rs.next()) {
			
			//creo variables de todos los resultados por cada jornada para poder manipular los datos mejor
			int ID = Integer.parseInt(rs.getString("ID_Jornada"));
			int Temporada = Integer.parseInt(rs.getString("Num_Temp"));
			listaPartidos = new ArrayList<Partido>();
			
			//CONSULTA PARA RECOGER LOS DATOS DE TODOS LOS PARTIDOS DE LA JORNADA ACTUAL
			Statement st2 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs2 = st2.executeQuery("SELECT * FROM balonmano.partidos WHERE ID_Jornada="+ID+";");
			
			
			while (rs2.next()) {
				
				int idP = Integer.parseInt(rs2.getString("cod_partido"));
				Equipo EL = new Equipo (rs2.getString("nom_equipo_loc"));
				int GL = Integer.parseInt(rs2.getString("goles_equipo_loc"));
				Equipo EV = new Equipo (rs2.getString("nom_equipo_vis"));
				int GV = Integer.parseInt(rs2.getString("goles_equipo_vis"));
				
				Partido p = new Partido (idP, EL, GL, EV, GV);
				listaPartidos.add(p);
				
			}
			
			rs2.close();
			st2.close();
			
			
			// creo una nueva jornada por cada registro
			Jornada j = new Jornada (ID,Temporada,listaPartidos);

			
			//lo añado a la lista donde están todas las jornadas
			
			listaJornadas.add(j);
		
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

		// si se ha conectado correctamente
		Vector<String> columnas = new Vector<String>();
		columnas.add("Equipo Local");
		columnas.add("Goles Local");
		columnas.add("Equipo Visitante");
		columnas.add("Goles Visitante");

		// creo el vector para los datos de la tabla
		datosTablaPartidos = new Vector<Vector<String>>();

		// creo el DefaultTableModel de la JTable
		dtmTablaPartidos = new DefaultTableModel(datosTablaPartidos, columnas);

		// creo una tabla y le añado el modelo por defecto
		tablaPartidos = new JTable(dtmTablaPartidos);
		tablaPartidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(tablaPartidos);
		
		//añado el Mouselistener para que ponga los datos seleccionados en los campos de texto
		tablaPartidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				cogerDatos();
			}

		});

		// creo un scroll pane y le añado la tabla
		JScrollPane scrollPane = new JScrollPane(tablaPartidos);
		scrollPane.setBounds(25, 250, 583, 300);

		// añado el scroll pane al panel principal
		contentPane.add(scrollPane);

		// creamos y añadimos un botón para volver a la ventanatemporadas
		btnAtras = new JButton("");
		contentPane.add(btnAtras);

		// propiedades del JButton
		btnAtras.setBackground(null);
		btnAtras.setBorder(null);
		btnAtras.setBounds(576, 517, 30, 30);
		btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAtras.setIcon(new ImageIcon("src/img/atras.png"));

		// añadimos los listeners necesarios
		btnAtras.addActionListener(this);
		btnAtras.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent me) {
				// cuando de pasa el ratón por encima
				btnAtras.setBackground(new Color(212, 212, 212));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				// Cuando el raton no esta por encima
				btnAtras.setBackground(null);
			}

		});

		// creamos y añadimos un Jlabel para indicar el textfield de nombre de usuario
		lblEquipoLocal = new JLabel("Equipo Local:");
		contentPane.add(lblEquipoLocal);

		// propiedades del JLabel
		lblEquipoLocal.setForeground(new Color(0, 0, 0));
		lblEquipoLocal.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblEquipoLocal.setBounds(25, 115, 80, 20);
		lblEquipoLocal.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// creamos y añadimos un JTextField donde pondremos el nombre de usuario nuevo que queramos introducir
		txtEquipoLocal = new JTextField();
		txtEquipoLocal.setEditable(false);
		contentPane.add(txtEquipoLocal);

		// propiedades del JTextField
		txtEquipoLocal.setBounds(120, 115, 100, 20);
		txtEquipoLocal.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtEquipoLocal.setColumns(10);

		// Añadimos los listeners necesarios
		txtEquipoLocal.addFocusListener(this);
		
		// creamos y añadimos un Jlabel para indicar el textfield de la contraseña
		lblEquipoVisit = new JLabel("Equipo Visitante:");
		contentPane.add(lblEquipoVisit);

		// propiedades del JLabel
		lblEquipoVisit.setForeground(new Color(0, 0, 0));
		lblEquipoVisit.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblEquipoVisit.setBounds(230, 115, 100, 20);
		lblEquipoVisit.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// creamos y añadimos un JTextField donde pondremos la contraseña nueva que queramos introducir
		txtEquipoVisit = new JTextField();
		txtEquipoVisit.setEditable(false);
		contentPane.add(txtEquipoVisit);

		// propiedades del JTextField
		txtEquipoVisit.setBounds(340, 115, 100, 20);
		txtEquipoVisit.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtEquipoVisit.setColumns(10);

		// Añadimos los listeners necesarios
		txtEquipoVisit.addFocusListener(this);
		
		// creamos y añadimos una JComboBox nueva que nos mostrara los distintos permisos que queramos introducir
		// creo una variable donde guardo las opciones de la combobox
		String[] Permisos = {"Usuario","Admin","Arbitro"};
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		

	}

	@Override
	public void focusGained(FocusEvent fe) {

		// Se cambia el borde a los objetos que estás seleccionando
		Object o = fe.getSource();
		((JComponent) o).setBorder(new LineBorder(new Color(150, 200, 240), 2));

	}

	@Override
	public void focusLost(FocusEvent fe) {

		// Se pone el borde por defecto a los objetos que no estás seleccionando
		Object o = fe.getSource();
		((JComponent) o).setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}

	
	public void cogerDatos() {
		// sacamos en que fila se ha hecho click
		int seleccion = tablaPartidos.getSelectedRow();
		// si se ha hecho click en una fila
		if (seleccion >= 0) {
			//cogemos el dato de permisos para ponerlo en la combobox de permisos
			int Permisos = -1;
			
			if(dtmTablaPartidos.getValueAt(seleccion, 1).equals("Usuario")) {
				
				Permisos = 0;
				
			}
			else if (dtmTablaPartidos.getValueAt(seleccion, 1).equals("Admin")) {
				
				Permisos = 1;
				
			}
			
			else {
				
				Permisos = 2;
				
			}
			
			// Establecemos los valores de los txt
			txtEquipoLocal.setText((String) dtmTablaPartidos.getValueAt(seleccion, 0));
			txtEquipoVisit.setText("");
		}
	}
	
	private void ActualizarCampos(){
		
		//carga los datos del registro actual en los campos de datos
		String mensaje;
		if (NumeroRegistros > 0) {	//si hay registros
		mensaje = "Registro "+(posicion + 1)+" de "+NumeroRegistros;
		
		//cargo los datos de la posicion actual
		Jornada valor = listaJornadas.get(posicion);
		txtDNI.setText(valor.getDni());
		txtNombre.setText(valor.getNombre());
		txtApellidos.setText(valor.getApellidos());
		txtGrupo.setText(valor.getGrupo());
		
		}
		
		else {	//si no hay registros
			
			mensaje = "No hay registros";
			txtDNI.setText("00000000A");
			txtNombre.setText("N0");
			txtApellidos.setText("A0");
			txtGrupo.setText("1DW3");
		}
		
		lblRegistros.setText(mensaje);
	}
	
}
