package com.example.tanawat.eleccontrol.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.view.SlidingTabLayout;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AddToolFragment extends Fragment {

    ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    public AddToolFragment() {
        super();
    }

    public static AddToolFragment newInstance() {
        AddToolFragment fragment = new AddToolFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_tool, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        setHasOptionsMenu(true);
    }




    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        viewPager=(ViewPager) rootView.findViewById((R.id.viewPager));
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return AddRemoteFragment.newInstance();
                    case 1:
                        return AddSwitchFragment.newInstance();
                    case 2:
                        return AddCurtainFragment.newInstance();
                    default:
                        return null;
                }

            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0:
                        return "Remote";
                    case 1:
                        return "Switch";
                    case 2:
                        return "Curtain";
                    default:
                        return null;
                }
            }
        });
        slidingTabLayout = (SlidingTabLayout )rootView.findViewById(R.id.slideTabLayout);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

}
