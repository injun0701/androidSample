package com.injun.supportlibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class OneFragment extends ListFragment {
    @Override
    //뷰 설정 메서드
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //데이터 생성해서 출력
        String [] datas = {"Java", "Node.js", "Django", "PHP"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, datas);
        setListAdapter(adapter);
    }

    @Override
    //항목을 클릭했을 때 호출되는 메서드
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
