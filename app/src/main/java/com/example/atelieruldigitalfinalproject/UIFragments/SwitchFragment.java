package com.example.atelieruldigitalfinalproject.UIFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.atelieruldigitalfinalproject.DataPackage.RoomDB.TripRepository;
import com.example.atelieruldigitalfinalproject.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SwitchFragment extends Fragment {
    private MainFragment mainFragment;
    private FavouriteFragment favouriteFragment;
    private TripRepository repository;

    private ViewPager viewPager;

    public SwitchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_switch, container, false);
        repository = new TripRepository(container.getContext());
        mainFragment = new MainFragment();
        favouriteFragment = new FavouriteFragment();

        viewPager = view.findViewById(R.id.favouriteViewPager);

        setupViewPager(viewPager);
        TabLayout tabLayout = view.findViewById(R.id.favouriteTab);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(mainFragment, "All List");
        adapter.addFragment(favouriteFragment, "Favourite List");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragment = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public Adapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragment.add(fragment);
            fragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
