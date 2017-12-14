package cz.uxes.konqueror_game.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

import cz.uxes.konqueror_game.network.Player;

/**
 * Created by uxes on 14.12.17.
 */

public class Storage extends SQLiteOpenHelper {
    private Context context;

    public Storage(Context context) {
        super(context, "Konqueror.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user " + "(id TEXT PRIMARY KEY, name TEXT, type INTEGER, cost INTEGER)");
        db.execSQL("CREATE TABLE server " + "(id INTEGER PRIMARY KEY, address TEXT, port INTEGER)");
        db.execSQL("CREATE TABLE score " + "(id INTEGER PRIMARY KEY, name TEXT, score INTEGER)");

        ContentValues values = new ContentValues();
        values.put("id", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));


        db.insert("user", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS server");
        db.execSQL("DROP TABLE IF EXISTS score");
        onCreate(db);
    }

    public List<Player> getScores() {

        //todo: akutalně pseudo fetching
        List<Player> scores = new ArrayList<>();
        scores.add(0, new Player("pepa", "11"));
        scores.add(1, new Player("alexandr", "66"));
        return scores;
    }
}
