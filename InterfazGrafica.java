
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class InterfazGrafica extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color BORDO = new Color(0x80, 0x08, 0x08);
    private static final Color CREMA = new Color(0xFF, 0xE7, 0xC5);
    private static final Color GRIS_SUAVE = new Color(0xA1, 0x8A, 0x81);
    private static final Color BLANCO = Color.WHITE;

    private final JTextField pantalla;
    private final JTextArea areaInfo;
    private final JLabel labelEstado;
    private final Map<String, JButton> botones;

    public InterfazGrafica() {

        botones = new LinkedHashMap<>();

        setTitle("Alejo's Pistacho - Cotizador e Interactivo");
        setSize(550, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(CREMA);

        // ENCABEZADO
        JPanel panelHeader = new JPanel(new GridLayout(2, 1));
        panelHeader.setBackground(BORDO);

        JLabel lblTitulo = new JLabel(
                "Alejo's Pistacho",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 22));
        lblTitulo.setForeground(BLANCO);

        JLabel lblEslogan = new JLabel(
                "Delicias en tu mesa | Sabores Argentino-Colombianos",
                SwingConstants.CENTER
        );

        lblEslogan.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblEslogan.setForeground(CREMA);

        panelHeader.add(lblTitulo);
        panelHeader.add(lblEslogan);

        add(panelHeader, BorderLayout.NORTH);

        // PANEL CENTRAL
        JPanel panelCentro = new JPanel(new BorderLayout(5, 5));
        panelCentro.setBackground(CREMA);

        pantalla = new JTextField("0 COP");
        pantalla.setFont(new Font("SansSerif", Font.BOLD, 24));
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setEditable(false);
        pantalla.setBackground(BLANCO);
        pantalla.setForeground(BORDO);

        panelCentro.add(pantalla, BorderLayout.NORTH);

        areaInfo = new JTextArea();
        areaInfo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        areaInfo.setEditable(false);
        areaInfo.setLineWrap(true);
        areaInfo.setWrapStyleWord(true);
        areaInfo.setBackground(BLANCO);
        areaInfo.setForeground(Color.DARK_GRAY);

        JScrollPane scrollInfo = new JScrollPane(areaInfo);
        scrollInfo.setPreferredSize(new Dimension(500, 150));

        panelCentro.add(scrollInfo, BorderLayout.CENTER);

        labelEstado = new JLabel(
                "Presiona INFO (CICLO) para recorrer el catalogo e historia",
                SwingConstants.CENTER
        );

        labelEstado.setFont(new Font("SansSerif", Font.BOLD, 11));
        labelEstado.setForeground(BORDO);

        panelCentro.add(labelEstado, BorderLayout.SOUTH);

        add(panelCentro, BorderLayout.CENTER);

        // PANEL DE BOTONES
        JPanel panelBotones = new JPanel(
                new GridLayout(6, 3, 4, 4)
        );

        panelBotones.setBackground(CREMA);

        String[] textosBotones = {
            "INFO (CICLO)", "CHEF ALEJO", "COTIZAR",
            "EMPANADAS", "PALOS QUESO", "ALFAJORES",
            "PAN CHOCOLATE", "PAN MASA MADRE", "CHIMICHURRI",
            "GALLETAS", "PASTELES", "C (LIMPIAR)",
            "WHATSAPP", "INSTAGRAM", "PEDIR WHATSAPP"
        };

        for (String texto : textosBotones) {

            JButton btn = new JButton(texto);

            btn.setFont(new Font("SansSerif", Font.BOLD, 10));

            personalizarBoton(btn, texto);

            botones.put(texto, btn);

            panelBotones.add(btn);
        }

        add(panelBotones, BorderLayout.SOUTH);

        // INFORMACION INICIAL
        areaInfo.setText("""
                Bienvenidos a Alejo's Pistacho

                Combinacion de cultura argentina y colombiana con sazon artesanal.

                Ubicacion: Cl. 47 #84-42, La Floresta (A 1 cuadra del parque).

                Horario: Lunes a Viernes de 8:00 am a 6:00 pm.

                WhatsApp: 3052158681 | IG: @Alejo_pistacho

                Selecciona los productos abajo para armar la cotizacion.
                """);
    }

    private void personalizarBoton(JButton btn, String texto) {

        if (texto.equals("INFO (CICLO)")
                || texto.equals("CHEF ALEJO")
                || texto.equals("PEDIR WHATSAPP")) {

            btn.setBackground(BORDO);
            btn.setForeground(BLANCO);

        } else if (texto.equals("WHATSAPP")
                || texto.equals("INSTAGRAM")) {

            btn.setBackground(new Color(0x00, 0x80, 0x69));
            btn.setForeground(BLANCO);

        } else if (texto.equals("C (LIMPIAR)")) {

            btn.setBackground(Color.RED);
            btn.setForeground(BLANCO);

        } else if (texto.equals("COTIZAR")) {

            btn.setBackground(new Color(0x55, 0x6B, 0x2F));
            btn.setForeground(BLANCO);

        } else if (Character.isDigit(texto.charAt(0))) {

            btn.setBackground(BLANCO);
            btn.setForeground(BORDO);

        } else {

            btn.setBackground(GRIS_SUAVE);
            btn.setForeground(BLANCO);
        }
    }

    // DEVUELVE LOS BOTONES PARA CONECTARLOS CON ACCIONES.JAVA
    public Collection<JButton> getBotones() {
        return botones.values();
    }

    // RESTABLECE LA INFORMACION INICIAL
    public void mostrarInformacionInicial() {

        areaInfo.setText("""
                Bienvenidos a Alejo's Pistacho

                Combinacion de cultura argentina y colombiana con sazon artesanal.

                Ubicacion: Cl. 47 #84-42, La Floresta (A 1 cuadra del parque).

                Horario: Lunes a Viernes de 8:00 am a 6:00 pm.

                WhatsApp: 3052158681 | IG: @Alejo_pistacho

                Selecciona los productos abajo para armar la cotizacion.
                """);
    }

    public void setTextoPantalla(String texto) {
        pantalla.setText(texto);
    }

    public String getTextoPantalla() {
        return pantalla.getText();
    }

    public void setInformacion(String texto) {
        areaInfo.setText(texto);
    }

    public void setEstado(String texto) {
        labelEstado.setText(texto);
    }
}

