package tdh.ifm.android.imatch.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.bean.FriendInfo;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.Util;

/**
 * Created by tdh on 2017/5/4.
 */

public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<FriendInfo> friendInfos;

    private FriendListAdapter.onClickFriendListItemListener listener;
    public FriendListAdapter(Context context){
        this.context=context;
    }

    public void setData(List<FriendInfo> friendInfos){
        this.friendInfos = friendInfos;
        this.notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_friend, parent, false);
        // 创建一个ViewHolder
        FriendListAdapter.MyViewHolder holder = new FriendListAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FriendInfo friendInfo=friendInfos.get(position);
        if(!TextUtils.isEmpty(friendInfo.getFullName())){
            ((MyViewHolder) holder).tvName.setText(friendInfo.getFullName());
        }
        if (!TextUtils.isEmpty(friendInfo.getMobile())){
            ((MyViewHolder) holder).tvMobile.setText(friendInfo.getMobile());
        }

        if( Constants.USERTYPE_C.equals(friendInfo.getUserType())) {
            ((MyViewHolder) holder).imgHeadUser.setImageResource(R.mipmap.head_driver);
            ((MyViewHolder) holder).llCompanyNmae.setVisibility(View.GONE);
            if (friendInfo.getDriverInfo() == null) {
                ((MyViewHolder) holder).llDriverInformation.setVisibility(View.GONE);
            }else {
                ((MyViewHolder) holder).llDriverInformation.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(friendInfo.getDriverInfo().getPlateNo())) {
                    ((MyViewHolder) holder).btnPlateNumber.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).btnPlateNumber.setText(friendInfo.getDriverInfo().getPlateNo());
                } else {
                    ((MyViewHolder) holder).btnPlateNumber.setVisibility(View.GONE);
                }
                String str="";
                String str1 = "";
                if (!TextUtils.isEmpty(friendInfo.getDriverInfo().getTruckType())){
                    str1=Util.getKeyByValue( Constants.tkcarTypeValues,Constants.tkcartypeKeys,friendInfo.getDriverInfo().getTruckType());
                }
                if (!TextUtils.isEmpty(friendInfo.getDriverInfo().getLength())) {
                    str = str1 +" "+ friendInfo.getDriverInfo().getLength()+"米";
                }
                if (!TextUtils.isEmpty(str)) {
                    ((MyViewHolder) holder).btnDriverType.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).btnDriverType.setText(str);
                } else {
                    ((MyViewHolder) holder).btnDriverType.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(friendInfo.getDriverInfo().getLength())) {
                    ((MyViewHolder) holder).btnLoad.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).btnLoad.setText("载重 " + friendInfo.getDriverInfo().getWeight() + "吨");
                } else {
                    ((MyViewHolder) holder).btnLoad.setVisibility(View.GONE);
                }
            }
        }else if( Constants.USERTYPE_S.equals(friendInfo.getUserType())){
            ((MyViewHolder) holder).imgHeadUser.setImageResource(R.mipmap.head_shipper);
            ((MyViewHolder) holder).llDriverInformation.setVisibility(View.GONE);
            if (friendInfo.getCompanyInfo() == null) {
                ((MyViewHolder) holder).llCompanyNmae.setVisibility(View.GONE);
            }else {
                ((MyViewHolder) holder).llCompanyNmae.setVisibility(View.VISIBLE);
                if(friendInfo.getCompanyInfo()!=null&&!TextUtils.isEmpty(friendInfo.getCompanyInfo().getEnterpriseName()))
                    ((MyViewHolder) holder).tvCompanyNmae.setText(friendInfo.getCompanyInfo().getEnterpriseName());
            }
        }else if(Constants.USERTYPE_L.equals(friendInfo.getUserType())){
            ((MyViewHolder) holder).imgHeadUser.setImageResource(R.mipmap.head_lc);
            ((MyViewHolder) holder).llDriverInformation.setVisibility(View.GONE);
            if (friendInfo.getCompanyInfo() == null) {
                ((MyViewHolder) holder).llCompanyNmae.setVisibility(View.GONE);
            }else {
                ((MyViewHolder) holder).llCompanyNmae.setVisibility(View.VISIBLE);
                if(friendInfo.getCompanyInfo()!=null&&!TextUtils.isEmpty(friendInfo.getCompanyInfo().getEnterpriseName()))
                    ((MyViewHolder) holder).tvCompanyNmae.setText(friendInfo.getCompanyInfo().getEnterpriseName());
            }
        }else if(Constants.USERTYPE_AGENT.equals(friendInfo.getUserType())){
            ((MyViewHolder) holder).imgHeadUser.setImageResource(R.mipmap.head_broker);
            ((MyViewHolder) holder).llDriverInformation.setVisibility(View.GONE);
            ((MyViewHolder) holder).llCompanyNmae.setVisibility(View.GONE);
        }
        ((MyViewHolder) holder).imgTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.dial(context,friendInfo.getMobile());
            }
        });

        ((MyViewHolder) holder).llItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClickFriendListItem(friendInfo);
                return true;
            }
        });
        ((MyViewHolder) holder).llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickFriendListItem(friendInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (friendInfos == null) {
            return 0;
        }
        return friendInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.img_head_portrait)
        ImageView imgHeadPortrait;
        @BindView(R.id.img_tel)
        ImageView imgTel;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.img_head_user)
        ImageView imgHeadUser;
        @BindView(R.id.tv_fixture_number)
        TextView tvFixtureNumber;
        @BindView(R.id.tv_mobile)
        TextView tvMobile;
        @BindView(R.id.ll_driver_information)
        LinearLayout llDriverInformation;
        @BindView(R.id.btn_plate_number)
        Button btnPlateNumber;
        @BindView(R.id.btn_driver_type)
        Button btnDriverType;
        @BindView(R.id.btn_load)
        Button btnLoad;
        @BindView(R.id.ll_company_name)
        LinearLayout llCompanyNmae;
        @BindView(R.id.tv_company_name)
        TextView tvCompanyNmae;

        public MyViewHolder(View view) {
            super(view);
            // 给父控件绑定监听器
            AutoUtils.autoSize(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface onClickFriendListItemListener {
        void onLongClickFriendListItem(FriendInfo friendInfo);
        void onClickFriendListItem(FriendInfo friendInfo);
    }

    public void setListener(FriendListAdapter.onClickFriendListItemListener listener) {
        this.listener = listener;
    }
}
