package crazy8s.player;

import crazy8s.card.Card;
import java.util.ArrayList;

/**
 * Interface for both human and computer player
 *
 * @author Liam Harwood and Juan Vasquez
 */
public interface IPlayer {

    /**
     * Gets the other player's hand size
     *
     * @return An integer representing the number of cards in the other player's
     * hand
     */
    public int getOPHS();

    /**
     * Gets a command from the player.
     *
     * @return Encoded command
     */
    public int getCommand();

    /**
     * Gets the player's hand.
     *
     * @return Player's hand.
     */
    public ArrayList<Card> getHand();

    /**
     * Reports who played what.
     *
     * @param player Player making this play.
     * @param card Card they played.
     */
    public void played(IPlayer player, Card card);

    /**
     * Reports who drew from deck.
     *
     * @param player Player drawing a card.
     * @param card Card drawn from deck.
     */
    public void drew(IPlayer player, Card card);

    /**
     * Gets the 8s suit.
     *
     * @return Integer representing the suit.
     */
    public int getSuit();

    /**
     * Sets the 8s suit.
     *
     * @param suit 8s suit
     */
    public void setSuit(int suit);

    /**
     * Gets the player's score
     *
     * @return Score
     */
    public int getScore();

    /**
     * Sets the player's score
     *
     * @param score New score
     */
    public void setScore(int score);

}
