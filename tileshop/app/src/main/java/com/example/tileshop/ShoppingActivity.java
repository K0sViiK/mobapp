package com.example.tileshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ShoppingActivity extends AppCompatActivity {

    private static final String LOG_TAG = ShoppingActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private ArrayList<ShoppingItem> mItemList;
    private ShoppingItemAdapter mAdapter;

    private FrameLayout thatRedCircle;
    private TextView contentTextView;


    private int gridNumber = 1;
    private int cartitems = 0;

    private boolean viewRow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shopping);

            user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Log.d(LOG_TAG,"Authenticated user!");
            } else {
                Log.d(LOG_TAG,"Unauthenticated user! Terminating session!");
                finish();
            }

            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this,gridNumber));
            mItemList = new ArrayList<>();

            mAdapter = new ShoppingItemAdapter(this, mItemList);

            mRecyclerView.setAdapter(mAdapter);

            initializeData();
        }

    private void initializeData() {
        String [] itemsList = getResources().getStringArray(R.array.shopping_item_names);
        String [] itemsInfo = getResources().getStringArray(R.array.shopping_item_description);
        String [] itemsPrice = getResources().getStringArray(R.array.shopping_item_prices);
        TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.shopping_item_images);


        mItemList.clear();

        for(int i=0;i< itemsList.length;i++){
            mItemList.add(new ShoppingItem(itemsList[i],itemsInfo[i],itemsPrice[i],itemsImageResource.getResourceId(i,0)));
        }

        itemsImageResource.recycle();

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.shoplistmenu,menu);
        MenuItem menuItem = menu.findItem(R.id.searchfield);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG,s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.logout:
                Log.d(LOG_TAG,"Log out clicked.");
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.settings:
                Log.d(LOG_TAG,"Settings clicked.");
                return true;
            case R.id.cart:
                Log.d(LOG_TAG,"Cart clicked.");
                return true;
            case R.id.viewselector:
                Log.d(LOG_TAG,"View change clicked.");
                if(viewRow){
                    changeSpanCount(item,R.drawable.grid,1);
                }else {
                    changeSpanCount(item,R.drawable.vert,1);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void changeSpanCount (MenuItem item, int drawableId, int spanCount){
        viewRow = !viewRow;
        item.setIcon(drawableId);
        GridLayoutManager layoutManager = (GridLayoutManager)  mRecyclerView.getLayoutManager();
        layoutManager.setSpanCount(spanCount);

    }




}
