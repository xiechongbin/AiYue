package com.chexiaoya.aiyue.adapter;

/**
 * Created by xcb on 2019/1/8.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 区别于GridDividerItemDecoration
 * 这个适合有HeaderType,在Header下又有图片类型的，在Adapter中item布局可以不使用recyclerView
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "GridMoreTypeDecoration";
    private int mDividerWidth;          //您所需指定的间隔宽度，主要为第一列和最后一列与父控件的间隔；行间距，列间距将动态分配
    private boolean isNeedSpace = false;//第一列和最后一列是否需要指定间隔(默认不指定)
    private int spanCount = 0;
    private Context mContext;

    /**
     * 意思是存储每一个个HeadView 的之前所有Item包括自己的数量
     */
    private LinkedHashMap<Integer, Integer> headPositionTotalCountMap = new LinkedHashMap<>();
    /**
     * 每一个子Item(非HeadView),存储自己对应的headView的Item数量，
     * 主要用于取余计算时，位置换算
     */
    private LinkedHashMap<Integer, Integer> subItemPositionCountMap = new LinkedHashMap<>();

    /**
     * @param dividerWidth 间隔宽度
     * @param isNeedSpace  第一列和最后一列是否需要间隔
     */
    public RecyclerViewItemDecoration(Context context, int dividerWidth, boolean isNeedSpace) {
        this(context, dividerWidth, 0, isNeedSpace, false);
    }

    /**
     * @param dividerWidth      间隔宽度
     * @param isNeedSpace       第一列和最后一列是否需要间隔
     * @param firstRowTopMargin 第一行顶部是否需要间隔(根据间隔大小判断)
     */
    public RecyclerViewItemDecoration(Context context, int dividerWidth, int firstRowTopMargin, boolean isNeedSpace) {
        this(context, dividerWidth, firstRowTopMargin, isNeedSpace, false);
    }

    /**
     * @param dividerWidth       间隔宽度
     * @param firstRowTopMargin  第一行顶部是否需要间隔
     * @param isNeedSpace        第一列和最后一列是否需要间隔
     * @param isLastRowNeedSpace 最后一行是否需要间隔
     */
    public RecyclerViewItemDecoration(Context context, int dividerWidth, int firstRowTopMargin, boolean isNeedSpace, boolean isLastRowNeedSpace) {
        this(context, dividerWidth, firstRowTopMargin, isNeedSpace, isLastRowNeedSpace, Color.GRAY);
    }

    /**
     * @param dividerWidth       间隔宽度
     * @param firstRowTopMargin  第一行顶部是否需要间隔
     * @param isNeedSpace        第一列和最后一列是否需要间隔
     * @param isLastRowNeedSpace 最后一行是否需要间隔
     */
    public RecyclerViewItemDecoration(Context context, int dividerWidth, int firstRowTopMargin, boolean isNeedSpace, boolean isLastRowNeedSpace, @ColorInt int color) {
        mDividerWidth = dividerWidth;
        this.isNeedSpace = isNeedSpace;
        this.mContext = context;
        //最后一行是否需要间隔(默认不需要)
        boolean isLastRowNeedSpace1 = isLastRowNeedSpace;
        //第一行顶部是否需要间隔
        int mFirstRowTopMargin = firstRowTopMargin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int top = 0;
        int left = 0;
        int right = 0;
        int bottom = 0;

        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        spanCount = getSpanCount(parent);
        int spanSize = getSpanSize(itemPosition, parent);
        Log.e(TAG, "itemPosition ==" + itemPosition);
        Log.e(TAG, "spanCount ==" + spanCount);
        Log.e(TAG, "spanSize ==" + spanSize);

        if (spanSize == spanCount) {//有HeaderView 的情况
            Log.e(TAG, "当前为headView");
            left = mDividerWidth;
            right = mDividerWidth;
            bottom = 0;
            top = itemPosition == 0 ? mDividerWidth : 2 * mDividerWidth;//为了画分隔线，第一行HeaderView不需要画分隔线

            if (!headPositionTotalCountMap.containsKey(itemPosition)) {
                headPositionTotalCountMap.put(itemPosition, itemPosition + 1);
            }
        } else {//类似HeaderView 下的子item
            if (!subItemPositionCountMap.containsKey(itemPosition)) {
                int headViewTotalCount = headPositionTotalCountMap.size() == 0 ? 0 : getMapTail(headPositionTotalCountMap).getValue();
                subItemPositionCountMap.put(itemPosition, headViewTotalCount);
            }
            int maxAllDividerWidth = getMaxDividerWidth(view); //获得最大剩余宽度

            int spaceWidth = 0;//首尾两列与父布局之间的间隔
            if (isNeedSpace)
                spaceWidth = mDividerWidth;

            int eachItemWidth = maxAllDividerWidth / spanCount;//每个Item left+right
            int dividerItemWidth = (maxAllDividerWidth - 2 * spaceWidth) / (spanCount - 1);//item与item之间的距离

            if (subItemPositionCountMap != null) {
                Integer integer = subItemPositionCountMap.get(itemPosition);
                if (integer != null)
                    left = (itemPosition - integer % spanCount * (dividerItemWidth - eachItemWidth) + spaceWidth);
                right = eachItemWidth - left;
            }

            bottom = 0;
            top = mDividerWidth;
        }

        outRect.set(left, top, right, bottom);
    }

    /**
     * 获取最近一个Entry
     */
    private Map.Entry<Integer, Integer> getMapTail(LinkedHashMap<Integer, Integer> headCountMap) {
        Iterator<Map.Entry<Integer, Integer>> iterator = headCountMap.entrySet().iterator();
        Map.Entry tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
        }
        return tail;
    }

    /**
     * 获取Item View的大小，若无则自动分配空间
     * 并根据 屏幕宽度-View的宽度*spanCount 得到屏幕剩余空间
     */
    private int getMaxDividerWidth(View view) {
        int itemWidth = view.getLayoutParams().width;
        int itemHeight = view.getLayoutParams().height;

        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels > mContext.getResources().getDisplayMetrics().heightPixels
                ? mContext.getResources().getDisplayMetrics().heightPixels : mContext.getResources().getDisplayMetrics().widthPixels;

        int maxDividerWidth = screenWidth - itemWidth * spanCount;
        if (itemHeight < 0 || itemWidth < 0 || (isNeedSpace && maxDividerWidth <= (spanCount - 1) * mDividerWidth)) {
            view.getLayoutParams().width = getAttachColumnWidth();
            view.getLayoutParams().height = getAttachColumnWidth();

            maxDividerWidth = screenWidth - view.getLayoutParams().width * spanCount;
        }
        return maxDividerWidth;
    }

    /**
     * 根据屏幕宽度和item数量分配 item View的width和height
     */
    private int getAttachColumnWidth() {
        int itemWidth = 0;
        int spaceWidth = 0;
        try {
            int width = mContext.getResources().getDisplayMetrics().widthPixels > mContext.getResources().getDisplayMetrics().heightPixels
                    ? mContext.getResources().getDisplayMetrics().heightPixels : mContext.getResources().getDisplayMetrics().widthPixels;
            if (isNeedSpace)
                spaceWidth = 2 * mDividerWidth;
            itemWidth = (width - spaceWidth) / spanCount - 40;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemWidth;
    }

    /**
     * 判读是否是第一列
     */
    private boolean isFirstColumn(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return pos % spanCount == 0;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                return pos % spanCount == 0;
            }
        }
        return false;
    }

    /**
     * 判断是否是最后一列
     */
    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return (pos + 1) % spanCount == 0;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                return (pos + 1) % spanCount == 0;
            }
        }
        return false;
    }

    /**
     * 判读是否是最后一行
     */
    private boolean isLastRow(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int lines = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
            return lines == pos / spanCount + 1;
        }
        return false;
    }

    /**
     * 判断是否是第一行
     */
    private boolean isFirstRow(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return (pos / spanCount + 1) == 1;
        }
        return false;
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
