package com.example.android.lerno;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.lerno.Adapter.TitleAdapter;
import com.example.android.lerno.Interface.TitleClickListener;
import com.example.android.lerno.Model.Constants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context mContex;
    private ArrayList<String> titleList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContex = MainActivity.this;

        titleList = new ArrayList<String>();
        titleList.add(Constants.TITLEA);
        titleList.add(Constants.TITLEB);
        titleList.add(Constants.TITLEC);
        titleList.add(Constants.TITLED);
        titleList.add(Constants.TITLEE);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContex,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        TitleAdapter titleAdapter = new TitleAdapter(mContex, titleList, new TitleClickListener() {
            @Override
            public void onItemClick(View itemview, int position) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("titles",titleList.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(titleAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menu_quiz){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Read all chapters carefully before taking quiz.");
            builder.setTitle("Take quiz?");
            builder.setPositiveButton("Start Quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MainActivity.this, QuizMainActivity.class );
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (id==R.id.menu_about){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class );
            startActivity(intent);
        }
        if (id==R.id.menu_exit){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
