package venta.reportes;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.*;

import java.util.*;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import common.*;

import venta.reference.UtilityPtoVenta;
import venta.reportes.reference.*;
import componentes.gs.componentes.JLabelOrange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgResumenVenta extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgResumenVenta.class);

    private FarmaTableModel tableModelListaFormadePago;
    private Frame myParentFrame;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JLabelWhite jLabelWhite3 = new JLabelWhite();
    private JLabelWhite jLabelWhite4 = new JLabelWhite();
    private JLabelWhite jLabelWhite5 = new JLabelWhite();
    private JLabelWhite jLabelWhite6 = new JLabelWhite();
    private JLabelWhite jLabelWhite7 = new JLabelWhite();
    private JPanelTitle jPanelTitle3 = new JPanelTitle();
    private JLabelWhite jLabelWhite8 = new JLabelWhite();
    private JLabelWhite txtGuiasAnuladas = new JLabelWhite();
    private JLabelWhite txtGuiasEmitidas = new JLabelWhite();
    private JLabelWhite txtFacturasAnuladas = new JLabelWhite();
    private JLabelWhite txtFacturasEmitidas = new JLabelWhite();
    private JLabelWhite txtBoletasAnuladas = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidasM = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas2 = new JLabelWhite();
    private JLabelWhite txtBoletasAnuladasM = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas4 = new JLabelWhite();
    private JLabelWhite txtFacturasEmitidasM = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas6 = new JLabelWhite();
    private JLabelWhite txtFacturasAnuladasM = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas8 = new JLabelWhite();
    private JLabelWhite txtGuiasEmitidasM = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas10 = new JLabelWhite();
    private JLabelWhite txtGuiasAnuladasM = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas12 = new JLabelWhite();
    private JPanelTitle jPanelTitle4 = new JPanelTitle();
    private JLabelWhite jLabelWhite9 = new JLabelWhite();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblFormaPago = new JTable();
    private JPanelTitle jPanelTitle5 = new JPanelTitle();
    private JLabelWhite jLabelWhite10 = new JLabelWhite();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelWhite lblTotalCantidad = new JLabelWhite();
    private JLabelWhite lblTotalMonto = new JLabelWhite();
    private JLabelWhite lblCantidadReg = new JLabelWhite();
    private JLabelWhite lblSumaTotal = new JLabelWhite();
    private JPanelTitle jPanelTitle6 = new JPanelTitle();
    private JLabelWhite lblTotalMontoDelivery = new JLabelWhite();
    private JLabelWhite lblTotalCantidadDelivery = new JLabelWhite();
    private JLabelWhite jLabelWhite14 = new JLabelWhite();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelWhite jLabelWhite12 = new JLabelWhite();
    private JLabelWhite txtDeliveryAnulado = new JLabelWhite();
    private JLabelWhite txtDeliveryAnuladoM = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas13 = new JLabelWhite();
    private JLabelWhite txtDeliveryEmitidoM = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas11 = new JLabelWhite();
    private JLabelWhite txtDeliveryEmitido = new JLabelWhite();
    private JLabelWhite jLabelWhite13 = new JLabelWhite();
    private JLabelWhite txtNcfCantidad = new JLabelWhite();
    private JLabelWhite txtNcfMonto = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas14 = new JLabelWhite();
    private JLabelWhite txtNcbMonto = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas15 = new JLabelWhite();
    private JLabelWhite txtNcbCantidad = new JLabelWhite();
    private JLabelWhite jLabelWhite15 = new JLabelWhite();
    private JLabelWhite jLabelWhite16 = new JLabelWhite();
    private JPanelTitle jPanelTitle7 = new JPanelTitle();
    private JLabelWhite lblTotalMontoInst = new JLabelWhite();
    private JLabelWhite lblTotalCantidadInst = new JLabelWhite();
    private JLabelWhite jLabelWhite17 = new JLabelWhite();
    private JLabelWhite txtInstiAnuladoM = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas16 = new JLabelWhite();
    private JLabelWhite txtInstAnulado = new JLabelWhite();
    private JLabelWhite jLabelWhite18 = new JLabelWhite();
    private JLabelWhite txtInstEmitidoM = new JLabelWhite();
    private JLabelWhite txtBoletasEmitidas17 = new JLabelWhite();
    private JLabelWhite txtInstEmitida = new JLabelWhite();
    private JLabelWhite jLabelWhite19 = new JLabelWhite();
    private JLabelOrange lblHoraTerm_t = new JLabelOrange();
    private JLabelOrange lblHoraTerm = new JLabelOrange();
    private JLabelWhite jLabelWhite20 = new JLabelWhite();
    private JLabelWhite jLabelWhite22 = new JLabelWhite();
    private JLabelWhite jLabelWhite23 = new JLabelWhite();
    private JLabelWhite txtTicketsEmitidosM = new JLabelWhite();
    private JLabelWhite txtTicketsEmitidos = new JLabelWhite();
    private JLabelWhite txtTicketsAnuladosM = new JLabelWhite();
    private JLabelWhite txtTicketsAnulados = new JLabelWhite();
    private JLabelWhite lblTickFacEmitidas_T = new JLabelWhite();
    private JLabelWhite lblTickFacAnuladas_T = new JLabelWhite();
    private JLabelWhite txtTicketsFacEmitidos = new JLabelWhite();
    private JLabelWhite txtTicketsFacAnulados = new JLabelWhite();
    private JLabelWhite txtTicketsFacEmitidosM = new JLabelWhite();
    private JLabelWhite txtTicketsFacAnuladosM = new JLabelWhite();

    public DlgResumenVenta()
  {
    this(null, "", false);
  }

  public DlgResumenVenta(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
       initialize();
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(662, 528));
    this.getContentPane().setLayout(null);
     this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Resumen");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }

        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
      });
    jPanelWhite1.setLayout(null);
    jPanelWhite1.setBounds(new Rectangle(0, 0, 660, 505));
    jPanelTitle1.setBounds(new Rectangle(5, 10, 310, 20));
    jPanelTitle1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jLabelWhite1.setText("Descripcion                                  Cantidad         Monto S/.");
    jLabelWhite1.setBounds(new Rectangle(5, 0, 300, 20));
    jPanelTitle2.setBounds(new Rectangle(5, 30, 310, 300));
    jPanelTitle2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jPanelTitle2.setBackground(Color.white);
    jLabelWhite2.setText("Boletas Emitidas :");
    jLabelWhite2.setBounds(new Rectangle(5, 55, 120, 20));
    jLabelWhite2.setBackground(new Color(0,114,169));
    jLabelWhite2.setForeground(new Color(0,114,169));
    jLabelWhite3.setText("Boletas Anuladas :");
    jLabelWhite3.setBounds(new Rectangle(5, 75, 120, 20));
    jLabelWhite3.setBackground(new Color(0,114,169));
    jLabelWhite3.setForeground(new Color(0,114,169));
    jLabelWhite4.setText("Facturas Emitidas :");
    jLabelWhite4.setBounds(new Rectangle(5, 105, 120, 20));
    jLabelWhite4.setBackground(new Color(0,114,169));
    jLabelWhite4.setForeground(new Color(0,114,169));
    jLabelWhite5.setText("Facturas Anuladas :");
    jLabelWhite5.setBounds(new Rectangle(5, 125, 120, 20));
    jLabelWhite5.setBackground(new Color(0,114,169));
    jLabelWhite5.setForeground(new Color(0,114,169));
    jLabelWhite6.setText("Guias Emitidas :");
    jLabelWhite6.setBounds(new Rectangle(5, 160, 120, 20));
    jLabelWhite6.setBackground(new Color(0,114,169));
    jLabelWhite6.setForeground(new Color(0,114,169));
    jLabelWhite7.setText("Guias Anuladas :");
    jLabelWhite7.setBounds(new Rectangle(5, 180, 120, 20));
    jLabelWhite7.setBackground(new Color(0,114,169));
    jLabelWhite7.setForeground(new Color(0,114,169));
    jPanelTitle3.setBounds(new Rectangle(5, 330, 310, 25));
    jPanelTitle3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jLabelWhite8.setText("Totales");
    jLabelWhite8.setBounds(new Rectangle(5, 0, 80, 20));
    txtGuiasAnuladas.setText("0");
    txtGuiasAnuladas.setBounds(new Rectangle(170, 180, 50, 20));
    txtGuiasAnuladas.setBackground(new Color(0,114,169));
    txtGuiasAnuladas.setForeground(new Color(0,114,169));
    txtGuiasAnuladas.setHorizontalAlignment(SwingConstants.RIGHT);
    txtGuiasEmitidas.setText("0");
    txtGuiasEmitidas.setBounds(new Rectangle(170, 160, 50, 20));
    txtGuiasEmitidas.setBackground(new Color(0,114,169));
    txtGuiasEmitidas.setForeground(new Color(0,114,169));
    txtGuiasEmitidas.setHorizontalAlignment(SwingConstants.RIGHT);
    txtFacturasAnuladas.setText("0");
    txtFacturasAnuladas.setBounds(new Rectangle(170, 125, 50, 20));
    txtFacturasAnuladas.setBackground(new Color(0,114,169));
    txtFacturasAnuladas.setForeground(new Color(0,114,169));
    txtFacturasAnuladas.setHorizontalAlignment(SwingConstants.RIGHT);
    txtFacturasEmitidas.setText("0");
    txtFacturasEmitidas.setBounds(new Rectangle(170, 105, 50, 20));
    txtFacturasEmitidas.setBackground(new Color(0,114,169));
    txtFacturasEmitidas.setForeground(new Color(0,114,169));
    txtFacturasEmitidas.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasAnuladas.setText("0");
    txtBoletasAnuladas.setBounds(new Rectangle(170, 75, 50, 20));
    txtBoletasAnuladas.setBackground(new Color(0,114,169));
    txtBoletasAnuladas.setForeground(new Color(0,114,169));
    txtBoletasAnuladas.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas.setText("0");
    txtBoletasEmitidas.setBounds(new Rectangle(170, 55, 50, 20));
    txtBoletasEmitidas.setBackground(new Color(0,114,169));
    txtBoletasEmitidas.setForeground(new Color(0,114,169));
    txtBoletasEmitidas.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidasM.setText("0");
    txtBoletasEmitidasM.setBounds(new Rectangle(230, 55, 65, 20));
    txtBoletasEmitidasM.setBackground(new Color(0,114,169));
    txtBoletasEmitidasM.setForeground(new Color(0,114,169));
    txtBoletasEmitidasM.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas2.setText("0");
    txtBoletasEmitidas2.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas2.setBackground(new Color(0,114,169));
    txtBoletasEmitidas2.setForeground(new Color(0,114,169));
    txtBoletasAnuladasM.setText("0");
    txtBoletasAnuladasM.setBounds(new Rectangle(230, 75, 65, 20));
    txtBoletasAnuladasM.setBackground(new Color(0,114,169));
    txtBoletasAnuladasM.setForeground(new Color(0,114,169));
    txtBoletasAnuladasM.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas4.setText("0");
    txtBoletasEmitidas4.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas4.setBackground(new Color(0,114,169));
    txtBoletasEmitidas4.setForeground(new Color(0,114,169));
    txtFacturasEmitidasM.setText("0");
    txtFacturasEmitidasM.setBounds(new Rectangle(230, 105, 65, 20));
    txtFacturasEmitidasM.setBackground(new Color(0,114,169));
    txtFacturasEmitidasM.setForeground(new Color(0,114,169));
    txtFacturasEmitidasM.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas6.setText("0");
    txtBoletasEmitidas6.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas6.setBackground(new Color(0,114,169));
    txtBoletasEmitidas6.setForeground(new Color(0,114,169));
    txtFacturasAnuladasM.setText("0");
    txtFacturasAnuladasM.setBounds(new Rectangle(230, 125, 65, 20));
    txtFacturasAnuladasM.setBackground(new Color(0,114,169));
    txtFacturasAnuladasM.setForeground(new Color(0,114,169));
    txtFacturasAnuladasM.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas8.setText("0");
    txtBoletasEmitidas8.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas8.setBackground(new Color(0,114,169));
    txtBoletasEmitidas8.setForeground(new Color(0,114,169));
    txtGuiasEmitidasM.setText("0");
    txtGuiasEmitidasM.setBounds(new Rectangle(230, 160, 65, 20));
    txtGuiasEmitidasM.setBackground(new Color(0,114,169));
    txtGuiasEmitidasM.setForeground(new Color(0,114,169));
    txtGuiasEmitidasM.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas10.setText("0");
    txtBoletasEmitidas10.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas10.setBackground(new Color(0,114,169));
    txtBoletasEmitidas10.setForeground(new Color(0,114,169));
    txtGuiasAnuladasM.setText("0");
    txtGuiasAnuladasM.setBounds(new Rectangle(230, 180, 65, 20));
    txtGuiasAnuladasM.setBackground(new Color(0,114,169));
    txtGuiasAnuladasM.setForeground(new Color(0,114,169));
    txtGuiasAnuladasM.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas12.setText("0");
    txtBoletasEmitidas12.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas12.setBackground(new Color(0,114,169));
    txtBoletasEmitidas12.setForeground(new Color(0,114,169));
    jPanelTitle4.setBounds(new Rectangle(325, 10, 320, 20));
    jPanelTitle4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jLabelWhite9.setText("Relacion de Forma de Pago");
    jLabelWhite9.setBounds(new Rectangle(5, 0, 300, 20));
    jScrollPane1.setBounds(new Rectangle(325, 30, 320, 300));
    jScrollPane1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    tblFormaPago.addKeyListener(new KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          tblFormaPago_keyReleased(e);
        }

        public void keyPressed(KeyEvent e)
        {
          tblFormaPago_keyPressed(e);
        }
      });
    jPanelTitle5.setBounds(new Rectangle(325, 330, 320, 25));
    jPanelTitle5.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jLabelWhite10.setText("Registros:");
    jLabelWhite10.setBounds(new Rectangle(5, 5, 80, 15));
    jLabelFunction1.setBounds(new Rectangle(520, 475, 117, 19));
    jLabelFunction1.setText("[ ESC ]Escape");
        lblTotalCantidad.setText("0");
    lblTotalCantidad.setBounds(new Rectangle(170, 0, 50, 20));
    lblTotalCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotalMonto.setText("0");
    lblTotalMonto.setBounds(new Rectangle(230, 0, 65, 20));
    lblTotalMonto.setHorizontalAlignment(SwingConstants.RIGHT);
    lblCantidadReg.setText("0");
    lblCantidadReg.setBounds(new Rectangle(100, 5, 65, 15));
    lblCantidadReg.setHorizontalAlignment(SwingConstants.RIGHT);
    lblSumaTotal.setText("0");
    lblSumaTotal.setBounds(new Rectangle(215, 5, 100, 15));
    lblSumaTotal.setHorizontalAlignment(SwingConstants.RIGHT);
    jPanelTitle6.setBounds(new Rectangle(5, 400, 310, 25));
    jPanelTitle6.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lblTotalMontoDelivery.setText("0");
    lblTotalMontoDelivery.setBounds(new Rectangle(230, 0, 65, 20));
    lblTotalMontoDelivery.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotalCantidadDelivery.setText("0");
    lblTotalCantidadDelivery.setBounds(new Rectangle(165, 0, 55, 20));
    lblTotalCantidadDelivery.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabelWhite14.setText("P. Delivery");
    jLabelWhite14.setBounds(new Rectangle(5, 0, 80, 20));
    lblF2.setBounds(new Rectangle(385, 475, 125, 20));
    lblF2.setText("[F2] Imprimir");
    jLabelWhite12.setText("Delivery Anuladas :");
    jLabelWhite12.setBounds(new Rectangle(10, 375, 120, 25));
    jLabelWhite12.setBackground(new Color(0,114,169));
    jLabelWhite12.setForeground(new Color(0,114,169));
    txtDeliveryAnulado.setText("0");
    txtDeliveryAnulado.setBounds(new Rectangle(170, 375, 55, 25));
    txtDeliveryAnulado.setBackground(new Color(0,114,169));
    txtDeliveryAnulado.setForeground(new Color(0,114,169));
    txtDeliveryAnulado.setHorizontalAlignment(SwingConstants.RIGHT);
    txtDeliveryAnuladoM.setText("0");
    txtDeliveryAnuladoM.setBounds(new Rectangle(235, 375, 65, 25));
    txtDeliveryAnuladoM.setBackground(new Color(0,114,169));
    txtDeliveryAnuladoM.setForeground(new Color(0,114,169));
    txtDeliveryAnuladoM.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas13.setText("0");
    txtBoletasEmitidas13.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas13.setBackground(new Color(0,114,169));
    txtBoletasEmitidas13.setForeground(new Color(0,114,169));
    txtDeliveryEmitidoM.setText("0");
    txtDeliveryEmitidoM.setBounds(new Rectangle(235, 355, 65, 25));
    txtDeliveryEmitidoM.setBackground(new Color(0,114,169));
    txtDeliveryEmitidoM.setForeground(new Color(0,114,169));
    txtDeliveryEmitidoM.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas11.setText("0");
    txtBoletasEmitidas11.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas11.setBackground(new Color(0,114,169));
    txtBoletasEmitidas11.setForeground(new Color(0,114,169));
    txtDeliveryEmitido.setText("0");
    txtDeliveryEmitido.setBounds(new Rectangle(170, 355, 55, 25));
    txtDeliveryEmitido.setBackground(new Color(0,114,169));
    txtDeliveryEmitido.setForeground(new Color(0,114,169));
    txtDeliveryEmitido.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabelWhite13.setText("Delivery Emitidas :");
    jLabelWhite13.setBounds(new Rectangle(10, 355, 120, 25));
    jLabelWhite13.setBackground(new Color(0,114,169));
    jLabelWhite13.setForeground(new Color(0,114,169));
    txtNcfCantidad.setText("0");
    txtNcfCantidad.setBounds(new Rectangle(170, 230, 50, 20));
    txtNcfCantidad.setBackground(new Color(0,114,169));
    txtNcfCantidad.setForeground(new Color(0,114,169));
    txtNcfCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
    txtNcfMonto.setText("0");
    txtNcfMonto.setBounds(new Rectangle(230, 230, 65, 20));
    txtNcfMonto.setBackground(new Color(0,114,169));
    txtNcfMonto.setForeground(new Color(0,114,169));
    txtNcfMonto.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas14.setText("0");
    txtBoletasEmitidas14.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas14.setBackground(new Color(0,114,169));
    txtBoletasEmitidas14.setForeground(new Color(0,114,169));
    txtNcbMonto.setText("0");
    txtNcbMonto.setBounds(new Rectangle(230, 210, 65, 20));
    txtNcbMonto.setBackground(new Color(0,114,169));
    txtNcbMonto.setForeground(new Color(0,114,169));
    txtNcbMonto.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas15.setText("0");
    txtBoletasEmitidas15.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas15.setBackground(new Color(0,114,169));
    txtBoletasEmitidas15.setForeground(new Color(0,114,169));
    txtNcbCantidad.setText("0");
    txtNcbCantidad.setBounds(new Rectangle(170, 210, 50, 20));
    txtNcbCantidad.setBackground(new Color(0,114,169));
    txtNcbCantidad.setForeground(new Color(0,114,169));
    txtNcbCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabelWhite15.setText("Nota Credito Facturas :");
    jLabelWhite15.setBounds(new Rectangle(5, 230, 140, 20));
    jLabelWhite15.setBackground(new Color(0,114,169));
    jLabelWhite15.setForeground(new Color(0,114,169));
    jLabelWhite16.setText("Nota Credito Boletas : ");
    jLabelWhite16.setBounds(new Rectangle(5, 210, 135, 20));
    jLabelWhite16.setBackground(new Color(0,114,169));
    jLabelWhite16.setForeground(new Color(0,114,169));
    jPanelTitle7.setBounds(new Rectangle(5, 470, 310, 25));
    jPanelTitle7.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lblTotalMontoInst.setText("0");
    lblTotalMontoInst.setBounds(new Rectangle(225, 0, 70, 20));
    lblTotalMontoInst.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotalCantidadInst.setText("0");
    lblTotalCantidadInst.setBounds(new Rectangle(160, 0, 60, 20));
    lblTotalCantidadInst.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabelWhite17.setText("Vta. Institucional");
    jLabelWhite17.setBounds(new Rectangle(5, 0, 125, 20));
    txtInstiAnuladoM.setText("0");
    txtInstiAnuladoM.setBounds(new Rectangle(235, 445, 65, 25));
    txtInstiAnuladoM.setBackground(new Color(0,114,169));
    txtInstiAnuladoM.setForeground(new Color(0,114,169));
    txtInstiAnuladoM.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas16.setText("0");
    txtBoletasEmitidas16.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas16.setBackground(new Color(0,114,169));
    txtBoletasEmitidas16.setForeground(new Color(0,114,169));
    txtInstAnulado.setText("0");
    txtInstAnulado.setBounds(new Rectangle(170, 445, 55, 25));
    txtInstAnulado.setBackground(new Color(0,114,169));
    txtInstAnulado.setForeground(new Color(0,114,169));
    txtInstAnulado.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabelWhite18.setText("Vta. Institucional Anulada :");
    jLabelWhite18.setBounds(new Rectangle(10, 445, 150, 25));
    jLabelWhite18.setBackground(new Color(0,114,169));
    jLabelWhite18.setForeground(new Color(0,114,169));
    txtInstEmitidoM.setText("0");
    txtInstEmitidoM.setBounds(new Rectangle(235, 425, 65, 25));
    txtInstEmitidoM.setBackground(new Color(0,114,169));
    txtInstEmitidoM.setForeground(new Color(0,114,169));
    txtInstEmitidoM.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoletasEmitidas17.setText("0");
    txtBoletasEmitidas17.setBounds(new Rectangle(240, 10, 40, 25));
    txtBoletasEmitidas17.setBackground(new Color(0,114,169));
    txtBoletasEmitidas17.setForeground(new Color(0,114,169));
    txtInstEmitida.setText("0");
    txtInstEmitida.setBounds(new Rectangle(170, 425, 55, 25));
    txtInstEmitida.setBackground(new Color(0,114,169));
    txtInstEmitida.setForeground(new Color(0,114,169));
    txtInstEmitida.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabelWhite19.setText("Vta. Institucional Emitida :");
    jLabelWhite19.setBounds(new Rectangle(10, 425, 150, 25));
    jLabelWhite19.setBackground(new Color(0,114,169));
    jLabelWhite19.setForeground(new Color(0,114,169));
    lblHoraTerm_t.setText("Hora de T�rmino del Proceso:");
    lblHoraTerm_t.setBounds(new Rectangle(325, 360, 175, 20));
    lblHoraTerm_t.setForeground(new Color(0,114,169));
    lblHoraTerm.setBounds(new Rectangle(495, 360, 150, 20));
    lblHoraTerm.setForeground(new Color(0,114,169));
    
        jLabelWhite20.setText("Tickets Emitidos :");
        jLabelWhite20.setBounds(new Rectangle(5, 5, 120, 20));
        jLabelWhite20.setBackground(new Color(0,114,169));
        jLabelWhite20.setForeground(new Color(0,114,169));

        jLabelWhite22.setText("jLabelWhite22");
        jLabelWhite23.setText("Tickets Anulados :");
        jLabelWhite23.setBounds(new Rectangle(5, 25, 130, 20));
        jLabelWhite23.setBackground(new Color(0,114,169));
        jLabelWhite23.setForeground(new Color(0,114,169));
 
        txtTicketsEmitidosM.setText("0");
        txtTicketsEmitidosM.setBounds(new Rectangle(230, 5, 65, 20));
        txtTicketsEmitidosM.setBackground(new Color(0,114,169));
        txtTicketsEmitidosM.setForeground(new Color(0,114,169));
        txtTicketsEmitidosM.setHorizontalAlignment(SwingConstants.RIGHT);

        txtTicketsEmitidos.setText("0");
        txtTicketsEmitidos.setBounds(new Rectangle(170, 5, 50, 20));
        txtTicketsEmitidos.setBackground(new Color(0,114,169));
        txtTicketsEmitidos.setForeground(new Color(0,114,169));
        txtTicketsEmitidos.setHorizontalAlignment(SwingConstants.RIGHT);

        txtTicketsAnuladosM.setText("0");
        txtTicketsAnuladosM.setBounds(new Rectangle(245, 25, 50, 20));
        txtTicketsAnuladosM.setBackground(new Color(0,114,169));
        txtTicketsAnuladosM.setForeground(new Color(0,114,169));
        txtTicketsAnuladosM.setHorizontalAlignment(SwingConstants.RIGHT);

        txtTicketsAnulados.setText("0");
        txtTicketsAnulados.setBounds(new Rectangle(170, 25, 50, 20));
        txtTicketsAnulados.setBackground(new Color(0,114,169));
        txtTicketsAnulados.setForeground(new Color(0,114,169));
        txtTicketsAnulados.setHorizontalAlignment(SwingConstants.RIGHT);

        lblTickFacEmitidas_T.setText("Tickets Facturas Emitidos:");
        lblTickFacEmitidas_T.setBounds(new Rectangle(5, 260, 170, 20));
        lblTickFacEmitidas_T.setSize(new Dimension(155, 20));
        lblTickFacEmitidas_T.setForeground(new Color(0,114,169));

        lblTickFacEmitidas_T.setPreferredSize(new Dimension(155, 20));
        lblTickFacEmitidas_T.setMinimumSize(new Dimension(155, 20));
        lblTickFacEmitidas_T.setMaximumSize(new Dimension(155, 20));
        lblTickFacAnuladas_T.setText("Tickets Facturas Anulados:");
        lblTickFacAnuladas_T.setBounds(new Rectangle(5, 280, 165, 20));
        lblTickFacAnuladas_T.setSize(new Dimension(155, 20));
        lblTickFacAnuladas_T.setForeground(new Color(0,114,169));

        lblTickFacAnuladas_T.setPreferredSize(new Dimension(155, 20));
        lblTickFacAnuladas_T.setMinimumSize(new Dimension(155, 20));
        lblTickFacAnuladas_T.setMaximumSize(new Dimension(155, 20));
        
        txtTicketsFacEmitidos.setBounds(new Rectangle(170, 260, 50, 20));
        txtTicketsFacEmitidos.setSize(new Dimension(50, 20));
        txtTicketsFacEmitidos.setPreferredSize(new Dimension(50, 20));
        txtTicketsFacEmitidos.setMinimumSize(new Dimension(50, 20));
        txtTicketsFacEmitidos.setMaximumSize(new Dimension(50, 20));
        txtTicketsFacEmitidos.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTicketsFacEmitidos.setText("0");
        txtTicketsFacEmitidos.setForeground(new Color(0,114,169));
            
        txtTicketsFacAnulados.setText("0");
        txtTicketsFacAnulados.setBounds(new Rectangle(170, 280, 50, 20));
        txtTicketsFacAnulados.setSize(new Dimension(50, 20));
        txtTicketsFacAnulados.setPreferredSize(new Dimension(50, 20));
        txtTicketsFacAnulados.setMinimumSize(new Dimension(50, 20));
        txtTicketsFacAnulados.setMaximumSize(new Dimension(50, 20));
        txtTicketsFacAnulados.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTicketsFacAnulados.setForeground(new Color(0,114,169));
            
        txtTicketsFacEmitidosM.setText("0");
        txtTicketsFacEmitidosM.setBounds(new Rectangle(230, 260, 65, 20));
        txtTicketsFacEmitidosM.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTicketsFacEmitidosM.setPreferredSize(new Dimension(65, 20));
        txtTicketsFacEmitidosM.setMinimumSize(new Dimension(65, 20));
        txtTicketsFacEmitidosM.setMaximumSize(new Dimension(65, 20));
        txtTicketsFacEmitidosM.setForeground(new Color(0,114,169));
        
        txtTicketsFacAnuladosM.setText("0");
        txtTicketsFacAnuladosM.setBounds(new Rectangle(230, 280, 65, 20));
        txtTicketsFacAnuladosM.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTicketsFacAnuladosM.setSize(new Dimension(65, 20));
        txtTicketsFacAnuladosM.setPreferredSize(new Dimension(65, 20));
        txtTicketsFacAnuladosM.setMinimumSize(new Dimension(65, 20));
        txtTicketsFacAnuladosM.setMaximumSize(new Dimension(65, 20));
        txtTicketsFacAnuladosM.setForeground(new Color(0,114,169));
            
        jPanelTitle7.add(lblTotalMontoInst, null);
        jPanelTitle7.add(lblTotalCantidadInst, null);
        jPanelTitle7.add(jLabelWhite17, null);
        jPanelTitle6.add(lblTotalMontoDelivery, null);
        jPanelTitle6.add(lblTotalCantidadDelivery, null);
        jPanelTitle6.add(jLabelWhite14, null);
        jPanelTitle2.add(txtTicketsFacAnuladosM, null);
        jPanelTitle2.add(txtTicketsFacEmitidosM, null);
        jPanelTitle2.add(txtTicketsFacEmitidos, null);
        jPanelTitle2.add(lblTickFacAnuladas_T, null);
        jPanelTitle2.add(lblTickFacEmitidas_T, null);
        jPanelTitle2.add(txtTicketsAnulados, null);
        jPanelTitle2.add(txtTicketsAnuladosM, null);
        jPanelTitle2.add(txtTicketsEmitidos, null);
        jPanelTitle2.add(txtTicketsEmitidosM, null);
        jPanelTitle2.add(jLabelWhite23, null);
        jPanelTitle2.add(jLabelWhite20, null);
        jPanelTitle2.add(jLabelWhite16, null);
        jPanelTitle2.add(jLabelWhite15, null);
        jPanelTitle2.add(txtNcbCantidad, null);
        txtNcbMonto.add(txtBoletasEmitidas15, null);
        jPanelTitle2.add(txtNcbMonto, null);
        txtNcfMonto.add(txtBoletasEmitidas14, null);
        jPanelTitle2.add(txtNcfMonto, null);
        jPanelTitle2.add(txtNcfCantidad, null);
        txtGuiasAnuladasM.add(txtBoletasEmitidas12, null);
        jPanelTitle2.add(txtGuiasAnuladasM, null);
        txtGuiasEmitidasM.add(txtBoletasEmitidas10, null);
        jPanelTitle2.add(txtGuiasEmitidasM, null);
        txtFacturasAnuladasM.add(txtBoletasEmitidas8, null);
        jPanelTitle2.add(txtFacturasAnuladasM, null);
        txtFacturasEmitidasM.add(txtBoletasEmitidas6, null);
        jPanelTitle2.add(txtFacturasEmitidasM, null);
        txtBoletasAnuladasM.add(txtBoletasEmitidas4, null);
        jPanelTitle2.add(txtBoletasAnuladasM, null);
        txtBoletasEmitidasM.add(txtBoletasEmitidas2, null);
        jPanelTitle2.add(txtBoletasEmitidasM, null);
        jPanelTitle2.add(txtBoletasEmitidas, null);
        jPanelTitle2.add(txtBoletasAnuladas, null);
        jPanelTitle2.add(txtFacturasEmitidas, null);
        jPanelTitle2.add(txtFacturasAnuladas, null);
        jPanelTitle2.add(txtGuiasEmitidas, null);
        jPanelTitle2.add(txtGuiasAnuladas, null);
        jPanelTitle2.add(jLabelWhite7, null);
        jPanelTitle2.add(jLabelWhite6, null);
        jPanelTitle2.add(jLabelWhite5, null);
        jPanelTitle2.add(jLabelWhite4, null);
        jPanelTitle2.add(jLabelWhite3, null);
        jPanelTitle2.add(jLabelWhite2, null);
        jPanelTitle2.add(txtTicketsFacAnulados, null);
        jPanelTitle3.add(lblTotalMonto, null);
        jPanelTitle3.add(lblTotalCantidad, null);
        jPanelTitle3.add(jLabelWhite8, null);
        jPanelTitle5.add(lblSumaTotal, null);
        jPanelTitle5.add(lblCantidadReg, null);
        jPanelTitle5.add(jLabelWhite10, null);
        jPanelWhite1.add(lblHoraTerm, null);
        jPanelWhite1.add(lblHoraTerm_t, null);
        jPanelWhite1.add(jLabelWhite19, null);
        jPanelWhite1.add(txtInstEmitida, null);
        txtInstEmitidoM.add(txtBoletasEmitidas17, null);
        jPanelWhite1.add(txtInstEmitidoM, null);
        jPanelWhite1.add(jLabelWhite18, null);
        jPanelWhite1.add(txtInstAnulado, null);
        txtInstiAnuladoM.add(txtBoletasEmitidas16, null);
        jPanelWhite1.add(txtInstiAnuladoM, null);
        jPanelWhite1.add(jPanelTitle7, null);
        jPanelWhite1.add(lblF2, null);
        jPanelWhite1.add(jPanelTitle6, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(jPanelTitle5, null);
        jScrollPane1.getViewport().add(tblFormaPago, null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanelTitle4.add(jLabelWhite9, null);
        jPanelWhite1.add(jPanelTitle4, null);
        jPanelWhite1.add(jPanelTitle3, null);
        jPanelWhite1.add(jPanelTitle2, null);
        jPanelTitle1.add(jLabelWhite1, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelWhite1.add(jLabelWhite12, null);
        jPanelWhite1.add(txtDeliveryAnulado, null);
        txtDeliveryAnuladoM.add(txtBoletasEmitidas13, null);
        jPanelWhite1.add(txtDeliveryAnuladoM, null);
        txtDeliveryEmitidoM.add(txtBoletasEmitidas11, null);
        jPanelWhite1.add(txtDeliveryEmitidoM, null);
        jPanelWhite1.add(txtDeliveryEmitido, null);
        jPanelWhite1.add(jLabelWhite13, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

  
    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        listaformadepago();
        sumaResumen();
        obtieneInfoResumen();
        obtieneInfoResumenNotacredito();
        calculoTotales();
        mostrarLabelHora();
    }
  
    private void listaformadepago()
    {
        try
        {
            DBReportes.cargaListaFormadePago(tableModelListaFormadePago,
                                             VariablesReporte.vFechaInicio, 
                                             VariablesReporte.vFechaFin);
            FarmaUtility.ordenar(tblFormaPago, 
                                 tableModelListaFormadePago, 
                                 0, 
                                 FarmaConstants.ORDEN_ASCENDENTE);
            lblCantidadReg.setText("" + tblFormaPago.getRowCount());
        }
        catch(SQLException sql)
        {
            log.error("",sql);
            FarmaUtility.showMessage(this, "Error al listar las Formas de Pago : \n" +sql.getMessage(),null);
        }
    }
  
    private void initTableListaFormadePago()
    {
        tableModelListaFormadePago = new FarmaTableModel(ConstantsReporte.columnsListaFormasdePago,
                                                         ConstantsReporte.defaultValuesListaListaFormasdePago,
                                                         0);
        FarmaUtility.initSimpleList(tblFormaPago,
                                    tableModelListaFormadePago,
                                    ConstantsReporte.columnsListaFormasdePago);
    }
  
    private void sumaResumen()
    {
        if(tblFormaPago.getRowCount()>0)
        {
            double totFormPago=0;
            for(int i=0;i<tblFormaPago.getRowCount();i++)
            {
                totFormPago = totFormPago + 
                              FarmaUtility.getDecimalNumber(tblFormaPago.getValueAt(i,2).toString().trim());
            }
            lblSumaTotal.setText(""+FarmaUtility.formatNumber(totFormPago));
        }
    }
    
    private void obtieneInfoResumen()
    {
        ArrayList infoResumen= new ArrayList();
        try
        {
            infoResumen = DBReportes.obtieneInfoResumen(VariablesReporte.vFechaInicio,VariablesReporte.vFechaFin);
        }
        catch (SQLException sql)
        {
            infoResumen= new ArrayList();
            log.error("",sql);
            FarmaUtility.showMessage(this,
                                     "Error al obtener informaci�n del resumen : \n" + sql.getMessage(),
                                     null);
        }   
        String flag="";
        String tipComp="";
        String tipPed ="";
        
        for(int i=0;i<infoResumen.size();i++)
        {
            flag=((String) ((ArrayList) infoResumen.get(i)).get(0)).trim();
            tipComp=((String) ((ArrayList) infoResumen.get(i)).get(1)).trim();
            //tipPed=((String) ((ArrayList) infoResumen.get(i)).get(1)).trim();
            
            if(flag.equals("N") && tipComp.equals(ConstantsReporte.TIP_COMP_BOLETA))
            {
                txtBoletasEmitidas.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                txtBoletasEmitidasM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }
            else if(flag.equals("N") && tipComp.equals(ConstantsReporte.TIP_COMP_FACTURA))
            {
                txtFacturasEmitidas.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                txtFacturasEmitidasM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }
            else if(flag.equals("N") && tipComp.equals(ConstantsReporte.TIP_COMP_GUIA))
            {
                txtGuiasEmitidas.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                txtGuiasEmitidasM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }
            //JCHAVEZ 13.07.2009.sn    
            else if ( flag.equals("N") && tipComp.equals(ConstantsReporte.TIP_COMP_TICKET) )
            {
                this.txtTicketsEmitidos.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                this.txtTicketsEmitidosM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }  //JCHAVEZ 13.07.2009.en
            //LLEIVA 05-Feb-2014 Se a�ade el indicador de Ticket Factura
            else if ( flag.equals("N") && tipComp.equals(ConstantsReporte.TIP_COMP_TICKET_FACT) )
            {
                this.txtTicketsFacEmitidos.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                this.txtTicketsFacEmitidosM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }  //JCHAVEZ 13.07.2009.en
            
            if(flag.equals("S") && tipComp.equals(ConstantsReporte.TIP_COMP_BOLETA))
            {
                txtBoletasAnuladas.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                txtBoletasAnuladasM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }
            else  if(flag.equals("S") && tipComp.equals(ConstantsReporte.TIP_COMP_FACTURA))
            {
                txtFacturasAnuladas.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                txtFacturasAnuladasM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }
            else if(flag.equals("S") && tipComp.equals(ConstantsReporte.TIP_COMP_GUIA))
            {
                txtGuiasAnuladas.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                txtGuiasAnuladasM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }
            //JCHAVEZ 13.07.2009.sn    
            else if ( flag.equals("S") && tipComp.equals(ConstantsReporte.TIP_COMP_TICKET) )
            {
                this.txtTicketsAnulados.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                this.txtTicketsAnuladosM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }
            //LLEIVA 05-Feb-2014 Se a�ade el indicador de Ticket Factura
            else if ( flag.equals("S") && tipComp.equals(ConstantsReporte.TIP_COMP_TICKET_FACT) )
            {
                this.txtTicketsFacAnulados.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                this.txtTicketsFacAnuladosM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }  //JCHAVEZ 13.07.2009.en
            
            if(flag.equals("N") && tipComp.equals(ConstantsReporte.TIP_PEDIDO_DELIVERY))
            {
                txtDeliveryEmitido.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                txtDeliveryEmitidoM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }
            else if(flag.equals("S") && tipComp.equals(ConstantsReporte.TIP_PEDIDO_DELIVERY))
            {
                txtDeliveryAnulado.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                txtDeliveryAnuladoM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );  
            }  
            
            if(flag.equals("N") && tipComp.equals(ConstantsReporte.TIP_PEDIDO_INSTITUCIONAL))
            {
                txtInstEmitida.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                txtInstEmitidoM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );
            }
            else if(flag.equals("S") && tipComp.equals(ConstantsReporte.TIP_PEDIDO_INSTITUCIONAL))
            {
                txtInstAnulado.setText( ((String) ((ArrayList) infoResumen.get(i)).get(2)).trim() );
                txtInstiAnuladoM.setText( ((String) ((ArrayList) infoResumen.get(i)).get(3)).trim() );  
            }  
        }
    }
    
  private void obtieneInfoResumenNotacredito()
  {
    ArrayList infoResumenNotaCredito= new ArrayList();
		try {
			 infoResumenNotaCredito = DBReportes.obtieneInfoResumenNotaCredito(VariablesReporte.vFechaInicio,VariablesReporte.vFechaFin);
		} catch (SQLException sql) {
      infoResumenNotaCredito= new ArrayList();
			log.error("",sql);
      FarmaUtility.showMessage(this,"Error al obtener informaci�n del resumen de Nota de cr�dito : \n" + sql.getMessage(),null);
	
      
		}   
    String tipComp = "" ;
    
    for(int i=0;i<infoResumenNotaCredito.size();i++){
      tipComp=((String) ((ArrayList) infoResumenNotaCredito.get(i)).get(0)).trim();
      
      if(tipComp.equals(ConstantsReporte.TIP_COMP_BOLETA)){
        txtNcbCantidad.setText( ((String) ((ArrayList) infoResumenNotaCredito.get(i)).get(1)).trim() );
        txtNcbMonto.setText( ((String) ((ArrayList) infoResumenNotaCredito.get(i)).get(2)).trim() );
      } if (tipComp.equals(ConstantsReporte.TIP_COMP_FACTURA)){
          txtNcfCantidad.setText( ((String) ((ArrayList) infoResumenNotaCredito.get(i)).get(1)).trim() );
          txtNcfMonto.setText( ((String) ((ArrayList) infoResumenNotaCredito.get(i)).get(2)).trim() );        
    }
  }
  }
    
    private void calculoTotales()
    {
        int cantboletas = 0, 
            cantfacturas = 0, 
            cantguias = 0, 
            totcantidad = 0, 
            cantdelivery = 0,
            cantinstitucionales = 0,
            canttickets = 0,
            cantticketsfac = 0;
        double montboletas = 0, 
                montfacturas = 0, 
                montguias = 0, 
                totmonto = 0, 
                montodelivery = 0,
                montonotacredito = 0, 
                montinstitucionales = 0, 
                monttickets=0,
                montticketsfac=0;
    
        cantboletas = (Integer.parseInt(txtBoletasEmitidas.getText().trim()) - 
                       Integer.parseInt(txtBoletasAnuladas.getText().trim()));
        cantfacturas = (Integer.parseInt(txtFacturasEmitidas.getText().trim()) - 
                        Integer.parseInt(txtFacturasAnuladas.getText().trim()));
        cantguias = (Integer.parseInt(txtGuiasEmitidas.getText().trim()) - 
                     Integer.parseInt(txtGuiasAnuladas.getText().trim()));
        canttickets = (Integer.parseInt(this.txtTicketsEmitidos.getText().trim()) - 
                      Integer.parseInt(this.txtTicketsAnulados.getText().trim()));//JCHAVEZ 13.07.2009.n
        //LLEIVA 05-Feb-2014 Se a�ade la suma de las cantidades de Tickets Factura
        cantticketsfac= (Integer.parseInt(this.txtTicketsFacEmitidos.getText().trim()) - 
                        Integer.parseInt(this.txtTicketsFacAnulados.getText().trim()));

        montboletas = FarmaUtility.getDecimalNumber(txtBoletasEmitidasM.getText().trim()) - 
                      FarmaUtility.getDecimalNumber(txtBoletasAnuladasM.getText().trim());
        montfacturas = FarmaUtility.getDecimalNumber(txtFacturasEmitidasM.getText().trim()) - 
                       FarmaUtility.getDecimalNumber(txtFacturasAnuladasM.getText().trim());
        montguias = FarmaUtility.getDecimalNumber(txtGuiasEmitidasM.getText().trim()) - 
                    FarmaUtility.getDecimalNumber(txtGuiasAnuladasM.getText().trim());
        monttickets=FarmaUtility.getDecimalNumber(this.txtTicketsEmitidosM.getText().trim()) - 
                    FarmaUtility.getDecimalNumber(this.txtTicketsAnuladosM.getText().trim());//JCHAVEZ 13.07.2009.n
        //LLEIVA 05-Feb-2014 Se a�ade la suma de los montos de Tickets Factura
        montticketsfac= FarmaUtility.getDecimalNumber(this.txtTicketsFacEmitidosM.getText().trim()) - 
                        FarmaUtility.getDecimalNumber(this.txtTicketsFacAnuladosM.getText().trim());
        
        montonotacredito = FarmaUtility.getDecimalNumber(txtNcbMonto.getText().trim()) + 
                           FarmaUtility.getDecimalNumber(txtNcfMonto.getText().trim());
        cantdelivery = (Integer.parseInt(txtDeliveryEmitido.getText().trim()) - 
                        Integer.parseInt(txtDeliveryAnulado.getText().trim()));
        montodelivery = FarmaUtility.getDecimalNumber(txtDeliveryEmitidoM.getText().trim()) - 
                        FarmaUtility.getDecimalNumber(txtDeliveryAnuladoM.getText().trim()); 
        cantinstitucionales = (Integer.parseInt(txtInstEmitida.getText().trim()) - 
                               Integer.parseInt(txtInstAnulado.getText().trim()));
        montinstitucionales = FarmaUtility.getDecimalNumber(txtInstEmitidoM.getText().trim()) - 
                              FarmaUtility.getDecimalNumber(txtInstiAnuladoM.getText().trim()); 
        
        totcantidad = cantboletas + 
                      cantfacturas + 
                      cantguias +
                      canttickets +     //JCHAVEZ 13.07.2009.n se agreg� canttickets
                      cantticketsfac;   //LLEIVA 05-Feb-2014
        totmonto = montboletas + 
                   montfacturas + 
                   montguias + 
                   montonotacredito+
                   monttickets +    //JCHAVEZ 13.07.2009.n monttickets
                   montticketsfac;  //LLEIVA 05-Feb-2014
        
        lblTotalCantidad.setText(""+totcantidad);
        lblTotalMonto.setText(""+FarmaUtility.formatNumber(totmonto));
        
        lblTotalCantidadDelivery.setText(""+cantdelivery);
        lblTotalMontoDelivery.setText(""+FarmaUtility.formatNumber(montodelivery));
        
        lblTotalCantidadInst.setText(""+cantinstitucionales);
        lblTotalMontoInst.setText(""+montinstitucionales);
    }
  
  private void initialize() {
    initTableListaFormadePago();
    FarmaVariables.vAceptar = false;
  }
  
   private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

  private void tblFormaPago_keyReleased(KeyEvent e)
  {
  if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(true);
    }
  }

  private void this_windowClosing(WindowEvent e)
  {
  FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
    void imprimeDetalleCierre()
    {
        if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        //FarmaPrintService vPrint = new FarmaPrintService(45,FarmaVariables.vImprReporte, true);
        FarmaPrintService vPrint = new FarmaPrintService(66,FarmaVariables.vImprReporte, true);
        log.debug(FarmaVariables.vImprReporte);
        
        if (!vPrint.startPrintService())
        {
            FarmaUtility.showMessage(this,"No se pudo inicializar el proceso de impresi�n",tblFormaPago);
            return;
        }
        try
        {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.alinearIzquierda(FarmaVariables.vDescCia,25)+FarmaPRNUtility.llenarBlancos(10)+ "Fecha: "+fechaActual,true);
            vPrint.printBold(FarmaPRNUtility.alinearIzquierda("Dpto. de Sistemas",20)+ FarmaPRNUtility.llenarBlancos(79),true);
            vPrint.printBold("",true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(18)+ "RESUMEN DE VENTAS - "+FarmaVariables.vDescCortaLocal,true);
            vPrint.printBold("",true);
            vPrint.activateCondensed();
            vPrint.printBold("Fecha Generacion de Reporte  : "+lblHoraTerm.getText(),true);
            vPrint.printBold("Fecha Inicio  : " + VariablesReporte.vFechaInicio + "  Fecha Fin  : "+ VariablesReporte.vFechaFin ,true);
            vPrint.printBold("=====================================================",true);
            
            vPrint.setEndHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(17)+"Cantidad              Monto",true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(17)+"--------      -------------",true);
            vPrint.printLine("Bol.Emitidas   :   "+FarmaPRNUtility.alinearDerecha(txtBoletasEmitidas.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(txtBoletasEmitidasM.getText().trim(),16),true);
            vPrint.printLine("Bol.Anuladas   :   "+FarmaPRNUtility.alinearDerecha(txtBoletasAnuladas.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(txtBoletasAnuladasM.getText().trim(),16)+"-",true);
            vPrint.printLine("Fact. Emitidas :   "+FarmaPRNUtility.alinearDerecha(txtFacturasEmitidas.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(txtFacturasEmitidasM.getText().trim(),16),true);
            vPrint.printLine("Fact. Anuladas :   "+FarmaPRNUtility.alinearDerecha(txtFacturasAnuladas.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(txtFacturasAnuladasM.getText().trim(),16)+"-",true);
            vPrint.printLine("Guias Emitidas :   "+FarmaPRNUtility.alinearDerecha(txtGuiasEmitidas.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(txtGuiasEmitidasM.getText().trim(),16),true);
            vPrint.printLine("Guias Anuladas :   "+FarmaPRNUtility.alinearDerecha(txtGuiasAnuladas.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(txtGuiasAnuladasM.getText().trim(),16)+"-",true);
            //LLEIVA 05-Feb-2014
            vPrint.printLine("Ticket Emitidos:   "+FarmaPRNUtility.alinearDerecha(txtTicketsEmitidos.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(txtTicketsEmitidosM.getText().trim(),16),true);
            vPrint.printLine("Ticket Anulados:   "+FarmaPRNUtility.alinearDerecha(txtTicketsAnulados.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(txtTicketsAnuladosM.getText().trim(),16)+"-",true);
            vPrint.printLine("Ticket Facturas:   "+FarmaPRNUtility.alinearDerecha(txtTicketsFacEmitidos.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(txtTicketsFacEmitidosM.getText().trim(),16),true);
            vPrint.printLine("Emitidos",true);
            vPrint.printLine("Ticket Facturas:   "+FarmaPRNUtility.alinearDerecha(txtTicketsFacAnulados.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(txtTicketsFacAnuladosM.getText().trim(),16)+"-",true);
            vPrint.printLine("Anulados",true);
            
            // vPrint.printLine("  P.Delivery   :   "+FarmaPRNUtility.alinearDerecha(lblCantDeli.getText().trim(),6)+"   "+FarmaPRNUtility.alinearDerecha(lblMontoDeli.getText().trim(),16),true);
            vPrint.printLine("------------------------------------------------",true);
            vPrint.printLine(" Total Venta      :  "+FarmaPRNUtility.alinearDerecha(lblTotalMonto.getText().trim(),16),true);
            vPrint.printBold("------------------------------------------------",true);
            
            vPrint.printLine(" Total Delivery   :  "+FarmaPRNUtility.alinearDerecha(lblTotalMontoDelivery.getText().trim(),16),true);
            
            vPrint.printBold("",true);
            vPrint.printLine("===================================================",true);
            vPrint.printBold("Codigo       Forma de Pago                    Total",true);
            vPrint.printLine("===================================================",true);
            vPrint.deactivateCondensed();
            
            for (int i=0; i<tblFormaPago.getRowCount();i++)
            {
              vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda(((String)tblFormaPago.getValueAt(i,0)).trim(),10) + "   " +
                      FarmaPRNUtility.alinearIzquierda(((String)tblFormaPago.getValueAt(i,1)).trim(),22) + "   " +
                      FarmaPRNUtility.alinearDerecha(((String)tblFormaPago.getValueAt(i,2)).trim(),13),true);
            }
            
            vPrint.activateCondensed();
            vPrint.printLine("===================================================",true);
            vPrint.printLine("Registros :  " + FarmaPRNUtility.alinearIzquierda(""+tblFormaPago.getRowCount(),2) + FarmaPRNUtility.llenarBlancos(22) + "" +
                          FarmaPRNUtility.alinearDerecha(lblSumaTotal.getText().trim(),14),true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        }
        catch(SQLException sql)
        {
            log.error("",sql);
            FarmaUtility.showMessage(this,"Ocurri� un error al imprimir : \n"+sql.getMessage(),tblFormaPago);    
        }
    }
  
    private void chkKeyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            cerrarVentana(true);
        }
        else if(UtilityPtoVenta.verificaVK_F2(e))
        {
            imprimeDetalleCierre();    
        }
    }

  private void tblFormaPago_keyPressed(KeyEvent e)
  {chkKeyPressed(e);
  }

  /**NUEVO!!
   * @Autor: Luis Reque Orellana
   * @Fecha: 13-04-2007
   * */
  private void mostrarLabelHora()
  {
    try
    {
      FarmaSearch.getFechaHoraBD(1);
      lblHoraTerm.setText(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA));  
    }
    catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Error al obtener la hora de t�rmino del proceso.",null);
      FarmaUtility.moveFocusJTable(tblFormaPago);
    }
  }

}