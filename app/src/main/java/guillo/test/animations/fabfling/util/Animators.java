package guillo.test.animations.fabfling.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;


public class Animators {
    private static final float FAB_SCALE_DOWN_SIZE = 0.5f;
    private static final int FAB_SCALE_DURATION = 1000;
    private static final float FAB_SCALE_UP_SIZE = 1f;
    private static final String TAG = Animators.class.getSimpleName();

    public static void flingView(final View view, final float friction, final float frictionLow,
                                 final float minValue, final float velocityX, final float velocityY) {
        FlingAnimation xFling = new FlingAnimation(view, DynamicAnimation.X);
        FlingAnimation yFling = new FlingAnimation(view, DynamicAnimation.Y);
        xFling.setFriction(friction).setMinValue(minValue);
        yFling.setFriction(frictionLow).setMinValue(minValue);
        xFling.setStartVelocity(velocityX);
        yFling.setStartVelocity(velocityY);
        xFling.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                physicScaleDown(view);
                // todo test physic based scale vs property scale
//                scaleDown(view);
            }
        });
        xFling.start();
        yFling.start();
    }

    public static void physicBounce(final View view) {
        final SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y, 0);
        springAnimation.setStartVelocity(2000);
        springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        springAnimation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        springAnimation.start();
    }

    public static void physicScaleDown(final View view) {
        SpringAnimation xScale = new SpringAnimation(view, DynamicAnimation.SCALE_X, FAB_SCALE_DOWN_SIZE);
        SpringAnimation yScale = new SpringAnimation(view, DynamicAnimation.SCALE_Y, FAB_SCALE_DOWN_SIZE);
        xScale.setStartVelocity(FAB_SCALE_DURATION).setMaxValue(1f);
        yScale.setStartVelocity(FAB_SCALE_DURATION).setMaxValue(1f);
        xScale.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY).setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        yScale.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY).setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        yScale.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                physicScaleUp(view);
            }
        });
        xScale.start();
        yScale.start();
    }

    public static void physicScaleUp(final View view) {
        final SpringAnimation xScale = new SpringAnimation(view, DynamicAnimation.SCALE_X, 1f);
        final SpringAnimation yScale = new SpringAnimation(view, DynamicAnimation.SCALE_Y, 1f);
        xScale.setStartVelocity(FAB_SCALE_DURATION).setMaxValue(1f).setMinValue(FAB_SCALE_DOWN_SIZE);
        yScale.setStartVelocity(FAB_SCALE_DURATION).setMaxValue(1f).setMinValue(FAB_SCALE_DOWN_SIZE);
        xScale.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY).setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        yScale.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY).setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        xScale.start();
        yScale.start();
    }

    public static void bounce(final View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", 0f, 20f);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new CycleInterpolator(5f));
        objectAnimator.start();
    }

    private static ObjectAnimator rotateRight(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 360f, 0f);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        return objectAnimator;
    }

    private static void scaleDown(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", FAB_SCALE_UP_SIZE, FAB_SCALE_DOWN_SIZE);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", FAB_SCALE_UP_SIZE, FAB_SCALE_DOWN_SIZE);
        xObjectAnimator.setDuration(FAB_SCALE_DURATION);
        yObjectAnimator.setDuration(FAB_SCALE_DURATION);
        xObjectAnimator.setInterpolator(new LinearInterpolator());
        yObjectAnimator.setInterpolator(new LinearInterpolator());
        animatorSet.play(xObjectAnimator).with(yObjectAnimator);
        animatorSet.start();
    }

    private static AnimatorSet scaleUp(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", FAB_SCALE_DOWN_SIZE, FAB_SCALE_UP_SIZE);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", FAB_SCALE_DOWN_SIZE, FAB_SCALE_UP_SIZE);
        xObjectAnimator.setDuration(FAB_SCALE_DURATION);
        yObjectAnimator.setDuration(FAB_SCALE_DURATION);
        xObjectAnimator.setInterpolator(new LinearInterpolator());
        yObjectAnimator.setInterpolator(new LinearInterpolator());
        animatorSet.play(xObjectAnimator).with(yObjectAnimator);
        return animatorSet;
    }

}
