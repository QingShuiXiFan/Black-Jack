/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-09-22 18:07:51
 * @LastEditTime: 2019-09-23 11:36:16
 * @LastEditors: Please set LastEditors
 */
package bj;

import java.util.Scanner;

class Player extends Person{
    private Card[] cardsInLeft = {};
    private Card[] cardsInRight = {};
    private int bet;

    public Player(int ID, int balance){
        super(ID, balance);
        this.bet = 0;
    }

    //set bet amount for each round
    public void setBet(int bet){
        this.bet = bet;
    }

    //clear bet amount after each round
    public void clearBet(){
        this.bet = 0;
    }

    //choose action
    public int chooseAction(Cards cards){
        System.out.println("Actions:");
        System.out.println("1. Hit"); //1
        System.out.println("2. Stand"); //2
        //judge if player can split his/her cards
        if(this.cardsInLeft[0] == this.cardsInLeft[1] && this.cardsInLeft[2] == null){
            System.out.println("3. Split");//3
        }
        System.out.println("4. Double Up");//4
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
            else break;
        }

        switch(choice){
            case 1: {this.hit(cards); return 1;}
            case 2: return this.stand();
            case 3:
            case 4:
            default: return 0;
        }
    }

    /** four possible actions */
    //Hit, return the card received
    public Card hit(Cards cards){
        //get one card in hand
        this.cardsInLeft[cardsInLeft.length] = cards.pop();
        return this.cardsInLeft[cardsInLeft.length-1];
    }

    //Stand
    public int stand(){
        return 2;
    }

    //Split
    public void split(){

    }

    //Double up
    public void doubleUp(){
        
    }
}