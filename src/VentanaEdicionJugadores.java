import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaEdicionJugadores extends JFrame implements ActionListener, FocusListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private Vector<Vector<String>> datosTablaJugadores = new Vector<Vector<String>>();
	private Vector<String> fila;
	private JTable tablaJugadores;
	private DefaultTableModel dtmTablaJugadores;
	private JButton btnAtras;
	private JButton btnBorrar;
	private JButton btnAñadir;
	private JButton btnActualizar;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JLabel lblLocalidad;
	private JTextField txtLocalidad;
	private JLabel lblAñoNac;
	private JTextField txtAñoNac;
	private JLabel lblPosicion;
	private JComboBox cmbPosicion;
	private JLabel lblEquipo;
	private JComboBox cmbEquipo;
	private JLabel lblImagen;
	private JTextField txtImagen;
	private JButton btnImagen;
	private JLabel lblImagenJugador;
	private List<String> Equipos;
	public static final Logger LOGGERJ = Logger.getLogger(VentanaResultados.class.getName());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaEdicionEquipos frame = new VentanaEdicionEquipos();
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
	@SuppressWarnings("unchecked")
	public VentanaEdicionJugadores() {

		configureLogger();
		
		// establecemos título e icono de la aplicación
		setTitle("Real Federación EspaÑola de Balonmano");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaTemporadas.class.getResource("/img/Logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ubicación y tamaño de la ventana
		setBounds(100, 100, 1210, 600);
		setLocationRelativeTo(null);

		// quita el redimensionado de la ventana
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// creamos y añadimos un Jlabel para el título de Jugadores
		lblTitulo = new JLabel("Jugadores");
		contentPane.add(lblTitulo);

		// propiedades del JLabel
		lblTitulo.setForeground(new Color(0, 0, 0));
		lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 30));
		lblTitulo.setBounds(508, 15, 180, 40);
		lblTitulo.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

/*-----------------------------------------------BASE DE DATOS MYSQL---------------------------------------------------------------------*/
		
		//me intento conectar a la base de datos mysql para coger los datos de los jugadores y de los equipos de la base de datos mysql
		try {
			
			//me conecto a la base de datos como root
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");


			//CONSULTA PARA COGER LOS DATOS DE LOS JUGADORES
			//creo el Statement para coger las temporadas que haya en la base de datos
			Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			//como es una query, creo un objeto ResultSet 
			ResultSet rs = st.executeQuery("SELECT * FROM balonmano.jugadores WHERE ID_Equipo<1000;");

			// si se ha conectado correctamente
			Vector<String> columnas = new Vector<String>();
			columnas.add("ID Jugador");
			columnas.add("Nombre");
			columnas.add("Localidad");
			columnas.add("Año Nacimiento");
			columnas.add("Posición");
			columnas.add("Equipo");
			columnas.add("Imagen");
			

			// creo el vector para los datos de la tabla
			datosTablaJugadores = new Vector<Vector<String>>();
			
			while (rs.next()) {
				
				fila = new Vector<String>();
				fila.add(rs.getString("ID_Jugador"));
				fila.add(rs.getString("Nombre"));
				fila.add(rs.getString("Localidad"));
				fila.add(rs.getString("Año_Nacimiento"));
				fila.add(rs.getString("Posicion"));

				//CONSULTA PARA COGER EL NOMBRE DEL EQUIPO AL QUE PERTENECE EL JUGADOR
				//creo el Statement para coger las temporadas que haya en la base de datos
				Statement st2 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				//como es una query, creo un objeto ResultSet 
				ResultSet rs2 = st2.executeQuery("SELECT Nom_Equipo FROM balonmano.equipos WHERE ID_Equipo="+rs.getString("ID_Equipo")+";");
				
				while(rs2.next()) {
					
					fila.add(rs2.getString("Nom_Equipo"));	
				}
				
				fila.add(rs.getString("Imagen"));
				fila.add("\n\n\n\n\n\n\n");
				datosTablaJugadores.add(fila);

				rs2.close();
				st2.close();
				
				}

			// creo el DefaultTableModel de la JTable
			dtmTablaJugadores= new DefaultTableModel(datosTablaJugadores, columnas);
			
			rs = st.executeQuery("SELECT Nom_Equipo FROM balonmano.equipos WHERE Num_Temp= 0");
			
			Equipos = new ArrayList<>();
			
			while(rs.next()) {
				
			Equipos.add(rs.getString("Nom_Equipo"));
				
			}
				
			
			
			//Cierro el resultset
			rs.close();
			
			//Cierro el statement 
			st.close();
		
			// cierro la conexion
			conexion.close();
			
			
			}
			
			catch (SQLException e) {
				// si se produce una excepción SQL
				int errorcode = e.getErrorCode();
				
				//si se produce cualquier error sql
				 System.out.println("Error SQL Numero "+e.getErrorCode()+":"+e.getMessage());
				
				
			}
		
		
		/*-------------------------------------------------------------------------------------------------------------------------------------*/
		// creo una tabla y le añado el modelo por defecto
		tablaJugadores = new JTable(dtmTablaJugadores);
		tablaJugadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(tablaJugadores);
		
		//añado el Mouselistener para que ponga los datos seleccionados en los campos de texto
		tablaJugadores.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent me) {
				cogerDatos();
			}

		});

		// creo un scroll pane y le añado la tabla
		JScrollPane scrollPane = new JScrollPane(tablaJugadores);
		scrollPane.setBounds(25, 100, 1150, 400);

		// añado el scroll pane al panel principal
		contentPane.add(scrollPane);

		// creamos y añadimos un botón para volver a la ventanatemporadas
		btnAtras = new JButton("");
		contentPane.add(btnAtras);

		// propiedades del JButton
		btnAtras.setBackground(null);
		btnAtras.setBorder(null);
		btnAtras.setBounds(1145, 511, 30, 30);
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

		// creamos y añadimos un botón para borrar el jugador seleccionado en la tabla
		btnBorrar = new JButton("Borrar jugador");
		contentPane.add(btnBorrar);

		// propiedades del JButton
		btnBorrar.setBackground(new Color(192, 192, 192));
		btnBorrar.setForeground(new Color(0, 0, 0));
		btnBorrar.setBorder(null);
		btnBorrar.setBounds(498, 510, 200, 30);
		btnBorrar.setFont(new Font("Arial Black", Font.BOLD, 15));
		btnBorrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// añadimos los listeners necesarios
		btnBorrar.addActionListener(this);
		btnBorrar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent me) {
				// cuando de pasa el ratón por encima
				btnBorrar.setBackground(new Color(128, 128, 128));
				btnBorrar.setForeground(new Color(255, 255, 255));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				// Cuando el raton no esta por encima
				btnBorrar.setBackground(new Color(192, 192, 192));
				btnBorrar.setForeground(new Color(0, 0, 0));
			}

		});

		// creamos y añadimos un botón para añadir un jugador con los datos introducidos
		btnAñadir = new JButton("Añadir jugador");
		contentPane.add(btnAñadir);

		// propiedades del JButton
		btnAñadir.setBackground(new Color(192, 192, 192));
		btnAñadir.setForeground(new Color(0, 0, 0));
		btnAñadir.setBorder(null);
		btnAñadir.setBounds(258, 510, 200, 30);
		btnAñadir.setFont(new Font("Arial Black", Font.BOLD, 15));
		btnAñadir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// añadimos los listeners necesarios
		btnAñadir.addActionListener(this);
		btnAñadir.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent me) {
				// cuando de pasa el ratón por encima
				btnAñadir.setBackground(new Color(128, 128, 128));
				btnAñadir.setForeground(new Color(255, 255, 255));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				// Cuando el raton no esta por encima
				btnAñadir.setBackground(new Color(192, 192, 192));
				btnAñadir.setForeground(new Color(0, 0, 0));
			}

		});

		// creamos y añadimos un botón para actualizar el jugador con los nuevos datos
		btnActualizar = new JButton("Actualizar jugador");
		contentPane.add(btnActualizar);

		// propiedades del JButton
		btnActualizar.setBackground(new Color(192, 192, 192));
		btnActualizar.setForeground(new Color(0, 0, 0));
		btnActualizar.setBorder(null);
		btnActualizar.setBounds(748, 510, 200, 30);
		btnActualizar.setFont(new Font("Arial Black", Font.BOLD, 15));
		btnActualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// añadimos los listeners necesarios
		btnActualizar.addActionListener(this);
		btnActualizar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent me) {
				// cuando de pasa el ratón por encima
				btnActualizar.setBackground(new Color(128, 128, 128));
				btnActualizar.setForeground(new Color(255, 255, 255));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				// Cuando el raton no esta por encima
				btnActualizar.setBackground(new Color(192, 192, 192));
				btnActualizar.setForeground(new Color(0, 0, 0));
			}

		});

		// creamos y añadimos un Jlabel para indicar el textfield de nombre del jugador
		lblNombre = new JLabel("Nombre:");
		contentPane.add(lblNombre);

		// propiedades del JLabel
		lblNombre.setForeground(new Color(0, 0, 0));
		lblNombre.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblNombre.setBounds(25, 65, 50, 20);
		lblNombre.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// creamos y añadimos un JTextField donde pondremos el nombre del jugador que queramos introducir
		txtNombre = new JTextField();
		contentPane.add(txtNombre);

		// propiedades del JTextField
		txtNombre.setBounds(75, 65, 100, 20);
		txtNombre.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtNombre.setColumns(10);

		// Añadimos los listeners necesarios
		txtNombre.addFocusListener(this);
		
		// creamos y añadimos un Jlabel para indicar el textfield del himno
		lblLocalidad = new JLabel("Localidad:");
		contentPane.add(lblLocalidad);

		// propiedades del JLabel
		lblLocalidad.setForeground(new Color(0, 0, 0));
		lblLocalidad.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblLocalidad.setBounds(185, 65, 65, 20);
		lblLocalidad.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// creamos y añadimos un JTextField donde pondremos el himno que queramos introducir
		txtLocalidad= new JTextField();
		contentPane.add(txtLocalidad);

		// propiedades del JTextField
		txtLocalidad.setBounds(250, 65, 100, 20);
		txtLocalidad.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtLocalidad.setColumns(10);

		// Añadimos los listeners necesarios
		txtLocalidad.addFocusListener(this);

		// creamos y añadimos un Jlabel para indicar el textfield de la equipacion
		lblAñoNac = new JLabel("Año Nacimiento:");
		contentPane.add(lblAñoNac);

		// propiedades del JLabel
		lblAñoNac.setForeground(new Color(0, 0, 0));
		lblAñoNac.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblAñoNac.setBounds(355, 65, 95, 20);
		lblAñoNac.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		

		// creamos y añadimos un JTextField donde pondremos la equipacion que queramos introducir
		txtAñoNac = new JTextField();
		contentPane.add(txtAñoNac);

		// propiedades del JTextField
		txtAñoNac.setBounds(450, 65, 100, 20);
		txtAñoNac.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtAñoNac.setColumns(10);
		txtAñoNac.setHorizontalAlignment(SwingConstants.RIGHT);
		
		// creamos y añadimos un Jlabel para indicar la JComboBox de las posiciones
		lblPosicion = new JLabel("Posición:");
		contentPane.add(lblPosicion);

		// propiedades del JLabel
		lblPosicion.setForeground(new Color(0, 0, 0));
		lblPosicion.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblPosicion.setBounds(555, 65, 55, 20);
		lblPosicion.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
				

		// creamos y añadimos una JComboBox donde pondremos las posiciones que queramos introducir
		String[] Posiciones = {"Portero","Central","Lateral Izquierdo","Lateral Derecho","Mediocentro","Pivote","Mediocentro Ofensivo","Delantero","Extremo Izquierdo","Extremo Derecho"};
		cmbPosicion = new JComboBox<>(Posiciones);
		contentPane.add(cmbPosicion);

		// propiedades de la JComboBox
		cmbPosicion.setBounds(610, 65, 100, 20);
		cmbPosicion.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cmbPosicion.setSelectedIndex(-1);
		
		// creamos y añadimos un Jlabel para indicar la JComboBox de los equipos
		lblEquipo = new JLabel("Equipo:");
		contentPane.add(lblEquipo);

		// propiedades del JLabel
		lblEquipo.setForeground(new Color(0, 0, 0));
		lblEquipo.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblEquipo.setBounds(715, 65, 45, 20);
		lblEquipo.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		
		//creamos y añadimos una JComboBox donde pondremos todos los nombres de los equipos disponibles
		cmbEquipo = new JComboBox<String>();
		contentPane.add(cmbEquipo);

		// propiedades de la JComboBox
		cmbEquipo.setBounds(760, 65, 100, 20);
		cmbEquipo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		//añadimos todos los equipos que hemos recogido antes desde la consulta 
		for (int i = 0; i < Equipos.size(); i++) {
            cmbEquipo.addItem(Equipos.get(i));
        }
		cmbEquipo.setSelectedIndex(-1);
		
		// creamos y añadimos un Jlabel para indicar el textfield del capitán
		lblImagen = new JLabel("Foto:");
		contentPane.add(lblImagen);

		// propiedades del JLabel
		lblImagen.setForeground(new Color(0, 0, 0));
		lblImagen.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblImagen.setBounds(870, 65, 30, 20);
		lblImagen.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		
		// creamos y añadimos un JTextField donde pondremos el path de la imagen del jugador que queramos introducir
		txtImagen = new JTextField();
		contentPane.add(txtImagen);

		// propiedades del JTextField
		txtImagen.setBounds(905, 65, 100, 20);
		txtImagen.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtImagen.setColumns(10);
		txtImagen.setEditable(false);
		
		//creamos y añadimos un boton para abrir el navegador de archivos para poder subir una imagen
		btnImagen = new JButton("Seleccionar imagen");
		btnImagen.setFont(new Font("Arial", Font.PLAIN, 11));
		contentPane.add(btnImagen);
		
		//propiedades del JButton
		btnImagen.setBounds(1010, 65, 135, 20);
		btnImagen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		//añado los listeners necesarios
		btnImagen.addActionListener(this);
		
		//creamos y añadimos un JLabel donde se reflejará la imagen seleccionada en la tabla
		lblImagenJugador = new JLabel("");
		contentPane.add(lblImagenJugador);
				
		//propiedades del JLabel
		lblImagenJugador.setBounds(930, 11, 50, 50);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == btnBorrar) {

			// compruebo que haya algun jugador seleccionado en la tabla
			int filas = tablaJugadores.getSelectedRowCount();
			if (filas < 0) {
				// si no hay ningun elemento seleccionado
				JOptionPane.showMessageDialog(this, "Error, no hay ningun elemento seleccionado.","Ningun elemento seleccionado", JOptionPane.ERROR_MESSAGE, null);

			}

			else {
				
				//me intento conectar a la base de datos mysql para borrar el jugador seleccionado
				try {
					
					int fila = tablaJugadores.getSelectedRow();
					String ID = (String) dtmTablaJugadores.getValueAt(fila, 0);
					//me conecto a la base de datos como root
					Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");


					//CONSULTA PARA ELIMINAR EL JUGADOR SELECCIONADO
					//creo el Statement para eliminar el jugador que esté seleccionado en la tabla
					Statement st = conexion.createStatement();
					
					st.executeUpdate("DELETE FROM balonmano.jugadores WHERE ID_Jugador="+ID+" ;");
					
					//Cierro el statement 
					st.close();
				
					// cierro la conexion
					conexion.close();
					
					// lo borro de la tabla
					LOGGERJ.info("Se ha eliminado el jugador '"+dtmTablaJugadores.getValueAt(fila, 1)+"'.");
					dtmTablaJugadores.removeRow(fila);

					// informamos del borrado
					JOptionPane.showMessageDialog(this, "Se ha eliminado el jugador correctamente", "Jugador borrado correctamente", JOptionPane.INFORMATION_MESSAGE, null);


					// Establecemos los valores de los txt a campos vacíos
					txtNombre.setText("");
					txtLocalidad.setText("");
					txtAñoNac.setText("");
					cmbPosicion.setSelectedIndex(-1);
					cmbEquipo.setSelectedIndex(-1);
					txtImagen.setText("");
					
					
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

		else if (o == btnAtras) {

			VentanaTemporadas vt = new VentanaTemporadas();
			vt.setVisible(true);
			dispose();

		}

		else if (o == btnAñadir) {
			
			
			
			if (txtNombre.getText().isEmpty() || txtLocalidad.getText().isEmpty() || txtAñoNac.getText().isEmpty() || cmbPosicion.getSelectedIndex() <0 || cmbEquipo.getSelectedIndex() <0 || txtImagen.getText().isEmpty() ) {

				// si los campos están vacíos
				JOptionPane.showMessageDialog(this, "Rellena todos los campos para crear un jugador nuevo.","Error, campo(s) vacío(s)", JOptionPane.ERROR_MESSAGE, null);
				
			}
			
			else if (existeEquipo(dtmTablaJugadores,txtNombre.getText(),0)) {
				
				// si existe algun jugador con ese nombre
				JOptionPane.showMessageDialog(this, "Ya existe un jugador con este nombre.","Error, inserción de equipo fallida", JOptionPane.ERROR_MESSAGE, null);
				
			}
			
			else {
				
				//Se crean variables que guardan los datos de los campos para poder manipular correctamente con estos
				String Nombre = txtNombre.getText();
				String Localidad = txtLocalidad.getText();
				String Nacimiento = txtAñoNac.getText();
				String Posicion  = (String)cmbPosicion.getSelectedItem();
				String Equipo = (String)cmbEquipo.getSelectedItem();
				String Imagen = txtImagen.getText();
				
				//me intento conectar a la base de datos mysql para añadir el jugador deseado
				try {
					
					//me conecto a la base de datos como root
					Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");


					//CONSULTA PARA AÑADIR UN JUGADOR NUEVO
					//creo el Statement para coger la id mas grande que haya en la base de datos para crear una nueva id
					Statement st = conexion.createStatement();
					ResultSet rs = st.executeQuery("SELECT MAX(ID_Jugador) FROM balonmano.jugadores WHERE ID_Jugador < 10000;");
					int idJ = 0;
					int idE = 0;
					
					while (rs.next()) {
						
						idJ = Integer.parseInt(rs.getString("MAX(ID_Jugador)"));
						idJ = idJ + 1;
						
					}
					
					rs = st.executeQuery("SELECT ID_Equipo FROM balonmano.equipos WHERE Num_Temp=0 AND Nom_Equipo ='"+Equipo+"';");
					
					while (rs.next()) {
						
						idE = Integer.parseInt(rs.getString("ID_Equipo"));
						
					}
					
					st.executeUpdate("INSERT INTO balonmano.jugadores VALUES ("+idJ+",'"+Nombre+"','"+Posicion+"','"+Localidad+"','"+Nacimiento+"',"+idE+",'Por definir','"+Imagen+"');");
					
					//Cierro el resultset
					rs.close();
					
					//Cierro el statement 
					st.close();
				
					// cierro la conexion
					conexion.close();
					
					

					//si lo ha insertado correctamente en la base de datos
					//lo inserto en la tabla
					fila = new Vector<String>();
					fila.add(""+idJ);
					fila.add(Nombre);
					fila.add(Localidad);
					fila.add(Nacimiento);
					fila.add(Posicion);
					fila.add(Equipo);
					fila.add(Imagen);
					fila.add("\n\n\n\n\n\n\n");
					dtmTablaJugadores.addRow(fila);
					

				    LOGGERJ.info("Se ha creado el jugador '"+Nombre+"'.");
					JOptionPane.showMessageDialog(this,"Jugador '"+Nombre+"' creado correctamente.","Creación exitosa",JOptionPane.INFORMATION_MESSAGE,null);
					

					// Establecemos los valores de los txt a campos vacíos
					txtNombre.setText("");
					txtLocalidad.setText("");
					txtAñoNac.setText("");
					cmbPosicion.setSelectedIndex(-1);
					cmbEquipo.setSelectedIndex(-1);
					txtImagen.setText("");
					
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

		else if (o == btnActualizar) {

			int filas = tablaJugadores.getSelectedRow();
			
			if (filas < 0) { // compruebo que haya algun jugador seleccionado en la tabla
				// si no hay ningun elemento seleccionado
				JOptionPane.showMessageDialog(this, "Error, no hay ningun elemento seleccionado.","Ningun elemento seleccionado", JOptionPane.ERROR_MESSAGE, null);

			}
			
			else if (txtNombre.getText().isEmpty() || txtLocalidad.getText().isEmpty() || txtAñoNac.getText().isEmpty() || cmbPosicion.getSelectedIndex() <0 || cmbEquipo.getSelectedIndex() <0 || txtImagen.getText().isEmpty()) {

				// si los campos están vacíos
				JOptionPane.showMessageDialog(this, "Rellena todos los campos para modificar un jugador.","Error, campo(s) vacío(s)", JOptionPane.ERROR_MESSAGE, null);
				
			}
			
			else {
				
				//Se crean variables que guardan los datos de los campos para poder manipular correctamente con estos
				String Nombre = txtNombre.getText();
				String Localidad = txtLocalidad.getText();
				String Nacimiento = txtAñoNac.getText();
				String Posicion  = (String)cmbPosicion.getSelectedItem();
				String Equipo = (String)cmbEquipo.getSelectedItem();
				String Imagen = txtImagen.getText();
				String id = (String)dtmTablaJugadores.getValueAt(filas, 0);
				String NombreAntes = (String) dtmTablaJugadores.getValueAt(filas, 1);
				int idE = 0;
				int idJ = Integer.parseInt(id);
				
				//me intento conectar a la base de datos mysql para actualizar el jugador deseado
				try {
					
					//me conecto a la base de datos como root
					Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");

					Statement st = conexion.createStatement();
					ResultSet rs = st.executeQuery("SELECT ID_Equipo FROM balonmano.equipos WHERE Num_Temp=0 AND Nom_Equipo ='"+Equipo+"';");
					
					while (rs.next()) {
						
						idE = Integer.parseInt(rs.getString("ID_Equipo"));
						
					}

					//CONSULTA PARA ACTUALIZAR EL JUGADOR SELECCIONADO
					//creo el Statement para actualizar todos los datos que podemos introducir en el jugador seleccionado
					st.executeUpdate("UPDATE balonmano.jugadores SET Nombre='"+Nombre+"',Posicion='"+Posicion+"',Localidad='"+Localidad+"',Año_Nacimiento='"+Nacimiento+"',ID_Equipo='"+idE+"',Capitan='Por definir',Imagen='"+Imagen+"' WHERE ID_Jugador="+idJ+";");
					
					//cierro el resultset
					rs.close();
					
					//Cierro el statement 
					st.close();
				
					// cierro la conexion
					conexion.close();
					
					JOptionPane.showMessageDialog(this,"Jugador '"+NombreAntes+"' actualizado correctamente.","Actualización exitosa",JOptionPane.INFORMATION_MESSAGE,null);
				    LOGGERJ.info("Se ha modificado el jugador'"+NombreAntes+"'.");
					
					//lo actualizamos en la tabla
					dtmTablaJugadores.setValueAt(Nombre, filas, 1);
					dtmTablaJugadores.setValueAt(Localidad, filas, 2);
					dtmTablaJugadores.setValueAt(Nacimiento, filas, 3);
					dtmTablaJugadores.setValueAt(Posicion, filas, 4);
					dtmTablaJugadores.setValueAt(Equipo, filas, 5);
					dtmTablaJugadores.setValueAt(Imagen, filas, 6);
					

					// Establecemos los valores de los txt a campos vacíos
					txtNombre.setText("");
					txtLocalidad.setText("");
					txtAñoNac.setText("");
					cmbPosicion.setSelectedIndex(-1);
					cmbEquipo.setSelectedIndex(-1);
					txtImagen.setText("");
					
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
		
		else if (o == btnImagen) {
			
			JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String fileName = selectedFile.getName(); // Obtener solo el nombre del archivo
                txtImagen.setText(fileName);
                guardarImagen(selectedFile);
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

	// Función para comprobar si un DefaultTableModel contiene un string específico en una columna específica
    public static boolean existeEquipo(DefaultTableModel dtm, String nombre, int columna) {
    	
        int Filas = dtm.getRowCount();
        for (int i = 0; i < Filas; i++) {
            Object valorEnFila = dtm.getValueAt(i, columna);
            if (valorEnFila != null && valorEnFila.toString().equals(nombre)) {
                return true; // Se encontró el string en la columna
            }
        }
        return false; // No se encontró el string en la columna
    }
    
    public void cogerDatos() {
		// sacamos en que fila se ha hecho click
		int seleccion = tablaJugadores.getSelectedRow();
		// si se ha hecho click en una fila
		if (seleccion >= 0) {
			// Establecemos los valores de los txt
			txtNombre.setText((String) dtmTablaJugadores.getValueAt(seleccion, 1));
			txtLocalidad.setText((String) dtmTablaJugadores.getValueAt(seleccion, 2));
			txtAñoNac.setText((String) dtmTablaJugadores.getValueAt(seleccion, 3));
			cmbPosicion.setSelectedItem(dtmTablaJugadores.getValueAt(seleccion, 4));
			cmbEquipo.setSelectedItem(dtmTablaJugadores.getValueAt(seleccion, 5));
			txtImagen.setText((String)dtmTablaJugadores.getValueAt(seleccion, 6));
			ImageIcon imgc = new ImageIcon("src/img/jugadores/"+dtmTablaJugadores.getValueAt(seleccion, 6));
			Image img = imgc.getImage();
			img = img.getScaledInstance(lblImagenJugador.getWidth(), lblImagenJugador.getHeight(), Image.SCALE_SMOOTH);
			lblImagenJugador.setIcon(new ImageIcon(img));
		}
	}
    
    private void guardarImagen(File imageFile) {
        try {
            // Define la carpeta donde se guardarán las imágenes
            String folderPath = "src/img/jugadores/";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdir();
            }
            // Copia el archivo de la imagen seleccionada a la carpeta
            Path sourcePath = imageFile.toPath();
            Path targetPath = Paths.get(folderPath + imageFile.getName());
            Files.copy(sourcePath, targetPath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "La imagen ya existe por lo que se usará la imagen ya almacenada.");
        }
    }
    
	private void configureLogger() { //Configuramos un log para la edicion de los jugadores
	    try {
	        // Ruta del archivo de registro en la carpeta src
	        String logFilePath = "src/logs/logJugadores.txt";

	        // Verificar si el directorio existe, si no, intentar crearlo
	        File logFile = new File(logFilePath);
	        if (!logFile.getParentFile().exists()) {
	            logFile.getParentFile().mkdirs();
	        }

	        // Crear FileHandler con la ruta del archivo de registro
	        FileHandler fileHandler = new FileHandler(logFilePath, true);
	        fileHandler.setFormatter(new SimpleFormatter());
	        LOGGERJ.addHandler(fileHandler);
	        LOGGERJ.setLevel(Level.ALL);
	    } catch (IOException | SecurityException e) {
	        LOGGERJ.log(Level.SEVERE, "Error al configurar el sistema de logging: " + e.getMessage(), e);
	    }
	}
    
}
