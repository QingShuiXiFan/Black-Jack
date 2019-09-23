/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-09-22 18:07:06
 * @LastEditTime: 2019-09-23 13:37:26
 * @LastEditors: Please set LastEditors
 */
package bj;

class Judge{
    //juage if dealer or player has bust
    public static boolean isBust(Card[] cards){
        int sum = 0;
        for(int i=0; i<cards.length; i++){
            sum += cards[i].getValue();
        }
        if(sum > 24)return true;
        else return false;
    }

    // juage who is winner
    public static Person whoWin(){

    }

    // settle balance for dealer and player after one round
    public static void settleBalance(){
        
    }

    // print balance for all players
    public static void printBalance(){

    }
}