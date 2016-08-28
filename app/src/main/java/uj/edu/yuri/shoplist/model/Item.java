package uj.edu.yuri.shoplist.model;

import android.support.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Yuri on 28.08.2016.
 */
public class Item {

    @Getter
    @Nullable
    private Long id;

    @Getter
    private String body;

    @Getter
    @Setter
    private boolean done = false;

    private Item(){}

    public Item(long id, String body, boolean done) {
        this.id = id;
        this.body = body;
        this.done = done;
    }

    public Item(String body) {
        this.body  = body;
    }
}
