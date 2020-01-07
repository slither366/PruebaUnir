package venta.fotos_cliente;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.image.BufferedImage;

import javax.swing.*;

public class panelMiniatura extends JPanel {

    ImageIcon imagen;
    Prueba p;

    public panelMiniatura(ImageIcon img, Prueba prin) {
        imagen = img;
        p = prin;
        setPreferredSize(new Dimension(80, 80));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        MouseListener ml = new MouseListener() {

            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                p.getPm().quitarBorder();
                panelMiniatura pm = (panelMiniatura)e.getSource();
                pm.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
                p.getPv().setImagen(pm.getImagen());
                p.getPv().repaint();
                p.repaint();
            }
        };
        addMouseListener(ml);
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        if (imagen != null) {
            g.drawImage(imagen.getImage(), 0, 0, 80, 80, this);
        }
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }
}
