package cz.uxes.konqueror_game.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cz.uxes.konqueror_game.network.Player;
import cz.uxes.konqueror_game.network.Realm;

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
        db.execSQL("CREATE TABLE user " + "(id TEXT PRIMARY KEY, name TEXT, type INTEGER, hostname TEXT, level INTEGER)");
        db.execSQL("CREATE TABLE server " + "(id INTEGER PRIMARY KEY, address TEXT, port INTEGER)");
        db.execSQL("CREATE TABLE score " + "(id INTEGER PRIMARY KEY, name TEXT, score INTEGER)");

        ContentValues values = new ContentValues();
        values.put("id", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        values.put("name", "Franta");
        values.put("hostname", "10.0.0.139");
        values.put("level", "0");
        db.insert("user", null, values);

        //db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS server");
        db.execSQL("DROP TABLE IF EXISTS score");
        onCreate(db);
    }

    public Player playerInfo(){

        SQLiteDatabase db = this.getReadableDatabase();
        onUpgrade(db, 0, 0);

        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        Cursor cursor = db.rawQuery("select * from user where id = ?", new String[] {id});
        cursor.moveToFirst();

        //db.close();

        return new Player(cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("hostname")), id);

    }

    public List<Player> getScores() {

        //todo: akutalně pseudo fetching
        List<Player> scores = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user", new String[]{});

        while (cursor.isAfterLast() == false){
            scores.add(new Player(//todo, fixme: padá to hned po kliknu na menu
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("level")),
                    cursor.getString(cursor.getColumnIndex("hostname"))
            ));
            cursor.moveToNext();
        }
        return scores;
    }

    public void updatePlayer(Player player) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", player.getNick());
        values.put("hostname", player.getHostname());
        values.put("level", player.getLevel());
        String where;
        try{
            where = "id = '" +  player.getId() + "'";
        }
        catch (Exception e){
            where = "name = '" +  player.getNick() + "'";
        }

        db.update("user", values, where, null);

        db.close();

    }
}
