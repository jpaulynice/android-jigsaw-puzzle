package com.jigdraw.draw.activity;

import static com.jigdraw.draw.util.ToastUtil.shortToast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jigdraw.draw.R;
import com.jigdraw.draw.model.enums.Difficulty;
import com.jigdraw.draw.tasks.JigsawGenerator;
import com.jigdraw.draw.views.DrawingView;

import java.util.Arrays;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting draw activity...");
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "view id clicked: " + view.getId());
        if (view.getId() == R.id.color_pick) {
            handleColorPick();
        } else if (view.getId() == R.id.erase_btn) {
            handleEraseButton();
        } else if (view.getId() == R.id.new_btn) {
            handleNewButton();
        } else if (view.getId() == R.id.save_btn) {
            handleSaveButton();
        }
    }

    private void handleColorPick() {
        drawView.setErase(false);
        drawView.setBrushSize(drawView.getLastBrushSize());
        openDialog(false);
    }

    void openDialog(boolean supportsAlpha) {
        Log.d(TAG, "show color picker dialog...");

        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, drawView
                .getPaintColor(),
                supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Log.d(TAG, "cancel clicked...");
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Log.d(TAG, "selected color: " + color);
                drawView.setColor(color);
                setBrushColor(color);
            }
        });
        dialog.show();
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

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Difficulty:")
                .items(levels)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view,
                                            int which, CharSequence text) {
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
        setBrushColor(drawView.getPaintColor());
    }

    /**
     * Initialize views
     */
    private void initViews() {
        drawView = (DrawingView) findViewById(R.id.drawing);
        drawView.setBrushSize(getResources().getInteger(R.integer.medium_size));

        LinearLayout layout = (LinearLayout) findViewById(R.id.top_options);
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = layout.getChildAt(i);
            view.setOnClickListener(this);
        }
    }
}