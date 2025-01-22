package figury;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class Kwadrat extends Figura {
    public Kwadrat(Graphics2D buf, int del, int w, int h) {
        super(buf, del, w, h);
        // Tworzenie kwadratu o wymiarach 50x50
        area = new Area(new Rectangle(0, 0, 50, 50));
        aft = new AffineTransform(); // Domyślna transformacja
    }
}
