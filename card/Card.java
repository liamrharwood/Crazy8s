package crazy8s.card;

/**
 * Represents a playing card.
 *
 * @author Liam Harwood and Juan Vasquez
 */
public class Card implements ICard {

    public final static int CLUBS = 1;

    public final static int DIAMONDS = 2;

    public final static int HEARTS = 3;

    public final static int SPADES = 4;

    int rank;

    int suit;

    /**
     * Constructor
     *
     * @param rank Integer representing rank of card
     * @param suit Integer representing suit of card
     */
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Gets the rank of the card
     *
     * @return rank Integer representing rank of card
     */
    @Override
    public int getRank() {
        return rank;
    }

    /**
     * Gets the suit of the card
     *
     * @return suit Integer representing suit of card
     */
    @Override
    public int getSuit() {
        return suit;
    }

    /**
     * Sets the suit of the card
     *
     * @param suit Suit to be set to
     */
    public void setSuit(int suit) {
        this.suit = suit;
    }

    /**
     * Converts me to a string
     *
     * @return String saying rank and suit of card
     */
    @Override
    public String toString() {
        return decodeRank() + " of " + decodeSuit();
    }

    /**
     * Decodes integer values representing rank and converts it to a string
     *
     * @return String of the card's rank
     */
    public String decodeRank() {
        if (rank == 1) {
            return "Ace";
        }

        if (rank == 11) {
            return "Jack";
        }

        if (rank == 12) {
            return "Queen";
        }

        if (rank == 13) {
            return "King";
        }

        if ((rank >= 2) && (rank <= 10)) {
            return rank + "";
        }

        return "";
    }

    /**
     * Decodes integer values representing suit and converts it to a string
     *
     * @return String of the card's suit
     */
    public String decodeSuit() {
        if (suit == CLUBS) {
            return "Clubs";
        }

        if (suit == DIAMONDS) {
            return "Diamonds";
        }

        if (suit == HEARTS) {
            return "Hearts";
        }

        if (suit == SPADES) {
            return "Spades";
        }

        return "";
    }

    /**
     * Test the card class
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Card card = new Card(2, 1);
        System.out.println(card);

        Card card2 = new Card(11, 2);
        System.out.println(card2);

        Card card3 = new Card(1, 4);
        System.out.println(card3);

    }
}
