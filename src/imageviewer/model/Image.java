package imageviewer.model;

public interface Image {
    public Object bitMap();
    public Image prev();
    public Image next();
}