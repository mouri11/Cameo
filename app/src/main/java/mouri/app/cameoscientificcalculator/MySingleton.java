package mouri.app.cameoscientificcalculator;

/**
 * Created by mouri on 5/3/17.
 */
public class MySingleton {
    private volatile static MySingleton mInstance = null;
    private String myString;

    private MySingleton(){
        myString="hello";//initializing variables
    }

    public static MySingleton getInstance() {

        if(mInstance==null)mInstance=new MySingleton();
        return mInstance;
    }

    public String getMyString(){ return this.myString; }
    public void setMyString(String s){ myString=s; }
}
