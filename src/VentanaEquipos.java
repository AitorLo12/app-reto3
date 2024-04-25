import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
    JLabel lblTemporada;
    JButton btnEquipo;

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
        setTitle("NSLA");
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
        
            
            
            //creamos y añadimos un JLabel para indicar el nombre del equipo
            lblEquipo = new JLabel(equipo.getNombre());
            contentPane.add(lblEquipo);
        
            //propiedades del JLabel
            lblEquipo.setBounds(68 + (equipoIndex % 3) * 200, 250 + (equipoIndex/3) * 150, 100, 100);
            lblEquipo.setForeground(new Color(0,0,0));
            lblEquipo.setFont(new Font("Arial", Font.BOLD, 15));
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
            public void mouseClicked(MouseEvent me) {
                //cuando se pulsa el ratón encima cerramos la ventana actual y volvemos a la ventana de inicio

                VentanaInicio vi = new VentanaInicio();
                vi.setVisible(true);
                dispose();
            }

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

    }

}
