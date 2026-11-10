
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.swing.JButton;

public class Acciones implements ActionListener {

    private final InterfazGrafica interfaz;
    private final GestorPedido miPedido;

    private int pasoCiclo = 0;
    private boolean botonesConectados = false;

    public Acciones(InterfazGrafica interfaz) {

        this.interfaz = interfaz;
        this.miPedido = new GestorPedido();
    }

    // CONECTA LOS BOTONES CON LOS EVENTOS
    public void conectarBotones() {

        if (botonesConectados) {
            return;
        }

        for (JButton boton : interfaz.getBotones()) {
            boton.addActionListener(this);
        }

        botonesConectados = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String comando = e.getActionCommand();

        switch (comando) {

            case "INFO (CICLO)" -> avanzarCicloInformacion();

            case "CHEF ALEJO" -> {

                interfaz.setInformacion("""
                        SOBRE EL CHEF Y FUNDADOR:

                        Alejandro Pereyra es oriundo de Argentina.
                        En 2023 fundo Alejo's Pistacho como cocina cerrada.

                        MISION:
                        Que cada cliente disfrute sabores frescos y artesanales,
                        con masa bien lograda e ingredientes de calidad.
                        """);

                interfaz.setEstado("Seccion: Chef Alejandro y Mision");
            }

            case "EMPANADAS" ->
                procesarSeleccionProducto("Empanadas de carne", 5000);

            case "PALOS QUESO" ->
                procesarSeleccionProducto("Palos de queso / Tequenos", 4000);

            case "ALFAJORES" ->
                procesarSeleccionProducto("Alfajores artesanales", 3500);

            case "PAN CHOCOLATE" ->
                procesarSeleccionProducto("Pan de Chocolate", 25000);

            case "PAN MASA MADRE" ->
                procesarSeleccionProducto("Pan de Masa Madre", 25000);

            case "CHIMICHURRI" ->
                procesarSeleccionProducto("Salsa Chimichurri artesanal", 20000);

            case "GALLETAS" ->
                procesarSeleccionProducto("Galletas artesanales", 5500);

            case "PASTELES" ->
                procesarSeleccionProducto("Pasteles de la casa", 7000);

            case "COTIZAR" -> mostrarCotizacion();

            case "PEDIR WHATSAPP" -> prepararPedidoWhatsApp();

            case "WHATSAPP" -> abrirWhatsAppGeneral();

            case "INSTAGRAM" -> abrirInstagram();

            case "C (LIMPIAR)" -> limpiarPedido();

            default -> procesarBotonNumerico(comando);
        }
    }

    // AGREGA UN PRODUCTO AL PEDIDO
    private void procesarSeleccionProducto(String nombre, double precio) {

        Producto nuevoItem = new Producto(nombre, precio);

        miPedido.agregarProducto(nuevoItem);

        double total = miPedido.calcularTotal();

        interfaz.setTextoPantalla(
                String.format("$%.0f COP", total)
        );

        interfaz.setInformacion("""
                %s agregado.

                Subtotal actual: $%s COP

                Haz clic en PEDIR WHATSAPP cuando termines.
                """.formatted(
                nombre,
                String.format("%.0f", total)
        ));

        interfaz.setEstado("Producto agregado al pedido");
    }

    // MUESTRA EL RESUMEN DEL PEDIDO
    private void mostrarCotizacion() {

        double totalActual = miPedido.calcularTotal();

        interfaz.setInformacion("""
                RESUMEN DE COTIZACION:

                Items:
                %s

                Total a pagar: $%s COP

                NOTA:
                Para eventos o pedidos por volumen
                requerimos minimo 2 dias de anticipacion.
                """.formatted(
                miPedido.obtenerDetalleTexto(),
                String.format("%.0f", totalActual)
        ));

        interfaz.setEstado("Resumen de cotizacion listo");
    }

    // PREPARA EL PEDIDO PARA ENVIARLO POR WHATSAPP
    private void prepararPedidoWhatsApp() {

        try {

            double totalFinal = miPedido.calcularTotal();

            String mensaje = """
                    Hola! Quiero hacer el siguiente pedido de Alejo's Pistacho:

                    %s

                    Total a pagar: $%s COP

                    Quedo atento para coordinar la entrega o pago. Gracias!
                    """.formatted(
                    miPedido.obtenerDetalleTexto(),
                    String.format("%.0f", totalFinal)
            );

            String mensajeCodificado = URLEncoder.encode(
                    mensaje,
                    StandardCharsets.UTF_8
            );

            String urlWhatsApp =
                    "https://wa.me/573052158681?text=" + mensajeCodificado;

            Desktop.getDesktop().browse(new URI(urlWhatsApp));

            interfaz.setInformacion("""
                    Pedido preparado.

                    Se esta abriendo WhatsApp con tu orden
                    lista para revisar y enviar al 3052158681.
                    """);

            interfaz.setEstado("Pedido preparado para enviar por WhatsApp");

        } catch (IOException | URISyntaxException | UnsupportedOperationException ex) {

            interfaz.setInformacion(
                    "Error al abrir WhatsApp automaticamente. Escribenos al 3052158681 con tu pedido."
            );

            interfaz.setEstado("No se pudo abrir WhatsApp");
        }
    }

    // ABRE EL CHAT GENERAL DE WHATSAPP
    private void abrirWhatsAppGeneral() {

        try {

            String urlWhatsApp =
                    "https://wa.me/573052158681?text=Hola,%20quiero%20informacion%20sobre%20los%20productos%20de%20Alejo's%20Pistacho";

            Desktop.getDesktop().browse(new URI(urlWhatsApp));

        } catch (IOException | URISyntaxException | UnsupportedOperationException ex) {

            interfaz.setInformacion("WhatsApp: 3052158681");
        }

        interfaz.setEstado("Abriendo chat general de WhatsApp");
    }

    // ABRE INSTAGRAM
    private void abrirInstagram() {

        try {

            String urlInstagram =
                    "https://www.instagram.com/Alejo_pistacho/";

            Desktop.getDesktop().browse(new URI(urlInstagram));

        } catch (IOException | URISyntaxException | UnsupportedOperationException ex) {

            interfaz.setInformacion("Instagram: @Alejo_pistacho");
        }

        interfaz.setEstado("Abriendo Instagram");
    }

    // LIMPIA EL PEDIDO
    private void limpiarPedido() {

        miPedido.limpiarPedido();

        interfaz.setTextoPantalla("0 COP");

        interfaz.mostrarInformacionInicial();

        interfaz.setEstado("Cotizacion reiniciada.");
    }

    // PROCESA LOS BOTONES NUMERICOS
    private void procesarBotonNumerico(String comando) {

        if (!comando.matches("\\d+")) {
            return;
        }

        if (interfaz.getTextoPantalla().equals("0 COP")) {

            interfaz.setTextoPantalla(comando);

        } else {

            interfaz.setTextoPantalla(
                    interfaz.getTextoPantalla() + comando
            );
        }
    }

    // CAMBIA LA INFORMACION MOSTRADA
    private void avanzarCicloInformacion() {

        pasoCiclo = (pasoCiclo + 1) % 4;

        switch (pasoCiclo) {

            case 0 -> {

                interfaz.mostrarInformacionInicial();

                interfaz.setEstado("Ciclo 1/4: Ubicacion y Horarios");
            }

            case 1 -> {

                interfaz.setInformacion("""
                        CATALOGO Y PRECIOS:

                        • Pan de Chocolate: $25.000
                        • Masa Madre: $25.000
                        • Chimichurri: $20.000
                        • Pasteles: $7.000
                        • Galletas: $5.500
                        • Empanadas: $5.000
                        • Palos de queso / Tequenos: $4.000
                        • Alfajores: $3.500
                        """);

                interfaz.setEstado("Ciclo 2/4: Catalogo y Precios");
            }

            case 2 -> {

                interfaz.setInformacion("""
                        PREGUNTAS FRECUENTES (FAQ):

                        • ¿Hacen pedidos para eventos?
                          ¡Si! Cubrimos empresas y desayunos.

                        • ¿Con cuanto tiempo pedir?
                          Minimo 2 dias de anticipacion.
                        """);

                interfaz.setEstado("Ciclo 3/4: Preguntas Frecuentes");
            }

            case 3 -> {

                interfaz.setInformacion("""
                        TESTIMONIOS DE CLIENTES:

                        • "Productos de excelente calidad y sabor inigualable."

                        • "La mejor combinacion argentino-colombiana en La Floresta."
                        """);

                interfaz.setEstado("Ciclo 4/4: Testimonios");
            }

            default -> throw new IllegalStateException(
                    "Paso de informacion no valido: " + pasoCiclo
            );
        }
    }
}


// CLASE PRODUCTO
class Producto {

    private final String nombre;
    private final double precio;

    public Producto(String nombre, double precio) {

        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}


// CLASE GESTOR DEL PEDIDO
class GestorPedido {

    private final ArrayList<Producto> listaProductos = new ArrayList<>();

    public void agregarProducto(Producto producto) {

        listaProductos.add(producto);
    }

    public double calcularTotal() {

        double total = 0;

        for (Producto producto : listaProductos) {

            total += producto.getPrecio();
        }

        return total;
    }

    
public String obtenerDetalleTexto() {

    if (listaProductos.isEmpty()) {
        return "Ningun producto seleccionado aun.";
    }

    StringBuilder sb = new StringBuilder();

    for (Producto producto : listaProductos) {

        if (sb.length() > 0) {
            sb.append(", ");
        }

        sb.append(producto.getNombre());
    }

    return sb.toString();
}

    public void limpiarPedido() {

        listaProductos.clear();
    }
}
