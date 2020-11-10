package android.example.simpleasynctask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editSearch;
    private Button buttonSearch;
    private RecyclerView recycleView;
    private ArrayList<ItemData> values;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editSearch=findViewById(R.id.edit_query);
        buttonSearch=findViewById(R.id.button_search);
        recycleView=findViewById(R.id.recycler_view);

        values=new ArrayList<>();
        itemAdapter=new ItemAdapter(this, values);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(itemAdapter);


        /**pengecekan koneksi internet**/
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBooks();

            }
        });
    }
    private void searchBooks(){
        String queryString=editSearch.getText().toString();
        ConnectivityManager connMgr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected() && queryString.length()>0){
            new FetchBook(this, values, itemAdapter, recycleView).
                    execute(queryString);

        }else{
            Toast.makeText(this, "Please check your network connection!", Toast.LENGTH_SHORT).show();
        }

    }
}