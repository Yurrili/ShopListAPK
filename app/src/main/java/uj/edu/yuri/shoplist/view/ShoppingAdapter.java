package uj.edu.yuri.shoplist.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import uj.edu.yuri.shoplist.R;
import uj.edu.yuri.shoplist.controller.DataBaseHelperImpl;
import uj.edu.yuri.shoplist.model.Item;
import uj.edu.yuri.shoplist.model.ShoppingList;


/**
 * Created by Yuri on 18.08.2016.
 */
public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.CustomViewHolder> {
    private ShoppingList items;
    private Context mContext;
    private DataBaseHelperImpl SqlDB;


    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public ShoppingAdapter(Context context, ShoppingList items) {
        this.items = items;
        this.mContext = context;
        this.SqlDB = new DataBaseHelperImpl(context);
    }

    @Override
    public ShoppingAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_item, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingAdapter.CustomViewHolder holder, int position) {
        holder.bind(holder, items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        Item item;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.cbItem)
        CheckBox checkbox;

        @OnClick(R.id.ic_trash_listitem)
        void onClick(){
            Log.d("item", "remove " + item.getBody());
            items.remove(item);
            SqlDB.removeItem(item);
            notifyDataSetChanged();

        }

        @OnCheckedChanged(R.id.cbItem)
        void onChecked(){
            Log.d("Item", "checked " + item.getBody());
            items.setDoneTo(item);
            SqlDB.checkItem(item);
        }

        public CustomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(RecyclerView.ViewHolder holder, final Item item, final int position) {
            this.item = item;
            title.setText(item.getBody());
            checkbox.setChecked(item.isDone());

        }

    }


}