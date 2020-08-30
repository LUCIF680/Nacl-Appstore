package com.nacl.android.Model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

import com.google.android.material.card.MaterialCardView;
import com.nacl.android.R;

public class CardLayout extends NaclModel {
    private Context context;
    private Currency currency = new Currency();
    private String game_name;
    private String company_name;
    private int image_resource;

    public void setImageResource(int image_resource) { this.image_resource = image_resource; }
    public CardLayout(Context context){
        super(context);
        this.context = context;
    }
    public void setPrice(String price,int currency){
        this.currency.setPrice(price);
        this.currency.setCurrency(currency);
    }
    public String getPrice(){return currency.getPrice();}
    public void setGameName(String game_name){ this.game_name = game_name ; }
    public String getGameName(){return game_name;}
    public void setCompanyName(String company_name){ this.company_name = company_name ; }
    public String getCompanyName(){return company_name;}

    public CardView init(){
        MaterialCardView card_view = new MaterialCardView(context);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(dpToPixel(110), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(dpToPixel(8),8,8, dpToPixel(8));
        card_view.setLayoutParams(new android.widget.LinearLayout.LayoutParams(params));

        params.setMargins(0,0,0,0);
        android.widget.LinearLayout linear_layout_one = new LinearLayout(context);
        linear_layout_one.setOrientation(android.widget.LinearLayout.VERTICAL);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        linear_layout_one.setLayoutParams(new LinearLayout.LayoutParams(params));


        ImageView icon = new ImageView(context);
        params.height = dpToPixel(110);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        icon.setContentDescription("ICON");
        icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        icon.setImageResource(image_resource);
        icon.setLayoutParams(new LinearLayout.LayoutParams(params));

        linear_layout_one.addView(icon);

        LinearLayout linear_layout_two = new LinearLayout(context);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        linear_layout_two.setLayoutParams(new LinearLayout.LayoutParams(params));
        linear_layout_two.setOrientation(LinearLayout.VERTICAL);
        linear_layout_two.setPadding(dpToPixel(6), dpToPixel(6), dpToPixel(6), dpToPixel(6));

        /*Title and Company name are added to above liner layout*/
        CardLayout.Text text = new CardLayout.Text(context);
        text.setLayoutMargin(dpToPixel(6),0,0,0);
        text.init(game_name, R.style.TextAppearance_MaterialComponents_Overline,10);
        linear_layout_two.addView(text.getText());

        text = new CardLayout.Text(context);
        text.setLayoutMargin(6, dpToPixel(6),0,0);
        text.init(company_name, R.style.Toolbar_TitleText,5);
        linear_layout_two.addView(text.getText());

        linear_layout_one.addView(linear_layout_two);
        linear_layout_one.addView(new CardLayout.Button(context,currency.getPriceWithCurrencySymbol()).getButton());
        card_view.addView(linear_layout_one);
        return card_view;
    }



    public static class Text{
        TextView description;
        ViewGroup.MarginLayoutParams params;
        public Text(Context context){
            description = new TextView(context);
        }
        public void setLayoutMargin(int left,int top,int right,int bottom){
            params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(left,top,right,bottom);
        }
        public void init(String text, int font,int size) {
            description.setText(text);
            description.setLayoutParams(params);
            description.setTextColor(description.getContext().getResources().getColor(R.color.colorSearchBackground));
            // can't use description as it is not available in api below 23
            TextViewCompat.setTextAppearance(description, font);
            description.setTextSize(size);
        }
        public TextView getText(){
            return description;
        }
    }

    public static class Button{
        android.widget.Button buy_button;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        public Button(Context context,String price){
            buy_button = new android.widget.Button(context);
            buy_button.setText(price);
            buy_button.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            TextViewCompat.setTextAppearance(buy_button, R.style.TextAppearance_MaterialComponents_Overline);
            buy_button.setTextColor(Color.WHITE);
        }
        public void setLayout(int width,int height){
            params = new LinearLayout.LayoutParams(width, height);
        }
        public android.widget.Button getButton(){
            buy_button.setLayoutParams(params);
            return buy_button;
        }
    }
}
