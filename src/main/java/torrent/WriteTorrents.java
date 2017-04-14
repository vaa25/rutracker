package torrent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Created by vaa25 on 25.03.2017.
 */
public class WriteTorrents {
    private final String folder;

    public WriteTorrents(String folder) {
        this.folder = folder;
    }

    public void write(Collection<Torrent> torrents) {
        torrents.forEach(this::write);
    }

    private void write(Torrent torrent) {
        Path path = Paths.get(folder, torrent.name());
        try {
            Files.write(path, torrent.content());
            System.out.println("Write " + torrent.name() + " in " + folder);
        } catch (IOException e) {
            throw new RuntimeException("Can't write torrent " + torrent.name() + " in folder " + folder);
        }
    }
}
