package temple.edu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ViewPagerFragment extends Fragment {

    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    ArrayList<BookDetailsFragment> list = new ArrayList<>();
    public static ViewPagerFragment newInstance() {
        ViewPagerFragment pagerFragment = new ViewPagerFragment();
        return pagerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager,container,false);
        viewPager = view.findViewById(R.id.viewpager);

        pagerAdapter = new PagerAdapter(getChildFragmentManager(),list);
        viewPager.setAdapter(pagerAdapter);

        return view;
    }

    public void setBooks(ArrayList<Book> books) {
        list.clear();
        for(int i = 0 ; i < books.size(); i++){
            list.add(BookDetailsFragment.newInstance(books.get(i)));
        }
        pagerAdapter.notifyDataSetChanged();
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        private ArrayList<BookDetailsFragment> pages;
        public PagerAdapter(FragmentManager fm,ArrayList<BookDetailsFragment> pages){
            super(fm);
            this.pages   =pages;

        }

        @Override
        public int getItemPosition( Object object) {
            return PagerAdapter.POSITION_NONE;
        }
        @Override
        public Fragment getItem(int i) {
            return pages.get(i);
        }

        @Override
        public int getCount() {
            return pages.size();
        }
    }
}
