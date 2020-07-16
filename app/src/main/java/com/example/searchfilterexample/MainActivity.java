package com.example.searchfilterexample;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements TextWatcher {
    RecyclerView recyclerView;
    EditText editText;
    RecyclerViewAdapter adapter;

    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recylcerview);
        editText = (EditText)findViewById(R.id.edittext);
      /*
       EditText에 사용자가 텍스트를 입력할 때 사용한다.
       입력되는 텍스트에 변화가 있을때마다 리스터 이벤트가 작동한다.
       */
        editText.addTextChangedListener(this);
        items.add("김씨수정");
        items.add("이씨수정");
        items.add("정씨");
        items.add("박씨");
        items.add("오씨");
        items.add("박씨");
        items.add("금씨");
        items.add("최씨");

        adapter = new RecyclerViewAdapter(getApplicationContext(), items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    @Override //입력하기 전에
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override //입력되는 텍스트에 변화가 있을때
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);
    }

    @Override //입력이 끝났을 때
    public void afterTextChanged(Editable s) {

    }
}
