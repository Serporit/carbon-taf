package utils;

import com.coremedia.iso.IsoFile;

import java.io.IOException;

public class VideoUtil {
    public static int getVideoDuration(String filePath) {
        IsoFile isoFile = null;
        try {
            isoFile = new IsoFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (int) (isoFile.getMovieBox().getMovieHeaderBox().getDuration() / isoFile.getMovieBox().getMovieHeaderBox().getTimescale() + 1);
    }


    public static int getSampleDuration(String exercise) {
        return getVideoDuration("/home/zerg/carbon-taf/src/test/resources/videos/" + exercise + ".mp4");
    }
}
