import javax.imageio.ImageIO;
import javax.swing.JFrame;

import javafx.scene.shape.Circle;
import sun.security.provider.PolicyParser.GrantEntry;

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

    public void update(double deltaTime){
        _x += _incrX * deltaTime;

        if (_x < 0) {
            _x = -_x;
            _incrX *= -1;
        } 
        else if (_x > getWidth() - _imageWidth) {
            _x = 2 * (getWidth() - _imageWidth) - _x;
            _incrX *= -1;
        }
    }

    public void render(Graphics g){
        if(_logo != null)
            g.setColor(Color.blue);
            g.fillRect(0, 0, 800, 800);
            g.drawImage(_logo, (int)_x, 100, null);
    }

    public static void main(String[] args) {
        Paint ventana = new Paint("Paint");
        ventana.init();
        ventana.setVisible(true);

        int veces = 100;
        do {
            try {
                ventana.createBufferStrategy(2);
            } catch (Exception e){
                
            }
        } while(veces-- > 0);

        if (veces == 0){
            System.err.println("No se pudo crear el BufferStrategy.");
            return;
        }

        java.awt.image.BufferStrategy strategy;
        strategy = ventana.getBufferStrategy();

        long lastFrameTime = System.nanoTime();

        while(true){

            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            ventana.update(elapsedTime);
            do {
                do {
                    Graphics g = strategy.getDrawGraphics();
                    try {
                        ventana.render(g);
                    }
                    finally {
                        g.dispose();
                    }
                } while(strategy.contentsRestored());

                strategy.show();
            } while(strategy.contentsLost());
            try {
                Thread.sleep(1);
            } catch (Exception e) {

            }
        }
    }

    Image _logo;
    double _x = 0;
    int _incrX = 50;
    int _imageWidth;
}