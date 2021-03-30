package pollub.ism.tictac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final Button[][] buttons = new Button[3][3];

    private static String KEY_STATE = "state";
    private static String KEY_TURNS = "turn";
    private static String KEY_NAME = "name";

    private static char[][] btn_info;
    private TextView info;

    private int numOfturns = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resourceID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        info = (TextView) findViewById(R.id.textView);
        info.setText("Player 1");

    }

    @Override
    public void onClick(View v) {
        if (numOfturns % 2 == 0) {
            if (checkWin()) {
                Toast.makeText(getApplicationContext(), "Player 2 wins, press reset to reset game", Toast.LENGTH_SHORT).show();
            } else {
                ((Button) v).setText("O");
                ((Button) v).setEnabled(false);
                info.setText("Player 2");
            }

        } else {
            if (checkWin()) {
                Toast.makeText(getApplicationContext(), "Player 1 wins, press reset to reset game", Toast.LENGTH_SHORT).show();
            } else {
                ((Button) v).setText("X");
                ((Button) v).setEnabled(false);
                info.setText("Player 1");
            }
        }

        if (checkWin() && numOfturns % 2 == 0) {
            Toast.makeText(getApplicationContext(), "Player 1 wins, press reset to reset game", Toast.LENGTH_SHORT).show();
        }
        else if (checkWin() && numOfturns % 2 != 0){
            Toast.makeText(getApplicationContext(), "Player 2 wins, press reset to reset game", Toast.LENGTH_SHORT).show();
        }

        numOfturns++;

        if (!checkWin() && numOfturns > 8) {
            Toast.makeText(getApplicationContext(), "Draw, press reset to reset game", Toast.LENGTH_SHORT).show();
        }


    }

    public void clearBoard(View v) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        numOfturns = 0;
        info.setText("Player 1");
    }

    public boolean checkWin() {
        String[][] arr = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = buttons[i][j].getText().toString();
            }
        }

        // check rows
        for (int i = 0; i < 3; i++) {
            if (arr[i][0].equals(arr[i][1]) && arr[i][0].equals(arr[i][2]) && !arr[i][0].equals("")) {
                return true;
            }
        }

        // check columns

        for (int i = 0; i < 3; i++) {
            if (arr[0][i].equals(arr[1][i]) && arr[0][i].equals(arr[2][i]) && !arr[0][i].equals("")) {
                return true;
            }
        }

        // check diagonals

        if (arr[0][0].equals(arr[1][1]) && arr[0][0].equals(arr[2][2]) && !arr[0][0].equals("")) {
            return true;
        }

        if (arr[0][2].equals(arr[1][1]) && arr[0][2].equals(arr[2][0]) && !arr[0][2].equals("")) {
            return true;
        }

        return false;
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String arr[] = new String[9];
        int k = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[k] = buttons[i][j].getText().toString();
                k++;
            }
        }
        outState.putStringArray(KEY_STATE, arr);
        outState.putInt(KEY_TURNS, numOfturns);
        outState.putString(KEY_NAME, info.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int k = 0;
        String arr[];
        arr = savedInstanceState.getStringArray(KEY_STATE);
        numOfturns = savedInstanceState.getInt(KEY_TURNS);
        info.setText(savedInstanceState.getString(KEY_NAME));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(arr[k]);
                if (arr[k] != "") {
                    buttons[i][j].setEnabled(false);
                }
                k++;
            }
        }

    }
}