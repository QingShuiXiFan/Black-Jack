/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-09-22 18:06:55
 * @LastEditTime: 2019-09-28 13:16:47
 * @LastEditors: Please set LastEditors
 */
package bj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cards{
    //private Card[] cards; // whole 52 cards
    private List<Card> cards;

    @Override
    public String toString() {
        for (int i=0;i<cards.size();i++)
        System.out.println(cards.get(i).getRealValue());
        return super.toString();
    }

    public Cards(){
        cards=new ArrayList<Card>();
        int count=0;
        for(int i=4;i<=52;i+=4){
            count++;
            for (int j=i-4+1;j<=i;j++){
                Card c=new Card("","",0);
                if(count==1) {
                    c = new Card("A", "A", count);
                }else if(count<=10){
                    c= new Card(String.valueOf(count),String.valueOf(count),count);
                }else if (count<=13){
                    if (count==11)
                        c= new Card("J","J",10);
                    else if(count==12)
                        c= new Card("Q","Q",10);
                    else if(count==13)
                        c= new Card("K","K",10);
                }
                cards.add(c);
            }
        }
        for(int i=0;i<52;i++){
            if(i%4==0){
                cards.get(i).setSuit("♦");
            }else if(i%4==1){
                cards.get(i).setSuit("♠");
            }else if(i%4==2){
                cards.get(i).setSuit("♤");
            }else{
                cards.get(i).setSuit("♧");
            }
        }
        shuffle();
    }

    /** methods */
    // shuffle cards
    private void shuffle(){
        Collections.shuffle(cards);
    }

    // 0 for invisible, 1 for visible
    public Card pop(int isVisible){
        Card c=cards.get(0);
        //changed by Jun Li
        if(isVisible == 1)
            System.out.println("received card: " + c.getSuit() + " " + c.getRealValue());
        else
            System.out.println("received card: **(invisible)");
        cards.remove(0);
        return c;
    }
}