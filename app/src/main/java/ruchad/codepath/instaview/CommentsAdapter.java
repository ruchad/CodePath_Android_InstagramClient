package ruchad.codepath.instaview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class CommentsAdapter extends ArrayAdapter<PopularPhotoComment> {

    public CommentsAdapter(Context context, List<PopularPhotoComment> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PopularPhotoComment photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }
        ImageView ivUserPic = (ImageView) convertView.findViewById(R.id.ivUserPic);
        TextView ivUser = (TextView) convertView.findViewById(R.id.tvUser);
        TextView ivUserComment = (TextView)convertView.findViewById(R.id.tvUserComment);
        TextView ivUserCommentTime = (TextView)convertView.findViewById(R.id.tvCommentTime);
        //set values

        ivUserPic.setImageResource(0);
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(getContext()).load(photo.getUserPic()).transform(transformation).into(ivUserPic);
        ivUser.setText(photo.getUser());
        ivUserComment.setText(photo.getUserComment());
        ivUserCommentTime.setText(Util.calcRelativeTime(photo.getUserCommentTime()));
        return convertView;
    }
}
