package edu.csp.csc315.contacts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
this class takes the Contacts from MainActivity and puts them into views based on the
contact_row.xml layout. then these views are put into the recycler view on the app
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Contact> contacts;
    private LinearLayout ll;

    public RVAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    // create the view that will display the contact
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_row, parent, false);
        return new MyViewHolder(view);
    }

    // display the contact from the array in the text view
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(contacts.get(position).getName());
        holder.tvPhone.setText(contacts.get(position).getPhone());
        holder.id = contacts.get(position).getId();
    }

    // get the number of items in the array
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    // create the views that will then display the contact to the user in the recycler view
    public class MyViewHolder  extends RecyclerView.ViewHolder{

        TextView tvName, tvPhone;
        int id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            ll= (LinearLayout) itemView.findViewById(R.id.llContact);

            // when a row is clicked, go to the view contact activity "page"
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ViewContactActivity.class);
                    // send the contact info of the clicked item to the ViewContactActivity
                    intent.putExtra("id", id);
                    intent.putExtra("name", contacts.get(getLayoutPosition()).getName());
                    intent.putExtra("phone", contacts.get(getLayoutPosition()).getPhone());
                    intent.putExtra("email", contacts.get(getLayoutPosition()).getEmail());
                    intent.putExtra("address", contacts.get(getLayoutPosition()).getAddress());
                    // start the intent and send the contact info to the ViewContactActivity
                    context.startActivity(intent);
                }
            });
        }
    }
}
