import javax.swing.*;
import java.awt.*;

public class Testing extends JPanel {
    private int x = 200; // posición inicial del rectángulo en el eje X
    private final int y = 100; // posición en el eje Y
    private final int ANCHO = 50; // ancho del rectángulo
    private final int ALTO = 30; // alto del rectángulo
    private final int LIMITE_IZQUIERDO = x - 100; // límite izquierdo
    private final int LIMITE_DERECHO = x + 100; // límite derecho
    private int velocidad = 5; // velocidad del movimiento
    private boolean moviendoDerecha = true; // dirección de movimiento
    private long rightTime, leftTime;
    private long startTime = 0;

    public Testing() {
        // Crear un hilo para mover el rectángulo
        Thread hiloMovimiento = new Thread(() -> {
            while (true) {
                if (moviendoDerecha) {
                    // Mover hacia la derecha
                    x += velocidad;
                    if (x + ANCHO >= LIMITE_DERECHO) {
                        long currentTime = System.currentTimeMillis();
                        rightTime = currentTime - startTime;
                        System.out.println("Tiempo hacia la derecha: " + rightTime + " ms");
                        startTime = currentTime;
                        moviendoDerecha = false; // Cambiar dirección
                    }
                } else {
                    // Mover hacia la izquierda
                    x -= velocidad;
                    if (x <= LIMITE_IZQUIERDO) {
                        long currentTime = System.currentTimeMillis();
                        leftTime = currentTime - startTime;
                        System.out.println("Tiempo hacia la izquierda: " + leftTime + " ms");
                        startTime = currentTime;
                        moviendoDerecha = true; // Cambiar dirección
                    }
                }
                repaint(); // Redibujar el panel para actualizar la posición
                try {
                    Thread.sleep(10); // Pausa para crear el movimiento visible
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        hiloMovimiento.start(); // Iniciar el hilo de movimiento
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED); // Color del rectángulo
        g.fillRect(x, y, ANCHO, ALTO); // Dibujar el rectángulo en la posición actual
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Movimiento de Rectángulo");
        Testing panel = new Testing();
        ventana.setSize(500, 300); // Tamaño de la ventana
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.add(panel);
        ventana.setVisible(true);
    }
}