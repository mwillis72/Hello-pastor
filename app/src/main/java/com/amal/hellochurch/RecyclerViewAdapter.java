package com.amal.hellochurch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;

import java.util.List;



/**
 * The {@link RecyclerViewAdapter} class.
 * <p>The adapter provides access to the items in the {@link PreacherViewHolder}
 * or the {@link AdViewHolder}.</p>
 */
class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    // The banner ad view type.
    private static final int BANNER_AD_VIEW_TYPE = 1;

    // An Activity's Context.
    private final Context context;

    // The list of banner ads and menu items.
    private final List<Object> recyclerViewItems;
    //URLS
    private static final String URL_TDJAKES= "https://thepottershouse.org/";
    private static final String URL_BENNY = "https://www.bennyhinn.org/";
    private static final String URL_STEVE= "https://stevenfurtick.com/";
    private static final String URL_ROBERT= "https://robertkayanjaministries.org/";
    private static final String URL_OYEDEPO = "http://faithtabernacle.org.ng/";
    private static final String URL_JOEL = "https://www.lakewoodchurch.com/";
    private static final String URL_WILSON = "https://www.youtube.com/channel/UCWAclNnnZxWEqCwil6nmOow";
    private static final String URL_MITALA = "https://advancingnativemissions.com/ugandan-pastor-leads-movement-train-childrens-workers/";
    private static final String URL_JOYCE = "https://joycemeyer.org/";

    //adapterItemClicked variable
//    private AdapterListener mListener;
    /**
     * For this example app, the recyclerViewItems list contains only
     * {@link Preacher} and {@link AdView} types.
     */
    public RecyclerViewAdapter(Context context, List<Object> recyclerViewItems) {
        this.context = context;
        this.recyclerViewItems = recyclerViewItems;
//        this.mListener = mListener;
    }

    /**
     * The {@link PreacherViewHolder} class.
     * Provides a reference to each view in the menu item view.
     */
    public class PreacherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView preacherName;
        private TextView preacherDescription;
        private TextView preacherMinistry;
        private TextView preacherOnlinechannel;
        private ImageView preacherImage;
        private RelativeLayout preacherDetails;
        ItemClickListener itemClickListener;

        private PreacherViewHolder(View view) {
            super(view);
            preacherImage = view.findViewById(R.id.preacher_image);
            preacherDetails = view.findViewById(R.id.preacher_details);
           // preacherDetails.setOnClickListener(this);
            preacherName = view.findViewById(R.id.preacher_name);
            preacherMinistry = view.findViewById(R.id.preacher_ministry);
            preacherOnlinechannel = view.findViewById(R.id.preacher_onlinechannel);
            preacherDescription = view.findViewById(R.id.preacher_description);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
     //mListener.afterAdapterItemClicked(getAdapterPosition());
            this.itemClickListener.onItemClickListener(view, getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }
    }



    private void openURL(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * The {@link AdViewHolder} class.
     */
    public class AdViewHolder extends RecyclerView.ViewHolder {

        AdViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }

    /**
     * Determines the view type for the given position.
     */
    @Override
    public int getItemViewType(int position) {
        return (position % YourPreacher.ITEMS_PER_AD == 0) ? BANNER_AD_VIEW_TYPE
                : MENU_ITEM_VIEW_TYPE;
    }

    /**
     * Creates a new view for a menu item view or a banner ad view
     * based on the viewType. This method is invoked by the layout manager.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
                View preacherLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.preacher_container, viewGroup, false);
                return new PreacherViewHolder(preacherLayoutView);
            case BANNER_AD_VIEW_TYPE:
                // fall through
            default:
                View bannerLayoutView = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.banner_ad_container,
                        viewGroup, false);
                return new AdViewHolder(bannerLayoutView);

        }

    }

    /**
     * Replaces the content in the views that make up the menu item view and the
     * banner ad view. This method is invoked by the layout manager.
     */
     @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
                PreacherViewHolder preacherHolder = (PreacherViewHolder) holder;
                Preacher preacher = (Preacher) recyclerViewItems.get(position);

                // Get the preacher image resource ID.
                String imageName = preacher.getImageName();
                int imageResID = context.getResources().getIdentifier(imageName, "drawable",
                        context.getPackageName());

                // Add the preacher details to the preacher view.
                preacherHolder.preacherImage.setImageResource(imageResID);
                preacherHolder.preacherName.setText(preacher.getName());
                preacherHolder.preacherMinistry.setText(preacher.getMinistry());
                preacherHolder.preacherOnlinechannel.setText(preacher.getOnlinechannel());
                preacherHolder.preacherDescription.setText(preacher.getDescription());
                break;
            case BANNER_AD_VIEW_TYPE:
                // fall through
            default:
                AdViewHolder bannerHolder = (AdViewHolder) holder;
                AdView adView = (AdView) recyclerViewItems.get(position);
                ViewGroup adCardView = (ViewGroup) bannerHolder.itemView;
                // The AdViewHolder recycled by the RecyclerView may be a different
                // instance than the one used previously for this position. Clear the
                // AdViewHolder of any subviews in case it has a different
                // AdView associated with it, and make sure the AdView for this position doesn't
                // already have a parent of a different recycled AdViewHolder.
                if (adCardView.getChildCount() > 0) {
                    adCardView.removeAllViews();
                }
                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView);
                }

                // Add the banner ad to the ad view.
                adCardView.addView(adView);

//                holder.setItemClickListener(new ItemClickListener(){
//                    @Override
//                    public void onItemClickListener(View v, int position){
//                        switch (position) {
//                            case 0:
//                                openURL(URL_TDJAKES);
//                                break;
//                            case 1:
//                                openURL(URL_BENNY);
//                            case 2:
//                                openURL(URL_STEVE);
//                                break;
//                            case 3:
//                                openURL(URL_ROBERT);
//                            case 4:
//                                openURL(URL_OYEDEPO);
//                                break;
//                            case 5:
//                                openURL(URL_JOEL);
//                            case 6:
//                                openURL(URL_WILSON);
//                                break;
//                            case 7:
//                                openURL(URL_MITALA);
//                            case 8:
//                                openURL(URL_JOYCE);
//                        }
//
//                    }
//
//                });

               }

        }


    }


