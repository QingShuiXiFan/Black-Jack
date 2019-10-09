/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-09-27 17:54:29
 * @LastEditTime: 2019-10-08 21:53:33
 * @LastEditors: Please set LastEditors
 */
package bj;

import java.util.EnumSet;
import java.util.Scanner;

import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

public class Round {
	 private int [] is_bust;                     //is_bust: 0 - neither bust, 1 - only left hand bust, 2 - only right hand bust, 3 - both hands bust 
	 private int [] is_stand;                    //is_stand: 0 - neither stand, 1 - only left hand stand, 2 - only right hand stand, 3 - both hands stand 
	 private int num_of_players;
	 private int dealer_index;

	 /**
	  * constructor
	  */
	 public Round(int num_of_players, int dealer_index){
		this.num_of_players = num_of_players;
		this.dealer_index = dealer_index;
		is_bust = new int[num_of_players];
		is_stand = new int[num_of_players];
	 }

	 public void newRound(Person[] person){
		 	System.out.println("============NEW ROUND START=============");
	    	Cards cards = new Cards();
	    	if (deal_cards_start(person, cards)){
	    		System.out.println("all players quit, this round ends");
	    		return;
			}
	    		for(int i = 0; i < num_of_players; i++) {                            
	    			if(i != dealer_index) {
	    				while(player_is_active(i, is_bust, is_stand, person) == true){
							player_left(person, i, cards);
	    				}
	    				if(player_is_bust(i, is_bust, person) == true) {
							System.out.println("Player "+ i + " bust!!!!!");
							System.out.println("Player"+ i + " loses $" + ((Player) person[i]).getBet() + " money");
	    					person[dealer_index].setBalance(person[dealer_index].getBalance() + ((Player) person[i]).getBet());
							person[i].setBalance(person[i].getBalance() - ((Player) person[i]).getBet());
	    				}
	    				else System.out.println("Player "+ i + " done.");
	    			}
				}
	    		if(all_player_is_bust(is_bust, person) == false) {
				System.out.println("****************************************");
	    		dealer_action(person, cards);	    		
	    		}
	    	}
	 
	 public boolean deal_cards_start(Person[] person, Cards cards) {
		 Scanner in=new Scanner(System.in);
	 			int count=0;
	    		for(int j = 0; j < num_of_players; j++) {
					System.out.print("Dealer ");
					person[dealer_index].addCard(cards.pop(1));
	    			if(j != dealer_index) {
	    				System.out.print("Player " + j + " ");
						person[j].addCard(cards.pop(1));
						System.out.println("Dealer's first card: " + person[dealer_index].getCards()[0].getSuit() + person[dealer_index].getCards()[0].getRealValue());
	    				System.out.println("Player " + j + " wants quit this round?(y/n)");
	    				String s=in.nextLine();
	    				if(s.equals("y")){
	    					count++;
	    					is_bust[j]=3;
						}
						else{
							System.out.print("Player " + j + " ");
							person[j].addCard(cards.pop(1));
							System.out.print("Player " + j + " ");
							person[j].addCard(cards.pop(1));
						}
	    			}
	    			else {
						System.out.print("Dealer ");
						person[j].addCard(cards.pop(0));
						System.out.print("Dealer ");
						person[j].addCard(cards.pop(0));
	    			}
	    		}
	    		return count==num_of_players-1;
	 }
	 
	 public void dealer_action(Person[] person, Cards cards) {
		System.out.println("Banker's action: ");
		 ((Player)person[dealer_index]).autoHit(cards); // banker auto hit
		
		settleBalance(person);
	}
	 
	 public void player_left(Person[] person, int i, Cards cards) {                     // i indicate the index of this player in person[]
		 int actionLeft = 0;
			 if(Judge.isBust(person[i].getCards()) == true) {                             // if left hand bust
				if(person[i].getCards().length != 0)
					System.out.println("Player " + i + "hand bust!!!");
				if(is_bust[i] == 0)                                                       // if no hand bust before
					 is_bust[i] = 1;
//				else if(is_bust[i] == 2)                                                  // if right already bust before
//					 is_bust[i] = 3;
			 }
			 else {
				 person[i].printCardsInHand();
				 actionLeft = ((Player) person[i]).chooseAction(cards);
				 if(actionLeft == 3){
					 player_left(person, i, cards);
				 }
				 if(actionLeft == 2) {
					 if(is_stand[i] == 0)
						 is_stand[i] = 1;
					 else if(is_stand[i] == 2)
						 is_stand[i] = 3;
				 }			 
			 }
	 }
	 
//	 public void player_right(Person[] person, int i, Cards cards) {                    // i indicate the index of this player in person[]
//		 int actionRight = 0;
//		 if(person[i].getCards(1).length > 0){
//			 if(is_bust[i] == 2 || is_bust[i] == 3 || is_stand[i] == 2 || is_stand[i] == 3)    // right hand bust or stand
//				 return;
//			 else {
//				 if(Judge.isBust(person[i].getCards(1)) == true) {                             // if right hand bust
//					System.out.println("Player " + i + " right hand bust!!!");
//					 if(is_bust[i] == 0)                                                       // if no hand bust before
//						 is_bust[i] = 2;
//					 else if(is_bust[i] == 1)                                                  // if left already bust before
//						 is_bust[i] = 3;
//				 }
//				 else {
//					 person[i].printCardsInHand();
//					 actionRight = ((Player) person[i]).chooseAction(cards);
//					 if(actionRight == 2) {
//						 if(is_stand[i] == 0)
//							 is_stand[i] = 2;
//						 else if(is_stand[i] == 1)
//							 is_stand[i] = 3;
//					 }
//				 }
//			 }
//		 }
//	 }
	 
	 public boolean player_is_bust(int i, int [] is_bust, Person[] person) {          //judge whether this player bust
		 if((is_bust[i] == 1) && (person[i].getCards().length == 0) || is_bust[i] == 3)
			 return true;
		 else
			 return false;
	 }
	 
	 public boolean player_is_active(int i, int [] is_bust, int [] is_stand, Person[] person) {
		 if(is_bust[i] == 3)
			 return false;
		 if(is_stand[i] == 1)
			 return false;
//		 if(is_bust[i] == 1 && person[i].getCards().length == 0)
//			 return false;
//		 if(is_stand[i] == 1 && person[i].getCards().length == 0)
//			 return false;
		 if(is_bust[i] == 1)
		 	return false;
		 return true;
	 }
	 
	 public boolean all_player_is_bust(int [] is_bust, Person[] person) {          //judge whether all players bust
		 for(int j = 0; j < num_of_players; j++) {
			 if(j != dealer_index) {
				 if(((is_bust[j] == 1) && (person[j].getCards().length == 0)) || is_bust[j] == 3)
					 continue;
				 else
					 return false;
			 }				 
		 }
		 return true;			
	 }

	 /**
	  * settle everyone's balance for one round
	  * @param person
	  */
	 private void settleBalance(Person[] person){
		if(Judge.isBust(person[dealer_index].getCards()) == true) {                                 //dealer bust
			System.out.println("Banker's bust, all surviving players win!!!!");

			for(int i = 0; i < num_of_players; i++) {
				if (is_bust[i]==3){
					continue;
				}
				if(i != dealer_index && (player_is_bust(i, is_bust, person) == false)) {     //surviving player
					person[i].setBalance(person[i].getBalance() + ((Player) person[i]).getBet());
					person[dealer_index].setBalance(person[dealer_index].getBalance() - ((Player) person[i]).getBet());
				}
			}
		 }
		 else {
			for(int i = 0; i < num_of_players; i++) {
				if (is_bust[i]==3){
					continue;
				}
				if(player_is_bust(i, is_bust, person) == false) {   //if not bust
					if(i != dealer_index) {                                                             //compare with players' hand
						//System.out.println(Judge.whoWin(person, Main.dealer_index, i, 0));
						if(Judge.whoWin(person, dealer_index, i) == dealer_index) {
							System.out.println("Player "+ i + " loses $" + ((Player) person[i]).getBet() + " money");
							person[dealer_index].setBalance(person[dealer_index].getBalance() + ((Player) person[i]).getBet());
							person[i].setBalance(person[i].getBalance() - ((Player) person[i]).getBet());
						}
						else if(Judge.whoWin(person, dealer_index, i) == i) {
							System.out.println("Player "+ i + " wins $" + ((Player) person[i]).getBet() + " money");
							person[dealer_index].setBalance(person[dealer_index].getBalance() - ((Player) person[i]).getBet());
							person[i].setBalance(person[i].getBalance() + ((Player) person[i]).getBet());
						}
						else {//tie
							System.out.println("Player "+ i + " draws back the bet.");
						}
					}
				}
			}
		}
	 }
	
	 public void clear_game(Person[] person) {
		 ((Player) person[dealer_index]).clear_cards();
		 for(int i = 0; i < num_of_players; i++) {
			 if(i != dealer_index) {
				 ((Player) person[i]).clearBet();
				 ((Player) person[i]).clear_cards();
			 }
			 is_bust[i] = 0;
			 is_stand[i] = 0;
		 }
	 }
}
