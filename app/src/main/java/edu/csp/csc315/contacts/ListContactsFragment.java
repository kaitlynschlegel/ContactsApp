package edu.csp.csc315.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListContactsFragment extends Fragment {

    private RecyclerView rv;
    private LinearLayout ll;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_contacts_fragment, container, false);

        // create the RecyclerView
        rv = view.findViewById(R.id.rvContacts);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv.setAdapter(new RVAdapter(getActivity(), ((MainActivity)this.getActivity()).getContacts()));

        return view;
    }
}