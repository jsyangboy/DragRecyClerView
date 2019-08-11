package itboy.dragrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import itboy.dragrecyclerview.adapter.MyAdapter;
import itboy.dragrecyclerview.bean.A_Item;
import itboy.dragrecyclerview.bean.B_Item;
import itboy.dragrecyclerview.bean.Base_Item;
import itboy.dragrecyclerview.inter.OnItemClickListener;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private MyAdapter myAdapter;

    private List<Base_Item> base_items = new ArrayList<>(30);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleView);

        myAdapter = new MyAdapter(this, base_items);
        myAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, Object object) {
                if (object == null) {
                    Log.e("yqy", "object == null");
                    return;
                }
                Base_Item base_item = null;
                try {
                    base_item = (Base_Item) object;
                } catch (Exception e) {
                    e.getMessage();
                } finally {
                    if (base_item == null) {
                        Log.e("yqy", "base_item == null");
                        return;
                    }

                    switch (base_item.getItemType()) {
                        case A_Item.Item_Tpye:
                            final A_Item a_item = (A_Item) base_item;
                            Toast.makeText(getApplication(), "点击了 A 类：" + a_item.getTitle(), Toast.LENGTH_SHORT).show();
                            break;

                        case B_Item.Item_Tpye:
                            final B_Item b_item = (B_Item) base_item;
                            Toast.makeText(getApplication(), "点击了 B 类：" + b_item.getTitle(), Toast.LENGTH_SHORT).show();
                            break;
                    }

                    Log.e("yqy", "Item_Tpye=" + base_item.getItemType());
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        initTouchListener();

        /**
         * 加载假数据
         */
        loadData();
    }


    private void loadData() {
        base_items.addAll(Const.getAItem());
        base_items.addAll(Const.getBItem());
    }


    private void initTouchListener() {
        final ItemTouchHelper itemTouchHelp = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(base_items, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(base_items, i, i - 1);
                    }
                }
                myAdapter.notifyItemMoved(fromPosition, toPosition);

                Log.e("yqy", "onMove");
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Log.e("yqy", "onSwiped");
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                /*if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setAlpha(0.5f);
                    viewHolder.itemView.setBackgroundColor(0);
                }*/
                switch (actionState) {
                    case ItemTouchHelper.ACTION_STATE_IDLE:
                        Log.e("yqy", "ACTION_STATE_IDLE");
                        //空闲状态
                        break;
                    case ItemTouchHelper.ACTION_STATE_SWIPE:
                        //滑动状态
                        Log.e("yqy", "ACTION_STATE_SWIPE");
                        break;
                    case ItemTouchHelper.ACTION_STATE_DRAG:
                        Log.e("yqy", "ACTION_STATE_DRAG");
                        //拖动状态
                        break;
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setAlpha(1);
                Log.e("yqy", "clearView");
                myAdapter.cleanDrag();
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

        });
        itemTouchHelp.attachToRecyclerView(recyclerView);

        myAdapter.setItemTouchHelper(itemTouchHelp);
    }

}
