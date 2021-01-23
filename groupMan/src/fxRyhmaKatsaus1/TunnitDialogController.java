package fxRyhmaKatsaus1;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ryhma.Jasen;
import ryhma.TyotunnitHlo;


/**
 * TunnitDialogController Luokka:
 * Controlleri tunneille jotta nämä saadaan yhdeistettyä käyttöliittymän toimintaan
 * Kesken...
 * @author Roope T, Veikko M
 * @version 19.4.2020
 *
 */
public class TunnitDialogController implements ModalControllerInterface<TyotunnitHlo>,  Initializable {
    
    @FXML private Label labelVirhe;
    //@FXML private TextField editMaTav;
    //@FXML private TextField editMaTot;
    //@FXML private TextField editTiTav;
    //@FXML private TextField editTiTot;
    
    @FXML private TextField editTavTunnit;
    @FXML private TextField editTosTunnit;
    /**
     *  haluat:        
     *  textField editTavTunnit
     *  textField editTosTunnit       
     */
    private TextField edits[];
    private Jasen jasenKohdalla;
    
    
    /**
     * @param edits tyhjennetään
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }

    
    /**
     * alustetaan text fieldit
     
    protected void alusta() {
        edits = new TextField[] {editMaTav, editMaTot, editTiTav, editTiTot};
            int i = 0; 
            for (TextField edit : edits) { 
              final int k = ++i; 
              edit.setOnKeyReleased( e -> kasitteleMuutosTunteihin(k, (TextField)(e.getSource()))); 
          } 
    }*/
    
    protected void alusta() {
        edits = new TextField[] {editTavTunnit, editTosTunnit};
            int i = 0; 
            for (TextField edit : edits) { 
              final int k = ++i; 
              edit.setOnKeyReleased( e -> kasitteleMuutosTunteihin(k, (TextField)(e.getSource()))); 
          } 
    }
    
    // ei jäsenkohdalla nimestä ole hyötyä
    // aseta jasenKohdalla.setTavTunnit(s); break; jne
    // tuon joutuu käydä tekemässä jompaankumpaan niistä tunti luokista
    // en oo täysin varma mutta kannattaa kokeilla. Lähinnä se että tunnit pitää varmaa muuttaa strngiks kun ne hakee
    // koska string s = edit.getText()
    private void kasitteleMuutosTunteihin(int k, TextField edit) {
        if (jasenKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
           case 1 : virhe = jasenKohdalla.setNimi(s); break;
           case 2 : virhe = jasenKohdalla.setIka(s); break;
           case 3 : virhe = jasenKohdalla.setTerveys(s); break;
           default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            //naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            //naytaVirhe(virhe);
        }
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }

    
    @Override
    public TyotunnitHlo getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    
    /**
     * @param oletus eli missä jasenessa mennään.
     */
    public void setDefault(Jasen oletus) {
        // TODO Auto-generated method stub
        jasenKohdalla = oletus;
        naytaTunnit(edits, jasenKohdalla);
    }
    
    
    @FXML private void handleDefaultOk() {
        ModalController.closeStage(labelVirhe);
    }
    
    
    /**
     * @param edits kenttien tiedot tällä jäsenellä
     * @param jasen näytettävän jäsenen tunnit
     */
    public static void naytaTunnit(TextField edits[], Jasen jasen) {
        if (jasen == null) return;
        edits[0].setText("[0, 0, 0, 0, 0]");
        edits[1].setText("[0, 0, 0, 0, 0]");
        //edits[2].setText("7");
        //edits[3].setText("7");
            
    }
    
    
    /**
     * @param modalityStage mille ollaan modaalisia
     * @param oletus mitä dataa näytetään oletuksena
     * @return null, jos painetaan Cancel
     */
    public static Jasen kysyTunnit(Stage modalityStage, @SuppressWarnings("unused") Jasen oletus) {
        
        return ModalController.showModal(RyhmakatsausGUIController.class.getResource("TyotunnitJaMuistiinpanotView.fxml"),
                "Ryhma", modalityStage, null);
        
    }


    @Override
    public void setDefault(TyotunnitHlo oletus) {
        // TODO Auto-generated method stub
        
    }

    /*
    @Override
    public void setDefault(TyotunnitHlo arg0) {
        // TODO Auto-generated method stub
        
    }*/
    
}
