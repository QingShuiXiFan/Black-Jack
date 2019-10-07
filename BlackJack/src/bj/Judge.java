/*
 * @Description: Class contains some tool methods
 * @Author: Jun Li
 * @Date: 2019-09-22 18:07:06
 * @LastEditTime: 2019-10-06 21:50:49
 * @LastEditors: Please set LastEditors
 */
package bj;

public class Judge{
    static int winNumber=31;

    /**judge if dealer or player is bust
     * @param cards: Cards instance
     */
    public static boolean isBust(Card[] cards){
        int sum = 0;
        for(int i=0; i<cards.length; i++){
            sum += cards[i].getValue();
        }
        if(sum > winNumber)return true;
        else return false;
    }

    /** judge if cards is natural BlackJack
     * @param cards: Cards instance
    */
    public static boolean isNaturalBJ(Card[] cards){
        
        int valueSum = 0; // calculate sum of cards' value in hand
        for(int i=0;i<cards.length;i++){
            valueSum += cards[i].getValue();
        }

        if(valueSum == 21 && cards.length == 21/10 && aceCount(cards) == 1){
            //when valueSum = 31 and has 3 cards in hand which contains one and only one Ace
            return true;
        }
        else return false;
    }

    /** judge if cards is natural TriantaEna
     * @param Card[] cards: Cards instance
    */
    public static boolean isNaturalTE(Card[] cards){
        int valueSum = 0; // calculate sum of cards' value in hand
        for(int i=0;i<cards.length;i++){
            valueSum += cards[i].getValue();
        }

        if(valueSum == winNumber && cards.length == winNumber/10 && aceCount(cards) == 1){
            //when valueSum = 31 and has 3 cards in hand which contains one and only one Ace
            return true;
        }
        else return false;
    }

    /** judge if cards is lucky14
     * @param Card[] cards: Cards instance
    */
    public static boolean isLucky14(Card[] cards){
        int valueSum = 0; // calculate sum of cards' value in hand
        String suit = cards[0].getSuit();
        for(int i=0;i<cards.length;i++){
            valueSum += cards[i].getValue();
            // if suit is different, then return false
            if(!cards[i].getSuit().equals(suit)){
                return false;
            }
        }

        if(valueSum == 14){
            return true;
        }
        else return false;
    }

    //

    /** judge who is winner if exists two or more persons who does not bust
    * @param Person[] ps: all Person instances
    * @param int dealer_index
    * @param int player_index
    * @param int leftOrRight: left hand or right hand
    * @return int: winner index in Person[], if ties, return -1
    */
    public static int whoWin(Person[] ps, int dealer_index, int player_index, int leftOrRight){
        int dealerValue = ps[dealer_index].getCardsValue();

        //calculate max value of cards if exists 'A's
        for(int i=0 ; i < aceCount(ps[dealer_index].getCards()); i++){
            if(dealerValue + 10 <= winNumber) dealerValue += 10;
            else break;
        }

        int playerValue = 0;
        if(Judge.isBust(ps[player_index].getCards(leftOrRight)) == false){
            playerValue = ps[player_index].getCardsValue(leftOrRight);

            //calculate max value of cards if exists 'A's
            for(int i=0 ; i < aceCount(ps[dealer_index].getCards(leftOrRight)); i++){
                if(playerValue + 10 <= winNumber) playerValue += 10;
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

    /**
     * Calculate value in hand, treat Ace as 1
     * @param cards
     * @return sum value of cards in hand
     */
    public static int getCardsValue(Card[] cards){
        int valueSum = 0;
            for(int i=0; i<cards.length; i++){
                valueSum += cards[i].getValue();
            }
        return valueSum;
    }

    /**
     * Calculate value in hand, treat all Ace as 11
     * @param cards
     * @return sum value of cards in hand
     */
    public static int getMaxCardsValueForBJ(Card[] cards){
        int valueSum = getCardsValue(cards);

        for(int i=0;i<aceCount(cards);i++){
            valueSum += 10;
        }
        return valueSum;
    }

    /**
     * Calculate value in hand, treat all Ace as 11
     * @param cards
     * @return sum value of cards in hand
     */
    public static int getMaxCardsValueForTE(Card[] cards){
        int valueSum = getCardsValue(cards);

        // if exists one or more Ace
        for(int i = 0;i<aceCount(cards);i++){
            valueSum += 10;
        }
        return valueSum;
    }

    /**
     * Calculate value in hand, treat one Ace as 1, others as 11
     * @param cards
     * @return sum value of cards in hand
     */
    public static int getLeastCardsValueForTE(Card[] cards){
        int valueSum = getCardsValue(cards);
        
        // if exists one or more Ace
        for(int i = 0;i<aceCount(cards)-1;i++){
            valueSum += 10;
        }
        return valueSum;
    }
}