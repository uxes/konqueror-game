package cz.uxes.konqueror_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WellcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
    }

    public void showJoinGame(View view){
        Intent intent = new Intent(this, UsersListActivity.class);
        startActivity(intent);

    }
}
