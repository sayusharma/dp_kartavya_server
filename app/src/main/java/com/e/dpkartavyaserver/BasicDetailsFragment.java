package com.e.dpkartavyaserver;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.Adapter.ChildrenAdapter;
import com.e.dpkartavyaserver.Common.CurrentChildrenList;
import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Model.Children;
import com.e.dpkartavyaserver.Model.VerifySnr;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasicDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicDetailsFragment extends Fragment {
    private EditText dob_senior,dob_spouse,wedding,name,mob;
    private DatePickerDialog pickerSeniorDialog,pickerSpouseDialog,pickerWeddingDialog;
    private ImageView relativeLayout,relSpouse,relWedding;
    private VerifySnr snr;
    private String gender;
    private Spinner childrenSpinner;
    private ArrayList<Children> arrayList;
    private Button addChildren;
    private RecyclerView recyclerView;
    private ChildrenAdapter childrenAdapter;
    private String retired;
    private ImageView snrPhoto;
    private EditText snrName,snrAddress,snrMob,snrEmail,spouseName,
            snrRetiredFrom,snrRetiredYear,snrField,snrResidingWith,snrHealth,snrFreeTime,snrRelativeName,
            snrRelativeRelation,snrRelativeMob,snrRelativeAddr;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BasicDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasicDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BasicDetailsFragment newInstance(String param1, String param2) {
        BasicDetailsFragment fragment = new BasicDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void onClickRetired(View view){
        TextView textFemale = view.findViewById(R.id.seniorNoText);
        TextView textMale =view.findViewById(R.id.seniorYesText);
        CardView female = view.findViewById(R.id.seniorNo);
        CardView male = view.findViewById(R.id.seniorYes);
        EditText from = view.findViewById(R.id.retiredFrom);
        EditText year = view.findViewById(R.id.retiredYear);
        switch (retired){
            case "YES":
                //retired = "YES";
                from.setEnabled(true);
                year.setEnabled(true);
                male.setCardBackgroundColor(Color.parseColor("#1F2144"));
                textMale.setTextColor(Color.parseColor("#ffffff"));
                female.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textFemale.setTextColor(Color.parseColor("#bdbdbd"));
                break;
            case "NO":
               // retired = "NO";
                from.setEnabled(false);
                from.setText("");
                year.setText("");
                year.setEnabled(false);
                female.setCardBackgroundColor(Color.parseColor("#1F2144"));
                textFemale.setTextColor(Color.parseColor("#ffffff"));
                male.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textMale.setTextColor(Color.parseColor("#bdbdbd"));
                break;
        }
    }
    public void onClickGenderTenant(View view){
        TextView textFemale = view.findViewById(R.id.tenant_female_text);
        TextView textMale = view.findViewById(R.id.tenant_male_text);
        TextView textOther = view.findViewById(R.id.tenant_other_text);
        CardView female = view.findViewById(R.id.tenant_female);
        CardView male = view.findViewById(R.id.tenant_male);
        CardView other = view.findViewById(R.id.tenant_other);
        switch (gender){
            case "male":
               // tenant_current_gender = "male";
                male.setCardBackgroundColor(Color.parseColor("#1F2144"));
                textMale.setTextColor(Color.parseColor("#ffffff"));
                female.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textFemale.setTextColor(Color.parseColor("#bdbdbd"));
                other.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textOther.setTextColor(Color.parseColor("#bdbdbd"));
                break;
            case "female":
               // tenant_current_gender = "female";
                female.setCardBackgroundColor(Color.parseColor("#1F2144"));
                textFemale.setTextColor(Color.parseColor("#ffffff"));
                male.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textMale.setTextColor(Color.parseColor("#bdbdbd"));
                other.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textOther.setTextColor(Color.parseColor("#bdbdbd"));
                break;
            case "other":
              //  tenant_current_gender = "other";
                other.setCardBackgroundColor(Color.parseColor("#1F2144"));
                textOther.setTextColor(Color.parseColor("#ffffff"));
                female.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textFemale.setTextColor(Color.parseColor("#bdbdbd"));
                male.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textMale.setTextColor(Color.parseColor("#bdbdbd"));
                break;
        }
    }
    private void initialise(View view) {
        snrPhoto=view.findViewById(R.id.seniorPhoto);
        snrName=view.findViewById(R.id.seniorName);
        snrAddress=view.findViewById(R.id.seniorAddress);snrMob=view.findViewById(R.id.seniorMob);
        snrEmail=view.findViewById(R.id.seniorEmail);spouseName=view.findViewById(R.id.spouseName);
        snrRetiredFrom=view.findViewById(R.id.retiredFrom);snrRetiredYear=view.findViewById(R.id.retiredYear);
        snrField=view.findViewById(R.id.field);snrResidingWith=view.findViewById(R.id.residingWith);
        snrHealth=view.findViewById(R.id.health);snrFreeTime=view.findViewById(R.id.freeTime);
        snrRelativeName=view.findViewById(R.id.relativeName);snrRelativeRelation=view.findViewById(R.id.relativeRelation);
        snrRelativeMob=view.findViewById(R.id.relativeMob);snrRelativeAddr=view.findViewById(R.id.relativeAdd);
        snrMob.setEnabled(false);
        snrName.setText(snr.getBasicDetails().getPersonalDetails().getName());
        snrAddress.setText(snr.getBasicDetails().getPersonalDetails().getAddress());
        snrMob.setText(snr.getBasicDetails().getPersonalDetails().getMob());
        snrEmail.setText(snr.getBasicDetails().getPersonalDetails().getEmail());
        spouseName.setText(snr.getBasicDetails().getSpouseDetails().getName());
        snrRetiredFrom.setText(snr.getBasicDetails().getAdditionalDetails().getFrom());
        snrRetiredYear.setText(snr.getBasicDetails().getAdditionalDetails().getYear());
        snrField.setText(snr.getBasicDetails().getAdditionalDetails().getField());
        snrFreeTime.setText(snr.getBasicDetails().getAdditionalDetails().getAvailable_hours());
        snrResidingWith.setText(snr.getBasicDetails().getAdditionalDetails().getResidingWith());
        snrHealth.setText(snr.getBasicDetails().getAdditionalDetails().getHealth());

        snrRelativeName.setText(snr.getBasicDetails().getRelativeDetails().getName());
        snrRelativeAddr.setText(snr.getBasicDetails().getRelativeDetails().getAddress());
        snrRelativeMob.setText(snr.getBasicDetails().getRelativeDetails().getMob());
        snrRelativeRelation.setText(snr.getBasicDetails().getRelativeDetails().getRelation());
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
        View view = inflater.inflate(R.layout.fragment_basic_details, container, false);
        snr = CurrentSenior.currentSenior;
        snrPhoto = view.findViewById(R.id.seniorPhoto);
        gender = snr.getBasicDetails().getPersonalDetails().getGender();
        retired = snr.getBasicDetails().getAdditionalDetails().getRetired();
        try {
            Picasso.get()
                    .load(snr.getBasicDetails().getPersonalDetails().getPhoto())
                    .into(snrPhoto);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(snr.getBasicDetails().getChildrenDetails() == null){
            arrayList = new ArrayList<>();
        }
       else{
            arrayList = snr.getBasicDetails().getChildrenDetails();
        }
        recyclerView = view.findViewById(R.id.childrenRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        childrenAdapter = new ChildrenAdapter(getContext(),arrayList);
        recyclerView.setAdapter(childrenAdapter);
        CurrentChildrenList.currentChildrenList = arrayList;
        onClickRetired(view);
        onClickGenderTenant(view);
        addChildren = view.findViewById(R.id.btnAddChildren);
        dob_spouse = view.findViewById(R.id.dob_spouse);
        relSpouse = view.findViewById(R.id.dob_spouse_relative);
        childrenSpinner = view.findViewById(R.id.childrenResidence);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getActivity(),
                R.array.children_residence, R.layout.spinner_item_text);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        childrenSpinner.setAdapter(adapters);
        name = view.findViewById(R.id.childrenName);
        mob = view.findViewById(R.id.childrenMob);
        initialise(view);
        addChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(mob.getText())){
                    Toast.makeText(getActivity(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_SHORT).show();
                }
                else if(mob.getText().length()!=10){
                    Toast.makeText(getActivity(),"MOB NO CANNOT BE LESS THAN 10 DIGITS",Toast.LENGTH_SHORT).show();
                }
                else if(childrenSpinner.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity(),"PLEASE SELECT RESIDENCE",Toast.LENGTH_SHORT).show();
                }
                else {
                    Children children = new Children(name.getText().toString(),mob.getText().toString(),childrenSpinner.getSelectedItem().toString());
                    name.setText("");
                    mob.setText("");
                    childrenSpinner.setSelection(0);
                    arrayList.add(children);
                   // Toast.makeText(getContext(),children.getName(),Toast.LENGTH_SHORT).show();
                    childrenAdapter = new ChildrenAdapter(getContext(),arrayList);
                    recyclerView.setAdapter(childrenAdapter);
                    CurrentChildrenList.currentChildrenList = arrayList;
                }
            }
        });
        dob_senior = view.findViewById(R.id.dob_senior);
        relativeLayout = view.findViewById(R.id.dob_senior_relative);
        wedding = view.findViewById(R.id.wedding);
        relWedding = view.findViewById(R.id.wedding_relative);
        dob_spouse = view.findViewById(R.id.dob_spouse);
        relSpouse = view.findViewById(R.id.dob_spouse_relative);
        dob_senior.setText(CurrentSenior.currentSenior.getBasicDetails().getPersonalDetails().getDob());
        wedding.setText(CurrentSenior.currentSenior.getBasicDetails().getSpouseDetails().getWedding());
        dob_spouse.setText(CurrentSenior.currentSenior.getBasicDetails().getSpouseDetails().getDob());
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                pickerSeniorDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dob_senior.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pickerSeniorDialog.show();
                pickerSeniorDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


            }
        });
        relSpouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                pickerSpouseDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dob_spouse.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pickerSpouseDialog.show();
                pickerSpouseDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });
        relWedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                pickerWeddingDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                wedding.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pickerWeddingDialog.show();
                pickerWeddingDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });
        return view;
    }
}