package fxRyhmaKatsaus1;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * EtusivuController Luokka: Huolehtii ensimmäisen ikkunan toiminnasta
 * Kesken... uuden tiedoston avausta ei implementoitu.
 * Kystään ryhman nimi ja luodaan siihen dialogi.
 * 
 * @author Veikko M, Roope T
 * @version 20.04.2020
 * The required InterfaceName or Class is the name of an interface 
 * or class in a type library whose methods will be implemented 
 * by the corresponding methods in the Visual Basic class.
 */
public class EtusivuController implements ModalControllerInterface<String> {
    
    @FXML private TextField textVastaus;
    private String vastaus = null;

    
    /**
     * käsitellään ok
     */
    @FXML private void handleOK() {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }

    
    /**
     * käsitellään cancel
     */
    @FXML private void handleCancel() {
        //vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }


    @Override
    public String getResult() {
        return vastaus;
    }

    
    @Override
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);
    }

    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        textVastaus.requestFocus();
    }
    
    
    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                EtusivuController.class.getResource("EtusivuIkkuna.fxml"),
                "Ryhma",
                modalityStage, oletus);
    }
    
    @FXML
    void handlePaaIkkuna() {
        ModalController.showModal(EtusivuController.class.getResource("EiLuotu.fxml"),
                    "Ryhma", null, "oletus");
    }
}

