package com.jigdraw.draw.activity;

import static com.jigdraw.draw.util.ToastUtil.shortToast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.jigdraw.draw.R;
import com.jigdraw.draw.model.enums.Difficulty;
import com.jigdraw.draw.tasks.JigsawGenerator;
import com.jigdraw.draw.views.DrawingView;

import java.util.Arrays;
import java.util.List;

/**
 * Main activity class represents all the activities that a user starts with
 * such as draw, choose to create a new drawing, save the current drawing,
 * choose eraser and brush sizes etc.
 *
 * @author Jay Paulynice
 */
public class DrawActivity extends BaseJigsawActivity implements OnClickListener {
    /** Class name for logging */
    private static final String TAG = "DrawActivity";

    /** Custom view for drawing */
    private DrawingView drawView;

    /** Current paint */
    private ImageButton currPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting draw activity...");
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "view id clicked: " + view.getId());
        if (view.getId() == R.id.erase_btn) {
            handleEraseButton();
        } else if (view.getId() == R.id.new_btn) {
            handleNewButton();
        } else if (view.getId() == R.id.save_btn) {
            handleSaveButton();
        }
    }

    /**
     * Set erase to true on eraser click
     */
    public void handleEraseButton() {
        drawView.setErase(true);
    }

    /**
     * Handle the new button click
     */
    public void handleNewButton() {
        // new button
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("New drawing");
        newDialog.setMessage("Start new drawing (you will lose the current "
                + "drawing)?");
        newDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        drawView.startNew();
                        dialog.dismiss();
                    }
                });
        newDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        newDialog.show();
    }

    /**
     * Handle the save button click
     */
    public void handleSaveButton() {
        CharSequence levels[] = new CharSequence[]{"Easy", "Medium", "Hard"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose difficulty:");
        builder.setItems(levels, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createJigsaw(which);
            }
        });
        builder.show();
    }

    /**
     * Create jigsaw and give user feedback
     *
     * @param which the selected option in dialog
     */
    public void createJigsaw(int which) {
        drawView.setDrawingCacheEnabled(true);
        Bitmap bitmap = drawView.getDrawingCache();

        JigsawGenerator task = new JigsawGenerator(getApplicationContext(),
                Difficulty.fromValue(which));

        shortToast(getApplicationContext(), "Loading jigsaw puzzle...");
        task.execute(bitmap.copy(bitmap.getConfig(), true));
        drawView.destroyDrawingCache();
    }

    /**
     * Handle the change of the brush size
     *
     * @param view the current brush view
     */
    public void handleBrushSize(View view) {
        // default to medium brush
        float bSize = getResources().getInteger(R.integer.medium_size);
        if (view.getId() == R.id.small_brush) {
            bSize = getResources().getInteger(R.integer.small_size);
        } else if (view.getId() == R.id.large_brush) {
            bSize = getResources().getInteger(R.integer.large_size);
        } else if (view.getId() == R.id.largest_brush) {
            bSize = getResources().getInteger(R.integer.largest_size);
        }
        drawView.setErase(false);
        drawView.setBrushSize(bSize);
        drawView.setLastBrushSize(bSize);
    }

    /**
     * Handles the change of color chosen
     *
     * @param view the view for the color chosen
     */
    public void paintClicked(View view) {
        drawView.setErase(false);
        drawView.setBrushSize(drawView.getLastBrushSize());

        if (view != currPaint) {
            String color = changeColor(view);
            setBrushColor(Color.parseColor(color));
        }
    }

    /**
     * Change to selected color
     *
     * @param view the image button clicked
     * @return the color to set
     */
    private String changeColor(View view) {
        ImageButton imgView = (ImageButton) view;
        String color = view.getTag().toString();
        drawView.setColor(color);

        updateUI(imgView, view);

        return color;
    }

    /**
     * Update UI with new selected color
     *
     * @param imgView the image view
     * @param view the view
     */
    private void updateUI(ImageButton imgView, View view) {
        imgView.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.paint_pressed, null));
        currPaint
                .setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        (R.drawable.paint), null));
        currPaint = (ImageButton) view;
    }

    /**
     * Set the brushes to the color chosen
     *
     * @param color the chosen color
     */
    private void setBrushColor(int color) {
        for (ImageButton im : getBrushes()) {
            GradientDrawable d = (GradientDrawable) im.getDrawable();
            d.setColor(color);
        }
    }

    /**
     * Make a list of the brushes
     */
    public List<ImageButton> getBrushes() {
        return Arrays.asList((ImageButton) findViewById(R.id
                        .small_brush),
                (ImageButton) findViewById(R.id.medium_brush),
                (ImageButton) findViewById(R.id.large_brush),
                (ImageButton) findViewById(R.id.largest_brush));
    }

    /**
     * Initialize all the ui components
     */
    private void init() {
        setContentView(R.layout.activity_main);
        initViews();
        initLayout();
        setBrushColor(drawView.getPaintColor());
    }

    /**
     * Initialize the layout and set current color to first one
     */
    private void initLayout() {
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.paint_pressed, null));
    }

    /**
     * Initialize views
     */
    private void initViews() {
        drawView = (DrawingView) findViewById(R.id.drawing);
        drawView.setBrushSize(getResources().getInteger(R.integer.medium_size));

        for (View view : getMenuButtons()) {
            view.setOnClickListener(this);
        }
    }

    /**
     * Initialize the buttons
     */
    public List<View> getMenuButtons() {
        return Arrays.asList(findViewById(R.id.erase_btn),
                findViewById(R.id.new_btn), findViewById(R.id.save_btn));
    }
}