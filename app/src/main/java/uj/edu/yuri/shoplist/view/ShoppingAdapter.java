package uj.edu.yuri.shoplist.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uj.edu.yuri.shoplist.R;
import uj.edu.yuri.shoplist.model.Item;
import uj.edu.yuri.shoplist.model.ShoppingList;


/**
 * Created by Yuri on 18.08.2016.
 */
public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.CustomViewHolder> {
    private ShoppingList items;
    private Context mContext;


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
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.items_quantity)
        TextView items_quantity;
        @BindView(R.id.fa_shopping_cart)
        TextView fa_shopping_cart;


        public CustomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(RecyclerView.ViewHolder holder, final Item item, final int position) {
            title.setText(item.getBody());
        }

    }


}