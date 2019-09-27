/*
 * @Description: In User Settings Edit
 * @Author: Jun Li
 * @Date: 2019-09-22 18:07:57
 * @LastEditTime: 2019-09-24 19:22:32
 * @LastEditors: Please set LastEditors
 */
package bj;

class Dealer extends Person{
    private Card[] cardsInHand = {};
     
    public Dealer(int ID, int balance){
        super(ID, balance);
    }

    /**deal method and auto hit method*/
    /** Once the Player stands, the dealer reveals their face down card to
    the Player, and continues to hit until the hand value of the Dealer
    reaches or exceeds 17.*/
    public void autoHit(Cards cards){
        //calculate sum value of cards in hand
        int valueSum = getCardsValue();

        while(valueSum < 17){
            // hit
            cardsInHand[cardsInHand.length] = cards.pop();
            valueSum += cardsInHand[cardsInHand.length-1].getValue();
        }
    }

    @Override
    //calculate sum value of cards in hand
    public int getCardsValue(){
        int valueSum = 0;
        for(int i=0; i<cardsInHand.length; i++){
            valueSum += cardsInHand[i].getValue();
        }
        return valueSum;
    }

    //add one card
    public void addCard(Card card){
        this.cardsInHand = Player.add(cardsInHand , card);
    }
}