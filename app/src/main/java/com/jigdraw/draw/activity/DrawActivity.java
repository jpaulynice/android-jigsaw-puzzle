package com.jigdraw.draw.activity;

import static com.jigdraw.draw.util.ToastUtil.shortToast;

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

import java.util.ArrayList;
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
            openNewDrawingDialog();
        } else if (view.getId() == R.id.save_btn) {
            openCreateJigsawDialog();
        }
    }

    private void handleColorPick() {
        drawView.setErase(false);
        drawView.setBrushSize(drawView.getLastBrushSize());
        openColorPickerDialog(false);
    }

    private void openColorPickerDialog(boolean supportsAlpha) {
        Log.d(TAG, "show color picker dialog...");
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, drawView
                .getPaintColor(), supportsAlpha, getColorPickerCallback());
        dialog.show();
    }

    private AmbilWarnaDialog.OnAmbilWarnaListener getColorPickerCallback() {
        return new AmbilWarnaDialog.OnAmbilWarnaListener() {
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
        };
    }

    /**
     * Set erase to true on eraser click
     */
    private void handleEraseButton() {
        drawView.setErase(true);
    }

    /**
     * Handle the new button click
     */
    private void openNewDrawingDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.title("New drawing")
                .callback(getMDCallback())
                .positiveText("Ok")
                .negativeText("Cancel")
                .content(("Start new drawing (you will lose the current "
                        + "drawing)?"));
        builder.show();
    }

    private MaterialDialog.ButtonCallback getMDCallback() {
        return new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                drawView.startNew();
                dialog.dismiss();
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                dialog.cancel();
            }
        };
    }

    /**
     * Handle the save button click
     */
    private void openCreateJigsawDialog() {
        CharSequence levels[] = new CharSequence[]{"Easy", "Medium", "Hard"};

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Difficulty:")
                .items(levels)
                .itemsCallback(getMDListCallback());

        builder.show();
    }

    private MaterialDialog.ListCallback getMDListCallback() {
        return new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View view,
                                    int which, CharSequence text) {
                createJigsaw(which);
            }
        };
    }

    /**
     * Create jigsaw and give user feedback
     *
     * @param which the selected option in dialog
     */
    private void createJigsaw(int which) {
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
    }

    /**
     * Set the brushes to the color chosen
     *
     * @param color the chosen color
     */
    private void setBrushColor(int color) {
        for (View v : getBottomButtons()) {
            ImageButton im = (ImageButton) v;
            GradientDrawable d = (GradientDrawable) im.getDrawable();
            d.setColor(color);
        }
    }

    public List<View> getBottomButtons() {
        return getLayoutViews(R.id.all_brushes);
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

        for (View v : getTopButtons()) {
            v.setOnClickListener(this);
        }
    }

    public List<View> getTopButtons() {
        return getLayoutViews(R.id.top_options);
    }

    private List<View> getLayoutViews(final int layoutId) {
        List<View> views = new ArrayList<>();
        LinearLayout layout = (LinearLayout) findViewById(layoutId);
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {
            views.add(layout.getChildAt(i));
        }

        return views;
    }
}