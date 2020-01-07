package mifarma.ptoventa.centromedico.reference;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class PintarFila extends DefaultTableCellRenderer {
    private int pColumna ;


    public PintarFila(int vColumna){
        this.pColumna = vColumna;
    }

    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){        
        //setBackground(Color.white);
        //table.setForeground(Color.black);
        Component cell = super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        if(table.getValueAt(row,pColumna).equals("Por subir...")){
            setBackground(selected ? Color.pink : Color.pink);
            setForeground(selected ? Color.black : Color.black);
        }else{
            setBackground(selected ? Color.pink : Color.white);
            setForeground(selected ? Color.black : Color.black);
        }
        return this;
    }
}
