package id.jyotisa.praktikumprogmob2021.helper;

import android.view.View;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import id.jyotisa.praktikumprogmob2021.R;

public class CompanyTextDrawable {
    public static void setTextDrawable(String companyName, View view){
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color2 = generator.getColor(String.valueOf(companyName.charAt(0)));

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .endConfig()
                .buildRound(String.valueOf(companyName.charAt(0)), color2);

        ImageView image = (ImageView) view.findViewById(R.id.logo);
        image.setImageDrawable(drawable);
    }
}
