import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener; // import java.awt.event.*; sirve para importar todo lo que hay en event
import java.awt.event.ActionEvent;

// Clase HolaSwing que hereda de la clase JFrame
//El fichero se llama como esta clase porque es la única clase pública del archivo
public class HolaSwing extends JFrame{

    // Constructora de HolaSwing
    public HolaSwing(String title) {
        super(title); // Llamada al constructor padre

        _title = title;
    }

    protected void ponBoton(String etiqueta, String mensaje) {

        JButton boton = new JButton(etiqueta);
        add(boton);

        // Función lambda
        boton.addActionListener((ActionEvent e) -> 
                System.out.println(MENSAJE + 
                " Pulsado sobre ventana '" + _title + "' al botón '" + mensaje + "'")
        );
        
        /*boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(MENSAJE + 
                " Pulsado sobre ventana '" + _title + "' al botón '" + mensaje + "'");
            }
        });*/

    }

    public void init() {

        setSize(400, 400);
        setLayout(new java.awt.GridLayout(3, 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cuando se cierre la ventana, la consola dejará de estar bloqueada
        
        ponBoton("Uno", "1");
        ponBoton("Dos", "2");
        ponBoton("Tres", "3");
    }

    private static final String MENSAJE = "AY!!";
    private String _title;

    public static void main(String[] args) {

        HolaSwing ventana = new HolaSwing("Hola Mundo");

        ventana.init();

        ventana.setVisible(true);

        System.out.println("¡Ventana creada!");
        System.out.println("No tengo nada más que hacer.");
    }
}
