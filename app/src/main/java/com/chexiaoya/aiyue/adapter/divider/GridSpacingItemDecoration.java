package com.chexiaoya.aiyue.adapter.divider;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by xcb on 2019/1/9.
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spanSize;
    private int spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spacing, boolean includeEdge) {
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        spanCount = getSpanCount(parent);
        int column = position % spanCount;
        spanSize = getSpanSize(position, parent);
        if (spanCount == spanSize) {

        } else {
            if (includeEdge) {
                if (spanCount % 4 == 0) {
                    outRect.left = spacing;
                    outRect.right = spacing;
                } else {
                    outRect.left = spacing - column * spacing / spanCount;
                    outRect.right = spacing * (column + 1) / spanCount;
                }
                outRect.top = spacing;
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position > spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }

    /**
     * 获取列数
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    /**
     * e
     * 返回recycleView 设置了setSpanSizeLookup，判断几个单元格合并为一个
     */
    private int getSpanSize(int position, RecyclerView parent) {
        int spanSize = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanSize = ((GridLayoutManager) layoutManager).getSpanSizeLookup().getSpanSize(position);
        }
        return spanSize;
    }
}
