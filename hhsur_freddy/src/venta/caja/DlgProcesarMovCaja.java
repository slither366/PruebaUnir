package venta.caja;


import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JTextFieldSanSerif;
import componentes.gs.worker.JDialogProgress;

import java.awt.Frame;

import java.sql.SQLException;

import common.FarmaSearch;
import common.FarmaUtility;
import common.FarmaVariables;

import componentes.gs.worker.JDialogProgress;

import venta.administracion.fondoSencillo.reference.DBFondoSencillo;
import venta.administracion.fondoSencillo.reference.UtilityFondoSencillo;
import venta.administracion.fondoSencillo.reference.VariablesFondoSencillo;
import venta.caja.reference.ConstantsCaja;
import venta.caja.reference.VariablesCaja;
import venta.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesarMovCaja extends JDialogProgress {
    
    private static final Logger log = LoggerFactory.getLogger(DlgProcesarMovCaja.class);
    
    private DlgMovCaja dlgMovCaja;
    private JLabelFunction lblEsc;
    private JTextFieldSanSerif txtFactura;
    private String vTipMovCajaAux;
    
    public DlgProcesarMovCaja(Frame frame, String string, boolean b) {
        super(frame, string, b);
    }

    public DlgProcesarMovCaja() {
        super();
    }

    @Override
    public void ejecutaProceso()
    {
        boolean flag = true, vAsignaMovCajaSencillo = false;
        try
        {
            /*
             * is tiene sencillo asignado
             * */
            /*
             * dubilluz - 15.06.2010
             * */
            flag = true;
            vAsignaMovCajaSencillo = true;
            
            if (flag)
            {
                boolean flagFinal = true;
                dlgMovCaja.buscarComprobantes(); //01/02/09 asolis
                
                // dubilluz 27.05.2015
                //if (dlgMovCaja.ValidarIngreso_Boleta_Factura())
                if(true)
                {
                    dlgMovCaja.efectuarMovimiento();
                    
                    if( UtilityFondoSencillo.indActivoFondo())
                    {
                        //JMIRANDA 03.03.2010
                        if (vAsignaMovCajaSencillo)
                        {
                            VariablesFondoSencillo.vSecFondoSen = DBFondoSencillo.aceptaMontoAsignado(FarmaVariables.vNuSecUsu);
                        }
                        ///
                        if (VariablesFondoSencillo.vIndTieneFondoSencillo.equalsIgnoreCase("S") && 
                            VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_APERTURA))
                        {
                            //JMIRANDA 02.03.10 RELACIONA MOVIENTO CAJA EN CE_FONDO_SENCILLO 
                            if(VariablesFondoSencillo.vSecFondoSen.trim().equalsIgnoreCase("N"))
                            {
                                flagFinal = false;
                                FarmaUtility.liberarTransaccion(); //libera bloqueo
                                FarmaUtility.showMessage(this,
                                                        "El Monto asignado ha sido anulado.\n" +
                                                        "Confirme con el Administrador del Local.",
                                                         txtFactura);
                            }
                            else
                            {
                                DBFondoSencillo.grabaSecMovCajaFondoSencillo(FarmaVariables.vNuSecUsu.trim(), 
                                                                            VariablesCaja.vNumCaja.trim(), 
                                                                            VariablesFondoSencillo.vSecFondoSen.trim());
                                //imprimir Asignaci�n Aceptado
                                flagFinal = true;
                                /*UtilityFondoSencillo.imprimeVoucherDiferencias(this,
                                                                            VariablesFondoSencillo.vSecFondoSen,txtFactura); */
                                VariablesFondoSencillo.vImprimeVoucherFondoSencillo
                                            =UtilityFondoSencillo.imprimeVoucherDiferencias_DU(this,
                                                                                VariablesFondoSencillo.vSecFondoSen,txtFactura,true);
                            }
                        }
                    }

                    if (flagFinal)
                    {
                        FarmaSearch.updateNumera(ConstantsPtoVenta.TIP_NUMERA_MOV_CAJA);
                        FarmaUtility.aceptarTransaccion();
        
                        /* Cesar Huanes  ..05/12/13 
                         * Se cambio  los mensajes correctos para el cierre y apertura de caja.
                         *Se cambio la variable vTipMovCajaAux por  vTipMovCaja
                         */
                        if (VariablesCaja.vTipMovCaja.equals(ConstantsPtoVenta.TIP_MOV_CAJA_CIERRE))
                        {
                            FarmaUtility.showMessage(this, 
                                                    "Se guard� el cierre de caja correctamente", 
                                                    lblEsc);
                        }
                        if (VariablesCaja.vTipMovCaja.equals(ConstantsCaja.MOVIMIENTO_APERTURA))
                        {
                            //dubilluz 20.07.2010
                            String mensaje = "Ticket de confirmaci�n de fondo de sencillo se ha impreso con �xito.\nLa operaci�n se realiz� correctamente";
                            if(VariablesFondoSencillo.vImprimeVoucherFondoSencillo)
                            {
                                FarmaUtility.showMessage(this, mensaje, lblEsc);
                            }
                            else
                            {
                                FarmaUtility.showMessage(this, 
                                                         "La operaci�n de apertura de caja se  realiz� correctamente", 
                                                         lblEsc);
                            }
                        }
                        dlgMovCaja.cerrarVentana(true);
                    }
                }
                else
                {
                    flagFinal=false;
                    flag=false;
                    dlgMovCaja.LimpiarTextBox();
                    FarmaUtility.showMessage(this, 
                                             "El N�mero de Boleta y/o Factura no corresponde(n) al n�mero de comprobantes en el Sistema.Verifique !", 
                                             lblEsc);
                }
            }
        }
        catch (SQLException ex)
        {
            FarmaUtility.liberarTransaccion();
            
            String mensaje = "";
            if (ex.getErrorCode() == 20009)
                mensaje = "No se puede aperturar una caja cuando ya se encuentra abierta.";
            else if (ex.getErrorCode() == 20010)
                mensaje = "No se puede cerrar una caja cuando ya se encuentra cerrada.";
            else if (ex.getErrorCode() == 20011)
                mensaje = "No se puede cerrar caja ya que existen pedidos pendientes o en proceso de cobro. Vuelva a intentar!!!.";
            else if (ex.getErrorCode() == 20018)
            {
                //ERIOS 06.12.2013 En caso se envio el codigo 20018.
                mensaje = "No se puede cerrar caja ya que existen pedidos pendientes o en proceso de cobro. Vuelva a intentar!!!";
            }
            else
            {
                log.error("",ex);
                mensaje = ex.getMessage();
            }
            FarmaUtility.showMessage(this, 
                                    "Error al registrar movimiento de caja : \n" +
                                    mensaje, 
                                    lblEsc);
            dlgMovCaja.cerrarVentana(false);
        }
    }

    public void setDlgMovCaja(DlgMovCaja dlgMovCaja) {
        this.dlgMovCaja = dlgMovCaja;
    }

    public DlgMovCaja getDlgMovCaja() {
        return dlgMovCaja;
    }

    public void setLblEsc(JLabelFunction lblEsc) {
        this.lblEsc = lblEsc;
    }

    public JLabelFunction getLblEsc() {
        return lblEsc;
    }

    public void setTxtFactura(JTextFieldSanSerif txtFactura) {
        this.txtFactura = txtFactura;
    }

    public JTextFieldSanSerif getTxtFactura() {
        return txtFactura;
    }

    public void setVTipMovCajaAux(String vTipMovCajaAux) {
        this.vTipMovCajaAux = vTipMovCajaAux;
    }

    public String getVTipMovCajaAux() {
        return vTipMovCajaAux;
    }
}
