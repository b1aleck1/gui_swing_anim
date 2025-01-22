package zad1;

import java.awt.*;
import java.awt.image.BufferedImage;

// Efekt negatywu
class NegativeEffect implements Runnable {
    private final DrawingPanel panel;

    public NegativeEffect(DrawingPanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        System.out.println("NegativeEffect started in thread: " + Thread.currentThread().getName());
        BufferedImage sourceImage = panel.getImage();
        BufferedImage negativeImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());
        // ThreadMonitoring.printThreadInfo();
        for (int x = 0; x < sourceImage.getWidth(); x++) {
            for (int y = 0; y < sourceImage.getHeight(); y++) {
                Color color = new Color(sourceImage.getRGB(x, y), true);
                int r = 255 - color.getRed();
                int g = 255 - color.getGreen();
                int b = 255 - color.getBlue();
                negativeImage.setRGB(x, y, new Color(r, g, b, color.getAlpha()).getRGB());
            }
        }

        panel.setImage(negativeImage);
        System.out.println("NegativeEffect finished in thread: " + Thread.currentThread().getName());
        // ThreadMonitoring.printThreadInfo();
    }
}