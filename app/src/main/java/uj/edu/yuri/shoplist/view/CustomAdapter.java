package uj.edu.yuri.shoplist.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import uj.edu.yuri.shoplist.R;
import uj.edu.yuri.shoplist.model.ShoppingList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private List<ShoppingList> items;
    private Context mContext;
//    private final OnItemClickListener listener;
//    private final OnItemLongClickListener listenerLong;
    private Typeface typeFace;

//    public interface OnItemClickListener {
//        void onItemClicked(int position, ShoppingList task);
//    }
//
//    public interface OnItemLongClickListener {
//        boolean onItemLongClicked(int position, ShoppingList task);
//    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    public ShoppingList getItem(int position) {
        return items.get(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

//    public CustomAdapter(Context context, List<ShoppingList> items,
//                         OnItemClickListener listener,
//                         OnItemLongClickListener listenerLong) {
//        this.items = items;
//        this.mContext = context;
//        this.listener = listener;
//        this.listenerLong = listenerLong;
////        this.typeFace = Typeface.createFromAsset(mContext.getAssets(),
////                mContext.getResources().getString(R.string.font_place));
//        Log.d("ADAPTER", "adapter size " + items.size());
//
//    }

    public CustomAdapter(Context context, List<ShoppingList> items) {
        this.items = items;
        this.mContext = context;
        Log.d("ADAPTER", "adapter size " + items.size());

    }

    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_itemlist, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.CustomViewHolder holder, int position) {
        holder.bind(holder, items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_list)
        TextView title;
        @BindView(R.id.items_quantity)
        TextView items_quantity;
        @BindView(R.id.items_quantity_text)
        TextView items_quantity_text;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.fa_calendar)
        TextView fa_calendar;
        @BindView(R.id.fa_shopping_cart)
        TextView fa_shopping_cart;

        @BindString(R.string.items_quantity_text_single)
        String items_quantity_text_single;


        public CustomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(RecyclerView.ViewHolder holder, final ShoppingList item, final int position) {
            fa_calendar.setTypeface(typeFace);
            fa_shopping_cart.setTypeface(typeFace);

            title.setText(item.getTitle());
            date.setText(item.getTimestamp().toString());
            items_quantity.setText(String.valueOf(item.getQuantity()));

            Utility.FontManager.setTypeface(fa_calendar, mContext,Utility.FontManager.FONTAWESOME);
            Utility.FontManager.setTypeface(fa_shopping_cart, mContext,Utility.FontManager.FONTAWESOME);

            if (item.getQuantity() == 1) {
                items_quantity_text.setText(items_quantity_text_single);
            }

        }

    }


}