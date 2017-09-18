package example.intentex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText numA, numB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numA = (EditText) findViewById(R.id.numA);
        numB = (EditText) findViewById(R.id.numB);
    }

    public void onClickButton(View v) {
        Intent myIntent = new Intent(MainActivity.this, ResultActivity.class);

        int a = Integer.parseInt(numA.getText().toString());
        int b = Integer.parseInt(numB.getText().toString());

        Bundle bundle = new Bundle();
        bundle.putInt("a", a);
        bundle.putInt("b", b);

        myIntent.putExtra("MyPackage", bundle);

        startActivity(myIntent);
    }
}

