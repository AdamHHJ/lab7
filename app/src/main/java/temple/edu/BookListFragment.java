package temple.edu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class BookListFragment extends Fragment {
    public void addSelectListener(OnItemSelectedListener listener){
        this.listener = listener;
    }

    public interface OnItemSelectedListener{
        void onItemSelected(String book);
    }
    private OnItemSelectedListener listener;

    private ArrayList<String> books;

    public static BookListFragment newInstance(ArrayList<String> books) {
        BookListFragment bookListFragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("data",books);
        bookListFragment.setArguments(args);
        return bookListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        books = getArguments().getStringArrayList("data");
        ListView listView = (ListView) inflater.inflate(R.layout.fragment_list_book,container,false);
        listView.setAdapter(new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_1,books));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener!=null){
                    listener.onItemSelected(books.get(position));
                }
            }
        });

        return listView;
    }

}
