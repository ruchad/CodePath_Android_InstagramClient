package ruchad.codepath.instaview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class PopularPhotoAdapter extends ArrayAdapter<PopularPhoto> {
    public PopularPhotoAdapter(Context context, List<PopularPhoto> objects) {
        super(context,android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PopularPhoto photo = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        ImageView ivProfilePic = (ImageView) convertView.findViewById(R.id.ivProfilePic);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        LinearLayout llComment1 = (LinearLayout) convertView.findViewById(R.id.llCommentsView1);
        TextView tvComment1 = (TextView) llComment1.findViewById(R.id.tvComment);
        TextView tvCommentUser1 = (TextView)llComment1.findViewById(R.id.tvCommentUser);
        LinearLayout llComment2 = (LinearLayout) convertView.findViewById(R.id.llCommentsView2);
        TextView tvComment2 = (TextView) llComment2.findViewById(R.id.tvComment);
        TextView tvCommentUser2 = (TextView)llComment2.findViewById(R.id.tvCommentUser);
        TextView tvViewComments = (TextView) convertView.findViewById(R.id.tvViewComments);
        tvViewComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CommentsActivity.class);
                Bundle args = new Bundle();
                args.putParcelableArrayList("comments", photo.getComments());
                i.putExtras(args);
                getContext().startActivity(i);
            }
        });

        //Set values
        ivProfilePic.setImageResource(0);
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(getContext()).load(photo.getProfileImageURL()).transform(transformation).into(ivProfilePic);
        tvUsername.setText(photo.getUsername());
        tvTimestamp.setText(photo.getRelativeTime());
        tvCaption.setText(photo.getCaption());
        tvLikes.setText(photo.getLikesCount() + " Likes");
        ivPhoto.setImageResource(0); // clear out the (recycled) image
        Picasso.with(getContext()).load(photo.getImageURL()).placeholder(R.drawable.loading_img).fit().centerCrop().into(ivPhoto);
        int count = photo.getComments().size();
        if(count==1) {
            tvCommentUser1.setText(photo.getComments().get(0).getUser());
            tvComment1.setText(photo.getComments().get(0).getUserComment());
        }else if(count>1){
            tvCommentUser1.setText(photo.getComments().get(0).getUser());
            tvComment1.setText(photo.getComments().get(0).getUserComment());

            tvCommentUser2.setText(photo.getComments().get(1).getUser());
            tvComment2.setText(photo.getComments().get(1).getUserComment());
        }
        tvViewComments.setText("View all " + photo.getCommentsCount() + " comments");
        return convertView;
    }
}
