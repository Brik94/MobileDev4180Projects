package com.example.citru_000.inclass07;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ContactAdapter adapter;
    ArrayList<Contact> contactList = new ArrayList<Contact>();
    String TAG = "Demo";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



        //adapter.notifyDataSetChanged();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        contactList = mListener.getMainContactList();
        if(contactList.size() > 0)
            Log.d("test", "" +contactList.get(0).name);
        Log.d("test", " " + contactList.size());
        adapter.notifyDataSetChanged();
        Log.d("test", "list resume");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.createButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
                mListener.goToCreateFragment();
            }
        });



       // contactList = new ArrayList<>();
        ListView listView = getView().findViewById(R.id.contactListView);
        adapter = new ContactAdapter(getActivity(), R.layout.contact_list_layout, contactList);
        listView.setAdapter(adapter);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void goToCreateFragment();
        ArrayList<Contact> getMainContactList();
    }

    public class ContactAdapter extends ArrayAdapter<Contact> {
        public ContactAdapter(Context context, int resource, List<Contact> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Contact contact = getItem(position);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_layout, parent, false);

            TextView textViewName = (TextView) convertView.findViewById(R.id.nameTextView);
            TextView textViewEmail = (TextView) convertView.findViewById(R.id.emailTextView);
            TextView textViewPhone = (TextView) convertView.findViewById(R.id.phoneTextView);
            TextView textViewDept = (TextView) convertView.findViewById(R.id.deptTextView);
            ImageView listAvatarView = (ImageView) convertView.findViewById(R.id.listAvatarView);

            //set the data from the email object
            textViewName.setText(contact.name);
            textViewEmail.setText(contact.email);
            textViewPhone.setText(contact.phone);
            textViewDept.setText(contact.dept);
            listAvatarView.setImageDrawable(contact.image);

            return convertView;
        }
    }

}
