package com.e.dpkartavyaserver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Model.VerifySnr;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecurityChecksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecurityChecksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private VerifySnr snr;
    private SwitchCompat Aaa,Aab,Aac,Aad,Aae,Aaf,Aag,Aba,Abb,Abc,Abd,Abe,Abf,Abg,Ba,Bb,Bc,Bd,Be,Bf;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecurityChecksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecurityChecksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecurityChecksFragment newInstance(String param1, String param2) {
        SecurityChecksFragment fragment = new SecurityChecksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void initialise(View view){
        Aaa=view.findViewById(R.id.Aaa);Aab=view.findViewById(R.id.Aab);Aac=view.findViewById(R.id.Aac);Aad=view.findViewById(R.id.Aad);
        Aae=view.findViewById(R.id.Aae);Aaf=view.findViewById(R.id.Aaf);Aag=view.findViewById(R.id.Aag);Aba=view.findViewById(R.id.Aba);
        Abb=view.findViewById(R.id.Abb);Abc=view.findViewById(R.id.Abc);Abd=view.findViewById(R.id.Abd);Abe=view.findViewById(R.id.Abe);
        Abf=view.findViewById(R.id.Abf);Abg=view.findViewById(R.id.Abg);Ba=view.findViewById(R.id.Ba);Bb=view.findViewById(R.id.Bb);
        Bc=view.findViewById(R.id.Bc);Bd=view.findViewById(R.id.Bd);Be=view.findViewById(R.id.Be);Bf=view.findViewById(R.id.Bf);

        setSwitch(Aaa,snr.getSecurityChecks().getAaa());
        setSwitch(Aab,snr.getSecurityChecks().getAab());
        setSwitch(Aac,snr.getSecurityChecks().getAac());
        setSwitch(Aad,snr.getSecurityChecks().getAad());
        setSwitch(Aae,snr.getSecurityChecks().getAae());
        setSwitch(Aaf,snr.getSecurityChecks().getAaf());
        setSwitch(Aag,snr.getSecurityChecks().getAag());
        setSwitch(Aba,snr.getSecurityChecks().getAba());
        setSwitch(Abb,snr.getSecurityChecks().getAbb());
        setSwitch(Abc,snr.getSecurityChecks().getAbc());
        setSwitch(Abd,snr.getSecurityChecks().getAbd());
        setSwitch(Abe,snr.getSecurityChecks().getAbe());
        setSwitch(Abf,snr.getSecurityChecks().getAbf());
        setSwitch(Abg,snr.getSecurityChecks().getAbg());
        setSwitch(Ba,snr.getSecurityChecks().getBa());
        setSwitch(Bb,snr.getSecurityChecks().getBb());
        setSwitch(Bc,snr.getSecurityChecks().getBc());
        setSwitch(Bd,snr.getSecurityChecks().getBd());
        setSwitch(Be,snr.getSecurityChecks().getBe());
        setSwitch(Bf,snr.getSecurityChecks().getBf());
    //    setSwitch(Ba,snr.getSecurityChecks().getBa());


    }
    public void setSwitch(SwitchCompat switchCompat,int s){
        if(s==0 || s==2){
            switchCompat.setChecked(false);
        }
        else{
            switchCompat.setChecked(true);
        }
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
        View view = inflater.inflate(R.layout.fragment_security_checks, container, false);
        snr = CurrentSenior.currentSenior;
        initialise(view);
        return view;
    }
}