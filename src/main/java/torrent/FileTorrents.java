package torrent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by vaa25 on 25.03.2017.
 */
public class FileTorrents implements Torrents {

    private final String folder;

    public FileTorrents(String folder) {
        this.folder = folder;
    }

    @Override
    public List<Torrent> get() {
        Path path = Paths.get(folder);
        try {
            TorrentVisitor visitor = new TorrentVisitor();
            Files.walkFileTree(path, visitor);
            return visitor.getTorrents();
        } catch (IOException e) {
            throw new RuntimeException("Can't read torrents from folder " + folder, e);
        }
    }

}
