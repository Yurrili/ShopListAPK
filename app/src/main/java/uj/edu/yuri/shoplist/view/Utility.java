package uj.edu.yuri.shoplist.view;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Yuri on 29.08.2016.
 */
public class Utility {

    public static final class FontManager {

        public static final String ROOT = "font/", FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

        private static Typeface getTypeface(Context context, String font) {
            return Typeface.createFromAsset(context.getAssets(), font);
        }

        public static void setTypeface(TextView txtview, Context ctx, String font){
            txtview.setTypeface(FontManager.getTypeface(ctx, font));
        }

    }
}
