package torrent;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Created by vaa25 on 25.03.2017.
 */
public class DownloadTorrents {

    private final List<Torrent> torrents;
    private final Map<String, String> cookies;

    public DownloadTorrents(List<Torrent> torrents, Map<String, String> cookies) {
        this.torrents = torrents;
        this.cookies = cookies;
    }

    public Map<Torrent, Torrent> get() {
        return torrents
                .stream()
                .collect(toMap(torrent -> torrent, torrent -> downloadTorrent(new RutrackerTorrentUrl(torrent).get())));
    }

    private ResponseTorrent downloadTorrent(String url) {
        try {
            return new ResponseTorrent(Jsoup.connect(url).ignoreContentType(true).cookies(cookies).execute());
        } catch (IOException e) {
            throw new RuntimeException("Can't download torrent " + url, e);
        }
    }

}
