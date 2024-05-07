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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;

public class VentanaClasificacion extends JFrame implements FocusListener, ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblClasificacion;
    private JLabel lblTemporada;
    private JLabel lblLog;
    JButton btnAtras;
    private JButton btnPDF;
	private Vector<Vector<String>> datosTablaClasificacion = new Vector<Vector<String>>();
	private Vector<String> fila;
	private JTable tablaClasificacion;
	private DefaultTableModel dtmTablaClasificacion;
	private VentanaResultados vr;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaClasificacion frame = new VentanaClasificacion();
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
    public VentanaClasificacion() {

        //establecemos título e icono de la aplicación
        setTitle("Real Federación EspaÑola de Balonmano");
        setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaClasificacion.class.getResource("img/logo.png")));
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
        
        //Llamamos a la función que crea la clasificación
        crearClasificacion();
        
        //creamos y añadimos un JLabel de título
        lblClasificacion = new JLabel("Clasificación");
        contentPane.add(lblClasificacion);

        //propiedades del JLabel
        lblClasificacion.setForeground(new Color(0, 0, 0));
        lblClasificacion.setFont(new Font("Arial Black", Font.BOLD, 30));
        lblClasificacion.setBounds(204, 45, 230, 36);
        lblClasificacion.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

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
        
      //creamos y añadimos un botón para exportar la clasificación en PDF
        btnPDF = new JButton("Exportar a PDF");
        contentPane.add(btnPDF);

        //propiedades del JButton
		btnPDF.setBackground(new Color(192, 192, 192));
		btnPDF.setForeground(new Color(0, 0, 0));
        btnPDF.setBorder(null);
        btnPDF.setBounds(506, 100, 100, 20);
        btnPDF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));        

        //añadimos los listeners necesarios
        btnPDF.addActionListener(this);
        btnPDF.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {
                //cuando de pasa el ratón por encima
                btnPDF.setBackground(new Color(212, 212, 212));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //Cuando el raton no esta por encima
                btnPDF.setBackground(new Color(192,192,192));
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
    		
                VentanaInicio vi = new VentanaInicio();
                vi.setVisible(true);
                if(!(vr == null)) {
                	
                	vr.dispose();
                }
                
                dispose();	
    	}

    	else if (o == btnPDF) {
    		
    		generarPDF();
    		
    	}
    }
    	
    public void crearClasificacion () {
    	
/*-----------------------------------------------BASE DE DATOS MYSQL---------------------------------------------------------------------*/
		
		//me intento conectar a la base de datos mysql para coger los datos de los equipos de la base de datos mysql
		try {
			
			//me conecto a la base de datos como root
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/balonmano", "root", "");


			//CONSULTA PARA COGER LOS DATOS DE LOS EQUIPOS
			//creo el Statement para coger las temporadas que haya en la base de datos
			Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			//como es una query, creo un objeto ResultSet 
			ResultSet rs = st.executeQuery("SELECT * FROM balonmano.equipos WHERE Num_Temp="+VentanaTemporadas.temporadaSeleccionada.getFecha()+";");

			List <Equipo> listaEquiposClasificacion = new ArrayList<Equipo>();
			
			// si se ha conectado correctamente
			Vector<String> columnas = new Vector<String>();
			columnas.add("Nombre");
			columnas.add("Puntos");
			columnas.add("PJ");
			columnas.add("PG");
			columnas.add("PP");
			columnas.add("GF");
			columnas.add("GC");

			// creo el vector para los datos de la tabla
			datosTablaClasificacion = new Vector<Vector<String>>();
			
			while (rs.next()) {
				
				//creo variables de todos los resultados por cada equipo para poder manipular los datos mejor
				int ID = Integer.parseInt(rs.getString("ID_Equipo"));
				String Nombre = rs.getString("Nom_Equipo");
				int TempEquipo = Integer.parseInt(rs.getString("Num_Temp"));
				int Puntos = Integer.parseInt(rs.getString("Puntos"));
				int PJ = Integer.parseInt(rs.getString("Partidos_Jugados"));
				int PG = Integer.parseInt(rs.getString("Partidos_Ganados"));
				int PP = Integer.parseInt(rs.getString("Partidos_Perdidos"));
				int GF = Integer.parseInt(rs.getString("GF"));
				int GC = Integer.parseInt(rs.getString("GC"));
				
				// creo un nuevo Equipo por cada registro
				Equipo e = new Equipo (ID,Nombre,TempEquipo,Puntos,PJ,PG,PP,GF,GC);

				
				//lo añado a la lista donde están todos los equipos
				
				listaEquiposClasificacion.add(e);
				
				// Ordenar la lista de equipos
			    Collections.sort(listaEquiposClasificacion, (e2, e1) -> {
			        if (e1.getPuntos() != e2.getPuntos()) {
			            return Integer.compare(e1.getPuntos(), e2.getPuntos());
			        } else if (e1.getGolesfavor() != e2.getGolesfavor()){
			            return Integer.compare(e1.getGolesfavor(), e2.getGolesfavor());
			        }
			        else {
			        	return Integer.compare(e2.getGolescontra(), e1.getGolescontra());
			        }
			    });
				
				}
			
			for (Equipo e : listaEquiposClasificacion) {
				
				fila = new Vector<String>();
				fila.add(e.getNombre());
				fila.add(""+e.getPuntos());
				fila.add(""+e.getPjugados());
				fila.add(""+e.getPganados());
				fila.add(""+e.getPperdidos());
				fila.add(""+e.getGolesfavor());
				fila.add(""+e.getGolescontra());
				fila.add("\n\n\n\n\n\n\n");
				datosTablaClasificacion.add(fila);
				
			}
			

			// creo el DefaultTableModel de la JTable
			dtmTablaClasificacion= new DefaultTableModel(datosTablaClasificacion, columnas);
				
		
			//Cierro el resultset
			rs.close();
			
			//Cierro el statement 
			st.close();
		
			// cierro la conexion
			conexion.close();
			
			// creo una tabla y le añado el modelo por defecto
			tablaClasificacion = new JTable(dtmTablaClasificacion);
			tablaClasificacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			contentPane.add(tablaClasificacion);
			tablaClasificacion.setRowHeight(60);
			
			//asignamos el ancho que nosotros queramos a cada columna
	        tablaClasificacion.getColumnModel().getColumn(0).setPreferredWidth(80);
	        tablaClasificacion.getColumnModel().getColumn(1).setPreferredWidth(40); 
	        tablaClasificacion.getColumnModel().getColumn(2).setPreferredWidth(40);
	        tablaClasificacion.getColumnModel().getColumn(3).setPreferredWidth(40);
	        tablaClasificacion.getColumnModel().getColumn(4).setPreferredWidth(40);
	        tablaClasificacion.getColumnModel().getColumn(5).setPreferredWidth(40);
	        tablaClasificacion.getColumnModel().getColumn(6).setPreferredWidth(40);
			
			// creo un scroll pane y le añado la tabla
			JScrollPane scrollPane = new JScrollPane(tablaClasificacion);
			scrollPane.setBounds(25, 125, 583, 384);

			// añado el scroll pane al panel principal
			contentPane.add(scrollPane);
			
			
			
			
			}
			
			catch (SQLException e) {
				// si se produce una excepción SQL
				int errorcode = e.getErrorCode();
				
				//si se produce cualquier error sql
				 System.out.println("Error SQL Numero "+e.getErrorCode()+":"+e.getMessage());
				
				
			}
		
		
		/*-------------------------------------------------------------------------------------------------------------------------------------*/
    	
    }
    
    private void generarPDF() {

		MessageFormat header = new MessageFormat("Clasificación de la temporada " + VentanaTemporadas.temporadaSeleccionada.getFecha());

		MessageFormat footer = new MessageFormat("Real Federación EspaÑola de Balonmano");



		try {


			tablaClasificacion.print(JTable.PrintMode.FIT_WIDTH, header, footer, !rootPaneCheckingEnabled, null, !rootPaneCheckingEnabled);

			JOptionPane.showMessageDialog(this, "Se ha exportado el archivo PDF.","Clasificación exportada a PDF", JOptionPane.INFORMATION_MESSAGE, null);



		} catch (java.awt.print.PrinterException e) {

			JOptionPane.showMessageDialog(this, "Error al exportar el PDF, no se ha seleccionado ninguna ruta.","PDF no exportado",JOptionPane.ERROR_MESSAGE,null);
			
		}

	}

}
