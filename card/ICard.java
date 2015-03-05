package crazy8s.card;

/**
 * Interface for all cards.
 *
 * @author Liam Harwood and Juan Vasquez
 */
public interface ICard {

    /**
     * Gets the card's rank
     *
     * @return An integer representing the card rank
     */
    public int getRank();

    /**
     * Gets the card's suit.
     *
     * @return An integer representing the card suit
     */
    public int getSuit();
}
