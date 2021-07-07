package com.aditya.implement_swipe_to_delete_in_recycler_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView folder_content_view;

    NotesCustomAdapter notesCustomAdapter;

    //creating a listView data that is to be passed on the recyclerView so that list of data can be shown in the recyclerView
    List<String> movies ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        folder_content_view = findViewById(R.id.folder_content_view);

        //initializing ListView
        movies = new ArrayList<>();

        //adding data to our arrayList
        movies.add("agents of shield 1");
        movies.add("agents of shield 2");
        movies.add("hulk");
        movies.add("captiain america");
        movies.add("Ironman");
        movies.add("Ironman2");
        movies.add("Thor");
        movies.add("Avengers");
        movies.add("Captain America 2");
        movies.add("Thor 2");
        movies.add("Ironman3");
        movies.add("Guardians of the galaxy");
        movies.add("Spiderman 1");
        movies.add("Antman");
        movies.add("Avengers 2");
        movies.add("Antman 2");
        movies.add("Spiderman 2");
        movies.add("Captain Marvel");
        movies.add("Black Panther 1");
        movies.add("Captain America 3");
        movies.add("Spiderman homecoming");
        movies.add("Black Panther 2");
        movies.add("Thor 3");
        movies.add("Dr. Strange");
        movies.add("Guardians of the Galaxy 2");
        movies.add("Avenger Infinity war 1");
        movies.add("Venom");
        movies.add("Venom 2");
        movies.add("Avengers EndGame");
        movies.add("Spiderman Far From Home");
        movies.add("Venom 3");

        notesCustomAdapter  = new NotesCustomAdapter(movies);
        folder_content_view.setAdapter(notesCustomAdapter);
        folder_content_view.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //attach SimpleCallback To our recyclerView
         ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simplecallback);
         itemTouchHelper.attachToRecyclerView(folder_content_view);
    }

    //these variables will hold the data incase if user press undo button
    String deleted_note_title=null;
    String getDeleted_note_content=null;

    //for swiping operation always put drag Dirs - 0
    //Enable left swipe to edit
    //Enable right swipe to Delete
    ItemTouchHelper.SimpleCallback simplecallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
    {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
           //this method is only needed when you want to rearrange the rows inside the recyclerView
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            //getting the position of the row of the recyclerView
            int position = viewHolder.getAdapterPosition();

            //this method is used for handeling the swipe
            switch(direction)
            {
                case ItemTouchHelper.LEFT:
                    // add edit note logic open editNote activity
                    Log.i("swipe","left swipe done");
                    break;
                case ItemTouchHelper.RIGHT:
                    //add delete note logic
                    //open a delete dialog box
                    //add material library in gradel file
                    //implementation 'com.google.android.material:material:<version>'
                    deleted_note_title = movies.get(position);

                    //now perform delete operation
                    movies.remove(position);
                    notesCustomAdapter.notifyItemRemoved(position);
                    //send notification to the user

                    //undo using snack bar
                    Snackbar.make(folder_content_view,deleted_note_title, Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    movies.add(position,deleted_note_title);
                                    notesCustomAdapter.notifyItemInserted(position);
                                }
                            }).show();


                    Log.i("swipe","Right swipe done");
                    break;
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //here call your recyclerView Adapter class here in our case it is notesCustomAdapter
                //newtext is the search string entered by the user in the search menu
                notesCustomAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}