package br.com.concretesolutions.audience.demo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.concretesolutions.audience.demo.R;

public final class RepositoriesActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Audience_Theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        initToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); // no up task from here =(
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
