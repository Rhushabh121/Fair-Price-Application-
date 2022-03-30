<<<<<<< HEAD
package comp3350.fairprice.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import comp3350.fairprice.business.AccessPosts;
import comp3350.fairprice.objects.Post;
import comp3350.fairprice.databinding.ActivityMainBinding;

import java.util.ArrayList;

//This is the main page o the application that contains the list of posts.
public class MainActivity extends AppCompatActivity {

    private AccessPosts accessPosts;
    private ArrayList<Post> postList;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        accessPosts = new AccessPosts();
        postList = accessPosts.getPosts();

        ListAdapter listAdapter = new ListAdapter(MainActivity.this, postList);

        binding.postList.setAdapter(listAdapter);
        binding.postList.setClickable(true);

        Intent mainIntent = this.getIntent();

        if (mainIntent != null) {
            String title = mainIntent.getStringExtra("title");
            String description = mainIntent.getStringExtra("description");
            String price = mainIntent.getStringExtra("price");
            if (title != null) {
                accessPosts.addPost(new Post(title, description, Integer.valueOf(price)));
            }
        }
    }
//When this method is activated by the newPost button, it goes to the newPostActivity
    public void buttonNewPost(View v) {
        Intent mainIntent = new Intent(this, NewPostActivity.class);
        startActivity(mainIntent);
    }

    public void goToHome(View v) {
        Intent mainIntent = new Intent(this, HomepageActivity.class);
        startActivity(mainIntent);
    }
=======
package comp3350.fairprice.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import comp3350.fairprice.application.Main;
import comp3350.fairprice.business.AccessPosts;
import comp3350.fairprice.objects.Post;
import comp3350.fairprice.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//This is the main page o the application that contains the list of posts.
public class MainActivity extends AppCompatActivity {

    private AccessPosts accessPosts;
    private List<Post> postList;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        copyDatabaseToDevice();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent mainIntent = this.getIntent();
        accessPosts = new AccessPosts();
        if (mainIntent != null) {
            int tempId = mainIntent.getIntExtra("PostId", 2);
            String title = mainIntent.getStringExtra("title");
            String description = mainIntent.getStringExtra("description");
            String price = mainIntent.getStringExtra("price");
            if (title != null) {
                accessPosts.addPost(title, description, Integer.valueOf(price));
            }
        }


        postList = new ArrayList<>();
        postList.addAll(accessPosts.getPosts());

        ListAdapter listAdapter = new ListAdapter(MainActivity.this, postList);

        binding.postList.setAdapter(listAdapter);
        binding.postList.setClickable(true);




    }
//When this method is activated by the newPost button, it goes to the newPostActivity
    public void buttonNewPost(View v) {
        Intent mainIntent = new Intent(this, NewPostActivity.class);
        startActivity(mainIntent);
    }

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            //Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
>>>>>>> fixed-db
}