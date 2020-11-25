package game.loaders;

import javax.swing.ImageIcon;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {

    private File imageFolder;

    public ImageLoader(File imageFolder) {
        this.imageFolder = imageFolder;
    }

    public List<ImageIcon> loadImages(int imageCount) {
        List<ImageIcon> knownImages = new ArrayList<>();
        File[] imageFiles = imageFolder.listFiles();
        for (File file : imageFiles) {
            knownImages.add(new ImageIcon(file.getAbsolutePath()));
        }

        if (imageCount <= knownImages.size()) {
            return knownImages.subList(0, imageCount);
        }

        List<ImageIcon> images = new ArrayList<>();
        int imagesLeft = imageCount;
        while (imagesLeft > 0) {
            images.addAll(knownImages);
            imagesLeft -= knownImages.size();
        }

        if (imagesLeft < 0) {
            return images.subList(0, imageCount);
        }

        return images;
    }

    public ImageIcon loadBackside(){
        return new ImageIcon(this.getClass().getResource("/backside/cross.png"));
    }

}
