package torrent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by vaa25 on 25.03.2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    public void run() throws Exception {
        final Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/torrent.properties"));
        final Map<String, String> cookies = new Login(
                properties.getProperty("rutracker.login"),
                properties.getProperty("rutracker.password")
        ).cookies();
        final String loadedFolder = properties.getProperty("torrent.loaded.folder");
        final String autoloadFolder = properties.getProperty("torrent.autoload.folder");
        final List<Torrent> finished = new FileTorrents(loadedFolder)
                .get()
                .stream()
                .filter(torrent -> "rutracker.org".equals(torrent.root().publisher()))
                .collect(
                        toList());
        final Torrent next = finished.get(0);
//        new PlayTorrent(next).play();
        final Map<Torrent, Torrent> downloaded = new DownloadTorrents(finished, cookies).get();
        final List<Torrent> autoload = new FileTorrents(autoloadFolder).get();
        final List<Torrent> loading = new FileTorrents(properties.getProperty("torrent.loading.folder")).get();
        final Map<Torrent, Torrent> updated = downloaded
                .keySet()
                .stream()
                .filter(torrent -> !Arrays.equals(torrent.content(), downloaded.get(torrent).content()))
                .filter(torrent -> !inTorrents(torrent, autoload))
                .filter(torrent -> !inTorrents(torrent, loading))
                .collect(toMap(
                        torrent -> torrent,
                        downloaded::get
                ));
        deleteUpdated(loadedFolder, updated.keySet());
        new WriteTorrents(autoloadFolder).write(updated.values());
        System.out.println("in autoload   " + autoload);
        System.out.println("in finished   " + finished);
        System.out.println("in loading   " + loading);
        System.out.println("deleted   " + updated.keySet());
        System.out.println("saved   " + updated.values());


    }

    private boolean inTorrents(Torrent torrent, List<Torrent> torrents) {
        return torrents
                .stream()
                .map(torrent1 -> torrent1.content())
                .anyMatch(bytes -> Arrays.equals(bytes, torrent.content()));
    }

    private void deleteUpdated(String loadedFolder, Collection<Torrent> updated) throws IOException {
        for (Torrent torrent : updated) {
            final Path path = Paths.get(loadedFolder, torrent.name());
            System.out.println(path);
            Files.delete(path);
        }
    }

}
