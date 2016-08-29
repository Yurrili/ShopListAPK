package uj.edu.yuri.shoplist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
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

    @BindView(R.id.new_shopping_item)
    protected EditText newItem;

    @BindView(R.id.title)
    protected EditText title;

    @OnClick(R.id.ic_add)
    public void addNew() {
        if (shoppingList != null) {
            shoppingList.add(sqLiteDatabase.insertItem(newItem.getText().toString(), shoppingList.getId()));
        }
        mAdapter.notifyDataSetChanged();
        newItem.setText("");
    }

    @OnTextChanged(R.id.title)
    public void changeTitle() {
        shoppingList.setTitle(title.getText().toString());
        sqLiteDatabase.updateList(shoppingList);
        Log.d("ListViewActivity", "changed title");
    }

    private static final String list_id = "SHOPPINGLIST_ID";

    private ShoppingList shoppingList;

    private DataBaseHelperImpl sqLiteDatabase;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initLibraries();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        sqLiteDatabase = new DataBaseHelperImpl(getApplicationContext());

        if(shoppingList == null){
            shoppingList = sqLiteDatabase.insertList(title.getText().toString());
        }

        initRecyclerView();
        checkAdapter();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (shoppingList != null) {
            outState.putLong(list_id, shoppingList.getId());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        long id = savedInstanceState.getLong(list_id);
        shoppingList = sqLiteDatabase.getList(id);
        shoppingList = sqLiteDatabase.getListElements(shoppingList);
        mAdapter = new ShoppingAdapter(this, shoppingList);
        mRecyclerView.setAdapter(mAdapter);
        checkAdapter();
    }


    protected void initLibraries(){
        // Library to binding views with valuables
        ButterKnife.bind(this);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void checkAdapter() {

        if(mAdapter == null){
            mAdapter = new ShoppingAdapter(this, shoppingList);
            mRecyclerView.setAdapter(mAdapter);
        }


        if (shoppingList.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                startActivityAfterCleanup(MainActivity.class);
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void startActivityAfterCleanup(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
