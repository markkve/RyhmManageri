/**
 * 
 */
package ryhma;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * KESKEN
 * 
 * Luokka yhden henkilön työtunneille.
 * 
 * Henkilön tunteihin kuuluu tavoitetunnit, toteutuneet tunnit, tunnusNro ja jäsenenNro.
 * Luokassa on kattava testipääohjelma.
 * @author Veikko M, Roope T
 * @version 5 Mar 2020
 *
 */
public class TyotunnitHlo {
    
    private int tunnusNro; //työtunnnille tunnus jotta jäsen osaa ottaa omansa
    private int jasenenNro; //jasenen tunnus jotta saadaan tietää kenelle kuuluu // tuodaan aina parametrina täällä
    private int [] tavoite; //tavoite tuntimäärä taulukossa
    private int [] tosi; //toteutunut tuntimäärä taulukossa
    
    private static int seuraavaNro = 1;
    
    
    /**
     * oletus muodostaja joka ei ole oikeastaan tarpeen koska javassa jos luokkaan ei kirjotia yhtään muodostajaa
     * niin siellä on aina automaattisesti olemassa oletusmuodostaja.
     * Tarvitaan vain testejä varten.
     */
    public TyotunnitHlo () {
        //
    }
    
    
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                getJasenenNro() + "|" +
                Arrays.toString(tavoite) + "|" +
                Arrays.toString(tosi);
    }
    
    
    /**
     * @param rivi yksi rivi tiedostosta
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        tunnusNro = Mjonot.erota(sb, '|', getTunnusNro());
        jasenenNro = Mjonot.erota(sb, '|', getJasenenNro());
        tavoite = tulkitse(Mjonot.erota(sb, '|'));
        tosi = tulkitse(Mjonot.erota(sb, '|'));
    }
    
    
    /**
     * Tulkitse metodi tunneille. (Tiedostosta luku)
     * @param t tutkittava taulukko
     * @return palauttaa tulkitun taulukon
     * @example
     * <pre name="test">
     * String t = "[2, 5, 4, 6, 9]";
     * tulkitse(t) === [2, 5, 4, 6, 9];
     * </pre>
     */
    public int[] tulkitse(String t) {
        StringBuilder sb = new StringBuilder(t);
        Mjonot.erota(sb,'['); // Poistetaan listan ensimmäinen hakasulku, muuten taulukkoon tulee pelkkiä nollia
        ArrayList<Integer> tunnit = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            Mjonot.erota(sb, ','); // Erotetaan pilkut, samasta syystä kuin '[' kohdalla.
            // poistetaan välimerkit taulukosta(?)
            int tunti = Mjonot.erotaInt(sb, 0);
            tunnit.add(tunti);
        }
        
        int[] tuntitaulu = new int[tunnit.size()];
        for (int i=0; i < tuntitaulu.length; i++)
        {
            tuntitaulu[i] = tunnit.get(i).intValue();
        }
        return tuntitaulu;
    }
    
    
    /**
     * @param jasenenNro henkilön tunnus numero
     * 
     * pitää muistaa että tässä ollaan jo yhden henkilön tuntipaketin kohdalla. 
     * tämän henkilön jäsenen numero tuodaan tänne kun luodaan nämä tunnit jäsenelle.
     * 
     * TÄTÄ tarvitaan varsinaista ohjelmaa varten koska toiminta vaati että työtunnitHlo tietää kuka on HLÖ
     * jolle ollaan luomassa uutta tuntipakettia.
     * muuten ei osata asettaa oikeaa tunnusta.
     * 
     * Tätä kutsutaan parametrillä jäsenen numero, tyyppiä int.
     */
    public TyotunnitHlo (int jasenenNro) {
        this.jasenenNro = jasenenNro;
    }
    
    
    /**
     * Metodi jolla asetetaan jnumerolla leimattuun tuntipakettiin taoitetunnit.
     * 
     * @param jnumero jäsenen numero parametrina että saadaan annettua oikealle jäsenelle tunnit
     * 
     * Kun tätä kutsutaan seuraa seuraavaa:
     * asetetaan jäsenenNumero = parametrin arvona tuoduksi (jnumero), koska on kyseessä yksilö.
     * Asetetaan tavoitetuntien taulukon pituudeksi 5
     * käydään for loopilla taulukko läpoi jä lyödään random arvot tunneille väliltä 1 - 10
     * Ei palauta mitään asettaa vain tavoite taulukolle arvot.
     * 
     * arvot näkyy kun tulostetaan jäsen.
     * eli nyt ASETETAAN tälle jäsenelle tunti taulukon arvot
     * ja kun tulostetaan mennään tämän jäsenen tuntien kohdalle
     * ja katsotaan mitä taulukko näyttää.
     */
    public void vastaaTavoiteTyotunnit(int jnumero) {
        jasenenNro = jnumero;
        tavoite = new int [5];
        
        for (int i = 0; i < tavoite.length; i++) {
            int rand = (int) Math.round(Math.random() * 10);
            tavoite[i] = rand;
          }
        
    }
    
    
    /**
     * Metodi jolla asetetaan jnumerolla leimattuun tuntipakettiin toteutuneet tunnit.
     * 
     * @param jnumero jäsenen numero parametrina että saadaan annettua oikealle jäsenelle tunnit
     * 
     * sama kaava kuin yllä.
     */
    public void vastaaTosiTyotunnit(int jnumero) {
        jasenenNro = jnumero;
        tosi = new int [5];
        
        for (int i = 0; i < tosi.length; i++) {
            int rand = (int) Math.round(Math.random() * 10);
            tosi[i] = rand;
          }
        
    }
   
    
    /**
     * tulostetaan työtunnit henkilolle
     * @param out tietovirtan johon tulsotetaan
     */
    public void tulosta(PrintStream out) {
        //out.println("Tavoite tunnit: " +"\n"+ Arrays.toString(tavoite)); //+"\n"+ "Toteutuneet tunnit: "+ "\n"+Arrays.toString(tosi));
        out.println(Arrays.toString(tavoite));
    }
    
    
    
    /**
     * Tulosta metodi toteutuneille tunneille
     * @param out tulostetaan todelliset tunnit
     */
    public void tulostaT(PrintStream out) {
        //out.println("Toteutuneet tunnit: "+ "\n"+Arrays.toString(tosi));
        out.println(Arrays.toString(tosi));
    }

    
    /**
     * Tulosta metodi
     * @param os tietovirta johon tulsotetaan
     */
    public void tulostaT(OutputStream os) {
        tulostaT(new PrintStream(os));
    }
    
    
    /**
     * @param os tietovirta johon tulsotetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    
    /**
     * Tuntien rekisteröinti
     * Testit samat kuin jäsenellä.
     * @return rekisteeroidaan henmkilon tunnit
     * huomaan että jostain systä tunnusnumero on nolla vaikka oletan että pitäisi olla 1... Miksi. 
     * Tosin tällähetkellä epäilen ettei tällä ole käyttöä muutenkaan. Jos muistan.
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * getter 
     * 
     * @return tarvitaan jasenen numero myös että osataa sylkeä oikeaan paikkaan
     * 
     * kutsutaan työtunnit javasta kun halutaan katsoa kenellä jäsenellä ollaan tulostamassa tunteja
     * 
     * tämä saa  arvonsa työtunnitHlo (int jasenen numero)
     * @example
     * <pre name="test">
     * Jasen jasen = new Jasen();
     * jasen.rekisteroi();
     * jasen.getTunnusNro() === 1;
     * </pre>
     */
    public int getJasenenNro() {
        return jasenenNro;
    }
    
    
    /**
     * getter 
     * 
     * @return tuntipaketin oma tunnus
     * 
     */
    public int getTunnusNro() {
        return tunnusNro;
    }

    
    /**
     * Testi - Pääohjelma TyötunnitHlo luokalle
     * Testataan:
     *      Uuden jäsenen luonti
     *          rekisteröinti
     *          täyttö(param (1)) eli ryhmän numerolla 1
     *      Uuden TyotunnitHlo olion luonti, kekeduuni. Parametrina viedään äskeisen jäsenen tunnusNro getterillä
     *          Täytetään TavoiteTunnit kekduunille
     *          tulostetaan
     *          rekisteröidään kekeduuni
     *      Printataan:
     *          keke.getRyhmanNro(), keke.getTunnusNro(), kekenduuni.getJasenenNro(),  kekenduuni.getTunnusNro()
     *      Luodaan uusi TyötunnitHlo olio : hlotu
     *          asetetaan tavoitetunnit parametrilla 2
     *          asetetaan toteutuneet tunnit parametrilla 2
     *          tulostetaan
     *          tulostetaan hlotu.getTunnusnumero. (Ei rekisteröity)
     *          rekisteröidään
     *          uudestaan hlotu.getTunnusnumero
     *      Uusi tyotunnitHlo olio
     *          täyttö
     *          tulostus
     *          tunnusnumeron haku === taas 0
     *      Uusi työtunnitHlo olio
     *          rekisteröinti
     *          täyttö(2)
     *          tulostus
     *          tunnusnumeron haku
     *      
     *      
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
    // TODO Auto-generated method stub
    // eli nyt kun ajaa huomataan että pakettien (hlotu:t) tunnus numerot ovat 0. Niitä ei olla rekisteröity.
    // jolloin oletus muodostaja alustaa sen arvoksi 0.
        Jasen keke = new Jasen();
        keke.rekisteroi();
        keke.taytaAkuTiedoilla(1);
        TyotunnitHlo kekenduuni = new TyotunnitHlo(keke.getTunnusNro());
        kekenduuni.vastaaTavoiteTyotunnit(keke.getTunnusNro());
        kekenduuni.tulosta(System.out);
        kekenduuni.rekisteroi();
        System.out.println(keke.getRyhmanNro() + " - " + keke.getTunnusNro() + " - " + kekenduuni.getJasenenNro() + " - " + kekenduuni.getTunnusNro());
        // yllä testatataan saako kekenduuni tietoon oman jäsenensä tunnusnumeron.
        System.out.println("\n");
        
        TyotunnitHlo hlotu = new TyotunnitHlo();
        
        hlotu.vastaaTavoiteTyotunnit(2);
        hlotu.vastaaTosiTyotunnit(2);
        hlotu.tulosta(System.out);
        System.out.println(hlotu.getTunnusNro() + " :tulee nolla ei rekisteröintiä");
        hlotu.rekisteroi();
        System.out.println(hlotu.getTunnusNro());
        
        TyotunnitHlo hlotu1 = new TyotunnitHlo();
        hlotu1.vastaaTavoiteTyotunnit(2);
        hlotu1.vastaaTosiTyotunnit(2);
        hlotu1.tulosta(System.out);
        System.out.println(hlotu1.getTunnusNro());
        
        TyotunnitHlo hlotu2 = new TyotunnitHlo();
        hlotu2.rekisteroi();
        hlotu2.vastaaTavoiteTyotunnit(2);
        hlotu2.vastaaTosiTyotunnit(2);
        hlotu2.tulosta(System.out);
        System.out.println(hlotu2.getTunnusNro());
    }




}
