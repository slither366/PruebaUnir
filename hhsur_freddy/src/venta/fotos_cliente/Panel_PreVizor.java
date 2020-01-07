package venta.fotos_cliente;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

    public class Panel_PreVizor extends JPanel {

        ImageIcon imagen;
        public Panel_PreVizor(ImageIcon img) {
            imagen = img;
            setPreferredSize(new Dimension(80, 80));
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
        }

        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            if (imagen != null) {
                g.drawImage(imagen.getImage(), 10, 20, this.getWidth()-30,this.getHeight()-30, this);
            }
        }

        public ImageIcon getImagen() {
            return imagen;
        }

        public void setImagen(ImageIcon imagen) {
            this.imagen = imagen;
        }
    }
