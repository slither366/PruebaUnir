package venta.pinpad.mastercard;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.Color;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiloReporteTotalPinpadMC extends Thread
{
    private static final Logger log = LoggerFactory.getLogger(HiloReporteTotalPinpadMC.class);
    public JLabelWhite lblMensaje = new JLabelWhite();
    public JPanelTitle pnlMensaje = new JPanelTitle();
    public JPanelWhite pnlFondo = new JPanelWhite();
    public JLabelFunction lblEsc = new JLabelFunction();
    
    public void run()
    {   ManejadorTramaRetornoMC mtrmc = new ManejadorTramaRetornoMC();
        Map<String,String> resultado = new HashMap<String,String>();
        try
        {   resultado = mtrmc.reporteTotales();
            String txt_imprimir = resultado.get("print_data");
            
            if(resultado!= null && "00".equals(resultado.get("response_code")))
            {   if(txt_imprimir != null && !("".equals(txt_imprimir)))
                    mtrmc.imprVoucher(txt_imprimir);
                mtrmc.guardarTramaRepTotalBD(resultado);
                exitoTransaccion();
            }
            else
            {   errorTransaccion();
            }
        }
        catch(Exception ex)
        {   log.error("",ex);
        }
    }
    
    private void exitoTransaccion()
    {   lblMensaje.setText("SE RETORNARON LOS DATOS PARA EL REPORTE TOTAL");
        pnlMensaje.setBackground(new Color(49, 141, 43));
        lblMensaje.setForeground(Color.BLACK);
        lblEsc.setEnabled(true);
    }
    
    private void errorTransaccion()
    {   lblMensaje.setText("ERROR EN EL PROCESO DE REPORTE TOTAL. INTENTE NUEVAMENTE");
        pnlMensaje.setBackground(Color.RED);
        lblEsc.setEnabled(true);
    }
}
