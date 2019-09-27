/*
 * @Description: In User Settings Edit
 * @Author: Jun Li
 * @Date: 2019-09-22 18:07:51
 * @LastEditTime: 2019-09-24 20:18:48
 * @LastEditors: Please set LastEditors
 */
package bj;

import java.util.Scanner;

class Player extends Person{
    private Card[] cardsInLeft = {}; //0
    private Card[] cardsInRight = {}; //1
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

    //calculate sum value of cards in hand
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

    //*****************************************************************

    

    //choose action. leftOrRight: 0 for lefthand, 1 for righthand
    public int chooseAction(Cards cards, int leftOrRight){
        System.out.println("Actions:");
        System.out.println("1 - Hit"); //1
        System.out.println("2 - Stand"); //2
        System.out.println("3 - Split");//3
        System.out.println("4 - Double Up");//4
        System.out.print("Please choose your action(input index number):");

        Scanner in = new Scanner(System.in);
        int choice;
        while(true){
            try{
                choice = in.nextInt();
                break;
            }catch(Exception e){
                System.out.print("Input invalid, input valid index number:");
                continue;
            }
        }
        while(true){
            if(choice<1 || choice >4){
                System.out.print("Input invalid, input valid index number:");
                choice = in.nextInt();
            }
            else if(choice == 3 && cardsInLeft.length != 2){
                System.out.print("Cannot split your cards, input another index:");
                choice = in.nextInt();
            }
            else if(!(this.cardsInLeft[0] == this.cardsInLeft[1] && cardsInLeft.length == 2 && cardsInRight.length == 0) && choice == 3){
                System.out.print("Cannot split your cards, input another index:");
                choice = in.nextInt();
            }
            else break;
        }
        in.close();

        switch(choice){
            case 1: 
            {
                this.hit(cards, leftOrRight);
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

    /** four possible actions */
    //Hit, return the card received (1)
    public Card hit(Cards cards, int leftOrRight){
        //get one card for left hand
        if(leftOrRight == 0){
            this.cardsInLeft = add(cardsInLeft, cards.pop());
            return this.cardsInLeft[cardsInLeft.length-1];
        }
        else{
            this.cardsInRight = add(cardsInRight, cards.pop());
            return this.cardsInRight[cardsInRight.length-1];
        }
    }

    //Stand (2)
    public int stand(){
        return 2;
    }

    //Split (3)
    public void split(Cards cards){
        this.cardsInRight[0] = this.cardsInLeft[1];
        this.cardsInLeft[1] = null;

        // receive oen card for both hands
        hit(cards, 0);
        hit(cards, 1);
    }

    //Double up (4)
    public int doubleUp(Cards cards, int leftOrRight){
        this.bet += this.bet;
        hit(cards, leftOrRight);
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
}