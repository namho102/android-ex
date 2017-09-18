package example.intentex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);

        resultText = (TextView) findViewById(R.id.resultText);

        Intent callerIntent = getIntent();

        Bundle packBundle = callerIntent.getBundleExtra("MyPackage");

        int a = packBundle.getInt("a");
        int b = packBundle.getInt("b");

        giaiPTBN(a, b);
    }

    public void giaiPTBN(int a, int b) {
        if(a == 0) {
            if(b == 0) {
                resultText.setText("Phuong trinh vo so nghiem");
            }
            else {
                resultText.setText("Phuong trinh vo nghiem");
            }
        }
        else {
            resultText.setText(String.valueOf((float) -b/a));
        }
    }
}
