package com.example.administrator.matata_android.zhzbase.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 单选实现，这里点击不会取消选中
 */
public class SingleCheckHelper  extends CheckHelper {
    private int checked = -1;

    public SingleCheckHelper(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public SingleCheckHelper(RecyclerView recyclerView, int checked) {
        super(recyclerView);
        this.checked = checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    @Override
    void bindViewHolder(final RecyclerView.ViewHolder viewHolder, View clickView) {
        final int position = viewHolder.getAdapterPosition();
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean contain = isCheckedPosition(position);
                if (contain) {
                    handleChange(viewHolder, true);
                } else {
                    if (checked != -1) {
                        RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(checked);
                        if (holder != null) {
                            stateChange(holder, false);
                        }
                    }
                    checked = position;
                    stateChange(viewHolder, true);
                }
                clickWhich(position);
            }
        });
        stateChange(viewHolder, isCheckedPosition(position));
    }


    @Override
    boolean isCheckedPosition(int position) {
        return position == checked;
    }
}
