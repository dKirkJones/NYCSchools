package com.djmakes.a20211205_darrinjones_nycschools;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


// This is abstract because we do not want to make an instance
// of the base activity we just want to use it as a template for our activities

public abstract class BaseActivity extends AppCompatActivity {

    public ProgressBar mProgressBar;
    public TextView mTextView;

    @Override
    public void setContentView(int layoutResID) {

        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);

        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);
        mTextView = constraintLayout.findViewById(R.id.search_text_view);

        // the frame layout acts as a container for any activities that extend base class
        getLayoutInflater().inflate(layoutResID, frameLayout,true );

        super.setContentView(layoutResID);
    }
    // This allows the progress bar to be reused throughout the app from one method
    // Becasue of time restraints I did not get to apply this version of the progress bar yet.
    public void showProgressBar(boolean visibility){
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

}
