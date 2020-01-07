package venta;


import componentes.gs.mifarma.SystemOutLogger;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.worker.JDialogProgress;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JLabel;

import common.FarmaConstants;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.caja.reference.DBCaja;
import venta.caja.reference.UtilityCaja;
import venta.hilos.SubProcesos;
import venta.reference.DBPtoVenta;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.reference.DBModuloVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesar extends JDialogProgress
{
  
  private static final Logger log = LoggerFactory.getLogger(DlgProcesar.class);
  
    private static Frame myParentFrame;
    boolean vVerPresioComp = false;

    public DlgProcesar()
  {
    this(null, "", false);
  }

  public DlgProcesar(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
      myParentFrame= parent;
  }
  
    public DlgProcesar(Frame parent, String title, boolean modal,String pIndVerPrecios)
    {
      super(parent, title, modal);
        myParentFrame= parent;
        if(pIndVerPrecios.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            vVerPresioComp = true;
            
    }
  
    public void cargaIndImpresionRojoTicket(){
        String pResultado = "";
     try
     {
        pResultado = DBModuloVenta.getIndImprimeRojo();
         log.info("pResultado:"+pResultado);    
         if(pResultado.trim().equalsIgnoreCase("S"))
            VariablesPtoVenta.vIndImprimeRojo = true;
         else
            VariablesPtoVenta.vIndImprimeRojo = false;
     }
     catch(SQLException err){
         log.error("",err);
        VariablesPtoVenta.vIndImprimeRojo = false;
     }
     
     log.info("VariablesPtoVenta.vIndImprimeRojo:"+VariablesPtoVenta.vIndImprimeRojo);
    }
    
    private void cargaDestinatarioEmailErrorCobro(){
        String pResultado = "";
        try
        {
           pResultado = DBModuloVenta.getDestinatarioErrorCobro();
           VariablesPtoVenta.vDestEmailErrorCobro = pResultado; 
        }
        catch(SQLException err){
            log.error("",err);
        }             
    }
    
    private void cargaDestinatarioEmailErrorAnulacion(){
        String pResultado = "";
        try
        {
           pResultado = DBModuloVenta.getDestinatarioErrorAnulacion();
           VariablesPtoVenta.vDestEmailErrorAnulacion = pResultado;
        }
        catch(SQLException err){
            log.error("",err);
        }                
    }
    
    private void cargaDestinatarioEmailErrorImpresion(){
        String pResultado = "";
        try
        {
           pResultado = DBModuloVenta.getDestinatarioErrorImpresion();
           VariablesPtoVenta.vDestEmailErrorImpresion = pResultado;
        }
        catch(SQLException err){
            log.error("",err);
        }                      
    }
    
    private void cargaIndVerStockLocales(){
        String pResultado = "";
        try
        {
           pResultado = DBModuloVenta.getIndVerStockLocales();
           VariablesPtoVenta.vIndVerStockLocales = pResultado;
        }
        catch(SQLException err){
           VariablesPtoVenta.vIndVerStockLocales = "N";
            log.error("",err);
        }                      
    }
 
    private void cargaIndVerRecetarioMagistral(){
        String pResultado = "";
        try
        {
           pResultado = DBModuloVenta.getIndVerRecetarioMagis();
           VariablesPtoVenta.vIndVerReceMagis = pResultado;
        }
        catch(SQLException err){
            log.error("",err);
           VariablesPtoVenta.vIndVerReceMagis = "N";
        }                      
    }

    /**
     * Grabas la imagenes del programa en el disco duro
     * @author ERIOS
     * @since 24.06.2013
     */
    private void grabarImagenesDisco() {        
        try {
            String sufijoEmpresa = DBPtoVenta.obtieneRutaImagen();
            String carpetaRaiz = DBPtoVenta.getDirectorioRaiz();
            String carpetaImagenes = DBPtoVenta.getDirectorioImagenes();
            String carpetaComprobantes = DBPtoVenta.getDirectorioComprobantes();
            
            //Crear carpeta raiz
            Path dir = Paths.get(carpetaRaiz);
            if(Files.notExists(dir)){
                Files.createDirectory(dir);
            }

            //Crear carpeta comprobantes
            dir = Paths.get(carpetaRaiz,carpetaComprobantes);
            if(Files.notExists(dir)){
                Files.createDirectory(dir);
            }
            
            //Crear carpeta imagenes
            dir = Paths.get(carpetaRaiz,carpetaImagenes);
            if(Files.notExists(dir)){
                Files.createDirectory(dir);
            }
            //Elimina el contenido del directorio
            //Verificar el metodo que borra codigos de barra
            /*if(Files.exists(dir)){
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir) ){
                    for (Path p : ds) {
                        Files.delete(p);
                    }
                } catch (IOException e) {
                    log.error("",e);
                }
            }*/
            //Copiar imagenes
            Path archivo = Paths.get(carpetaRaiz,carpetaImagenes,""+sufijoEmpresa);            
            URL u = FrmEconoFar.class.getResource("/venta/imagenes/"+sufijoEmpresa);            
            try (InputStream in = u.openStream()) {
                     Files.copy(in, archivo, StandardCopyOption.REPLACE_EXISTING);
                 }
            //consejos
            archivo = Paths.get(carpetaRaiz,carpetaImagenes,"consejo.jpg");            
            u = FrmEconoFar.class.getResource("/venta/imagenes/consejo.jpg");            
            try (InputStream in = u.openStream()) {
                     Files.copy(in, archivo, StandardCopyOption.REPLACE_EXISTING);
                 }
        } catch (IOException e) {
            log.error("Error al grabar imagenes al disco",e);
        } catch (SQLException e) {
            log.error("Error al recuperar informacion de la BBDD",e);
        }
    }

    /**
     * Indicador de servicios FarmaSix
     * @author ERIOS
     * @since 16.07.2013
     */
    private void cargaIndServicioFarmaSix() {
        String pResultado = "";
        try
        {
           pResultado = DBPtoVenta.getIndServicioFarmaSix();
           VariablesPtoVenta.vIndFarmaSix = pResultado;
        }
        catch(SQLException err){
            log.error("Error al ",err);
           VariablesPtoVenta.vIndFarmaSix = "N";
        }     
    }

    /**
     * Indicador de Pinpad
     * @author ERIOS
     * @since 16.08.2013
     */
    private void cargaIndPinpad() {
        String pResultado = "";
        try
        {
           pResultado = DBPtoVenta.getIndPinpad();
           VariablesPtoVenta.vIndPinpad = pResultado;
        }
        catch(SQLException err){
            log.error("Error al ",err);
           VariablesPtoVenta.vIndPinpad = "N";
        }     
    }   
    
    /**
     * Indicador de Impresion url web
     * @author ERIOS
     * @since 16.08.2013
     */
    private void cargaIndImprWeb() {
        String pResultado = "";
        try
        {
           pResultado = DBPtoVenta.getIndImprWeb();
           VariablesPtoVenta.vIndImprWeb = pResultado;
        }
        catch(SQLException err){
            log.error("Error al ",err);
           VariablesPtoVenta.vIndImprWeb = "S";
        }     
    }

    @Override
    public void ejecutaProceso() {

        try {
            VariablesPtoVenta.vNumeroDiasSinVentas = "0";//DBVentas.obtieneNumeroDiasSinVentas();
            cargaDestinatarioEmailErrorCobro();
            cargaDestinatarioEmailErrorAnulacion();
            cargaDestinatarioEmailErrorImpresion();
            cargaIndVerStockLocales();

            cargaIndVerRecetarioMagistral();
            grabarImagenesDisco();
            cargaIndServicioFarmaSix();
            cargaIndPinpad();
            cargaIndImprWeb();
            
            //ERIOS 2.2.8 Salida de mensajes ocultos
            SystemOutLogger.redirect();
            Thread.sleep(1000);
            //ERIOS 2.3.3 Carga de listado de productos
            SubProcesos subproceso1 = new SubProcesos("GET_PROD_VENTA",vVerPresioComp);
            subproceso1.start();
            while(subproceso1.isAlive()){
                ;
            }
            log.info("Termino procesar");
        } catch (Exception err) {
            log.error("", err);
            FarmaUtility.showMessage(this, "Error al obtener informacion relevante de la aplicacion.", null);
        }
    }

    /**
     * Indicador de Conciliacion En Linea
     * @author ERIOS
     * @since 29.11.2013
     */
    public static String cargaIndConciliaconOnline() {
        String pResultado = "";
        try
        {
           pResultado = DBPtoVenta.getIndConciliaconOnline();
        }
        catch(SQLException err){            
            log.error("Error al ",err);
            pResultado = "N";
        }    
        return pResultado;
    }
    
    /**
     * Se indica la version del sistema
     * @author ERIOS
     * @since 2.2.9
     */
    public static void setVersion() {        
        try
        {
           DBPtoVenta.setVersion();
        }
        catch(Exception err){            
            log.error("",err);
        }
    }    
}
