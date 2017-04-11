package torrent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by vaa25 on 25.03.2017.
 */
public class FileTorrent extends AbstractTorrent implements Torrent {

    private final String file;

    public FileTorrent(String file) {
        this.file = file;
    }

    @Override
    public String name() {
        return Paths.get(file).getFileName().toString();
    }

    @Override
    public byte[] content() {
        try {
            return Files.readAllBytes(Paths.get(file));
        } catch (IOException e) {
            throw new RuntimeException("Can't read torrent file " + file, e);
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
