package temple.edu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ViewPagerFragment extends Fragment {

    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    public static ViewPagerFragment newInstance(ArrayList<String> books) {
        ViewPagerFragment pagerFragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("data",books);
        pagerFragment.setArguments(args);
        return pagerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager,container,false);
        viewPager = view.findViewById(R.id.viewpager);
        ArrayList<String> books = getArguments().getStringArrayList("data");
        pagerAdapter = new PagerAdapter(getChildFragmentManager(),books);
        viewPager.setAdapter(pagerAdapter);

        return view;
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        ArrayList<String> books;
        public PagerAdapter(FragmentManager myFragment,ArrayList<String> books) {
            super(myFragment);
            this.books=books;
        }


        @Override
        public Fragment getItem(int position) {
            return BookDetailsFragment.newInstance(books.get(position));
        }

        @Override
        public int getCount() {
            return books.size();
        }
    }
}
