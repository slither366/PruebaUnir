package venta;


import componentes.gs.encripta.FarmaEncripta;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import javax.swing.JFrame;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.FarmaConstants;
import common.FarmaUtility;
import common.FarmaVariables;

import consorcio.HSurVariablesBD;

import consorcio.reportes.reference.DBReportesAtencion;

import venta.reference.BeanConexion;
import venta.reference.ConstantsPtoVenta;
import venta.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : EconoFar.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      27.12.2005   Creación<br>
 * ERIOS       20.06.2013   Modificacion<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class EconoFar {

    static private final Logger log = LoggerFactory.getLogger(EconoFar.class);

    static JFrame myparent = new JFrame();

    String prop1, prop2, prop3, prop4;
    private String vIndAdministracionGeneral = "N";

    /**
     * Frame principal de la Aplicación
     */
    public boolean lecturaConfiguracionBD_Reporte() {
        boolean propertiesClaveCorrecto = true;
        try {
            InputStream fis = null;
            Properties properties = null;
            File archivo = null;
            // LEE PROPERTIES DE LA CLAVE
            fis = this.getClass().getResourceAsStream(FarmaConstants.RUTA_PROPERTIES_CLAVE);

            if (fis == null) {
                archivo = new File(prop4);
                fis = new FileInputStream(archivo);
            }
            if (fis != null) {
                properties = new Properties();
                properties.load(fis);

                HSurVariablesBD.vClaveBD = properties.getProperty("ClaveBD_Reporte");
                HSurVariablesBD.vSID = properties.getProperty("SidBD_Reporte");
                HSurVariablesBD.vUsuarioBD = properties.getProperty("UsuarioBD_Reporte");
                HSurVariablesBD.vIpBD = properties.getProperty("IPBD_Reporte");
                HSurVariablesBD.vPuertoBD = properties.getProperty("vPuertoBD_Reporte");
                propertiesClaveCorrecto = true;
            } else {
                /*FarmaUtility.showMessage(myparent,
                                          "Archivo de Configuracion de Clave no Encontrado.\nPóngase en contacto con el área de sistemas.",
                                          null);-*/
                propertiesClaveCorrecto = false;
            }
            if (propertiesClaveCorrecto)
                return true;
            else
                return false;
        } catch (FileNotFoundException fnfException) {
            log.error("", fnfException);
            /* FarmaUtility.showMessage(myparent,
                                      "Archivo de Configuracion de Clave no Encontrado.\nPóngase en contacto con el área de sistemas.",
                                      null);*/
        } catch (IOException ioException) {
            log.error("", ioException);
            /*FarmaUtility.showMessage(myparent,
                                      "Error al leer archivo de Configuracion de Clave.\nPóngase en contacto con el área de sistemas.",
                                      null);*/
        }

        return false;
    }


    public EconoFar() {
        if (readFileProperties() && readFilePasswordProperties() && readFileServRemotosProperties() &&
            lecturaConfiguracionBD_Reporte()) {

            if (vIndAdministracionGeneral.trim().equalsIgnoreCase("N")) {

        Frame frame = new FrmEconoFar();
                frame.setLocationRelativeTo(null);
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                frame.setVisible(true);
            } else {
                if (vIndAdministracionGeneral.trim().equalsIgnoreCase("VER")) {
                    Frame frame = new FrmVerPrecios();
                    frame.setLocationRelativeTo(null);
                    frame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    frame.setVisible(true);
                } else {
                    Frame frame = new FrmAdministracion();
                    frame.setLocationRelativeTo(null);
                    frame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    frame.setVisible(true);
                }
            }
        }
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void main(String[] args) {
        String s1 = "SALBUTAMOL , APRO" ; 
         
         
                  System.out.println(s1.contains( "SALBUTAMOL" )); 
         
        
        colocandoLookAndFeel();
        //20.12.2007 ERIOS Se modifica el metodo para cargar desde el jar.
        if (args.length == 3) {
            log.debug(args[0]);
            log.debug(args[1]);
            log.debug(args[2]);
            new EconoFar(args[0], args[1], args[2]);
        } else if (args.length == 2) 
        {
            new EconoFar_Matriz(args[0], args[1]);
            //new EconoFar();
        } else {
            new EconoFar();
        }
    }

    /**
     * Realiza la lectura del archivo Properties para determinar el seteo de
     * variables
     */
    private boolean readFileProperties() {
        boolean propertiesServidorCorrecto = false;
        boolean propertiesClienteCorrecto = true;
        try {
            InputStream fis = null;
            Properties properties = null;
            File archivo = null;
            // LEE PROPERTIES DEL SERVIDOR
            fis = this.getClass().getResourceAsStream("/PtoVentaServ.properties");

            if (fis == null) {
                archivo = new File(prop1);
                fis = new FileInputStream(archivo);
            }
            if (fis != null) {
                properties = new Properties();
                properties.load(fis);
                FarmaVariables.vCodGrupoCia = properties.getProperty("CodigoGrupoCompania");
                FarmaVariables.vCodCia = properties.getProperty("CodigoCompania");
                FarmaVariables.vCodLocal = properties.getProperty("CodigoLocal");
                FarmaVariables.vImprReporte = properties.getProperty("ImpresoraReporte");
                FarmaVariables.vIPBD = properties.getProperty("IpServidor");


                try {
                    vIndAdministracionGeneral = properties.getProperty("IndAdministracion");
                } catch (Exception e) {
                    vIndAdministracionGeneral = "N";
                    // TODO: Add catch code
                    e.printStackTrace();
                }


                try {

                    if (properties.getProperty("verUtilidad").equalsIgnoreCase("N"))
                        VariablesPtoVenta.vPermiteVerUtilidad = false;
                    else
                        VariablesPtoVenta.vPermiteVerUtilidad = true;

                } catch (Exception e) {
                    vIndAdministracionGeneral = "N";
                    // TODO: Add catch code
                    e.printStackTrace();
                    VariablesPtoVenta.vPermiteVerUtilidad = true;
                }

                try {

                    if (properties.getProperty("cargaProductoLinea").equalsIgnoreCase("N"))
                        VariablesPtoVenta.vCargaProductoOnline = false;
                    else
                        VariablesPtoVenta.vCargaProductoOnline = true;
                } catch (Exception e) {
                    // TODO: Add catch code
                    e.printStackTrace();
                    VariablesPtoVenta.vCargaProductoOnline = false;
                }


                propertiesServidorCorrecto = true;
            } else {
                FarmaUtility.showMessage(myparent,
                                         "Archivo de Configuracion del Servidor no Encontrado.\nPóngase en contacto con el área de sistemas.",
                                         null);
                propertiesServidorCorrecto = false;
            }
            if (propertiesServidorCorrecto && propertiesClienteCorrecto)
                return true;
            else
                return false;
        } catch (FileNotFoundException fnfException) {
            log.error("", fnfException);
            FarmaUtility.showMessage(myparent,
                                     "Archivo de Configuracion del Servidor no Encontrado.\nPóngase en contacto con el área de sistemas.",
                                     null);
        } catch (IOException ioException) {
            log.error("", ioException);
            FarmaUtility.showMessage(myparent,
                                     "Error al leer archivo de Configuracion.\nPóngase en contacto con el área de sistemas.",
                                     null);
        }
        myparent.dispose();
        return false;
    }

    /**
     * Realiza la lectura del archivo Properties para determinar la clave
     * de conexion con BD
     */
    private boolean readFilePasswordProperties() {
        boolean propertiesClaveCorrecto = true;
        try {
            InputStream fis = null;
            Properties properties = null;
            File archivo = null;
            // LEE PROPERTIES DE LA CLAVE
            fis = this.getClass().getResourceAsStream(FarmaConstants.RUTA_PROPERTIES_CLAVE);

            if (fis == null) {
                archivo = new File(prop2);
                fis = new FileInputStream(archivo);
            }
            if (fis != null) {
                properties = new Properties();
                properties.load(fis);
                FarmaVariables.vClaveBD = FarmaEncripta.desencripta(properties.getProperty("ClaveBD"));

                FarmaVariables.vSID = properties.getProperty("SID");
                if (FarmaVariables.vSID == null) {
                    FarmaVariables.vSID = ConstantsPtoVenta.SID;
                }

                FarmaVariables.vUsuarioBD = properties.getProperty("UsuarioBD");
                if (FarmaVariables.vUsuarioBD == null) {
                    FarmaVariables.vUsuarioBD = ConstantsPtoVenta.USUARIO_BD;
                }

                propertiesClaveCorrecto = true;
            } else {
                FarmaUtility.showMessage(myparent,
                                         "Archivo de Configuracion de Clave no Encontrado.\nPóngase en contacto con el área de sistemas.",
                                         null);
                propertiesClaveCorrecto = false;
            }
            if (propertiesClaveCorrecto)
                return true;
            else
                return false;
        } catch (FileNotFoundException fnfException) {
            log.error("", fnfException);
            FarmaUtility.showMessage(myparent,
                                     "Archivo de Configuracion de Clave no Encontrado.\nPóngase en contacto con el área de sistemas.",
                                     null);
        } catch (IOException ioException) {
            log.error("", ioException);
            FarmaUtility.showMessage(myparent,
                                     "Error al leer archivo de Configuracion de Clave.\nPóngase en contacto con el área de sistemas.",
                                     null);
        }
        myparent.dispose();
        return false;
    }

    /**
     * Realiza la lectura del archivo Properties para determinar los valores de las bases de datos remotas
     */
    private boolean readFileServRemotosProperties() {
        boolean propertiesServidorCorrecto = false;
        boolean propertiesClienteCorrecto = true;
        /*FarmaVariables.vIdUsuDBMatriz = "UsuarioMatriz";
        FarmaVariables.vClaveBDMatriz = "ClaveMatriz";
        FarmaVariables.vIpServidorDBMatriz = "IpServidorMatriz";
        FarmaVariables.vSidDBMatriz = "SidMatriz";


        FarmaVariables.vIdUsuDBDelivery = "UsuarioDelivery";
        FarmaVariables.vClaveBDDelivery = "ClaveDelivery";
        FarmaVariables.vIpServidorDBDelivery = "IpServidorDelivery";
        FarmaVariables.vSidDBDelivery = "SidDelivery";


        FarmaVariables.vIdUsuDBADMCentral = "UsuarioADMCentral";
        FarmaVariables.vClaveBDADMCentral = "ClaveAdmCentral";
        FarmaVariables.vIpServidorDBADMCentral = "IpServidorADMCentral";
        FarmaVariables.vSidDBADMCentral = "SidADMCentral";


        FarmaVariables.vIdUsuDBRac = "UsuarioRAC";
        FarmaVariables.vClaveBDRac = "ClaveRAC";
        FarmaVariables.vIpServidorDBRac = "IpServidorRAC";
        FarmaVariables.vSidDBRac = "SidRAC";*/

        /*
        try {
            InputStream fis = null;
            Properties properties = null;
            File archivo = null;

            fis = this.getClass().getResourceAsStream("/PtoVentaServRemotos.properties");

            if (fis == null) {
                archivo = new File(prop3);
                fis = new FileInputStream(archivo);
            }
            if (fis != null) {
                properties = new Properties();
                properties.load(fis);

                FarmaVariables.vIdUsuDBMatriz = properties.getProperty("UsuarioMatriz");
                FarmaVariables.vClaveBDMatriz = FarmaEncripta.desencripta(properties.getProperty("ClaveMatriz"));
                FarmaVariables.vIpServidorDBMatriz = properties.getProperty("IpServidorMatriz");
                FarmaVariables.vSidDBMatriz = properties.getProperty("SidMatriz");


                FarmaVariables.vIdUsuDBDelivery = properties.getProperty("UsuarioDelivery");
                FarmaVariables.vClaveBDDelivery = FarmaEncripta.desencripta(properties.getProperty("ClaveDelivery"));
                FarmaVariables.vIpServidorDBDelivery = properties.getProperty("IpServidorDelivery");
                FarmaVariables.vSidDBDelivery = properties.getProperty("SidDelivery");


                FarmaVariables.vIdUsuDBADMCentral = properties.getProperty("UsuarioADMCentral")+FarmaVariables.vCodLocal;
                FarmaVariables.vClaveBDADMCentral =FarmaEncripta.desencripta(properties.getProperty("ClaveAdmCentral"));
                FarmaVariables.vIpServidorDBADMCentral = properties.getProperty("IpServidorADMCentral");
                FarmaVariables.vSidDBADMCentral = properties.getProperty("SidADMCentral");


                FarmaVariables.vIdUsuDBRac = properties.getProperty("UsuarioRAC");
                FarmaVariables.vClaveBDRac = FarmaEncripta.desencripta(properties.getProperty("ClaveRAC"));
                FarmaVariables.vIpServidorDBRac = properties.getProperty("IpServidorRAC");
                FarmaVariables.vSidDBRac = properties.getProperty("SidRAC");

                //ERIOS 19.06.2013 Se lee las variables para la conexion remota con el servidor FASA
                BeanConexion beanFasa = new BeanConexion();
                beanFasa.setUsuarioBD(properties.getProperty("UsuarioFASA"));
                beanFasa.setClaveBD(FarmaEncripta.desencripta(properties.getProperty("ClaveFASA")));
                beanFasa.setIPBD(properties.getProperty("IpServidorFASA"));
                beanFasa.setSID(properties.getProperty("SidFASA"));
                beanFasa.setPORT(properties.getProperty("PortFASA"));
                VariablesPtoVenta.conexionFasa = beanFasa;

                //GFONSECA 01.07.2013 Se lee las variables para la conexion remota con el servidor ADM
                BeanConexion beanAdm = new BeanConexion();
                beanAdm.setUsuarioBD(FarmaVariables.vIdUsuDBADMCentral);
                beanAdm.setClaveBD(FarmaVariables.vClaveBDADMCentral);
                beanAdm.setIPBD(FarmaVariables.vIpServidorDBADMCentral);
                beanAdm.setSID(FarmaVariables.vSidDBADMCentral);
                VariablesPtoVenta.conexionAdm = beanAdm;

                propertiesServidorCorrecto = true;
            } else {
                FarmaUtility.showMessage(myparent,
                                         "Archivo de Configuracion del Servidor no Encontrado.\n" +
                                         "Póngase en contacto con el área de sistemas.",
                                         null);
                propertiesServidorCorrecto = false;
            }
            if (propertiesServidorCorrecto && propertiesClienteCorrecto)
                return true;
            else
                return false;
        } catch (FileNotFoundException fnfException) {
            log.error("",fnfException);
            FarmaUtility.showMessage(myparent,
                                     "Archivo de Configuracion del Servidor no Encontrado.\n" +
                                     "Póngase en contacto con el área de sistemas.",
                                     null);
        } catch (IOException ioException) {
            log.error("",ioException);
            FarmaUtility.showMessage(myparent,
                                     "Error al leer archivo de Configuracion.\n" +
                                     "Póngase en contacto con el área de sistemas.",
                                     null);
        }
        myparent.dispose();
        return false;*/
        return true;
    }

    /**
     * Constructor que recibe parametros de properties
     * @param arch1 Properties FarmaVenta
     * @param arch2 Properties Clave
     * @param arch3 Properties Servidor Remoto
     * @author Edgar Rios Navarro
     * @since 20.12.2007
     */
    public EconoFar(String arch1, String arch2, String arch3) {
        prop1 = arch1;
        prop2 = arch2;
        prop3 = arch3;
        if (readFileProperties() && readFilePasswordProperties() && readFileServRemotosProperties()) {
            if (vIndAdministracionGeneral.trim().equalsIgnoreCase("N")) {

                Frame frame = new FrmEconoFar();
                frame.setLocationRelativeTo(null);
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                frame.setVisible(true);
            } else {

                Frame frame = new FrmAdministracion();
                frame.setLocationRelativeTo(null);
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                frame.setVisible(true);
            }

        }
    }

    private void jbInit() throws Exception {
        myparent.setSize(new Dimension(400, 300));
    }

    public static void colocandoLookAndFeel() {
        try {

            /*for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    System.out.println("CHOSEN THIS");
                    break;
                } else {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                }
            }*/
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        } catch (Exception e) {
        }
    }
}
