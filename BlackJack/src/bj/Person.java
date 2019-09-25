/*
 * @Description: In User Settings Edit
 * @Author: Jun Li
 * @Date: 2019-09-22 18:07:45
 * @LastEditTime: 2019-09-24 19:16:17
 * @LastEditors: Please set LastEditors
 */
package bj;

public class Person{
    private int ID;
    private int balance;

    public Person(int ID, int balance){
        this.ID = ID;
        this.balance = balance;
    }
    
    public void getBalance(){
        this.balance = balance;
    }
    
    public void setBalance(int balance){
        this.balance = balance;
    }

}