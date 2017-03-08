package mouri.app.cameoscientificcalculator;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ExtraActivity extends AppCompatActivity {

    public static boolean extra=false;
    //IDs of all operation buttons
    private int[] opBut={R.id.btnSin};

    //Textview used to display the output
    private TextView txtScreen2;

    //Represent that current state is in error or not
    private boolean stateError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        //Find the textview
        this.txtScreen2=(TextView) findViewById(R.id.txtScreen2);
        //txtScreen.setMovementMethod(new ScrollingMovementMethod());

        //Find and set OnClickListener to operator buttons, equal to button and decimal button
        setOpOnClickListener();
    }

    //Find and set OnClickListener to operator buttons, equal button and decimal button
    private void setOpOnClickListener(){
        //create a common OnClickListener for all operators
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if current state is error, do not append the operator, else append it
                if(!stateError){
                    Button button=(Button) v;
                    //Toast.makeText(getApplicationContext(),button.getText(),Toast.LENGTH_SHORT).show();
                    txtScreen2.append(button.getText());
                    MySingleton.getInstance().setMyString(button.getText().toString()+" ");
                    extra=true;
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        //assign listener to all operator buttons
        for(int id:opBut){
            findViewById(id).setOnClickListener(listener);
        }

        //clear button
        /*findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScreen2.setText("0");//clears screen
                //reset all stages and flags
                stateError=false;
            }
        });

        //equal button
        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onEqual();
            }
        });*/

        findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*private void onEqual(){
        //if the current state is an error, nothing to do.
        //A solution can only be found if the last character is a number
        if(!stateError){
            //read the expression
            String txt=txtScreen.getText().toString();
            //create an Expression(a class from exp4j library)
            Expression expression=new ExpressionBuilder(txt).build();
            try{
                //calculate the result and display
                double result=expression.evaluate();
                txtScreen.setText(Double.toString(result));
            }catch (ArithmeticException e){
                //display an error message
                txtScreen.setText("Error");
                stateError=true;
            }
        }
    }*/
}
