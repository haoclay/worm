package com.sgq.handle;

/**
 * Created by NAGOYA on 2020/4/14.
 * @author sgq
 */
public class CustomerMoneyException extends Exception{
    public CustomerMoneyException(){
        super();
    }

    public  CustomerMoneyException(String message){
        super(message);
    }
}
