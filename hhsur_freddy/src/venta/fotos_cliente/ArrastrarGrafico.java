package venta.fotos_cliente;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * Ejemplo de gr�fico que se puede arrastrar con el rat�n.
 *
 * @author Chuidiang
 *
  */
public class ArrastrarGrafico extends Canvas implements MouseMotionListener
{
    /**
   * serial uid
   */
  private static final long serialVersionUID = -4273648398171436938L;

  /** 
     * Posicion x del v�rtice superior izquierdo del rect�ngulo.
     */
    private int xRectangulo = 0;

    /** 
     * Posicion y del v�rtice superior izquierdo del rect�ngulo
     */
    private int yRectangulo = 0;

    /** 
     * Ancho del rect�ngulo
     */
    private int anchoRectangulo = 100;

    /** 
     * Alto del rect�ngulo
     */
    private int altoRectangulo = 100;

    /** 
     * Si actualmente se est� arrastrando o no el rect�ngulo.
     */
    private boolean arrastrando = false;

    /** 
     * x en la que estaba anteriormente el rat�n.
     */
    private int xAnteriorRaton;

    /** 
     * y en la que estaba anteriormente el rat�n
     */
    private int yAnteriorRaton;

    /**
     * Crea un nuevo objeto ArrastrarGrafico.
     */
    public ArrastrarGrafico()
    {
        addMouseMotionListener(this);
    }

    /**
     * Para darle un tama�o por defecto al Canvas de dibujo
     *
     * @return Dimension por defecto.
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(500, 500);
    }

    /**
     * Dibuja el rect�ngulo en la posici�n indicada por por xRectangulo e
     * yRectangulo.
     *
     * @param g Graphics con el que dibujar.
     */
    @Override
    public void paint(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillRect(xRectangulo, yRectangulo, anchoRectangulo, altoRectangulo);
        
    }

    /**
     * Crea la ventana con el Canvas y lo visualiza
     *
     * @param args Se ignoran
     */
    public static void main(String[] args)
    {
        JFrame v = new JFrame("Arrastrar Grafico");
        ArrastrarGrafico c = new ArrastrarGrafico();
        v.getContentPane().add(c);
        v.pack();
        v.setVisible(true);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * M�todo al que se llama cuando se arrastra el rat�n.
     * Se comprueba con el atributo arrastrando si est� empezando el arrastre o
     * ya se esta en medio del mismo.
     * Si se comienza el arrastre, se guardan las coordenadas del rat�n que
     * vienen en el evento MouseEvent y se cambia el valor del atributo arrastrando.
     * Si se est� en medio de un arrastre, se calcula la nueva posici�n del
     * rect�ngulo y se llama al m�todo repaint() para que se pinte.
     *
     * @param e Evento del rat�n
     */
    public void mouseDragged(MouseEvent e)
    {
      // Si comienza el arrastre ...
        if (!arrastrando)
        {
          // ... y el rat�n est� dentro del rect�ngulo
            if (estaDentro(e))
            {
              // Se guardan las posiciones del rat�n
                xAnteriorRaton = e.getX();
                yAnteriorRaton = e.getY();
                // y se marca que ha comenzado el arrastre.
                arrastrando = true;
            }
        }
        else
        {
          // Si ya hab�a empezado el arrastre, se calculan las nuevas
          // coordenadas del rect�ngulo
            xRectangulo = (xRectangulo + e.getX()) - xAnteriorRaton;
            yRectangulo = (yRectangulo + e.getY()) - yAnteriorRaton;
            
            // Se guarda la posici�n del rat�n para el siguiente c�lculo
            xAnteriorRaton = e.getX();
            yAnteriorRaton = e.getY();
            
            // y se manda repintar el Canvas
            repaint();
        }
    }

    /**
     * Para ver si el rat�n est� dentro del rect�ngulo.
     * Si est� dentro, puede comenzar el arrastre.
     *
     * @param e El evento de rat�n
     *
     * @return true si el rat�n est� dentro del rect�ngulo
     */
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

    /**
     * El rat�n se mueve sin arrastrar. Se marca fin de arrastre.
     *
     * @param e E
     */
    public void mouseMoved(MouseEvent e)
    {
        arrastrando = false;
    }
}