package com.djp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * An activity representing a list of Jigsaws. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link JigsawDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link JigsawListFragment} and the item details
 * (if present) is a {@link JigsawDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link JigsawListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class JigsawListActivity extends FragmentActivity
        implements JigsawListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jigsaw_list);

        if (findViewById(R.id.jigsaw_detail_container) != null) {
            mTwoPane = true;

            ((JigsawListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.jigsaw_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link JigsawListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(JigsawDetailFragment.ARG_ITEM_ID, id);
            JigsawDetailFragment fragment = new JigsawDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.jigsaw_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, JigsawDetailActivity.class);
            detailIntent.putExtra(JigsawDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
