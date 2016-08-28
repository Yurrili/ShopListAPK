package uj.edu.yuri.shoplist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uj.edu.yuri.shoplist.R;
import uj.edu.yuri.shoplist.controller.DataBaseHelperImpl;
import uj.edu.yuri.shoplist.model.Item;
import uj.edu.yuri.shoplist.model.ShoppingList;

public class ListViewActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.empty_view)
    protected TextView emptyView;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    private ShoppingList shoppingList = new ShoppingList("");

    private DataBaseHelperImpl sqLiteDatabase;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initLibraries();
        setSupportActionBar(toolbar);
        sqLiteDatabase = new DataBaseHelperImpl(getApplicationContext());
        initRecyclerView();
    }

    protected void initLibraries(){
        // Library to binding views with valuables
        ButterKnife.bind(this);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        if (shoppingList == null) {
//            shoppingList = sqLiteDatabase.getLists();
//        }

        setmAdapter();
    }

    private void setmAdapter() {

        shoppingList.add(new Item("huhuhu"));
        shoppingList.add(new Item("pluuum"));
        mAdapter = new ShoppingAdapter(this, shoppingList);
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
