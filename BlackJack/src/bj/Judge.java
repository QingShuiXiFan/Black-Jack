/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-09-22 18:07:06
 * @LastEditTime: 2019-09-24 19:23:43
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
        if(sum > 21)return true;
        else return false;
    }

    // judge who is winner if exists two or more persons who does not bust
    // input: Person[] ps: all Person instances
    //        dealer_index
    //        player_index
    //        leftOrRight: left hand or right hand
    // output: Person instance, if ties, return null
    public static Person whoWin(Person[] ps, int dealer_index, int player_index, int leftOrRight){
        int dealerValue = ps[dealer_index].getCardsValue();
        int playerValue = 0;
        if(Judge.isBust(ps[player_index].getCards(leftOrRight)) == false){
            playerValue = ps[player_index].getCardsValue(leftOrRight);
            if(playerValue > dealerValue)return ps[player_index];
            else if(playerValue == dealerValue) return null;
            else return ps[dealer_index];
        }
        else return ps[dealer_index];
    }

    // settle balance for dealer and player after one round
    public static void settleBalance(){

    }

    // print balance for all players
    public static void printBalance(Person[] ps){
        System.out.println("Balance for every players:");
        for(int i=0; i<ps.length; i++){
            System.out.println(ps[i].getID() + " : $" + ps[i].getBalance());
        }
    }
}