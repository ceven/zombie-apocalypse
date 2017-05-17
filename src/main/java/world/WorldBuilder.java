package world;

public class WorldBuilder {

    private int width;
    private int height;

    public WorldBuilder() {

    }

    public WorldBuilder ofWidth(int width) {
        this.width = width;
        return this;
    }

    public WorldBuilder ofHeight(int height) {
        this.height = height;
        return this;
    }

    public World build() {
        return new World(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
