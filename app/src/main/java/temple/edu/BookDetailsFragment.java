package temple.edu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BookDetailsFragment extends Fragment {


    private TextView tvBook;

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("book",book);
        bookDetailsFragment.setArguments(args);
        return bookDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        tvBook = view.findViewById(R.id.tv_name);
        if (getArguments()!=null) {
            Book book = (Book) getArguments().getSerializable("book");
            if (book != null) {
                displayBook(book);
            }
        }
        return view;
    }


    public void displayBook(Book book) {
        tvBook.setText(book.title);
    }


}
