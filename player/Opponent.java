package crazy8s.player;

import crazy8s.card.Card;
import java.util.ArrayList;

/**
 * This class implements the opponent with AI.
 *
 * @author Liam Harwood and Juan Vasquez
 */
public class Opponent implements IPlayer {

    ArrayList<Card> myhand = new ArrayList<>();

    private Card discarded;

    private int suit8;

    private int otherPlayerHandSize;

    private static int score;

    /**
     * Constructor
     */
    public Opponent() {
        otherPlayerHandSize = 8;
    }

    /**
     * Searches hand for a card of specified rank
     *
     * @param rank Integer representing the rank to search for
     * @return Index of card if found, -1 if no cards have the rank
     */
    protected int searchRank(int rank) {
        for (int index = 0; index < myhand.size(); index++) {
            Card card = myhand.get(index);
            if (rank == card.getRank() && card.getRank() != 8) {
                return index;
            }
        }
        return -1;

    }

    /**
     * Searches hand for a card of specified suit
     *
     * @param suit Integer representing the suit to search for
     * @return Index of card if found, -1 if no cards have the suit
     */
    protected int searchSuit(int suit) {
        for (int index = 0; index < myhand.size(); index++) {
            Card card = myhand.get(index);
            if (suit == card.getSuit() && card.getRank() != 8) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Searches hand for 8s
     *
     * @return Index of the card if 8 found, -1 if no cards are 8s
     */
    protected int search8s() {
        for (int index = 0; index < myhand.size(); index++) {
            Card card = myhand.get(index);
            if (card.getRank() == 8) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Uses AI to choose a suit when the opponent plays an 8
     *
     * @return Integer representing chosen suit
     */
    protected int chooseSuit() {
        // Part A. Count suits
        int[] counts = new int[5];
        for (Card card : myhand) {
            int suit = card.getSuit();
            if (card.getRank() != 8) {
                int count = counts[suit];
                count++;
                counts[suit] = count;
            }
        }

        // Part B. Find largest suit
        int maxCount = 0;
        int maxSuit = 0;
        for (int suit = 1; suit < counts.length; suit++) {
            if (counts[suit] > maxCount) {
                maxCount = counts[suit];
                maxSuit = suit;
            }
        }

        if (maxSuit == 0) {
            return Card.CLUBS;
        }

        return maxSuit;
    }

    /**
     * Uses AI to determine the actions of the opponent
     *
     * @return Integer representing command, or index of card to play
     */
    @Override
    public int getCommand() {
        int suit = 0;

        if (discarded.getRank() != 8) {
            int index = searchRank(discarded.getRank());
            if (index != -1) {
                return index;
            }
        } else {
            suit = suit8;
        }

        // If we get here, no rank matches
        if (suit == 0) {
            suit = discarded.getSuit();
        }

        int index = searchSuit(suit);
        if (index != -1) {
            return index;
        }

        index = search8s();
        if (index == -1) {
            return Command.DRAW;
        }

        return index;
    }

    /**
     * Gets the opponent's hand
     *
     * @return myhand Opponent's hand
     */
    @Override
    public ArrayList<Card> getHand() {
        return myhand;
    }

    /**
     * Reports who just played what
     *
     * @param player Player that played a card
     * @param card Card that was played
     */
    @Override
    public void played(IPlayer player, Card card) {
        this.discarded = card;
    }

    /**
     * Reports who drew what
     *
     * @param player Player who drew
     * @param card Card that was drawn
     */
    @Override
    public void drew(IPlayer player, Card card) {
        if (player != this) {
            this.otherPlayerHandSize++;
        }
    }

    /**
     * Gets the suit when opponent plays an 8
     *
     * @return Chosen suit as determined by chooseSuit()
     */
    @Override
    public int getSuit() {
        return chooseSuit();
    }

    /**
     * Sets internal suit knowledge
     *
     * @param suit Suit of discard card
     */
    @Override
    public void setSuit(int suit) {
        this.suit8 = suit;
    }

    /**
     * Gets the opponent's score
     *
     * @return score Opponent's score
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * Sets the opponent's score
     *
     * @param score Score to set to
     */
    @Override
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Converts me to a string
     *
     * @return "OPPONENT"
     */
    @Override
    public String toString() {
        return "OPPONENT";
    }

    /**
     * Gets the hand size of the opposing player
     *
     * @return otherPlayerHandSize Number of cards in other player's hand
     */
    @Override
    public int getOPHS() {
        return otherPlayerHandSize;
    }
}
