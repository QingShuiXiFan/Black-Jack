package bj;

public class play {
	 static int [] is_bust = new int[Main.num_of_players];                     //is_bust: 0 - neither bust, 1 - only left hand bust, 2 - only right hand bust, 3 - both hands bust 
	 static int [] is_stand = new int[Main.num_of_players];                    //is_stand: 0 - neither stand, 1 - only left hand stand, 2 - only right hand stand, 3 - both hands stand 
	 
	 
	 public static void round(Person[] person){
	    	int player_action = 0;                             
	    	Cards cards = new Cards();
	    	deal_cards_start(person, cards);                                                //deal 2 cards to start
	    	while(true) {                                                            //player choose action
	    		int inactive_player = 0;                                                 // player already bust or stand
	    		for(int i = 0; i < Main.num_of_players; i++) {                            
	    			if(i != Main.dealer_index) {
	    				if((is_bust[i] == 1 || is_stand[i] == 1) && (person[i].getCards(1).length == 0)) {   // player is inactive
	    					inactive_player++;
	    					continue;
	    				}
	    				else if(is_bust[i] == 3 || is_stand[i] == 3) {                                     //player is inactive
	    					inactive_player++;
	    					continue;
	    				}
	    				player_left(person, i, cards);
	    				player_right(person, i, cards);

	    			}
	    		}
	    		if(inactive_player == Main.num_of_players - 1) {
	    			dealer_action(person, cards);
	    		}
	    		
	    	}
	    }
	 
	 
	 
	 public static void deal_cards_start(Person[] person, Cards cards) {
		 for(int i = 0; i < 2; i++) {                                       //deal 2 cards to start
	    		for(int j = 0; j < Main.num_of_players; j++) {
	    			if(j != Main.dealer_index) {
	    				person[j].addCard(cards.pop(), 0);
	    			}
	    			else
	    				person[j].addCard(cards.pop());
	    		}
	    	}
	 }
	 
	 
	 public static void dealer_action(Person[] person, Cards cards) {
		 //displayCards(person[Main.dealer_index]);
		 ((Dealer) person[Main.dealer_index]).autoHit(cards);
		 if(Judge.isBust(person[Main.dealer_index].getCards()) == true) {                                 //dealer bust
			for(int i = 0; i < Main.num_of_players; i++) {
				if(i != Main.dealer_index && (is_bust[i] == 0 || (is_bust[i] == 1 && person[i].getCards(1).length > 0) || is_bust[i] == 2)) {     //surviving player
					person[i].setBalance(person[i].getBalance() + 2 * ((Player) person[i]).getBet());
				}
			}
		 }
		 else {
			for(int i = 0; i < Main.num_of_players; i++) {	                                            //dealer not bust
				if(i != Main.dealer_index) {                    //compare with players' left hand
					if(Judge.whoWin(person, Main.dealer_index, i, 0) == Main.dealer_index)
						person[Main.dealer_index].setBalance(person[Main.dealer_index].getBalance() + ((Player) person[i]).getBet());
					else if(Judge.whoWin(person, Main.dealer_index, i, 0) == i)
						person[i].setBalance(person[i].getBalance() + 2 * ((Player) person[i]).getBet());
					
				}
				else if(i != Main.dealer_index && person[i].getCards(1).length > 0) {            //compare with players' right hand
					if(Judge.whoWin(person, Main.dealer_index, i, 1) == Main.dealer_index)
						person[Main.dealer_index].setBalance(person[Main.dealer_index].getBalance() + ((Player) person[i]).getBet());
					else if(Judge.whoWin(person, Main.dealer_index, i, 1) == i)
						person[i].setBalance(person[i].getBalance() + 2 * ((Player) person[i]).getBet());
				}
			}
	 	 }
	 }
	 
	 
	 
	 public static void player_left(Person[] person, int i, Cards cards) {                     // i indicate the index of this player in person[]
		 int actionLeft = 0;
		 if(is_bust[i] == 1 || is_bust[i] == 3 || is_stand[i] == 1 || is_stand[i] == 3)  // left hand bust or stand
			 player_right(person, i, cards);
		 else {
			 person[i].addCard(cards.pop(), 0);
			 if(Judge.isBust(person[i].getCards(0)) == true) {                             // if left hand bust
				 if(is_bust[i] == 0)                                                       // if no hand bust before
					 is_bust[i] = 1;
				 else if(is_bust[i] == 2)                                                  // if right already bust before
					 is_bust[i] = 3;
			 }
			 else {
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
				 person[i].addCard(cards.pop(), 1);
				 if(Judge.isBust(person[i].getCards(1)) == true) {                             // if right hand bust
					 if(is_bust[i] == 0)                                                       // if no hand bust before
						 is_bust[i] = 2;
					 else if(is_bust[i] == 1)                                                  // if left already bust before
						 is_bust[i] = 3;
				 }
				 else {
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
}
