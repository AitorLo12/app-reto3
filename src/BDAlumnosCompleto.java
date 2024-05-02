package basesdedatos;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;

public class BDAlumnosCompleto extends JFrame implements ActionListener {

	private static final long serialVersionUID = -20240318L;
	private JPanel contenedor;
	private JButton btnSalir;
	private JButton btnInsertar;
	private JButton btnBorrar;
	private JButton btnActualizar;
	
	private JLabel lblDNI;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JTextField txtDNI;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	
	private JButton btnPrimero;
	private JButton btnAnterior;
	private JLabel lblRegistros;
	private JButton btnSiguiente;
	private JButton btnUltimo;
	
	private JLabel lblGrupo;
	private JTextField txtGrupo;
	
	private Vector<String> columnas;
	private Vector<Vector<String>> datosTabla;
	private DefaultTableModel dtmTabla;
	private Vector<String> columnasC;
	private Vector<Vector<String>> datosTablaC;
	private DefaultTableModel dtmTablaC;
	private JTable tabla;
	private TableRowSorter<DefaultTableModel> metodoOrdenacion;
	private List<RowSorter.SortKey> sortKeys;
	private JScrollPane scrollPane;
	
	private Connection conexion;
	private Statement st;
	private ResultSet rs;
	
	private int posicion;
	private int NumeroRegistros;
	ArrayList<Alumno> arrayList = new ArrayList<>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BDAlumnosCompleto frame = new BDAlumnosCompleto();
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
	public BDAlumnosCompleto() {
		//setType(Type.UTILITY);
		setForeground(new Color(0, 0, 128));
		setFont(new Font("Arial", Font.BOLD, 20));
		setResizable(false);
		setTitle("BDAlumnosCompleto");
		
		setBounds(100, 100, 797, 717);
		contenedor = new JPanel();
		contenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contenedor);
		contenedor.setLayout(null);
		
		lblDNI = new JLabel("DNI");
		lblDNI.setForeground(new Color(0, 0, 128));
		lblDNI.setFont(new Font("Arial", Font.BOLD, 24));
		lblDNI.setBounds(43, 62, 115, 40);
		contenedor.add(lblDNI);
		
		txtDNI = new JTextField();
		txtDNI.setColumns(10);
		txtDNI.setForeground(new Color(0, 0, 128));
		txtDNI.setFont(new Font("Arial", Font.BOLD, 24));
		txtDNI.setSize(400, 40);
		txtDNI.setLocation(168, 62);
		contenedor.add(txtDNI);
		
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setForeground(new Color(0, 0, 128));
		lblNombre.setFont(new Font("Arial", Font.BOLD, 24));
		lblNombre.setLocation(43, 112);
		lblNombre.setSize(115, 40);
		contenedor.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setForeground(new Color(0, 0, 128));
		txtNombre.setFont(new Font("Arial", Font.BOLD, 24));
		txtNombre.setLocation(168, 113);
		txtNombre.setSize(400, 40);
		contenedor.add(txtNombre);
		txtNombre.setColumns(10);
		
		lblApellidos = new JLabel("Apellidos");
		lblApellidos.setForeground(new Color(0, 0, 128));
		lblApellidos.setFont(new Font("Arial", Font.BOLD, 24));
		lblApellidos.setLocation(43, 178);
		lblApellidos.setSize(115, 40);
		contenedor.add(lblApellidos);
		
		txtApellidos = new JTextField();
		txtApellidos.setForeground(new Color(0, 0, 128));
		txtApellidos.setFont(new Font("Arial", Font.BOLD, 24));
		txtApellidos.setLocation(168, 178);
		txtApellidos.setSize(400, 40);
		contenedor.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		lblGrupo = new JLabel("Grupo");
		lblGrupo.setForeground(new Color(0, 0, 128));
		lblGrupo.setFont(new Font("Arial", Font.BOLD, 24));
		lblGrupo.setLocation(43, 246);
		lblGrupo.setSize(115, 40);
		contenedor.add(lblGrupo);
		
		txtGrupo = new JTextField();
		txtGrupo.setForeground(new Color(0, 0, 128));
		txtGrupo.setFont(new Font("Arial", Font.BOLD, 24));
		txtGrupo.setLocation(168, 246);
		txtGrupo.setSize(400, 40);
		contenedor.add(txtGrupo);
		txtGrupo.setColumns(10);
		
		btnInsertar = new JButton("Insertar");
		btnInsertar.setForeground(new Color(0, 0, 128));
		btnInsertar.setFont(new Font("Arial", Font.BOLD, 24));
		btnInsertar.setSize(128, 40);
		btnInsertar.setLocation(589, 62);
		contenedor.add(btnInsertar);
		btnInsertar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInsertar.addActionListener(this);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setForeground(new Color(0, 0, 128));
		btnBorrar.setFont(new Font("Arial", Font.BOLD, 24));
		btnBorrar.setLocation(589, 113);
		btnBorrar.setSize(128, 40);
		contenedor.add(btnBorrar);
		btnBorrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBorrar.addActionListener(this);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.setForeground(new Color(0, 0, 128));
		btnActualizar.setFont(new Font("Arial", Font.BOLD, 20));
		btnActualizar.setLocation(589, 178);
		btnActualizar.setSize(128, 40);
		contenedor.add(btnActualizar);
		btnActualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnActualizar.addActionListener(this);
		
		btnSalir = new JButton("Salir");
		btnSalir.setForeground(new Color(0, 0, 128));
		btnSalir.setFont(new Font("Arial", Font.BOLD, 24));
		btnSalir.setLocation(589, 246);
		btnSalir.setSize(128, 40);
		contenedor.add(btnSalir);
		btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalir.addActionListener(this);
		
		btnPrimero = new JButton("<<");
		btnPrimero.setForeground(new Color(0, 0, 128));
		btnPrimero.setFont(new Font("Arial", Font.BOLD, 24));
		btnPrimero.setLocation(10, 11);
		btnPrimero.setSize(128, 40);
		contenedor.add(btnPrimero);
		btnPrimero.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPrimero.addActionListener(this);
		
		btnAnterior = new JButton("<");
		btnAnterior.setForeground(new Color(0, 0, 128));
		btnAnterior.setFont(new Font("Arial", Font.BOLD, 24));
		btnAnterior.setLocation(148, 11);
		btnAnterior.setSize(128, 40);
		contenedor.add(btnAnterior);
		btnAnterior.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAnterior.addActionListener(this);
		
		lblRegistros = new JLabel("No hay registros");
		lblRegistros.setForeground(new Color(0, 0, 128));
		lblRegistros.setFont(new Font("Arial", Font.BOLD, 24));
		lblRegistros.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistros.setLocation(284, 11);
		lblRegistros.setSize(216, 40);
		contenedor.add(lblRegistros);
		
		btnSiguiente = new JButton(">");
		btnSiguiente.setForeground(new Color(0, 0, 128));
		btnSiguiente.setFont(new Font("Arial", Font.BOLD, 24));
		btnSiguiente.setLocation(510, 11);
		btnSiguiente.setSize(128, 40);
		contenedor.add(btnSiguiente);
		btnSiguiente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSiguiente.addActionListener(this);
		
		btnUltimo = new JButton(">>");
		btnUltimo.setForeground(new Color(0, 0, 128));
		btnUltimo.setFont(new Font("Arial", Font.BOLD, 24));
		btnUltimo.setLocation(646, 11);
		btnUltimo.setSize(128, 40);
		contenedor.add(btnUltimo);
		btnUltimo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUltimo.addActionListener(this);
			
		// creo las columnas de la cabecera para alumnos
		columnas = new Vector<String>();
		columnas.add("DNI");
		columnas.add("Nombre");
		columnas.add("Apellidos");
		columnas.add("Grupo");
		
		// creo el vector para los datos de la JTable
		datosTabla = new Vector<Vector<String>>();
		
		// creo el DefaultTableModel de alumnos
		dtmTabla = new DefaultTableModel(datosTabla, columnas);
		
		// creo las columnas de la cabecera para calificaciones
		columnasC = new Vector<String>();
		columnasC.add("DNI");
		columnasC.add("Codigo Asignatura");
		columnasC.add("Nota");
		
		// creo el vector para los datos de la JTable de calificaciones
		datosTablaC = new Vector<Vector<String>>();
			
		// creo el DefaultTableModel de calificaciones
		dtmTablaC = new DefaultTableModel(datosTablaC, columnasC);
		
		// creo la JTable de calificaciones
		tabla = new JTable(dtmTablaC);
		tabla.setFont(new Font("Arial", Font.BOLD, 20));
		tabla.setForeground(new Color(0,0,128));
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowHeight(30);
		
		scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(43, 421, 685, 246);
		contenedor.add(scrollPane);
		
		//cargo los datos desde la base de datos
				//en un arraylist
				
				
				try {
					
					Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
					
					// si se ha conectado correctamente
					System.out.println("Conexión Correcta.");
					
					//creo el Statement 
					Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					
					//como es una query, creo un objeto ResultSet 
					ResultSet rs = st.executeQuery("SELECT * FROM bdalumnos.alumnos;");
					
					//añado uno a uno los alumnos al Arraylist
					Alumno valor;
					String dni;
					String nombre;
					String apellidos;
					String grupo;
					
					while (rs.next()) {
						// creo un nuevo Alumno por cada registro
						dni = rs.getString("dni");
						nombre = rs.getString("nombre");
						apellidos = rs.getString("apellidos");
						grupo = rs.getString("grupo");
						valor = new Alumno (new Persona(dni,nombre,apellidos),grupo);
						//lo añado al arrayList
						arrayList.add(valor);
					}
					
					//Cierro el resultset
					rs.close();
					
					//Cierro el statement 
					st.close();
				
					// cierro la conexion
					conexion.close();
					
					//inicializo NumeroRegistros
					NumeroRegistros = arrayList.size();
					
				}
			
					catch (SQLException e) {
						// si se produce una excepción SQL
						int errorcode = e.getErrorCode();
						if (errorcode == 1062) {
						 // si es un error de clave duplicada
						 System.out.println("Error Clave Duplicada. Ya existe un registro con esa clave.");
						}
						else {
						//si se produce cualquier otro error sql
						 System.out.println("Error SQL Numero "+e.getErrorCode()+":"+e.getMessage());
						}
						

						//inicializo posicion
						NumeroRegistros = 0;
						
					}

				//inicializo posicion
				posicion = 0;	
			
				//llamo a la funcion de actualizar los campos
				ActualizarCampos();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (NumeroRegistros > 0) {
		
		if (e.getSource() == btnSalir) {
			
			System.exit(0);;
			
		}
		
		else if (e.getSource() == btnInsertar) {
			
			//compruebo si el dni está vacío
			String dni = txtDNI.getText();
			if (dni.isEmpty()) { //si el dni está vacío
				
				JOptionPane.showMessageDialog(this,"El campo de dni está vacío.","Error al insertar",JOptionPane.ERROR_MESSAGE,null);
				
			}
			
			else {
				//compruebo si ya está en el arraylist
				String nombre = txtNombre.getText();
				String apellidos = txtApellidos.getText();
				String grupo = this.txtGrupo.getText();
				Alumno valor = new Alumno (new Persona (dni,nombre,apellidos), grupo);
				if (arrayList.contains(valor)) {	//si ya está en el arraylist
					
					JOptionPane.showMessageDialog(this,"El registro introducido ya existe.","Registro ya existente",JOptionPane.ERROR_MESSAGE,null);
					
					
				}
				else {	//si no está en el arraylist
			
			
			//para conectarnos a la base de datos MySQL de nombre bdalumnos que se encuentra en el equipo, con el usuario root y contraseña ""
			try {
				
				conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
				
				//creo el Statement 
				st = conexion.createStatement();
				
				//introduzco un nuevo registro
				String consulta = "INSERT INTO bdalumnos.alumnos VALUES ('"+dni+"','"+nombre+"','"+apellidos+"','"+grupo+"');";
				st.executeUpdate(consulta);
				
				//Cierro el statement 
				st.close();
			
				// cierro la conexion
				conexion.close();
				
				//lo inserto en el arraylist
				arrayList.add(valor);
				
				//actualizo numero registros
				NumeroRegistros++;
				
				//actualizo posicion
				posicion = NumeroRegistros -1;
				
				//actualizo los campos
				ActualizarCampos();
				
				JOptionPane.showMessageDialog(this,"El registro se ha introducido correctamente.","Elemento introducido",JOptionPane.INFORMATION_MESSAGE,null);
				
				
				
				
				
			} 
			catch (SQLException i) {
				// si se produce una excepción SQL
				int errorcode = i.getErrorCode();
				if (errorcode == 1062) {
					// si es un error de clave duplicada
					JOptionPane.showMessageDialog(this,"Error Clave Duplicada. Ya existe un registro con esa clave.","Clave duplicada",JOptionPane.ERROR_MESSAGE,null);
				}
				else {
					//si se produce cualquier otro error sql
					JOptionPane.showMessageDialog(this,"Error SQL Numero "+i.getErrorCode()+":"+i.getMessage(),"Clave duplicada",JOptionPane.ERROR_MESSAGE,null);
				}
		}
		}
				}
		}
		
			else if (e.getSource() == btnBorrar) {
				
				//borro el alumno seleccionado en la tabla
				
				//compruebo si hay registros
				
				if (NumeroRegistros < 0) {
					//si no hay registros
					JOptionPane.showMessageDialog(this,"Error, no hay registros.","No hay registros",JOptionPane.ERROR_MESSAGE,null);
				}
				
				else { 
				
					//compruebo si el dni está vacío
					String dni = txtDNI.getText();
					if (dni.isEmpty()) { //si el dni está vacío
						
						JOptionPane.showMessageDialog(this,"El campo de dni está vacío.","Error al borrar",JOptionPane.ERROR_MESSAGE,null);
						
					}
				}
					
				//para conectarnos a la base de datos MySQL de nombre bdalumnos que se encuentra en el equipo, con el usuario root y contraseña ""
				try {
					
					conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
					
					//creo el Statement 
					st = conexion.createStatement();
					
					//obtengo el dni del registro actual
					String dni = txtDNI.getText();
					
					
					int registrosmodificados = st.executeUpdate("DELETE FROM bdalumnos.alumnos WHERE dni='"+dni+"';");
					//System.out.println("DELETE FROM bdalumnos.alumnos WHERE dni='"+dni+"';");
					
					if (registrosmodificados > 0) {
						
						//si se ha modificado el registro	
						JOptionPane.showMessageDialog(this,"El registro se ha borrado correctamente.","Elemento eliminado",JOptionPane.INFORMATION_MESSAGE,null);
						
						//obtengo los datos del registro actual
						String nombre = txtNombre.getText();
						String apellidos = txtApellidos.getText();
						String grupo = this.txtGrupo.getText();
						Alumno valor = new Alumno (new Persona (dni,nombre,apellidos), grupo);
						
						arrayList.remove(valor);
						NumeroRegistros--;
						posicion = NumeroRegistros -1;
						ActualizarCampos();
						
					}
					
					else {
						
						//si no se ha modificado el registro
						JOptionPane.showMessageDialog(this,"Error, no se ha borrado el registro.","Error al borrar",JOptionPane.ERROR_MESSAGE,null);
						
					}
					
					//Cierro el statement 
					st.close();
				
					// cierro la conexion
					conexion.close();
				} 
				
				catch (SQLException i) {
					// si se produce una excepción SQL
					int errorcode = i.getErrorCode();
					if (errorcode == 1062) {
						// si es un error de clave duplicada
						JOptionPane.showMessageDialog(this,"Error Clave Duplicada. Ya existe un registro con esa clave.","Clave duplicada",JOptionPane.ERROR_MESSAGE,null);
					}
					else {
						//si se produce cualquier otro error sql
						JOptionPane.showMessageDialog(this,"Error SQL Numero "+i.getErrorCode()+":"+i.getMessage(),"Clave duplicada",JOptionPane.ERROR_MESSAGE,null);
					}
			}
				
		
			}
		
			else if (e.getSource()==btnActualizar) {
				
//borro el alumno seleccionado en la tabla
				
				//compruebo si hay registros
				
				if (NumeroRegistros < 0) {
					//si no hay registros
					JOptionPane.showMessageDialog(this,"Error, no hay registros.","No hay registros",JOptionPane.ERROR_MESSAGE,null);
				}
				
				else { 
				
					//compruebo si el dni está vacío
					String dni = txtDNI.getText();
					if (dni.isEmpty()) { //si el dni está vacío
						
						JOptionPane.showMessageDialog(this,"El campo de dni está vacío.","Error al actualizar",JOptionPane.ERROR_MESSAGE,null);
						
					}
				}
					
				//para conectarnos a la base de datos MySQL de nombre bdalumnos que se encuentra en el equipo, con el usuario root y contraseña ""
				try {
					
					conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdalumnos", "root", "");
					
					//creo el Statement 
					st = conexion.createStatement();
					
					//obtengo el dni del registro actual
					String dni = txtDNI.getText();
					String nombre = txtNombre.getText();
					String apellidos = txtApellidos.getText();
					String grupo = this.txtGrupo.getText();
					
					String consulta = "UPDATE bdalumnos.alumnos SET nombre='"+nombre+"',apellidos='"+apellidos+"',grupo='"+grupo+"' WHERE dni='"+dni+"';";
					
					int registrosmodificados = st.executeUpdate(consulta);
					
					if (registrosmodificados > 0) {
						
						//si se ha modificado el registro	
						JOptionPane.showMessageDialog(this,"El registro se ha actualizado correctamente.","Elemento actualizado",JOptionPane.INFORMATION_MESSAGE,null);
						
						//lo actualizo en el arraylist
						Alumno valor = arrayList.get(posicion);
						valor.setNombre(nombre);
						valor.setApellidos(apellidos);
						valor.setGrupo(grupo);
						
					}
					
					else {
						
						//si no se ha modificado el registro
						JOptionPane.showMessageDialog(this,"Error, no se ha actualizado el registro.","Error al actualizar",JOptionPane.ERROR_MESSAGE,null);
						
					}
					
					//Cierro el statement 
					st.close();
				
					// cierro la conexion
					conexion.close();
				} 
				
				catch (SQLException i) {
					// si se produce una excepción SQL
					int errorcode = i.getErrorCode();
					if (errorcode == 1062) {
						// si es un error de clave duplicada
						JOptionPane.showMessageDialog(this,"Error Clave Duplicada. Ya existe un registro con esa clave.","Clave duplicada",JOptionPane.ERROR_MESSAGE,null);
					}
					else {
						//si se produce cualquier otro error sql
						JOptionPane.showMessageDialog(this,"Error SQL Numero "+i.getErrorCode()+":"+i.getMessage(),"Clave duplicada",JOptionPane.ERROR_MESSAGE,null);
					}
			}
				
			}
		
			else if (e.getSource()==btnPrimero) {
				
				if (posicion > 0) {
					//si no está en el primero
					posicion = 0;
					//actualizo los campos
					ActualizarCampos ();
				}
				
			}
		
			else if (e.getSource()==btnAnterior) {
				
				if (posicion > 0) {
					//si no está en el primero
					//voy al anterior
					--posicion;
					//actualizo los campos
					ActualizarCampos ();
				}
				
			}
		
			else if (e.getSource()==btnSiguiente) {
				
				if (posicion < (NumeroRegistros -1)) {
					//si no está en el último
					//voy al siguiente
					++posicion;
					//actualizo los campos
					ActualizarCampos ();
				}
				
			}
		
			else if (e.getSource()==btnUltimo) {
				
				if (posicion < (NumeroRegistros - 1)) {
					//si no está en el último
					//voy al ultimo
					posicion = NumeroRegistros-1;
					//actualizo los campos
					ActualizarCampos ();
				}
				
			}
		}
		else {
			
			//JOptionPane.showMessageDialog(this,"Error, no hay registros.","No hay registros",JOptionPane.ERROR_MESSAGE,null);
			
		}
		
			}
	
	private void ActualizarCampos(){
		
		//carga los datos del registro actual en los campos de datos
		String mensaje;
		if (NumeroRegistros > 0) {	//si hay registros
		mensaje = "Registro "+(posicion + 1)+" de "+NumeroRegistros;
		
		//cargo los datos de la posicion actual
		Alumno valor = arrayList.get(posicion);
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
