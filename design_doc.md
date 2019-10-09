<!--
 * @Description: In User Settings Edit
 * @Author: Jun Li
 * @Date: 2019-10-08 19:34:09
 * @LastEditTime: 2019-10-08 21:10:01
 * @LastEditors: Please set LastEditors
 -->

### Welcome, man! This is $\downarrow$
# Document for Trianta Ena

## 1. Class Design TBA
Here is the class diagram for Trianta Ena: <br>
![classes](https://github.com/QingShuiXiFan/Black-Jack/blob/TriantaEna/BlackJack/Package%20bj.png)

Brief introduction for every class: <br>
(1) **Person** <br>
The Person class is the parent class for Player (also for Dealer in BlackJack), and it contains the basic information ID and balance. The benefit to set this class is that we can reuse the code if we need other kind of characters except for Player and Dealer.<br>
Changes: none.<br>

(2) **Player** <br>
The Player class contains all the information needed for a Trianta Ena player. It has two methods which is hit and stand. A chooseAction() method  is set to select one of these actions. Other methods such as printCardsInHand(), getBalance(), setBalance(), etc. are also provided.<br>
Changes:<br> 
① Replace leftHand and rightHand with just a single hand.<br>
② Change every detail if there is a distinguish

(3) **Card** <br>
The Card class contains three attributes: suit, realValue, and value which stands for cards suit, true card value and the value in TriantaEna(BlackJack) game, it also provides the
related function to access these attributes.<br>
Changes: none.<br>

(4) **Cards** <br>
The Cards class provides the shuffle() method to provide a random permutation of 52 cards and pop() method to remove one card on the top.<br>
Changes: replace one deck with two decks.<br>

(5) **Round**<br>
The Round class sets the game logic.<br>
Changes:<br> 
① Improved method abstraction.<br>
② Changed game logic<br>

(6) **TriantaEna** <br>
This class can be instantiated in any main() and calls to play() can start a Trianta Ena game.<br>
Changes:<br> 
① Improved method abstraction.<br>
② Made some small changes according to Trianta Ena.<br>

(7) **Judge**<br>
Tool class to serve the game. This class implements some useful methods such as isBust(), isNaturalTE(), isLucky14(), whoWin() etc.<br>
Changes:<br> 
① Added several new methods to adapt to new game rule.<br>
② Set winNumber = 31.<br>

(8) **Main**<br>
Program entrance.<br>
Changes: none.<br>

## 2. Self Assessment
If we knew about the Trianta Ena game in advance, original Black Jack would be different. Reusability of our original design is not good enough. But the difference would not be large, since Person class, Judge class, Card class and Cards class are kinds of same to the original one.

Also, these changes made in the implement of Trianta Ena have made orighinal implementation of Black Jack easier and better!

**That's all. Thank you so much. lol**