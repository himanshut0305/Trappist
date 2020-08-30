package in.co.qedtech.trappist.slideResponse;

public class DIG {
    private String caption;
    private String url;

    public DIG(String caption, String url) {
        this.caption = caption;
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DIG{" +
                "caption='" + caption + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
