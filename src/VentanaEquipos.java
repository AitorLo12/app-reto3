import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;

public class VentanaEquipos extends JFrame implements FocusListener, ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblEquipos;
    private JLabel lblLog;
    private JLabel lblEquipo;
    private JButton btnAtras;
    private JButton btnAtras1;
    private JLabel lblTemporada;
    private JButton btnEquipo;
    private JPanel contentPaneEquipo;
    private JLabel lblNombreEquipo;
    private JLabel lblIcn;
    private JLabel lblIcnEquipo;
    private JLabel lblJugadores;
    private JLabel lblJugadoresIcn;
    private JLabel lblJugadoresNom;
    private JLabel lblEstadio;
    private JLabel lblEstadioEquipo;
    private JLabel lblEquipacion;
    private JLabel lblEquipacionEquipo;
    private JTable TablaJugadoresE;
    private DefaultTableModel dtmTablaJugadoresE;
	private Vector<Vector<String>> datosTablaJugadoresE = new Vector<Vector<String>>();
	private Vector<String> fila;
    private JScrollPane scrollPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaEquipos frame = new VentanaEquipos();
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
    public VentanaEquipos() {

        //establecemos título e icono de la aplicación
        setTitle("Real Federación EspaÑola de Balonmano");
        setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaEquipos.class.getResource("img/logo.png")));
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
        
        contentPaneEquipo = new JPanel();
        contentPaneEquipo.setBackground(new Color(255, 255, 255));
        contentPaneEquipo.setForeground(new Color(0, 0, 0));
        contentPaneEquipo.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPaneEquipo.setLayout(null);

        // asignamos el panel del menú como predeterminado
        setContentPane(contentPane);

        //------------------------------------------------------------Panel Normal----------------------------------------------------------------------//
        
        //creamos y añadimos un JLabel de título
        lblEquipos = new JLabel("Equipos");
        contentPane.add(lblEquipos);

        //propiedades del JLabel
        lblEquipos.setForeground(new Color(0, 0, 0));
        lblEquipos.setFont(new Font("Arial Black", Font.BOLD, 30));
        lblEquipos.setBounds(248, 45, 137, 36);
        lblEquipos.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

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

      	
      	
        int equipoIndex = 0;
        
        for (Equipo equipo : VentanaTemporadas.temporadaSeleccionada.getListaEquipos()) { 
        	
        	

            //creamos y añadimos un botón para poner la imagen del equipo
            btnEquipo = new JButton("");
            contentPane.add(btnEquipo);

            //propiedades del JButton
            btnEquipo.setBounds(68 + (equipoIndex % 3) * 200, 175 + (equipoIndex / 3) * 150, 100, 100);
            btnEquipo.setBackground(null);
            btnEquipo.setBorder(null);
            btnEquipo.setIcon(new ImageIcon(("src/img/"+equipo.getImagenEscudo())));
            
            //añadimos los listeners en los botones 
            btnEquipo.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    //cuando se pulsa el ratón encima cerramos el panel del menú y vamos al panel del equipo
                    contentPane.setVisible(false);
                    contentPaneEquipo.setVisible(true);
                    setContentPane(contentPaneEquipo);
                    lblJugadoresIcn.setIcon(null);
                    lblJugadoresNom.setText("");

                    // Actualizar los componentes del panel del equipo con la información del equipo seleccionado
                    actualizarPanelEquipo(equipo);
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    //Cuando el raton no esta por encima
                    btnEquipo.setBackground(null);
                }
                
                
                
            });
        
            
            
            //creamos y añadimos un JLabel para indicar el nombre del equipo
            lblEquipo = new JLabel(equipo.getNombre());
            contentPane.add(lblEquipo);
        
            //propiedades del JLabel
            lblEquipo.setBounds(68 + (equipoIndex % 3) * 200, 250 + (equipoIndex/3) * 150, 100, 100);
            lblEquipo.setForeground(new Color(0,0,0));
            lblEquipo.setFont(new Font("Arial", Font.BOLD, 14));
            lblEquipo.setHorizontalAlignment(SwingConstants.CENTER);
            
            equipoIndex++;
            
        }
        
        
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
        
//--------------------------------------------------------------Panel Equipo-----------------------------------------------------------------------//

        //creamos y añadimos un JLabel con el nombre del equipo como título
        lblNombreEquipo = new JLabel();
        contentPaneEquipo.add(lblNombreEquipo);

        //propiedades del JLabel
        lblNombreEquipo.setBounds(125, 50, 400, 50);
        lblNombreEquipo.setFont(new Font("Arial Black", Font.BOLD, 30));
        lblNombreEquipo.setHorizontalAlignment(SwingConstants.CENTER);

        //creamos y añadimos un JLabel como título para el logo
        lblIcn = new JLabel("Escudo");
        contentPaneEquipo.add(lblIcn);

        //propiedades del JLabel
        lblIcn.setBounds(30, 125, 100, 25);
        lblIcn.setFont(new Font("Arial Black", Font.PLAIN, 20));

        //creamos y añadimos un JLabel para insertar la foto del logo del equipo
        lblIcnEquipo = new JLabel();
        contentPaneEquipo.add(lblIcnEquipo);

        //propiedades del JLabel
        lblIcnEquipo.setBounds(20, 20, 100, 100);

        //creamos y añadimos un JLabel como título para el estadio
        lblEstadio = new JLabel("Estadio:");
        contentPaneEquipo.add(lblEstadio);

        //propiedades del JLabel
        lblEstadio.setBounds(20, 510, 90, 25);
        lblEstadio.setFont(new Font("Arial Black", Font.PLAIN, 18));

        //creamos y añadimos un JLabel para insertar el nombre del estadio del equipo
        lblEstadioEquipo = new JLabel();
        contentPaneEquipo.add(lblEstadioEquipo);

        //propiedades del JLabel
        lblEstadioEquipo.setBounds(110, 512, 150, 25); 
        lblEstadioEquipo.setFont(new Font("Arial", Font.PLAIN, 15));      

        //creamos y añadimos un JLabel como título para la equipacion
        lblEquipacion= new JLabel("Equipación:");
        contentPaneEquipo.add(lblEquipacion);

        //propiedades del JLabel
        lblEquipacion.setBounds(350, 510, 150, 25);
        lblEquipacion.setFont(new Font("Arial Black", Font.PLAIN, 18));

        //creamos y añadimos un JLabel para insertar el color de la equipacion del equipo
        lblEquipacionEquipo = new JLabel();
        contentPaneEquipo.add(lblEquipacionEquipo);

        //propiedades del JLabel
        lblEquipacionEquipo.setBounds(480, 512, 150, 25);
        lblEquipacionEquipo.setFont(new Font("Arial", Font.PLAIN, 15));                       
        
        //creamos y añadimos un JLabel como título para los jugadores
        lblJugadores = new JLabel("Jugadores");
        contentPaneEquipo.add(lblJugadores);

        //propiedades del JLabel
        lblJugadores.setBounds(110, 170, 150, 30);
        lblJugadores.setFont(new Font("Arial Black", Font.PLAIN, 20));
        
        //creamos y añadimos un JLabel donde pondremos la imagen del jugador seleccionado en la tabla
        lblJugadoresIcn = new JLabel("");
        contentPaneEquipo.add(lblJugadoresIcn);
        
        //propiedades del JLabel
        lblJugadoresIcn.setBounds(515,25,100,100);
        
        //creamos y añadimos un JLabel que indicará el nombre del jugador seleccionado y lo incluirá debajo de su foto
        lblJugadoresNom = new JLabel ("");
        contentPaneEquipo.add(lblJugadoresNom);
        
        //propiedades del JLabel
        lblJugadoresNom.setBounds(515,95,100,100);
        lblJugadoresNom.setHorizontalAlignment(SwingConstants.CENTER);
        lblJugadoresNom.setFont(new Font("Arial", Font.PLAIN, 15));
        
        // si se ha conectado correctamente
     	Vector<String> columnas = new Vector<String>();
     	columnas.add("Nombre");
     	columnas.add("Año Nacimiento");
     	columnas.add("Localidad");
     	columnas.add("Posicion");
     	columnas.add("Imagen");

     	// creo el vector para los datos de la tabla
     	datosTablaJugadoresE = new Vector<Vector<String>>();
     	
     	dtmTablaJugadoresE = new DefaultTableModel(datosTablaJugadoresE, columnas);

		// creo una tabla y le añado el modelo por defecto
		TablaJugadoresE = new JTable(dtmTablaJugadoresE);
		TablaJugadoresE.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPaneEquipo.add(TablaJugadoresE);
		
		//añado el Mouselistener para que ponga los datos seleccionados en los campos de texto
		TablaJugadoresE.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				cogerImagen();
			}

		});

        //creamos y añadimos un JScrollPane que contendrá el JTextArea con la lista de jugadores
        scrollPane = new JScrollPane(TablaJugadoresE);
        scrollPane.setBounds(100, 200, 450, 300);
        contentPaneEquipo.add(scrollPane);

        //creamos y añadimos un botón para volver al inicio
        btnAtras1 = new JButton("");
        contentPaneEquipo.add(btnAtras1);

        //propiedades del JButton
        btnAtras1.setBackground(null);
        btnAtras1.setIcon(new ImageIcon("src/img/atras.png"));
        btnAtras1.setBorder(null);
        btnAtras1.setBounds(576, 517, 30, 30);

        //añadimos los listeners necesarios
        btnAtras1.addFocusListener(this);
        btnAtras1.addActionListener(this);
        btnAtras1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAtras1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {
                //cuando de pasa el ratón por encima
                btnAtras1.setBackground(new Color(212, 212, 212));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //Cuando el raton no esta por encima
                btnAtras1.setBackground(null);
            }

        });

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

    	Object o = e.getSource();
    	
    	if (o == btnAtras) {

            //cuando se pulsa el ratón encima cerramos la ventana actual y volvemos a la ventana de inicio
            VentanaInicio vi = new VentanaInicio();
            vi.setVisible(true);
            dispose();
    		
    	}
    	
    	else if (o == btnAtras1) {
    		
            //cuando se pulsa el ratón encima, ponemos el background de los botones en null, cerramos el panel del equipo actual y volvemos al menú
            btnEquipo.setBackground(null);
            contentPaneEquipo.setVisible(false);
            contentPane.setVisible(true);
            setContentPane(contentPane);
    		
    	}
    }
    
  //Actualizar los componentes del panel del equipo con la información del equipo seleccionado
    public void actualizarPanelEquipo(Equipo equipo) {

    	
    	
        // Asignar el nombre del equipo al JLabel correspondiente
        lblNombreEquipo.setText(equipo.getNombre());
        
        //Creamos los vectores necesarios para crear una tabla con los jugadores del equipo seleccionado
     	Vector<String> columnas = new Vector<String>();
     	columnas.add("Nombre");
     	columnas.add("Año Nacimiento");
     	columnas.add("Localidad");
     	columnas.add("Posicion");
     	columnas.add("Imagen");

     	datosTablaJugadoresE = new Vector<Vector<String>>();
        //recorremos la lista de los jugadores del equipo y lo añadimos a la tabla
        for (Jugador j : equipo.getListaJugadores()) {
        	
        	fila = new Vector<String>();
			fila.add(j.getNombre());
			fila.add(""+j.getAño());
			fila.add(j.getLocalidad());
			fila.add(j.getPosicion());
			fila.add(j.getImagen());
			fila.add("\n\n\n\n\n\n\n");
			datosTablaJugadoresE.add(fila);
        	
        	
        }
        

		dtmTablaJugadoresE = new DefaultTableModel(datosTablaJugadoresE, columnas);
        
        TablaJugadoresE.setModel(dtmTablaJugadoresE);
        
        if (TablaJugadoresE.getRowCount() > 0) {
        
		TablaJugadoresE.setRowHeight(300/TablaJugadoresE.getRowCount());

        }
        
        // Cargar y mostrar la imagen del escudo del equipo
        lblIcnEquipo.setIcon(new ImageIcon("src/img/"+equipo.getImagenEscudo()));

        // Cargamos el nombre del estadio
        lblEstadioEquipo.setText(equipo.getEstadio()+"");;
        
        //Cargamos el color de la equipacion del equipo
        lblEquipacionEquipo.setText(equipo.getEquipacion());


    }
    
    public void cogerImagen() {

		// sacamos en que fila se ha hecho click
		int seleccion = TablaJugadoresE.getSelectedRow();
    	ImageIcon imgc = new ImageIcon("src/img/jugadores/"+dtmTablaJugadoresE.getValueAt(seleccion, 4));
		Image img = imgc.getImage();
		img = img.getScaledInstance(lblJugadoresIcn.getWidth(), lblJugadoresIcn.getHeight(), Image.SCALE_SMOOTH);
		lblJugadoresIcn.setIcon(new ImageIcon(img));
		
		lblJugadoresNom.setText(""+dtmTablaJugadoresE.getValueAt(seleccion, 0));
    	
    }
}
