package fxRyhmaKatsaus1;
    
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ryhma.Rekisteri;
import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Veikko M, Roope T
 * @version 20.04.2020
 * 
 *  
 * Pääohjelma Ryhmakatsausksen käynnistämiseksi.
 * Puutteet on selitetty RyhmakatsausGUIControllerin documentoinnissa.
 */
public class RyhmaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            //
           // BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("RyhmakatsausGUIView.fxml"));
           // Scene scene = new Scene(root,800,550);
            // FXMLLoader use = adding data to component controlled by another controller.
            
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("RyhmakatsausGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final RyhmakatsausGUIController rekisteriCtrl = (RyhmakatsausGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("ryhmakatsaus.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Ryhma");
            
            Rekisteri rekisteri = new Rekisteri();
            rekisteriCtrl.setRekisteri(rekisteri);
            
            // EN ymmärrä tätä. Tämä on se keino millä saadaan avattua ikkunat päällekkäin mutta en ymmärä miten. Ja jos alla olevan
            // primaryStagen ottaa pois homma pelaa silti täysin samalla tavoin.

            /* primaryStage.setOnCloseRequest((event) -> {
                if ( !ryhmaCtrl.voikoSulkea() ) event.consume();
            });
            
            /*
             * Yllä voiko sulkea jos painetaan ruksia niin kysyy tallenetaanko.
             * ei tarvit sitä atm.
             * 
             * It's generally recommended that you exit 
             * a JavaFX Application with a call to Platform.exit(), 
             * which allows for a graceful shutdown
             * 
             * eli primarystage on etusivuikkuna ja jos ryhmaCtrl.avaa()
             * boolean menee läpi etusivuikkuna saa komennon platform exit?
             */
            primaryStage.show();
            if ( !rekisteriCtrl.avaa() )
                Platform.exit();
        } 
        
        
        catch(Exception e) {
            e.printStackTrace();


        }
            
           

    
    }
    
    /**
     * @param args ei kayt
     */
    public static void main(String[] args) {
        launch(args);
    }
}
