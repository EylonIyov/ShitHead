import java.util.*;

/**
 * MyHand class implements the Hand interface for a card game.
 * This class represents a player's hand in a card game similar to the game "Palace" or "Shed",
 * where players have visible and hidden cards, and try to play all their cards on a common play stack.
 */
public class MyHand implements Hand {
    /**
     * List to store the player's hand of cards
     */
    private ArrayList<Card> my_cards = new ArrayList<Card>();

    /**
     * Array to store visible cards that can be played after the hand is empty
     */
    protected Card[] Visible = new Card[2];

    /**
     * Array to store hidden cards that can only be played after visible cards
     */
    protected Card[] Hidden = new Card[2];

    /**
     * Reference to the shared play stack where cards are played
     */
    final Stack<Card> play_stack;

    /**
     * Reference to the deck from which cards are drawn
     */
    final Deck deck;

    /**
     * Constructor for MyHand class.
     * Initializes the player's hand with references to the play stack and deck.
     *
     * @param play_stack The shared stack where cards are played
     * @param deck       The deck from which cards are drawn
     */
    public MyHand(Stack<Card> play_stack, Deck deck) {
        this.my_cards = new ArrayList<>();
        this.Visible = new Card[2];
        this.Hidden = new Card[2];
        this.play_stack = play_stack;
        this.deck = deck;
    }

    /**
     * Adds a single card to the player's hand and sorts cards by rank.
     *
     * @param card The card to add to the hand
     */
    @Override
    public void addCard(Card card) {
        my_cards.add(card);
        my_cards.sort(Comparator.comparingInt(Card::getRank));
    }


    //TODO: Create a method for dealing the initial stack

    /**
     * Attempts to play a card from the player's hand.
     * Displays the current state, asks for user input, and executes the appropriate action.
     * If a valid card is chosen, it's played to the stack.
     * If the player chooses 0, they take the entire deck.
     * If the hand size drops below 3, a new card is drawn if the deck is not empty.
     *
     * @return true if an action was performed successfully, false otherwise
     */
    public boolean playCardFromHand() {
        System.out.println("Last card that was played:" + play_stack.peek());
        printCards();
        System.out.println("Choose a card to play, enter '0' to take the entire stack");
        try (Scanner scanner = new Scanner(System.in)) {
            int choice = scanner.nextInt();
            if (choice == 0) {
                addCards(Deck.getDeck());
                return true;
            }
            Card choshenCard = my_cards.get(choice);
            if (verifyCard(choshenCard)) {
                play_stack.push(choshenCard);
                my_cards.remove(choshenCard);
            }
            if (my_cards.size() < 3 && !Deck.getDeck().isEmpty()) {
                addCard(Deck.getDeck().pop());
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * Attempts to play a card from the visible cards.
     * Shows the last played card and the visible cards, then takes user input.
     * If the player chooses 0, they take the entire play stack.
     * If they choose a valid card, it's played and the slot is set to null.
     *
     * @return true if a card was successfully played, false otherwise
     */
    public boolean playCardFromVisibleHand() {
        System.out.println("Last card that was played:" + play_stack.peek());
        System.out.println(Arrays.toString(this.Visible));
        try (Scanner scanner = new Scanner(System.in)) {
            int choice = scanner.nextInt();
            if (choice == 0) {
                addCards(play_stack);
            } else {
                Card choshenCard = Visible[choice];
                verifyCard(choshenCard);
                play_stack.push(choshenCard);
                Visible[choice] = null;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
        return true;
    }
    /**
     * Attempts to play a card from the hidden cards.
     * Shows the last played card and the visible cards, then takes user input.
     * If the card can be played, puts it in the playing stack, otherwise the player takes the entire playing stack.
     * If they choose a valid card, it's played and the slot is set to null.
     *
     * @return true if a card was successfully played, false otherwise
     */

    public boolean playCardFromHiddenHand() {
        System.out.println("Last card that was played:" + play_stack.peek());
        System.out.println("Choose a hidden card to play, if the card isn't suitable, the entire playing stack will be taken");
        System.out.println(Arrays.toString(this.Hidden));
        try (Scanner scanner = new Scanner(System.in)) {
            int choice = scanner.nextInt();
            Card choshenCard = Hidden[choice];
            choshenCard.setVisibility(true);
            try{
                verifyCard(choshenCard);
                play_stack.push(choshenCard);
                Hidden[choice] = null;
            }catch (Exception e){
                addCard(choshenCard);
                addCards(play_stack);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
        return true;
    }



    /**
     * Adds all cards from a stack to the player's hand and sorts them by rank.
     * Used when a player cannot play a valid card and must take the stack.
     *
     * @param stack The stack of cards to add to the player's hand
     */
    @Override
    public void addCards(Stack<Card> stack) {
        my_cards.addAll(stack);
        my_cards.sort(Comparator.comparingInt(Card::getRank));
    }

    @Override
    public void dealCards() {
        for (int i = 0 ; i < 2 ; i++) {
            Hidden[i] = Deck.deal();
            Hidden[i].setVisibility(false);
            Visible[i] = Deck.deal();
            my_cards.add(Deck.deal());
        }
        try(Scanner scanner = new Scanner(System.in)) {
            System.out.println("Would you like to switch any of the cards? if not simply press 0");
            int choice = scanner.nextInt();
            while (scanner.nextInt() != 0) {
                printCards();
                if (scanner.nextInt() == 0) {
                    break;
                }
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    /**
     * Displays the current state of the player's hand.
     * Shows all cards in hand, visible cards, and hidden cards.
     */
    public void printCards() {
        System.out.println("Current hand:" + my_cards);
        System.out.println("Visible cards:" + Arrays.toString(Visible));
        System.out.println("Hidden cards:" + Arrays.toString(Hidden));
    }

    /**
     * Verifies if a card can be legally played on the current play stack.
     * The rules include:
     * - Cards must generally be of higher rank than the current top card
     * - If the top card is a 7, the next card must be lower
     * - Cards with rank 2 can be played on any card
     * - Cards with rank 10 can be played on any card and clear the stack
     *
     * @param card The card to verify
     * @return true if the card can be played, otherwise throws an exception
     * @throws Exception If the card cannot be legally played
     */
    protected boolean verifyCard(Card card) throws Exception {
        if ((card.getRank() != 2 || card.getRank() != 10) && card.getRank() < play_stack.peek().getRank()) {
            throw new Exception("This is not a valid card, you need to choose a card of higher rank");
        }
        if ((card.getRank() != 2 || card.getRank() != 10) && play_stack.peek().getRank() == 7 && card.getRank() > play_stack.peek().getRank()) {
            throw new Exception("This is not a valid card, over a 7, you need to place a lower card");
        }
        if (card.getRank() == 2) {
            return true;
        } else if (card.getRank() == 10) {
            play_stack.empty();
            return true;
        }
        return true;
    }

    /**
     * Draws a single card from the deck and adds it to the player's hand.
     * If the deck is empty, it takes the play stack (except the top card),
     * puts it back into the deck, shuffles the deck, and places the top card back.
     */
    public void drawCard() {
        try {
            addCard(Deck.deal());
        }
        catch (Exception e) {
            Card LastCard = play_stack.pop();
            Deck.fillDeck(play_stack);
            play_stack.add(LastCard);
            Deck.shuffleDeck();
        }
    }
}