create or replace package PTOVENTA_CME_ADM AS
TYPE FarmaCursor IS REF CURSOR;

TIPO_BUSQUEDA_COMPROBANTE CONSTANT CHAR(1) := 'C';
TIPO_BUSQUEDA_PACIENTE     CONSTANT CHAR(1) := 'P';
TIPO_BUSQUEDA_ACTUALIZAR   CONSTANT CHAR(1) := 'A';

ESTADO_COMPROBANTE_PROCESADO     CONSTANT CHAR(1) := 'P';
ESTADO_COMPROBANTE_SINPROCESAR   CONSTANT CHAR(1) := 'A';
ESTADO_COMPROBANTE_INACTIVO   CONSTANT CHAR(1) := 'I';

ESTADO_AMEDICA_PEND_CONSULTA   CONSTANT CHAR(1) := 'P';

TIPO_OPERACION_INSERT CONSTANT CHAR(1) := 'I';
TIPO_OPERACION_UPDATE CONSTANT CHAR(1) := 'U';

INDICAR_ANULADO_SI CONSTANT CHAR(1) := 'S';
INDICAR_ANULADO_NO CONSTANT CHAR(1) := 'N';

FUNCTION CME_LISTA_TIPOS_DOCUMENTO(cCodGrupoCia_in IN CHAR)
RETURN FarmaCursor;

FUNCTION CME_LISTA_TIPOS_PACIENTE(cCodGrupoCia_in IN CHAR)
RETURN FarmaCursor;

FUNCTION CME_LISTA_TIPOS_PARENTESCO(cCodGrupoCia_in IN CHAR)
RETURN FarmaCursor;

FUNCTION CME_LISTA_TIPOS_SEXO(cCodGrupoCia_in IN CHAR)
RETURN FarmaCursor;

FUNCTION CME_LISTA_TIPOS_EST_CIVIL(cCodGrupoCia_in IN CHAR)
RETURN FarmaCursor;

FUNCTION CME_LISTA_TIPOS_GRADO_INS(cCodGrupoCia_in IN CHAR)
RETURN FarmaCursor;

FUNCTION CME_LISTA_TIPOS_FACTORRH(cCodGrupoCia_in IN CHAR)
RETURN FarmaCursor;

FUNCTION CME_LISTA_TIPOS_GRUPOSAN(cCodGrupoCia_in IN CHAR)
RETURN FarmaCursor;

FUNCTION CME_LISTA_TIPOS_SOL_MEDICA(cCodGrupoCia_in IN CHAR)
RETURN FarmaCursor;

FUNCTION CME_LISTA_MEDICOS(cCodMedico_in  IN VARCHAR2,
                           cApellido_in     IN VARCHAR2,
                           cNombre_in     IN VARCHAR2,
                           cCMP_in        IN VARCHAR2)
RETURN FarmaCursor;

FUNCTION CME_LISTA_ESPECIALIDADES
RETURN FarmaCursor;

FUNCTION CME_LISTA_PACIENTES(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cTipBusqueda_in IN CHAR,
                                cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                                cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE,
                                cTipDoc_in      IN vta_pedido_atencion_medica.tip_documento%TYPE,
                                cNumDoc_in      IN vta_pedido_atencion_medica.num_documento%TYPE,
                                cNombre_in      IN vta_pedido_atencion_medica.nombre%TYPE,
                                cApePat_in      IN vta_pedido_atencion_medica.ape_pat%TYPE,
                                cApeMat_in      IN vta_pedido_atencion_medica.ape_mat%TYPE)

RETURN FARMACURSOR;

FUNCTION CME_VALIDA_COMPROBANTE_PAGO(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                                       cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE)
RETURN FarmaCursor;

FUNCTION CME_GET_BENEFICIARIO(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                              cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE)
RETURN FarmaCursor;


FUNCTION CME_GET_EDAD(cFecNacimiento IN VARCHAR2)

RETURN FarmaCursor;


FUNCTION CME_GRABAR_PACIENTE_HC(cCodGrupoCia_in IN CHAR,cCodLocal_in    IN CHAR,vUsuario IN VARCHAR2,vTipoDDM IN VARCHAR2,
                                vNumHistoriaClinica IN VARCHAR2,vCodPaciente IN VARCHAR2,
                                vApePatPac_in IN VARCHAR2,vApeMatPac_in IN VARCHAR2,vNombrePac_in IN VARCHAR2,
                                vTipDocPac_in IN VARCHAR2,vNumDocPac_in IN VARCHAR2,
                                vNumHCFisica_in VARCHAR2,vFecHCFisica_in IN VARCHAR2,
                                vTipAcomp_in VARCHAR2,vNombreAcom_in IN VARCHAR2,vTipDocAcom_in IN VARCHAR2,vNumDocAcom_in IN VARCHAR2,
                                vSexo_in IN VARCHAR2,vEstadoCivil_in VARCHAR2,vFecNac VARCHAR2,vDireccion_in IN VARCHAR2,
                                vDepUbigeo_in IN VARCHAR2,vPvrUbigeo_in IN VARCHAR2,vDisUbigeo_in IN VARCHAR2,
                                vDepLugNac_in IN VARCHAR2,vPvrLugNac_in IN VARCHAR2,vDisLugNac_in IN VARCHAR2,
                                vDepLugPro_in IN VARCHAR2,vPvrLugPro_in IN VARCHAR2,vDisLugPro_in IN VARCHAR2,
                                vGradoIns_in IN VARCHAR2,vOcupacion_in IN VARCHAR2,vCentroEL_in IN VARCHAR2,
                                vGrupoSan_in IN VARCHAR2,vFactorRH_in IN VARCHAR2,
                                vCorreo_in IN VARCHAR2,vTelFijo_in IN VARCHAR2,vTelCel_in IN VARCHAR2)

RETURN VARCHAR2;



FUNCTION CME_INSERT_ATENCION_MEDICA(cCodGrupoCia_in IN CME_ATENCION_MEDICA.COD_GRUPO_CIA%type,
                                cCodCia_in      IN CME_ATENCION_MEDICA.COD_CIA%type,
                                cCodLocal_in    IN CME_ATENCION_MEDICA.COD_LOCAL%type,
                                cUsu_in         IN CME_ATENCION_MEDICA.USU_CREA%type,
                                cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                                cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE,
                                cNumPedVta_in   IN vta_comp_atencion_medica.Num_Ped_Vta%TYPE,
                                cCodLocalVta_in IN vta_comp_atencion_medica.Cod_Local%TYPE,
                                vCodPaciente    IN CME_ATENCION_MEDICA.COD_PACIENTE%type,
                                vCodMedico      IN CME_ATENCION_MEDICA.COD_MEDICO%type,
                                vEstado         IN CME_ATENCION_MEDICA.ESTADO%type,
                                vCodConsulta    IN CME_ATENCION_MEDICA.COD_MAES_DET%type,
                                vCodTipAten     IN Varchar2,
                                vIdConsultorio_in in varchar2,
                                vIdBus_in in varchar2,
                                vOrdenMedica_in in varchar2 default 'N')
RETURN VARCHAR2;



FUNCTION CME_INSERT_TRIAJE   (cCodGrupoCia_in IN CME_ATENCION_MEDICA_TRI.COD_GRUPO_CIA%type,
                                cCodCia_in      IN CME_ATENCION_MEDICA_TRI.COD_CIA%type,
                                cCodLocal_in    IN CME_ATENCION_MEDICA_TRI.COD_LOCAL%type,
                                cUsu_in         IN CME_ATENCION_MEDICA.USU_CREA%type,
                                cNumAtenMed_in  IN CME_ATENCION_MEDICA_TRI.NUM_ATEN_MED%type,
                                nPA1_in         IN CME_ATENCION_MEDICA_TRI.PA_1%type,
                                nPA2_in         IN CME_ATENCION_MEDICA_TRI.PA_2%type,
                                nFR_in          IN CME_ATENCION_MEDICA_TRI.FR%type,
                                nFC_in          IN CME_ATENCION_MEDICA_TRI.FC%type,
                                nTemp_in        IN VARCHAR2,--IN CME_ATENCION_MEDICA_TRI.TEMP%type,
                                nPeso_in        IN VARCHAR2,--CME_ATENCION_MEDICA_TRI.PESO%type,
                                nTalla_in       IN CME_ATENCION_MEDICA_TRI.TALLA%type,
                                nSaturacion_in  IN CME_ATENCION_MEDICA_TRI.SATURACION_OXIGENO%type)
RETURN VARCHAR2;

PROCEDURE CME_UPDATE_COMPROBANTE(cCodGrupoCia_in IN CME_ATENCION_MEDICA.COD_GRUPO_CIA%type,
                                 cNumPedVta_in   IN vta_comp_atencion_medica.Num_Ped_Vta%TYPE,
                                 cCodLocalVta_in IN vta_comp_atencion_medica.Cod_Local%TYPE,
                                 cUsu_in         IN CME_ATENCION_MEDICA.USU_CREA%type,
                                 cEstado_in in char,
                                 vNumAtencion_in in cme_atencion_medica.num_aten_med%TYPE
                                 );
                                /*cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                                cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE);*/


FUNCTION CME_GET_NUM_HIST_CLINICA(cCodGrupoCia_in    IN CME_HISTORIA_CLINICA.COD_GRUPO_CIA%type,
                                  vCodPaciente_in    IN CME_HISTORIA_CLINICA.COD_PACIENTE%TYPE,
                                  vNumDocPac_in      IN CME_PACIENTE.NUM_DOCUMENTO%type,
                                  vNumDocAcom_in     IN CME_HISTORIA_CLINICA.NUM_DOC_ACOM%type,
                                  cTipoOperacion_in  IN CHAR)
RETURN VARCHAR2;

FUNCTION CME_ANULAR_ATENCION_MEDICA(cCodGrupoCia_in   IN CME_ATENCION_MEDICA.COD_GRUPO_CIA%TYPE,
                                    cCodCia_in      IN CME_ATENCION_MEDICA.COD_CIA%type,
                                    cCodLocal_in      IN CME_ATENCION_MEDICA.COD_LOCAL%TYPE,
                                    cNumAtencion_in   IN CME_ATENCION_MEDICA.NUM_ATEN_MED%TYPE,
                                    cUsuario_in       IN CME_ATENCION_MEDICA.USU_MOD%TYPE,
                                    cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                                    cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE)

RETURN VARCHAR2;



FUNCTION CME_F_VARC_DATO_COMP(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                              cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE)
  RETURN VARCHAR2;

FUNCTION CME_F_VARC_DATO_COMP_MEDICA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    cNumPedAtencionMedica_in     IN CHAR)
RETURN VARCHAR2;

FUNCTION CME_F_CUR_DATOS_EXISTE_DNI(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR,cDNI_in IN CHAR)
RETURN FarmaCursor;

FUNCTION CME_F_VALIDA_FECHA(vFecha_in IN VARCHAR2,vMensaje_in IN VARCHAR2)
RETURN VARCHAR2;

FUNCTION F_LISTA_CONSULTAS
RETURN FARMACURSOR;

FUNCTION CME_GET_CONSULTA(cCodBusqueda_in IN CHAR)
RETURN FarmaCursor;

FUNCTION CME_GET_DESC_EST_CIVIL(cCodGrupoCia_in IN CHAR, cCod_Est_Civil IN CHAR)
RETURN char;

FUNCTION CME_GET_DESC_GR_INS(cCodGrupoCia_in IN CHAR, cCod_Gr_ins IN CHAR)
RETURN char;

FUNCTION F_OBTENER_NRO_ATENCION_MED(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,
                                    cCodLocal_in IN CHAR,codPaciente IN CHAR)
RETURN char;

FUNCTION CME_GET_DESC_GR_SANGUINEO(cCodGrupoCia_in IN CHAR, cCod_Gr_sang IN CHAR)
RETURN char;

FUNCTION CME_GET_DESC_FACTOR_RH(cCodGrupoCia_in IN CHAR, cCod_factor IN CHAR)
RETURN char;

FUNCTION CME_GET_DESC_TIPO_INF(cCodGrupoCia_in IN CHAR, cCod_inf IN CHAR)
RETURN char;

FUNCTION CME_GET_DESC_FUN_BIO(cCodGrupoCia_in IN CHAR, cCod_funcion IN CHAR)
RETURN char;

FUNCTION CME_GET_DESC_ESTADO_GRAL(cCodGrupoCia_in IN CHAR, cCod_estado IN CHAR)
RETURN char;

FUNCTION CME_GET_DESC_LUGAR(cCodGrupoCia_in IN CHAR,cCod_Dep IN CHAR, cCod_PPro IN CHAR, cCod_Dis IN CHAR)
RETURN char;

FUNCTION F_LISTA_TRAZABILIDAD(cCodGrupoCia_in IN CHAR,vFecIni   IN  VARCHAR2,
                                vFecFin   IN  VARCHAR2,vCodMed IN VARCHAR2 DEFAULT NULL,
                                vFilView IN CHAR DEFAULT NULL)
RETURN FARMACURSOR;

FUNCTION F_LISTA_LIBERADOS(cCodGrupoCia_in IN CHAR,vFecIni   IN  VARCHAR2,
                                vFecFin   IN  VARCHAR2/*,vCodMed IN VARCHAR2,
                                vFilView IN CHAR*/)
RETURN FARMACURSOR;

  
PROCEDURE CME_INSERT_TIP_DOCUMENTO(cCodGrupoCia_in IN CME_ATENCION_MEDICA.COD_GRUPO_CIA%type,
                                   cCodLocalVta_in IN vta_comp_atencion_medica.Cod_Local%TYPE,
                                   cTipDocDesc IN pbl_tip_documentos.desc_tip_documento%TYPE
                                   );  

END;
/
create or replace package body PTOVENTA_CME_ADM AS

  FUNCTION CME_LISTA_TIPOS_DOCUMENTO(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      select trim(cod_tip_documento) || 'Ã' ||
             --trim(decode(cod_tip_documento,02,'CE',desc_tip_documento))
             trim(desc_tip_documento)
      from pbl_tip_documentos V
      where V.SELECCIONABLE = 'S'
      order by 1 asc;
      -- cod_tip_documento in ('01','02','06');

      RETURN curVta;

 END;

  FUNCTION CME_LISTA_TIPOS_PACIENTE(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT trim(a.cod_maes_det) || 'Ã' ||
             trim(a.descripcion)
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND    b.cod_maestro=23;

      RETURN curVta;

 END;

   FUNCTION CME_LISTA_TIPOS_PARENTESCO(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT trim(a.cod_maes_det) || 'Ã' ||
             trim(a.descripcion)
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND    b.cod_maestro=24;

      RETURN curVta;

 END;

  FUNCTION CME_LISTA_TIPOS_SEXO(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT trim(a.valor1) || 'Ã' ||
             trim(a.descripcion)
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND    b.cod_maestro=25;

      RETURN curVta;

 END;

  FUNCTION CME_LISTA_TIPOS_EST_CIVIL(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT trim(a.cod_maes_det) || 'Ã' ||
             trim(a.descripcion)
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND    b.cod_maestro=26;

      RETURN curVta;

 END;

  FUNCTION CME_LISTA_TIPOS_GRADO_INS(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT trim(a.cod_maes_det) || 'Ã' ||
             trim(a.descripcion)
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND    b.cod_maestro=27;

      RETURN curVta;

 END;

   FUNCTION CME_LISTA_TIPOS_FACTORRH(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT trim(a.cod_maes_det) || 'Ã' ||
             trim(a.descripcion)
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND    b.cod_maestro=28;

      RETURN curVta;

 END;


   FUNCTION CME_LISTA_TIPOS_SOL_MEDICA(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT trim(a.cod_maes_det) || 'Ã' ||
             trim(a.descripcion)
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND    b.cod_maestro=42;

      RETURN curVta;

 END;

   FUNCTION CME_LISTA_TIPOS_GRUPOSAN(cCodGrupoCia_in IN CHAR)
  RETURN FarmaCursor
  IS
      curVta FarmaCursor;
  BEGIN
      OPEN curVta FOR
      SELECT trim(a.cod_maes_det) || 'Ã' ||
             trim(a.descripcion)
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND    b.cod_maestro=29;

      RETURN curVta;

 END;

  FUNCTION CME_LISTA_MEDICOS(cCodMedico_in  IN VARCHAR2,
                             cApellido_in     IN VARCHAR2,
                             cNombre_in     IN VARCHAR2,
                             cCMP_in        IN VARCHAR2)
  RETURN FarmaCursor
  IS
  cur FarmaCursor;

    v_select   varchar2(32767):='';
    v_WhereAnd varchar2(32767):='';
    v_OrderBy    varchar2(32767):='';

  BEGIN

             IF (cCodMedico_in is not null AND LENGTH(cCodMedico_in)<>0) THEN
             v_WhereAnd:=v_WhereAnd||' AND A.cod_medico='||''''||cCodMedico_in||'''';
             END IF;
             IF (cApellido_in is not null AND LENGTH(cApellido_in)<>0) THEN
             v_whereAnd:=v_WhereAnd||' AND TRIM(A.des_ape_medico) LIKE '||'''%'||cApellido_in||'%''';
             END IF;
             IF (cNombre_in is not null AND LENGTH(cNombre_in)<>0) THEN
             v_WhereAnd:=v_WhereAnd||' AND TRIM(A.des_nom_medico) LIKE '||'''%'||cNombre_in||'%''';
             END IF;
             IF (cCMP_in is not null AND LENGTH(cCMP_in)<>0) THEN
             v_WhereAnd:=v_WhereAnd||' AND TRIM(A.num_cmp)='||''''||cCMP_in||'''';
             END IF;


       v_select:='select a.cod_medico||'||''''||'Ã'||''''||
                 '||nvl(a.num_cmp,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                 '||a.des_ape_medico||'||''''||' '||''''||'||a.des_nom_medico||'||''''||'Ã'||''''||
                 '||nvl(a.des_ape_medico,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                 '||nvl(a.des_nom_medico,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                 '||nvl(a.num_cmp,'||''''||' '||''''||') '||
                 --'||nvl(b.descripcion,'||''''||' '||''''||') '||
                 'from mae_medico a
                 where 1 = 1
                 ' ;
                 --/*,maestro_detalle b
                /* ' where a.COD_ESPECIALIDAD_I=b.COD_MAES_DET
                  and a.flg_activo='||''''||'1'||''''||
                 'and a.flg_medi_centro='||''''||'1'||'''';*/

       v_OrderBy := v_OrderBy||' order by a.des_ape_medico asc';


       v_select:=v_select||v_WhereAnd||v_OrderBy;


  OPEN cur FOR v_select;


  RETURN cur;
  END;

    FUNCTION CME_LISTA_ESPECIALIDADES
  RETURN FarmaCursor
  IS
  cur FarmaCursor;
  BEGIN
  OPEN cur FOR
  SELECT a.cod_maes_det || 'Ã' || a.descripcion from maestro_detalle a,maestro b
    where a.cod_maestro=b.cod_maestro
    and b.cod_maestro=35
    order by a.descripcion asc;

  RETURN cur;
  END;


   FUNCTION CME_LISTA_PACIENTES(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in    IN CHAR,
                                cTipBusqueda_in IN CHAR,--"P : Busqueda Paciente , C : "Busqueda Comprobante""
                                cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                                cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE,
                                cTipDoc_in      IN vta_pedido_atencion_medica.tip_documento%TYPE,
                                cNumDoc_in      IN vta_pedido_atencion_medica.num_documento%TYPE,
                                cNombre_in      IN vta_pedido_atencion_medica.nombre%TYPE,
                                cApePat_in      IN vta_pedido_atencion_medica.ape_pat%TYPE,
                                cApeMat_in      IN vta_pedido_atencion_medica.ape_mat%TYPE)
   RETURN FARMACURSOR IS
    curLista FARMACURSOR;

    vTipoBusqueda   char(1);


    cursor c_beneficiario is
    select a.num_ped_vta,a.num_comp_pago,a.estado est_comp,
           b.tip_documento,
           b.num_documento,
           b.ape_pat,
           b.ape_mat,
           b.nombre,
           b.estado est_pedido
    from vta_comp_atencion_medica a,
         vta_pedido_atencion_medica b
    where a.cod_grupo_cia=cCodGrupoCia_in--'001'
    AND   a.cod_local=cCodLocal_in--'028'
    AND   a.tip_comp_pago=cTipComPago_in--'01'
    AND   a.num_comp_pago=cNumComPago_in--'BA0100002006'
    AND   a.cod_grupo_cia=b.cod_grupo_cia(+)
    AND   a.cod_local=b.cod_local(+)
    AND   a.num_ped_vta=b.num_ped_vta (+)
    ;


    l_regBeneficiario c_beneficiario%ROWTYPE;

    v_select varchar2(32767):='';
    v_WhereAnd varchar2(32767):='';

    vflagWhere boolean:=false;

  BEGIN


       IF (cTipBusqueda_in=TIPO_BUSQUEDA_COMPROBANTE) THEN
         OPEN c_beneficiario;
         FETCH c_beneficiario into l_regBeneficiario;
         CLOSE c_beneficiario;

         vflagWhere:=false;

             IF (l_regBeneficiario.tip_documento is not null AND LENGTH(l_regBeneficiario.tip_documento)<>0) THEN
             v_WhereAnd:=v_WhereAnd||' AND A.COD_TIP_DOCUMENTO='||''''||UPPER(l_regBeneficiario.tip_documento)||'''';
             vflagWhere:=true;
             END IF;             IF (l_regBeneficiario.num_documento is not null AND LENGTH(l_regBeneficiario.num_documento)<>0) THEN
             v_whereAnd:=v_WhereAnd||' AND A.NUM_DOCUMENTO='||''''||UPPER(l_regBeneficiario.num_documento)||'''';
             vflagWhere:=true;
             END IF;
             IF (l_regBeneficiario.ape_pat is not null AND LENGTH(l_regBeneficiario.ape_pat)<>0) THEN
             v_WhereAnd:=v_WhereAnd||' AND A.APE_PAT_CLI='||''''||UPPER(l_regBeneficiario.ape_pat)||'''';
             vflagWhere:=true;
             END IF;
             IF (l_regBeneficiario.ape_mat is not null AND LENGTH(l_regBeneficiario.ape_mat)<>0) THEN
             v_WhereAnd:=v_WhereAnd||' AND A.APE_MAT_CLI='||''''||UPPER(l_regBeneficiario.ape_mat)||'''';
             vflagWhere:=true;
             END IF;
             IF (l_regBeneficiario.nombre is not null AND LENGTH(l_regBeneficiario.nombre)<>0) THEN
             v_WhereAnd:=v_WhereAnd||' AND A.NOM_CLI='||''''||UPPER(l_regBeneficiario.nombre)||'''';
             vflagWhere:=true;
             END IF;


             IF(vflagWhere=false) THEN
             v_WhereAnd:=v_WhereAnd||' AND A.COD_PACIENTE='||''''||'0'||''''; --Si no hay datos de busqueda no realizar la busqueda
             END IF;

       ELSIF (cTipBusqueda_in=/*'P'*/TIPO_BUSQUEDA_PACIENTE) THEN

             IF (cTipDoc_in is not null AND LENGTH(cTipDoc_in)<>0) THEN
             v_WhereAnd:=v_WhereAnd||' AND A.COD_TIP_DOCUMENTO='||''''||cTipDoc_in||'''';
             END IF;
             IF (cNumDoc_in is not null AND LENGTH(cNumDoc_in)<>0) THEN
             v_whereAnd:=v_WhereAnd||' AND A.NUM_DOCUMENTO='||''''||cNumDoc_in||'''';
             END IF;
             IF (cApePat_in is not null AND LENGTH(cApePat_in)<>0) THEN
             v_WhereAnd:=v_WhereAnd||' AND A.APE_PAT_CLI='||''''||cApePat_in||'''';
             END IF;
             IF (cApeMat_in is not null AND LENGTH(cApeMat_in)<>0) THEN
             v_WhereAnd:=v_WhereAnd||' AND A.APE_MAT_CLI='||''''||cApeMat_in||'''';
             END IF;
             IF (cNombre_in is not null AND LENGTH(cNombre_in)<>0) THEN
             v_WhereAnd:=v_WhereAnd||' AND A.NOM_CLI LIKE '||'''%'||cNombre_in||'%''';
             END IF;


       ELSIF (cTipBusqueda_in=TIPO_BUSQUEDA_ACTUALIZAR) THEN
             v_WhereAnd:=v_WhereAnd||' AND A.COD_PACIENTE='||''''||'0'||''''; --En la busqueda inicial para actualizar no debe mostrar resultados

       END IF;


         v_select:='select a.COD_PACIENTE||'||''''||'Ã'||''''||
                   '||nvl(a.COD_TIP_DOCUMENTO,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   --'||nvl(decode(a.COD_TIP_DOCUMENTO,02,'||''''||'CE'||''''||',b.desc_tip_documento),'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(b.desc_tip_documento,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.num_documento,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.ape_pat_cli,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.ape_mat_cli,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.nom_cli,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||a.ape_pat_cli||'||''''||' '||''''||'||a.ape_mat_cli||'||''''||' '||''''||'||a.nom_cli||'||''''||'Ã'||''''||
                   '||a.ind_estado ||'||''''||'Ã'||''''||
                  ---
                   '||nvl(a.sexo_cli,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.estado_civil,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(to_char(a.fec_nac_cli,'||''''||'dd/mm/yyyy'||''''||'),'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   --'||nvl(a.lugar_nacimiento,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   --'||nvl(a.lugar_procedencia,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   --'||nvl(a.ubigeo,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(NULL,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(NULL,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(NULL,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.dir_cli,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.grado_instruccion,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.ocupacion,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.centro_edu_lab,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.email,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.fono_cli,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(a.cell_cli,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(c.cod_tip_acom,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(c.nom_acom,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(c.cod_tip_doc_acom,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(c.num_doc_acom,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(c.nro_hc_fisica,'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||nvl(to_char(c.fecha_hc,'||''''||'dd/mm/yyyy'||''''||'),'||''''||' '||''''||')||'||''''||'Ã'||''''||
                   '||c.factor_rh ||'||''''||'Ã'||''''||
                   '||c.grupo_sang  ||'||''''||'Ã'||''''||
                   --'||nvl(c.nro_hc_actual,'||''''||' '||''''||') '||
                   '||nvl(c.nro_hc_actual,'||''''||' '||''''||') ||'||''''||'Ã'||''''||
                   '||nvl(a.DEP_UBIGEO,'||''''||' '||''''||') ||'||''''||'Ã'||''''||
                   '||nvl(a.PRV_UBIGEO,'||''''||' '||''''||') ||'||''''||'Ã'||''''||
                   '||nvl(a.DIS_UBIGEO,'||''''||' '||''''||') ||'||''''||'Ã'||''''||
                   '||nvl(a.DEP_LUG_NAC,'||''''||' '||''''||') ||'||''''||'Ã'||''''||
                   '||nvl(a.PRV_LUG_NAC,'||''''||' '||''''||') ||'||''''||'Ã'||''''||
                   '||nvl(a.DIS_LUG_NAC,'||''''||' '||''''||') ||'||''''||'Ã'||''''||
                   '||nvl(a.DEP_LUG_PRO,'||''''||' '||''''||') ||'||''''||'Ã'||''''||
                   '||nvl(a.PRV_LUG_PRO,'||''''||' '||''''||') ||'||''''||'Ã'||''''||
                   '||nvl(a.DIS_LUG_PRO,'||''''||' '||''''||') '||
                   'from cme_paciente a,pbl_tip_documentos b,cme_historia_clinica c
                    where a.cod_grupo_cia=c.cod_grupo_cia (+)
                    AND  a.cod_paciente=c.cod_paciente (+)
                     AND a.COD_TIP_DOCUMENTO=b.cod_tip_documento
                    AND a.COD_GRUPO_CIA='||''''||cCodGrupoCia_in||'''';
                    -- AND a.COD_TIP_DOCUMENTO=b.cod_tip_documento

           v_select:=v_select||v_WhereAnd;
dbms_output.put_line(v_select);
         OPEN curLista FOR v_select;


    RETURN curLista;
  END;


  FUNCTION CME_GET_BENEFICIARIO(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                              cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE)

   RETURN FARMACURSOR IS
   curLista FARMACURSOR;

  BEGIN
             OPEN curLista FOR
                    select
                     nvl(b.tip_documento,'01') || 'Ã' ||
                     nvl(b.num_documento,' ') || 'Ã' ||
                     nvl(b.ape_pat,' ') || 'Ã' ||
                     nvl(b.ape_mat,' ') || 'Ã' ||
                     nvl(b.nombre,' ')
                    from vta_comp_atencion_medica a,
                         vta_pedido_atencion_medica b
                    where a.cod_grupo_cia=cCodGrupoCia_in--'001'
                    AND a.cod_local=cCodLocal_in--'028'
                    AND a.tip_comp_pago=cTipComPago_in--'01'
                    AND a.num_comp_pago=cNumComPago_in--'BA0100002011'
                    AND a.cod_grupo_cia=b.cod_grupo_cia(+)
                    AND a.cod_local=b.cod_local(+)
                    AND a.num_ped_vta=b.num_ped_vta (+)
                    ;


     RETURN curLista;
  END;


  FUNCTION CME_GET_EDAD(cFecNacimiento IN VARCHAR2)
  RETURN FARMACURSOR IS
  curLista FARMACURSOR;
  v_FecNac DATE;

  --vfechatexto varchar2(10):='03/08/2015';
  v_totaldias number;
  v_limite_anio number:=5;
  v_limite_dia number:=28;
  v_qanio number:=0;
  v_ranio number:=0;
  v_qmes  number:=0;
  v_rmes  number:=0;
  v_qdia  number:=0;

  v_flagMayorEdad  char(1):='N';

v_salida varchar2(1000);

  BEGIN

    v_FecNac:=to_date(CME_F_VALIDA_FECHA(cFecNacimiento,'de nacimiento'),'dd/mm/yyyy');

    select trunc(sysdate)-trunc(to_date(cFecNacimiento,'dd/mm/yyyy')) into v_totaldias from dual;
    --dbms_output.put_line('total días : '||v_totaldias);

    if(v_totaldias>=365) then
        v_qanio:=trunc(v_totaldias/365);
        v_ranio:=mod(v_totaldias,365);
        if(v_ranio>=30) then
          v_qmes:=trunc(v_ranio/30);
          v_rmes:=mod(v_ranio,30);
        elsif (v_ranio>0) then
            v_rmes:=v_ranio;
        end if;

    elsif (v_totaldias>=30) then
        v_qmes:=trunc(v_totaldias/30);
        v_rmes:=mod(v_totaldias,30);
    else
        v_rmes:=v_totaldias;
    end if;


    if(v_qanio>=5) then
      v_qmes:=0;
      v_rmes:=0;
    elsif(v_qanio>=0  AND  v_qmes<>0) then
          v_rmes:=0;
    end if;

    if(v_qanio<>0) then
    v_salida:=v_salida||v_qanio||' Año(s)  ';--' A\u00f1os  ';
    end if;
    if(v_qmes<>0) then
    v_salida:=v_salida||v_qmes||' Mes(es)  ';
    end if;
    if(v_rmes<>0) then
    v_salida:=v_salida||v_rmes||' Día(s)  ';--' D\u00edas';
    end if;


    /**** condicion mayor de edad ****/
    IF(v_qanio>=18) THEN
       v_flagMayorEdad:='S';
    ELSE
       v_flagMayorEdad:='N';
    END IF;
    /**** condicion mayor de edad ****/


    /*IF(cFecNacimiento is null ) THEN
       v_salida:=' '     || 'Ã' ||'-1'|| 'Ã' ||'LA FECHA NO PUEDE SER NULA'             || 'Ã' ||' ';
    ELSIF(v_qanio>=120) THEN
       v_salida:=' '     || 'Ã' ||'-1'|| 'Ã' ||'INGRESE UNA FECHA VALIDA'            || 'Ã' ||' ';
    ELS*IF(v_totaldias<0) THEN
       v_salida:=' '     || 'Ã' ||'-1'|| 'Ã' ||'LA FECHA DEBE SER MENOR O IGUAL A LA ACTUAL'|| 'Ã' ||' ';
    ELS*/IF(v_totaldias=0) THEN
       v_salida:='0 Días'|| 'Ã' ||'0' || 'Ã' ||v_flagMayorEdad                          || 'Ã' ||cFecNacimiento;
    ELSE
       v_salida:=v_salida|| 'Ã' ||'0' || 'Ã' ||v_flagMayorEdad                          || 'Ã' ||cFecNacimiento;

    end if;

    OPEN curLista FOR SELECT v_salida FROM DUAL;

    RETURN curLista;

    --formato de salida textoEdad||'Ã'||codigo(0: OK -1: Error)||'Ã'||mensaje Error/Flag Mayor de Edad||'Ã'||textoFecNac

    /*EXCEPTION WHEN OTHERS THEN
     v_salida:=' '        || 'Ã' ||'-1'|| 'Ã' ||'EL FORMATO DE FECHAS ES INCORRECTO'          || 'Ã' ||' ';
     OPEN curLista FOR SELECT v_salida  FROM DUAL;
     RETURN curLista;*/

  END;

  FUNCTION CME_GRABAR_PACIENTE_HC(cCodGrupoCia_in IN CHAR,cCodLocal_in  IN CHAR,vUsuario IN VARCHAR2,vTipoDDM IN VARCHAR2,
                                  vNumHistoriaClinica IN VARCHAR2,vCodPaciente IN VARCHAR2,
                                  vApePatPac_in IN VARCHAR2,vApeMatPac_in IN VARCHAR2,vNombrePac_in IN VARCHAR2,
                                  vTipDocPac_in IN VARCHAR2,vNumDocPac_in IN VARCHAR2,
                                  vNumHCFisica_in VARCHAR2,vFecHCFisica_in IN VARCHAR2,--
                                  vTipAcomp_in VARCHAR2,vNombreAcom_in IN VARCHAR2,vTipDocAcom_in IN VARCHAR2,vNumDocAcom_in IN VARCHAR2,--
                                  vSexo_in IN VARCHAR2,vEstadoCivil_in VARCHAR2,vFecNac VARCHAR2,vDireccion_in IN VARCHAR2,
                                  vDepUbigeo_in IN VARCHAR2,vPvrUbigeo_in IN VARCHAR2,vDisUbigeo_in IN VARCHAR2,
                                  vDepLugNac_in IN VARCHAR2,vPvrLugNac_in IN VARCHAR2,vDisLugNac_in IN VARCHAR2,
                                  vDepLugPro_in IN VARCHAR2,vPvrLugPro_in IN VARCHAR2,vDisLugPro_in IN VARCHAR2,
                                  vGradoIns_in IN VARCHAR2,vOcupacion_in IN VARCHAR2,vCentroEL_in IN VARCHAR2,
                                  vGrupoSan_in IN VARCHAR2,vFactorRH_in IN VARCHAR2,--
                                  vCorreo_in IN VARCHAR2,vTelFijo_in IN VARCHAR2,vTelCel_in IN VARCHAR2) --31 parameters

 RETURN VARCHAR2 IS
 vSalida VARCHAR2(100);
 vCodPacGenerado CME_PACIENTE.COD_PACIENTE%type;
 vIND_HC char(1);
 vNumHCFinal       CME_HISTORIA_CLINICA.NRO_HC_ACTUAL%type;

 v_FecNac DATE;
 v_FecHC  DATE;
 v_cantDNI number;

 BEGIN

      /*BEGIN
       v_FecNac:=to_date(vFecNac,'dd/mm/yyyy');
       v_FecHC:=to_date(vFecHCFisica_in,'dd/mm/yyyy');
      EXCEPTION WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001,'El formato de fechas debe ser DD/MM/YYYY');
      END;

      IF(v_FecNac>TRUNC(SYSDATE) OR v_FecHC>TRUNC(SYSDATE)) THEN
             RAISE_APPLICATION_ERROR(-20002,'Las fechas debe ser menores a la actual');
      END IF;*/

      /*BEGIN
       v_FecNac:=to_date(vFecNac,'dd/mm/yyyy');
      EXCEPTION WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001,'El formato de fecha de nacimiento debe ser DD/MM/YYYY');
      END;

      IF(v_FecNac>TRUNC(SYSDATE)) THEN
            RAISE_APPLICATION_ERROR(-20001,'La fecha de nacimiento debe ser menor o igual a la actual');

      ELSIF(TO_NUMBER(TO_CHAR(v_FecNac,'YYYY'))<1900) THEN
            RAISE_APPLICATION_ERROR(-20001,'El año de nacimiento debe ser mayor o igual a 1900');
      END IF;


      BEGIN
       v_FecHC:=to_date(vFecHCFisica_in,'dd/mm/yyyy');
      EXCEPTION WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002,'El formato de fecha de la HC debe ser DD/MM/YYYY');
      END;

      IF(v_FecHC>TRUNC(SYSDATE)) THEN
            RAISE_APPLICATION_ERROR(-20002,'La fecha de la HC debe ser menor o igual a la actual');

      ELSIF(TO_NUMBER(TO_CHAR(v_FecHC,'YYYY'))<1900) THEN
            RAISE_APPLICATION_ERROR(-20001,'El año de la HC debe ser mayor o igual a 1900');
      END IF;*/

      v_FecNac:=to_date(CME_F_VALIDA_FECHA(vFecNac,'de nacimiento'),'dd/mm/yyyy');
      v_FecHC:=to_date(CME_F_VALIDA_FECHA(vFecHCFisica_in,'de la HC'),'dd/mm/yyyy');


      IF(vNumHCFisica_in IS NOT NULL AND vNumHCFisica_in<>'') THEN
          vIND_HC:='S';
      ELSE
          vIND_HC:='N';
      END IF;


      IF(vCodPaciente='0') THEN -- PACIENTE NUEVO

            select count(1) into v_cantDNI from cme_paciente
            where num_documento=vNumDocPac_in;

            IF (v_cantDNI<>0) THEN
                   RAISE_APPLICATION_ERROR(-20003,'El DNI ya se encuentra registrado');
            END IF;

        vCodPacGenerado :=  cCodLocal_in||Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,'081'),/*10*/7,'0','I' );

        vNumHCFinal:=PTOVENTA_CME_ADM.CME_GET_NUM_HIST_CLINICA(cCodGrupoCia_in,vCodPacGenerado,vNumDocPac_in,vNumDocAcom_in,TIPO_OPERACION_INSERT);


        INSERT INTO CME_PACIENTE(COD_GRUPO_CIA,COD_PACIENTE,APE_PAT_CLI,APE_MAT_CLI,NOM_CLI,COD_TIP_DOCUMENTO,NUM_DOCUMENTO,
                                 SEXO_CLI,ESTADO_CIVIL,FEC_NAC_CLI,DIR_CLI,
                                 GRADO_INSTRUCCION,OCUPACION,CENTRO_EDU_LAB,EMAIL,FONO_CLI,CELL_CLI,
                                 FEC_CREA_CLIENTE,USU_CREA_CLIENTE,
                                 DEP_UBIGEO,PRV_UBIGEO,DIS_UBIGEO,
                                 DEP_LUG_NAC,PRV_LUG_NAC,DIS_LUG_NAC,
                                 DEP_LUG_PRO,PRV_LUG_PRO,DIS_LUG_PRO)

        VALUES
        (cCodGrupoCia_in,vCodPacGenerado,vApePatPac_in,vApeMatPac_in,vNombrePac_in,vTipDocPac_in,vNumDocPac_in,
         vSexo_in,vEstadoCivil_in,to_date(vFecNac,'dd/mm/yyyy'),vDireccion_in,
         vGradoIns_in,vOcupacion_in,vCentroEL_in,vCorreo_in,vTelFijo_in,vTelCel_in,
         sysdate,vUsuario,
         vDepUbigeo_in,vPvrUbigeo_in,vDisUbigeo_in,
         vDepLugNac_in,vPvrLugNac_in,vDisLugNac_in,
         vDepLugPro_in,vPvrLugPro_in,vDisLugPro_in);

           INSERT INTO CME_HISTORIA_CLINICA(COD_GRUPO_CIA,COD_PACIENTE,NRO_HC_FISICA,FECHA_HC,GRUPO_SANG,FACTOR_RH,
                                            COD_TIP_ACOM,NOM_ACOM,COD_TIP_DOC_ACOM,NUM_DOC_ACOM,NRO_HC_ACTUAL,
                                            FEC_CREA,USU_CREA,IND_HC_FISICA)

           VALUES
           (cCodGrupoCia_in,vCodPacGenerado,vNumHCFisica_in,to_date(vFecHCFisica_in,'dd/mm/yyyy'),vGrupoSan_in,vFactorRH_in,
            vTipAcomp_in,vNombreAcom_in,vTipDocAcom_in,vNumDocAcom_in,/*vNumDocPac_in*/vNumHCFinal,
            sysdate,vUsuario,vIND_HC);


           Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,'081',vUsuario);

      ELSE
           vCodPacGenerado:=vCodPaciente; -- PACIENTE REGISTRADO

           vNumHCFinal:=PTOVENTA_CME_ADM.CME_GET_NUM_HIST_CLINICA(cCodGrupoCia_in,vCodPacGenerado,vNumDocPac_in,vNumDocAcom_in,TIPO_OPERACION_UPDATE);

           UPDATE CME_PACIENTE SET
           APE_PAT_CLI=vApePatPac_in,
           APE_MAT_CLI=vApeMatPac_in,
           NOM_CLI=vNombrePac_in,
           COD_TIP_DOCUMENTO=vTipDocPac_in,
           NUM_DOCUMENTO=vNumDocPac_in,
           SEXO_CLI=vSexo_in,
           ESTADO_CIVIL=vEstadoCivil_in,
           FEC_NAC_CLI=to_date(vFecNac,'dd/mm/yyyy'),
           DIR_CLI=vDireccion_in,
           GRADO_INSTRUCCION=vGradoIns_in,
           OCUPACION=vOcupacion_in,
           CENTRO_EDU_LAB=vCentroEL_in,
           EMAIL=vCorreo_in,
           FONO_CLI=vTelFijo_in,
           CELL_CLI=vTelCel_in,
           FEC_MOD_CLIENTE = SYSDATE,
           USU_MOD_CLIENTE = vUsuario,
           DEP_UBIGEO   = vDepUbigeo_in,
           PRV_UBIGEO   = vPvrUbigeo_in,
           DIS_UBIGEO   = vDisUbigeo_in,
           DEP_LUG_NAC  = vDepLugNac_in,
           PRV_LUG_NAC  = vPvrLugNac_in,
           DIS_LUG_NAC  = vDisLugNac_in,
           DEP_LUG_PRO  = vDepLugPro_in,
           PRV_LUG_PRO  = vPvrLugPro_in,
           DIS_LUG_PRO  = vDisLugPro_in
           WHERE COD_GRUPO_CIA=cCodGrupoCia_in
           AND   COD_PACIENTE= vCodPaciente;


           UPDATE CME_HISTORIA_CLINICA SET
           GRUPO_SANG    = vGrupoSan_in,
           FACTOR_RH     = vFactorRH_in,
           IND_HC_FISICA = vIND_HC,
           FECHA_HC      = to_date(vFecHCFisica_in,'dd/mm/yyyy'),
           NRO_HC_FISICA = vNumHCFisica_in,
           COD_TIP_ACOM  = vTipAcomp_in,
           NOM_ACOM      = vNombreAcom_in,
           COD_TIP_DOC_ACOM = vTipDocAcom_in,
           NUM_DOC_ACOM     = vNumDocAcom_in,
           NRO_HC_ACTUAL    = /*vNumHistoriaClinica*/DECODE(vNumHCFinal,'0',NRO_HC_ACTUAL,vNumHCFinal),
           FEC_MOD = SYSDATE,
           USU_MOD = vUsuario
           WHERE COD_GRUPO_CIA=cCodGrupoCia_in
           AND   COD_PACIENTE= vCodPaciente;


        END IF;


         --FARMA_UTILITY.ACEPTAR_TRANSACCION;



       return vCodPacGenerado;
 END;


 FUNCTION CME_INSERT_ATENCION_MEDICA(cCodGrupoCia_in IN CME_ATENCION_MEDICA.COD_GRUPO_CIA%type,
                                cCodCia_in      IN CME_ATENCION_MEDICA.COD_CIA%type,
                                cCodLocal_in    IN CME_ATENCION_MEDICA.COD_LOCAL%type,
                                cUsu_in         IN CME_ATENCION_MEDICA.USU_CREA%type,
                                cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                                cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE,
                                cNumPedVta_in   IN vta_comp_atencion_medica.Num_Ped_Vta%TYPE,
                                cCodLocalVta_in IN vta_comp_atencion_medica.Cod_Local%TYPE,
                                vCodPaciente    IN CME_ATENCION_MEDICA.COD_PACIENTE%type,
                                vCodMedico      IN CME_ATENCION_MEDICA.COD_MEDICO%type,
                                vEstado         IN CME_ATENCION_MEDICA.ESTADO%type,
                                vCodConsulta    IN CME_ATENCION_MEDICA.COD_MAES_DET%type,
                                vCodTipAten     IN Varchar2,
                                vIdConsultorio_in in varchar2,
                                vIdBus_in in varchar2,
                                vOrdenMedica_in in varchar2 default 'N'
                                )
  RETURN VARCHAR2 IS
  vSalida VARCHAR2(100);
  vNumAtenMed CME_ATENCION_MEDICA.NUM_ATEN_MED%type;
  vExisteAsignacion number(8);
  vIdConsultorio_origen cc_bus.id_consultorio%type;
  vIdBus_origen cc_bus.id_bus%type;
  
  vPedidoFueAnulado number(8);
  -- dubilluz 20190903
  vValidaIngreso_in char(1) ;
  BEGIN
    
  
   if nvl(trim(vOrdenMedica_in),'N') != 'N' then
     -- si es una orden medico 
     -- se validara si esta fue asignada a una especialidad y consultorio
     
     select count(1)
     into   vExisteAsignacion
     from   vta_orden_venta v
     where  v.id_bus is not null
     and    v.id_consultorio is not null
     and    v.id_bus != 0
     and    v.id_consultorio != 0
     and    v.cod_grupo_cia = cCodGrupoCia_in
     and    v.cod_local = cCodLocal_in
     and    v.num_ped_vta= cNumPedVta_in
     and    v.num_orden_vta = nvl(trim(vOrdenMedica_in),'N');
     
     if vExisteAsignacion > 0 then
     -- se validara su ingreso de atencion a una que no corresponde
     select v.id_consultorio,v.id_bus
     into   vIdConsultorio_origen,vIdBus_origen
     from   vta_orden_venta v
     where  v.id_bus is not null
     and    v.id_consultorio is not null
     and    v.id_bus != 0
     and    v.id_consultorio != 0
     and    v.cod_grupo_cia = cCodGrupoCia_in
     and    v.cod_local = cCodLocal_in
     and    v.num_ped_vta= cNumPedVta_in
     and    v.num_orden_vta = nvl(trim(vOrdenMedica_in),'N');     
     
     
       if (vIdConsultorio_origen != to_number(trim(vIdConsultorio_in) ,'999990')
          or
          vIdBus_origen != to_number(trim(vIdBus_in) ,'999990'))
       then  
         -- dubilluz 
         -- dubilluz 20190903
         -- se verifica si la especialidad obliga la validacion
         begin
         select nvl(v.ind_valida_ingreso,'S')
         into  vValidaIngreso_in
         from  cc_consultorio   v
         where  v.id_consultorio = vIdConsultorio_origen;
         exception
           when others then
             vValidaIngreso_in := 'S';
           end;
          
         if vValidaIngreso_in = 'S' then
          RAISE_APPLICATION_ERROR(-20019,'Verifique!!,No se puede asignar al consultorio seleccionado');
         else
            if vIdConsultorio_origen != to_number(trim(vIdConsultorio_in) ,'999990') then
               RAISE_APPLICATION_ERROR(-20019,'Verifique!!,la especialidad no corresponde al ingreso seleccionado');
            end if;
         end if;
          --null;
       end if;
      
     end if;
   end if;
   
   -- verifica si el  pedido fue anulado
   select count(1)
   into   vPedidoFueAnulado
   from   vta_pedido_vta_cab c
   where  c.cod_grupo_cia = cCodGrupoCia_in
   and    c.cod_local = cCodLocal_in
   and    c.est_ped_vta = 'C'
   and    c.num_ped_vta_origen = cNumPedVta_in;
   
  if vPedidoFueAnulado > 0 then
    RAISE_APPLICATION_ERROR(-20020,'Verifique!!,El pedido se encuentra anulado');
  end if;

      vNumAtenMed :=  Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,'082'),10,'0','I' );

      INSERT INTO CME_ATENCION_MEDICA(COD_GRUPO_CIA,COD_CIA,COD_LOCAL,NUM_ATEN_MED,COD_PACIENTE,
                                      NUM_PED_VTA,COD_LOCAL_VTA,COD_MEDICO,
                                      COD_MAES_DET,ESTADO,FEC_CREA,USU_CREA,COD_TIPO_ATENCION,
                                      id_consultorio,
                                      id_bus,
                                      num_orden_vta)
      VALUES(cCodGrupoCia_in,cCodCia_in,cCodLocal_in,vNumAtenMed,vCodPaciente,
             cNumPedVta_in,cCodLocalVta_in,vCodMedico,vCodConsulta,vEstado,SYSDATE,
             cUsu_in,vCodTipAten,
             to_number(trim(vIdConsultorio_in) ,'999990'),
             to_number(trim(vIdBus_in) ,'999990'),
             nvl(trim(vOrdenMedica_in),'N')
             );


      Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,'082',cUsu_in);


      CME_UPDATE_COMPROBANTE(cCodGrupoCia_in=>cCodGrupoCia_in,
                                              cNumPedVta_in=>cNumPedVta_in,
                                              cCodLocalVta_in=>cCodLocalVta_in,
                                              cUsu_in=>cUsu_in,
                                              cEstado_in=>ESTADO_COMPROBANTE_PROCESADO,
                                              vNumAtencion_in=>vNumAtenMed);



       --FARMA_UTILITY.ACEPTAR_TRANSACCION;

       return vNumAtenMed;

  END;


  FUNCTION CME_INSERT_TRIAJE   (cCodGrupoCia_in IN CME_ATENCION_MEDICA_TRI.COD_GRUPO_CIA%type,
                                cCodCia_in      IN CME_ATENCION_MEDICA_TRI.COD_CIA%type,
                                cCodLocal_in    IN CME_ATENCION_MEDICA_TRI.COD_LOCAL%type,
                                cUsu_in         IN CME_ATENCION_MEDICA.USU_CREA%type,
                                cNumAtenMed_in  IN CME_ATENCION_MEDICA_TRI.NUM_ATEN_MED%type,
                                nPA1_in         IN CME_ATENCION_MEDICA_TRI.PA_1%type,
                                nPA2_in         IN CME_ATENCION_MEDICA_TRI.PA_2%type,
                                nFR_in          IN CME_ATENCION_MEDICA_TRI.FR%type,
                                nFC_in          IN CME_ATENCION_MEDICA_TRI.FC%type,
                                nTemp_in        IN VARCHAR2,--IN CME_ATENCION_MEDICA_TRI.TEMP%type,
                                nPeso_in        IN VARCHAR2,--CME_ATENCION_MEDICA_TRI.PESO%type,
                                nTalla_in       IN CME_ATENCION_MEDICA_TRI.TALLA%type,
                                nSaturacion_in  IN CME_ATENCION_MEDICA_TRI.SATURACION_OXIGENO%type
                                )
  RETURN VARCHAR2 IS
  BEGIN
      -- dubilluz 20190214
      INSERT INTO CME_ATENCION_MEDICA_TRI(COD_GRUPO_CIA,COD_CIA,COD_LOCAL,NUM_ATEN_MED,
                                          PA_1,PA_2,FR,FC,TEMP,PESO,TALLA,FEC_CREA,USU_CREA,
                                          saturacion_oxigeno
                                          )

      VALUES
      (cCodGrupoCia_in,cCodCia_in,cCodLocal_in,cNumAtenMed_in,
       nPA1_in,nPA2_in,nFR_in,nFC_in,
       --nTemp_in
       TO_NUMBER(nTemp_in, '999.99'),
       TO_NUMBER(nPeso_in, '999.99'),
       nTalla_in,sysdate,cUsu_in,
       to_number(nSaturacion_in,'9999.00')
       );


        CENTRO_MEDICO.F_UPDATE_SOLICITUD_ATENCION(cCodGrupoCia_in=>cCodGrupoCia_in,
                                                  cCodLocal_in=>cCodLocal_in,
                                                  cNumAtencion_in=>cNumAtenMed_in,
                                                  cCodEstadoNew_in=>ESTADO_AMEDICA_PEND_CONSULTA,
                                                  cUsuario_in=>cUsu_in);


       --FARMA_UTILITY.ACEPTAR_TRANSACCION;

       return cNumAtenMed_in;

  END;

PROCEDURE CME_UPDATE_COMPROBANTE(cCodGrupoCia_in IN CME_ATENCION_MEDICA.COD_GRUPO_CIA%type,
                                 cNumPedVta_in   IN vta_comp_atencion_medica.Num_Ped_Vta%TYPE,
                                 cCodLocalVta_in IN vta_comp_atencion_medica.Cod_Local%TYPE,
                                 cUsu_in         IN CME_ATENCION_MEDICA.USU_CREA%type,
                                 cEstado_in in char,
                                 vNumAtencion_in in cme_atencion_medica.num_aten_med%TYPE)

IS
vSec number;
vCodCia_in cme_atencion_medica.cod_cia%TYPE;
vCodLocal_in cme_atencion_medica.cod_local%TYPE;
--vNumAtencion_in cme_atencion_medica.num_aten_med%TYPE;
BEGIN


            UPDATE VTA_COMP_ATENCION_MEDICA
            SET ESTADO=cEstado_in,
                FEC_MOD=SYSDATE,
                USU_MOD=cUsu_in
            WHERE COD_GRUPO_CIA=cCodGrupoCia_in
            AND   COD_LOCAL    =cCodLocalVta_in
            AND   NUM_PED_VTA  =cNumPedVta_in;

            select max(v.sec_hist_med)+1
            into   vSec
            from   HIST_COMP_ATENCION_MEDICA v
            WHERE COD_GRUPO_CIA=cCodGrupoCia_in
            AND   COD_LOCAL    =cCodLocalVta_in
            AND   NUM_PED_VTA  =cNumPedVta_in;

            select a.cod_cia,a.cod_local
            into   vCodCia_in,vCodLocal_in
            from   cme_atencion_medica a
            where  a.cod_local_vta = cCodLocalVta_in
            and    a.num_ped_vta = cNumPedVta_in
            and    a.num_aten_med =  vNumAtencion_in;

            insert into HIST_COMP_ATENCION_MEDICA
            (cod_grupo_cia, cod_local, num_ped_vta,
            sec_hist_med, fec_hist_med,
            cod_cia, cod_local_cme,num_aten_med,
            estado, fec_crea, usu_crea)
            values
            (cCodGrupoCia_in,cCodLocalVta_in,cNumPedVta_in,
            vSec,sysdate,
            vCodCia_in,vCodLocal_in,vNumAtencion_in,
            cEstado_in,SYSDATE,cUsu_in
            );


            /*WHERE TIP_COMP_PAGO=cTipComPago_in
            AND NUM_COMP_PAGO=cNumComPago_in;*/

END;


FUNCTION CME_GET_NUM_HIST_CLINICA(cCodGrupoCia_in    IN CME_HISTORIA_CLINICA.COD_GRUPO_CIA%type,
                                  vCodPaciente_in    IN CME_HISTORIA_CLINICA.COD_PACIENTE%TYPE,
                                  vNumDocPac_in      IN CME_PACIENTE.NUM_DOCUMENTO%type /*default ''*/,
                                  vNumDocAcom_in     IN CME_HISTORIA_CLINICA.NUM_DOC_ACOM%type /*default ''*/,
                                  cTipoOperacion_in  IN CHAR)
RETURN VARCHAR2
IS
vNumHCFinal       CME_HISTORIA_CLINICA.NRO_HC_ACTUAL%type;
nCantHistRel      number;
BEGIN
     vNumHCFinal:=vCodPaciente_in;

     --Se cambio la lógica de creación de número de historia clinica
     /*IF(cTipoOperacion_in=TIPO_OPERACION_INSERT) THEN
        IF(vNumDocPac_in IS NOT NULL AND LENGTH(TRIM(vNumDocPac_in))<>0) THEN
               vNumHCFinal:=vNumDocPac_in;
        ELSE
           IF(vNumDocAcom_in IS NOT NULL AND LENGTH(TRIM(vNumDocAcom_in))<>0) THEN

              SELECT COUNT(1)+1 INTO nCantHistRel FROM CME_HISTORIA_CLINICA
              WHERE SUBSTR(NRO_HC_ACTUAL,1,LENGTH(NRO_HC_ACTUAL)-3)=TRIM(vNumDocAcom_in);
              --WHERE TRIM(NRO_HC_ACTUAL)=TRIM(vNumDocAcom_in);

              vNumHCFinal:=vNumDocAcom_in||Farma_Utility.COMPLETAR_CON_SIMBOLO(nCantHistRel,3,'0','I' );

           ELSE

              vNumHCFinal:=vCodPaciente_in;

           END IF;

        END IF;

     ELSIF(cTipoOperacion_in=TIPO_OPERACION_UPDATE) THEN
           IF(vNumDocPac_in IS NOT NULL AND LENGTH(TRIM(vNumDocPac_in))<>0) THEN
              vNumHCFinal:=vNumDocPac_in;
           ELSE
              --SELECT NRO_HC_ACTUAL INTO vNumHCFinal FROM CME_HISTORIA_CLINICA
              --WHERE COD_GRUPO_CIA=cCodGrupoCia_in
              --AND   COD_PACIENTE = vCodPaciente_in;
              vNumHCFinal:=0;--Debe tener su misma historia
           END IF;

     END IF;*/


     RETURN vNumHCFinal;

EXCEPTION WHEN OTHERS THEN
          vNumHCFinal:=vCodPaciente_in;
          RETURN vNumHCFinal;
END;

FUNCTION CME_ANULAR_ATENCION_MEDICA(cCodGrupoCia_in    IN CME_ATENCION_MEDICA.COD_GRUPO_CIA%TYPE,
                                    cCodCia_in        IN CME_ATENCION_MEDICA.COD_CIA%type,
                                    cCodLocal_in      IN CME_ATENCION_MEDICA.COD_LOCAL%TYPE,
                                    cNumAtencion_in   IN CME_ATENCION_MEDICA.NUM_ATEN_MED%TYPE,
                                    cUsuario_in       IN CME_ATENCION_MEDICA.USU_MOD%TYPE,
                                    cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                                    cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE)

RETURN VARCHAR2
IS
vSalida VARCHAR2(20):='S';
cNumPedVta     vta_comp_atencion_medica.Num_Ped_Vta%TYPE;
cCodLocalVta   vta_comp_atencion_medica.Cod_Local%TYPE;
BEGIN

        UPDATE CME_ATENCION_MEDICA A
        SET A.IND_ANULADO = INDICAR_ANULADO_SI,
            A.FEC_MOD = SYSDATE,
            A.USU_MOD = cUsuario_in
        WHERE A.COD_GRUPO_CIA = cCodGrupoCia_in
        AND A.COD_CIA = cCodCia_in
        AND A.COD_LOCAL = cCodLocal_in
        AND A.NUM_ATEN_MED = cNumAtencion_in;


        SELECT A.NUM_PED_VTA,A.COD_LOCAL_VTA INTO cNumPedVta,cCodLocalVta FROM CME_ATENCION_MEDICA A
        WHERE A.COD_GRUPO_CIA=cCodGrupoCia_in
        AND A.COD_CIA=cCodCia_in
        AND A.COD_LOCAL=cCodLocal_in
        AND A.NUM_ATEN_MED=cNumAtencion_in;




        CME_UPDATE_COMPROBANTE(cCodGrupoCia_in=>cCodGrupoCia_in,
                              cNumPedVta_in=>cNumPedVta,
                              cCodLocalVta_in=>cCodLocalVta,
                              cUsu_in=>cUsuario_in,
                              cEstado_in=>ESTADO_COMPROBANTE_SINPROCESAR,
                              vNumAtencion_in=>cNumAtencion_in);



       --FARMA_UTILITY.ACEPTAR_TRANSACCION;

     return vSalida;

END;


  FUNCTION CME_VALIDA_COMPROBANTE_PAGO(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                                       cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE)
  RETURN FARMACURSOR IS
  curLista FARMACURSOR;

    cursor c_comprobante is
    select a.cod_grupo_cia,a.cod_local,a.num_ped_vta,m.cod_paciente
    from   vta_comp_atencion_medica a,
           vta_medico_x_pedido m
    where  a.cod_grupo_cia=cCodGrupoCia_in--
    AND   a.cod_local=cCodLocal_in--
    AND   a.tip_comp_pago=cTipComPago_in
    AND   a.num_comp_pago=cNumComPago_in
    and   a.cod_grupo_cia = m.cod_grupo_cia
    and   a.cod_local = m.cod_local
    and   a.num_ped_vta = m.num_ped_vta;

    l_regComprobante c_comprobante%ROWTYPE;

    cursor c_histEstadoComp(p_cod_grupo_cia varchar2,p_cod_local varchar2,p_num_ped_vta varchar2) is
    select * from (
        select estado,rank() over (order by fec_crea desc) orden,num_ped_vta,fec_crea
        from HIST_COMP_ATENCION_MEDICA
        where cod_grupo_cia=p_cod_grupo_cia--'001'
        and cod_local=p_cod_local--'125'
        and num_ped_vta=p_num_ped_vta--'C001134755'
    ) T
    WHERE ORDEN=1;

    l_regHistEstadoComp c_histEstadoComp%ROWTYPE;

  BEGIN

         OPEN c_comprobante;
         FETCH c_comprobante into l_regComprobante;

         IF (l_regComprobante.num_ped_vta IS NULL) THEN
         RAISE_APPLICATION_ERROR(-20001,'El comprobante de pago ingresado no existe en el sistema.');

         ELSE
             OPEN c_histEstadoComp(l_regComprobante.Cod_Grupo_Cia,l_regComprobante.Cod_Local,l_regComprobante.Num_Ped_Vta);
             FETCH c_histEstadoComp into l_regHistEstadoComp;

             IF(l_regHistEstadoComp.ORDEN IS NULL) THEN
                RAISE_APPLICATION_ERROR(-20002,'Los estados del comprobante de pago ingresado no existen en el sistema.');
             ELSIF(l_regHistEstadoComp.ESTADO<>ESTADO_COMPROBANTE_SINPROCESAR) THEN
                IF(l_regHistEstadoComp.ESTADO=ESTADO_COMPROBANTE_INACTIVO) THEN
                   RAISE_APPLICATION_ERROR(-20003,'El comprobante esta anulado.');
                else
                   RAISE_APPLICATION_ERROR(-20004,'El comprobante de pago ya fue utilizado.');
                end if;
             ELSE
                OPEN curLista FOR
                Select '0'|| 'Ã' ||
                       l_regComprobante.Num_Ped_Vta|| 'Ã' ||
                       l_regComprobante.Cod_Local || 'Ã' ||
                       l_regComprobante.Cod_Paciente

                 from dual;
             END IF;

         END IF;

         /*IF(l_regComprobante.num_ped_vta is null) THEN -- No hay recibo
         --OPEN curLista FOR Select '1' from dual;

         ELSIF(l_regComprobante.estado<>'A') THEN -- Recibo usado
         --OPEN curLista FOR Select '2' from dual;
         RAISE_APPLICATION_ERROR(-20001,'El comprobante de pago ya fue utilizado.');
         ELSE
         OPEN curLista FOR Select '0'|| 'Ã' ||l_regComprobante.Num_Ped_Vta|| 'Ã' ||l_regComprobante.Cod_Local from dual;
         END IF;*/

     RETURN curLista;
  END;


FUNCTION CME_F_VARC_DATO_COMP(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              cTipComPago_in  IN vta_comp_atencion_medica.Tip_Comp_Pago%TYPE,
                              cNumComPago_in  IN vta_comp_atencion_medica.Num_Comp_Pago%TYPE)
  RETURN VARCHAR2 IS
  curLista FARMACURSOR;
  vCadena varchar2(1000);
  BEGIN

    BEGIN
    select a.cod_local||'@'||a.num_ped_vta
    into vCadena
    from   vta_comp_atencion_medica a
    where  a.cod_grupo_cia=cCodGrupoCia_in
    AND    a.tip_comp_pago=cTipComPago_in
    AND    a.num_comp_pago=cNumComPago_in;
    EXCEPTION
    WHEN OTHERS THEN
      vCadena := 'N';
    END;

    RETURN vCadena;
  END;


FUNCTION CME_F_VARC_DATO_COMP_MEDICA(cCodGrupoCia_in IN CHAR,
                                    cCodLocal_in     IN CHAR,
                                    cNumPedAtencionMedica_in     IN CHAR)
  RETURN VARCHAR2 IS
  curLista FARMACURSOR;
  vCadena varchar2(1000);
  BEGIN

    BEGIN
    select a.cod_local_vta||'@'||a.num_ped_vta
    into vCadena
    from   cme_atencion_medica a
    where  a.cod_grupo_cia=cCodGrupoCia_in
    AND    a.cod_local =cCodLocal_in
    AND    a.num_aten_med=cNumPedAtencionMedica_in;
    EXCEPTION
    WHEN OTHERS THEN
      vCadena := 'N';
    END;

    RETURN vCadena;
  END;


FUNCTION CME_F_CUR_DATOS_EXISTE_DNI(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,cCodLocal_in IN CHAR,cDNI_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCamp FarmaCursor;
    vDNI varchar2(20);
    nCant number;
  BEGIN

  --ptoventa_fid_reniec.p_genera_tarjeta_dni(cCodGrupoCia_in,cCodLocal_in, trim(cDNI_in));

    OPEN curCamp FOR

        SELECT
          A.DNI_CLI                                    || 'Ã' ||
          nvl(A.NOM_CLI,' ')                           || 'Ã' ||
          nvl(A.APE_PAT_CLI,' ')                       || 'Ã' ||
          nvl(A.APE_MAT_CLI,' ')                       || 'Ã' ||
          nvl(A.SEXO_CLI,' ')                          || 'Ã' ||
          nvl(TO_CHAR(A.FEC_NAC_CLI,'dd/MM/yyyy'),' ') || 'Ã' ||
          nvl(A.DIR_CLI,' ')                           || 'Ã' ||
          nvl(''||A.FONO_CLI,' ')                      || 'Ã' ||
          nvl(''||A.CELL_CLI,' ')                      || 'Ã' ||
          nvl(''||A.Email,' ')
        FROM PBL_CLIENTE A
        WHERE A.DNI_CLI = cDNI_in;
    --end if;

    RETURN curCamp;

  END;

FUNCTION CME_F_VALIDA_FECHA(vFecha_in IN VARCHAR2,vMensaje_in IN VARCHAR2)
RETURN VARCHAR2
IS
v_FecNac date;
v_salida varchar2(1000):='';

BEGIN
      BEGIN
       v_FecNac:=to_date(vFecha_in,'dd/mm/yyyy');
      EXCEPTION WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001,'El formato de fecha '||vMensaje_in||' debe ser DD/MM/YYYY');
      END;

      IF(v_FecNac>TRUNC(SYSDATE)) THEN
            RAISE_APPLICATION_ERROR(-20001,'La fecha '||vMensaje_in||' debe ser menor o igual a la actual');

      ELSIF(TO_NUMBER(TO_CHAR(v_FecNac,'YYYY'))<1900) THEN
            RAISE_APPLICATION_ERROR(-20001,'El año '||vMensaje_in||' debe ser mayor o igual a 1900');
      END IF;

      return v_salida||to_char(v_FecNac,'dd/mm/yyyy');

END;


FUNCTION F_LISTA_CONSULTAS
  RETURN FARMACURSOR IS
    vCur FARMACURSOR;
  BEGIN
    OPEN vCur FOR
    SELECT a.cod_maes_det COD_CONSULTA,a.descripcion DESC_CONSULTA from maestro_detalle a,maestro b
    where a.cod_maestro=b.cod_maestro
    and b.cod_maestro=35
    order by to_number(a.VALOR1) asc;

    RETURN vCur;
END;


FUNCTION CME_GET_CONSULTA(cCodBusqueda_in IN CHAR)
RETURN FarmaCursor
IS
curGral FarmaCursor;
BEGIN
    OPEN curGral FOR
        SELECT a.cod_maes_det || 'Ã' || a.descripcion from maestro_detalle a,maestro b
        where a.cod_maestro=b.cod_maestro
        and b.cod_maestro=35
        and a.cod_maes_det=cCodBusqueda_in;

     RETURN curGral;
END;

FUNCTION CME_GET_DESC_EST_CIVIL(cCodGrupoCia_in IN CHAR, cCod_Est_Civil IN CHAR)
  RETURN char
  IS
  desc_est_civil VARCHAR(20);
  BEGIN
      SELECT trim(a.descripcion) INTO desc_est_civil
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND      b.cod_maestro=26
      AND      a.cod_maes_det = cCod_Est_Civil;
      RETURN desc_est_civil;
END;

FUNCTION CME_GET_DESC_GR_INS(cCodGrupoCia_in IN CHAR, cCod_Gr_ins IN CHAR)
  RETURN char
  IS
  desc_grado_in VARCHAR(20);
  BEGIN
      SELECT trim(a.descripcion) INTO desc_grado_in
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND      b.cod_maestro=27
      AND      a.cod_maes_det = cCod_Gr_ins;
      RETURN desc_grado_in;
END;

FUNCTION F_OBTENER_NRO_ATENCION_MED(cCodGrupoCia_in IN CHAR,cCodCia_in IN CHAR,
                                    cCodLocal_in IN CHAR,codPaciente IN CHAR)
  RETURN char
  IS
  num_atencion VARCHAR(20);
  BEGIN
      select MAX(M.NUM_ATEN_MED) INTO num_atencion from cme_paciente p
      inner join cme_atencion_medica m ON p.cod_paciente = m.cod_paciente
      where m.cod_grupo_cia = cCodGrupoCia_in and
            m.cod_cia = cCodCia_in and
            m.cod_local = cCodLocal_in and
            p.cod_paciente = codPaciente;
      RETURN num_atencion;
END;

FUNCTION CME_GET_DESC_GR_SANGUINEO(cCodGrupoCia_in IN CHAR, cCod_Gr_sang IN CHAR)
  RETURN char
  IS
  desc_grupo_in VARCHAR(20);
  BEGIN
      SELECT trim(a.descripcion) INTO desc_grupo_in
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND      b.cod_maestro=29
      AND      a.cod_maes_det = cCod_Gr_sang;
      RETURN desc_grupo_in;
END;

FUNCTION CME_GET_DESC_FACTOR_RH(cCodGrupoCia_in IN CHAR, cCod_factor IN CHAR)
  RETURN char
  IS
  desc_factor VARCHAR(20);
  BEGIN
      SELECT trim(a.descripcion) INTO desc_factor
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND      b.cod_maestro=28
      AND      a.cod_maes_det = cCod_factor;
      RETURN desc_factor;
END;

FUNCTION CME_GET_DESC_TIPO_INF(cCodGrupoCia_in IN CHAR, cCod_inf IN CHAR)
  RETURN char
  IS
  desc_info VARCHAR(20);
  BEGIN
      SELECT trim(a.descripcion) INTO desc_info
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND      b.cod_maestro=40
      AND      a.valor1 = cCod_inf;
      RETURN desc_info;
END;

FUNCTION CME_GET_DESC_FUN_BIO(cCodGrupoCia_in IN CHAR, cCod_funcion IN CHAR)
  RETURN char
  IS
  desc_funcion VARCHAR(20);
  BEGIN
      SELECT trim(a.descripcion) INTO desc_funcion
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND      b.cod_maestro=39
      AND      a.valor1 = cCod_funcion;
      RETURN desc_funcion;
END;

FUNCTION CME_GET_DESC_ESTADO_GRAL(cCodGrupoCia_in IN CHAR, cCod_estado IN CHAR)
  RETURN char
  IS
  desc_estado VARCHAR(20);
  BEGIN
      SELECT trim(a.descripcion) INTO desc_estado
      FROM maestro_detalle a,maestro b
      WHERE    a.cod_maestro=b.cod_maestro
      AND      b.cod_maestro=38
      AND      a.valor1 = cCod_estado;
      RETURN desc_estado;
END;

FUNCTION CME_GET_DESC_LUGAR(cCodGrupoCia_in IN CHAR,
                            cCod_Dep        IN CHAR,
                            cCod_PPro       IN CHAR,
                            cCod_Dis        IN CHAR)
  RETURN char
  IS
  desc_lugar VARCHAR(20);
  BEGIN
      SELECT U.NODEP || '-' || U.NOPRV || '-' || U.NODIS INTO desc_lugar
      FROM UBIGEO U
      WHERE
      U.UBDEP = cCod_Dep AND
      U.UBPRV = cCod_PPro AND
      U.UBDIS = cCod_Dis;
      RETURN desc_lugar;
END;

  FUNCTION F_LISTA_TRAZABILIDAD(cCodGrupoCia_in IN CHAR,vFecIni   IN  VARCHAR2,
                                  vFecFin   IN  VARCHAR2,vCodMed IN VARCHAR2 DEFAULT NULL,
                                  vFilView IN CHAR DEFAULT NULL)
  RETURN FARMACURSOR IS
    curLista FARMACURSOR;
  BEGIN
    OPEN curLista FOR
     --Dflores: 18/08/2019
     SELECT TO_CHAR(A.FEC_CREA, 'DD/MM/YYYY') || 'Ã' || --HORA
            TO_CHAR(A.FEC_CREA, 'HH24:MI:SS') || 'Ã' || --HORA
             --nvl(P.NUM_DOCUMENTO,' ') || 'Ã' || -- NRO DOCUMENTO
             nvl(H.NRO_HC_ACTUAL,' ') || 'Ã' || -- NRO DOCUMENTO
             nvl(P.APE_PAT_CLI || ' ' || P.APE_MAT_CLI || ' ' || P.NOM_CLI,' ') || 'Ã' ||--NOMBRE,
             nvl(trim(to_char(FLOOR(MONTHS_BETWEEN(SYSDATE , P.FEC_NAC_CLI) /12),'99990')),' ') || 'Ã' ||--EDAD,
             nvl(DECODE(A.ESTADO, 'T', 'PEND.TRIAJE', 'P', 'PEND.ATENCION', 'C', 'EN CONSULTA', 'A', 'ATENDIDO', 'G', 'GRABADO TEMPORAL','ERROR'),' ') || 'Ã' ||--ESTADO,
             nvl(D.DESCRIPCION,' ') || 'Ã' ||--ESPECIALIDAD,
             nvl(TRIM(M.DES_APE_MEDICO),' ')||' '|| TRIM(M.DES_NOM_MEDICO) || 'Ã' ||--MEDICO,
             nvl(P.COD_PACIENTE,' ') || 'Ã' ||-- COD_PACIENTE,
             nvl(A.ESTADO,' ') || 'Ã' ||--COD_ESTADO,
             nvl(DECODE(TRIM(H.NRO_HC_FISICA), NULL, 'NO', 'SI'),' ')|| 'Ã' ||
             nvl(H.NRO_HC_FISICA, ' ') || 'Ã' ||--NRO_HC_FISICA
             nvl(A.NUM_ATEN_MED,' ')|| 'Ã' ||
             nvl(DECODE(A.IND_ANULADO,'N','ACTIVO','S','ANULADO'),' ') || 'Ã' ||
             A.COD_GRUPO_CIA || 'Ã' ||
             A.COD_CIA || 'Ã' ||
             A.COD_LOCAL || 'Ã' ||
             A.NUM_ATEN_MED || 'Ã' ||
             A.COD_PACIENTE || 'Ã' ||
             A.COD_MEDICO || 'Ã' ||
             C.ID_CONSULTORIO || 'Ã' ||
             C.DESCRIPCION
      FROM CME_ATENCION_MEDICA A,
           CME_PACIENTE P,
           CME_HISTORIA_CLINICA H,
           MAESTRO_DETALLE D,
           MAE_MEDICO M,
           CC_CONSULTORIO C
      WHERE A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND A.COD_PACIENTE = P.COD_PACIENTE
      AND A.COD_MAES_DET = D.COD_MAES_DET
      AND A.COD_MEDICO = M.COD_MEDICO
      AND A.COD_GRUPO_CIA = H.COD_GRUPO_CIA
      AND A.COD_PACIENTE = H.COD_PACIENTE
      AND A.ID_CONSULTORIO = C.ID_CONSULTORIO
      --Dflores:29/08/19 VT = VerTodos
      AND (M.COD_MEDICO = vCodMed OR 'VT' = vFilView)
      and A.IND_ANULADO = 'N'
      AND TRUNC(A.FEC_CREA)>=TO_DATE(vFecIni,'DD/MM/YYYY')  AND TRUNC(A.FEC_CREA)<=TO_DATE(vFecFin,'DD/MM/YYYY')
      --Dflores: Se agrega para que no se muestren Ventas Anuladas
      /*AND NOT EXISTS(
          SELECT * FROM VTA_PEDIDO_VTA_CAB VT WHERE VT.NUM_PED_VTA_ORIGEN = A.NUM_PED_VTA
      )*/
      ORDER BY A.NUM_ATEN_MED DESC;      
      
    RETURN curLista;
  END;

  FUNCTION F_LISTA_LIBERADOS(cCodGrupoCia_in IN CHAR,vFecIni   IN  VARCHAR2,
                                  vFecFin   IN  VARCHAR2/*,vCodMed IN VARCHAR2,
                                  vFilView IN CHAR*/)
  RETURN FARMACURSOR IS
    curLista FARMACURSOR;
  BEGIN
    OPEN curLista FOR
     --Dflores: 18/08/2019
     SELECT 
             nvl(PU.LOGIN_USU,' ')  || 'Ã' ||
             TO_CHAR(A.FECH_LIBERA, 'DD/MM/YYYY HH24:MI:SS') || 'Ã' || --HORA
             nvl(A.GLOSA,' ')  || 'Ã' ||
             TO_CHAR(A.FEC_CREA, 'DD/MM/YYYY') || 'Ã' || --HORA
             TO_CHAR(A.FEC_CREA, 'HH24:MI:SS') || 'Ã' || --HORA
             --nvl(P.NUM_DOCUMENTO,' ') || 'Ã' || -- NRO DOCUMENTO
             nvl(H.NRO_HC_ACTUAL,' ') || 'Ã' || -- NRO DOCUMENTO
             nvl(P.APE_PAT_CLI || ' ' || P.APE_MAT_CLI || ' ' || P.NOM_CLI,' ') || 'Ã' ||--NOMBRE,
             nvl(trim(to_char(FLOOR(MONTHS_BETWEEN(SYSDATE , P.FEC_NAC_CLI) /12),'99990')),' ') || 'Ã' ||--EDAD,
             nvl(DECODE(A.ESTADO, 'T', 'PEND.TRIAJE', 'P', 'PEND.ATENCION', 'C', 'EN CONSULTA', 'A', 'ATENDIDO', 'G', 'GRABADO TEMPORAL','ERROR'),' ') || 'Ã' ||--ESTADO,
             nvl(D.DESCRIPCION,' ') || 'Ã' ||--ESPECIALIDAD,
             nvl(TRIM(M.DES_APE_MEDICO),' ')||' '|| TRIM(M.DES_NOM_MEDICO) || 'Ã' ||--MEDICO,
             nvl(P.COD_PACIENTE,' ') || 'Ã' ||-- COD_PACIENTE,
             nvl(A.ESTADO,' ') || 'Ã' ||--COD_ESTADO,
             nvl(DECODE(TRIM(H.NRO_HC_FISICA), NULL, 'NO', 'SI'),' ')|| 'Ã' ||
             nvl(H.NRO_HC_FISICA, ' ') || 'Ã' ||--NRO_HC_FISICA
             nvl(A.NUM_ATEN_MED,' ')|| 'Ã' ||
             nvl(DECODE(A.IND_ANULADO,'N','ACTIVO','S','ANULADO'),' ') || 'Ã' ||
             A.COD_GRUPO_CIA || 'Ã' ||
             A.COD_CIA || 'Ã' ||
             A.COD_LOCAL || 'Ã' ||
             A.NUM_ATEN_MED || 'Ã' ||
             A.COD_PACIENTE || 'Ã' ||
             A.COD_MEDICO || 'Ã' ||
             C.ID_CONSULTORIO || 'Ã' ||
             C.DESCRIPCION
      FROM lib_cme_atencion_medica A,
           CME_PACIENTE P,
           CME_HISTORIA_CLINICA H,
           MAESTRO_DETALLE D,
           MAE_MEDICO M,
           CC_CONSULTORIO C,
           PBL_USU_LOCAL PU
      WHERE A.COD_GRUPO_CIA = P.COD_GRUPO_CIA
      AND A.COD_PACIENTE = P.COD_PACIENTE
      AND A.COD_MAES_DET = D.COD_MAES_DET
      AND A.COD_MEDICO = M.COD_MEDICO
      AND A.COD_GRUPO_CIA = H.COD_GRUPO_CIA
      AND A.COD_PACIENTE = H.COD_PACIENTE
      AND A.ID_CONSULTORIO = C.ID_CONSULTORIO
      AND A.SEC_USU_LIBERA = PU.SEC_USU_LOCAL
      --Dflores:29/08/19 VT = VerTodos
      --AND (M.COD_MEDICO = vCodMed OR 'VT' = vFilView)
      and A.IND_ANULADO = 'N'
      AND TRUNC(A.FECH_LIBERA)>=TO_DATE(vFecIni,'DD/MM/YYYY')  AND TRUNC(A.FECH_LIBERA)<=TO_DATE(vFecFin,'DD/MM/YYYY')
      --Dflores: Se agrega para que no se muestren Ventas Anuladas
      /*AND NOT EXISTS(
          SELECT * FROM VTA_PEDIDO_VTA_CAB VT WHERE VT.NUM_PED_VTA_ORIGEN = A.NUM_PED_VTA
      )*/
      ORDER BY A.NUM_ATEN_MED DESC;      
      
    RETURN curLista;
  END;
  
  PROCEDURE CME_INSERT_TIP_DOCUMENTO(cCodGrupoCia_in IN CME_ATENCION_MEDICA.COD_GRUPO_CIA%type,
                                   cCodLocalVta_in IN vta_comp_atencion_medica.Cod_Local%TYPE,
                                   cTipDocDesc IN pbl_tip_documentos.desc_tip_documento%TYPE
                                   )
  IS
  vCodDoc pbl_tip_documentos.desc_tip_documento%TYPE;
  --vNumAtencion_in cme_atencion_medica.num_aten_med%TYPE;
  BEGIN
    select (TRIM(max(cod_tip_documento))+1)||' '
      INTO vCodDoc
      from PBL_TIP_DOCUMENTOS;
   --
   INSERT INTO PBL_TIP_DOCUMENTOS(cod_tip_documento,desc_tip_documento,
    fec_crea_tip_documento,usu_crea_tip_documento,
    mascara,seleccionable)
    VALUES (vCodDoc,cTipDocDesc ,sysdate,'SISTEMAS','<#>[0-9]{10}+$','S');
  END;

END;
/
