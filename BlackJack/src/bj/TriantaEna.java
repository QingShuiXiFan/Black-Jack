/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-10-06 22:13:48
 * @LastEditTime: 2019-10-07 00:08:18
 * @LastEditors: Please set LastEditors
 */
package bj;
import java.util.*;

public class TriantaEna{
    private int num_of_players = 0, balance = 0, dealer_index = 0;
    public void play(){
        System.out.println("============ TRIANTA ENA =============");
        player_input(); // set number of players and balance for all players
        Person[] person = new Person[num_of_players];                   //initialize n Player instances
        	dealer_index = random_choose_dealer(num_of_players) - 1;
        	System.out.println("Player " + dealer_index + " is banker.");
        	person[dealer_index] = new Player(dealer_index, 3*balance);
        	for(int i = 0; i < num_of_players; i++) {
        		if(i != dealer_index)
        			person[i] = new Player(i, balance);
        		else
        			continue;
            }
            
        while(true) {                                                      //round
            bet_input(person);
            Round round = new Round(num_of_players, dealer_index);
			round.newRound(person);
			Judge.printBalance(person);
        	if(is_cash_out() == false && judge_balance(person) == -1) {
        		round = new Round(num_of_players, dealer_index);
			    round.newRound(person);
        		decideBanker(person);
        		continue;
        	}
        	else {
                int index = judge_balance(person);
				if(index != -1){
					System.out.println("Player " + index + " runs out of money, cash out.");
				}
        		break;
        	}
        }
        Judge.printBalance(person);
    }

    private void decideBanker(Person[] person) {
		Scanner input = new Scanner(System.in);
		Person p[]=Arrays.copyOf(person,person.length);
		Arrays.sort(p, new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				if(o1.getBalance()>o2.getBalance())
					return -1;
				else
					return 1;
			}
		});
		for(int i=0;i<p.length;i++){
			System.out.println(p[i].getID()+"wants to be Banker?(y/n):");
			String a=input.nextLine();
			if(a.equals("y")){
				dealer_index=p[i].getID();
				break;
			}else if(a.equals("n")){
				continue;
			}else{
				System.out.println("wrong answer, input again!");
			}
		}
	}


	public void player_input() {
    	System.out.println("Please input the number of players:");
    	while(true) {
    		try {
    			Scanner input = new Scanner(System.in);
    			num_of_players = input.nextInt();
    			if(num_of_players < 2) {
    				System.out.println("Player number should be no less than 2, input again: ");
    				continue;
    			}
    			else
    				break;
    		}
    		catch(Exception e) {
    			System.out.println("Input error, input again: ");
    		}
    	}
        System.out.println("Please input the balance for each player:");
        while(true) {
    		try {
    			Scanner input = new Scanner(System.in);
    			balance = input.nextInt();
    			if(balance <= 0) {
    				System.out.println("balance should no less than 1, input again: ");
    				continue;
    			}
    			else
    				break;
    		}
    		catch(Exception e) {
    			System.out.println("Input error, input again: ");
    		}
    	}
    }
    
    
    
    public void bet_input(Person[] person) {
    	int bet = 0;
    	for(int i = 0; i < num_of_players; i++) { 
    		if(i != dealer_index) {
    			System.out.println("Player " + i + " please input your bet(you have $" + person[i].getBalance() + "):");
    			while(true) {                                              //input bet for each player
    				try {
    					Scanner input = new Scanner(System.in);
    					bet = input.nextInt();
    					if(bet <= 0 || bet > person[i].getBalance()) {
    						System.out.println("Bet should be within 1 - " + person[i].getBalance());
    						continue;
    					}
    					else
    						break;
    				}
    				catch(Exception e) {
    					System.out.println("Input error, input again: ");
    				}
    			}
    			((Player) person[i]).setBet(bet);
    		}
    	}
    }
    	
    
    public boolean is_cash_out() {
    	int cash_out = 0;
    	System.out.println("Do you want to cash out? Please input 0 or 1");
    	System.out.println("0 - No. I want to continue.");
    	System.out.println("1 - Yes. Cash out.");
    	while(true) {
    		try {
    			Scanner input = new Scanner(System.in);
    			cash_out = input.nextInt();
    			if(cash_out != 0 && cash_out != 1) {
    				System.out.println("Input error.");
    				continue;
    			}
    			else
    				break;
    		}
    		catch(Exception e) {
    			System.out.println("Input error.");
    		}
    	}
    	if(cash_out == 0)
    		return false;
    	else
    		return true;
    }
    
    /**
     * check balance of all players to judge whether go on or not
     * @param person
     * @return index of player who runs out of money, -1 if none
     */
    public int judge_balance(Person[] person) {
    	for(int i = 0; i < num_of_players; i++) {
    		if(person[i].getBalance() <= 0)
    			return i;
    	}
    	return -1;
    }
    
    public int random_choose_dealer(int num_of_players) {         //[1,num_of_players + 1) random int
    	Random r = new Random();
    	return r.nextInt(num_of_players) + 1;                          
    }
}