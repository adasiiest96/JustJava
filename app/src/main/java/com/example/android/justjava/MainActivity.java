/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 **/
package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        // Finding the user name
        EditText name = new EditText(this);
        name = (EditText) findViewById(R.id.name_description_view);
        String newNmame = name.getText().toString();
        Log.v("MainActivity", newNmame);
        if (newNmame.isEmpty()) {
            Context context = getApplicationContext();
            CharSequence text = "Name is required to order";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        //Find if whipped Cream is checked or not
        CheckBox box = new CheckBox(this);
        box = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean isWhippedCreamOrdered = box.isChecked();

        //Find if Chocolate is checked ir not
        CheckBox box1 = new CheckBox(this);
        box1 = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean isChocokateOrdered = box1.isChecked();

        //Calculate the total price
        int price = calculatePrice(isWhippedCreamOrdered, isChocokateOrdered);

        //Generating Summary Text
        String subject = "Just java Order for " + newNmame;
        String summary = createOrderSummary(price, newNmame, isWhippedCreamOrdered, isChocokateOrdered);
        composeEmail(subject, summary);

    }

    public void composeEmail(String subject, String attachment) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // This method calculates the total price
    private int calculatePrice(boolean isWhippedCream, boolean isChocolate) {
        int basePrice = 5;
        if (isWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (isChocolate) {
            basePrice = basePrice + 2;
        }
        return (quantity * basePrice);
    }

    /*
    * Create the summary of a Order
    * @parm price of the order
    * @return text summary
    */
    private String createOrderSummary(int price, String name, boolean isWhippedCreamOrdered, boolean isChocolateOrdered) {
        String summary = getResources() .getString(R.string.order_summary_name,name);
        summary = summary + "\nAdd Whippped cream? " + isWhippedCreamOrdered;
        summary = summary + "\nAdd Chocolate? " + isChocolateOrdered;
        summary = summary + "\nQuantity : " + quantity;
        summary = summary + "\nTotal : " + price;
        summary = summary + "$\n" + getResources() .getString(R.string.thank_you);

        return summary;

    }

    // This method increaments the quantity
    public void increament(View view) {
        if (quantity == 100) {
            Context context = getApplicationContext();
            CharSequence text = "More than 100 coffess can't be ordered";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    // This method increaments the quantity
    public void decreament(View view) {
        if (quantity == 0) {
            Context context = getApplicationContext();
            CharSequence text = "Less than 0 coffess can't be ordered";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}