package uj.edu.yuri.shoplist.model;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Yuri on 28.08.2016.
 */
public class ShoppingList implements Iterable<Item> {

    @Getter
    @Nullable
    private Long id;

    private List<Item> shoppingList = new ArrayList<>();

    @Getter
    private String title;

    @Getter
    private Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

    @Getter
    @Setter
    private int quantity = 0;

    private ShoppingList(){}

    public ShoppingList(long id, String title, Timestamp timestamp) {
        this.id = id;
        this.title = title;
        this.timestamp = timestamp;
    }

    public ShoppingList(String title) {
        this.title = title;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void add(Optional<Item> a){
        if(a.isPresent())
            shoppingList.add(a.get());
    }

    public void add(Item a){
        if(a != null)
            shoppingList.add(a);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void remove(Optional<Item> a){
        if(a.isPresent())
            shoppingList.remove(a.get());
    }

    public void remove(Item a){
        if(a != null)
            shoppingList.remove(a);
    }

    public void remove(int i){
       shoppingList.remove(i);
    }

    public Item get(int i){
        return shoppingList.get(i);
    }

    @Override
    public Iterator<Item> iterator() {
        return shoppingList.iterator();
    }

}
