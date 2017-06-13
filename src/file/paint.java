package file;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class drawpad extends JComponent{
    public Image image;
    Graphics2D graphics2D;
    int currentX, currentY, oldX, oldY;

    public drawpad(){
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                oldX = e.getX();
                oldY = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                currentX = e.getX();
                currentY = e.getY();
                if(graphics2D != null)
                    graphics2D.drawLine(oldX, oldY, currentX, currentY);
                repaint();
                oldX = currentX;
                oldY = currentY;
            }
        });
    }

    public void paintComponent(Graphics g){
        if(image == null){
            image = createImage(getSize().width, getSize().height);
            graphics2D = (Graphics2D)image.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }

    public void clear(){
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setPaint(Color.black);
        repaint();
    }
}