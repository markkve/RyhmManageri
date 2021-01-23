package ryhma;



/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author Veikko M, Roope T
 * @version 10.03
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
