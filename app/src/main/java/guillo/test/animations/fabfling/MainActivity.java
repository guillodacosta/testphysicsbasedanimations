package guillo.test.animations.fabfling;

import android.content.Context;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import guillo.test.animations.fabfling.util.Animators;

public class MainActivity extends AppCompatActivity {

    private static final float FRICTION = 1.1f;
    private static final float FRICTION_LOW = 0.6f;
    private static final float MAX_VALUE_FAB_FLING = 1000f;
    private static final float MIN_VALUE_FAB_FLING = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    FloatingActionButton fab;
    private GestureListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gestureListener = new GestureListener(this);
        fab = findViewById(R.id.fab);
        fab.setOnTouchListener(gestureListener);
        fab.setOnClickListener(fabClickListener);
        fab.setOnLongClickListener(fabLongClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureListener.getDetector().onTouchEvent(event);
    }

    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Animators.physicBounce(view);
        }
    };

    View.OnLongClickListener fabLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View view) {
            Animators.bounce(view);
            return false;
        }
    };

    class GestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
        Context context;
        GestureDetector gestureDetector;

        public GestureListener(Context context) {
            this(context, null);
        }

        public GestureListener(Context context, GestureDetector _gestureDetector) {
            if(_gestureDetector == null)
                _gestureDetector = new GestureDetector(context, this);
            this.context = context;
            this.gestureDetector = _gestureDetector;
        }


        public GestureDetector getDetector() {
            return gestureDetector;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Animators.flingView(fab, FRICTION, FRICTION_LOW, MIN_VALUE_FAB_FLING, velocityX, velocityY);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    }
}
