package com.aditya.implement_swipe_to_delete_in_recycler_view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class NotesCustomAdapter extends RecyclerView.Adapter<NotesCustomAdapter.MyViewHolder>{

    private Context context;

    int position;

    List<String> movies ;

    public NotesCustomAdapter(List<String> movies)
    {
        this.movies = movies;
    }

    @NonNull
    @Override
    public NotesCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //we will be infating our row layout for our RecyclerView
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.note_list_card_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesCustomAdapter.MyViewHolder holder, int position) {
        this.position = position;
        holder.note_title_note_card_view_layout.setText(movies.get(position));
        holder.note_card_view_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),holder.note_title_note_card_view_layout.getText(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.note_card_view_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                movies.remove(position);
//                notifyItemRemoved(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView note_title_note_card_view_layout; //holds the title of a note
        TextView note_id__note_card_view_layout; //holds the id of the note from note.db
        TextView folder_id_note_row; //holds the folder_id
        TextView note_content_id_row; //will hold the contents of the note

        // calling our mainlayout from our recycler_view_row.xml file
        ConstraintLayout note_card_view_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //now getting the id's of those textView's
            note_title_note_card_view_layout = itemView.findViewById(R.id.note_title_note_card_view_layout);
            note_id__note_card_view_layout = itemView.findViewById(R.id.note_id__note_card_view_layout);
            folder_id_note_row = itemView.findViewById(R.id.folder_id_note_row);
            note_content_id_row = itemView.findViewById(R.id.note_content_id_row);

            // binding our mainlayout from our recycler_view_row.xml file to CustomAdapter class
            note_card_view_layout = itemView.findViewById(R.id.note_card_view_layout);
        }
    }
}
