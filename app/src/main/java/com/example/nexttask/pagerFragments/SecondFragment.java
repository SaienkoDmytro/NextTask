package com.example.nexttask.pagerFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nexttask.R;
import com.example.nexttask.recycler.DataModel;
import com.example.nexttask.recycler.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewSecondFrag = view.findViewById(R.id.recyclerSecond);
        recyclerViewSecondFrag.setLayoutManager(gridLayoutManager);
        List<DataModel> dataHolder = new ArrayList<>();
        DataModel obj1 = new DataModel(R.drawable.iconfinder_small_sun, "Ясно", "+2С");
        dataHolder.add(obj1);
        DataModel obj2 = new DataModel(R.drawable.iconfinder_small_sun, "Ясно", "+2С");
        dataHolder.add(obj2);
        DataModel obj3 = new DataModel(R.drawable.iconfinder_small_sun, "Ясно", "+2С");
        dataHolder.add(obj3);

        recyclerViewSecondFrag.setAdapter(new RecyclerAdapter(dataHolder));

        return view;
    }
}