import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;
import java.util.Scanner;


public class OpponentHand implements Hand {
    ArrayList<Card> my_cards = new ArrayList<Card>();
    Card[] Visible = new Card[2];
    Card[] Hidden = new Card[2];
    Stack<Card> play_stack = new Stack<>();
    Deck deck = new Deck();

    public OpponentHand(Stack<Card> PlayingStack) {
        this.my_cards =new ArrayList<>();
        this.Visible = new Card[2];
        this.Hidden = new Card[2];
        this.play_stack = PlayingStack;
    }
    @Override
    public void addCard(Card card){
        my_cards.add(card);
        my_cards.sort(Comparator.comparingInt(Card::getRank));
    }

    public Card playCard() {

        return play_stack.pop();
    }

    @Override
    public void addCards(Stack<Card> stack){
        my_cards.addAll(stack);
    }

    @Override
    public void dealCards() {

    }


}