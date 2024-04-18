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
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
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

public class VentanaTemporadas extends JFrame implements FocusListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelEquipos;
	private JLabel lblTemporadas;
	private JLabel lblInfo;
	private JLabel lblInfoEquipos;
	private JLabel lblInfoTemporada;
	private JLabel lblLog;
	private JLabel lblEstado;
	private JButton btnAtras;
	static JTextField txtTemporada;
	static JButton btnAñadir;
	private JButton btnSiguiente;
	static JButton btnBorrar;
	private List<Temporada> listaTemporadas;
	private JList<String> JlistTemporadas;
	public static Temporada temporadaSeleccionada;
	private DefaultListModel<String> dlmListaTemporadas = new DefaultListModel<>();
	public static List<Equipo> listaEquipos;
	public List<Equipo> listaEquiposSeleccionados = new ArrayList<>();
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
		
		//inicializamos tanto la lista donde guardamos las temporadas como el defaultlistmodel
		List <Temporada> listaTemporadas = new ArrayList<Temporada>();
		DefaultListModel<String> dlmListaTemporadas = new DefaultListModel<String>();
		
		
		//comprobamos si la lista está vacía y si lo está añadimos una temporada por defecto
		if (listaTemporadas.isEmpty()) {
			
			//creamos una temporada por defecto y la añadimos a la lista
			Temporada t = new Temporada ();
			añadirTemporada(t);
			
		}
		
		else {
		//recorremos la lista y vamos añadiendo todas las temporadas al defaultlistmodel
		for (Temporada t : listaTemporadas) {
			
			dlmListaTemporadas.addElement(t.getFecha()+" - "+t.getEstado());
			
		}
		}

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
        panelEquipos = new JPanel();
        panelEquipos.setBackground(Color.WHITE);
        panelEquipos.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelEquipos.setBounds(38, 400, 359, 137);
        panelEquipos.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        contentPane.add(panelEquipos);
        
        JScrollPane scrollPane = new JScrollPane(panelEquipos);
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane.setBounds(38, 400, 359, 137);
	    contentPane.add(scrollPane);
	    
	    GridLayout gridLayout = new GridLayout(0, 1);
	    panelEquipos.setLayout(gridLayout);
		
		
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
		lblInfoEquipos.setBounds(38, 370, 464, 18);
		
		
		
		//creamos y añadimos un JLabel con información de como introducir los datos para añadir una temporada nueva
		lblInfoTemporada = new JLabel ("Introduzca datos de la siguiente manera: \"Año - Año\".");
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
		lblLog.setBounds(10, 15, 616, 13);
		lblLog.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		
		//añadimos el texto que queremos a la JLabel
		lblLog.setText("Has iniciado sesión como: " + VentanaRegistro.getNombre() + ".");
		
		//creamos y añadimos un JLabel para indicar el estado de la temporada seleccionada
		lblEstado = new JLabel();
		contentPane.add(lblEstado);
		
		//propiedades del JLabel
		lblEstado.setForeground(new Color(0, 0, 0));
		lblEstado.setFont(new Font("Arial", Font.PLAIN, 14));
		lblEstado.setBounds(250, 100, 616, 13);
		lblEstado.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		
		//Ponemos el texto del estado de la temporada seleccionada
		if (temporadaSeleccionada != null) {
			
		lblEstado.setText("Estado: "+temporadaSeleccionada.getEstado());
		}
		
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
		//Añadimos los listeners necesarios
	
	    
		
		//creamos y añadimos un botón para volver al inicio
		btnAtras = new JButton("ATRAS");
		contentPane.add(btnAtras);
		
		//propiedades del JButton
		btnAtras.setBackground(null);
		btnAtras.setBorder(null);
		btnAtras.setBounds(576, 517, 30, 30);

		//añadimos los listeners necesarios
		btnAtras.addFocusListener(this);
		btnAtras.addActionListener(this);
		btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
			public void mouseClicked(MouseEvent me) {
				//cuando se pulsa el ratón encima cerramos la ventana actual y volvemos a la venta de registro

			
			}
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
		
		//Dependiendo los permisos del usuario introducido se le ocultarán o no los botones para editar las temporadas
		if (VentanaRegistro.NewReg.getPermisos() != "Admin") {
			btnAñadir.setVisible(false);
			btnBorrar.setVisible(false);
			txtTemporada.setVisible(false);
			lblInfoTemporada.setVisible(false);
			panelEquipos.setVisible(false);
			lblInfoEquipos.setVisible(false);
			scrollPane.setVisible(false);
			JlistTemporadas.setBounds(38,206,525,150);
			btnSiguiente.setBounds(38,400,525,100);
			}
			else {
			btnAñadir.setVisible(true);
			btnBorrar.setVisible(true);
			txtTemporada.setVisible(true);
			lblInfoTemporada.setVisible(true);
			panelEquipos.setVisible(true);
			lblInfoEquipos.setVisible(true);
			scrollPane.setVisible(true);
			JlistTemporadas.setBounds(38, 206, 200, 137);
			btnSiguiente.setBounds(290,300,269,45);
			}
		
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
	    
	    if (o == btnAñadir) { //al pulsar el boton añadir
	    	
	    	//creamos una variable donde almacenamos la fecha introducida en el textfield
	    	String fechanueva = txtTemporada.getText();
	    	Temporada t = new Temporada(fechanueva);
	    	
	    	if (txtTemporada.getText().isEmpty()) {
	    		
	    		JOptionPane.showMessageDialog(null, "No ha introducido ninguna fecha.", "Campos vacíos", JOptionPane.ERROR_MESSAGE);
	    		
	    	}
	    	
	    	else if (existeTemporada(txtTemporada.getText())){	//si ya existe una temporada con la fecha introducida
	    			
	    		JOptionPane.showMessageDialog(null, "Ya existe una temporada con la fecha introducida.", "Temporada ya existente", JOptionPane.ERROR_MESSAGE);
				
	    }
	    	
	    	else {		//si no existe ninguna temporada con esa fecha
	    		
	    		//la añadimos a la lista y al defaultlistmodel
	    		listaTemporadas.add(t);
	    		dlmListaTemporadas.addElement(t.getFecha()+" - "+t.getEstado());
	    		
	    		JOptionPane.showMessageDialog(null, "Temporada creada con éxito.", "Temporada añadida", JOptionPane.INFORMATION_MESSAGE,null);
				
	    		
	    	}
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
		  	listaTemporadas.add(temporada);
		  	dlmListaTemporadas.addElement(temporada.getFecha()+" - "+temporada.getEstado());
		  	
		}
		
}


