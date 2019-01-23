package com.chexiaoya.aiyue.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.RecyclerView.RefreshRecyclerView;
import com.chexiaoya.aiyue.RecyclerView.adapter.CommonRecyclerViewAdapter;
import com.chexiaoya.aiyue.RecyclerView.adapter.WrapRecyclerAdapter;
import com.chexiaoya.aiyue.RecyclerView.creater.DefaultRefreshCreator;
import com.chexiaoya.aiyue.RecyclerView.holder.RecyclerViewHolder;
import com.chexiaoya.aiyue.RecyclerView.holder.RecyclerViewHolder.HolderImageLoader;
import com.chexiaoya.aiyue.activity.NewsDetailActivity;
import com.chexiaoya.aiyue.adapter.divider.RecycleViewDivider;
import com.chexiaoya.aiyue.bean.Channel;
import com.chexiaoya.aiyue.bean.NewsDataBean;
import com.chexiaoya.aiyue.bean.NewsRequestJsonBean;
import com.chexiaoya.aiyue.interfaces.INetWorkCallback;
import com.chexiaoya.aiyue.interfaces.OnRefreshListener;
import com.chexiaoya.aiyue.utils.AndroidDeviceUtils;
import com.chexiaoya.aiyue.utils.GlideUtils;
import com.chexiaoya.aiyue.utils.RetrofitManager;
import com.chexiaoya.aiyue.view.dataloading.State;
import com.chexiaoya.aiyue.view.dataloading.StateLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 详情页面
 * Created by xcb on 2019/1/16.
 */
public class DetailsFragment extends BaseFragment implements INetWorkCallback<NewsRequestJsonBean>, OnRefreshListener {

    @BindView(R.id.rv_news)
    RefreshRecyclerView rvNews;
    Unbinder unbinder;
    @BindView(R.id.state_layout)
    StateLayout state_layout;
    private Channel channel;
    private List<NewsDataBean> newsDataBeanList = new ArrayList<>();
    private WrapRecyclerAdapter wrapRecyclerAdapter;
    private DefaultRefreshCreator defaultRefreshCreator;
    private int count;

    public static DetailsFragment newInstance(Channel channel) {
        Bundle args = new Bundle();
        args.putSerializable("channel", channel);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frag_details;
    }

    @Override
    public void initView(Object obj) {
        View rootView = (View) obj;
        unbinder = ButterKnife.bind(this, rootView);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle args = getArguments();
        if (args != null) {
            channel = (Channel) args.getSerializable("channel");
        }
        rvNews.setLayoutManager(new LinearLayoutManager(activity));
        RecycleViewDivider divider = new RecycleViewDivider(activity,
                LinearLayoutManager.HORIZONTAL,
                AndroidDeviceUtils.dip2px(activity, 30),
                getResources().getDrawable(R.drawable.layer_recyclew_divider));
        rvNews.addItemDecoration(divider);
        CommonRecyclerViewAdapter<NewsDataBean> commonRecyclerViewAdapter = new CommonRecyclerViewAdapter<NewsDataBean>(activity,
                newsDataBeanList, R.layout.layout_news_item) {
            @Override
            public void convert(final RecyclerView.ViewHolder holder, final NewsDataBean dataBean) {
                if (holder instanceof RecyclerViewHolder) {
                    final int pos = holder.getAdapterPosition();
                    RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
                    if (dataBean != null) {
                        viewHolder.setText(R.id.tv_news_title, dataBean.getTitle())
                                .setText(R.id.tv_news_date, dataBean.getDate())
                                .setText(R.id.tv_news_author_name, dataBean.getAuthor_name())
                                .setImageByUrl(R.id.iv_news_thumb1, new HolderImageLoader(null) {
                                    @Override
                                    public void displayImage(Context context, ImageView imageView, String mImagePath) {
                                        GlideUtils.loadImage(context, dataBean.getThumbnail_pic_s(), imageView);
                                    }
                                })
                                .setImageByUrl(R.id.iv_news_thumb2, new HolderImageLoader(null) {
                                    @Override
                                    public void displayImage(Context context, ImageView imageView, String mImagePath) {
                                        GlideUtils.loadImage(context, dataBean.getThumbnail_pic_s02(), imageView);
                                    }
                                }).setImageByUrl(R.id.iv_news_thumb3, new HolderImageLoader(null) {
                            @Override
                            public void displayImage(Context context, ImageView imageView, String mImagePath) {
                                GlideUtils.loadImage(context, dataBean.getThumbnail_pic_s03(), imageView);
                            }
                        }).setOnViewClickListener(R.id.iv_news_delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                newsDataBeanList.remove(pos);
                                notifyItemRemoved(pos);
                                notifyItemRangeChanged(pos, newsDataBeanList.size());
                            }
                        }).setOnItemClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                NewsDetailActivity.launch(activity, newsDataBeanList.get(pos));
                            }
                        });

                    }
                }
            }
        };
        wrapRecyclerAdapter = new WrapRecyclerAdapter(commonRecyclerViewAdapter);
        rvNews.setAdapter(wrapRecyclerAdapter);
        defaultRefreshCreator = new DefaultRefreshCreator(this);
        rvNews.addRefreshViewCreator(defaultRefreshCreator);
        state_layout.switchState(State.ING);
        RetrofitManager.getInstance().requestNews(channel, this);

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResponse(NewsRequestJsonBean newsRequestJsonBean) {
        count++;
        if (newsRequestJsonBean != null) {
            if (newsRequestJsonBean.getResult().getData() != null && newsRequestJsonBean.getResult().getData().size() > 0) {
                state_layout.setVisibility(View.GONE);
                rvNews.setVisibility(View.VISIBLE);
                newsDataBeanList.addAll(newsRequestJsonBean.getResult().getData());
                wrapRecyclerAdapter.notifyDataSetChanged();
                if (defaultRefreshCreator != null) {
                    defaultRefreshCreator.updateHeadView(newsRequestJsonBean.getResult().getData().size());
                }
                if (count > 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rvNews.onStopRefresh();
                                }
                            });
                        }
                    }, 3000);
                }

            } else {
                state_layout.setVisibility(View.VISIBLE);
                state_layout.switchState(State.EMPTY);
            }
        } else {
            state_layout.setVisibility(View.VISIBLE);
            state_layout.switchState(State.ERROR);
        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onRefresh() {
        RetrofitManager.getInstance().requestNews(channel, this);
    }
}
