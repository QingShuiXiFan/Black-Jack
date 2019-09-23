/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-09-22 18:07:57
 * @LastEditTime: 2019-09-22 19:07:45
 * @LastEditors: Please set LastEditors
 */
package bj;

class Dealer extends Person{
    Cards c;
    public Dealer(){
        super();
        c=new Cards();
    }
    public Card pop(){
        return c.pop();
    }

    /**deal method and auto hit method*/
}