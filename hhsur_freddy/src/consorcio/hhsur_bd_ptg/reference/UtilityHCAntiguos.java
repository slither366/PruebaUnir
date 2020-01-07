package consorcio.hhsur_bd_ptg.reference;

import common.FarmaTableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class UtilityHCAntiguos {

    public UtilityHCAntiguos() {

    }

    public void parsearResultado(List<String> pListado, FarmaTableModel pTableModel, boolean pWithCheck) {
        String tmpRes = "";
        ArrayList<Object> tmpArray = null;
        StringTokenizer st = null;
        pTableModel.clearTable();
        for (String beanResultado : pListado) {
            tmpRes = beanResultado;
            tmpArray = new ArrayList<Object>();
            if (tmpRes != null) {
                st = new StringTokenizer(tmpRes, "Ã");
                if (pWithCheck)
                    tmpArray.add(Boolean.valueOf(false));
                while (st.hasMoreTokens()) {
                    tmpArray.add(st.nextToken());
                }
            }
            pTableModel.insertRow(tmpArray);
        }
    }
}
