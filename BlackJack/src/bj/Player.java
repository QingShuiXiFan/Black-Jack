/*
 * @Description: In User Settings Edit
 * @Author: Jun Li
 * @Date: 2019-09-22 18:07:51
 * @LastEditTime: 2019-09-28 15:18:29
 * @LastEditors: Please set LastEditors
 */
package bj;

import java.util.Scanner;

public class Player extends Person{
    Card[] cardsInLeft = {}; //0
    Card[] cardsInRight = {}; //1
    private int bet;

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
    public void addCard(Card card, int leftOrRight){
        if(leftOrRight == 0){
            this.cardsInLeft = add(cardsInLeft, card);
        }
        else this.cardsInRight = add(cardsInRight, card);
    }

    //calculate max sum value of cards in hand, treat 'A's as 11 if !isBust()
    public int getCardsValue(int leftOrRight){
        int valueSum = 0;
        if(leftOrRight == 0){
            for(int i=0; i<cardsInLeft.length; i++){
                valueSum += cardsInLeft[i].getValue();
            }
        }
        else{
            for(int i=0; i<cardsInRight.length; i++){
                valueSum += cardsInRight[i].getValue();
            }
        }
        return valueSum;
    }

    //get received cards in one hand, 0 for left hand and 1 for right hand
    public Card[] getCards(int leftOrRight){
        if(leftOrRight == 0)
        return this.cardsInLeft;
        else return this.cardsInRight;
    }

    //print what the player has in certain hand
    public void printCardsInHand(){
        System.out.print("Cards in hand: ");
        if(cardsInRight.length == 0){
            for(int i=0;i<cardsInLeft.length;i++){
                System.out.print(cardsInLeft[i].getSuit()+ " " +cardsInLeft[i].getRealValue()+ " ");
            }
        }
        else{
            System.out.print("LEFT: ");
            for(int i=0;i<cardsInLeft.length;i++){
                System.out.print(cardsInLeft[i].getSuit()+ " " +cardsInLeft[i].getRealValue()+ " ");
            }
            System.out.print("  RIGHT: ");
            for(int i=0;i<cardsInRight.length;i++){
                System.out.print(cardsInRight[i].getSuit()+ " " +cardsInRight[i].getRealValue()+ " ");
            }
        }
        System.out.println();
    }
    //*****************************************************************

    

    //choose action. leftOrRight: 0 for lefthand, 1 for righthand
    public int chooseAction(Cards cards, int leftOrRight){
        System.out.println("1 - Hit"); //1
        System.out.println("2 - Stand"); //2
        System.out.println("3 - Split");//3
        System.out.println("4 - Double Up");//4
        System.out.print("Please choose your action(input index number):");

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
            if(choice<1 || choice >4){
                Scanner in = new Scanner(System.in);
                System.out.print("Input invalid, input valid index number:");
                choice = in.nextInt();
            }
            else if(choice == 3 && cardsInLeft.length != 2){
                Scanner in = new Scanner(System.in);
                System.out.print("Cannot split your cards, input another index:");
                choice = in.nextInt();
            }
            else if(!(this.cardsInLeft[0].getValue() == cardsInLeft[1].getValue() && cardsInLeft.length == 2 && cardsInRight.length == 0) && choice == 3){
                Scanner in = new Scanner(System.in);
                System.out.print("Cannot split your cards, input another index:");
                choice = in.nextInt();
            }
            else break;
        }

        switch(choice){
            case 1: 
            {
                hit(this, leftOrRight,cards);
                return 1;
            }
            case 2: return this.stand();
            case 3: {
                split(cards);
                return 3;
            }
            case 4: return doubleUp(cards, leftOrRight);
            default: return 0;
        }
    }
    /** four possible actions
     * @param player TODO
     * @param leftOrRight TODO*/
    //Hit, return the card received (1)
    public Card hit(Player player, int leftOrRight,Cards cards){
        //get one card for left hand
        if(leftOrRight == 0){
            player.cardsInLeft = Player.add(player.cardsInLeft, cards.pop(1));

            // print cards in hand
            player.printCardsInHand();
            return player.cardsInLeft[player.cardsInLeft.length-1];
        }
        else{
            player.cardsInRight = Player.add(player.cardsInRight, cards.pop(1));
            player.printCardsInHand();
            return player.cardsInRight[player.cardsInRight.length-1];
        }
    }
    //Stand (2)
    public int stand(){
        return 2;
    }

    //Split (3)
    public void split(Cards cards){
        add(cardsInRight, this.cardsInLeft[1]);
        this.cardsInLeft[1] = null;

        // receive oen card for both hands
        hit(this, 0,cards);
        hit(this, 1,cards);
    }

    //Double up (4)
    public int doubleUp(Cards cards, int leftOrRight){
        this.bet += this.bet;
        hit(this, leftOrRight,cards);
        return stand();
    }

    //This method will add an element to an array and return the resulting array
    //add an item to an array
    public static Card[] add(Card[] arr, Card element){
        Card[] tempArr = new Card[arr.length+1];
        System.arraycopy(arr, 0, tempArr, 0, arr.length);
        
        tempArr[arr.length] = element;
        return tempArr;
    }
    
    //clear all the cards in both hands
    public void clear_cards() {
    		Card[] new_cardsInLeft = {};
    		Card[] new_cardsInRight = {};
    		this.cardsInLeft = new_cardsInLeft;
    		this.cardsInRight = new_cardsInLeft;
    }
}