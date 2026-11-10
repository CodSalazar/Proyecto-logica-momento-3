
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Creamos la interfaz grafica
            InterfazGrafica interfaz = new InterfazGrafica();

            // Creamos el controlador de acciones
            Acciones acciones = new Acciones(interfaz);

            // Conectamos los botones con sus respectivas acciones
            acciones.conectarBotones();

            // Mostramos la ventana
            interfaz.setVisible(true);
        });
    }
}