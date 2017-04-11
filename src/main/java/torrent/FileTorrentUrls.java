package torrent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by vaa25 on 25.03.2017.
 */
public class FileTorrentUrls {

    private final String file;

    public FileTorrentUrls(String file) {
        this.file = file;
    }

    public List<String> torrentUrls() {
        try {
            List<String> strings = Files.readAllLines(Paths.get(this.getClass().getResource(file).toURI()));
            return strings.stream().map(s -> s.replace("viewtopic", "dl")).collect(toList());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Torrent list file name is not URI", e);
        } catch (IOException e) {
            throw new RuntimeException("Can't read torrent list file", e);
        }
    }
}
