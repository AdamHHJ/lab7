package temple.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private ArrayList<Book> books = new ArrayList<>();
    private BookListFragment listFragment;
    private BookDetailsFragment detailFragment;
    private EditText etSearch;
    private Button btSearch;
    private ViewPagerFragment viewpagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        etSearch = findViewById(R.id.et_content);
        btSearch = findViewById(R.id.bt_search);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = etSearch.getText().toString();
                searchBooks(key);
            }
        });
        WindowManager wm = getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (height > width) {
            viewpagerFragment = ViewPagerFragment.newInstance();
            fragmentTransaction.replace(R.id.fm_content, viewpagerFragment);
        } else {
            listFragment = BookListFragment.newInstance();
            detailFragment = new BookDetailsFragment();
            fragmentTransaction.replace(R.id.fl_list, listFragment);
            fragmentTransaction.replace(R.id.fl_content, detailFragment);
            listFragment.addSelectListener(new BookListFragment.OnItemSelectedListener() {
                @Override
                public void onItemSelected(Book book) {
                    detailFragment.displayBook(book);
                }
            });
        }
        fragmentTransaction.commit();



    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try{
                JSONArray  bookArray = new JSONArray((String)msg.obj);

                if(viewpagerFragment!=null){
                    for(int i = 0 ; i < bookArray.length(); i++){
                        JSONObject jsonObject = bookArray.optJSONObject(i);
                        Book book = new Book();
                        book.id = jsonObject.optInt("book_id");
                        book.title = jsonObject.optString("title");
                        book.author= jsonObject.optString("author");
                        book.published = jsonObject.optInt("published");
                        book.cover_url= jsonObject.optString("cover_url");
                        books.add(book);
                    }
                    viewpagerFragment.setBooks(books);
                   /* if (books!=null&&books.size()>0) {
                        detailFragment.displayBook(books.get(0));
                    }*/
                }else if (listFragment!=null){
                    listFragment.setBooks(bookArray);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private void searchBooks(final  String key) {
        new Thread(){
            public void run(){
                try{
                    String urlStr = "https://kamorris.com/lab/audlib/booksearch.php?search=" + key;
                    URL url = new URL(urlStr);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    StringBuilder builder = new StringBuilder();
                    String tmpString;

                    while((tmpString = reader.readLine()) != null){
                        builder.append(tmpString);
                    }
                    Log.e("tag",builder.toString());
                    Message msg = Message.obtain();
                    msg.obj = builder.toString();
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
