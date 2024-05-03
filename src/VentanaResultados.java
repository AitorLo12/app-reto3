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
import javax.swing.SwingConstants;

public class VentanaResultados extends JFrame implements ActionListener, FocusListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private JLabel lblJornadas;
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
	private JLabel lblGolesLocal;
	private JLabel lblGolesVisit;
	private JTextField txtGolesLocal;
	private JTextField txtGolesVisit;
	private JButton btnPrimero;
	private JButton btnAnterior;
	private JButton btnSiguiente;
	private JButton btnUltimo;
	private JButton btnInsertar;
	private JButton btnFinalizar;

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
		lblTitulo.setBounds(220, 20, 195, 30);
		lblTitulo.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		

		//creamos y añadimos un JLabel donde indicaremos el numero de jornada que estamos editando
		lblJornadas = new JLabel("");
		contentPane.add(lblJornadas);
		
		//propiedades del JLabel
		lblJornadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblJornadas.setForeground(Color.BLACK);
		lblJornadas.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblJornadas.setBounds(193, 60, 250, 30);

		try {
			
		//CONSULTA PARA COGER LAS JORNADAS Y SUS PARTIDOS
			
		//me conecto a la base de datos como root
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");
		
		//creo el Statement para coger los equipos
		Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		//como es una query, creo un objeto ResultSet 
		ResultSet rs = st.executeQuery("SELECT * FROM balonmano.jornadas WHERE Num_Temp="+VentanaTemporadas.temporadaSeleccionada.getFecha()+";");
		
		listaJornadas = new ArrayList<Jornada>();
		NumeroRegistros = 0;
		
		while (rs.next()) {
			
			//creo variables de todos los resultados por cada jornada para poder manipular los datos mejor
			int ID = Integer.parseInt(rs.getString("ID_Jornada"));
			int Temporada = Integer.parseInt(rs.getString("Num_Temp"));
			listaPartidos = new ArrayList<Partido>();
			NumeroRegistros++;
			
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
		
		

		ActualizarCampos();
		

		// creamos y añadimos un botón para volver a la ventanaJornadas
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

		// creamos y añadimos un Jlabel para indicar el equipo local que hemos seleccionado
		lblEquipoLocal = new JLabel("Equipo Local:");
		contentPane.add(lblEquipoLocal);

		// propiedades del JLabel
		lblEquipoLocal.setForeground(new Color(0, 0, 0));
		lblEquipoLocal.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblEquipoLocal.setBounds(25, 115, 80, 20);
		lblEquipoLocal.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// creamos y añadimos un JTextField donde nos saldrá el nombre del equipo local seleccionado
		txtEquipoLocal = new JTextField();
		txtEquipoLocal.setEditable(false);
		contentPane.add(txtEquipoLocal);

		// propiedades del JTextField
		txtEquipoLocal.setBounds(120, 115, 100, 20);
		txtEquipoLocal.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtEquipoLocal.setColumns(10);
		
		// creamos y añadimos un Jlabel para indicar el equipo visitante que tenemos seleccionado
		lblEquipoVisit = new JLabel("Equipo Visitante:");
		contentPane.add(lblEquipoVisit);

		// propiedades del JLabel
		lblEquipoVisit.setForeground(new Color(0, 0, 0));
		lblEquipoVisit.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblEquipoVisit.setBounds(230, 115, 100, 20);
		lblEquipoVisit.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// creamos y añadimos un JTextField donde nos aparecerá el nombre del equipo visitante seleccionado
		txtEquipoVisit = new JTextField();
		contentPane.add(txtEquipoVisit);

		// propiedades del JTextField
		txtEquipoVisit.setBounds(340, 115, 100, 20);
		txtEquipoVisit.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtEquipoVisit.setColumns(10);
		txtEquipoVisit.setEditable(false);

		
		//creamos y añadimos un JLabel que indique los goles del equipo local
		lblGolesLocal = new JLabel("Goles Local:");
		contentPane.add(lblGolesLocal);
		
		//propiedades del JLabel
		lblGolesLocal.setForeground(Color.BLACK);
		lblGolesLocal.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblGolesLocal.setBounds(25, 170, 80, 20);
		
		//Creamos y añadimos un JTextFiel donde podremos introducir los goles del equipo local
		txtGolesLocal = new JTextField();
		contentPane.add(txtGolesLocal);
		
		//Propiedades del JTextField
		txtGolesLocal.setColumns(10);
		txtGolesLocal.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtGolesLocal.setBounds(120, 170, 100, 20);
		

		//creamos y añadimos un JLabel que indique los goles del equipo visitante
		lblGolesVisit = new JLabel("Goles Visitante:");
		contentPane.add(lblGolesVisit);
		
		//propiedades del JLabel
		lblGolesVisit.setForeground(Color.BLACK);
		lblGolesVisit.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblGolesVisit.setBounds(230, 170, 100, 20);
		
		//Creamos y añadimos un JTextFiel donde podremos introducir los goles del equipo visitante
		txtGolesVisit = new JTextField();
		contentPane.add(txtGolesVisit);
		
		//Propiedades del JTextField
		txtGolesVisit.setColumns(10);
		txtGolesVisit.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtGolesVisit.setBounds(340, 170, 100, 20);

		// creamos y añadimos un botón para insertar los resultados que hayamos introducido en el partido que hayamos seleccionado
		btnInsertar = new JButton("Actualizar resultados");
		contentPane.add(btnInsertar);

		// propiedades del JButton
		btnInsertar.setBackground(new Color(192,192,192));
		btnInsertar.setBorder(null);
		btnInsertar.setBounds(465, 115, 130, 18);
		btnInsertar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// añadimos los listeners necesarios
		btnInsertar.addActionListener(this);
		btnInsertar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent me) {
				// cuando de pasa el ratón por encima
				btnInsertar.setBackground(new Color(212, 212, 212));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				// Cuando el raton no esta por encima
				btnInsertar.setBackground(new Color(192,192,192));
			}

		});
		
		// creamos y añadimos un botón para dar por finalizada la temporada
		btnFinalizar = new JButton("Finalizar temporada");
		contentPane.add(btnFinalizar);

		// propiedades del JButton
		btnFinalizar.setBackground(new Color(192,192,192));
		btnFinalizar.setBorder(null);
		btnFinalizar.setBounds(465, 170, 130, 18);
		btnFinalizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// añadimos los listeners necesarios
		btnFinalizar.addActionListener(this);
		btnFinalizar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent me) {
				// cuando de pasa el ratón por encima
				btnFinalizar.setBackground(new Color(212, 212, 212));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				// Cuando el raton no esta por encima
				btnFinalizar.setBackground(new Color (192,192,192));
			}

		});
		
		
		
		//crearemos y añadiremos distintos botones para navegar entre las distintas jornadas
		
		//Boton para ir a la primera jornada
		btnPrimero = new JButton("<<");
		contentPane.add(btnPrimero);
		btnPrimero.setFont(new Font("Arial", Font.PLAIN, 15));
		btnPrimero.setForeground(new Color(0, 0, 0));
		btnPrimero.setBackground(new Color(192,192,192));
		btnPrimero.setBounds(50, 56, 50, 40);
		btnPrimero.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPrimero.setBorder(null);
		
		//añadimos listeners necesarios
		btnPrimero.addActionListener(this);
		btnPrimero.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent me) {
				// cuando de pasa el ratón por encima
				btnPrimero.setBackground(new Color(212, 212, 212));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				// Cuando el raton no esta por encima
				btnPrimero.setBackground(new Color (192,192,192));
			}

		});
		
		//boton para ir a la jornada anterior
		btnAnterior = new JButton("<");
		contentPane.add(btnAnterior);
		btnAnterior.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAnterior.setForeground(new Color(0, 0, 0));
		btnAnterior.setBackground(new Color(192,192,192));
		btnAnterior.setBounds(125, 56, 50, 40);
		btnAnterior.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAnterior.setBorder(null);
		
		//añadimos los listeners necesarios
		btnAnterior.addActionListener(this);
		btnAnterior.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent me) {
				// cuando de pasa el ratón por encima
				btnAnterior.setBackground(new Color(212, 212, 212));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				// Cuando el raton no esta por encima
				btnAnterior.setBackground(new Color (192,192,192));
			}

		});
		
		//boton para ir a la jornada siguiente
		btnSiguiente = new JButton(">");
		contentPane.add(btnSiguiente);
		btnSiguiente.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSiguiente.setForeground(new Color(0, 0, 0));
		btnSiguiente.setBackground(new Color(192,192,192));
		btnSiguiente.setBounds(461, 56, 50, 40);
		btnSiguiente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSiguiente.setBorder(null);
		
		//añadimos los listeners necesarios
		btnSiguiente.addActionListener(this);
		btnSiguiente.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent me) {
				// cuando de pasa el ratón por encima
				btnSiguiente.setBackground(new Color(212, 212, 212));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				// Cuando el raton no esta por encima
				btnSiguiente.setBackground(new Color (192,192,192));
			}

		});
		
		
		//boton para ir a la ultima jornada
		btnUltimo = new JButton(">>");
		contentPane.add(btnUltimo);
		btnUltimo.setFont(new Font("Arial", Font.PLAIN, 15));
		btnUltimo.setForeground(new Color(0, 0, 0));
		btnUltimo.setBackground(new Color(192,192,192));
		btnUltimo.setBounds(536, 56, 50, 40);
		btnUltimo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUltimo.setBorder(null);
		
		//añadimos los listeners necesarios
		btnUltimo.addActionListener(this);
		btnUltimo.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent me) {
				// cuando de pasa el ratón por encima
				btnUltimo.setBackground(new Color(212, 212, 212));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				// Cuando el raton no esta por encima
				btnUltimo.setBackground(new Color (192,192,192));
			}

		});
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if (o == btnInsertar) {
			
			int filas = tablaPartidos.getSelectedRow();
			
			if (filas <= 0) { // compruebo que haya algun partido seleccionado en la tabla
				// si no hay ningun elemento seleccionado
				JOptionPane.showMessageDialog(this, "Error, no hay ningun elemento seleccionado.","Ningun elemento seleccionado", JOptionPane.ERROR_MESSAGE, null);

			}
			
			else if (txtGolesLocal.getText().isEmpty() || txtGolesVisit.getText().isEmpty()) {

				// si los campos están vacíos
				JOptionPane.showMessageDialog(this, "Rellena todos los campos para insertar un resultado del partido seleccionado.","Error, campo(s) vacío(s)", JOptionPane.ERROR_MESSAGE, null);
				
			}
			
			else {
				
				//Se crean variables que guardan los datos de los campos para poder manipular correctamente con estos
				int Cod = Integer.parseInt(""+dtmTablaPartidos.getValueAt(filas, 0));
				int GolesL = Integer.parseInt(txtGolesLocal.getText());
				int GolesV = Integer.parseInt(txtGolesVisit.getText());
				String EquipoL = txtEquipoLocal.getText();
				String EquipoV = txtEquipoVisit.getText();
				
				//me intento conectar a la base de datos mysql para actualizar el partido
				try {
					
					//me conecto a la base de datos como root
					Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");

					Statement st = conexion.createStatement();

					//CONSULTA PARA ACTUALIZAR EL PARTIDO SELECCIONADO
					//creo el Statement para actualizar los datos del partido seleccionado
					st.executeUpdate("UPDATE balonmano.partidos SET goles_equipo_loc="+GolesL+",goles_equipo_vis="+GolesV+" WHERE cod_partido="+Cod+";");
					
					//CONSULTA PARA COGER LOS PARTIDOS Y PUNTOS DE LOS EQUIPOS DEL PARTIDO
					
					
					
					//CONSULTA PARA ACTUALIZAR LOS EQUIPOS CON LOS GOLES Y LOS PARTIDOS JUGADOS
					
					
					//Cierro el statement 
					st.close();
				
					// cierro la conexion
					conexion.close();
					
					JOptionPane.showMessageDialog(this,"Resultado del partido seleccionado actualizado correctamente.","Actualización exitosa",JOptionPane.INFORMATION_MESSAGE,null);
					
					ActualizarCampos();
					

					// Establecemos los valores de los txt a campos vacíos
					txtEquipoLocal.setText("");
					txtEquipoVisit.setText("");
					txtGolesLocal.setText("");
					txtGolesVisit.setText("");
					
				}
			
					catch (SQLException er) {
						// si se produce una excepción SQL
						int errorcode = er.getErrorCode();
						if (errorcode == 1062) {
							// si es un error de clave duplicada
							JOptionPane.showMessageDialog(this,"Error Clave Duplicada. Ya existe un registro con esa clave.","Clave duplicada",JOptionPane.ERROR_MESSAGE,null);
						}
						else {
							//si se produce cualquier otro error sql
							JOptionPane.showMessageDialog(this,"Error SQL Numero "+er.getErrorCode()+":"+er.getMessage(),"Clave duplicada",JOptionPane.ERROR_MESSAGE,null);
						}
				}
				
			}
			
		}
		
		else if (o == btnFinalizar) {
			
			
			
		}
		
		else if (o == btnAtras) {
			
			VentanaJornadas vj = new VentanaJornadas();
			vj.setVisible(true);
			dispose();
			
		}
		
		else if (o == btnPrimero) {
			
			if (posicion > 0) {
				//si no está en el primero
				posicion = 0;
				//actualizo los campos
				ActualizarCampos ();
			}
			
		}
	
		else if ( o == btnAnterior) {
			
			if (posicion > 0) {
				//si no está en el primero
				//voy al anterior
				--posicion;
				//actualizo los campos
				ActualizarCampos ();
			}
			
		}
	
		else if (o == btnSiguiente) {
			
			if (posicion < (NumeroRegistros -1)) {
				//si no está en el último
				//voy al siguiente
				++posicion;
				//actualizo los campos
				ActualizarCampos ();
			}
			
		}
	
		else if (o == btnUltimo) {
			
			if (posicion < (NumeroRegistros - 1)) {
				//si no está en el último
				//voy al ultimo
				posicion = NumeroRegistros-1;
				
				//actualizo los campos
				ActualizarCampos ();
			}
			
		}

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
			
			// Establecemos los valores de los txt
			txtEquipoLocal.setText((String) dtmTablaPartidos.getValueAt(seleccion, 1));
			txtGolesLocal.setText((String) dtmTablaPartidos.getValueAt(seleccion, 2));
			txtEquipoVisit.setText((String) dtmTablaPartidos.getValueAt(seleccion, 3));
			txtGolesVisit.setText((String) dtmTablaPartidos.getValueAt(seleccion, 4));
		}
	}
	
	private void ActualizarCampos(){
		
		//carga los datos del registro actual en los campos de datos
		String mensaje;
		if (NumeroRegistros > 0) {	//si hay registros
		mensaje = "Jornada "+(posicion + 1)+" de "+NumeroRegistros;
		
		// si ha conseguido la jornada correctamente correctamente
		Vector<String> columnas = new Vector<String>();
		columnas.add("ID");
		columnas.add("Equipo Local");
		columnas.add("Goles Local");
		columnas.add("Equipo Visitante");
		columnas.add("Goles Visitante");
		
		//cargo los datos de la posicion actual
		Jornada valor = listaJornadas.get(posicion);

		// creo el vector para los datos de la tabla
		datosTablaPartidos = new Vector<Vector<String>>();
		
		for (Partido p : valor.getListaPartidos()) {
			
			fila = new Vector<String>();
			fila.add(""+p.getID());
			fila.add(p.getEquipoLocal().getNombre());
			fila.add(""+p.getPtsLocal());
			fila.add(p.getEquipoVisit().getNombre());
			fila.add(""+p.getPtsVisit());
			fila.add("\n\n\n\n\n\n\n");
			datosTablaPartidos.add(fila);
			
		}

		// creo el DefaultTableModel de la JTable
		dtmTablaPartidos = new DefaultTableModel(datosTablaPartidos, columnas);
		
		// creo una tabla y le añado el modelo por defecto
		tablaPartidos = new JTable(dtmTablaPartidos);
		tablaPartidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaPartidos.setRowHeight(92);
		contentPane.add(tablaPartidos);
		
		//añado el Mouselistener para que ponga los datos seleccionados en los campos de texto
		tablaPartidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				cogerDatos();
			}
		});

		tablaPartidos.getColumnModel().getColumn(0).setPreferredWidth(64);
		tablaPartidos.getColumnModel().getColumn(1).setPreferredWidth(200);
		tablaPartidos.getColumnModel().getColumn(2).setPreferredWidth(64);
		tablaPartidos.getColumnModel().getColumn(3).setPreferredWidth(200);
		tablaPartidos.getColumnModel().getColumn(4).setPreferredWidth(64);

		// creo un scroll pane y le añado la tabla
		JScrollPane scrollPane = new JScrollPane(tablaPartidos);
		scrollPane.setBounds(25, 210, 583, 300);

		// añado el scroll pane al panel principal
		contentPane.add(scrollPane);
		
		}
		
		else {	//si no hay registros
			
			mensaje = "No hay jornadas cargadas";
			
		}
		
		lblJornadas.setText(mensaje);
	}
}
