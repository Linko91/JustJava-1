package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox wippedCreamCheckBox = (CheckBox) findViewById(R.id.wipped_cream);
        boolean hasWippedCream = wippedCreamCheckBox.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolate.isChecked();

        EditText name = (EditText) findViewById(R.id.name_edit_text);
        String personName = name.getText().toString();

        int price = calculatePrice(hasWippedCream, hasChocolate);
        String priceMessage = createOrderSummary( price, hasWippedCream, hasChocolate, personName );
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Jast Java order for " + personName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public int calculatePrice(boolean addWippedCream, boolean addChocolate) {
        int basePrice = 5;

        if( addChocolate ) {
            basePrice += 2;
        }

        if( addWippedCream) {
            basePrice += 1;
        }

        return basePrice * quantity;
    }

    private String createOrderSummary( int price, boolean addWippedCream, boolean addChocolate, String nameOfPerson ) {
        return ("Name: " + nameOfPerson + "\nAdd Wipped Cream? " + addWippedCream + "\nAdd Chocolate? " + addChocolate +"\n" + "Quantity: " + quantity + "\n" + "Total: $" + price + " \n" + "Thank You!");
    }

    public void increment(View v ) {
        if( quantity < 100 ) {
            quantity += 1;
        } else {
            Toast.makeText(this, "You cannot order more than 100 coffee!", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    public void decrement(View v ) {
        if( quantity > 1 ) {
            quantity -= 1;
        } else {
            Toast.makeText(this, "You cannot order less than 1 coffee!", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}