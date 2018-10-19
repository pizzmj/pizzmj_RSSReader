package com.example.mike.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class RSSFeedActivity extends Activity implements MyListFragment.OnItemSelectedListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getResources().getBoolean(R.bool.twoPaneMode))
        {
            return;
        }

        if(savedInstanceState != null)
        {
            getFragmentManager().executePendingTransactions();
            Fragment fragmentByID = getFragmentManager().findFragmentById(R.id.fragment_container);

            if(fragmentByID!=null)
            {
                getFragmentManager().beginTransaction().remove(fragmentByID).commit();
            }
        }

        MyListFragment listFragment = new MyListFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, listFragment).commit();

    }
    @Override
    public void onRssItemSelected(String text)
    {
        if(getResources().getBoolean(R.bool.twoPaneMode))
        {
            DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
            fragment.setText(text);
        }
        else
        {
            DetailFragment newFragment = new DetailFragment();
            Bundle args = new Bundle();
            args.putString(DetailFragment.EXTRA_TEXT, text);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}