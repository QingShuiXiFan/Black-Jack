/*
 * @Description: In User Settings Edit
 * @Author: Jun Li
 * @Date: 2019-09-22 18:07:57
 * @LastEditTime: 2019-09-28 15:23:32
 * @LastEditors: Please set LastEditors
 */
package bj;

public class Dealer extends Person{
    private Card[] cardsInHand = {};
     
    public Dealer(int ID, int balance){
        super(ID, balance);
    }

    /**deal method and auto hit method*/
    /** Once the Player stands, the dealer reveals their face down card to
    the Player, and continues to hit until the hand value of the Dealer
    reaches or exceeds 17.*/
    public void autoHit(Cards cards){
        // if natural blackjack
        if(Judge.isNaturalBJ(cardsInHand) == true){
            System.out.println("Dealer has NATURAL BLACKJACK!!!");
            return;
        }

        //calculate sum value of cards in hand
        int valueSum = getCardsValue();

        int aceCount = Judge.aceCount(cardsInHand);
        while(valueSum < 17){
            // hit
            cardsInHand = add(cardsInHand, cards.pop(1));
            valueSum += cardsInHand[cardsInHand.length-1].getValue();

            //if receive 'A'
            if(cardsInHand[cardsInHand.length-1].getValue() == 1 && valueSum + 10 <=21 && valueSum + 10 >= 17){
                valueSum += 10;
            }
            else aceCount++;

            // if receive a card that leads to bust, then treat Ace as 1 until valueSum does not exceeds 21 or no more Ace left
            while(valueSum > 21 && aceCount>0){
                valueSum -= 10;
                aceCount--;
            }

            printCardsInHand();
        }
    }

    @Override
    //*************************************************************************** */
    //calculate sum value of cards in hand
    public int getCardsValue(){
        int valueSum = 0;
        for(int i=0; i<cardsInHand.length; i++){
            valueSum += cardsInHand[i].getValue();
        }

        // get number of Ace, to get maximum sum if has Ace's in hand
        int count = Judge.aceCount(cardsInHand);
        if(count>=1) valueSum += 10;
        
        return valueSum;
    }

    //add one card
    public void addCard(Card card){
        this.cardsInHand = add(cardsInHand , card);
    }

    public Card[] getCards(){
        return cardsInHand;
    }

    //print what the dealer has in certain hand
    public void printCardsInHand(){
        System.out.print("Dealer's cards in hand: ");
        for(int i=0;i<cardsInHand.length;i++){
            System.out.print(cardsInHand[i].getSuit()+cardsInHand[i].getRealValue() + " ");
        }
        System.out.println();
    }

    //*************************************************************************** */

    //This method will add an element to an array and return the resulting array
    //add an item to an array
    public static Card[] add(Card[] arr, Card element){
        Card[] tempArr = new Card[arr.length+1];
        System.arraycopy(arr, 0, tempArr, 0, arr.length);
        
        tempArr[arr.length] = element;
        return tempArr;
    }
}