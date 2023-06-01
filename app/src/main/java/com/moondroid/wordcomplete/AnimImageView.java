package com.moondroid.wordcomplete;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class AnimImageView extends AppCompatImageView {
    private Context ctx;
    Animation zoomIn, zoomOut;
    MediaPlayer player;

    public AnimImageView(@NonNull Context context) {
        super(context);
        this.ctx = context;
        initAnim();
    }

    public AnimImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        initAnim();
    }

    public AnimImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctx = context;
        initAnim();
    }

    private void initAnim() {
        zoomIn = AnimationUtils.loadAnimation(ctx, R.anim.zoom_in);
        zoomOut = AnimationUtils.loadAnimation(ctx, R.anim.zoom_out);
        player = MediaPlayer.create(ctx, R.raw.tab);
        setSoundEffectsEnabled(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                startAnimation(zoomOut);
                break;
            }
            case MotionEvent.ACTION_UP:
                playSound();
            case MotionEvent.ACTION_CANCEL:
                startAnimation(zoomIn);
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setVisibility(int visibility) {
        clearAnimation();
        super.setVisibility(visibility);
    }

    private void playSound() {
        if (player != null){
            player.stop();
            player.release();
            player = null;
        }
        player = MediaPlayer.create(ctx, R.raw.tab);
        player.setVolume(MainActivity.sound ? 1.0f : 0.0f,
                MainActivity.sound ? 1.0f : 0.0f);
        player.start();
    }


}
