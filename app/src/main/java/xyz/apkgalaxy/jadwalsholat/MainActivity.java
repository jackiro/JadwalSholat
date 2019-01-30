package xyz.apkgalaxy.jadwalsholat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.apkgalaxy.jadwalsholat.api.ApiService;
import xyz.apkgalaxy.jadwalsholat.api.ApiUrl;
import xyz.apkgalaxy.jadwalsholat.model.ModelJadwal;

public class MainActivity extends AppCompatActivity {
    String kota = "Jember";

    private TextView tvTanggal, tvLokasi, tvFajar, tvShuruq, tvDhuhur, tvAshar, tvMaghrib, tvIsya;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTanggal = findViewById(R.id.tvTanggal);
        tvLokasi = findViewById(R.id.tvLokasi);
        tvFajar = findViewById(R.id.tvFajar);
        tvShuruq = findViewById(R.id.tvShuruq);
        tvDhuhur = findViewById(R.id.tvDhuhur);
        tvAshar = findViewById(R.id.tvAshar);
        tvMaghrib = findViewById(R.id.tvMaghrib);
        tvIsya = findViewById(R.id.tvIsya);

        getJadwal();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menurefresh, menu);
        inflater.inflate(R.menu.menubahasa, menu);
        inflater.inflate(R.menu.menusearch, menu);

        MenuItem item = menu.findItem(R.id.search);
        final android.widget.SearchView searchView = (android.widget.SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                kota = query;
                getJadwal();
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.refreshlok) {
            getJadwal();
        }

        if (item.getItemId() == R.id.changelanguage) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }

        return true;
    }

    private void getJadwal() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...!!!");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ModelJadwal> call = apiService.getJadwal(kota);

        call.enqueue(new Callback<ModelJadwal>() {
            @Override
            public void onResponse(Call<ModelJadwal> call, Response<ModelJadwal> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    Calendar c1 = Calendar.getInstance();
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                    String strdate1 = sdf1.format(c1.getTime());

                    tvTanggal.setText(": " + strdate1);

                    String Lokasi = response.body().getCity();
                    if (Lokasi == "") {
                        tvLokasi.setText(": " + kota);
                    } else {
                        tvLokasi.setText(": " + Lokasi);
                    }
                    tvFajar.setText(response.body().getItems().get(0).getFajr());
                    tvShuruq.setText(response.body().getItems().get(0).getShurooq());
                    tvDhuhur.setText(response.body().getItems().get(0).getDhuhr());
                    tvAshar.setText(response.body().getItems().get(0).getAsr());
                    tvMaghrib.setText(response.body().getItems().get(0).getMaghrib());
                    tvIsya.setText(response.body().getItems().get(0).getIsha());

                }
            }

            @Override
            public void onFailure(Call<ModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Please, Try Again..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
