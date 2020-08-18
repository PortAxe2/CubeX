package com.example.CubeX;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Information#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Information extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String ID = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Sensors Data
    Map<String, Object> sensor1 = new HashMap<String, Object>();
    Map<String, Object> sensor2 = new HashMap<String, Object>();
    Map<String, Object> sensor3 = new HashMap<String, Object>();

    Double distance1, distance2, distance3;
    Date lastUpdate1, lastUpdate2, lastUpdate3;

    Boolean valuesSet = false;

    TextView textViewSensor1, textViewSensor2, textViewSensor3;
    TextView date1, date2, date3;

    public Information() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Information.
     */
    // TODO: Rename and change types and number of parameters
    public static Information newInstance(String param1, String param2) {
        Information fragment = new Information();
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

    }

    @Override
    public synchronized View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        ID = getArguments().getString("ID");

        Task<DocumentSnapshot> documentReference = db.collection("devices").document(ID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot doc = task.getResult();

                            //Fetch data and store in maps
                            sensor1 = (Map<String, Object>) doc.getData().get("sensor1");
                            sensor2 = (Map<String, Object>) doc.getData().get("sensor2");
                            sensor3 = (Map<String, Object>) doc.getData().get("sensor3");


                            //Assign distance values
                            distance1 = (Double) sensor1.get("distance");
                            distance2 = (Double) sensor2.get("distance");
                            distance3 = (Double) sensor3.get("distance");

                            //Assign last update values
                            lastUpdate1 = (Date) sensor1.get("lastUpdate");
                            lastUpdate2 = (Date) sensor2.get("lastUpdate");
                            lastUpdate3 = (Date) sensor3.get("lastUpdate");

                            //Assign distance views
                            textViewSensor1 = (TextView) view.findViewById(R.id.Sensor1);
                            textViewSensor2 = (TextView) view.findViewById(R.id.Sensor2);
                            textViewSensor3 = (TextView) view.findViewById(R.id.Sensor3);

                            //Assign last update views
                            date1 = (TextView) view.findViewById(R.id.Date1);
                            date2 = (TextView) view.findViewById(R.id.Date2);
                            date3 = (TextView) view.findViewById(R.id.Date3);

                            //Output distance to display
                            textViewSensor1.setText("Sensor 1: " + distance1 + " cm");
                            textViewSensor2.setText("Sensor 2: " + distance2 + " cm");
                            textViewSensor3.setText("Sensor 2: " + distance3 + " cm");

                            //Output last update to display
                            date1.setText(""+lastUpdate1);
                            date2.setText(""+lastUpdate2);
                            date3.setText(""+lastUpdate3);

                            Log.d("sensor1", "" + lastUpdate3);

                        }
                    }
                });

        return view;
    }


}