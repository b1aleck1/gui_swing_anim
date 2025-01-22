package zad1;

import java.awt.*;
import java.awt.image.BufferedImage;

// Efekt rozmycia
class BlurEffect implements Runnable {
    private final DrawingPanel panel;

    public BlurEffect(DrawingPanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        System.out.println("BlurEffect started in thread: " + Thread.currentThread().getName());
        BufferedImage sourceImage = panel.getImage();
        BufferedImage blurredImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

        for (int x = 1; x < sourceImage.getWidth() - 1; x++) {
            for (int y = 1; y < sourceImage.getHeight() - 1; y++) {
                int r = 0, g = 0, b = 0, a = 0;

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int rgb = sourceImage.getRGB(x + dx, y + dy);
                        Color color = new Color(rgb, true);
                        r += color.getRed();
                        g += color.getGreen();
                        b += color.getBlue();
                        a += color.getAlpha();
                    }
                }

                r /= 9;
                g /= 9;
                b /= 9;
                a /= 9;

                blurredImage.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }

        panel.setImage(blurredImage);
        System.out.println("BlurEffect finished in thread: " + Thread.currentThread().getName());
    }
}
