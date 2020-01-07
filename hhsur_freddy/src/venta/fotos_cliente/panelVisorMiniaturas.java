package venta.fotos_cliente;

import common.FarmaUtility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class panelVisorMiniaturas extends JPanel {
    
    private static final Logger log = LoggerFactory.getLogger(panelVisorMiniaturas.class);
    panelMiniatura paneles[] = new panelMiniatura[4];
    ImageIcon imagenes[] = new ImageIcon[6];
    int indices[] = new int[4];
    Prueba p;

    public panelVisorMiniaturas(Prueba prin) {
        cargarImagenes();
        p = prin;
        setLayout(new GridLayout(1, 4, 10, 10));
        for (int i = 0; i < 4; i++) {
            paneles[i] = new panelMiniatura(imagenes[i], p);
            add(paneles[i]);
        }
    }

    public void siguienteImagen() {
        if (indices[3] < 6-1) {
            for (int i = 0; i < 4; i++) {
                indices[i] = indices[i] + 1;
                paneles[i].setImagen(imagenes[indices[i]]);
            }

            p.getPm().quitarBorder();
            paneles[1].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
            p.getPv().setImagen(paneles[0].getImagen());
            p.getPv().repaint();

            repaint();
            p.repaint();
        }
    }

    public void anteriorImagen() {
        if (indices[0] > 0) {
            for (int i = 0; i < 4; i++) {
                indices[i] = indices[i] - 1;
                paneles[i].setImagen(imagenes[indices[i]]);
            }

            p.getPm().quitarBorder();
            paneles[1].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
            p.getPv().setImagen(paneles[0].getImagen());
            p.getPv().repaint();

            repaint();
            p.repaint();
        }
    }

    public void cargarImagenes() {
        for (int i = 0; i < 4; i++) {
            indices[i] = i;
        }
       /* for (int i = 0; i < 13; i++) {
            imagenes[i] = new ImageIcon(this.getClass().getResource("/mifarma/pb/Imagenes" + (i + 1) + ".jpg"));
        }*/
    }

    public void quitarBorder() {
        for (int i = 0; i < 4; i++) {
            paneles[i].setBorder(null);
        }
    }

    public panelMiniatura[] getPaneles() {
        return paneles;
    }

    public void setPaneles(panelMiniatura[] paneles) {
        this.paneles = paneles;
    }

    public ImageIcon[] getImagenes() {
        return imagenes;
    }

    public void setImagenes(ImageIcon[] imagenes) {
        this.imagenes = imagenes;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public void reseImage(){
        
    }
    
    public void setTotalImagenes(
                                   ArrayList vList,
                                   int ctd
                                   )
       {
           for (int i = 0; i < vList.size(); i++) {
               log.info("-- "+"c:\\copy\\"+FarmaUtility.getValueFieldArrayList(vList, i, 0));
               imagenes[i] = new ImageIcon("c:\\copy\\"+FarmaUtility.getValueFieldArrayList(vList, i, 0));
           }
           
           this.show();
           this.repaint();
           p.getPv().repaint();
           repaint();
           p.repaint();
       }
}
