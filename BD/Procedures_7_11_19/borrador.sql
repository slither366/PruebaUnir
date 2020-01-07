
SELECT * FROM pbl_usu_local pb where pb.login_usu = 'FZUNIGA'

SELECT * FROM CC_CONSULTORIO WHERE ID_CONSULTORIO = '39'

SELECT * FROM CC_BUS                       WHERE ID_CONSULTORIO = '39'

SELECT * FROM CC_MEDICO_X_BUS CM 
WHERE CM.NUM_CMP = '64313'
40687356
SELECT  * FROM MAE_MEDICO ME WHERE  me.cod_medico = '0000046287' like '%BORIS%'

Select * from cc_usu_x_bus
45627
select med.num_cmp || 'Ã' || med.des_nom_medico || 'Ã' || med.des_ape_medico from (
        select ME.NUM_CMP , ME.DES_NOM_MEDICO , ME.DES_APE_MEDICO 
        from MAE_MEDICO ME, CC_MEDICO_X_BUS CM
        WHERE ME.COD_MEDICO = CM.COD_MEDICO
        ORDER BY 3  ASC
) med

SELECT DES_NOM_MEDICO, DES_APE_MEDICO(SELECT SUM(BALFAC)FROM CC_MEDICO_X_BUS WHERE BUS) FROM MAE_MEDICO ME

SELECT m.DES_NOM_MEDICO, m.DES_APE_MEDICO, b.BUS 
FROM MAE_MEDICO m, CC_MEDICO_X_BUS b
where m.NUM_CMP = b.num_cmp
SELECT DES_NOM_MEDICO, DES_APE_MEDICO FROM MAE_MEDICO

select ME.NUM_CMP,ME.NUM_DOC_IDEN,ME.DES_NOM_MEDICO NOMBRE,ME.DES_APE_MEDICO APELLIDO, CC.DESCRIPCION
from MAE_MEDICO ME, CC_MEDICO_X_BUS CM, CC_BUS CB, CC_CONSULTORIO CC
WHERE ME.COD_MEDICO = CM.COD_MEDICO
AND CM.ID_BUS = CB.ID_BUS
AND CB.ID_CONSULTORIO = CC.ID_CONSULTORIO


 SELECT * FROM CC_MEDICO_X_BUS
select DESCRIPCION FROM CC_CONSULTORIO
----------------------------
--TABLA 1
select ME.NUM_CMP CMP,ME.DES_NOM_MEDICO NOMBRE,ME.DES_APE_MEDICO APELLIDO
from MAE_MEDICO ME, CC_MEDICO_X_BUS CM
WHERE ME.COD_MEDICO = CM.COD_MEDICO

--TABLA 2
select  CM.BUS
from MAE_MEDICO ME, CC_MEDICO_X_BUS CM, CC_BUS CB, CC_CONSULTORIO CC
WHERE ME.COD_MEDICO = CM.COD_MEDICO
AND CM.ID_BUS = CB.ID_BUS
AND CB.ID_CONSULTORIO = CC.ID_CONSULTORIO
AND CC.ESTADO = '1'
AND CM.NUM_CMP = '54056'

select num_cmp from
--TABLA 3

SELECT DESCRIPCION FROM CC_CONSULTORIO

select * from s

select * from cc_medico_x_bus for update 
--TABLA 4

/*select *
from  CC_MEDICO_X_BUS CM
WHERE  CM.ID_CONSULTORIO = '31'*/

SELECT  CB.ID_BUS, CB.BUS, CO.ID_CONSULTORIO, CO.DESCRIPCION
FROM CC_CONSULTORIO CO,CC_BUS CB
 WHERE CO.ID_CONSULTORIO = CB.ID_CONSULTORIO
 AND CO.ESTADO = 1
 AND CO.ID_CONSULTORIO= CB.ID_CONSULTORIO
  AND (co.Id_Consultorio = '1'/*cIdConsultorio OR 'VT'=cFilView*/)
-----

 SELECT ID_CONSULTORIO|| 'Ã' ||
             DESCRIPCION 
      FROM(
        SELECT distinct C.ID_CONSULTORIO,
               UPPER(NVL(C.DESCRIPCION,' ')) DESCRIPCION
          FROM CC_CONSULTORIO C,CC_MEDICO_X_BUS B
         WHERE C.ESTADO = '1'
         AND C.ID_CONSULTORIO = B.ID_CONSULTORIO
          --AND (B.Cod_Medico = cCodMedico OR 'VT'=cFilView)
       )
       ORDER BY DESCRIPCION asc; 
       
    ------------
    select * from CC_CONSULTORIO
    select * from cc_bus
    
        SELECT distinct B.ID_BUS ,
               UPPER(NVL(b.bus,' ')) DESCRIPCION, C.ID_CONSULTORIO
          FROM CC_CONSULTORIO C,CC_MEDICO_X_BUS B
         WHERE C.ESTADO = '1'
         AND C.ID_CONSULTORIO = B.ID_CONSULTORIO
          AND (c.Id_Consultorio = '1'/*cIdConsultorio OR 'VT'=cFilView*/)
       ORDER BY DESCRIPCION asc;
       
       --CONSULTORIOS DISPONIBLES
       select distinct * from cc_medico_x_bus
       
       select id_consultorio from cc_consultorio

      select id_bus from cc_medico_x_bus
       select * from cc_medico_x_bus
           
      select procedure cme_insert_tip_documento from  ptoventa_cme_adm 
    select * from  pbl_tip_documentos.desc_tip_documento
      
select* from CC_MEDICO_X_BUS cm
where cm.bus= 'PRUEBA'









 select med.num_cmp || 'Ã' || med.des_ape_medico || 'Ã' || med.des_nom_medico || 'Ã' ||med.cod_medico 
      from (
        select distinct ME.NUM_CMP ,ME.COD_MEDICO, ME.DES_NOM_MEDICO , ME.DES_APE_MEDICO 
        from MAE_MEDICO ME, CC_MEDICO_X_BUS CM
        WHERE ME.COD_MEDICO = CM.COD_MEDICO
        ORDER BY 3  ASC
      ) med;
