package mouri.app.cameoscientificcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    //IDs of all numeric buttons
    private int[] numBut={R.id.btnZero,R.id.btnOne,R.id.btnTwo,R.id.btnThree,R.id.btnFour,R.id.btnFive,R.id.btnSix,R.id.btnSeven,R.id.btnEight,R.id.btnNine,R.id.btnBracOpen,R.id.btnBracClose};

    //IDs of all operation buttons
    private int[] opBut={R.id.btnAdd,R.id.btnSubtract,R.id.btnMultiply,R.id.btnDivide};

    //Textview used to display the output
    private TextView txtScreen;

    //Represents whether the last pressed key is numeric or not
    private boolean lastNumeric;

    //Represent that current state is in error or not
    private boolean stateError;

    //To check for only one dot in a number
    private boolean lastDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the textview
        this.txtScreen=(TextView) findViewById(R.id.txtScreen);
        txtScreen.setMovementMethod(new ScrollingMovementMethod());

        //Find and set OnClickListener to numeric buttons
        setNumOnClickListener();

        //Find and set OnClickListener to operator buttons, equal to button and decimal button
        setOpOnClickListener();
    }

    //Find and set OnClickListener to numeric buttons
    private void setNumOnClickListener(){
        //create a common OnClickListener
        View.OnClickListener listener=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //just append/or set the text of clicked button
                Button button=(Button)v;
                if(stateError){
                    //If current state is error, replace the error message
                    txtScreen.setText(button.getText());
                    stateError=false;
                }else{
                    //if no error, the entered expression is correct, so append to it
                    txtScreen.append(button.getText());
                }
                //set the flag
                lastNumeric=true;
            }
        };

        //assign listener to all numeric buttons
        for (int id:numBut){
            findViewById(id).setOnClickListener(listener);
        }
    }

    //Find and set OnClickListener to operator buttons, equal button and decimal button
    private void setOpOnClickListener(){
        //create a common OnClickListener for all operators
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if current state is error, do not append the operator, else append it
                if(lastNumeric && !stateError){
                    Button button=(Button) v;
                    txtScreen.append(button.getText());
                    lastNumeric=false;
                    lastDot=false; //resetting the dot flag
                }
            }
        };

        //assign listener to all operator buttons
        for(int id:opBut){
            findViewById(id).setOnClickListener(listener);
        }

        //decimal point
        findViewById(R.id.btnDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastNumeric && !stateError && !lastDot){
                    txtScreen.append(".");
                    lastNumeric=false;
                    lastDot=true;
                }
            }
        });

        //clear button
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScreen.setText("0");//clears screen
                //reset all stages and flags
                lastDot=false;
                lastNumeric=false;
                stateError=false;
            }
        });

        //changes screen to show more functionalities
        findViewById(R.id.btnXtra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ExtraActivity.class);
                startActivity(intent);
            }
        });

        //equal button
        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }

    //changes the current activity
    private void changeActivity(){
        /*Intent intent=new Intent(this, ExtraActivity.class);
        startActivity(intent);*/
    }

    private void onEqual(){
        //if the current state is an error, nothing to do.
        //A solution can only be found if the last character is a number
        if(lastNumeric && !stateError){
            //read the expression
            String txt=txtScreen.getText().toString();
            //create an Expression(a class from exp4j library)
            Expression expression=new ExpressionBuilder(txt).build();
            try{
                //calculate the result and display
                double result=expression.evaluate();
                txtScreen.setText(Double.toString(result));
                lastDot=true;
            }catch (ArithmeticException e){
                //display an error message
                txtScreen.setText("Error");
                stateError=true;
                lastNumeric=false;
            }
        }
    }
}
