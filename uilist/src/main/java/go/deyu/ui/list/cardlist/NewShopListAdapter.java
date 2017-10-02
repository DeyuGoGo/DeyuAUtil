package go.deyu.ui.list.cardlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import go.deyu.ui.list.R;


public class NewShopListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<?> mDatas;
    protected Context mContext;
    protected LayoutInflater layoutInflater;
    private View.OnClickListener mLisener;
    private int itemWidth = -1;


    public NewShopListAdapter(Context context, List<?> shoplist , View.OnClickListener listener) {
        this.mContext = context;
        mDatas = shoplist;
        this.mLisener = listener;
        this.layoutInflater = LayoutInflater.from(context);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  layoutInflater.inflate(R.layout.listitem_shop_new, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        vh.itemView.setOnClickListener(mLisener);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(itemWidth==-1) {
            ViewTreeObserver viewTreeObserver = holder.itemView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        holder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        final float scale = mContext.getResources().getDisplayMetrics().density;
                        itemWidth = (int) ((holder.itemView.getWidth()- holder.itemView.getPaddingLeft()
                                - holder.itemView.getPaddingRight())/scale);
                    }
                });
            }
        }
        ViewHolder viewholder = (ViewHolder)holder;
        viewholder.name.setText("");
        viewholder.address.setText("");
        viewholder.hours.setText("");
        viewholder.distance.setText("");
        viewholder.heartcount.setSelected(false);
        viewholder.heart.setSelected(true);
        viewholder.heartcount.setText("");
        viewholder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });




    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private int getcount(int size , int a , int b){
        if(a==0)return size;
        size+=a;
        int sum = a+b;
        if(sum>=5)
            return getcount(size,sum/5,sum%5);
        return size;
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        ImageView heart;
        TextView heartcount;
        TextView name;
        TextView address;
        TextView hours;
        TextView distance;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.listitem_shop_tv_name);
            address = (TextView) itemView.findViewById(R.id.listitem_shop_tv_address);
            hours = (TextView) itemView.findViewById(R.id.listitem_shop_tv_time);
            distance = (TextView) itemView.findViewById(R.id.listitem_shop_tv_distance);
            icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            heart = (ImageView)itemView.findViewById(R.id.rating_img_heart);
            heartcount = (TextView)itemView.findViewById(R.id.rating_text_like_count);
        }
    }



}