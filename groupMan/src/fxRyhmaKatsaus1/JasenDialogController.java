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

/**
 * Controller jäsenelle. Luodaan uusi dialogi ja kysytään tämän tiedot.
 * 
 * @author Veikko M, Roope T
 * @version 7 Apr 2020
 *
 */
public class JasenDialogController implements ModalControllerInterface<Jasen>, Initializable{
    
    @FXML private TextField editNimi;
    @FXML private TextField editIka;
    @FXML private TextField editKoulutus;
    @FXML private TextField editTyokokemus;
    @FXML private TextField editTerveys;
    @FXML private TextField editAlkupera;
    @FXML private TextField editPerhe;
    @FXML private TextField editHarrastus;
    @FXML private TextField editAsuinmuoto;
    @FXML private TextField editVahvuus;
    @FXML private TextField editHeikkous;
    
    @FXML private Label labelVirhe;
    
    @FXML private void handleDefaultOK() {
        if ( jasenKohdalla != null && jasenKohdalla.getNimi().trim().equals("") ) {                  
            naytaVirhe("Tyhjä nimi");
            return;
    } 
        ModalController.closeStage(labelVirhe);
    }
    
    @FXML private void handleDefaultCancel() {
        //jasenKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        // TODO Auto-generated method stub
        alusta();
    }
    
    //=============================================

    
    private Jasen jasenKohdalla;
    private TextField edits[];
    
    
    /**
     * @param edits tyhjennetään
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }

    
    /**
     * alustetaan text fieldit
     */
    protected void alusta() {
        edits = new TextField[] {editNimi, editIka, editKoulutus, editTyokokemus, editTerveys, 
                editAlkupera, editPerhe, editHarrastus, editAsuinmuoto, editVahvuus, editHeikkous};
            int i = 0; 
            for (TextField edit : edits) { 
              final int k = ++i; 
              edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen(k, (TextField)(e.getSource()))); 
          } 
    }
    
    
    /**
     * haetaan kenen kohdalla ollaan
     */
    @Override
    public Jasen getResult() {
        // TODO Auto-generated method stub
        return jasenKohdalla;
    }

    
    /**
     * Focus editNimen kohdalle
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
        
    }

    
    /**
     * JasenKohdalla on oletusarvo.
     * kutsutaan näytäjäsen aliohjelmaa editseillä(mihin tiedot laitetaan) ja valitulla jasenellä
     */
    @Override
    public void setDefault(Jasen oletus) {
        jasenKohdalla = oletus;
        naytaJasen(edits, jasenKohdalla);
        
    }
    
     /**
      * Käsitellään jäseneen tullut muutos
      * @param edit muuttunut kenttä
      */
     private void kasitteleMuutosJaseneen(int k, TextField edit) {
         if (jasenKohdalla == null) return;
         String s = edit.getText();
         String virhe = null;
         switch (k) {
            case 1 : virhe = jasenKohdalla.setNimi(s); break;
            case 2 : virhe = jasenKohdalla.setIka(s); break;
            case 3 : virhe = jasenKohdalla.setKoulutus(s); break;
            case 4 : virhe = jasenKohdalla.setKokemus(s); break;
            case 5 : virhe = jasenKohdalla.setTerveys(s); break;
            case 6 : virhe = jasenKohdalla.setAlkupera(s); break;
            case 7 : virhe = jasenKohdalla.setPerhe(s); break;
            case 8 : virhe = jasenKohdalla.setHarrastus(s); break;
            case 9 : virhe = jasenKohdalla.setAsuinmuoto(s); break;
            case 10: virhe = jasenKohdalla.setVahvuus(s); break;
            case 11: virhe = jasenKohdalla.setHeikkous(s); break;
            default:
         }
         if (virhe == null) {
             Dialogs.setToolTipText(edit,"");
             edit.getStyleClass().removeAll("virhe");
             naytaVirhe(virhe);
         } else {
             Dialogs.setToolTipText(edit,virhe);
             edit.getStyleClass().add("virhe");
             naytaVirhe(virhe);
         }
     }
    
     private void naytaVirhe(String virhe) {
         if ( virhe == null || virhe.isEmpty() ) {
             labelVirhe.setText("");
             labelVirhe.getStyleClass().removeAll("virhe");
             return;
         }
         labelVirhe.setText(virhe);
         labelVirhe.getStyleClass().add("virhe");
     }
     
     
    /**
     * @param edits kenttien tiedot kyseisella jasenella
     * @param jasen kyseinen jasen
     * 
     * terveyden paikka ei ole 2 vaan missä lie tarkista kun täytät kaikki
     */
    public static void naytaJasen(TextField[] edits, Jasen jasen) {
        if (jasen == null) return;
        edits[0].setText(jasen.getNimi());
        edits[1].setText(jasen.getIka());
        edits[2].setText(jasen.getKoulutus());
        edits[3].setText(jasen.getTyokokemus());
        edits[4].setText(jasen.getTerveys());
        edits[5].setText(jasen.getAlkupera());
        edits[6].setText(jasen.getPerhe());
        edits[7].setText(jasen.getHarrastus());
        edits[8].setText(jasen.getAsuinmuoto());
        edits[9].setText(jasen.getVahvuus());
        edits[10].setText(jasen.getHeikkous());
    }
    
       
    /**
     * @param modalityStage mille ollaan modaalisia
     * @param oletus mitä dataa näytetään oletuksena
     * @return null jos cancel muuten täytetty tietue
     */
    public static Jasen kysyJasen(Stage modalityStage, Jasen oletus) {
        return ModalController.<Jasen, JasenDialogController>showModal(
                JasenDialogController.class.getResource("LisaaJasenView.fxml"), "Ryhmä", 
                modalityStage, oletus, null);
                
    }

}
