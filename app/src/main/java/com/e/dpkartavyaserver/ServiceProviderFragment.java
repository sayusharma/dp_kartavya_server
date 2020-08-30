package com.e.dpkartavyaserver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Model.VerifySnr;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceProviderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceProviderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText driverName,driverAddr,driverVerStatus,driverVerNo;
    private EditText watchmanName,watchmanAddr,watchmanVerStatus,watchmanVerNo;
    private EditText servantName,servantAddr,servantVerStatus,servantVerNo;
    private EditText tenantName,tenantAddr,tenantVerStatus,tenantVerNo;
    private EditText sweeperName,sweeperAddr,sweeperVerStatus,sweeperVerNo;
    private EditText carName,carAddr,carVerStatus,carVerNo;
    private VerifySnr snr;
    private EditText otherName,otherAddr,otherVerStatus,otherVerNo,otherServType;
    private Spinner driverSpinner,watchmanSpinner,tenantSpinner,servantSpinner,sweeperSpinner,carcleanerSpinner,otherSpinner;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ServiceProviderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceProviderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceProviderFragment newInstance(String param1, String param2) {
        ServiceProviderFragment fragment = new ServiceProviderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void initialise(View view){
        driverName=view.findViewById(R.id.driverName);driverAddr=view.findViewById(R.id.driverAdd);driverVerNo=view.findViewById(R.id.driverVerNo);
        watchmanName=view.findViewById(R.id.WatchmanName);watchmanAddr=view.findViewById(R.id.WatchmanAdd);watchmanVerNo=view.findViewById(R.id.WatchmanVerNo);
        servantName=view.findViewById(R.id.ServantName);servantAddr=view.findViewById(R.id.ServantAdd);servantVerNo=view.findViewById(R.id.ServantVerNo);
        tenantName=view.findViewById(R.id.TenantName);tenantAddr=view.findViewById(R.id.TenantAdd);tenantVerNo=view.findViewById(R.id.TenantVerNo);
        sweeperName=view.findViewById(R.id.SweeperName);sweeperAddr=view.findViewById(R.id.SweeperAdd);sweeperVerNo=view.findViewById(R.id.SweeperVerNo);
        carName=view.findViewById(R.id.CarCleanerName);carAddr=view.findViewById(R.id.CarCleanerAdd);carVerNo=view.findViewById(R.id.CarCleanerVerNo);
        otherServType=view.findViewById(R.id.OthersSerType);otherName=view.findViewById(R.id.OthersName);otherAddr=view.findViewById(R.id.OthersAdd);
        otherVerNo=view.findViewById(R.id.OthersVerNo);


        driverName.setText(snr.getServiceProviders().getDriver().getName());
        driverAddr.setText(snr.getServiceProviders().getDriver().getAddress());
        //driverVerStatus.setText(snr.getServiceProviders().getDriver().getVerStatus());
        driverVerNo.setText(snr.getServiceProviders().getDriver().getVerNo());
        watchmanName.setText(snr.getServiceProviders().getWatchman().getName());
        watchmanAddr.setText(snr.getServiceProviders().getWatchman().getAddress());
       // watchmanVerStatus.setText(snr.getServiceProviders().getWatchman().getVerStatus());
        watchmanVerNo.setText(snr.getServiceProviders().getWatchman().getVerNo());

        servantName.setText(snr.getServiceProviders().getServant().getName());
        servantAddr.setText(snr.getServiceProviders().getServant().getAddress());
        //servantVerStatus.setText(snr.getServiceProviders().getServant().getVerStatus());
        servantVerNo.setText(snr.getServiceProviders().getServant().getVerNo());
        tenantName.setText(snr.getServiceProviders().getTenant().getName());
        tenantAddr.setText(snr.getServiceProviders().getTenant().getAddress());
       // tenantVerStatus.setText(snr.getServiceProviders().getTenant().getVerStatus());
        tenantVerNo.setText(snr.getServiceProviders().getTenant().getVerNo());
        sweeperName.setText(snr.getServiceProviders().getSweeper().getName());
        sweeperAddr.setText(snr.getServiceProviders().getSweeper().getAddress());
       // sweeperVerStatus.setText(snr.getServiceProviders().getSweeper().getVerStatus());
        sweeperVerNo.setText(snr.getServiceProviders().getSweeper().getVerNo());
        carName.setText(snr.getServiceProviders().getCarCleaner().getName());
        carAddr.setText(snr.getServiceProviders().getCarCleaner().getAddress());
       // carVerStatus.setText(snr.getServiceProviders().getCarCleaner().getVerStatus());
        carVerNo.setText(snr.getServiceProviders().getCarCleaner().getVerNo());
        otherName.setText(snr.getServiceProviders().getOthers().getName());
        otherAddr.setText(snr.getServiceProviders().getOthers().getAddress());
       // otherVerStatus.setText(snr.getServiceProviders().getOthers().getVerStatus());
        otherVerNo.setText(snr.getServiceProviders().getOthers().getVerNo());
        otherServType.setText(snr.getServiceProviders().getOthers().getSerType());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_provider, container, false);
        snr = CurrentSenior.currentSenior;
        initialise(view);
        driverSpinner = view.findViewById(R.id.driverVerStatus);
        watchmanSpinner = view.findViewById(R.id.WatchmanVerStatus);
        tenantSpinner = view.findViewById(R.id.TenantVerStatus);
        servantSpinner = view.findViewById(R.id.ServantVerStatus);
        sweeperSpinner = view.findViewById(R.id.SweeperVerStatus);
        carcleanerSpinner = view.findViewById(R.id.CarCleanerVerStatus);
        otherSpinner = view.findViewById(R.id.OthersVerStatus);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getActivity(),
                R.array.verification, R.layout.spinner_item_text);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driverSpinner.setAdapter(adapters);
        watchmanSpinner.setAdapter(adapters);
        tenantSpinner.setAdapter(adapters);
        servantSpinner.setAdapter(adapters);
        sweeperSpinner.setAdapter(adapters);
        carcleanerSpinner.setAdapter(adapters);
        otherSpinner.setAdapter(adapters);
        driverSpinner.setSelection(decodeVerStatus(CurrentSenior.currentSenior.getServiceProviders().getDriver().getVerStatus()));
        watchmanSpinner.setSelection(decodeVerStatus(CurrentSenior.currentSenior.getServiceProviders().getWatchman().getVerStatus()));
        tenantSpinner.setSelection(decodeVerStatus(CurrentSenior.currentSenior.getServiceProviders().getTenant().getVerStatus()));
        servantSpinner.setSelection(decodeVerStatus(CurrentSenior.currentSenior.getServiceProviders().getServant().getVerStatus()));
        sweeperSpinner.setSelection(decodeVerStatus(CurrentSenior.currentSenior.getServiceProviders().getSweeper().getVerStatus()));
        carcleanerSpinner.setSelection(decodeVerStatus(CurrentSenior.currentSenior.getServiceProviders().getCarCleaner().getVerStatus()));
        otherSpinner.setSelection(decodeVerStatus(CurrentSenior.currentSenior.getServiceProviders().getOthers().getVerStatus()));
        return view;
    }
    public int decodeVerStatus(String s){
        if(s.equals("")){
            return 0;
        }
        if(s.equals("Verified")){
            return 1;
        }
        if(s.equals("Not verified")){
            return 2;
        }
        if(s.equals("Part-Time")){
            return 3;
        }
        if(s.equals("Verification Pending")){
            return 4;
        }
        else return 5;
    }
}