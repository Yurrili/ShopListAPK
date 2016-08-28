package uj.edu.yuri.shoplist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;


import uj.edu.yuri.shoplist.R;
import uj.edu.yuri.shoplist.controller.DataBaseHelperImpl;
import uj.edu.yuri.shoplist.model.ShoppingList;

/**
 * Created by Yuri on 28.08.2016.
 */
public class MainActivity  extends BaseActivity {

    private List<ShoppingList> shoppingList;
    private RecyclerView.Adapter mAdapter;
    private DataBaseHelperImpl sqLiteDatabase;

    RecyclerView mRecyclerView;
    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.activity_main, contentLayout);
        emptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        sqLiteDatabase = new DataBaseHelperImpl(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        initRecyclerView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListViewActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (shoppingList == null) {
            shoppingList = sqLiteDatabase.getLists();
        }

        setmAdapter();
    }

    private void setmAdapter() {
        shoppingList.add(new ShoppingList("huhuhu"));
        shoppingList.add(new ShoppingList("pluuum"));
        // Pair - first : onClick-info, second : onLongClick-edit
        mAdapter = new CustomAdapter(getApplicationContext(), shoppingList);
        mRecyclerView.setAdapter(mAdapter);
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (shoppingList.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

    }
}
