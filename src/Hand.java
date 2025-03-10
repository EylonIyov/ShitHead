import java.util.ArrayList;
import java.util.Stack;

interface Hand {
    ArrayList<Card> my_cards = new ArrayList<>();
    Card[] Visible = new Card[2];
    Card[] Hidden = new Card[2];
    Stack<Card> play_stack = new Stack<>();
    Deck deck = null;

    void addCard(Card card);
    void addCards(Stack<Card> cards);
    void dealCards();



}
