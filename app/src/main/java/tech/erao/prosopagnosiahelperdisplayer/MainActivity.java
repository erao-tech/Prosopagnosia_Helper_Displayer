package tech.erao.prosopagnosiahelperdisplayer;


import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.vuzix.hud.actionmenu.ActionMenuActivity;

public class MainActivity extends ActionMenuActivity {
    private TextView user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_name = findViewById(R.id.user_name);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        single click for the touchpad
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            user_name.setText("Hi, my name is EraO");
        }
        return true;
    }
}
