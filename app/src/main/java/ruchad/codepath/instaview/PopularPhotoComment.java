package ruchad.codepath.instaview;

import android.os.Parcel;
import android.os.Parcelable;

public class PopularPhotoComment implements Parcelable{
    private String user;
    private String userComment;
    private String userPic;
    private long userCommentTime;

    PopularPhotoComment(){}

    PopularPhotoComment(Parcel in){
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<PopularPhotoComment> CREATOR = new Parcelable.Creator<PopularPhotoComment>(){
        @Override
        public PopularPhotoComment createFromParcel(Parcel source) {
            return new PopularPhotoComment(source);
        }

        @Override
        public PopularPhotoComment[] newArray(int size) {
            return new PopularPhotoComment[size];
        }
    };

    public String getUser() {return user;}

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public long getUserCommentTime() {
        return userCommentTime;
    }

    public void setUserCommentTime(long userCommentTime) {
        this.userCommentTime = userCommentTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getUser());
        dest.writeString(getUserComment());
        dest.writeString(getUserPic());
        dest.writeLong(getUserCommentTime());

    }

    public void readFromParcel(Parcel in){
        user=in.readString();
        userComment=in.readString();
        userPic=in.readString();
        userCommentTime=in.readLong();
    }
}
