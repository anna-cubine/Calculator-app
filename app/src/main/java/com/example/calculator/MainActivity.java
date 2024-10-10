package com.example.calculator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import android.view.View;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTV, solutionTV;
    MaterialButton buttonC, buttonOpenParen, buttonCloseParen;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonMul, buttonDiv, buttonSub, buttonAdd, buttonEquals;
    MaterialButton buttonAC, buttonDec;

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

        resultTV = findViewById(R.id.result_tv);
        solutionTV = findViewById(R.id.solution_tv);

        assignID(buttonC, R.id.button_c);
        assignID(buttonOpenParen, R.id.button_open_parenthesis);
        assignID(buttonCloseParen, R.id.button_close_parenthesis);

        assignID(button0, R.id.button_0);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);

        assignID(buttonMul, R.id.button_mul);
        assignID(buttonDiv, R.id.button_divide);
        assignID(buttonSub, R.id.button_sub);
        assignID(buttonAdd, R.id.button_add);
        assignID(buttonEquals, R.id.button_equal);

        assignID(buttonAC, R.id.button_ac);
        assignID(buttonDec, R.id.button_dec);
    }

    /**
     * Method that assigns the ids for all buttons
     * and creates a listener for them
     * @param btn
     * @param id
     */
    void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    /**
     * Method that deals with each buttons functions
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (resultTV.getText().equals("Cubine")) {
            solutionTV.setText("");
            resultTV.setText("0");
        }
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalc = solutionTV.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solutionTV.setText(resultTV.getText());
            return;
        }
        if (buttonText.equals("C")) {
            dataToCalc = dataToCalc.substring(0, dataToCalc.length()-1);
        } else {
            dataToCalc+=buttonText;
        }

        solutionTV.setText(dataToCalc);

        String finalResult = getResults(dataToCalc);
        if(!finalResult.equals("Err")) {
            resultTV.setText(finalResult);
        }
    }

    /**
     * Method that takes the data sent and returns the
     * results whcih will be displayed
     * @param data given data to calculate
     * @return either the result or an error
     */
    String getResults(String data) {
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            return context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
        } catch(Exception e) {
            return "Err";
        }
    }
}