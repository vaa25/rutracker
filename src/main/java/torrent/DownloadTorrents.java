package torrent;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by vaa25 on 25.03.2017.
 */
public class DownloadTorrents implements Torrents {

    private final List<String> urls;
    private final Map<String, String> cookies;

    public DownloadTorrents(List<String> urls, Map<String, String> cookies) {
        this.urls = urls;
        this.cookies = cookies;
    }

    @Override
    public List<Torrent> get() {
        return urls.stream().map(string -> {
            try {
                return new ResponseTorrent(Jsoup.connect(string).ignoreContentType(true).cookies(cookies).execute());
            } catch (IOException e) {
                throw new RuntimeException("Can't download torrent " + string, e);
            }
        }).collect(toList());
    }
}
