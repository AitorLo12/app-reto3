import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import java.awt.Toolkit;

public class VentanaRegistro extends JFrame implements FocusListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPaneL;
	private JPanel contentPaneR;
	private JLabel lblLogin;
	private JLabel lblNombreLog;
	private JLabel lblPassLog;
	private JTextField txtNombreLog;
	private JPasswordField txtPassLog;
	private JToggleButton btnVisibleLog;
	private JLabel lblRegistrarse;
	private JButton btnLogin;
	private JLabel lblRegistro;
	private JLabel lblNombreReg;
	private JLabel lblPassReg1;
	private JLabel lblPassReg2;
	private JTextField txtNombreReg;
	private JPasswordField txtPassReg1;
	private JPasswordField txtPassReg2;
	private JToggleButton btnVisibleReg1;
	private JToggleButton btnVisibleReg2;
	private JLabel lblLog;
	private JButton btnReg;
	public static List<Usuario> listaUsuarios = new ArrayList<>();
	static Usuario NewReg;
	static String Nombre;
	public static final Logger LOGGER = Logger.getLogger(VentanaRegistro.class.getName());
	
	//creamos la varible para poder llamar a la función de usuario


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistro frame = new VentanaRegistro();
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
	public VentanaRegistro() {
		
		//Creamos un log que guarde la información de ciertas acciones de las que queramos guardar información
		configureLogger(); 		
		
		//establecemos título e icono de la aplicación
		setTitle("Real Federación EspaÑola de Balonmano");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaRegistro.class.getResource("/img/Logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ubicación y tamaño de la ventana
		setBounds(100, 100, 650, 600);
		setLocationRelativeTo(null);
		
		//quita el redimensionado de la ventana
		setResizable(false);
		
/*--------------------------------------------------BASE DE DATOS ORIENTADA A OBJETOS-----------------------------------------------------------*/
		
		
		// Se conecta a la base de datos orientada a objetos para coger los datos de los Usuarios
		// crea una base de datos de balonmano si todavia no existe
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:db/balonmano.odb");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		//Recojo todas las temporadas de la base de datos y los añado a la listaUsuarios
		TypedQuery<Usuario> tq1 = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		listaUsuarios = tq1.getResultList();	
		
		if (listaUsuarios.isEmpty()) {
			
			Usuario u = new Usuario();
			listaUsuarios.add(u);
			em.persist(u);
			
		}
		
		em.getTransaction().commit();
		
		//Cierro la conexión
		em.close();
		emf.close();
		
		
/*-----------------------------------------------------------------------------------------------------------------------------------------------*/		

		
		//creamos un panel para el login
		contentPaneL = new JPanel();
		contentPaneL.setBackground(new Color(255, 255, 255));
		contentPaneL.setForeground(new Color(0, 0, 0));
		contentPaneL.setBorder(new EmptyBorder(5, 5, 5, 5));

		//lo asignamos como predeterminado00
		setContentPane(contentPaneL);
		contentPaneL.setLayout(null);
		
		//creamos un panel para el registro
		contentPaneR = new JPanel();
		contentPaneR.setBackground(new Color(255, 255, 255));
		contentPaneR.setForeground(new Color(0, 0, 0));
		contentPaneR.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPaneR.setLayout(null);
		
	/*----------------------------------------------LOGIN----------------------------------------------------------*/
		
		
		//añadir JLabel de inicio de sesion
		lblLogin = new JLabel ("Iniciar sesión");
		contentPaneL.add(lblLogin);
		
		//propiedades del JLabel
		lblLogin.setBounds(200,60,236,100);
		lblLogin.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		lblLogin.setFont(new Font("Arial Black", Font.BOLD, 30));
		lblLogin.setForeground(new Color(0, 0, 0));
		

		
		//añadir JLabel de nombre login
		lblNombreLog = new JLabel("Nombre");
		contentPaneL.add(lblNombreLog);
		
		//propiedades del JLabel
		lblNombreLog.setBounds(30,170,170,30);
		lblNombreLog.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNombreLog.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		lblNombreLog.setForeground(new Color(0, 0, 0));
		
		
		
		//añadir JLabel de contraseña login
		lblPassLog = new JLabel ("Contraseña");
		contentPaneL.add(lblPassLog);
		
		//propiedades del JLabel
		lblPassLog.setBounds(30,300,343,30);
		lblPassLog.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblPassLog.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		lblPassLog.setForeground(new Color(0, 0, 0));
		
		
		
		//añadir textField de nombre del login
		txtNombreLog = new JTextField ("");
		contentPaneL.add(txtNombreLog);
		
		//propiedades del textfield
		txtNombreLog.setBounds(30,240,550,30);
		txtNombreLog.setFont(new Font("Arial", Font.PLAIN, 20));
		txtNombreLog.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		//Añadimos los listeners necesarios
		txtNombreLog.addActionListener(this);
		txtNombreLog.addFocusListener(this);
		
		
		
		//añadir passwordfield de la contraseña del login
		txtPassLog = new JPasswordField ("");
		contentPaneL.add(txtPassLog);
		
		//propiedades del passwordfield
		txtPassLog.setBounds(30,370,520,30);
		txtPassLog.setFont(new Font("Arial", Font.PLAIN, 20));
		txtPassLog.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		//Añadimos los listeners necesarios
		txtPassLog.addActionListener(this);
		txtPassLog.addFocusListener(this);
		
		
				
		//añadir boton toggle para mostrar la contraseña en el login
		btnVisibleLog = new JToggleButton ();
		btnVisibleLog.setForeground(new Color(0, 0, 0));
		btnVisibleLog.setFont(new Font("Arial", Font.PLAIN, 7));
		btnVisibleLog.setText("Mostrar");
		contentPaneL.add(btnVisibleLog);
		
		//propiedades del botón
		btnVisibleLog.setBounds(550,370,30,30);
		btnVisibleLog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVisibleLog.setBackground(new Color(192, 192, 192));
		btnVisibleLog.setBorder(null);
		
		//Añadimos los listeners necesarios
		btnVisibleLog.addActionListener(this);
		btnVisibleLog.addFocusListener(this);
		
		
		
		//añadir JButton para iniciar sesión con los datos introducidos
		btnLogin = new JButton("Iniciar sesión");
		contentPaneL.add(btnLogin);
		
		//propiedades del Jbutton
		btnLogin.setForeground(new Color(0, 0, 0));
		btnLogin.setBackground(new Color(192, 192, 192));
		btnLogin.setFont(new Font("Arial Black", Font.PLAIN, 20));
		btnLogin.setBorder(null);
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.setBounds(223, 466, 190, 50);
		
		//añadir listeners necesarios
		btnLogin.addActionListener(this);
		btnLogin.addFocusListener(this);
		btnLogin.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent me) {
				//cuando se pulsa el ratón encima
			}
				
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnLogin.setForeground(new Color(255,255,255));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnLogin.setForeground(new Color(0,0,0));
			}
			
		});
	
		//añadir JLabel para registrarse
		lblRegistrarse = new JLabel("<HTML><U>ReSgistrarse</U></HTML>");
		lblRegistrarse.setForeground(new Color(0, 0, 0));
		contentPaneL.add(lblRegistrarse);

		//propiedades del JLabel
		lblRegistrarse.setBounds(30, 420, 68, 13);
		lblRegistrarse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRegistrarse.setFont(new Font("Arial", Font.PLAIN, 12));

		//añadir listeners necesarios
		lblRegistrarse.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent me) {
				//cuando se pulsa el ratón encima se cierra el panel del login y se abre el del registro
				contentPaneL.setVisible(false);
				contentPaneR.setVisible(true);
				setContentPane(contentPaneR);
				//nos aseguramos que los campos de texto tengan fondo blanco y estén en blanco por si se han cambiado anteriormente
				txtNombreReg.setBackground(new Color(255,255,255));
				txtNombreReg.setText("");
				txtPassReg1.setBackground(new Color(255,255,255));
				txtPassReg1.setText("");
				txtPassReg2.setBackground(new Color(255,255,255));
				txtPassReg2.setText("");
			}
				
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				lblRegistrarse.setForeground(new Color(128,128,128));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				lblRegistrarse.setForeground(new Color(0,0,0));
			}
			
		});
		
		
/*----------------------------------------------------REGISTRO-------------------------------------------------*/
		
		//añadir JLabel del título registro
		lblRegistro = new JLabel ("Registro");
		contentPaneR.add(lblRegistro);
		
		//propiedades del JLabel
		lblRegistro.setBounds(226,60,148,100);
		lblRegistro.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		lblRegistro.setFont(new Font("Arial Black", Font.BOLD, 30));
		lblRegistro.setForeground(new Color(0, 0, 0));
		

		
		//añadir JLabel de nombre registro
		lblNombreReg = new JLabel("Nombre");
		contentPaneR.add(lblNombreReg);
		
		//propiedades del JLabel
		lblNombreReg.setBounds(30,140,170,30);
		lblNombreReg.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNombreReg.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		lblNombreReg.setForeground(new Color(0, 0, 0));
		
		
		
		//añadir JLabel de contraseña registro
		lblPassReg1 = new JLabel ("Contraseña");
		contentPaneR.add(lblPassReg1);
		
		//propiedades del JLabel
		lblPassReg1.setBounds(30,260,343,30);
		lblPassReg1.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblPassReg1.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		lblPassReg1.setForeground(new Color(0, 0, 0));
		
		
		
		//añadir JLabel de confirmación de contraseña del registro
		lblPassReg2 = new JLabel ("Confirmar contraseña");
		contentPaneR.add(lblPassReg2);
		
		//propiedades del JLabel
		lblPassReg2.setBounds(30,380,343,30);
		lblPassReg2.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblPassReg2.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		lblPassReg2.setForeground(new Color(0, 0, 0));
		
		
		
		//añadir textField de nombre del registro
		txtNombreReg = new JTextField ();
		contentPaneR.add(txtNombreReg);
		
		//propiedades del textfield
		txtNombreReg.setBounds(30,190,550,30);
		txtNombreReg.setFont(new Font("Arial", Font.PLAIN, 20));
		txtNombreReg.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		//Añadimos los listeners necesarios
		txtNombreReg.addActionListener(this);
		txtNombreReg.addFocusListener(this);
		
		
		
		//añadir passwordfield de la contraseña del registro
		txtPassReg1 = new JPasswordField ();
		contentPaneR.add(txtPassReg1);
		
		//propiedades del passwordfield
		txtPassReg1.setBounds(30,310,520,30);
		txtPassReg1.setFont(new Font("Arial", Font.PLAIN, 20));
		txtPassReg1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		//Añadimos los listeners necesarios
		txtPassReg1.addActionListener(this);
		txtPassReg1.addFocusListener(this);
		
		
		
		//añadir passwordfield de la confirmación de la contraseña del registro
		txtPassReg2 = new JPasswordField ();
		contentPaneR.add(txtPassReg2);
		
		//propiedades del passwordfield
		txtPassReg2.setBounds(30,430,520,30);
		txtPassReg2.setFont(new Font("Arial", Font.PLAIN, 20));
		txtPassReg2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null)); 
		
		//Añadimos los listeners necesarios
		txtPassReg2.addActionListener(this);
		txtPassReg2.addFocusListener(this);
		
		
				
		//añadir boton toggle para mostrar la contraseña en el registro
		btnVisibleReg1 = new JToggleButton ();
		btnVisibleReg1.setForeground(new Color(0, 0, 0));
		btnVisibleReg1.setFont(new Font("Arial", Font.PLAIN, 7));
		btnVisibleReg1.setText("Mostrar");
		contentPaneR.add(btnVisibleReg1);
		
		//propiedades del botón
		btnVisibleReg1.setBounds(550,310,30,30);
		btnVisibleReg1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVisibleReg1.setBackground(new Color(192, 192, 192));
		btnVisibleReg1.setBorder(null);
		
		//Añadimos los listeners necesarios
		btnVisibleReg1.addActionListener(this);
		btnVisibleReg1.addFocusListener(this);
		
		
		
		//añadir boton toggle para mostrar la confirmación de contraseña del registro
		btnVisibleReg2 = new JToggleButton ();
		btnVisibleReg2.setForeground(new Color(0, 0, 0));
		btnVisibleReg2.setFont(new Font("Arial", Font.PLAIN, 7));
		btnVisibleReg2.setText("Mostrar");
		contentPaneR.add(btnVisibleReg2);
		
		//propiedades del botón
		btnVisibleReg2.setBounds(550,430,30,30);
		btnVisibleReg2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVisibleReg2.setBackground(new Color(192, 192, 192));
		btnVisibleReg2.setBorder(null);
		
		//Añadimos los listeners necesarios
		btnVisibleReg2.addActionListener(this);
		btnVisibleReg2.addFocusListener(this);
		
		
		
		//añadir JLabel para volver al inicio de sesión
		lblLog = new JLabel("<HTML><U>Iniciar sesión</U></HTML>");
		lblLog.setForeground(new Color(0, 0, 0));
		contentPaneR.add(lblLog);

		//propiedades del JLabel
		lblLog.setBounds(30, 480, 80, 13);
		lblLog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLog.setFont(new Font("Arial", Font.PLAIN, 12));

		//añadir listeners necesarios
		lblLog.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent me) {
				//cuando se pulsa el ratón encima se cierra el panel del login y se abre el del registro
				contentPaneR.setVisible(false);
				contentPaneL.setVisible(true);
				setContentPane(contentPaneL);
				
				//nos aseguramos que los campos de texto tengan fondo blanco y estén vacíos por si se han cambiado anteriormente
				txtNombreLog.setBackground(new Color(255,255,255));
				txtNombreLog.setText("");
				txtPassLog.setBackground(new Color(255,255,255));
				txtPassLog.setText("");
			}
				
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				lblLog.setForeground(new Color(128,128,128));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				lblLog.setForeground(new Color(0,0,0));
			}
			
		});
		
		
		//añadir JButton para registrarse con los datos introducidos
		btnReg = new JButton("Registrarse");		
		contentPaneR.add(btnReg);
				
		//propiedades del Jbutton
		btnReg.setForeground(new Color(0, 0, 0));
		btnReg.setBackground(new Color(192, 192, 192));
		btnReg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReg.setFont(new Font("Arial Black", Font.PLAIN, 16));
		btnReg.setBorder(null);
		btnReg.setBounds(220, 480, 190, 40);
				
		//añadir listeners necesarios
		btnReg.addActionListener(this);
		btnReg.addFocusListener(this);
		btnReg.addMouseListener(new MouseAdapter(){
		
			
			@Override
			public void mouseClicked(MouseEvent me) {
				//cuando se pulsa el ratón encima
			}
				
			@Override
			public void mouseEntered(MouseEvent me) {
				//cuando de pasa el ratón por encima
				btnReg.setForeground(new Color(255,255,255));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				//Cuando el raton no esta por encima
				btnReg.setForeground(new Color(0,0,0));
			}
			
		});
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
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		
		Object o = ae.getSource();
		
		//Cuando el botón se pulsa se revelan los carácteres del campo de texto de contraseña del login
		if (btnVisibleLog.isSelected()) {
			txtPassLog.setEchoChar((char)0);
			btnVisibleLog.setText("Ocultar");
			btnVisibleLog.setBackground(new Color (224,224,224));
		}
		//Cuando el botón se pulsa de nuevo estos se ocultan de nuevo
		else {
			txtPassLog.setEchoChar('\u25cf');
			btnVisibleLog.setText("Mostrar");
			btnVisibleLog.setBackground(new Color(192, 192, 192));
		}
		
		
		
		//Cuando el botón se pulsa se revelan los carácteres del campo de texto de contraseña del registro
		if (btnVisibleReg1.isSelected()) {
			txtPassReg1.setEchoChar((char)0);
			btnVisibleReg1.setText("Ocultar");
			btnVisibleReg1.setBackground(new Color (224,224,224));
		}
		//Cuando el botón se pulsa de nuevo estos se ocultan de nuevo
		else {
			txtPassReg1.setEchoChar('\u25cf');
			btnVisibleReg1.setText("Mostrar");
			btnVisibleReg1.setBackground(new Color(192, 192, 192));
		}
		
		
		
		//Cuando el botón se pulsa se revelan los carácteres del campo de texto de la confirmación de la contraseña del registro
		if (btnVisibleReg2.isSelected()) {
			txtPassReg2.setEchoChar((char)0);
			btnVisibleReg2.setText("Ocultar");
			btnVisibleReg2.setBackground(new Color (224,224,224));
		}
		//Cuando el botón se pulsa de nuevo estos se ocultan de nuevo
		else {
			txtPassReg2.setEchoChar('\u25cf');
			btnVisibleReg2.setText("Mostrar");
			btnVisibleReg2.setBackground(new Color(192, 192, 192));
		}
		
		//Cuando el boton de login es pulsado o se pulsa enter en cualquiera de los campos
		if (o == btnLogin || o == txtNombreLog || o == txtPassLog) {
			
			//Se crean variables que guardan los datos de los campos para poder manipular correctamente con estos
		
			String nombre = txtNombreLog.getText();
			char [] Pas = txtPassLog.getPassword();
			String pass = new String(Pas);
			NewReg = new Usuario (nombre, pass);
			
			
			//Si algún campo está vacio
			if (NewReg.getNombre().isEmpty() || NewReg.getContraseña().isEmpty()){
				//Cambia el color del fondo de los campos
				if (NewReg.getNombre().isEmpty()) {
					txtNombreLog.setBackground(new Color(255,192,183));
					}
				if (NewReg.getContraseña().isEmpty()) {
					txtPassLog.setBackground(new Color(255,192,183));
					}
				//Monstramos error por pantalla
				JOptionPane.showMessageDialog(null, "Por favor, introduzca sus datos en TODOS los campos.", "Campos Vacios", JOptionPane.ERROR_MESSAGE);

				
				
			}
			
			
			//Si los datos introducidos no coinciden con ningún usuario existente
			else if (!loginCorrecto(NewReg, listaUsuarios)){
				//Monstramos error por pantalla
				JOptionPane.showMessageDialog(null, "Los datos introducidos no coinciden con ningún usuario existente.", "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
			
			}
				
				//Si coinciden, se le redirige a la ventana de temporadas
				else {					
					Nombre = NewReg.getNombre();
					VentanaTemporadas vt = new VentanaTemporadas();
					vt.setVisible(true);
					dispose();
				    LOGGER.info(VentanaRegistro.getNombre()+" ha iniciado sesion");
				}
				
		
			
		}
		
		//Cuando el boton de registrarse es pulsado o se pulsa enter en cualquiera de los campos se crea una variable de clase usuario con los datos introducidos
		if (o == btnReg || o == txtNombreReg || o == txtPassReg1 || o == txtPassReg2) {
			
			//Se crean variables que guardan los datos de los campos para poder manipular correctamente con estos
			String nombre = txtNombreReg.getText();
			char [] Pas1 = txtPassReg1.getPassword();
			char [] Pas2 = txtPassReg2.getPassword();
			String pass1 = new String(Pas1);
			String pass2 = new String(Pas2);
			NewReg = new Usuario (nombre, pass1);
			
			
			//Si algún campo está vacio
			if (nombre.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
				//Cambia el color del fondo de los campos
				if (nombre.isEmpty()) {
					this.txtNombreReg.setBackground(new Color(255,192,183));
					}
				if (pass1.isEmpty()) {
					this.txtPassReg1.setBackground(new Color(255,192,183));
					}
				if (pass2.isEmpty()) {
					this.txtPassReg2.setBackground(new Color(255,192,183));
					}
				//Monstramos error por pantalla
				JOptionPane.showMessageDialog(null, "Por favor, introduzca sus datos en TODOS los campos.", "Campos Vacios", JOptionPane.ERROR_MESSAGE);
			
			}
			
			//Si existe un usuario con este nombre
			else if (existeUser(NewReg.getNombre(), listaUsuarios)) {
				
				//Monstramos error por pantalla
				JOptionPane.showMessageDialog(null, "Ya existe un usuario con este nombre.", "Usuario ya existente", JOptionPane.ERROR_MESSAGE);
				
			}
			
			
			//Si los campos de las contraseñas no coinciden
			else if (!mismaPass(pass1, pass2)){
				//Monstramos error por pantalla
				JOptionPane.showMessageDialog(null, "Las contraseñas introducidas no coinciden.", "Contraseña incorrecta", JOptionPane.ERROR_MESSAGE);
				
			}
			
			else {
				
				//Añadimos el usuario
				añadirUsuario(NewReg);
				//Informamos del registro correcto
				JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.", "Registro completo", JOptionPane.INFORMATION_MESSAGE);
				
				//Regresamos al usuario al panel del login
				contentPaneR.setVisible(false);
				contentPaneL.setVisible(true);
				setContentPane(contentPaneL);
				//nos aseguramos que los campos de texto tengan fondo blanco y estén vacíos por si se han cambiado anteriormente
				txtNombreLog.setBackground(new Color(255,255,255));
				txtNombreLog.setText("");
				txtPassLog.setBackground(new Color(255,255,255));
				txtPassLog.setText("");
				
			}
		}
		
	}
	
	private void configureLogger() {
	    try {
	        // Ruta del archivo de registro en la carpeta src
	        String logFilePath = "src/log.txt";

	        // Verificar si el directorio existe, si no, intentar crearlo
	        File logFile = new File(logFilePath);
	        if (!logFile.getParentFile().exists()) {
	            logFile.getParentFile().mkdirs();
	        }

	        // Crear FileHandler con la ruta del archivo de registro
	        FileHandler fileHandler = new FileHandler(logFilePath, true);
	        fileHandler.setFormatter(new SimpleFormatter());
	        LOGGER.addHandler(fileHandler);
	        LOGGER.setLevel(Level.ALL);
	    } catch (IOException | SecurityException e) {
	        LOGGER.log(Level.SEVERE, "Error al configurar el sistema de logging: " + e.getMessage(), e);
	    }
	}
	
    //Método para comprobar si el login es correcto
    private static boolean loginCorrecto (Usuario NewReg, List<Usuario> listaUsuarios) {
	  for (Usuario usuario : listaUsuarios) {
          if (usuario.getNombre().equals(NewReg.getNombre()) &&
              usuario.getContraseña().equals(NewReg.getContraseña())) {
        	  
        	  //El usuario con esas credenciales existe en la lista entonces devolvemos true y le damos los permisos del usuario encontrado a NewReg
        	  NewReg.setPermisos(usuario.getPermisos());
        	  return true; 
          }
      }
	  //El usuario con esas credenciales no existe en la lista
      return false; 
  }
    
	 //Método para comprobar si un nombre ya existe en la lista
    public static boolean existeUser(String nombre, List<Usuario> listaUsuarios) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombre().equals(nombre)) {
            	//El nombre ya existe en la lista
            	return true; 
            }
        }
        //El nombre no existe en la lista
        return false; 
    }
	private static boolean mismaPass (String pass1, String pass2) {
		
		if (pass1.equals(pass2)) {
			
			//Las contraseñas coindicen
			return true;
		}
		//las contraseñas no coinciden
		return false;	
	}
	
	//Añade el usuario a la lista de usuarios y lo actualiza en la base de datos
	public void añadirUsuario(Usuario Usuario) {
			  	listaUsuarios.add(Usuario);
			  	
			  	// Se conecta a la base de datos
				// crea una base de datos de balonmano si todavia no existe
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:db/balonmano.odb");
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();

				//Añado la temporada a la base de datos orientada a objetos
				em.persist(Usuario);
				
				//Guardo los cambios
				em.getTransaction().commit();
				
				//Cierro la conexión
				em.close();
				emf.close();
			  	
			}
	
	//Getters y setters del nombre

	public static String getNombre() {
		return Nombre;
	}

	public static void setNombre(String nombre) {
		Nombre = nombre;
	}  	
  }


