package com.aditya.implement_swipe_to_delete_in_recycler_view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class NotesCustomAdapter extends RecyclerView.Adapter<NotesCustomAdapter.MyViewHolder> implements Filterable {

    private Context context;

    int position;

    //when we filter our search this movie list will get filtered and will not contain all the movies that were present earlier in this list
    //hence we require a new list type variable and take a backup of the list before we do any sort of filtering
    List<String> movies ;
   // this will contain the list of all the movies without being filtered
    List<String> moviesListWithoutFilter;

    public NotesCustomAdapter(List<String> movies)
    {
        this.movies = movies;
        this.moviesListWithoutFilter = new ArrayList<>(movies);
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //performFiltering is run on a background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //logic of filtering search results
            List<String> filtered_List_of_movies =  new ArrayList<>();
            if(constraint.toString().isEmpty())
            {
                //if the constraint is empty then all the movies must be shown
                filtered_List_of_movies.addAll(moviesListWithoutFilter);
            }
            else
            {
                for(String movie: moviesListWithoutFilter)
                {
                    if(movie.toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        filtered_List_of_movies.add(movie);
                    }
                }
            }
            //create a variable of type filtered results
            FilterResults filterResults = new FilterResults();
            filterResults.values = filtered_List_of_movies;
            //return filterResults; will return the value of this to the publishResults method down below
            return filterResults;
        }
       //it runs on a ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //here we need to update the ui
            movies.clear();
            movies.addAll((Collection<? extends String>) results.values);
            //now notify the recycler view that the data set has been changed
            notifyDataSetChanged();
        }
    };

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
