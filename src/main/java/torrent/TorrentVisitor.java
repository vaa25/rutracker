package torrent;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaa25 on 25.03.2017.
 */
@Getter
public class TorrentVisitor extends SimpleFileVisitor<Path> {

    private final List<Torrent> torrents;

    public TorrentVisitor() {
        torrents = new ArrayList<>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().endsWith(".torrent")) {
            torrents.add(new FileTorrent(file.toString()));
        }
        return super.visitFile(file, attrs);
    }

}
