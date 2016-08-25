package com.voctex.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voctex.R;
import com.voctex.view.BestArcView;
import com.voctex.view.CircleWaveView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    private ViewGroup mViewGroup;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewGroup= (ViewGroup) inflater.inflate(R.layout.fragment_second, container, false);

        CircleWaveView view= (CircleWaveView) mViewGroup.findViewById(R.id.view5);
        view.start();

        BestArcView view1=(BestArcView)mViewGroup.findViewById(R.id.view6);
        view1.setDuration(1,25);

        return mViewGroup;
    }

}
