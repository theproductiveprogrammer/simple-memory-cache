package simple.memory.cache;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.HttpRequest;
import io.micronaut.views.View;
import io.micronaut.http.uri.UriBuilder;

import io.reactivex.Maybe;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;


@Controller
public class Tester {

    @Inject
    @Client
    RxHttpClient client;

    @View("/index")
    @Get
    public Maybe<PexelPhotoResponse> index(String q) {
        return main(q);
    }

    @Get("/pexels")
    public Maybe<PexelPhotoResponse> main(String q) {
        String uri = UriBuilder.of("https://api.pexels.com/v1/search")
            .queryParam("query", q)
            .toString();
        return client
                .retrieve(
                        HttpRequest.GET(uri)
                        .header("Authorization", "<<your auth key>>"),
                        PexelPhotoResponse.class
                        )
                .firstElement();
    }

@Generated("jsonschema2pojo")
public static class PexelPhotoResponse {

private Integer totalResults;
private Integer page;
private Integer perPage;
private List<Photo> photos = null;
private String nextPage;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Integer getTotalResults() {
return totalResults;
}

public void setTotalResults(Integer totalResults) {
this.totalResults = totalResults;
}

public Integer getPage() {
return page;
}

public void setPage(Integer page) {
this.page = page;
}

public Integer getPerPage() {
return perPage;
}

public void setPerPage(Integer perPage) {
this.perPage = perPage;
}

public List<Photo> getPhotos() {
return photos;
}

public void setPhotos(List<Photo> photos) {
this.photos = photos;
}

public String getNextPage() {
return nextPage;
}

public void setNextPage(String nextPage) {
this.nextPage = nextPage;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

@Generated("jsonschema2pojo")
public static class Photo {

private Integer id;
private Integer width;
private Integer height;
private String url;
private String photographer;
private String photographerUrl;
private Integer photographerId;
private String avgColor;
private Src src;
private Boolean liked;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getWidth() {
return width;
}

public void setWidth(Integer width) {
this.width = width;
}

public Integer getHeight() {
return height;
}

public void setHeight(Integer height) {
this.height = height;
}

public String getUrl() {
return url;
}

public void setUrl(String url) {
this.url = url;
}

public String getPhotographer() {
return photographer;
}

public void setPhotographer(String photographer) {
this.photographer = photographer;
}

public String getPhotographerUrl() {
return photographerUrl;
}

public void setPhotographerUrl(String photographerUrl) {
this.photographerUrl = photographerUrl;
}

public Integer getPhotographerId() {
return photographerId;
}

public void setPhotographerId(Integer photographerId) {
this.photographerId = photographerId;
}

public String getAvgColor() {
return avgColor;
}

public void setAvgColor(String avgColor) {
this.avgColor = avgColor;
}

public Src getSrc() {
return src;
}

public void setSrc(Src src) {
this.src = src;
}

public Boolean getLiked() {
return liked;
}

public void setLiked(Boolean liked) {
this.liked = liked;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
@Generated("jsonschema2pojo")
public static class Src {

private String original;
private String large2x;
private String large;
private String medium;
private String small;
private String portrait;
private String landscape;
private String tiny;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getOriginal() {
return original;
}

public void setOriginal(String original) {
this.original = original;
}

public String getLarge2x() {
return large2x;
}

public void setLarge2x(String large2x) {
this.large2x = large2x;
}

public String getLarge() {
return large;
}

public void setLarge(String large) {
this.large = large;
}

public String getMedium() {
return medium;
}

public void setMedium(String medium) {
this.medium = medium;
}

public String getSmall() {
return small;
}

public void setSmall(String small) {
this.small = small;
}

public String getPortrait() {
return portrait;
}

public void setPortrait(String portrait) {
this.portrait = portrait;
}

public String getLandscape() {
return landscape;
}

public void setLandscape(String landscape) {
this.landscape = landscape;
}

public String getTiny() {
return tiny;
}

public void setTiny(String tiny) {
this.tiny = tiny;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}


}
