package torrent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static java.util.stream.Collectors.toList;

/**
 * Created by vaa25 on 25.03.2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    public void run() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/torrent.properties"));

//        List<String> urls = new FileTorrentUrls("/links.txt").torrentUrls();
        Map<String, String> cookies = new Login(
                properties.getProperty("rutracker.login"),
                properties.getProperty("rutracker.password")
        ).cookies();
        final String loadedFolder = properties.getProperty("torrent.loaded.folder");
        List<Torrent> loaded = new FileTorrents(loadedFolder).get();
        List<String> urls = loaded
                .stream()
                .map(Torrent::root)
                .filter(torrent -> "rutracker.org".equals(torrent.publisher()))
                .map(torrent -> torrent.publisherUrl().replace("viewtopic", "dl"))
                .distinct()
                .collect(toList());
        List<Torrent> download = new DownloadTorrents(urls, cookies).get();
//        download.stream().map(PlayTorrent::new).forEach(PlayTorrent::play);
        String autoloadFolder = properties.getProperty("torrent.autoload.folder");
        List<Torrent> autoload = new FileTorrents(autoloadFolder).get();
        List<Torrent> loading = new FileTorrents(properties.getProperty("torrent.loading.folder")).get();
        List<Torrent> removing = new ArrayList<>(download);
        removing.removeAll(autoload);
        removing.removeAll(loading);
        final List<Torrent> updated = new ArrayList<>(loaded);
        updated.removeAll(removing);
        deleteUpdated(loadedFolder, updated);
        removing.removeAll(loaded);
        new WriteTorrents(autoloadFolder).write(removing);
        System.out.println("download   " + download);
        System.out.println("autoload   " + autoload);
        System.out.println("loaded   " + loaded);
        System.out.println("loading   " + loading);
        System.out.println("left   " + removing);
        System.out.println("deleted   " + updated);


    }

    private void deleteUpdated(String loadedFolder, List<Torrent> updated) throws IOException {
        for (Torrent torrent : updated) {
            final Path path = Paths.get(loadedFolder, torrent.name());
            System.out.println(path);
            Files.delete(path);
        }
    }

}
