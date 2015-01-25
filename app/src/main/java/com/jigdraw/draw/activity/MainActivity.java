package com.jigdraw.draw.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jigdraw.draw.R;
import com.jigdraw.draw.views.DrawingView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Main activity class represents all the activities that a user starts with
 * such as draw, choose to create a new drawing, save the current drawing,
 * choose eraser and brush sizes etc.
 *
 * @author Jay Paulynice
 */
public class MainActivity extends Activity implements OnClickListener {
    /** activity name for logging */
    private static final String TAG = "MainActivity";

    /** custom drawing view */
    private DrawingView drawView;

    /** buttons for drawing */
    private ImageButton currPaint, eraseBtn, newBtn, saveBtn;

    /** brush sizes */
    private float smallBrush, mediumBrush, largeBrush, largestBrush;

    /** list of brushes */
    private List<ImageButton> brushes = new ArrayList<ImageButton>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
     * Handle the change of the brush size
     *
     * @param view the current brush view
     */
    public void handleBrushSize(View view) {
        //default to medium brush
        float bSize = mediumBrush;
        if (view.getId() == R.id.small_brush) {
            Log.d(TAG, "small brush clicked.");
            bSize = smallBrush;
        } else if (view.getId() == R.id.large_brush) {
            Log.d(TAG, "large brush clicked.");
            bSize = largeBrush;
        } else if (view.getId() == R.id.largest_brush) {
            Log.d(TAG, "largest brush clicked.");
            bSize = largestBrush;
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
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            //update ui
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint = (ImageButton) view;

            Log.d(TAG, "setting brush color..." + color);
            setBrushColor(Color.parseColor(color));
        }
    }

    /**
     * Set the brushes to the color chosen
     *
     * @param color the chosen color
     */
    public void setBrushColor(int color) {
        initBrushList();
        for (ImageButton im : brushes) {
            GradientDrawable d = (GradientDrawable) im.getDrawable();
            d.setColor(color);
        }
    }

    /**
     * Make a list of the brushes
     */
    private void initBrushList() {
        if (brushes.isEmpty()) {
            brushes.addAll(Arrays.asList((ImageButton) findViewById(R.id.small_brush),
                    (ImageButton) findViewById(R.id.medium_brush),
                    (ImageButton) findViewById(R.id.large_brush),
                    (ImageButton) findViewById(R.id.largest_brush)));
        }
    }

    /**
     * Initialize all the ui components
     */
    private void init() {
        setContentView(R.layout.activity_main);

        initBrushes();
        initView();
        initLayout();
        initButtons();
        setBrushColor(drawView.getPaintColor());
    }

    /**
     * Initialize the layout and set current color to first one
     */
    private void initLayout() {
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
    }

    /**
     * Initialize the drawing view
     */
    private void initView() {
        drawView = (DrawingView) findViewById(R.id.drawing);
        drawView.setBrushSize(mediumBrush);
    }

    /**
     * Initialize the brushes
     */
    private void initBrushes() {
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        largestBrush = getResources().getInteger(R.integer.largest_size);
    }

    /**
     * Initialize the buttons
     */
    private void initButtons() {
        //erase button
        eraseBtn = (ImageButton) findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        //new button
        newBtn = (ImageButton) findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);

        //save button
        saveBtn = (ImageButton) findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);
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
    private void handleNewButton() {
        //new button
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("New drawing");
        newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
        newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                drawView.startNew();
                dialog.dismiss();
            }
        });
        newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        newDialog.show();
    }

    /**
     * Handle the save button click
     */
    private void handleSaveButton() {
        //save drawing
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing to device Gallery?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                saveImage();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    /**
     * Save the drawing and give user feedback whether the image
     * * is saved or not
     */
    private void saveImage() {
        drawView.setDrawingCacheEnabled(true);
        String imgSaved = MediaStore.Images.Media.insertImage(
                getContentResolver(), drawView.getDrawingCache(),
                UUID.randomUUID().toString() + ".png", "drawing");
        String feedback;
        if (imgSaved != null) {
            feedback = "Drawing saved to Gallery!";
        } else {
            feedback = "Oops! Image could not be saved.";
        }
        Toast toast = Toast.makeText(getApplicationContext(),
                feedback, Toast.LENGTH_SHORT);
        toast.show();
        drawView.destroyDrawingCache();
    }
}