package venta.fotos_cliente;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sun.awt.image.BufferedImageDevice;


public class panelVisualizador extends Canvas implements MouseMotionListener{

    Prueba p;
    ImageIcon imagen;
    
    //private BufferedImageDevice img;
    
    private float scale = 1;

    /** 
     * Si actualmente se está arrastrando o no el rectángulo.
     */
    private boolean arrastrando = false;
    /** 
       * Posicion x del vértice superior izquierdo del rectángulo.
       */
      private int xRectangulo = 0;

      /** 
       * Posicion y del vértice superior izquierdo del rectángulo
       */
      private int yRectangulo = 0;    
      
    /** 
     * Ancho del rectángulo
     */
    private int anchoRectangulo = 100;

    /** 
     * Alto del rectángulo
     */
    private int altoRectangulo = 100;
    

    private int xAnteriorRaton;

    /**
    * y en la que estaba anteriormente el ratón
    */
    private int yAnteriorRaton;
    
    
    public panelVisualizador(Prueba prin) {
        p = prin;
        
        addMouseMotionListener(this);
        
        addMouseWheelListener(new MouseAdapter() {
                       @Override
                       public void mouseWheelMoved(MouseWheelEvent e) {
                           double delta = 0.05f * e.getPreciseWheelRotation();
                           scale += delta;
                           revalidate();
                           repaint();
                       }

                   });
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = new Dimension(200, 200);
        size.width = Math.round(size.width * scale);
        size.height = Math.round(size.height * scale);
        
        anchoRectangulo =  size.height;
        altoRectangulo =size.width ;
        return size;
    }
    
    @Override
    public void paint(Graphics g)
    {
        if (imagen != null) {

            Graphics2D g2d = (Graphics2D)g;
            g2d.scale(scale, scale);
            //g2d.drawImage(imagen.getImage(), 0 , 0, p.getPv().getWidth(), p.getPv().getHeight(), this);
            g2d.drawImage(imagen.getImage(), xRectangulo , yRectangulo, 
                         altoRectangulo,anchoRectangulo, this);
            
            
        } 
        else{
            Graphics2D g2d = (Graphics2D)g;
            g2d.scale(scale, scale);
            g2d.drawString(".", 10, 10);
            this.repaint();
        }
    }
    
   
    protected void paintComponent(Graphics g) {
        //super.paintComponents(g);
        if (imagen != null) {
           /*Graphics2D g2d = (Graphics2D) g.create();
           AffineTransform at = new AffineTransform();
           at.scale(scale, scale);
           BufferedImage img;
           img = (BufferedImage)imagen.getImage();
           g2d.drawImage(img, at, this);
           g2d.dispose();*/
            Graphics2D g2d = (Graphics2D)g;
            g2d.scale(scale, scale);
            g2d.drawImage(imagen.getImage(), 0 , 0, p.getPv().getWidth(), p.getPv().getHeight(), this);
        }        
        
        /*super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        g2d.setTransform(at);

        g2d.setColor(Color.RED);

        // This is for demonstration purposes only
        // I prefer to use getWidth and getHeight
        int width = 200;
        int height = 200;

        Path2D.Float path = new Path2D.Float();
        int seg = width / 3;
        path.moveTo(0, height / 2);
        path.curveTo(0, 0, seg, 0, seg, height / 2);
        path.curveTo(
                seg, height, 
                seg * 2, height, 
                seg * 2, height / 2);
        path.curveTo(
                seg * 2, 0, 
                seg * 3, 0, 
                seg * 3, height / 2);

        g2d.draw(path);


        g2d.dispose();*/
    }    



    public Prueba getP() {
        return p;
    }

    public void setP(Prueba p) {
        this.p = p;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
        xRectangulo = 0;
        yRectangulo = 0;
        anchoRectangulo = imagen.getIconHeight();
        altoRectangulo = imagen.getIconWidth();
    }

    private boolean estaDentro(MouseEvent e)
    {
        if (
            (e.getX() > xRectangulo) &&
                (e.getX() < (xRectangulo + anchoRectangulo)) &&
                (e.getY() > yRectangulo) &&
                (e.getY() < (yRectangulo + altoRectangulo)))
        {
            return true;
        }

        return false;
    }
    
    public void mouseDragged(MouseEvent e) {
        // Si comienza el arrastre ...
        System.out.println("m");
                if (!arrastrando)
                {
                  // ... y el ratón está dentro del rectángulo
                    if (estaDentro(e))
                    {
                        System.out.println("Se guardan las posiciones del ratón");
                      // Se guardan las posiciones del ratón
                        xAnteriorRaton = e.getX();
                        yAnteriorRaton = e.getY();
                        // y se marca que ha comenzado el arrastre.
                        arrastrando = true;
                    }
                }
                else
                {
                  // Si ya había empezado el arrastre, se calculan las nuevas
                  // coordenadas del rectángulo
                    xRectangulo = (xRectangulo + e.getX()) - xAnteriorRaton;
                    yRectangulo = (yRectangulo + e.getY()) - yAnteriorRaton;
                    
                    // Se guarda la posición del ratón para el siguiente cálculo
                    xAnteriorRaton = e.getX();
                    yAnteriorRaton = e.getY();
                    System.out.println("nuevas");
                    // y se manda repintar el Canvas
                    repaint();
                }        
    }

    public void mouseMoved(MouseEvent e) {
        arrastrando = false;
    }
}
