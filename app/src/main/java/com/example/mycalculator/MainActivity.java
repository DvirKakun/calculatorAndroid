package com.example.mycalculator;

import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView result ;
    TextView subResult;
    double firstNum = 0;
    double secondNum = 0;

    double resultNum = 0;
    boolean isOperandClicked = false;
    boolean isShouldClearText = true;
    boolean isSecondNumTyped = false;
    char operand = ' ';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        result = findViewById(R.id.textViewResult);
        subResult = findViewById(R.id.textViewSubResult);
    }

    public void numFunc(View view) {
        Button numBtn = (Button) view;
        if(isOperandClicked){
            isSecondNumTyped = true;
        }
        if(isShouldClearText){
            result.setText(numBtn.getText().toString());
        }
        if(isShouldClearText && numBtn.getId() != R.id.numberZero){
            result.setText(numBtn.getText().toString());
            isShouldClearText = false;
        }
        else if(!result.getText().toString().equals("0")) {
            result.append(numBtn.getText().toString());
        }
    }

    public void operationFunc(View view) {
            Button operationBtn = (Button) view;

            if(isSecondNumTyped){
                calc();
            }
            operand = operationBtn.getText().charAt(0);
            isOperandClicked = true;

            if(firstNum == 0){
                firstNum = Double.parseDouble(result.getText().toString());
            }
            isShouldClearText = true;

            subResult.setText(firstNum + " " + operand);
    }

    public void resultFunc(View view) {
        calc();
    }
    private  void calc (){
        secondNum = Double.parseDouble(result.getText().toString());
        try {
            switch (operand) {
                case '+':
                    resultNum = firstNum + secondNum;
                    break;
                case '-':
                    resultNum = firstNum - secondNum;
                    break;
                case '/':
                    resultNum = (double) firstNum / secondNum;
                    break;
                case 'X':
                    resultNum = firstNum * secondNum;
                    break;
            }
            if(operand != ' ') {
                result.setText(resultNum + "");
                subResult.setText(firstNum + " " + operand + " " + secondNum + " = ");
            }
        }
        catch (ArithmeticException e){
            result.setText(e.toString());
        }
        finally {
            clearAll();
        }
    }

    private void clearAll() {
        firstNum = 0;
        secondNum = 0;
        resultNum = 0;
        isOperandClicked = false;
        isSecondNumTyped = false;
        operand = ' ';
        isShouldClearText = true;
    }

    public void clearEnrtyFunc(View view) {
        isShouldClearText = true;
        isSecondNumTyped = false;
        secondNum = 0;
        result.setText("0");

        if (operand == ' ') {
            firstNum = 0;
        }
    }

    public void clear(View view) {
        result.setText("0");
        subResult.setText("");
        clearAll();
    }

    public void deleteFunc(View view) {
        String resultText = result.getText().toString();
        if(isOperandClicked && !isSecondNumTyped){
            return;
        }
        if(resultText.length() == 1){
            result.setText("0");
            isShouldClearText = true;
            isSecondNumTyped = false;
        }else{
            result.setText(resultText.substring(0, resultText.length() - 1));
        }
    }
}