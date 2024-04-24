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

public class VentanaUsuarios extends JFrame implements ActionListener, FocusListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private Vector<Vector<String>> datosTablaUsuarios = new Vector<Vector<String>>();
	private Vector<String> fila;
	private JTable tablaUsuarios;
	private DefaultTableModel dtmTablaUsuarios;
	private JButton btnAtras;
	private JButton btnBorrar;
	private JButton btnAñadir;
	private JButton btnActualizar;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JLabel lblPass;
	private JTextField txtPass;
	private JLabel lblPermisos;
	private JComboBox<String> cmbPermisos;
	private List<Usuario> listaUsuarios = new ArrayList<Usuario>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaUsuarios frame = new VentanaUsuarios();
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
	public VentanaUsuarios() {

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
		lblTitulo = new JLabel("Usuarios");
		contentPane.add(lblTitulo);

		// propiedades del JLabel
		lblTitulo.setForeground(new Color(0, 0, 0));
		lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 30));
		lblTitulo.setBounds(241, 20, 154, 30);
		lblTitulo.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// Se conecta a la base de datos
		// crea una base de datos si todavia no existe
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:db/balonmano.odb");
		EntityManager em = emf.createEntityManager();

		// si se ha conectado correctamente
		Vector<String> columnas = new Vector<String>();
		columnas.add("Usuarios");
		columnas.add("Permisos");

		// creo el vector para los datos de la tabla
		datosTablaUsuarios = new Vector<Vector<String>>();

		// ejecuto la consulta
		TypedQuery<Usuario> tq1 = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		List<Usuario> results = tq1.getResultList();

		for (Usuario u : results) {

			fila = new Vector<String>();
			fila.add(u.getNombre());
			fila.add(u.getPermisos());
			fila.add("\n\n\n\n\n\n\n");
			datosTablaUsuarios.add(fila);
		}

		// Cierro el EntityManager
		em.close();

		// Cierro el EntityManagerFactory
		emf.close();

		// creo el DefaultTableModel de la JTable
		dtmTablaUsuarios = new DefaultTableModel(datosTablaUsuarios, columnas);

		// creo una tabla y le añado el modelo por defecto
		tablaUsuarios = new JTable(dtmTablaUsuarios);
		tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(tablaUsuarios);

		// creo un scroll pane y le añado la tabla
		JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
		scrollPane.setBounds(25, 100, 583, 400);

		// añado el scroll pane al panel principal
		contentPane.add(scrollPane);

		// creamos y añadimos un botón para volver a la ventanatemporadas
		btnAtras = new JButton("ATRAS");
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

		// creamos y añadimos un botón para volver a la ventanatemporadas
		btnBorrar = new JButton("Borrar usuario");
		contentPane.add(btnBorrar);

		// propiedades del JButton
		btnBorrar.setBackground(new Color(192, 192, 192));
		btnBorrar.setForeground(new Color(0, 0, 0));
		btnBorrar.setBorder(null);
		btnBorrar.setBounds(250, 510, 140, 30);
		btnBorrar.setFont(new Font("Arial Black", Font.BOLD, 12));
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

		// creamos y añadimos un botón para volver a la ventanatemporadas
		btnAñadir = new JButton("Añadir usuario");
		contentPane.add(btnAñadir);

		// propiedades del JButton
		btnAñadir.setBackground(new Color(192, 192, 192));
		btnAñadir.setForeground(new Color(0, 0, 0));
		btnAñadir.setBorder(null);
		btnAñadir.setBounds(100, 510, 140, 30);
		btnAñadir.setFont(new Font("Arial Black", Font.BOLD, 12));
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

		// creamos y añadimos un botón para volver a la ventanatemporadas
		btnActualizar = new JButton("Actualizar usuario");
		contentPane.add(btnActualizar);

		// propiedades del JButton
		btnActualizar.setBackground(new Color(192, 192, 192));
		btnActualizar.setForeground(new Color(0, 0, 0));
		btnActualizar.setBorder(null);
		btnActualizar.setBounds(400, 510, 140, 30);
		btnActualizar.setFont(new Font("Arial Black", Font.BOLD, 12));
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

		// creamos y añadimos un Jlabel para indicar el textfield de nombre de usuario
		lblNombre = new JLabel("Nombre de Usuario");
		contentPane.add(lblNombre);

		// propiedades del JLabel
		lblNombre.setForeground(new Color(0, 0, 0));
		lblNombre.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblNombre.setBounds(25, 65, 120, 20);
		lblNombre.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// creamos y añadimos un JTextField donde pondremos el nombre de usuario nuevo que queramos introducir
		txtNombre = new JTextField();
		contentPane.add(txtNombre);

		// propiedades del JTextField
		txtNombre.setBounds(140, 65, 100, 20);
		txtNombre.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtNombre.setColumns(10);

		// Añadimos los listeners necesarios
		txtNombre.addFocusListener(this);
		
		// creamos y añadimos un Jlabel para indicar el textfield de nombre de usuario
		lblPass = new JLabel("Contraseña");
		contentPane.add(lblPass);

		// propiedades del JLabel
		lblPass.setForeground(new Color(0, 0, 0));
		lblPass.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblPass.setBounds(250, 65, 80, 20);
		lblPass.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// creamos y añadimos un JTextField donde pondremos la contraseña nueva que queramos introducir
		txtPass = new JTextField();
		contentPane.add(txtPass);

		// propiedades del JTextField
		txtPass.setBounds(320, 65, 100, 20);
		txtPass.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtPass.setColumns(10);

		// Añadimos los listeners necesarios
		txtPass.addFocusListener(this);

		// creamos y añadimos un Jlabel para indicar el textfield de nombre de usuario
		lblPermisos = new JLabel("Permisos");
		contentPane.add(lblPermisos);

		// propiedades del JLabel
		lblPermisos.setForeground(new Color(0, 0, 0));
		lblPermisos.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblPermisos.setBounds(430, 65, 70, 20);
		lblPermisos.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		
		// creamos y añadimos una JComboBox nueva que nos mostrara los distintos permisos que queramos introducir
		// creo una variable donde guardo las opciones de la combobox
		String[] Permisos = {"Usuario","Admin","Arbitro"};
		cmbPermisos = new JComboBox<>(Permisos);
		contentPane.add(cmbPermisos);
		
		//propiedades de la JComboBox
		cmbPermisos.setBounds(490,65,100,20);
		cmbPermisos.setBorder(new TitledBorder(null,"",TitledBorder.LEADING,TitledBorder.TOP,null,null));
		cmbPermisos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cmbPermisos.setBackground(new Color(192,192,192));
		cmbPermisos.setSelectedIndex(-1);
		
		//añadimos los listeners necesarios
		cmbPermisos.addFocusListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == btnBorrar) {

			// compruebo que haya algun Usuario seleccionado en la tabla
			int filas = tablaUsuarios.getSelectedRowCount();
			if (filas == 0) {
				// si no hay ningun elemento seleccionado
				JOptionPane.showMessageDialog(this, "Error, no hay ningun elemento seleccionado.","Ningun elemento seleccionado", JOptionPane.ERROR_MESSAGE, null);

			}

			else if (dtmTablaUsuarios.getValueAt(tablaUsuarios.getSelectedRow(), 1).equals("Admin")) {

				// si el elemento seleccionado es un administrador
				JOptionPane.showMessageDialog(this, "No se puede eliminar a un administrador.","Error al eliminar al usuario", JOptionPane.ERROR_MESSAGE, null);

			}

			else {
				// Se conecta a la base de datos
				// crea una base de datos si todavia no existe
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:db/balonmano.odb");
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				// creo una consulta
				Query q;
				String consulta;
				String nombre;

				// obtengo la posicion a borrar en la tabla
				int borrar = tablaUsuarios.getSelectedRow();

				// lo borro de la base de datos
				nombre = (String) dtmTablaUsuarios.getValueAt(borrar, 0);
				consulta = "DELETE FROM Usuario u WHERE u.Nombre = '" + nombre + "'";
				q = em.createQuery(consulta);
				q.executeUpdate();

				// lo borro de la tabla
				dtmTablaUsuarios.removeRow(borrar);

				// informamos del borrado
				JOptionPane.showMessageDialog(this, "Se ha eliminado el usuario correctamente",
						"Usuario borrado correctamente", JOptionPane.INFORMATION_MESSAGE, null);

				// guardo los cambios de la base de datos
				em.getTransaction().commit();
			}

		}

		else if (o == btnAtras) {

			VentanaTemporadas vt = new VentanaTemporadas();
			vt.setVisible(true);
			dispose();

		}

		else if (o == btnAñadir) {
			
			//Se crean variables que guardan los datos de los campos para poder manipular correctamente con estos
			String Nombre = txtNombre.getText();
			String Pass = txtPass.getText();
			String Permisos = (String) cmbPermisos.getSelectedItem();
			Usuario Usuario = new Usuario (Nombre, Pass,Permisos);
			
			// Se conecta a la base de datos
			// crea una base de datos si todavia no existe
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:db/balonmano.odb");
			EntityManager em = emf.createEntityManager();
			
			// ejecuto la consulta para coger los nombres de los usuarios que tenemos en la base de datos
			TypedQuery<Usuario> tq1 = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
			listaUsuarios = tq1.getResultList();
			
			if (txtNombre.getText().isEmpty() || txtPass.getText().isEmpty() || cmbPermisos.getSelectedIndex() <0) {

				// si los campos están vacíos
				JOptionPane.showMessageDialog(this, "Rellena todos los campos para crear un usuario.","Error, campo(s) vacío(s)", JOptionPane.ERROR_MESSAGE, null);
				
			}
			
			else if (existeUsuario(Usuario.getNombre())) {
				
				// si existe un usuario con ese nombre
				JOptionPane.showMessageDialog(this, "Ya existe un usuario con este nombre.","Error, inserción de usuario fallida", JOptionPane.ERROR_MESSAGE, null);
				
				
			}
			
			else {
				
				em.getTransaction().begin();
				em.persist(Usuario);
				em.getTransaction().commit();
				JOptionPane.showMessageDialog(this,"Usuario '"+Usuario.getNombre()+"' creado correctamente.","Creación exitosa",JOptionPane.INFORMATION_MESSAGE,null);
				
				//si lo ha insertado correctamente en la base de datos
				//lo inserto en la tabla
				fila = new Vector<String>();
				fila.add(Usuario.getNombre());
				fila.add(Usuario.getPermisos());
				fila.add("\n\n\n\n\n\n\n");
				dtmTablaUsuarios.addRow(fila);
				
				
			}
			
			em.close();
			emf.close();

		}

		else if (o == btnActualizar) {

			int filas = tablaUsuarios.getSelectedRow();
			
			if (filas == 0) { // compruebo que haya algun Usuario seleccionado en la tabla
				// si no hay ningun elemento seleccionado
				JOptionPane.showMessageDialog(this, "Error, no hay ningun elemento seleccionado.","Ningun elemento seleccionado", JOptionPane.ERROR_MESSAGE, null);

			}
			
			else if (txtNombre.getText().isEmpty() || txtPass.getText().isEmpty() || cmbPermisos.getSelectedIndex() <0) {

				// si los campos están vacíos
				JOptionPane.showMessageDialog(this, "Rellena todos los campos para modificar un usuario.","Error, campo(s) vacío(s)", JOptionPane.ERROR_MESSAGE, null);
				
			}
			
			else {
				
				//Se crean variables que guardan los datos de los campos para poder manipular correctamente con estos
				Query q;
				String consulta;
				String NombreAntes = (String) dtmTablaUsuarios.getValueAt(filas, 0);
				String Nombre = txtNombre.getText();
				String Pass = txtPass.getText();
				String Permisos = (String) cmbPermisos.getSelectedItem();
				
				// Se conecta a la base de datos
				// crea una base de datos si todavia no existe
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:db/balonmano.odb");
				EntityManager em = emf.createEntityManager();
				
				em.getTransaction().begin();
				
				//lo actualizo en la base de datos
				consulta = "UPDATE Usuario u SET Nombre = '"+Nombre+"', Contraseña = '"+Pass+"', Permisos = '"+Permisos+"', Correo ='"+Nombre+"@gmail.com' WHERE u.Nombre = '"+NombreAntes+"'";
				q = em.createQuery(consulta);
				q.executeUpdate();
				//lo actualizo la tabla
				dtmTablaUsuarios.setValueAt(Nombre, filas, 0);
				dtmTablaUsuarios.setValueAt(Permisos,filas , 1);
				
				em.getTransaction().commit();
				JOptionPane.showMessageDialog(this,"Usuario '"+NombreAntes+"' ha sido modificado correctamente.","Modificación exitosa",JOptionPane.INFORMATION_MESSAGE,null);
				
				em.close();
				emf.close();
				
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

	public boolean existeUsuario(String nombre) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }
	
}
