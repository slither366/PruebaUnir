package consorcio.reportes;

import common.FarmaUtility;

import componentes.gs.worker.JDialogProgress;

import consorcio.liquidacion.DlgReporteVentas_01;

import consorcio.reportes.reference.DBReportesAtencion;

import java.awt.Frame;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesoVentaHH extends JDialogProgress{
    String FechaInicio, FechaFin;
    private static final Logger log = LoggerFactory.getLogger(DlgProcesoVentaHH.class);
    
      private static Frame myParentFrame;
      private DlgReporteVentas_01 dlg;
    public DlgProcesoVentaHH() {
        super();
    }


    public DlgProcesoVentaHH(Frame parent, String title, boolean modal,String FechaInicio,String FechaFin,
                             DlgReporteVentas_01 auxDlg)
    {
    super(parent, title, modal);
      myParentFrame= parent;
      this.FechaInicio = FechaInicio;
        this.FechaFin = FechaFin;
        dlg = auxDlg;
    }

    @Override
    public void ejecutaProceso() {


        try {
            DBReportesAtencion.proceso_rtp_venta(FechaInicio, FechaFin);
            FarmaUtility.aceptarTransaccion();
            dlg.busqueda();
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            e.printStackTrace();
        }
    }
}
