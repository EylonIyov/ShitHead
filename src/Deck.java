import java.util.*;

public class Deck {
    private static final Stack<Card> deck = new Stack<Card>();

    public static void fillDeck() { //fills the deck with a new set of cards, sorted
        int[] ranks = {2,3,4,5,6,7,8,9,10};
        for (int rank : ranks) {
            for (Card.Suit suit : Card.Suit.values()) {
                deck.push(new Card(rank, suit));
            }
        }
    }
    public static void fillDeck(Stack<Card> playingDeck) {
        while (!playingDeck.isEmpty()) {
            deck.push(playingDeck.pop());
        }
    }
    public static void shuffleDeck() {
        Collections.shuffle(deck);
    }
    public static Card deal() throws EmptyStackException {
        if (!deck.isEmpty()) {
            return deck.pop();
        }else{
            throw new EmptyStackException();
        }

    }
    public boolean IsEmpty() {
        return deck.empty();
    }
    public static Stack<Card> getDeck() {
        return deck;
    }
}
