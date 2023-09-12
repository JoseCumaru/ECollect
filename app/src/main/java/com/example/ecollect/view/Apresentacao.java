package com.example.ecollect.view;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ecollect.MenuPrincipal;
import com.example.ecollect.R;

public class Apresentacao extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btnNext;

    private int[] imageIds = {R.drawable.apresentacao_1, R.drawable.apresentacao_2, R.drawable.apresentacao_3, R.drawable.apresentacao_4};
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apresentacao);

        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);

        SlidePagerAdapter slidePagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(slidePagerAdapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage < imageIds.length - 1) {
                    currentPage++;
                    viewPager.setCurrentItem(currentPage);
                } else {
                    Intent intent = new Intent(Apresentacao.this, MenuPrincipal.class);
                    startActivity(intent);
                    finish();
                    // Aqui você pode redirecionar para a próxima atividade ou tomar outra ação
                }
            }
        });
    }

    private class SlidePagerAdapter extends FragmentPagerAdapter {

        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return SlideFragment.newInstance(imageIds[position]);
        }

        @Override
        public int getCount() {
            return imageIds.length;
        }
    }

    public static class SlideFragment extends Fragment {

        private static final String ARG_IMAGE_ID = "imageId";

        public static SlideFragment newInstance(int imageId) {
            SlideFragment fragment = new SlideFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_IMAGE_ID, imageId);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.slide_item, container, false);

            ImageView imageView = view.findViewById(R.id.imageView);
            int imageId = getArguments().getInt(ARG_IMAGE_ID);
            imageView.setImageResource(imageId);

            return view;
        }
    }
}
