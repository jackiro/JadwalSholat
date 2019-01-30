package xyz.apkgalaxy.jadwalsholat.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.apkgalaxy.jadwalsholat.model.ModelJadwal;

public interface ApiService {
    @GET("{kota}/daily.json?key=196e97e6cf7163c74e125fe0b869b38c")
    Call<ModelJadwal> getJadwal(@Path("kota") String kota);
}
