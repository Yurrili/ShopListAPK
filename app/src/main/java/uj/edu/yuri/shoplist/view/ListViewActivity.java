package uj.edu.yuri.shoplist.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uj.edu.yuri.shoplist.R;

public class ListViewActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    protected RecyclerView recyclerView;

    @BindView(R.id.empty_view)
    protected TextView emptyView;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initLibraries();
        setSupportActionBar(toolbar);

    }

    protected void initLibraries(){
        // Library to binding views with valuables
        ButterKnife.bind(this);
    }

}
