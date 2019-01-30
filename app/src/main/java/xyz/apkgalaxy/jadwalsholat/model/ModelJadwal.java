package xyz.apkgalaxy.jadwalsholat.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelJadwal {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("map_image")
    @Expose
    private String mapImage;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public String getMapImage() {
        return mapImage;
    }

    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}