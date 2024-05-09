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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaEdicionEquipos extends JFrame implements ActionListener, FocusListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private Vector<Vector<String>> datosTablaEquipos = new Vector<Vector<String>>();
	private Vector<String> fila;
	private JTable tablaEquipos;
	private DefaultTableModel dtmTablaEquipos;
	private JButton btnAtras;
	private JButton btnBorrar;
	private JButton btnAñadir;
	private JButton btnActualizar;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JLabel lblHimno;
	private JTextField txtHimno;
	private JLabel lblEquipacion;
	private JTextField txtEquipacion;
	private JLabel lblEstadio;
	private JTextField txtEstadio;
	private JLabel lblEscudo;
	private JTextField txtEscudo;
	private JButton btnEscudo;
	private JLabel lblImagen;
	public static final Logger LOGGERE = Logger.getLogger(VentanaResultados.class.getName());

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
	public VentanaEdicionEquipos() {

		configureLogger();
		
		// establecemos título e icono de la aplicación
		setTitle("Real Federación EspaÑola de Balonmano");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaTemporadas.class.getResource("/img/Logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ubicación y tamaño de la ventana
		setBounds(100, 100, 1000, 600);
		setLocationRelativeTo(null);

		// quita el redimensionado de la ventana
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// creamos y añadimos un Jlabel para el título de Equipos
		lblTitulo = new JLabel("Equipos");
		contentPane.add(lblTitulo);

		// propiedades del JLabel
		lblTitulo.setForeground(new Color(0, 0, 0));
		lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 30));
		lblTitulo.setBounds(415, 20, 154, 30);
		lblTitulo.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

/*-----------------------------------------------BASE DE DATOS MYSQL---------------------------------------------------------------------*/
		
		//me intento conectar a la base de datos mysql para coger los datos de los equipos de la base de datos mysql
		try {
			
			//me conecto a la base de datos como root
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");


			//CONSULTA PARA COGER LOS DATOS DE LOS EQUIPOS
			//creo el Statement para coger las temporadas que haya en la base de datos
			Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			//como es una query, creo un objeto ResultSet 
			ResultSet rs = st.executeQuery("SELECT * FROM balonmano.equipos WHERE Num_Temp=0;");

			// si se ha conectado correctamente
			Vector<String> columnas = new Vector<String>();
			columnas.add("Nombre");
			columnas.add("Himno");
			columnas.add("Equipacion");
			columnas.add("Estadio");
			columnas.add("Escudo");

			// creo el vector para los datos de la tabla
			datosTablaEquipos = new Vector<Vector<String>>();
			
			while (rs.next()) {
				
				fila = new Vector<String>();
				fila.add(rs.getString("Nom_Equipo"));
				fila.add(rs.getString("Himno"));
				fila.add(rs.getString("Equipacion"));
				fila.add(rs.getString("Estadio"));
				fila.add(rs.getString("Escudo"));
				fila.add("\n\n\n\n\n\n\n");
				datosTablaEquipos.add(fila);
				
				
				}

			// creo el DefaultTableModel de la JTable
			dtmTablaEquipos= new DefaultTableModel(datosTablaEquipos, columnas);
				
			
			
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
		tablaEquipos = new JTable(dtmTablaEquipos);
		tablaEquipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(tablaEquipos);
		
		//añado el Mouselistener para que ponga los datos seleccionados en los campos de texto
		tablaEquipos.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent me) {
				cogerDatos();
			}

		});

		// creo un scroll pane y le añado la tabla
		JScrollPane scrollPane = new JScrollPane(tablaEquipos);
		scrollPane.setBounds(25, 100, 940, 400);

		// añado el scroll pane al panel principal
		contentPane.add(scrollPane);

		// creamos y añadimos un botón para volver a la ventanatemporadas
		btnAtras = new JButton("");
		contentPane.add(btnAtras);

		// propiedades del JButton
		btnAtras.setBackground(null);
		btnAtras.setBorder(null);
		btnAtras.setBounds(935, 511, 30, 30);
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

		// creamos y añadimos un botón para borrar el equipo seleccionado en la tabla
		btnBorrar = new JButton("Borrar equipo");
		contentPane.add(btnBorrar);

		// propiedades del JButton
		btnBorrar.setBackground(new Color(192, 192, 192));
		btnBorrar.setForeground(new Color(0, 0, 0));
		btnBorrar.setBorder(null);
		btnBorrar.setBounds(392, 510, 200, 30);
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

		// creamos y añadimos un botón para añadir un equipo con los datos introducidos
		btnAñadir = new JButton("Añadir equipo");
		contentPane.add(btnAñadir);

		// propiedades del JButton
		btnAñadir.setBackground(new Color(192, 192, 192));
		btnAñadir.setForeground(new Color(0, 0, 0));
		btnAñadir.setBorder(null);
		btnAñadir.setBounds(172, 510, 200, 30);
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

		// creamos y añadimos un botón para actualizar el equipo con los nuevos datos
		btnActualizar = new JButton("Actualizar equipo");
		contentPane.add(btnActualizar);

		// propiedades del JButton
		btnActualizar.setBackground(new Color(192, 192, 192));
		btnActualizar.setForeground(new Color(0, 0, 0));
		btnActualizar.setBorder(null);
		btnActualizar.setBounds(612, 510, 200, 30);
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

		// creamos y añadimos un Jlabel para indicar el textfield de nombre del equipo
		lblNombre = new JLabel("Nombre:");
		contentPane.add(lblNombre);

		// propiedades del JLabel
		lblNombre.setForeground(new Color(0, 0, 0));
		lblNombre.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblNombre.setBounds(25, 65, 50, 20);
		lblNombre.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// creamos y añadimos un JTextField donde pondremos el nombre del equipo que queramos introducir
		txtNombre = new JTextField();
		contentPane.add(txtNombre);

		// propiedades del JTextField
		txtNombre.setBounds(75, 65, 100, 20);
		txtNombre.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtNombre.setColumns(10);

		// Añadimos los listeners necesarios
		txtNombre.addFocusListener(this);
		
		// creamos y añadimos un Jlabel para indicar el textfield del himno
		lblHimno = new JLabel("Himno:");
		contentPane.add(lblHimno);

		// propiedades del JLabel
		lblHimno.setForeground(new Color(0, 0, 0));
		lblHimno.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblHimno.setBounds(185, 65, 40, 20);
		lblHimno.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// creamos y añadimos un JTextField donde pondremos el himno que queramos introducir
		txtHimno= new JTextField();
		contentPane.add(txtHimno);

		// propiedades del JTextField
		txtHimno.setBounds(230, 65, 100, 20);
		txtHimno.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtHimno.setColumns(10);

		// Añadimos los listeners necesarios
		txtHimno.addFocusListener(this);

		// creamos y añadimos un Jlabel para indicar el textfield de la equipacion
		lblEquipacion = new JLabel("Equipacion:");
		contentPane.add(lblEquipacion);

		// propiedades del JLabel
		lblEquipacion.setForeground(new Color(0, 0, 0));
		lblEquipacion.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblEquipacion.setBounds(335, 65, 70, 20);
		lblEquipacion.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		

		// creamos y añadimos un JTextField donde pondremos la equipacion que queramos introducir
		txtEquipacion = new JTextField();
		contentPane.add(txtEquipacion);

		// propiedades del JTextField
		txtEquipacion.setBounds(410, 65, 100, 20);
		txtEquipacion.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtEquipacion.setColumns(10);
		
		// creamos y añadimos un Jlabel para indicar el textfield del estadio
		lblEstadio = new JLabel("Estadio:");
		contentPane.add(lblEstadio);

		// propiedades del JLabel
		lblEstadio.setForeground(new Color(0, 0, 0));
		lblEstadio.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblEstadio.setBounds(520, 65, 50, 20);
		lblEstadio.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
				

		// creamos y añadimos un JTextField donde pondremos el estadio que queramos introducir
		txtEstadio = new JTextField();
		contentPane.add(txtEstadio);

		// propiedades del JTextField
		txtEstadio.setBounds(570, 65, 100, 20);
		txtEstadio.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtEstadio.setColumns(10);
		
		// creamos y añadimos un Jlabel para indicar el textfield de la equipacion
		lblEscudo = new JLabel("Escudo:");
		contentPane.add(lblEscudo);

		// propiedades del JLabel
		lblEscudo.setForeground(new Color(0, 0, 0));
		lblEscudo.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblEscudo.setBounds(675, 65, 50, 20);
		lblEscudo.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		
		// creamos y añadimos un JTextField donde pondremos el path del escudo que queramos introducir
		txtEscudo = new JTextField();
		contentPane.add(txtEscudo);

		// propiedades del JTextField
		txtEscudo.setBounds(725, 65, 100, 20);
		txtEscudo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtEscudo.setColumns(10);
		txtEscudo.setEditable(false);
		
		//creamos y añadimos un boton para abrir el navegador de archivos para poder subir una imagen
		btnEscudo = new JButton("Seleccionar imagen");
		btnEscudo.setFont(new Font("Arial", Font.PLAIN, 11));
		contentPane.add(btnEscudo);
		
		//propiedades del JButton
		btnEscudo.setBounds(830, 65, 135, 20);
		btnEscudo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		//creamos y añadimos un JLabel donde se reflejará la imagen seleccionada en la tabla
		lblImagen = new JLabel("");
		contentPane.add(lblImagen);
		
		//propiedades del JLabel
		lblImagen.setBounds(750, 11, 50, 50);
		
		
		//añado los listeners necesarios
		btnEscudo.addActionListener(this);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == btnBorrar) {

			// compruebo que haya algun equipo seleccionado en la tabla
			int filas = tablaEquipos.getSelectedRowCount();
			if (filas < 0) {
				// si no hay ningun elemento seleccionado
				JOptionPane.showMessageDialog(this, "Error, no hay ningun elemento seleccionado.","Ningun elemento seleccionado", JOptionPane.ERROR_MESSAGE, null);

			}

			else {
				
				//me intento conectar a la base de datos mysql para borrar el equipo seleccionado
				try {
					
					int fila = tablaEquipos.getSelectedRow();
					String Nombre = (String) dtmTablaEquipos.getValueAt(fila, 0);
					//me conecto a la base de datos como root
					Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");


					//CONSULTA PARA ELIMINAR EL EQUIPO SELECCIONADO
					//creo el Statement para eliminar el equipo que esté seleccionado en la tabla
					Statement st = conexion.createStatement();
					
					st.executeUpdate("DELETE FROM balonmano.equipos WHERE Num_Temp=0 AND Nom_Equipo = '"+Nombre+"';");
					
					//Cierro el statement 
					st.close();
				
					// cierro la conexion
					conexion.close();
					
					// lo borro de la tabla
					LOGGERE.info("Se ha borrado el equipo '"+Nombre+"'.");
					dtmTablaEquipos.removeRow(fila);

					// informamos del borrado
					JOptionPane.showMessageDialog(this, "Se ha eliminado el equipo correctamente", "Equipo borrado correctamente", JOptionPane.INFORMATION_MESSAGE, null);


					// Establecemos los valores de los txt a campos vacíos 
					txtNombre.setText("");
					txtHimno.setText("");
					txtEquipacion.setText("");
					txtEstadio.setText("");
					txtEscudo.setText("");
					
					
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
			
			
			
			if (txtNombre.getText().isEmpty() || txtHimno.getText().isEmpty() || txtEquipacion.getText().isEmpty() || txtEstadio.getText().isEmpty() || txtEscudo.getText().isEmpty()) {

				// si los campos están vacíos
				JOptionPane.showMessageDialog(this, "Rellena todos los campos para crear un equipo nuevo.","Error, campo(s) vacío(s)", JOptionPane.ERROR_MESSAGE, null);
				
			}
			
			else if (existeEquipo(dtmTablaEquipos,txtNombre.getText(),0)) {
				
				// si existe algun equipo con ese nombre
				JOptionPane.showMessageDialog(this, "Ya existe un equipo con este nombre.","Error, inserción de equipo fallida", JOptionPane.ERROR_MESSAGE, null);
				
			}
			
			else {
				
				//Se crean variables que guardan los datos de los campos para poder manipular correctamente con estos
				String Nombre = txtNombre.getText();
				String Himno = txtHimno.getText();
				String Equipacion = txtEquipacion.getText();
				String Estadio  = txtEstadio.getText();
				String Escudo = txtEscudo.getText();
				
				//me intento conectar a la base de datos mysql para añadir el equipo deseado
				try {
					
					//me conecto a la base de datos como root
					Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");


					//CONSULTA PARA AÑADIR UN EQUIPO NUEVO
					//creo el Statement para coger la id mas grande que haya en la base de datos para crear una nueva id
					Statement st = conexion.createStatement();
					ResultSet rs = st.executeQuery("SELECT MAX(ID_Equipo) FROM balonmano.equipos WHERE Num_Temp=0;");
					int id = 0;
					
					while (rs.next()) {
						
						id = Integer.parseInt(rs.getString("MAX(ID_Equipo)"));
						id = id + 1;
					
					}
					
					st.executeUpdate("INSERT INTO balonmano.equipos VALUES ("+id+",0,'"+Nombre+"',0,'"+Himno+"','"+Equipacion+"','"+Estadio+"',0,0,0,0,0,'"+Escudo+"');");
					
					//Cierro el resultset
					rs.close();
					
					//Cierro el statement 
					st.close();
				
					// cierro la conexion
					conexion.close();
					

					//si lo ha insertado correctamente en la base de datos
					//lo inserto en la tabla
					fila = new Vector<String>();
					fila.add(Nombre);
					fila.add(Himno);
					fila.add(Equipacion);
					fila.add(Estadio);
					fila.add(Escudo);
					fila.add("\n\n\n\n\n\n\n");
					dtmTablaEquipos.addRow(fila);
					

				    LOGGERE.info("Se ha creado el equipo '"+Nombre+"'.");
					JOptionPane.showMessageDialog(this,"Equipo '"+Nombre+"' creado correctamente.","Creación exitosa",JOptionPane.INFORMATION_MESSAGE,null);
					

					// Establecemos los valores de los txt a campos vacíos
					txtNombre.setText("");
					txtHimno.setText("");
					txtEquipacion.setText("");
					txtEstadio.setText("");
					txtEscudo.setText("");
					
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

			int filas = tablaEquipos.getSelectedRow();
			
			if (filas < 0) { // compruebo que haya algun Equipo seleccionado en la tabla
				// si no hay ningun elemento seleccionado
				JOptionPane.showMessageDialog(this, "Error, no hay ningun elemento seleccionado.","Ningun elemento seleccionado", JOptionPane.ERROR_MESSAGE, null);

			}
			
			else if (txtNombre.getText().isEmpty() || txtHimno.getText().isEmpty() || txtEquipacion.getText().isEmpty() || txtEstadio.getText().isEmpty() || txtEscudo.getText().isEmpty()) {

				// si los campos están vacíos
				JOptionPane.showMessageDialog(this, "Rellena todos los campos para modificar un equipo.","Error, campo(s) vacío(s)", JOptionPane.ERROR_MESSAGE, null);
				
			}
			
			else {
				
				//Se crean variables que guardan los datos de los campos para poder manipular correctamente con estos
				String NombreAntes = (String) dtmTablaEquipos.getValueAt(filas, 0);
				String Nombre = txtNombre.getText();
				String Himno = txtHimno.getText();
				String Equipacion = txtEquipacion.getText();
				String Estadio  = txtEstadio.getText();
				String Escudo = txtEscudo.getText();
				
				//me intento conectar a la base de datos mysql para actualizar el equipo deseado
				try {
					
					//me conecto a la base de datos como root
					Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");


					//CONSULTA PARA ACTUALIZAR EL EQUIPO SELECCIONADO
					//creo el Statement para actualizar todos los datos que podemos introducir en el equipo seleccionado
					Statement st = conexion.createStatement();
					st.executeUpdate("UPDATE balonmano.equipos SET Nom_Equipo='"+Nombre+"',Himno='"+Himno+"',Equipacion='"+Equipacion+"',Estadio='"+Estadio+"',Escudo='"+Escudo+"' WHERE Nom_Equipo='"+NombreAntes+"' AND Num_Temp = 0;");
					
					//Cierro el statement 
					st.close();
				
					// cierro la conexion
					conexion.close();
					

				    LOGGERE.info("Se ha modificado el equipo '"+NombreAntes+"'.");
					JOptionPane.showMessageDialog(this,"Equipo '"+NombreAntes+"' actualizado correctamente.","Actualización exitosa",JOptionPane.INFORMATION_MESSAGE,null);
					
					//lo actualizamos en la tabla
					dtmTablaEquipos.setValueAt(Nombre, filas, 0);
					dtmTablaEquipos.setValueAt(Himno, filas, 1);
					dtmTablaEquipos.setValueAt(Equipacion, filas, 2);
					dtmTablaEquipos.setValueAt(Estadio, filas, 3);
					dtmTablaEquipos.setValueAt(Escudo, filas, 4);
					

					// Establecemos los valores de los txt a campos vacíos
					txtNombre.setText("");
					txtHimno.setText("");
					txtEquipacion.setText("");
					txtEstadio.setText("");
					txtEscudo.setText("");
					
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
		
		else if (o == btnEscudo) {
			
			JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String fileName = selectedFile.getName(); // Obtener solo el nombre del archivo
                txtEscudo.setText(fileName);
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
		int seleccion = tablaEquipos.getSelectedRow();
		// si se ha hecho click en una fila
		if (seleccion >= 0) {
			// Establecemos los valores de los txt
			txtNombre.setText(""+dtmTablaEquipos.getValueAt(seleccion, 0));
			txtHimno.setText(""+dtmTablaEquipos.getValueAt(seleccion, 1));
			txtEquipacion.setText(""+dtmTablaEquipos.getValueAt(seleccion, 2));
			txtEstadio.setText(""+dtmTablaEquipos.getValueAt(seleccion, 3));
			txtEscudo.setText(""+dtmTablaEquipos.getValueAt(seleccion, 4));
			ImageIcon imgc = new ImageIcon("src/img/"+dtmTablaEquipos.getValueAt(seleccion, 4));
			Image img = imgc.getImage();
			img = img.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH);
			lblImagen.setIcon(new ImageIcon(img));
			
		}
	}

    private void guardarImagen(File imageFile) {
        try {
            // Define la carpeta donde se guardarán las imágenes
            String folderPath = "src/img/";
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
    
	private void configureLogger() { //Configuramos un log para la edicion de los equipos
	    try {
	        // Ruta del archivo de registro en la carpeta src
	        String logFilePath = "src/logs/logEquipos.txt";

	        // Verificar si el directorio existe, si no, intentar crearlo
	        File logFile = new File(logFilePath);
	        if (!logFile.getParentFile().exists()) {
	            logFile.getParentFile().mkdirs();
	        }

	        // Crear FileHandler con la ruta del archivo de registro
	        FileHandler fileHandler = new FileHandler(logFilePath, true);
	        fileHandler.setFormatter(new SimpleFormatter());
	        LOGGERE.addHandler(fileHandler);
	        LOGGERE.setLevel(Level.ALL);
	    } catch (IOException | SecurityException e) {
	        LOGGERE.log(Level.SEVERE, "Error al configurar el sistema de logging: " + e.getMessage(), e);
	    }
	}
    
}
