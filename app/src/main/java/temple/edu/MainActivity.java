package temple.edu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    ArrayList<String> books = new ArrayList<>();
    private BookListFragment listFragment;
    private BookDetailsFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 1; i < 15; i++) {
            books.add("BookCase"+i);
        }

        WindowManager wm = getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (height > width) {
            fragmentTransaction.replace(R.id.fm_content, ViewPagerFragment.newInstance(books));
        } else {
            listFragment = BookListFragment.newInstance(books);
            detailFragment = BookDetailsFragment.newInstance(books.get(0));
            fragmentTransaction.replace(R.id.fl_list, listFragment);
            fragmentTransaction.replace(R.id.fl_content, detailFragment);
            listFragment.addSelectListener(new BookListFragment.OnItemSelectedListener() {
                @Override
                public void onItemSelected(String book) {
                    detailFragment.displayBook(book);
                }
            });
        }
        fragmentTransaction.commit();



    }

}
