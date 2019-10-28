package temple.edu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BookDetailsFragment extends Fragment {


    private TextView tvBook;

    public static BookDetailsFragment newInstance(String title) {
        BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        bookDetailsFragment.setArguments(args);
        return bookDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        tvBook = view.findViewById(R.id.tv_name);
        String title = getArguments().getString("title");
        displayBook(title);
        return view;
    }


    public void displayBook(String title) {
        tvBook.setText(title);
    }


}
