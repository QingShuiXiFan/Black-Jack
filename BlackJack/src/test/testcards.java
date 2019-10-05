package test;

import bj.Cards;

public class testcards {
    public static void main(String[] args){
        Cards cards=new Cards();
        //cards.shuffle();
        //System.out.println(cards.toString());
        //cards=new Cards();
        //System.out.println(cards.toString());
        for(int i=0;i<200;i++){
            cards.pop(1);
        }
    }
}
