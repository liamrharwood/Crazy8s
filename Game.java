package crazy8s;

import crazy8s.card.Card;
import static crazy8s.card.Card.CLUBS;
import static crazy8s.card.Card.DIAMONDS;
import static crazy8s.card.Card.HEARTS;
import static crazy8s.card.Card.SPADES;
import crazy8s.card.Deck;
import crazy8s.player.Command;
import crazy8s.player.Human;
import crazy8s.player.IPlayer;
import crazy8s.player.Opponent;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Game class which implements Crazy 8s play rules.
 *
 * @author Liam Harwood and Juan Vasquez
 */
public class Game {

    protected ArrayList<IPlayer> players;

    protected Deck deck;

    /**
     * Constructor
     */
    public Game() {
        players = new ArrayList<>();
        players.add(new Human());
        players.add(new Opponent());
        Collections.shuffle(players);
        deck = new Deck();
    }

    /**
     * Decodes integer value representing suit and converts it to a string
     *
     * @param suit Integer representing suit
     * @return Decoded string
     */
    protected String decodeSuit(int suit) {
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
     * Deals cards to both players
     */
    protected void deal() {
        for (int index = 0; index < 8; index++) {
            for (IPlayer player : players) {
                ArrayList<Card> hand = player.getHand();
                Card card = deck.draw();
                hand.add(card);
            }
        }
        Card card = deck.draw();
        deck.discard(card);
        for (IPlayer player : players) {
            player.played(player, card);
        }

    }

    /**
     * Runs the command to play a card
     *
     * @param player Player playing a card
     * @param index Index of card in hand to be played
     */
    protected void doPlay(IPlayer player, int index) {
        Card card = player.getHand().remove(index);
        System.out.println(player + " played " + card);
        if (card.getRank() == 8) {
            int suit = player.getSuit();
            for (IPlayer eachPlayer : players) {
                eachPlayer.setSuit(suit);
            }
            player.setSuit(suit);
            card.setSuit(suit);
            System.out.println(player + " changed discard suit to " + card.decodeSuit());
        }
        deck.discard(card);
        for (IPlayer eachPlayer : players) {
            eachPlayer.played(player, card);
        }
        if (player instanceof Human) {
            doShow(player);
        }

    }

    /**
     * Runs the command to draw a card
     *
     * @param player Player drawing a card
     */
    protected void doDraw(IPlayer player) {
        Card card = deck.draw();
        ArrayList<Card> hand = player.getHand();
        hand.add(card);
        for (IPlayer eachPlayer : players) {
            eachPlayer.drew(player, card);
        }
        System.out.println(player + " drew a card.");
        if (player instanceof Human) {
            doShow(player);
        }

    }

    /**
     * Runs the command to show discard card, score and cards in hand
     *
     * @param player Player giving command
     */
    protected void doShow(IPlayer player) {
        Card discard = new Card(deck.getDiscardRank(), deck.getDiscardSuit());
        System.out.println("Discard card is " + discard);

        System.out.println(player);
        System.out.println("Score: " + player.getScore());

        ArrayList<Card> hand = player.getHand();
        for (int index = 0; index < hand.size(); index++) {
            Card card = hand.get(index);
            System.out.println((index + 1) + ". " + card);
        }
    }

    /**
     * Runs the command to show discard card, score, cards in hand, number of
     * cards in deck, number of cards in discard pile, and number of cards in
     * other player's hand
     *
     * @param player Player giving command
     */
    protected void doRefresh(IPlayer player) {
        doShow(player);
        System.out.println("Number of cards in deck: " + deck.cards.size());
        System.out.println("Number of cards in discard pile: " + deck.discards.size());
        System.out.println("Number of cards in opponent's hand: " + player.getOPHS());

    }

    /**
     * Sums up the card values in losing player's hand at end of game to
     * determine score to be added to winner's score
     *
     * @param player Player who won
     * @return Score to be added
     */
    protected int sumScore(IPlayer player) {
        int score = 0;
        for (IPlayer p : players) {
            for (Card card : p.getHand()) {
                if (card.getRank() == 8) {
                    score += 50;
                } else if (card.getRank() > 9) {
                    score += 10;
                } else {
                    score += card.getRank();
                }

            }
        }

        return score;
    }

    /**
     * Checks if player goes out and wins the game, adds to their score
     *
     * @param player Player who played a card
     * @return 0 if they still have cards, 1 if they won the game
     */
    protected int checkOut(IPlayer player) {
        if (player.getHand().size() < 1) {
            System.out.println("Game over");
            int score = sumScore(player);
            System.out.println(player + " won and earned " + score + " points!");
            player.setScore(player.getScore() + score);
            for (IPlayer p : players) {
                System.out.println(p + " score:" + p.getScore());
            }
            return 1;
        }
        return 0;
    }

    /**
     * Starts a new game
     */
    protected void newGame() {
        Game game = new Game();
        game.go();
    }

    /**
     * Main game loop
     */
    protected void loop() {
        while (true) {
            playerLoop:
            for (IPlayer player : players) {
                do {
                    int command = player.getCommand();
                    switch (command) {
                        case Command.SHOW:
                            doShow(player);
                            break;
                        case Command.REFRESH:
                            doRefresh(player);
                            break;
                        case Command.DRAW:
                            doDraw(player);
                            break;
                        case Command.QUIT:
                            System.exit(0);
                        case Command.NO_COMMAND:
                            System.out.println("Invalid command");
                            continue;
                        default:
                            doPlay(player, command);
                            if (checkOut(player) == 1) {
                                newGame();
                            }
                            continue playerLoop;
                    }
                } while (true);
            }
        }
    }

    /**
     * Deals card, shows instructions and starts main game loop
     */
    public void go() {
        deal();
        System.out.println("New game:");
        System.out.println("Commands:");
        System.out.println("s = Shows discard card, your score, and cards in your hand");
        System.out.println("r = Does 's' command and shows number of cards in the deck, the discard pile, and the opponent's hand");
        System.out.println("d = Draws a card");
        System.out.println("q = Quits the game");
        System.out.println("Index number of card in hand = Plays that card");
        loop();

    }

    /**
     * Play the game
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        System.out.println("Welcome to Crazy 8s!");
        game.go();
    }

}
