/*
 * @Description: Main class
 * @Author: your name
 * @Date: 2019-09-22 17:59:28
 * @LastEditTime: 2019-09-23 13:20:20
 * @LastEditors: Please set LastEditors
 */
package bj;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

class Main{
    public static void main(String[] args) {
    	int num_of_players = 0, balance = 0, choose_dealer = 0, cash_out = 0, dealer_index = 0;
    	System.out.println("Please input the number of players.");
    	while(true) {
    		try {
    			Scanner input = new Scanner(System.in);
    			num_of_players = input.nextInt();
    			if(num_of_players < 2) {
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
        System.out.println("Please input the balance for each player.");
        while(true) {
    		try {
    			Scanner input = new Scanner(System.in);
    			balance = input.nextInt();
    			if(balance <= 0) {
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
        System.out.println("Please choose dealer. Input 1 or 2");
        System.out.println("1 - computer.");
        System.out.println("2 - randomly from players.");
        while(true) {
    		try {
    			Scanner input = new Scanner(System.in);
    			choose_dealer = input.nextInt();
    			if(choose_dealer != 1 && choose_dealer != 2) {
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
        Person[] person = new Person[num_of_players];                   //initialize n Player instances
        if(choose_dealer == 1) {
        	person[0] = new Dealer();
        	for(int i = 1; i < num_of_players; i++) {
        		person[1] = new Player();
        	}
        }
        else if(choose_dealer == 2) {
        	dealer_index = random_choose_dealer(num_of_players) - 1;
        	person[dealer_index] = new Dealer();
        	for(int i = 0; i < num_of_players; i++) {
        		if(i != dealer_index)
        			person[i] = new Player();
        		else
        			continue;
        	}
        }
        Judge judge = new Judge();                                      //initialize judge instance
        
        while(true) {                                                   //round
        	round();
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
        		continue;
        	else if(cash_out == 1)
        		break;
        	
        }
    }

    public static void round(){
    	
    }
    
    public static int random_choose_dealer(int num_of_players) {         //[1,num_of_players + 1) random int
    	Random r = new Random();
    	return r.nextInt(num_of_players) + 1;                          
    }
}