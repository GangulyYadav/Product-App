package com.project.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recycler_view_product_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList);
        productAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(productAdapter);

        fetchProductsFromAPI();
    }

    private void fetchProductsFromAPI() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://dummyjson.com/products")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        JSONArray jsonArray = jsonObject.getJSONArray("products");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject productObject = jsonArray.getJSONObject(i);

                            JSONArray imagesArray = productObject.getJSONArray("images");
                            List<String> imagesList = new ArrayList<>();
                            for (int j = 0; j < imagesArray.length(); j++) {
                                imagesList.add(imagesArray.getString(j));
                            }

                            Product product = new Product(
                                    productObject.getInt("id"),
                                    productObject.getString("title"),
                                    productObject.getString("description"),
                                    productObject.getDouble("price"),
                                    productObject.getDouble("discountPercentage"),
                                    productObject.getDouble("rating"),
                                    productObject.getString("brand"),
                                    productObject.getInt("stock"),
                                    productObject.getString("category"),
                                    productObject.getString("thumbnail"),
                                    imagesList // Pass the list of images
                            );

                            productList.add(product);
                        }

                        runOnUiThread(() -> productAdapter.notifyDataSetChanged());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Product clickedProduct = productList.get(position);

//        Log.d("CLICK", "CLICKED ON PRODUCT " + productList.get(position));

        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
        intent.putExtra("productId", clickedProduct.getId());
        intent.putExtra("productName", clickedProduct.getTitle());
        intent.putExtra("productDescription", clickedProduct.getDescription());
        intent.putExtra("productPrice", clickedProduct.getPrice());
        intent.putExtra("productDiscountPercentage", clickedProduct.getDiscountPercentage());
        intent.putExtra("productRating", clickedProduct.getRating());
        intent.putExtra("productBrand", clickedProduct.getBrand());
        intent.putExtra("productCategory", clickedProduct.getCategory());
        intent.putExtra("productStock", clickedProduct.getStock());
        intent.putExtra("productThumbnail", clickedProduct.getThumbnail());

        String[] imagesArray = clickedProduct.getImages().toArray(new String[0]);
        intent.putExtra("productImages", imagesArray);

        startActivity(intent);
    }

}
