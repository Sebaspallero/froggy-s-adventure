package manager;

import java.awt.Graphics;
import java.awt.Image;

public class AnimationManager {

    private Image spriteSheet; 
    private int frameWidth, frameHeight; 
    private int currentFrame = 0; 
    private int frameCount; 
    private long lastFrameTime = 0; 
    @SuppressWarnings("unused")
    private long frameDelay; 
    private int loopCount; // número de loops deseados (0 = infinito)
    private int currentLoop = 0; // número de ciclos completos
    private long frameDelayInNanos; // Almacenamos el frameDelay en nanosegundos para mayor precisión

    // Constructor con la posibilidad de limitar el número de loops
    public AnimationManager(Image spriteSheet, int frameWidth, int frameHeight, int frameCount, long frameDelay, int loopCount) {
        this.spriteSheet = spriteSheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.frameDelay = frameDelay;
        this.loopCount = loopCount; // Si es 0, la animación se repetirá indefinidamente
        this.frameDelayInNanos = frameDelay * 1_000_000; // Convertimos milisegundos a nanosegundos para precisión
    }

    // Método para actualizar la animación según el tiempo transcurrido
    public void update() {
        long currentTime = System.nanoTime(); // Usamos nanoTime para mayor precisión en el tiempo
        if (currentTime - lastFrameTime >= frameDelayInNanos) { // Comparamos con el tiempo en nanosegundos
            currentFrame++; // Pasar al siguiente frame

            // Si alcanzamos el último frame, completamos un ciclo y reiniciamos el frame
            if (currentFrame >= frameCount) {
                currentFrame = 0;
                currentLoop++; // Aumentamos el contador de loops

                if (loopCount > 0 && currentLoop >= loopCount) {
                    return; // Terminamos la animación si ya completamos el número de loops
                }
            }
            lastFrameTime = currentTime; // Actualizamos el tiempo del último frame
        }
    }

    // Método para dibujar la animación en la pantalla
    public void draw(Graphics g, int x, int y, int width, int height) {
        int sourceX1 = currentFrame * frameWidth; // Calcular la coordenada X del frame actual
        int sourceY1 = 0; // Siempre es 0 porque el sprite sheet tiene solo una fila
        int sourceX2 = sourceX1 + frameWidth;
        int sourceY2 = sourceY1 + frameHeight;

        g.drawImage(spriteSheet, x, y, x + width, y + height, sourceX1, sourceY1, sourceX2, sourceY2, null); // Dibuja el frame
    }

    // Método que nos indica si la animación ha terminado (basado en los loops)
    public boolean isAnimationComplete() {
        return loopCount > 0 && currentLoop >= loopCount; // Comprobamos si se han completado todos los loops
    }

    // Método para reiniciar la animación
    public void reset() {
        currentFrame = 0;
        currentLoop = 0;
    }

    // Getter para el frame actual
    public int getCurrentFrame() {
        return this.currentFrame;
    }
}