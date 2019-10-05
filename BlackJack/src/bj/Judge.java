/*
 * @Description: In User Settings Edit
 * @Author: Jun Li
 * @Date: 2019-09-22 18:07:06
 * @LastEditTime: 2019-09-30 20:53:16
 * @LastEditors: Please set LastEditors
 */
package bj;

public class Judge{
    //judge if dealer or player has bust
    static int winnumber=31;
    public static boolean isBust(Card[] cards){
        int sum = 0;
        for(int i=0; i<cards.length; i++){
            sum += cards[i].getValue();
        }
        if(sum > winnumber)return true;
        else return false;
    }

    //judge if cards is natural BlackJack
    public static boolean isNaturalBJ(Card[] cards){
        if(cards[0].getValue()+cards[1].getValue() == 11 && cards.length == 2 && (cards[0].getRealValue() == "A" || cards[1].getRealValue() == "A")){
            return true;
        }
        else return false;
    }

    // judge who is winner if exists two or more persons who does not bust
    // input: Person[] ps: all Person instances
    //        dealer_index
    //        player_index
    //        leftOrRight: left hand or right hand
    // output: winner index in Person[], if ties, return -1
    public static int whoWin(Person[] ps, int dealer_index, int player_index, int leftOrRight){
        int dealerValue = ps[dealer_index].getCardsValue();

        //calculate max value of cards if exists 'A's
        for(int i=0 ; i < aceCount(ps[dealer_index].getCards()); i++){
            if(dealerValue + 10 <= winnumber) dealerValue += 10;
            else break;
        }

        int playerValue = 0;
        if(Judge.isBust(ps[player_index].getCards(leftOrRight)) == false){
            playerValue = ps[player_index].getCardsValue(leftOrRight);

            //calculate max value of cards if exists 'A's
            for(int i=0 ; i < aceCount(ps[dealer_index].getCards(leftOrRight)); i++){
                if(playerValue + 10 <= winnumber) playerValue += 10;
                else break;
            }

            if(playerValue > dealerValue)return player_index;
            else if(playerValue == dealerValue){
                //if player is natural bj and dealer is not
                if(Judge.isNaturalBJ(ps[player_index].getCards(leftOrRight)) && !Judge.isNaturalBJ(ps[dealer_index].getCards())){
                    return player_index;
                }
                //if dealer is neatural bj and player is not
                else if(!Judge.isNaturalBJ(ps[player_index].getCards(leftOrRight)) && Judge.isNaturalBJ(ps[dealer_index].getCards())){
                    return dealer_index;
                }
                else return -1;

            }
            else return dealer_index;
        }
        else return dealer_index;
    }

    // settle balance for dealer and player after one round
    // input: Person[] ps,
    //        index: index of player
    //        amount: balance change, can be positive and negative
    public static Person settleBalance(Person[] ps, int index, int amount){
        ps[index].setBalance(ps[index].getBalance()+amount);
        return ps[index];
    }

    // print balance for all persons
    public static void printBalance(Person[] ps){
        System.out.println("Balance for every players:");
        for(int i=0; i<ps.length; i++){
        	if(i != Main.dealer_index)
            System.out.println("Player " + ps[i].getID() + " : $" + ps[i].getBalance());
        	else if(i == Main.dealer_index)
        		System.out.println("Dealer : $" + ps[i].getBalance());
        }
    }

    // reuturn the number of 'A's in cards
    public static int aceCount(Card[] cards){
        int count = 0;
        for(int i=0; i < cards.length; i++){
            if(cards[i].getValue() == 1){
                count++;
            }
        }
        return count;
    }
}