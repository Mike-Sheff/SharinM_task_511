package ru.netologia.sharinm_task_511;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    private ItemDataAdapter adapter;
    private String[][] masList;
    private File myFile;
    private ListView listView;
    private static final String FILE_NAME = "myFile.txt";

    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), FILE_NAME);

        Toolbar toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listView);

        // создаем файл, если отсутствует
        if (!myFile.exists()) {
            try {
                FileWriter fileWriter = new FileWriter(myFile);
                for(int i=0 ; i <6; i++) {
                    fileWriter.write(getString(R.string.textSample,
                                                getString(R.string.textLesson, i+1 ),
                                                getString(R.string.textTheme, i+1 )));
                }
                fileWriter.close();

                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        readFile();

        setSupportActionBar(toolbar);

        adapterAdd();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFile();
                String lesson = getString(R.string.textLesson, (adapter.getCount() + 1));
                String theme = getString(R.string.textTheme, (adapter.getCount() + 1));
                try {
                    FileWriter fileWriter = new FileWriter(myFile, true);
                    fileWriter.write(getString(R.string.textSample, lesson, theme));
                    fileWriter.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                readFile();
                adapterAdd();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                showItemData(position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.removeItem(position);
                return true;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveFile();
    }

    private void saveFile(){
        try {
            PrintWriter printWriter = new PrintWriter(myFile);
            printWriter.print("");
            printWriter.close();

            FileWriter fileWriter = new FileWriter(myFile);
            //    fileWriter.write(String.valueOf(("").getBytes()));
            for(int i=0; i < adapter.getCount();i++) {
                fileWriter.write(adapter.getItem(i).getTitle() + "$" + adapter.getItem(i).getSubtitle() + ";");
            }
            fileWriter.close();

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void adapterAdd(){
        adapter = new ItemDataAdapter(this, null);

        for (int i = 0; i < masList.length; i++) {
            adapter.addItem(new ItemData(masList[i][0], masList[i][1], ContextCompat.getDrawable(MainActivity.this, R.drawable.android1)));
        }
        listView.setAdapter(adapter);
    }

    private  void readFile(){
        if (myFile.canRead()) {
            myFile.list();
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(myFile));

                String[] mas = bufferedReader.readLine().split(";");

                masList = new String[mas.length][2];

                for (int i = 0; i < mas.length; i++) {
                    String[] mas_mas = mas[i].split("\\$");
                    masList[i][0] = mas_mas[0];
                    masList[i][1] = mas_mas[1];
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showItemData(int position) {
        ItemData itemData = adapter.getItem(position);
        Toast.makeText(MainActivity.this,
                "Тема: " + itemData.getTitle() + "\n" + "Подтема: " + itemData.getSubtitle() + "\n",
                Toast.LENGTH_SHORT).show();
    }
}
