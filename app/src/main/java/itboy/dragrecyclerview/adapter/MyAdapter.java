package itboy.dragrecyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import itboy.dragrecyclerview.R;
import itboy.dragrecyclerview.bean.A_Item;
import itboy.dragrecyclerview.bean.B_Item;
import itboy.dragrecyclerview.bean.Base_Item;
import itboy.dragrecyclerview.inter.OnItemClickListener;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Base_Item> showData;
    private ItemTouchHelper itemTouchHelper;
    private Base_Item currentDrageItem = null;

    /**
     * 正常颜色
     */
    private static int color_normal;

    /**
     * 正在拖动item的颜色
     */
    private static int color_Drag;

    /**
     * 拖动状态下，非选中item的颜色
     */
    private static int color_Draging;

    /**
     * 当前是否正在拖动，如果拖动，改变没有被拖动的item的背景颜色
     */
    private boolean draging = false;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    public MyAdapter(Context context, List<Base_Item> showData) {
        this.showData = showData;
        this.context = context;
        color_normal = Color.parseColor("#10000000");
        color_Drag = Color.parseColor("#50000000");
        color_Draging = Color.parseColor("#20000000");

    }

    @Override
    public int getItemViewType(int position) {
        if (showData == null || position > showData.size() - 1) {
            return super.getItemViewType(position);
        } else {
            return showData.get(position).getItemType();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        RecyclerView.ViewHolder holder = null;
        switch (type) {
            case A_Item.Item_Tpye: {
                View itemView = LayoutInflater.from(context).inflate(R.layout.layout_adapter_a_item, viewGroup,
                        false);
                holder = new A_ViewHolder(itemView);
            }
            break;
            case B_Item.Item_Tpye: {
                View itemView = LayoutInflater.from(context).inflate(R.layout.layout_adapter_b_item, viewGroup,
                        false);
                holder = new B_ViewHolder(itemView);
            }
            break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof A_ViewHolder) {
            bindView_A((A_ViewHolder) viewHolder, showData.get(position), position);
        } else {
            bindView_B((B_ViewHolder) viewHolder, showData.get(position), position);
        }
    }


    @Override
    public int getItemCount() {
        return null == showData ? 0 : showData.size();
    }


    /**
     *
     */
    private void bindView_A(final A_ViewHolder holder, final Base_Item base_item, final int position) {
        if (base_item == null) {
            return;
        }

        A_Item a_item = null;
        try {
            a_item = (A_Item) base_item;
        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (a_item == null) {
                return;
            }

            final String title = a_item.getTitle();
            if (!TextUtils.isEmpty(title)) {
                holder.title.setText(title);
            }

            final A_Item finalA_item = a_item;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("yqy", "onClick");

                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickListener(position, base_item);
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    /**
                     * 这里也是为了避免异常情况
                     */
                    if (currentDrageItem != null && currentDrageItem.isDrag()) {
                        currentDrageItem.setDrag(false);
                    }
                    currentDrageItem = base_item;

                    finalA_item.setDrag(true);
                    holder.itemView.setBackgroundColor(color_Drag);

                    draging = true;
                    notifyDataSetChanged();

                    if (itemTouchHelper != null) {
                        //itemTouchHelper.startDrag(holder);
                    }
                    return false;
                }
            });

            if (a_item.isDrag()) {
                holder.itemView.setBackgroundColor(color_Drag);
            } else {
                if (draging) {
                    holder.itemView.setBackgroundColor(color_Draging);
                } else {
                    holder.itemView.setBackgroundColor(color_normal);
                }
            }
        }
    }

    /**
     *
     */
    private void bindView_B(final B_ViewHolder holder, final Base_Item base_item, final int position) {
        if (base_item == null) {
            return;
        }

        B_Item b_item = null;
        try {
            b_item = (B_Item) base_item;
        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (b_item == null) {
                return;
            }

            final String title = b_item.getTitle();
            if (!TextUtils.isEmpty(title)) {
                holder.title.setText(title);
            }

            final B_Item finalB_item = b_item;

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("yqy", "onClick");
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickListener(position, base_item);
                    }
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    /**
                     * 这里也是为了避免异常情况
                     */
                    if (currentDrageItem != null && currentDrageItem.isDrag()) {
                        currentDrageItem.setDrag(false);
                    }
                    currentDrageItem = base_item;

                    finalB_item.setDrag(true);
                    holder.itemView.setBackgroundColor(color_Drag);

                    draging = true;
                    notifyDataSetChanged();

                    if (itemTouchHelper != null) {
                        //itemTouchHelper.startDrag(holder);
                    }
                    return false;
                }
            });

            if (b_item.isDrag()) {
                holder.itemView.setBackgroundColor(color_Drag);
            } else {
                if (draging) {
                    holder.itemView.setBackgroundColor(color_Draging);
                } else {
                    holder.itemView.setBackgroundColor(color_normal);
                }
            }
        }
    }

    public void cleanDrag() {
        boolean isClean = false;
        draging = false;
        if (currentDrageItem != null && currentDrageItem.isDrag()) {
            currentDrageItem.setDrag(false);
            isClean = true;
        }

        /**
         * 这里为了避免异常情况
         */
        if (!isClean) {
            for (Base_Item base_item : showData) {
                if (base_item.isDrag()) {
                    base_item.setDrag(false);
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }


    /**
     * 适配器A
     */
    class A_ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public A_ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }
    }


    /**
     * 适配器B
     */
    class B_ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public B_ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }
    }
}
