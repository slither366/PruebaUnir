package con_general;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

/**
 *
 * @author ERUFASTO 20190219
 */
public class SuggestionLabel extends JLabel{
    

    private boolean focused = false;
    private final JWindow autoSuggestionsPopUpWindow;
    private final JTextField textField;
    private final JLabel lblCodigo ;
    private final AutoSuggestor autoSuggestor;
    private Color suggestionsTextColor, suggestionBorderColor;
    
    public String vCodProd = "";
    public String vNomProd = "";

    public SuggestionLabel(String pCodigo,String vCadena, 
                           final Color borderColor, Color suggestionsTextColor, AutoSuggestor autoSuggestor) {
        /*String vCodProd = "";
        String vNomProd = "";
        
        vCodProd = vCadena.split("|@|")[1];
        vNomProd = vCadena.split("|@|")[2];*/
        
        // "|@|"
        super(vCadena);
        vCodProd = pCodigo;
        vNomProd = vCadena;
        //vCodProd = vCadena.split("|@|")[1];
        //vNomProd = vCadena.split("|@|")[2];
        
        this.suggestionsTextColor = suggestionsTextColor;
        this.autoSuggestor = autoSuggestor;
        this.textField = autoSuggestor.getTextField();
        this.lblCodigo = autoSuggestor.getLabelCodig();
        this.suggestionBorderColor = borderColor;
        this.autoSuggestionsPopUpWindow = autoSuggestor.getAutoSuggestionPopUpWindow();

        initComponent();
    }

    private void initComponent() {
        setFocusable(true);
        setForeground(suggestionsTextColor);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                replaceWithSuggestedText();

                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "Enter released");
        getActionMap().put("Enter released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                replaceWithSuggestedText();
                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });
    }

    public void setFocused(boolean focused) {
        if (focused) {
            setBorder(new LineBorder(suggestionBorderColor));
        } else {
            setBorder(null);
        }
        repaint();
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

    private void replaceWithSuggestedText() {
        //String[] pListCadena = getText().split("|@|");
        //vCodProd = pListCadena[1].toString();
        //vNomProd = pListCadena[2].toString();
        String suggestedWord = vNomProd;
        String vCodProdN = vCodProd;
        
        String text = textField.getText();
        String typedWord = autoSuggestor.getCurrentlyTypedWord();
        String t = text.substring(0, text.lastIndexOf(typedWord));
        String tmp = t + text.substring(text.lastIndexOf(typedWord)).replace(typedWord, suggestedWord);
        textField.setText(tmp + " ");
        
        lblCodigo.setText(vCodProdN);
    }
}
