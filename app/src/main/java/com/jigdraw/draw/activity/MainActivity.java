package com.jigdraw.draw.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Main activity class
 *
 * @author Jay Paulynice
 */
public class MainActivity extends Activity implements OnClickListener {
    private static final String TAG = "MainActivity";
    private DrawingView drawView;
    private ImageButton currPaint, eraseBtn, newBtn, saveBtn;
    private float smallBrush, mediumBrush, largeBrush, largestBrush;

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

    public void handleBrushSize(View view) {
        if (view.getId() == R.id.small_brush) {
            Log.d(TAG, "small brush clicked.");
            drawView.setErase(false);
            drawView.setBrushSize(smallBrush);
            drawView.setLastBrushSize(smallBrush);
        } else if (view.getId() == R.id.medium_brush) {
            Log.d(TAG, "medium brush clicked.");
            drawView.setErase(false);
            drawView.setBrushSize(mediumBrush);
            drawView.setLastBrushSize(mediumBrush);
        } else if (view.getId() == R.id.large_brush) {
            Log.d(TAG, "large brush clicked.");
            drawView.setErase(false);
            drawView.setBrushSize(largeBrush);
            drawView.setLastBrushSize(largeBrush);
        } else if (view.getId() == R.id.largest_brush) {
            Log.d(TAG, "largest brush clicked.");
            drawView.setErase(false);
            drawView.setBrushSize(largestBrush);
            drawView.setLastBrushSize(largestBrush);
        }
    }

    /**
     * @param view
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
            setBrushColor(color);
        }
    }

    public void setBrushColor(String color) {
        ImageButton a = (ImageButton) findViewById(R.id.small_brush);
        ImageButton b = (ImageButton) findViewById(R.id.medium_brush);
        ImageButton c = (ImageButton) findViewById(R.id.large_brush);
        ImageButton d = (ImageButton) findViewById(R.id.largest_brush);

        a.setBackgroundColor(Color.parseColor(color));
        b.setBackgroundColor(Color.parseColor(color));
        c.setBackgroundColor(Color.parseColor(color));
        d.setBackgroundColor(Color.parseColor(color));
    }

    private void init() {
        setContentView(R.layout.activity_main);

        initLayout();
        initBrushes();
        initView();
        initButtons();
    }

    private void initLayout() {
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
    }

    private void initView() {
        drawView = (DrawingView) findViewById(R.id.drawing);
        drawView.setBrushSize(mediumBrush);
    }

    private void initBrushes() {
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        largestBrush = getResources().getInteger(R.integer.largest_size);
    }

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

    private void handleEraseButton() {
        drawView.setErase(true);
    }

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

    private void handleSaveButton() {
        //save drawing
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing to device Gallery?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //save drawing
                drawView.setDrawingCacheEnabled(true);
                //attempt to save
                String imgSaved = MediaStore.Images.Media.insertImage(
                        getContentResolver(), drawView.getDrawingCache(),
                        UUID.randomUUID().toString() + ".png", "drawing");
                //feedback
                if (imgSaved != null) {
                    Toast savedToast = Toast.makeText(getApplicationContext(),
                            "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                    savedToast.show();
                } else {
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }
                drawView.destroyDrawingCache();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        saveDialog.show();
    }
}
