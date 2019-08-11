package itboy.dragrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import itboy.dragrecyclerview.adapter.MyAdapter;
import itboy.dragrecyclerview.bean.Base_Item;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private MyAdapter multiItemTypeAdapter;

    private List<Base_Item> base_items = new ArrayList<>(30);

    private boolean positionChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleView);

        multiItemTypeAdapter = new MyAdapter(this, base_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(multiItemTypeAdapter);

        initTouchListener();

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
                positionChange = true;
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(base_items, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(base_items, i, i - 1);
                    }
                }
                multiItemTypeAdapter.notifyItemMoved(fromPosition,toPosition);

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
                multiItemTypeAdapter.cleanDrag();
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

        });
        itemTouchHelp.attachToRecyclerView(recyclerView);

        multiItemTypeAdapter.setItemTouchHelper(itemTouchHelp);
    }

}
