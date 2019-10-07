/*
 * @Description: In User Settings Edit
 * @Author: Jun Li
 * @Date: 2019-09-22 18:07:45
 * @LastEditTime: 2019-10-06 23:47:02
 * @LastEditors: Please set LastEditors
 */
package bj;

public class Person{
    private int ID;
    private int balance;

    public Person(int ID, int balance){
        this.ID = ID;
        this.balance = balance;
    }
    
    public int getID(){
        return ID;
    }

    public int getBalance(){
        return balance;
    }
    
    public void setBalance(int balance){
        this.balance = balance;
    }

    public void addCard(Card card, int leftOrRight){}
    public void addCard(Card card){}
    public int getCardsValue(){return 0;}
    public int getCardsValue(int leftOrRight){return 0;}
    public int getMaxCardsValueWithoutBust(){return 0;};
    public Card[] getCards(){
        Card[] cards = {};
        return cards;
    }
    public Card[] getCards(int leftOrRight){
        Card[] cards = {};
        return cards;
    }
    public void printCardsInHand(){}
    public void printCardsInHand(int leftOrRight){}
}