package com.shoyu666.module.ui.circle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.widget.SeekBar;

import com.zebra.module.ui.circle.R;

/**
 * @author xining
 * @date 2019/3/1
 */
public class MainActivity extends AppCompatActivity {
    public CycleView mInstrumentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInstrumentView = findViewById(R.id.CircleView);
        SeekBar seekBar = findViewById(R.id.seekBar);
        mInstrumentView.setOutProcess(0.35f);
        mInstrumentView.setInnerProcess(0.2f);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float p = progress;
                float pro = p / 100;
                mInstrumentView.setOutProcess(pro);
                mInstrumentView.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        SeekBar seekBar2 = findViewById(R.id.seekBar2);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float p = progress;
                float pro = p / 100;
                mInstrumentView.setInnerProcess(pro);
                mInstrumentView.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        SpannableString spannableString = new SpannableString("80km/h");
        RelativeSizeSpan unit = new RelativeSizeSpan(0.4f);
        spannableString.setSpan(unit,4,spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mInstrumentView.setCenterText(spannableString);
    }
}
