/*
 * @Description: In User Settings Edit
 * @Author: Jun Li
 * @Date: 2019-09-22 18:07:51
 * @LastEditTime: 2019-10-06 23:46:19
 * @LastEditors: Please set LastEditors
 */
package bj;

import java.util.Scanner;

public class Player extends Person{
    private Card[] cardsInHand = {}; // array for cards received
    private int bet; // bet for every round

    public Player(int ID, int balance){
        super(ID, balance);
        this.bet = 0;
    }

    //set bet amount for each round
    public void setBet(int bet){
        this.bet = bet;
    }

    public int getBet(){
        return this.bet;
    }

    //clear bet amount after each round
    public void clearBet(){
        this.bet = 0;
    }

    //************** Override methods from Person class *****************
    @Override
    public void addCard(Card card){
        cardsInHand = add(cardsInHand, card);
    }

    /**
     * treat Ace as 1, 
     * @return least sum of value
     */
    public int getCardsValue(){
        return Judge.getCardsValue(this.cardsInHand);
    }

    /**
     * treat one Ace as 1, other Ace as 1 
     * @return least sum of value
     */
    public int getMaxCardsValue(){
        return Judge.getMaxCardsValueForTE(this.cardsInHand);
    }

    /**
     * use it to judge whoWin()
     * @return max sum of value without bust
     */
    public int getMaxCardsValueWithoutBust(){
        return Judge.getMaxCardsValueWithoutBustForTE(this.cardsInHand);
    }

    /**
     * @return cards in hand
     */
    public Card[] getCards(){
        return this.cardsInHand;
    }

    /**
     * print all cards in hand in console
     * */ 
    public void printCardsInHand(){
        System.out.print("Cards in hand: ");
        for(int i=0;i<cardsInHand.length;i++){
            System.out.print(cardsInHand[i].getSuit()+ " " +cardsInHand[i].getRealValue()+ " ");
        }
        System.out.println();
    }
    //*****************************************************************

    

    //choose action. leftOrRight: 0 for lefthand, 1 for righthand
    public int chooseAction(Cards cards){
        System.out.println("****************************************");
        System.out.println("Player " + this.getID() + ", please choose your action:");
        System.out.println("1 - Hit"); //1
        System.out.println("2 - Stand"); //2
        System.out.print("Please choose your action(1 or 2):");

        int choice;
        while(true){
            try{
                Scanner in = new Scanner(System.in);
                choice = in.nextInt();
                break;
            }catch(Exception e){
                System.out.print("Input invalid, input valid index number:");
                continue;
            }
        }
        while(true){
            if(choice<1 || choice >2){
                Scanner in = new Scanner(System.in);
                System.out.print("Input invalid, input valid index number(1 or 2):");
                choice = in.nextInt();
            }
            else break;
        }

        switch(choice){
            case 1: 
            {
                hit(cards);
                return 1;
            }
            case 2: return this.stand();
            default: return 0;
        }
    }
    
    // two possible actions

    /**
     * Hit action
     * @param cards
     * @return 1
     */
    public int hit(Cards cards){
            this.cardsInHand = Player.add(this.cardsInHand, cards.pop(1));
            // print cards in hand
            this.printCardsInHand();
            return 1;
    }

    /**
     * Stand action
     * @return 2
     */
    public int stand(){
        return 2;
    }

    /** Once all Players stand or bust, the dealer reveals their face down card to
    the Player, and continues to hit until the hand value of the Dealer
    reaches or exceeds 27.*/
    public void autoHit(Cards cards){
        printCardsInHand();
        // if natural TRIANTAENA
        if(Judge.isNaturalBJ(cardsInHand) == true){
            System.out.println("Dealer has NATURAL TRIANTAENA!!!");
            return;
        }
        // if lucky 14
        if(Judge.isLucky14(cardsInHand) == true){
            System.out.println("Dealer has LUCKY 14!!!");
            return;
        }

        boolean aceUsed = false; //if there is one Ace treated as 1
        //calculate sum value of cards in hand
        int valueSum = getMaxCardsValue();

        while(valueSum < Judge.winNumber-4){
            // hit
            hit(cards);

            // if receive a card that leads to bust, then treat Ace as 1 until valueSum does not exceeds 21 or no more Ace left
            if(valueSum > Judge.winNumber && Judge.aceCount(cardsInHand) > 0 && aceUsed == false){
                valueSum -= 10;
                aceUsed = true;
            }   
        }
    }


    //This method will add an element to an array at end and return the resulting array
    public static Card[] add(Card[] arr, Card element){
        Card[] tempArr = new Card[arr.length+1];
        System.arraycopy(arr, 0, tempArr, 0, arr.length);
        
        tempArr[arr.length] = element;
        return tempArr;
    }

    public static Card[] remove(Card[] arr, int index){
        Card[] tempArr = new Card[arr.length-1];
        System.arraycopy(arr, 0, tempArr, 0, arr.length-1);
        return tempArr;
    }
    
    //clear all the cards in hand
    public void clear_cards() {
    		Card[] emptyCardArray = {};
    		this.cardsInHand = emptyCardArray;
    }
}