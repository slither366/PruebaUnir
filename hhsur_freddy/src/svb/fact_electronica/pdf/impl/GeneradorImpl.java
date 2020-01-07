package svb.fact_electronica.pdf.impl;

import svb.fact_electronica.pdf.bean.DocumentoCabeceraFB;
import svb.fact_electronica.pdf.bean.DocumentoCabeceraNT;
import svb.fact_electronica.pdf.bean.DocumentoDetalleFB;
import svb.fact_electronica.pdf.bean.DocumentoDetalleNT;
import svb.fact_electronica.pdf.bean.Empresa;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

import svb.fact_electronica.pdf.bean.Empresa;

public class GeneradorImpl {
    
    public Empresa obtenerDatosEmpresa(Connection conn, String codGrupoCia,String pCodLocal) throws SQLException {
        CallableStatement cstm = null;
        Empresa empresa = null;

        if (conn != null) {
            cstm = conn.prepareCall("{ CALL PTOVENTA_JASPER_ELECTRONICO.SP_DATA_EMISOR(?,?,?)}");
            cstm.setString(1, codGrupoCia);
            cstm.setString(2, pCodLocal);
            cstm.registerOutParameter(3, OracleTypes.CURSOR);

            cstm.executeQuery();
            ResultSet rs = (ResultSet) cstm.getObject(3);
            if (rs.next()) {
                empresa = new Empresa(rs.getString(1),
                                        rs.getString(2),
                                        rs.getString(3),
                                        rs.getString(4),
                                        rs.getString(5),
                                        rs.getString(6),
                                        rs.getString(7),
                                        rs.getString(8),
                                        rs.getString(9),
                                        rs.getString(10),
                                        rs.getString(11),
                                        rs.getString(12));
            }
        } else {
            throw new SQLException("SP_DATA_EMISOR: PROBLEMAS DE CONEXION A LA BD!...");
        }
        return empresa;
    }

    public DocumentoCabeceraFB obtenerDatosCabeceraFB(Connection conn, String numPedVta, String codGrupoCia, String codLocal) throws SQLException {
        CallableStatement cstm = null;
        DocumentoCabeceraFB cabecera = null;

        if (conn != null) {
            cstm = conn.prepareCall("{ CALL PTOVENTA_JASPER_ELECTRONICO.SP_DATA_DOCUMENTO_CAB_FB(?,?,?,?)}");
            cstm.setString(1, codGrupoCia);
            cstm.setString(2, codLocal);
            cstm.setString(3, numPedVta);
            cstm.registerOutParameter(4, OracleTypes.CURSOR);

            cstm.executeQuery();
            ResultSet rs = (ResultSet) cstm.getObject(4);
            if (rs.next()) {
                cabecera = new DocumentoCabeceraFB(rs.getString(1),
                                                    rs.getString(2),
                                                    rs.getString(3),
                                                    rs.getString(4),
                                                    rs.getString(5),
                                                    rs.getString(6),
                                                    rs.getString(7),
                                                    rs.getString(8),
                                                    rs.getString(9),
                                                    rs.getString(10),
                                                    rs.getString(11),
                                                    rs.getString(12),
                                                    rs.getString(13),
                                                    rs.getString(14),
                                                    rs.getString(15),
                                                    rs.getString(16),
                                                    rs.getString(17),
                                                    rs.getString(18));
            }
        } else {
            throw new SQLException("SP_DATA_DOCUMENTO_CAB: PROBLEMAS DE CONEXION A LA BD!...");
        }
        return cabecera;
    }

    public List<DocumentoDetalleFB> obtenerDatosDetalleFB(Connection conn, String numPedVta, String codGrupoCia, String codLocal) throws SQLException {
        CallableStatement cstm = null;
        DocumentoDetalleFB detalleItem = null;
        List<DocumentoDetalleFB> listaDocumentoDetalle = null;

        if (conn != null) {
            cstm = conn.prepareCall("{ CALL PTOVENTA_JASPER_ELECTRONICO.SP_DATA_DOCUMENTO_DET_FB(?,?,?,?)}");
            cstm.setString(1, codGrupoCia);
            cstm.setString(2, codLocal);
            cstm.setString(3, numPedVta);
            cstm.registerOutParameter(4, OracleTypes.CURSOR);

            cstm.executeQuery();
            ResultSet rs = (ResultSet) cstm.getObject(4);
            listaDocumentoDetalle = new ArrayList<>();
            while (rs.next()) {
                detalleItem = new DocumentoDetalleFB(rs.getString(1),
                                                    rs.getString(2),
                                                    rs.getString(3),
                                                    rs.getString(4),
                                                    rs.getString(5),
                                                    rs.getString(6));
                listaDocumentoDetalle.add(detalleItem);
            }
        } else {
            throw new SQLException("SP_DATA_DOCUMENTO_DET: PROBLEMAS DE CONEXION A LA BD!...");
        }
        return listaDocumentoDetalle;
    }
    
    public DocumentoCabeceraNT obtenerDatosCabeceraNT(Connection conn, String numPedVta, String codGrupoCia, String codLocal) throws SQLException {
        CallableStatement cstm = null;
        DocumentoCabeceraNT cabecera = null;

        if (conn != null) {
            cstm = conn.prepareCall("{ CALL PTOVENTA_JASPER_ELECTRONICO.SP_DATA_DOCUMENTO_CAB_NT(?,?,?,?)}");
            cstm.setString(1, codGrupoCia);
            cstm.setString(2, codLocal);
            cstm.setString(3, numPedVta);
            cstm.registerOutParameter(4, OracleTypes.CURSOR);

            cstm.executeQuery();
            ResultSet rs = (ResultSet) cstm.getObject(4);
            if (rs.next()) {
                cabecera = new DocumentoCabeceraNT(rs.getString(1),
                                                    rs.getString(2),
                                                    rs.getString(3),
                                                    rs.getString(4),
                                                    rs.getString(5),
                                                    rs.getString(6),
                                                    rs.getString(7),
                                                    rs.getString(8),
                                                    rs.getString(9),
                                                    rs.getString(10),
                                                    rs.getString(11),
                                                    rs.getString(12),
                                                    rs.getString(13),
                                                    rs.getString(14),
                                                    rs.getString(15),
                                                    rs.getString(16),
                                                    rs.getString(17),
                                                    rs.getString(18));
            }
        } else {
            throw new SQLException("SP_DATA_DOCUMENTO_CAB: PROBLEMAS DE CONEXION A LA BD!...");
        }
        return cabecera;
    }

    public List<DocumentoDetalleNT> obtenerDatosDetalleNT(Connection conn, String numPedVta, String codGrupoCia, String codLocal) throws SQLException {
        CallableStatement cstm = null;
        DocumentoDetalleNT detalleItem = null;
        List<DocumentoDetalleNT> listaDocumentoDetalle = null;

        if (conn != null) {
            cstm = conn.prepareCall("{ CALL PTOVENTA_JASPER_ELECTRONICO.SP_DATA_DOCUMENTO_DET_NT(?,?,?,?)}");
            cstm.setString(1, codGrupoCia);
            cstm.setString(2, codLocal);
            cstm.setString(3, numPedVta);
            cstm.registerOutParameter(4, OracleTypes.CURSOR);

            cstm.executeQuery();
            ResultSet rs = (ResultSet) cstm.getObject(4);
            listaDocumentoDetalle = new ArrayList<>();
            while (rs.next()) {
                detalleItem = new DocumentoDetalleNT(rs.getString(1),
                                                    rs.getString(2),
                                                    rs.getString(3),
                                                    rs.getString(4),
                                                    rs.getString(5),
                                                    rs.getString(6));
                listaDocumentoDetalle.add(detalleItem);
            }
        } else {
            throw new SQLException("SP_DATA_DOCUMENTO_DET: PROBLEMAS DE CONEXION A LA BD!...");
        }
        return listaDocumentoDetalle;
    }

}
