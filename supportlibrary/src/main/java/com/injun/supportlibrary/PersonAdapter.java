package com.injun.supportlibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    //출력할 데이터 List 생성
    ArrayList<Person> items = new ArrayList<Person>();
    
    //항목뷰를 생성하는 메서드
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.person_item, viewGroup, false);
        return new ViewHolder(itemView);
    }
    
    //데이터 출력을 위한 뷰
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //데이터 찾아오기
        Person item = items.get(position);
        //뷰 홀더를 이용해서 데이터 출력
        viewHolder.setItem(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    //데이터 개수를 설정해주는 메서드
    @Override
    public int getItemCount() {
        return items.size();
    }
    
    
    //사용자 정의 메서드 : items 에 데이터 추가하고 설정하는 메서드
    public void setItems(ArrayList<Person> items) {
        this.items = items;
    }
    
    //데이터 1개를 가져오고 설정하는 메서드
    public Person getItem(int position) {
        return items.get(position);
    }
    
    public void setItem(int position, Person item) {
        items.set(position, item);
    }
    
    //데이터를 마지막에 추가해주는 메서드
    public void addItem(Person item) {
        items.add(item);
    }

    public void setItem(ArrayList<Person> list) {
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //항목 안의 뷰들을 프로퍼티로 선언
        TextView textView;
        TextView textView2;

        //생성자에서 항목 뷰를 대입 받아서 프로퍼티를 초기화 합니다.
        public ViewHolder(View itemView) {
            super(itemView);
            //항목 뷰 안의 뷰를 찾아오기
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }
        public void setItem(Person item) {
            textView.setText(item.getName());
            textView2.setText(item.getPhone());
        }
    }
}
