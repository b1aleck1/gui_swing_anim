package zad1;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class GraphicEditor extends JFrame {
    private final DrawingPanel drawingPanel;
    private final JButton blurButton;
    private final JButton negativeButton;
    private final JButton colorButton;
    private final JButton clearButton;
    private final JButton pasteImageButton;
    private final JButton simulateButton;
    private final ReentrantLock imageLock = new ReentrantLock();

    public GraphicEditor() {
        super("Graphic Editor with Effects and Image Paste");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        drawingPanel = new DrawingPanel(imageLock);
        blurButton = new JButton("Blur Effect");
        negativeButton = new JButton("Negative Effect");
        colorButton = new JButton("Choose Pen Color");
        clearButton = new JButton("Clear Panel");
        pasteImageButton = new JButton("Paste Image"); // Inicjalizacja nowego przycisku
        simulateButton = new JButton("Automatyczne rysowanie");

        JPanel controlPanel = new JPanel();
        controlPanel.add(blurButton);
        controlPanel.add(negativeButton);
        controlPanel.add(colorButton);
        controlPanel.add(clearButton);
        controlPanel.add(pasteImageButton);
        controlPanel.add(simulateButton);

        add(drawingPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        blurButton.addActionListener(e -> new Thread(new BlurEffect(drawingPanel)).start());
        negativeButton.addActionListener(e -> new Thread(new NegativeEffect(drawingPanel)).start());
        simulateButton.addActionListener(e -> drawingPanel.simulateDrawing());
        // Action listener for color picker
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Pen Color", drawingPanel.getPenColor());
            if (newColor != null) {
                drawingPanel.setPenColor(newColor);
            }
        });

        // Action listener for clearing the panel
        clearButton.addActionListener(e -> drawingPanel.clearPanel());

        // Action listener for pasting an image
        pasteImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    BufferedImage image = ImageIO.read(file);
                    drawingPanel.pasteImage(image);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphicEditor editor = new GraphicEditor();
            editor.setVisible(true);
        });
    }
}

// Panel do rysowania
class DrawingPanel extends JPanel {
    private BufferedImage image;
    //private final ReentrantLock lock;
    private Color penColor = Color.BLACK; // Default pen color

    public DrawingPanel(ReentrantLock lock) {
        //this.lock = lock;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        clearImage(); // Inicjalizacja pustego obrazu

        // Rysowanie myszką
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                lock.lock();
                try {
                    Graphics2D g2d = image.createGraphics();
                    g2d.setColor(penColor); // Użyj wybranego koloru
                    g2d.fillOval(e.getX() - 5, e.getY() - 5, 10, 10);
                    g2d.dispose();
                    repaint();
                } finally {
                    lock.unlock();
                }
            }
        });
    }

    public BufferedImage getImage() {
        //lock.lock();
        try {
            return image;
        } finally {
            //lock.unlock();
        }
    }

    public void setImage(BufferedImage newImage) {
        //lock.lock();
        try {
            image = newImage;
            repaint();
        } finally {
            //lock.unlock();
        }
    }

    public Color getPenColor() {
        return penColor;
    }

    public void setPenColor(Color newColor) {
        this.penColor = newColor;
    }

    // Czyszczenie panelu
    public void clearPanel() {
        //lock.lock();
        try {
            clearImage();
            repaint();
        } finally {
           // lock.unlock();
        }
    }

    private void clearImage() {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.dispose();
    }

    // Wklejanie obrazu
    public void pasteImage(BufferedImage newImage) {
        //lock.lock();
        try {
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(newImage, 0, 0, this); // Wklej obraz w lewym górnym rogu
            g2d.dispose();
            repaint();
        } finally {
          //  lock.unlock();
        }
    }

    public void simulateDrawing() {
        new Thread(() -> {
            Random rand = new Random();
            boolean koniec = true;
            for (int i = 0; koniec ; i++) { // Rysowanie 50 punktów
                //lock.lock();
                try {
                    Graphics2D g2d = image.createGraphics();
                    g2d.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                    int x = rand.nextInt(getWidth());
                    int y = rand.nextInt(getHeight());
                    g2d.fillOval(x, y, 10, 10);
                    g2d.dispose();
                    repaint();
                    Thread.sleep(100); // Opóźnienie
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    //lock.unlock();
                }
            }
        }).start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //lock.lock();
        try {
            g.drawImage(image, 0, 0, null);
        } finally {
            //lock.unlock();
        }
    }
}


