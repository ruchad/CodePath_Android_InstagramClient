package ruchad.codepath.instaview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    private ArrayList<PopularPhotoComment> comments;
    private CommentsAdapter commentsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_comments);
        Bundle extras = getIntent().getExtras();
        comments = extras.getParcelableArrayList("comments");
        commentsAdapter = new CommentsAdapter(getApplicationContext(), comments);
        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        lvComments.setAdapter(commentsAdapter);
    }
}
