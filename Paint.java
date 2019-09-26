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
        setIgnoreRepaint(true);

        try {
            _logo = ImageIO.read(new java.io.File("logo.png"));
            _imageWidth = _logo.getWidth(null);
        } 
        catch (IOException e) {
            System.err.println("La imagen no se ha podido cargar.");
        }
    }

    public void update(long deltaTime){
        _x += _incrX * deltaTime / 1000.0;

        if (_x < 0) {
            _x = -_x;
            _incrX *= -1;
        } 
        else if (_x > getWidth() - _imageWidth) {
            _x = 2 * (getWidth() - _imageWidth) - _x;
            _incrX *= -1;
        }
    }

    public void render(){
        Graphics g = getGraphics();
        try {
            g.setColor(Color.blue);
            g.fillRect(0, 0, 400, 400);
            if (_logo != null) {
                g.drawImage(_logo, (int)_x, 100, null);
            }
        }
        finally {
            g.dispose();
        }
    }

    public static void main(String[] args) {
        Paint ventana = new Paint("Paint");
        ventana.init();
        ventana.setVisible(true);
        long lastFrameTime = System.nanoTime();

        while(true){
            long currentTime = System.nanoTime();
            ventana.update((currentTime - lastFrameTime) / 1000000);
            lastFrameTime = currentTime;
            ventana.render();
            try {
                Thread.sleep(1);
            } catch (Exception e) {

            }
        }
    }

    Image _logo;
    double _x = 0;
    int _incrX = 5;
    int _imageWidth;
}