package tdh.ifm.android.imatch.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import tdh.ifm.android.imatch.app.R;

/**
 * Created by tdh on 2017/4/28.
 */

public class DialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private String[] keys;
    private String[] valus;

    private String title;
    private onClickIemListener listener;


    public DialogAdapter(Context context, String[] keys, String[] valus,String title) {
        this.context = context;
        this.keys = keys;
        this.valus = valus;
        this.title =title;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_dialog, parent, false);
        // 创建一个ViewHolder
        DialogAdapter.MyViewHolder holder = new DialogAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            ((DialogAdapter.MyViewHolder) holder).tvItemDialong.setText(title);
        } else {
            ((DialogAdapter.MyViewHolder) holder).tvItemDialong.setText(valus[position - 1]);
            ((DialogAdapter.MyViewHolder) holder).tvItemDialong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != 0) {
                        listener.clickIemListener(keys[position - 1], valus[position - 1]);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (keys == null) {
            return 0;
        }
        return keys.length + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemDialong;

        public MyViewHolder(View view) {
            super(view);
            // 给父控件绑定监听器
            AutoUtils.autoSize(view);
            tvItemDialong = (TextView) view.findViewById(R.id.tv_item_dialog);
        }
    }

    public interface onClickIemListener {
        void clickIemListener(String key, String value);
    }


    public void setListener(DialogAdapter.onClickIemListener listener) {
        this.listener = listener;
    }
}