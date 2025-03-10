public class Card {
    private int rank;
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;
    }
    private Suit suit;
    private boolean faceUp;

    public Card() {

    }

    public Card(int rank, Suit suit) {//Constructor for a new card
        this.rank = rank;
        this.suit = suit;
        faceUp = true;
    }
    public Card(int rank, Suit suit, boolean faceUp) {//Empty constructor
        this.rank = rank;
        this.suit = suit;
        this.faceUp = faceUp;
    }
    public void setVisibility(boolean isVisable) {//Constructor used to define the hidden cards
        this.faceUp = isVisable;
    }

    public int getRank() {
        return rank;
    }
    public Suit getSuit() {
        return suit;
    }
    public String toString(){
        if (faceUp){
            return   rank + " of " + suit;
        }
        return "Hidden";
    }
}


