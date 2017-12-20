package cz.uxes.konqueror_game.game;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.uxes.konqueror_game.R;
import cz.uxes.konqueror_game.UsersListActivity;
import cz.uxes.konqueror_game.WellcomeActivity;
import cz.uxes.konqueror_game.db.Storage;
import cz.uxes.konqueror_game.map.KonquerMap;
import cz.uxes.konqueror_game.network.WsConnection;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class AnswerActivity extends AppCompatActivity {

    final boolean LOOSER = TRUE;
    final boolean WINNER = FALSE;

    private Integer actualRealm;

    private List<String> answers;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        this.context = this;

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        String question = extras.getString("question");
        answers = new ArrayList<>();

        Log.d("aktivita- extra", question);

        ((TextView) findViewById(R.id.question)).setText(question);

        for(int i = 0; i < 4; i++){

            answers.add(i, extras.getString("answer" + i));

        }

        //lets make a copy of answers and set em randomly (cause originally item on position 0 is right)
        List<String> randomizedAnswers = new ArrayList<String>(answers);
        long seed = System.nanoTime();
        Collections.shuffle(randomizedAnswers, new Random(seed));

        Storage storage = new Storage(this);


        ((TextView) findViewById(R.id.viewOpponent)).setText("Opponent: " + extras.getString("opponent"));
        ((TextView) findViewById(R.id.viewNick)).setText("Nick: " + storage.playerInfo().getNick());
        ((TextView) findViewById(R.id.viewAnswers)).setText("Answers left: " + WellcomeActivity.instance.triesLeft);
        ((TextView) findViewById(R.id.viewScore)).setText("Score: " + WellcomeActivity.instance.score);
        ((TextView) findViewById(R.id.viewRealm)).setText("Realm: " + KonquerMap.realms[extras.getInt("realm")].name);

        actualRealm = extras.getInt("realm");


        ((Button) findViewById(R.id.answer0)).setText(randomizedAnswers.get(0));
        ((Button) findViewById(R.id.answer1)).setText(randomizedAnswers.get(1));
        ((Button) findViewById(R.id.answer2)).setText(randomizedAnswers.get(2));
        ((Button) findViewById(R.id.answer3)).setText(randomizedAnswers.get(3));




    }

    @Override
    protected void onPause(){
        super.onPause();

        finish();

    }

    /**
     * @param status 1 = winner, 0 = looser
     */
    public void gameOver(boolean status){
        WellcomeActivity.instance.score = 0;
        WellcomeActivity.instance.triesLeft = 4;

        String message = (status == LOOSER ? "You'v Lost" : "You won");


        new AlertDialog.Builder(this.context)
                .setTitle("Game over")
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .show();
    }

    @OnClick(R.id.exitAnswer)
    public void exitAnswe(View view){
        Log.d("close", "close me plz");
        finish();
        finishActivity(0);
    }

    public void clickAnswer(View view){

        Button butt = (Button) view;

        WellcomeActivity.instance.triesLeft -= 1;


        if(butt.getText() == answers.get(0)){
            Log.d("jo", "tohle je spravna odpoved");
            WellcomeActivity.instance.score += 1;
        }
        else{
            Log.d("jo", "tohle neni spravna odpoved");
        }


        if(WellcomeActivity.instance.score >= 4){
            this.gameOver(WINNER);

            //todo: navyšit score na serveru

            UsersListActivity.ws.incrementUserLevel();

            //todo: navyšit score loklně

            //todo: dodat aktualni realm do pole vyhranych

            //todo: zabarvit jinou barvy ty realmy jenž jsou zabrany

        }
        else if(WellcomeActivity.instance.triesLeft < 0){
            this.gameOver(LOOSER);
        }
        else{
            UsersListActivity.ws.getQuestion(actualRealm);
        }


    }
}
