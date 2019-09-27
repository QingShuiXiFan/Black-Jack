/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-09-22 18:06:39
 * @LastEditTime: 2019-09-22 18:29:00
 * @LastEditors: Please set LastEditors
 */
package bj;

class Card{
    private String suit; // ♦, ♠, ♤, ♧
    private String realValue; // true card value
    private int value; // value in black jack game

    public Card(String c,String realValue,int value){
        suit=c;
        this.realValue=realValue;
        this.value=value;
    }
    /** set, get methods**/
    public String get(){
        return suit;
    }
}