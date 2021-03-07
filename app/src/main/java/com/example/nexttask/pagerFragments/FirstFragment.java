package com.example.nexttask.pagerFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nexttask.R;
import com.example.nexttask.recycler.DataModel;
import com.example.nexttask.recycler.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {

    private DataPassListener mCallback;

    public interface DataPassListener{
        void passFirstData(int data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        RecyclerView recyclerViewFirst = view.findViewById(R.id.recyclerFirst);
        recyclerViewFirst.setLayoutManager(new LinearLayoutManager(getContext()));
        List<DataModel> dataHolder = new ArrayList<>();
        DataModel obj1 = new DataModel(R.drawable.iconfinder_small_sun, "Сегодня - Ясно", "-6С/+2С");
        dataHolder.add(obj1);
        DataModel obj2 = new DataModel(R.drawable.iconfinder_small_sun, "Сегодня - Ясно", "-6С/+2С");
        dataHolder.add(obj2);
        DataModel obj3 = new DataModel(R.drawable.iconfinder_small_sun, "Сегодня - Ясно", "-6С/+2С");
        dataHolder.add(obj3);

        recyclerViewFirst.setAdapter(new RecyclerAdapter(dataHolder));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = view.findViewById(R.id.additional);
        button.setOnClickListener(v -> mCallback.passFirstData(1));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DataPassListener) {
            mCallback = (DataPassListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement DataPassListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

}