package consorcio.reportes;

import common.FarmaUtility;

import componentes.gs.worker.JDialogProgress;

import consorcio.liquidacion.DlgReporteVentasLaboratorio;
import consorcio.liquidacion.DlgReporteVentas_01;

import consorcio.reportes.reference.DBReportesAtencion;

import java.awt.Frame;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesoLabAnat extends JDialogProgress{
    String FechaInicio, FechaFin;
    private static final Logger log = LoggerFactory.getLogger(DlgProcesoLabAnat.class);
    
      private static Frame myParentFrame;
      private DlgReporteVentasLaboratorio dlg;
    public DlgProcesoLabAnat() {
        super();
    }


    public DlgProcesoLabAnat(Frame parent, String title, boolean modal,String FechaInicio,String FechaFin,
                             DlgReporteVentasLaboratorio auxDlg)
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
            //DBReportesAtencion.proceso_rtp_venta(FechaInicio, FechaFin);
           // FarmaUtility.aceptarTransaccion();
            dlg.cargarListadoVentasLab(FechaInicio, FechaFin);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            e.printStackTrace();
        }
    }
}
