package manager;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

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
    private boolean flip = false; // Variable que indica si la animación debe ser invertida

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

        // Si el flip está activado, invertimos la imagen
        if (flip) {
            BufferedImage flippedImage = flipImage((BufferedImage) spriteSheet);
            g.drawImage(flippedImage, x, y, x + width, y + height, sourceX1, sourceY1, sourceX2, sourceY2, null);
        } else {
            g.drawImage(spriteSheet, x, y, x + width, y + height, sourceX1, sourceY1, sourceX2, sourceY2, null); // Dibuja el frame
        }
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

    // Método para voltear (flip) la imagen
    private BufferedImage flipImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, img.getType());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                flippedImage.setRGB(width - i - 1, j, img.getRGB(i, j));
            }
        }
        return flippedImage;
    }

    // Método para activar o desactivar el flip de la imagen
    public void setFlip(boolean flip) {
        this.flip = flip;
    }
}