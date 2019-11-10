package temple.edu;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookListFragment extends Fragment {
    private BookAdapter adapter;

    public void addSelectListener(OnItemSelectedListener listener){
        this.listener = listener;
    }





    public interface OnItemSelectedListener{
        void onItemSelected(Book book);
    }
    private OnItemSelectedListener listener;

    ArrayList<Book> books= new ArrayList<>();

    public static BookListFragment newInstance() {
        BookListFragment bookListFragment = new BookListFragment();
        return bookListFragment;
    }

    public void setBooks(JSONArray jsonArray) {
            books.clear();
        for(int i = 0 ; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            Book book = new Book();
            book.id = jsonObject.optInt("book_id");
            book.title = jsonObject.optString("title");
            book.author= jsonObject.optString("author");
            book.published = jsonObject.optInt("published");
            book.cover_url= jsonObject.optString("cover_url");
            books.add(book);
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView listView = (ListView) inflater.inflate(R.layout.fragment_list_book,container,false);
        adapter = new BookAdapter(getActivity() , books);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (listener!=null){
                   listener.onItemSelected(books.get(position));
               }
            }
        });

        adapter.notifyDataSetChanged();

        return listView;
    }

    public class BookAdapter extends BaseAdapter {
        Context context;
        ArrayList<Book> books;

        public BookAdapter(Context context, ArrayList <Book> books){
            this.context = context;
            this.books = books;
        }
        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int position) {
            return books.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(context);
            textView.setText(books.get(position).title);
            textView.setTextSize(20);
            return textView;
        }
    }

}
