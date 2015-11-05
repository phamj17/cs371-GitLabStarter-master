package edu.up.cs371.textmod;

/**
 * class TextModActivity
 * another comment
 * Allow text to be modified in simple ways with button-presses.
 */
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Random;

public class TextModActivity extends ActionBarActivity implements View.OnClickListener{
//this is the TextModActivity class
//Jay's tag
    // array-list that contains our images to display
    private ArrayList<Bitmap> images;

    private Button clear;
    private Button deleteSpaces;
    //private EditText editText;
    private Button lowerButton;
    private Button reverseButton;
    private Button randomButton;
    private Button randChar;
    private Button removePunctuation;

    // instance variables containing widgets
    private ImageView imageView; // the view that shows the image

    private EditText editText;
    private Button makeCap;
    //Justin's changes
    private EditText textField;
    private Button copyName;
    private Spinner spinner;
    private int spinnerPos;
    String[] spinnerNames;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // perform superclass initialization; load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_mod);


        randomButton = (Button) findViewById(R.id.randomButton);
        randomButton.setOnClickListener(this);


        reverseButton = (Button)findViewById(R.id.button4);
        reverseButton.setOnClickListener(this);
        deleteSpaces = (Button)findViewById(R.id.deleteSpaces);
        deleteSpaces.setOnClickListener(this);
        editText = (EditText)findViewById(R.id.editText);
        makeCap = (Button)findViewById(R.id.button6);
        makeCap.setOnClickListener(this);
        // set instance variables for our widgets
        imageView = (ImageView)findViewById(R.id.imageView);


        // Set up the spinner so that it shows the names in the spinner array resources
        //
        // get spinner object
        spinner = (Spinner)findViewById(R.id.spinner);
        // get array of strings
        spinnerNames = getResources().getStringArray(R.array.spinner_names);
        // create adapter with the strings
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, spinnerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        spinner.setAdapter(adapter);

        //Justin's changes
        textField = (EditText)findViewById(R.id.editText);
        copyName = (Button)findViewById(R.id.button2);
        copyName.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                   public void onClick(View v) {
                                           String temp = textField.getText() + spinnerNames[spinnerPos];
                                           textField.setText(temp);
                                       }
                                    });

        randChar = (Button)findViewById(R.id.random_char);
        randChar.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Random alphabet = new Random();
                            String temp = textField.getText() + "";
                            int randCharInt = alphabet.nextInt(127);
                            char randCharString = (char) (randCharInt + 'a');
                            int randPos = alphabet.nextInt(temp.length());
                            textField.setText(temp.substring(0,randPos) + randCharString + temp.substring(randPos,temp.length()));
                        }
                });

        // load the images from the resources
        //
        // create the arraylist to hold the images
        images = new ArrayList<Bitmap>();
        // get array of image-resource IDs
        TypedArray imageIds2 = getResources().obtainTypedArray(R.array.imageIdArray);
        // loop through, adding one image per string
        for (int i = 0; i < spinnerNames.length; i++) {
            // determine the index; use 0 if out of bounds
            int id = imageIds2.getResourceId(i,0);
            if (id == 0) id = imageIds2.getResourceId(0,0);
            // load the image; add to arraylist
            Bitmap img = BitmapFactory.decodeResource(getResources(), id);
            images.add(img);
        }

        // define a listener for the spinner
        spinner.setOnItemSelectedListener(new MySpinnerListener());


        clear = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        lowerButton = (Button) findViewById(R.id.button7);

        clear.setOnClickListener(this);
        lowerButton.setOnClickListener(this);

        removePunctuation = (Button)findViewById(R.id.removePunctuation);
        removePunctuation.setOnClickListener(this);


    }

    public void onClick(View view){
        if(view.getId() == R.id.randomButton){
            String random = editText.getText().toString();
            int length = random.length();


            Random cool = new Random();
            int random2 = 1 + cool.nextInt(length - 1);
            String firsthalf = random.substring(0, random2);
            String secondhalf = random.substring(random2, length);

            editText.setText(secondhalf + firsthalf);
        }


        if(view.getId() == R.id.button){
            editText.setText("");
        }
        if(view.getId() == R.id.deleteSpaces)
        {
            String spaces = editText.getText().toString();
            spaces = spaces.replaceAll("\\s","");
            editText.setText(spaces);
        }
        if(view.getId() == R.id.button7){
            //lowercase
            String lower = editText.getText().toString();
            lower = lower.toLowerCase();
            editText.setText(lower);
        }

        if(view.getId()== R.id.button6)
        {
            String temp = editText.getText().toString().toUpperCase();
            editText.setText(temp);

        }
        if(view.getId() == R.id.button4)
        {
            String myString = editText.getText().toString();
            String reverse = new StringBuffer(myString).reverse().toString();
            editText.setText(reverse);
        }
        if(view.getId() == R.id.removePunctuation)
        {
            String input = editText.getText().toString();
            String temp = input.replaceAll("[^a-zA-Z\\s]", " ");
            editText.setText(temp);
        }

    }
    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_mod, menu);
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * class that handles our spinner's selection events
     */
    private class MySpinnerListener implements OnItemSelectedListener {

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
         *                  android.widget.AdapterView, android.view.View, int, long)
         */
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                   int position, long id) {
            // set the image to the one corresponding to the index selected by the spinner
            imageView.setImageBitmap(images.get(position));
            spinnerPos = spinner.getSelectedItemPosition();
        }

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(
         *                  android.widget.AdapterView)
         */
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }
    }
}
