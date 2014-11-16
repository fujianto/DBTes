package com.naisinpo.fujianto.dbtes;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.naisinpo.fujianto.dbtes.data.Comment;
import com.naisinpo.fujianto.dbtes.data.CommentsDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    private CommentsDataSource datasource;
    private ListView listViewComment;
    private Button btnAdd, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Material AppCompat Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        try{
            datasource = new CommentsDataSource(this);
            datasource.open();
        } catch (SQLException e){
            Log.d("MainActivity SQLException", e.getMessage());
        }

        listViewComment = (ListView) findViewById(R.id.listComment);
        btnAdd = (Button) findViewById(R.id.add);
        btnDelete = (Button) findViewById(R.id.delete);
        List<Comment> values = datasource.getAllComments();

        // todo 6: use the Adapter to show the elements in a ListView
        final ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        listViewComment.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = null;
                String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);

                //todo 7: save the new comment to the database, Add it to ListView
                comment = datasource.createComment(comments[nextInt]);
                adapter.add(comment);
                adapter.notifyDataSetChanged();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = null;

                if (adapter.getCount() > 0) {
                    comment = (Comment) adapter.getItem(0);
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onPause() {
        try{
            datasource.open();
        } catch (SQLException e){
            Log.d("MainActivity SQLException", e.getMessage());
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
//        datasource.close();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
