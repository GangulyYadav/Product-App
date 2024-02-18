package com.project.product;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Toast.makeText(this, "No product data available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        int productId = extras.getInt("productId");
        String productName = extras.getString("productName");
        String productDescription = extras.getString("productDescription");
        double productPrice = extras.getDouble("productPrice");
        double productDiscountPercentage = extras.getDouble("productDiscountPercentage");
        double productRating = extras.getDouble("productRating");
        String productBrand = extras.getString("productBrand");
        String productCategory = extras.getString("productCategory");
        int productStock = extras.getInt("productStock",1);
        String productThumbnail = extras.getString("productThumbnail");
        String[] imagesArray = getIntent().getStringArrayExtra("productImages");
        List<String> imagesList = Arrays.asList(imagesArray);


        ImageView imageView = findViewById(R.id.image_product_details);
        TextView textViewName = findViewById(R.id.text_product_name_details);
        TextView textViewDescription = findViewById(R.id.text_product_description);
        TextView textViewPrice = findViewById(R.id.text_product_price_details);
        TextView textViewDiscount = findViewById(R.id.text_product_discount);
        TextView textViewBrand = findViewById(R.id.text_product_brand);
        TextView textViewRating = findViewById(R.id.text_product_rating);
        TextView textViewCategory = findViewById(R.id.text_product_category);
        TextView textViewStock = findViewById(R.id.text_product_stock);

        textViewName.setText(productName);
        textViewDescription.setText(productDescription);
        textViewPrice.setText(String.format("$%.2f", productPrice));
        double discountPrice = productPrice - productPrice*productDiscountPercentage/100;
        textViewDiscount.setText(String.format("Discount Price - $%.2f",discountPrice));
        textViewBrand.setText("Brand - "+productBrand);
        textViewRating.setText(String.format("Ratings - %.2f",productRating));
        textViewCategory.setText("Category - "+productCategory);
        textViewStock.setText(String.format("Available in Stock - %d",productStock));

        Picasso.get()
                .load(productThumbnail)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageView);

    }
}
