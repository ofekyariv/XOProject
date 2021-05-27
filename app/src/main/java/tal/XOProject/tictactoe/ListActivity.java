package tal.XOProject.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList<Player> players;
    private PlayerAdapter adapter;
    private PlayerDataBase playerDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView = findViewById(R.id.listView);

        //create DB instance;
        playerDataBase  = new PlayerDataBase(this);
        //pull data to Array
        players = PlayerDataBase.getPlayers();

        //players = playerDataBase.getAllRecords();
        adapter = new PlayerAdapter(this, players);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(this);

  }
    public void toMain(View view) {
        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("delete player");
        alert.setMessage("are you sure you want to delete player?");
        alert.setIcon(R.drawable.x);
        alert.setCancelable(false);

        alert.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                playerDataBase.deletePlayerByRow(players.get(position).getId());
                PlayerDataBase.getPlayers().remove(position);
                adapter.notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        });

        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.create();
        alert.show();
        adapter.notifyDataSetChanged();
        return true;
    }
}
