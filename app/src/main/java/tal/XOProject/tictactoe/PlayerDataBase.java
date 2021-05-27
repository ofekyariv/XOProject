package tal.XOProject.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerDataBase extends SQLiteOpenHelper {
    private static final String DATABASENAME = "score.db";
    private static final String TABLE_RECORD = "tblscore";
    private static final int DATABASEVERSION = 1;
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_VICTORYQUOTE = "VictoryQuote";
    private static final String COLUMN_SCORE = "Score";
    private static final String COLUMN_GENDER = "Gender";
    private static final String[] allColumns = {COLUMN_ID, COLUMN_NAME, COLUMN_VICTORYQUOTE, COLUMN_SCORE, COLUMN_GENDER};
    private static ArrayList<Player> players;

    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE IF NOT EXISTS " +
            TABLE_RECORD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME + " TEXT," +
            COLUMN_VICTORYQUOTE + " TEXT," +
            COLUMN_GENDER + " TEXT," +
            COLUMN_SCORE + " INTEGER );";

    private SQLiteDatabase database;

    public PlayerDataBase(Context context) { 
        super(context, DATABASENAME, null, DATABASEVERSION);
        getAllRecords();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CUSTOMER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        onCreate(sqLiteDatabase);
    }

    public void setRecord(Player player) {
        Player current = null;
        for (Player p: players)
        {
            if(p.getName().equalsIgnoreCase(player.getName()))
            {
                current=p;
                break;
            }
        }
        if(current==null) {
            players.add(player);
            createRecord(player);
        }
        else {
            current.addScore();
            current.setVictoryQuote(player.getVictoryQuote());
            updateByRow(current);
        }
    }

// write record
    public Player createRecord(Player record) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, record.getName());
        values.put(COLUMN_VICTORYQUOTE, record.getVictoryQuote());
        values.put(COLUMN_GENDER, record.getGender());
        values.put(COLUMN_SCORE, record.getScore());
        long id = database.insert(TABLE_RECORD, null, values);
        record.setId(id);
        database.close();
        return record;
    }
// read records
    private ArrayList<Player> getAllRecords() {
        database = getReadableDatabase();
        players = new ArrayList<>();
        String sortOrder = COLUMN_SCORE + " DESC"; // sorting by score
        Cursor cursor = database.query(TABLE_RECORD, allColumns, null, null, null, null, sortOrder);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String victoryQuote = cursor.getString(cursor.getColumnIndex(COLUMN_VICTORYQUOTE));
                String gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
                int score = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                Player record = new Player(name,victoryQuote, score, gender, id);
                players.add(record);
            }
        }
        database.close();
        return players;
    }

//delete record
    public void deletePlayerByRow(long id) {
        database = getWritableDatabase();
        database.delete(TABLE_RECORD, COLUMN_ID + " = " + id, null);
        database.close();
    }

//update record
    public void updateByRow(Player player) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, player.getId());
        values.put(COLUMN_NAME, player.getName());
        values.put(COLUMN_VICTORYQUOTE, player.getVictoryQuote());
        values.put(COLUMN_GENDER, player.getGender());
        values.put(COLUMN_SCORE, player.getScore());
        database.update(TABLE_RECORD, values, COLUMN_ID + "=" + player.getId(), null);
        database.close();
    }

    // setters and getters
    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players) {
        PlayerDataBase.players = players;
    }
}

