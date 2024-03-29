package com.andzoid.spotifry;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TracksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TracksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TracksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TracksFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static TracksFragment newInstance(String param1, String param2) {
//        TracksFragment fragment = new TracksFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    //private TrackAdapter trackAdapter;
    private ArrayList<ParcelableTrack> tracks;


    public TracksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null || !savedInstanceState.containsKey("tracks")) {

            this.tracks = new ArrayList<ParcelableTrack>();
        }
        else {

            this.tracks = savedInstanceState.getParcelableArrayList("tracks");
        }

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle b) {

        b.putParcelableArrayList("tracks", this.tracks);

        super.onSaveInstanceState(b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TrackAdapter trackAdapter = new TrackAdapter(getActivity(), this.tracks);


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tracks, container, false);

        ListView listView = (ListView) v.findViewById(R.id.listView);
        listView.setAdapter(trackAdapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (this.tracks.isEmpty()) {

            Intent intent = getActivity().getIntent();

            String s = intent.getStringExtra("ARTIST");

            SpotifyArtistTopTenTracksAsyncTask task = new SpotifyArtistTopTenTracksAsyncTask();
            task.execute(s);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }


    public class SpotifyArtistTopTenTracksAsyncTask extends AsyncTask<String, Void, List<Track>> {

        @Override
        protected List<Track> doInBackground(String... s) {

            SpotifyApi api = new SpotifyApi();
            SpotifyService service = api.getService();

            Map<String, Object> cn = new HashMap<>();
            cn.put("country", "CA");

            Tracks results = service.getArtistTopTrack(s[0], cn);

            List<Track> l = results.tracks;

            return l;
        }

        @Override
        protected void onPostExecute(List<Track> l) {

            if (l != null) {

                if (l.isEmpty()) {

                    Toast.makeText(getActivity(), getString(R.string.artist_tracks_not_found_msg), Toast.LENGTH_SHORT).show();
                }

                tracks.clear();

                for (Track t : l) {

                    tracks.add(new ParcelableTrack(t.name, t.album.name, t.album.images.isEmpty() ? "" : t.album.images.get(0).url));
                }
            }
        }
    }

    public class TrackAdapter extends ArrayAdapter<ParcelableTrack> {
        /**
         * This is our own custom constructor (it doesn't mirror a superclass constructor).
         * The context is used to inflate the layout file, and the List is the data we want
         * to populate into the lists
         *
         * @param context        The current context. Used to inflate the layout file.
         * @param l A List of AndroidFlavor objects to display in a list
         */
        public TrackAdapter(Activity context, List<ParcelableTrack> l) {
            // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
            // the second argument is used when the ArrayAdapter is populating a single TextView.
            // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
            // going to use this second argument, so it can be any value. Here, we used 0.
            super(context, 0, l);
        }

        /**
         * Provides a view for an AdapterView (ListView, GridView, etc.)
         *
         * @param position    The AdapterView position that is requesting a view
         * @param convertView The recycled view to populate.
         *                    (search online for "android view recycling" to learn more)
         * @param parent The parent ViewGroup that is used for inflation.
         * @return The View for the position in the AdapterView.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
            ParcelableTrack t = getItem(position);

            // Adapters recycle views to AdapterViews.
            // If this is a new View object we're getting, then inflate the layout.
            // If not, this view already has the layout inflated from a previous call to getView,
            // and we modify the View widgets as usual.
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listviewitem_track, parent, false);
            }

            ImageView iv = (ImageView) convertView.findViewById(R.id.track_icon);

            if (!t.ImageUrl.isEmpty()) {
                Picasso.with(getContext()).load(t.ImageUrl).into(iv);
            }

            TextView tv = (TextView) convertView.findViewById(R.id.track_name);
            tv.setText(t.Name);

            tv = (TextView) convertView.findViewById(R.id.track_album);
            tv.setText(t.Album);

            return convertView;
        }
    }
}
