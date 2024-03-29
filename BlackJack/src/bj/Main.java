/*
 * @Description: Main class
 * @Author: your name
 * @Date: 2019-09-22 17:59:28
 * @LastEditTime: 2019-09-30 21:14:22
 * @LastEditors: Please set LastEditors
 */
package bj;

import java.util.*;

public class Main{
	public static int num_of_players = 0, balance = 0, dealer_index = 0;
    public static void main(String[] args) {
    	player_input();
        Person[] person = new Person[num_of_players];                   //initialize n Player instances
//        if(choose_dealer == 1) {
//        	person[0] = new Dealer(0, balance*3);
//        	for(int i = 1; i < num_of_players; i++) {
//        		person[i] = new Player(i, balance);
//        	}
//        }
        //else if(choose_dealer == 2) {
        	dealer_index = random_choose_dealer(num_of_players) - 1;
        	System.out.println("Player " + dealer_index + " is dealer.");
        	person[dealer_index] = new Dealer(dealer_index, balance);
        	for(int i = 0; i < num_of_players; i++) {
        		if(i != dealer_index)
        			person[i] = new Player(i, balance);
        		else
        			continue;
        	}
        //}
        while(true) {                                                      //round
        	bet_input(person);
			Play.round(person);
			Judge.printBalance(person);
        	if(is_cash_out() == false && judge_balance(person) == true) {
        		Play.clear_game(person);
        		decideBanker(person);
        		continue;
        	}
        	else {
				if(judge_balance(person) == false){
					System.out.println("Someone runs out of money, cash out.");
				}
        		break;
        	}
        }
        Judge.printBalance(person);
    }

	private static void decideBanker(Person[] person) {
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


	public static void player_input() {
//    	System.out.println("Please choose dealer. Input 1 or 2");
//        System.out.println("1 - computer.");
//        System.out.println("2 - randomly from players.");
//        while(true) {
//    		try {
//    			Scanner input = new Scanner(System.in);
//    			choose_dealer = input.nextInt();
//    			if(choose_dealer != 1 && choose_dealer != 2) {
//    				System.out.println("Input error.");
//    				continue;
//    			}
//    			else
//    				break;
//    		}
//    		catch(Exception e) {
//    			System.out.println("Input error.");
//    		}
//    	}
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
//        if(choose_dealer == 1)                                                 //add computer into the player array
//        	num_of_players++;
    }
    
    
    
    public static void bet_input(Person[] person) {
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
    	
    
    public static boolean is_cash_out() {
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
    
    
    public static boolean judge_balance(Person[] person) {
    	for(int i = 0; i < num_of_players; i++) {
    		if(person[i].getBalance() == 0)
    			return false;
    	}
    	return true;
    }
    
    
    
    public static int random_choose_dealer(int num_of_players) {         //[1,num_of_players + 1) random int
    	Random r = new Random();
    	return r.nextInt(num_of_players) + 1;                          
    }
    
    
    
}