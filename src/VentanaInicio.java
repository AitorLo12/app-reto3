import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;

public class VentanaInicio extends JFrame implements FocusListener, ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblInicio;
    private JLabel lblTemporada;
    private JButton btnAtras;
    private JButton btnClasificacion;
    private JButton btnJornadas;
    private JButton btnEquipos;
    private JLabel lblLog;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaInicio frame = new VentanaInicio();
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
    public VentanaInicio() {
        
        //comprobamos si la lista de las jornadas ha sido rellenada anteriormente
        //if (!listaJornadas.isEmpty()) { //si tiene contenido, lo borramos para no añadir datos de forma infinita
            
            //listaJornadas.removeAll(listaJornadas);
            
        //}
        
        //establecemos título e icono de la aplicación
        setTitle("Real Federación EspaÑola de Balonmano");
        setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaInicio.class.getResource("/img/logo.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
        
        //creamos y añadimos un Jlabel para el título de inicio
        lblInicio= new JLabel("Inicio");
        contentPane.add(lblInicio);
        
        //propiedades del JLabel
        lblInicio.setForeground(new Color(0, 0, 0));
        lblInicio.setFont(new Font("Arial Black", Font.BOLD, 30));
        lblInicio.setBounds(269, 45, 98, 30);
        lblInicio.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        
        
        
        //creamos y añadimos un Jlabel para marcar la temporada
        lblTemporada = new JLabel();
        contentPane.add(lblTemporada);
        
        //propiedades del JLabel
        lblTemporada.setForeground(new Color(0, 0, 0));
        lblTemporada.setFont(new Font("Arial Black", Font.PLAIN, 20));
        lblTemporada.setBounds(260, 85, 115, 23);
        lblTemporada.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        
        //Asignamos la temporada 
        lblTemporada.setText("" + VentanaTemporadas.temporadaSeleccionada.getFecha());
        lblTemporada.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        
        //creamos y añadimos un JLabel para mostrar con qué usuario ha iniciado sesión
        lblLog = new JLabel("");
        contentPane.add(lblLog);
        
        //propiedades del JLabel
        lblLog.setForeground(new Color(0, 0, 0));
        lblLog.setFont(new Font("Arial", Font.PLAIN, 14));
        lblLog.setBounds(10, 15, 616, 13);
        lblLog.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        
        //añadimos el texto que queremos a la JLabel
        lblLog.setText("Has iniciado sesión como: " + VentanaRegistro.getNombre() + ".");
        
        
        
        //creamos y añadimos un botón para volver al inicio
        btnAtras = new JButton();
        contentPane.add(btnAtras);
        
        //propiedades del JButton
        btnAtras.setBackground(null);
        btnAtras.setIcon(new ImageIcon("src/img/atras.png"));
        btnAtras.setBorder(null);
        btnAtras.setBounds(576, 517, 30, 30);
        btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //añadimos los listeners necesarios
        btnAtras.addFocusListener(this);
        btnAtras.addActionListener(this);
        btnAtras.addMouseListener(new MouseAdapter(){
            
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
        
        

        //creamos y añadimos un botón para ir a la clasificación
        btnClasificacion = new JButton("Clasificación");
        contentPane.add(btnClasificacion);
        
        //propiedades del JButton
        btnClasificacion.setFont(new Font("Arial", Font.PLAIN, 15));
        btnClasificacion.setForeground(new Color(0, 0, 0));
        btnClasificacion.setBackground(new Color(192, 192, 192));
        btnClasificacion.setBorder(null);
        btnClasificacion.setBounds(228, 200, 180, 55);
        btnClasificacion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        //Añadimos los listeners necesarios
        btnClasificacion.addActionListener(this);
        btnClasificacion.addMouseListener(new MouseAdapter(){
            
            @Override
            public void mouseEntered(MouseEvent me) {
                //cuando de pasa el ratón por encima
                btnClasificacion.setForeground(new Color(255,255,255));
                btnClasificacion.setBackground(new Color(128,128,128));
            }
            @Override
            public void mouseExited(MouseEvent me) {
                //Cuando el raton no esta por encima
                btnClasificacion.setForeground(new Color(0,0,0));
                btnClasificacion.setBackground(new Color(192,192,192));
            }
        
        });
        

        //creamos y añadimos un botón para ir a las jornadas
        btnJornadas = new JButton("Jornadas");
        contentPane.add(btnJornadas);
        
        //propiedades del JButton
        btnJornadas.setForeground(new Color(0,0,0));
        btnJornadas.setFont(new Font("Arial", Font.PLAIN, 15));
        btnJornadas.setBorder(null);
        btnJornadas.setBackground(new Color(192,192,192));
        btnJornadas.setBounds(228, 300, 180, 55);
        btnJornadas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        //Añadimos los listeners necesarios
        btnJornadas.addActionListener(this);
        btnJornadas.addMouseListener(new MouseAdapter(){
            
            @Override
            public void mouseEntered(MouseEvent me) {
                //cuando de pasa el ratón por encima
                btnJornadas.setForeground(new Color(255,255,255));
                btnJornadas.setBackground(new Color(128,128,128));
            }
            @Override
            public void mouseExited(MouseEvent me) {
                //Cuando el raton no esta por encima
                btnJornadas.setForeground(new Color(0,0,0));
                btnJornadas.setBackground(new Color(192,192,192));
            }
        
        });

        
        
        //creamos y añadimos un botón para ir a los equipos
        btnEquipos = new JButton("Equipos");
        contentPane.add(btnEquipos);
        
        //propiedades del JButton
        btnEquipos.setForeground(new Color(0,0,0));
        btnEquipos.setFont(new Font("Arial", Font.PLAIN, 15));
        btnEquipos.setBorder(null);
        btnEquipos.setBackground(new Color(192,192,192));
        btnEquipos.setBounds(228, 400, 180, 55);
        btnEquipos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //Añadimos los listeners necesarios
        btnEquipos.addActionListener(this);
        btnEquipos.addMouseListener(new MouseAdapter(){
            
            @Override
            public void mouseEntered(MouseEvent me) {
                //cuando de pasa el ratón por encima
                btnEquipos.setForeground(new Color(255,255,255));
                btnEquipos.setBackground(new Color(128,128,128));
            }
            @Override
            public void mouseExited(MouseEvent me) {
                //Cuando el raton no esta por encima
                btnEquipos.setForeground(new Color(0,0,0));
                btnEquipos.setBackground(new Color(192,192,192));
            }
        });
        
        
    }


    @Override
    public void focusGained(FocusEvent fe) {
        
        //Se cambia el borde a los objetos que estás seleccionando
        Object o = fe.getSource();
        ((JComponent) o).setBorder(new LineBorder(new Color(150,200,240), 2    )); 
        
    }

    @Override
    public void focusLost(FocusEvent fe) {
        
        //Se pone el borde por defecto a los objetos que no estás seleccionando
        Object o = fe.getSource();
        ((JComponent) o).setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null)); 
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	Object o = e.getSource();
    	
    	if (o == btnAtras) {
    		
    		//cuando se pulsa btnAtras cerramos la ventana actual y volvemos a la ventana de temporadas
            VentanaTemporadas vt = new VentanaTemporadas();
            vt.setVisible(true);
            dispose();
    		
    	}
    	
    	else if (o == btnClasificacion) {
    		
    		//cuando se pulsa btnClasificacion cerramos la ventana actual y vamos a la ventana de cllasificación
            
            VentanaClasificacion vc = new VentanaClasificacion();
            vc.setVisible(true);
            dispose();
    		
    	}
        
    	else if (o == btnJornadas) {
    		
            //cuando se pulsa btnJornadas cerramos la ventana actual y vamos a la ventana de jornadas

        	VentanaResultados vr = new VentanaResultados();
            vr.setVisible(true);
            vr.setLocation(100, 100);
            VentanaClasificacion vc = new VentanaClasificacion();
            vc.setVisible(true);
            vc.setLocation(750, 100);
            vc.setAlwaysOnTop(true);
            dispose();
    		
    	}
    	
    	else if (o == btnEquipos) {
    		
    		 //cuando se pulsa btnEquipos cerramos la ventana actual y vamos a la ventana de Equipos
            
            VentanaEquipos ve = new VentanaEquipos();
            ve.setVisible(true);
            dispose();
    		
    	}
        
    }
}
