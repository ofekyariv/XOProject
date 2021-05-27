package tal.XOProject.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PlayerAdapter extends ArrayAdapter<Player> {

    private Context context;
    private ArrayList<Player> list;

    public PlayerAdapter(@NonNull Context context, ArrayList<Player> list) {
        super(context, R.layout.item_player ,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView= inflater.inflate(R.layout.item_player,parent,false);
        //UserName
        TextView tvUserName = rowView.findViewById(R.id.tvPlayerUN);
        tvUserName.setText(list.get(position).getName());
        //Victory Quote
        TextView tvVictoryQuote = rowView.findViewById(R.id.tvPlayerVQ);
        tvVictoryQuote.setText(""+list.get(position).getVictoryQuote());
        //score
        TextView tvScore = rowView.findViewById(R.id.tvScore);
        tvScore.setText(""+list.get(position).getScore());
        //pic
        ImageView imageView=rowView.findViewById(R.id.imgPlayer);
        if (list.get(position).getGender().equalsIgnoreCase("male"))
        {imageView.setImageResource(R.drawable.male);}
        else {imageView.setImageResource(R.drawable.female);}

        return rowView;
    }
}
