package crazy8s.card;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a card deck.
 *
 * @author Liam Harwood and Juan Vasquez
 */
public final class Deck {

    int rank;

    int suit;

    Random ran = new Random();

    public ArrayList<Card> cards = new ArrayList<>();

    public ArrayList<Card> discards = new ArrayList<>();

    /**
     * Constructor
     */
    public Deck() {
        for (int r = 1; r <= 13; r++) {
            for (int s = 1; s <= 4; s++) {
                Card card = new Card(r, s);
                cards.add(card);

            }
        }

        this.shuffle();
    }

    /**
     * Draws a card from the deck
     *
     * @return card Card that is drawn
     */
    public Card draw() {
        if (!(cards.isEmpty())) {
            Card card = cards.remove(0);
            return card;
        }
        for (int index = discards.size() - 1; index > 0; index--) {
            Card card = discards.remove(index);
            cards.add(card);
        }
        shuffle();
        Card card = cards.remove(0);
        return card;
    }

    /**
     * Puts a card on top of the discard pile
     *
     * @param card Card to be discarded
     */
    public void discard(Card card) {
        discards.add(0, card);
        rank = card.rank;
        suit = card.suit;
    }

    /**
     * Gets the rank of the discard card
     *
     * @return rank Integer representing discard card rank
     */
    public int getDiscardRank() {
        return rank;
    }

    /**
     * Gets the suit of the discard card
     *
     * @return suit Integer representing discard card suit
     */
    public int getDiscardSuit() {
        return suit;
    }

    /**
     * Sets the suit of the discard card
     *
     * @param suit Suit to set it to
     */
    public void setDiscardSuit(int suit) {
        this.suit = suit;
    }

    /**
     * Shuffles the deck
     */
    public void shuffle() {
        for (int index = 0; index < cards.size(); index++) {
            Card card1 = cards.get(index);

            int lottery = ran.nextInt(cards.size());

            Card card2 = cards.get(lottery);

            cards.set(index, card2);

            cards.set(lottery, card1);
        }
    }

    /**
     * Converts me to a string
     *
     * @return String listing cards in the deck
     */
    @Override
    public String toString() {
        String s = "";
        for (int index = 0; index <= 10; ++index) {
            Card card = cards.get(index);

            String t = card + "\n";

            s = s + (index + 1) + ". " + t;
        }
        return s;
    }

    /**
     * Test the deck
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck);
        System.out.println(deck.discards);
    }
}
