package com.example.korzhik.testproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DashFragment extends Fragment {//профиль

    int i;

    ImageView chars[] = new ImageView[5];

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_dash, container, false);
        chars[0] = v.findViewById(R.id.char_intelligence);
        chars[1] = v.findViewById(R.id.char_social);
        chars[2] = v.findViewById(R.id.char_friendly);
        chars[3] = v.findViewById(R.id.char_housekeeping);
        chars[4] = v.findViewById(R.id.char_health);




        for (i = 0; i < 5; ++i) {
            chars[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.char_intelligence:
                            Snackbar.make(DashFragment.this.v,
                                    "Интеллект",
                                    Snackbar.LENGTH_SHORT).show();
                            break;
                        case R.id.char_social:
                            Snackbar.make(DashFragment.this.v,
                                    "Социальные навыки",
                                    Snackbar.LENGTH_SHORT).show();
                            break;
                        case R.id.char_friendly:
                            Snackbar.make(DashFragment.this.v,
                                    "Дружелюбие",
                                    Snackbar.LENGTH_SHORT).show();
                            break;
                        case R.id.char_housekeeping:
                            Snackbar.make(DashFragment.this.v,
                                    "Домоводство",
                                    Snackbar.LENGTH_SHORT).show();
                            break;
                        case R.id.char_health:
                            Snackbar.make(DashFragment.this.v,
                                    "Физическое развитие",
                                    Snackbar.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }

        return v;
    }
}