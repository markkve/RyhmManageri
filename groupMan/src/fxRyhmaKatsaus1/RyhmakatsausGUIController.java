package fxRyhmaKatsaus1;

// import java.awt.Desktop;
// import java.io.IOException;
import java.io.PrintStream;
// import java.net.URI;
// import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
//import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
//import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
//import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
//import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ryhma.Jasen;
import ryhma.Rekisteri;
import ryhma.Ryhma;
import ryhma.SailoException;
import ryhma.TyotunnitHlo;



/**
 * Luokka ryhmakatsauksen käyttöliittymän taphatumien hoitoon.
 * 
 * @author Veikko M, Roope T
 * @version 20.04.2020
 * TODO: Tuntien luku toimivaksi
 * TODO(?): Lisää tunnit alustaa ne 00000
 * 
 * luokat omaavat melko kattavat testipääohjelmat.
 * Haku operaation pikaista selostusta. Ryhmät chooserin kanssa toimiessa jotta saadaan jäsenet näkyviin
 * on haettava kaikki jäsenet läpi jotta löydetään valitun ryhmän indeksin omaavat yksilöt.
 * Nyt valittaessa jäsen on haettava läpi myös kaikki työtunti tyypin oliot ja poimittava ne jotka omaavat attribuuttina
 * halutun jäsenen numeron. Tulostetaan henkilön tavoitetunnit ja toteutuneet tunnit omiin lokeroihinsa.
 * 
 * Puutteita: 
 * 
 * Puuttuu poistaminen ja tulostus.
 *
 * 
 * Tuntien muokkaaminen ei toimi tiedoston ulkopuolella. 
 * 
 * (Varsinainen haku lähtee guicontrollerista)
 * Erillinen pieni haku on toteutettu myös ryhmat luokassa mutta ei saatu tälle käyttöä. Haetaan onko tietty ryhmä kyseessä.
 * Voisi toteuttaa esim erillisenä hakuna pääikkunassa mutta vaikea toteuttaa karanteenista läppärilä joten jääköön.
 * 
 *  Uuden tiedoston luomista ei olla implementoitu
 */
public class RyhmakatsausGUIController implements Initializable {
    
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
    //@FXML private TextArea tavoiteTunnit;
    //@FXML private TextArea tosiTunnit;
    
    
    
    //@FXML private TextField hakuehto;
    // @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML ListChooser<Jasen> chooserJasenet;
    //@FXML private ScrollPane panelJasen;
    
    @FXML ListChooser<Ryhma> chooserRyhmat;
    @FXML private ScrollPane panelTosT; // tavallaan sisältää myös yhden jäsenen ja tulee sisältämään tämän harrastukset. 
    @FXML private ScrollPane panelTavT;                                   //tässä (5 vaihe) tarkoittaa aluetta mihin tulostetaan

    //private String ryhmannimi = "";
    
    /**
     * kutsutaan alusta metodia
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    

    /**
     * tallentaminen pääikkunassa menussa
     */
    @FXML
    void handleTallenna() {
        // Dialogs.showMessageDialog("Ei osata vielä tallentaa");
        tallenna();
    }
    
    
    /**
     * Jäsenen poisto pääikkunassa
     */
    @FXML
    void handlePoistaJasen() {
        Dialogs.showQuestionDialog("Poisto?", "Poistetaanko jäsen:", "Yes", "No");
        poistaJasen();
        
    }
    
    /**
     * Ryhman poisto pääikkunassa
     */
    @FXML
    void handlePoistaRyhma() {
        Dialogs.showQuestionDialog("Poisto?", "Poistetaanko Ryhma:", "Yes", "No");
        poistaRyhma();
        
    }
    
    
    /**
     * kutsutaan avaa metodia. 
     */
    @FXML
    private void handleAvaa() {
        avaa();
    }
    
    
    /**
     * Ei osata vielä tulostaa menu paaikkuna
     */
    @FXML
    void handleTulosta() {
        Dialogs.showMessageDialog("Ei osata vielä tulostaa mitään");
    }
    
    
    /**
     * Jasenen muokkaaminen paaikkunassa
     */
    @FXML
    void handleMuokkaaJasen() {
        muokkaa();
        
    }
    
    
    /**
     * painettu Uusi jasen
     * uuden jasenen lisaaminen. kutsutaan omaa aliohjelmaa
     */
    @FXML
    void handleUusiJasen() {
        // ModalController.showModal(RyhmakatsausGUIController.class.getResource("LisaaJasenView.fxml"),
        //        "Ryhma", null, "oletus");
        uusiJasen();
    }
    
    
    /**
     * uudeet työtunnit jasenelle
     * taytyy olla jasenen kohdalla
     */
    @FXML
    void handleLisaaTunnit() {
        // ModalController.showModal(RyhmakatsausGUIController.class.getResource("LisaaJasenView.fxml"),
        //        "Ryhma", null, "oletus");
        if (jasenKohdalla == null) return;
        uusiTuntip();
    }
    
    
    /**
     * kutsutaan uusiRyhma aliohjelmaa
     */
    @FXML
    void handleUusiRyhma() {
        // ModalController.showModal(RyhmakatsausGUIController.class.getResource("LisaaJasenView.fxml"),
        //        "Ryhma", null, "oletus");
        uusiRyhma();
    }
    
    
    /**
     * tyotuntien muokkaaminen paaikkunassa. Avataan modaalinen ikkuna
     */
    @FXML
    void handleMuokkaaTyotunnit() {
        // ModalController.showModal(RyhmakatsausGUIController.class.getResource("TyotunnitJaMuistiinpanotView.fxml"),
        //        "Ryhma", null, "oletus");
        muokkaaTunnit();   
       
    }


    /**
     * Avataan pääikkuna menun avaa tiedostosta. Liitännäinen edelle mainittuun handle avaa kohtaan
     */
    @FXML
    void handleAvaaRyhma() {
        //ModalController.showModal(RyhmakatsausGUIController.class.getResource("EtusivuIkkuna.fxml"),
        //        "Kerho", null, "oletus");
        Dialogs.showMessageDialog("Ei osata vielä tulostaa mitään");
    }
    
    
    @FXML
    void handleLopeta() {
        /// Dialogs.showQuestionDialog("Lopetetaanko?", "Lopetetaanko?", "Kyllä", "Ei");
        Platform.exit();
    }


    
    
    //===========================================================================
    // TÄHÄN ALLE EI SUORAAN KÄYTTÖLIITTYMÄÄN LIITTYVÄÄ KOODIA
    
    // nopea muistutus kuinka tätä luodaan. ALhaalta ylöspäin. Eli Esim Sieltä missä ei ole nuolia
    // lähdetään koodaamaan ylöspäin. Eli esim Yksi RYHMA luokka, Sittten RYHMAT luokka johon ryhmät ilmestyy.
    // sieltä Rekisteriin joka ohjaa ryhmien toimia.
    //
    // ja käytttöliittymä ohjaa toimintaa ja näkyvyyttä. Kukaan ei saan näkyä ellei kutsuta.
    
    // -kohdalla ovat olioita jotka kartoittavat choosereissa kuka on milloinkin valittuna
    // täytyy tietää kuka on valittuna jotta osataan lisätä oikealle ryhmälle jasenet ja oikealle jäsenelle tunnit.
    
    private Rekisteri rekisteri;
    private TextArea areaTostunnit = new TextArea(); //areaRyhma
    private TextArea areaTavtunnit = new TextArea();
    private Ryhma ryhmaKohdalla;
    private Jasen jasenKohdalla;
    private String rekisterinnimi = "masterf"; // rekisteriin tallentuu ryhmat, jasenet ja työtunnit.
    private TextField[] edits;
    
    
    
    /**
     * Alustus. Vaihe 5 text area johon tulostetaan tiedot.
     * vaihe 7. ListChooserit jäsenille ja ryhmille.
     * Fancympi text area molemmille tunneille.
     */
    protected void alusta() {
        //panelRyhma.setContent(areaRyhma);
        //areaRyhma.setFont(new Font("Comic Sans", 16));
        panelTavT.setFitToHeight(true);
        panelTavT.setContent(areaTavtunnit);
        panelTosT.setFitToHeight(true);
        panelTosT.setContent(areaTostunnit);
        
        
        chooserRyhmat.clear();
        chooserRyhmat.addExample("Reset");
        chooserRyhmat.addSelectionListener(e -> naytaRyhma()); // kuunellaan missä "valinta" on ja näytetään sitä vastaavat tiedot
                                                               // eli kutsutaan naytaRyhma aliohjelmaa
        chooserJasenet.clear();
        chooserJasenet.addExample("Reset");
        chooserJasenet.addSelectionListener(e -> naytaJasen());// samoin kuunnellaan valintaa ja näytetään jäsenen tiedot. mukaan lukien tunnit
                                                               // naytaJasen aliohjelma
        
        edits = new TextField[] {editNimi, editIka, editKoulutus, editTyokokemus, editTerveys,
                editAlkupera, editPerhe, editHarrastus, editAsuinmuoto, editVahvuus, editHeikkous};
    }
    
    
    /**
     * Asetetaan kontrollerin rekisteriviite
     * @param rekisteri mihin rekisteriin viitataan
     */
    public void setRekisteri(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;
    }
    
    
    /**
     * luodaan uusi ryhma
     * siirrytään Ryhma luokkaan rekisteröimään tämä ryhmä
     * täyttö tiedoilla tapahtuu samassa luokassa
     * 
     * siirrytään rekisteriin ja lisätään ryhmä. Rekisterissä kutsutaan ryhmät luokkaa jossa lisäys tapahtuu taulukkoon.
     * 
     * hae aliohjelma ohjaa toimintaa chooserissa. Parametrina viedään uuden syntyneen ryhmän tunnusnumero.
     *
     */
    private void uusiRyhma() {
        //chooserJasenet.clear();
        Ryhma uusi = new Ryhma();
        uusi.rekisteroi();
        uusi.taytaRyhmaTiedoilla();
        try {
            rekisteri.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelma uutta Ryhmaa luodessa " + e.getMessage());
            return;
        }
        hae(uusi.getTunnusNro());
        
    }
    
    
    /**
     * 
     * @param rtunnus ryhman tunnusnumero
     * 
     * puhdistetaan chooserRyhmat jotta edellinen ryhmä ei tuplaannu
     * 
     * pieni funktio joka toimii yhteydessä rekisterin kanssa jotta chooserRyhmät "muistaa" aina puhdistuksen -
     * jälkeen jo aiemmin luodut ryhmät ja nyt -
     * lisää tällä kierroksella luotavan ryhmän niiden perään list chooseriin.
     * 
     * jos silmukassa käsiteltävän ryhmän tunnus on sama kuin parametrina tuotu uuden ryhmän numero indeksi saa arvokseen i:n
     * Ryhmä lisätään chooseriin ja siirretään "osoitin" tämän uuden ryhmän päälle eli indeksiin.
     * 
     * samalla laitetaan chooserJasenen odoitin indeksiin 0 vakiona joka on reset tila tähän hätään.
     */
    private void hae(int rtunnus) {
        chooserRyhmat.clear();  //huolehtii että kun luodaan uusi ryhma vanha ryhma ei tuplaannu
        int indeksi = 0;
        for (int i = 0; i < rekisteri.getRyhmia(); i++) {
            Ryhma ryhma = rekisteri.annaRyhma(i); // ryhmä olio tässä taulukon kohdassa. Eli tällä i:n arvolla.
            if (ryhma.getTunnusNro() == rtunnus) indeksi = i;
            chooserRyhmat.add(ryhma.getNimi(), ryhma); // add metodi tarvitsee parametreina String ja ryhmän (paikasta i)
            
        }
        chooserRyhmat.getSelectionModel().select(indeksi);
        chooserJasenet.getSelectionModel().select(0);
    }
    
    /**
     * Metodi joka pohjustaa ryhmien ja jäsenten yhteistyön.
     * @param ryhmakohdalla ryhma chooserissa valittuna oleva alkio / ryhma
     * 
     */
    private void haeJasenet(Ryhma ryhmakohdalla) {
        chooserJasenet.clear();
        List<Jasen> jasenet = rekisteri.annaJasenet(ryhmakohdalla);
        for (var jasen : jasenet) {
            chooserJasenet.add(jasen.getNimi() + " | R: " + jasen.getRyhmanNro(), jasen);
        }
    }
    
    /*
    private void haeTunnit(Jasen jasenkohdalla) {
        List<TyotunnitHlo> tunnit = rekisteri.annaTunnit(jasenkohdalla);
        for (var jasen : tunnit) {
            areaRyhma.appendText(jasen.getJasenenNro() + "  " + jasen.getTunnusNro());
        }
    }
    */
    
    
    /**
     * Selection listener kutsuu. Tarvitaan oma aliohjelma jolla haetaan viereiseen list chooseriin
     * valitun ryhmän kaikki jäsenet.
     * 
     * Tulostaa valitun ryhman tiedot tekstikentaan. Tassa tapauksessa vain sen nimen ja tunnus numeron.
     * Jos ollaan tehty ryhma ja jasenia ei saa pelkan ryhman tietoja enaa nakyviin. 
     * Olisi hyva jos saisi tai jasen näyttäisi ryhman nimen. Tämä on parempi oikeastaan.
     * 
     * Kutsutan tulosta metodia parametreilla os ja ryhmäKohdalla joka on viite tällähetkellä valittuun ryhmään.
     */
    protected void naytaRyhma() {
        this.ryhmaKohdalla = this.chooserRyhmat.getSelectedObject();
        if (this.ryhmaKohdalla == null) return;
             
        
        /* this.areaRyhma.setText(""); // resettaa text arean. Muuten näyttäisi kaiken text areaan tulostetun kaman
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(this.areaRyhma)) {
             tulosta(os, ryhmaKohdalla);
             
         }  */
        haeJasenet(ryhmaKohdalla);
    }
    
    
    /**
     * Poistetaan ryhmä. 
     * Siirrytään rekisteriin
     */
    private void poistaRyhma() {
        Ryhma ryhma = this.ryhmaKohdalla;
        if (ryhma == null) return;
        
        this.rekisteri.poista(ryhma);
        hae(0);
        naytaRyhma();
    }
    
    
    /**
     * Luodaan uusi jasen. 
     * Jos ei ole luotu ryhmaa palauttaa nullin. 
     * Aletaan pelaamaan jasen luokan kanssa.
     * Rekisteroidaan ja taytetaan uusi jasen tiedoilla.
     * taytaAkuTiedoilla tarvitsee parametrina ryhman id.
     * pitaa teitaa missä ryhmassa ollaan.
     * mennään rekisteriin lisäämään jasen.
     * catchaa sailoexceptionin jos on liikaa jasenia 15.
     * Lisaa jasenen nimen chooserJasenet lohkoon. Tassa humio kaikki jasenet nakyvissa.
     * 
     * 
     * Olisi hyvä muuttaa niin että nayttaa vaan sen ryhma njasenet missä ollaan. Toisaalta näinkin
     * voi nopeuttaa käyttöä jos on vain yksi pääkäyttäjä.
     * sailo expetion ei tee mitään still
     */
    private void uusiJasen() {
        
        if (ryhmaKohdalla == null) return;
        //int testi = ryhmaKohdalla.getTunnusNro();
        Jasen jasen = new Jasen();
        jasen.rekisteroi();
        jasen.taytaAkuTiedoilla(this.ryhmaKohdalla.getTunnusNro()); // TODO: Korvaa kunnon dialogilla.
        try {
            this.rekisteri.lisaa(jasen);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelma uutta Jasenta luodessa " + e.getMessage());
            return;
        }
         //haeJasen(jasenKohdalla.getTunnusNro()); //tämä on paremmin toimiva versio mutta kuinka toteuttaa listalle

         chooserJasenet.add(jasen.getNimi() + " | R: " + jasen.getRyhmanNro(), jasen);
         areaTostunnit.clear();
         areaTavtunnit.clear();
    }   
    
   /**
    * muokkaa metodi jäsenille, jotta voidaan päivittää tietoja uudessa ikkunassa.
    * Yhteistyössä JasenDialogControllerin kanssa
    */
   private void muokkaa () {
       //ModalController.showModal(RyhmakatsausGUIController.class.getResource("LisaaJasenView.fxml"), "Ryhma", null, "oletus");
       if ( jasenKohdalla == null ) return; 
       JasenDialogController.kysyJasen(null, jasenKohdalla);
       haeJasenet(ryhmaKohdalla);
       
   }
   
   //
   
   /**
    * Poistetaan jasen. 
    * Siirrytään rekisteriin
    */
   private void poistaJasen() {
       Jasen jasen = this.jasenKohdalla;
       if (jasen == null) return;
       
       //if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko jasen: " + jasen.getNimi(), "Kyllä", "Ei")); return;
       rekisteri.poista(jasen);
       haeJasenet(ryhmaKohdalla);
       
       //int indeksi = this.chooserJasenet.getSelectedIndex();
       chooserJasenet.getSelectionModel().select(0);
       
   }
   
   /*
    private void muokkaa() {  
        if ( jasenKohdalla == null ) return;  
        try {  
            Jasen jasen;  
            jasen = JasenDialogController.kysyJasen(null, jasenKohdalla.clone());  
            if ( jasen == null ) return;  
            rekisteri.korvaaTaiLisaa(jasen);  
            hae(jasen.getTunnusNro());  
        } catch (CloneNotSupportedException e) {  
        //  
        } catch (SailoException e) {  
            Dialogs.showMessageDialog(e.getMessage());  
        }  
   }*/

    /**
     * Tulostaa jasenen tiedot tekstikentään.
     * 
     * Eli otetaan selvää jasenKohdalla avulla kuka kyseinen jasen on (missä valinta on parhaillaan)
     * Jos ei ole jäsentä ei tulosteta mitään.
     * Asetetaan "" jotta ei text area ei näytä turhaa tietoa
     * kutsutaan tulosta aliohjelmaa ja parametreina viedään virta mihin tulostetaan ja tällähetkellä valittu jäsen.
     * 
     * Muutetaan niin että tulostaa myös ryhman nimen ja montako jasenta siina on.
     * Yllä oleva ei kannata. Mieluummin niin että näytttää chooserissa vain tietyn
     * ryhman jasenet.
     * Jos ei ole jasenia ei tulosta mitaan.
     * jasen kohdalla saa arvokseen chooserJasenista valitun henkilön.
     */
    private void naytaJasen() {
        this.jasenKohdalla = this.chooserJasenet.getSelectedObject();
        if (this.jasenKohdalla == null) return;
        /* this.areaRyhma.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(this.areaRyhma)) {
            //tulostaR(os, ryhmaKohdalla); ei kannata pitää tehdä uusi tulosta metodi ryhma luokkaan
            // näyttäisi että toimii myös ilman jäsen oliota parametrina, koska jasenKohdalla on yleisesti tiedossa.
            // Selkeyden ja varmuuden vuoksi kun en täysin ole varma mitä vaikutuskia voi olla jätetään parametriksi
            // Kuitenkin jos olisi enemmän tulosta metodeja pellkä tulosta os ei ehkä toimisi koska nyt jos myös tunneille
            // olisi sama parametritön (os) tulosta metodi ei tiedettäisi mitä tulostaa.  
            tulosta(os, jasenKohdalla);
        } */
        areaTostunnit.clear();
        areaTavtunnit.clear();
        this.areaTavtunnit.setText("");
        this.areaTostunnit.setText("");
        JasenDialogController.naytaJasen(edits, jasenKohdalla);
        //editNimi.setText(jasenKohdalla.getNimi());
        //naytaTavtunnit(jasenKohdalla);
        naytaTostunnit(jasenKohdalla);
        naytaTavtunnit(jasenKohdalla);
    }
    
    
    private void naytaTostunnit(Jasen jasen) {
        
        if ( jasen == null ) return;
        
            List<TyotunnitHlo> tunnit = rekisteri.annaTunnit(jasen);
        if ( tunnit.size() == 0 ) return;
        for (TyotunnitHlo tun: tunnit)
            naytaTostunnit(tun);
            
         
    }
    
    
    private void naytaTostunnit(TyotunnitHlo tun) {
        //areaTostunnit.setText(""); // resettaa text arean. Muuten näyttäisi kaiken text areaan tulostetun kaman
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTostunnit)) {
             tulostaT(os, tun);
             }
    }
    

    /**
     * 
     * @param jasen kenen tunnit halutaan
     * 
     * metodi jolla haetaan ja näytetään tällä hetkellä valitun jäsenen toteutuneet
     */
    private void naytaTavtunnit(Jasen jasen) {
        
        if ( jasen == null ) return;
        
        List<TyotunnitHlo> tunnit = rekisteri.annaTunnit(jasen);
        if ( tunnit.size() == 0 ) return;
        for (var tun: tunnit)
            naytaTavtunnit(tun); 
    }

    
     /**
     * 
     * @param jasen kenen tunnit halutaan
     * 
     * metodi jolla haetaan ja näytetään tällä hetkellä valitun jäsenen tavoitetunnit
     */
    private void naytaTavtunnit(TyotunnitHlo tun) {
        //areaTavtunnit.setText(""); // resettaa text arean. Muuten näyttäisi kaiken text areaan tulostetun kaman
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTavtunnit)) {
             tulostaOletus(os, tun);
             }
    }

    /**
     * oma tulosta metodi kun luodaan tunnit ja halutaan näyttää ne text areassa.
     * yksinään ei jäseniä tai ryhmiä
     */
    
    private void tulostaT(PrintStream os, TyotunnitHlo tun) {
        if (chooserJasenet == null ) return;
        tun.tulostaT(os);
    }
    
    /**
     * oma tulosta metodi kun luodaan tunnit ja halutaan näyttää ne text areassa.
     * yksinään ei jäseniä tai ryhmiä
     */
    private void tulostaOletus(PrintStream os, TyotunnitHlo tun) {
        if (chooserJasenet == null ) return;
        tun.tulosta(os);
    }
    
    
    /**
     * aloitettu työtuntien muokkausta, mutta ei totetutettu loppuun
     */
    private void muokkaaTunnit() {
        TunnitDialogController.kysyTunnit(null, jasenKohdalla);
        //ModalController.showModal(RyhmakatsausGUIController.class.getResource("TyotunnitJaMuistiinpanotView.fxml"),
        //        "Ryhma", null, "oletus");
    }

    
    
    
    // eli näyttää tunnit vasta kun on kaksi jasenta., Miksi?
    /**
     * kun luodaan uusi tuntipaketti se luodaan tietylle jäsenelle. 
     * Tämä tarkoittaa että paketin pitää tietää oma jäsenensä. Siksi sille viedään
     * parametrina chooserissavalitun jäsenen tunnusnumero.
     * 
     * Ja näin ollen työtunnitHlo luokassa pitää olla muodostaja joka huomioi jos luodaan uusi tuntipaketti henkilölle.
     */
    private void uusiTuntip() {  
        if (jasenKohdalla == null) return;
        TyotunnitHlo uusi = new TyotunnitHlo(jasenKohdalla.getTunnusNro());
        uusi.rekisteroi();
        uusi.vastaaTavoiteTyotunnit(jasenKohdalla.getTunnusNro());
        uusi.vastaaTosiTyotunnit(jasenKohdalla.getTunnusNro());
        try {
            rekisteri.lisaa(uusi);
            naytaTostunnit(jasenKohdalla);
            naytaTavtunnit(jasenKohdalla);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelma tyotunteja lisatessa " + e.getMessage());
            return;
        }
        naytaJasen();
        //OK huomio. ELi tässä on error. Kun Lisaa tunnit jasenelle toisen kerran se tulostaa vanhat tunnit myös.
        //mun mielestä tätä oli estetty silla chooserRyhmat clearilla hae aliohjelmassa. Tälle ei ole omaa hae versiota tosin.
    }
    
    

    

    
     /**
     * @param os virata
     * @param ryhma valittu ryhmä
     * 
     * halutaan tulostaa juuri tämän ryhmän tiedot, ryhmä luokan tulosta metodi
     * ja haetaan kaikki kyseisen ryhmän jäsenet rekisterin kautta.
     */
    public void tulosta(PrintStream os, Ryhma ryhma) {
         os.println("---------------------------------");
         ryhma.tulosta(os);
         os.println("---------------------------------");
         
         // vaatii kommenttia että siäsistän todella: 
         // Eli Uusi Jasen tyypin lista jasenet joka täytetään -
         // kaikilla juuri tämän ryhmän jäsenillä jotka haetaan rekisteristä. (Rekisterin kautta)
         // For each silmukka: Jokaiselle tyypin Jasen jasen(elle) äsken luodussa jasenet listassa -
         // suoritetetaan tulosta toiminto. 
         
         List<Jasen> jasenet = rekisteri.annaJasenet(ryhma);
         for (Jasen jasen : jasenet) {
             jasen.tulostaNt(os);
         }
    }    
    
    
    /**
     * tulostaa jasenen tiedot text areaan
     * @param os tietovirta
     * @param jasen jasenKohdalla
     * 
     * Siirrytään jasen luokkaan ja tulostetaan parametrina tuotu "valittu" jäsen. 
     * 
     * Otetaan käsittelyyn myös työtunnit.
     * Tehdään uusi lista TyötunnitHlo luokkaa mukaillen. Kutsutaan rekisterin annaTunnit aliohjelmaa ja viedään parametrina valittu jäsen. 
     * 
     * Saadaan viite jäsenen tunteihin ja tulostetaan ne kaikki.
     * 
     * Tässä halutaan tulostaa tämän jäsenen kaikki tunnit (koska niitä voi olla samalla jäsenellä useampiakin) tekstikenttään.
     * 
     * Nyt for each silmukka. Tulostetaan kaikki löytyneet tunnit rekisteristä saaduista jäsenen tunneista. tyyppi voisi olla myös var tun:nille.
     
    private void tulosta(PrintStream os, Jasen jasen) {
        os.println("---------------------------------"); 
        jasen.tulosta(os);
        os.println("---------------------------------");
        
        List<TyotunnitHlo> tunnit = rekisteri.annaTunnit(jasen);
         
        for (TyotunnitHlo tun : tunnit) // näin koska ei pysty tulostamaan listaa<tuntiolioista> voi tulostaa yhden olion.
            
            tun.tulosta(os);
    }*/
    
    

    

    

    
    /**
     * Ohjelman suunnitelma avautuu selaimeen
     * TOISTAISEKSI EI TOIMI, KUTSU ON KYLLÄKIN YLEMPÄNÄ.
     */
    /* TODO: Tee apua nappi
    private void apua() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2020k/ht/markkvmx");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    */
    
    
    /**
     * EI merkitystä enää.
     * kutsutaan mainista asti että saadaan paaikkuna auki. rekisterictrl.avaa()
     *  true jos tiedoston nimi pitää paikkansa
     * @return onko nimi oikein
     */
    public boolean avaa() {
        String uusinimi = EtusivuController.kysyNimi(null, rekisterinnimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }

    
    private String lueTiedosto(String nimi) {
        rekisterinnimi = nimi;
        //setTitle("rekisteri - " + rekisterinnimi);
        try {
            rekisteri.lueTiedostosta(nimi);
            hae(0);
            haeJasenet(ryhmaKohdalla);
            // haeTunnit(jasenKohdalla);
            // chooserJasenet.getSelectionModel().select(0);
            // naytaJasen();
            return null; 
        } catch (SailoException e) {
            //hae (0);
            String virhe = e.getMessage();
            return virhe;
        }
        
        
    }


    private String tallenna() {
        try {
            rekisteri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }


    /**
     * @return voi sulkea
     */
    public boolean voikoSulkea() {
        tallenna();
        /* try {
            rekisteri.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa tai sulkemisessa ongelmia " + e.getMessage());
        }*/
        return true;
    }


}
