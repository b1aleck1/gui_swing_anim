package figury;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Elipsa extends Figura {
    public Elipsa(Graphics2D buf, int del, int w, int h) {
        super(buf, del, w, h);
        // Tworzenie elipsy o wymiarach 50x30
        area = new Area(new Ellipse2D.Double(0, 0, 50, 30));
        aft = new AffineTransform(); // Domyślna transformacja
    }
}
