package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonInsert, buttonClear;
    TextView textView;
    RecyclerView recyclerView;
    WordViewModel wordViewModel;
    MyAdapter normalAdapter, cardAdapter;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonClear = findViewById(R.id.buttonclear);
        buttonInsert = findViewById(R.id.buttoninsert);
        aSwitch = findViewById(R.id.switch1);
        textView = findViewById(R.id.tveng);
        recyclerView = findViewById(R.id.recyclerView);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        normalAdapter = new MyAdapter(false, wordViewModel);
        cardAdapter = new MyAdapter(true, wordViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(normalAdapter);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recyclerView.setAdapter(cardAdapter);
                } else {
                    recyclerView.setAdapter(normalAdapter);
                }
            }
        });
        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = normalAdapter.getItemCount();
                normalAdapter.setAllWords(words);
                cardAdapter.setAllWords(words);
                if (temp != words.size()) {
                    normalAdapter.notifyDataSetChanged();
                    cardAdapter.notifyDataSetChanged();
                }
            }
        });
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] english = {
                        "Hello",
                        "World",
                        "Android",
                        "Google",
                        "Studio",
                        "Project",
                        "Database",
                        "Recycler",
                        "View",
                        "String",
                        "Value",
                        "Integer"
                };
                String[] chinese = {
                        "你好",
                        "世界",
                        "安卓系统",
                        "谷歌公司",
                        "工作室",
                        "项目",
                        "数据库",
                        "回收站",
                        "视图",
                        "字符串",
                        "价值",
                        "整数类型"
                };
                for (int i = 0; i < english.length; i++) {
                    wordViewModel.insertWord(new Word(english[i], chinese[i]));
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAllWords();
            }
        });
    }

}
