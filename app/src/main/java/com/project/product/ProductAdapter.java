package com.project.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textProductName.setText(product.getTitle());
//        holder.textProductDescription.setText(product.getDescription());
        holder.textProductPrice.setText(String.format("$%.2f", product.getPrice()));

        holder.textProductDiscount.setText(String.format("Discount: %.2f%%", product.getDiscountPercentage()));
        holder.textProductRating.setText(String.format("Rating: %.2f", product.getRating()));
        holder.textProductBrand.setText(String.format("Brand: %s", product.getBrand()));

        Picasso.get()
                .load(product.getThumbnail())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.imageProduct);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textProductName, textProductDescription, textProductPrice;
        TextView textProductDiscount, textProductRating, textProductBrand;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.image_product);
            textProductName = itemView.findViewById(R.id.text_product_name);
            textProductDescription = itemView.findViewById(R.id.text_product_description);
            textProductPrice = itemView.findViewById(R.id.text_product_price);
            textProductDiscount = itemView.findViewById(R.id.text_product_discount);
            textProductRating = itemView.findViewById(R.id.text_product_rating);
            textProductBrand = itemView.findViewById(R.id.text_product_brand);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
