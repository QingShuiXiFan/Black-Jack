package bj;

public class play {
	 static int [] is_bust = new int[Main.num_of_players];                     //is_bust: 0 - neither bust, 1 - only left hand bust, 2 - only right hand bust, 3 - both hands bust 
	 static int [] is_stand = new int[Main.num_of_players];                    //is_stand: 0 - neither stand, 1 - only left hand stand, 2 - only right hand stand, 3 - both hands stand 
	 
	 
	 public static void round(Person[] person){
	    	int player_action = 0;                             
	    	Cards cards = new Cards();
	    	deal_cards_start(person, cards);                                                //deal 2 cards to start
	    		int inactive_player = 0;                                                 // player already bust or stand
	    		for(int i = 0; i < Main.num_of_players; i++) {                            
	    			if(i != Main.dealer_index) {
	    				while(player_is_active(i, is_bust, is_stand, person) == true){
	    				player_left(person, i, cards);
	    				player_right(person, i, cards);
	    				}
	    				if(player_is_bust(i, is_bust, person) == true) {
	    					person[Main.dealer_index].setBalance(person[Main.dealer_index].getBalance() + ((Player) person[i]).getBet());
							person[i].setBalance(person[i].getBalance() - ((Player) person[i]).getBet());
	    				}
	    				System.out.println(i + " out");
	    			}
	    		}
	    		dealer_action(person, cards);	    		
	    	}
	 
	 
	 
	 public static void deal_cards_start(Person[] person, Cards cards) {
		 for(int i = 0; i < 2; i++) {                                       //deal 2 cards to start
	    		for(int j = 0; j < Main.num_of_players; j++) {
	    			if(j != Main.dealer_index) {
	    				System.out.print("Player " + j + " left hand ");
	    				person[j].addCard(cards.pop(), 0);
	    			}
	    			else {
	    				System.out.print("Dealer ");
	    				person[j].addCard(cards.pop());
	    			}
	    		}
	    		//System.out.println(i);
	    	}
	 }
	 
	 
	 public static void dealer_action(Person[] person, Cards cards) {
		 //displayCards(person[Main.dealer_index]);
		 System.out.println("dealer action");
		 ((Dealer) person[Main.dealer_index]).autoHit(cards);
		 if(Judge.isBust(person[Main.dealer_index].getCards()) == true) {                                 //dealer bust
			for(int i = 0; i < Main.num_of_players; i++) {
				if(i != Main.dealer_index && (player_is_bust(i, is_bust, person) == false)) {     //surviving player
					person[i].setBalance(person[i].getBalance() + 2 * ((Player) person[i]).getBet());
				}
			}
		 }
		 else {
			for(int i = 0; i < Main.num_of_players; i++) {	                                            //dealer not bust
				if(player_is_bust(i, is_bust, person) == false) {   //if not bust
				if(i != Main.dealer_index) {                                                             //compare with players' left hand
					System.out.println(Judge.whoWin(person, Main.dealer_index, i, 0));
					if(Judge.whoWin(person, Main.dealer_index, i, 0) == Main.dealer_index) {
						person[Main.dealer_index].setBalance(person[Main.dealer_index].getBalance() + ((Player) person[i]).getBet());
						person[i].setBalance(person[i].getBalance() - ((Player) person[i]).getBet());
					}
					else if(Judge.whoWin(person, Main.dealer_index, i, 0) == i) {
						System.out.println(i + " lose_money");
						person[Main.dealer_index].setBalance(person[Main.dealer_index].getBalance() - 2 * ((Player) person[i]).getBet());
						person[i].setBalance(person[i].getBalance() + 2 * ((Player) person[i]).getBet());
					}
				}
				else if(i != Main.dealer_index && person[i].getCards(1).length > 0) {            //compare with players' right hand
					if(Judge.whoWin(person, Main.dealer_index, i, 1) == Main.dealer_index) {
						person[i].setBalance(person[i].getBalance() - ((Player) person[i]).getBet());
						person[Main.dealer_index].setBalance(person[Main.dealer_index].getBalance() + ((Player) person[i]).getBet());
					}
					else if(Judge.whoWin(person, Main.dealer_index, i, 1) == i) {
						person[i].setBalance(person[i].getBalance() + 2 * ((Player) person[i]).getBet());
						person[Main.dealer_index].setBalance(person[Main.dealer_index].getBalance() - 2 * ((Player) person[i]).getBet());
					}
				}
			}
			}
		 }
	 }
	 
	 
	 
	 public static void player_left(Person[] person, int i, Cards cards) {                     // i indicate the index of this player in person[]
		 int actionLeft = 0;
		 if(is_bust[i] == 1 || is_bust[i] == 3 || is_stand[i] == 1 || is_stand[i] == 3)  // left hand bust or stand
			 player_right(person, i, cards);
		 else {                                                            
			 //System.out.print("Player " + i + " left hand ");
			 //person[i].addCard(cards.pop(), 0);
			 if(Judge.isBust(person[i].getCards(0)) == true) {                             // if left hand bust
				 if(is_bust[i] == 0)                                                       // if no hand bust before
					 is_bust[i] = 1;
				 else if(is_bust[i] == 2)                                                  // if right already bust before
					 is_bust[i] = 3;
			 }
			 else {
				 System.out.println("Player " + i + " choose your action.");
				 actionLeft = ((Player) person[i]).chooseAction(cards, 0);
				 if(actionLeft == 2) {
					 if(is_stand[i] == 0)
						 is_stand[i] = 1;
					 else if(is_stand[i] == 2)
						 is_stand[i] = 3;
				 }			 
			 }
			 player_right(person, i, cards);
		 }
	 }
	 
	 
	 
	 public static void player_right(Person[] person, int i, Cards cards) {                    // i indicate the index of this player in person[]
		 int actionRight = 0;
		 if(person[i].getCards(1).length > 0){
			 if(is_bust[i] == 2 || is_bust[i] == 3 || is_stand[i] == 2 || is_stand[i] == 3)    // right hand bust or stand
				 return;
			 else {
				 //System.out.print("Player " + i + " right hand ");
				 //person[i].addCard(cards.pop(), 1);
				 if(Judge.isBust(person[i].getCards(1)) == true) {                             // if right hand bust
					 if(is_bust[i] == 0)                                                       // if no hand bust before
						 is_bust[i] = 2;
					 else if(is_bust[i] == 1)                                                  // if left already bust before
						 is_bust[i] = 3;
				 }
				 else {
					 System.out.println("Player " + i + " choose your action.");
					 actionRight = ((Player) person[i]).chooseAction(cards, 1);
					 if(actionRight == 2) {
						 if(is_stand[i] == 0)
							 is_stand[i] = 2;
						 else if(is_stand[i] == 1)
							 is_stand[i] = 3;
					 }			 
				 }			 
			 }
		 }
	 }
	 
	 
	 
	 public static boolean player_is_bust(int i, int [] is_bust, Person[] person) {          //judge whether this player bust
		 if((is_bust[i] == 1) && (person[i].getCards(1).length == 0) || is_bust[i] == 3)
			 return true;
		 else
			 return false;
	 }
	 
	 
	 public static boolean player_is_active(int i, int [] is_bust, int [] is_stand, Person[] person) {
		 if(is_bust[i] == 3)
			 return false;
		 if(is_stand[i] == 3)
			 return false;
		 if(is_bust[i] == 1 && person[i].getCards(1).length == 0)
			 return false;
		 if(is_stand[i] == 1 && person[i].getCards(1).length == 0)
			 return false;
		 return true;
	 }
}
