package crazy8s.player;

import crazy8s.card.Card;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class implements a human player.
 *
 * @author Liam Harwood and Juan Vasquez
 */
public class Human implements IPlayer {

    private static int score;

    int suit;

    ArrayList<Card> myhand;

    BufferedReader br;

    private Card discarded;

    private int otherPlayerHandSize;

    /**
     * Constructor
     */
    public Human() {
        otherPlayerHandSize = 8;
        try {
            myhand = new ArrayList<>();

            br = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            System.err.println("constructor failed: " + e);
        }
    }

    /**
     * Gets the encoded command from the player. That is, the player provides
     * some input in the form of "s", "r", "d", "q", or a number string "1",
     * "2", ... "52", and this method converts this input into an integer. The
     * letter commands are in the class, Command. A number represents itself as
     * an index in the player's hand.
     *
     * @return Encoded command
     */
    @Override
    public int getCommand() {
        if (br == null) {
            return 0;
        }

        try {
            // Show the command prompt

            System.out.print("Input command => ");

            String input = br.readLine();

            int cmd = encodeCommand(input);

            return cmd;
        } catch (Exception e) {
            System.err.println(e);
        }

        return Command.NO_COMMAND;
    }

    /**
     * Encodes a command string.
     *
     * @param input User input to encode
     * @return <0 if encoding fails, 0 to (n-1) if card index, >52 if other
     * command
     * @see Command
     */
    protected int encodeCommand(String input) {
        input = input.trim().toLowerCase();

        switch (input) {
            case "s":
                return Command.SHOW;
            case "r":
                return Command.REFRESH;
            case "d":
                if (validateDraw() == 0) {
                    System.out.println("You have a valid play.");
                    return Command.NO_COMMAND;
                }
                return Command.DRAW;
            case "q":
                return Command.QUIT;
            case "ron":
                System.out.println("YOU WIN!!!!!!!");
                System.exit(0);
            default:
                for (int index = 1; index <= myhand.size(); index++) {
                    String indexs = index + "";

                    if (input.equals(indexs)) {
                        if (validatePlay(myhand.get(index - 1)) == 1) {
                            return index - 1;
                        } else {
                            System.out.println("Neither rank nor suit matches");
                            return Command.NO_COMMAND;
                        }
                    }

                }
                break;
        }

        return Command.NO_COMMAND;
    }

    /**
     * Makes sure the user does not have a valid play before drawing
     *
     * @return 0 if the user has a valid play, 1 if they do not
     */
    protected int validateDraw() {

        if (searchRank(discarded.getRank()) != -1) {
            return 0;
        }
        if (searchSuit(discarded.getSuit()) != -1) {
            return 0;
        }
        if (search8s() != -1) {
            return 0;
        }
        return 1;

    }

    /**
     * Checks that the user is playing a valid card
     *
     * @param card The card that the user is playing
     * @return 0 if the play is not valid, 1 if the play is valid
     */
    protected int validatePlay(Card card) {
        if (card.getRank() != 8) {
            if (card.getRank() != discarded.getRank() && card.getSuit() != discarded.getSuit()) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * Searches for cards of a particular suit in the player's hand
     *
     * @param suit Suit to search for
     * @return Index of card in hand if it has the suit, -1 if no cards have the
     * suit
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
     * Searches for cards of a particular rank in the player's hand
     *
     * @param rank Rank to search for
     * @return Index of card in hand if it has the rank, -1 if no cards have the
     * rank
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
     * Searches for 8s in the player's hand
     *
     * @return Index of card in hand if it is an 8, -1 if there are no 8s
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
     * Encode the user's string input (c, d, h, or s) when choosing a suit after
     * playing an 8, converting it to an integer defined in the Card class
     *
     * @param input User input for suit to be encoded
     * @return Encoded suit
     */
    protected int encodeSuit(String input) {
        input = input.trim().toLowerCase();

        switch (input) {
            case "c":
                return Card.CLUBS;
            case "d":
                return Card.DIAMONDS;
            case "h":
                return Card.HEARTS;
            case "s":
                return Card.SPADES;
            default:
                System.out.println("Please input a valid suit.");
                break;
        }

        return Command.NO_COMMAND;
    }

    /**
     * Gets the user's hand
     *
     * @return myhand
     */
    @Override
    public ArrayList<Card> getHand() {
        return myhand;
    }

    /**
     * Reports who just played what and decrease OPHS if necessary
     *
     * @param player Play who played this card.
     * @param card Card played by the player.
     */
    @Override
    public void played(IPlayer player, Card card) {
        this.discarded = card;
        // Did the other player play a card?
        if (player != this) {
            this.otherPlayerHandSize--;
        }
    }

    /**
     * Reports who drew from deck and increases OPHS if necessary
     *
     * @param player Player who drew this card.
     * @param card Card that was drawn
     */
    @Override
    public void drew(IPlayer player, Card card) {
        // Did the other player draw?
        if (player != this) {
            this.otherPlayerHandSize++;
        }
    }

    /**
     * Gets suit if 8 was played.
     *
     * @return Encoded integer value for suit
     */
    @Override
    public int getSuit() {

        try {
            do {
                System.out.print("Input suit (c, d, h, s) => ");
                String input = br.readLine();
                int suit = encodeSuit(input);
                if (suit == Command.NO_COMMAND) {
                    continue;
                }
                return suit;
            } while (true);

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;

    }

    /**
     * Sets suit if 8 was played.
     *
     * @param suit Suit to set to
     */
    @Override
    public void setSuit(int suit) {
        this.suit = suit;
    }

    /**
     * Converts me to a string
     *
     * @return "HUMAN"
     */
    @Override
    public String toString() {
        return "HUMAN";
    }

    /**
     * Gets the player's score
     *
     * @return score The player's score
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score
     *
     * @param score Score to be set to
     */
    @Override
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the hand size of the opposing player
     *
     * @return otherPlayerHandSize Number of cards in opponent's hand
     */
    @Override
    public int getOPHS() {
        return otherPlayerHandSize;
    }

    /**
     * Test the human player
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Human player = new Human();

        while (true) {
            Integer command = player.getCommand();
            System.out.println(player + ": sent encoded command " + command);

            if (command == Command.QUIT) {
                break;
            }
        }
    }

}
