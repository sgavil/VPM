import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.*;
import java.io.IOException;

public class Paint extends JFrame {

    public Paint(String title) {
        super(title);
    }

    public void init() {
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            _logo = ImageIO.read(new java.io.File("logo.jpg"));
            _imageWidth = _logo.getWidth(null);
        } catch (IOException e) {
            System.err.println("la imagen no se ha podido cargar");
        }
    }

    Image _logo;
    int _x = 0;
    int _incrX = 5;
    int _imageWidth;

    public void paint(Graphics g) {
        // super.paint(g);
        g.drawImage(_logo, _x, 100, null);
        _x += _incrX;

        if (_x < 0) {
            _x = -_x;
            _incrX *= -1;
        } else if (_x > getWidth() - _imageWidth) {
            _x = 2 * (getWidth() - _imageWidth) - _x;
            _incrX *= -1;
        }
        try {
            Thread.sleep(15);
        } catch (Exception e) {

        }
        repaint();

        /*
         * g.setColor(Color.blue); g.fillRect(100, 100, 200, 200); g.setColor(new
         * Color(255, 0, 0, 128)); g.fillRect(0, 0, 200, 200);
         */

    }

    public static void main(String[] args) {
        Paint ventana = new Paint("Paint");
        ventana.init();
        ventana.setVisible(true);

    }
}