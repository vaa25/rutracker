package torrent;

/**
 * Created by vaa25 on 14.04.2017.
 */
public class RutrackerTorrentUrl {
    private final Torrent torrent;

    public RutrackerTorrentUrl(Torrent torrent) {
        this.torrent = torrent;
    }

    public String get() {
        return "rutracker.org".equals(torrent.root().publisher()) ? torrent
                .root()
                .publisherUrl()
                .replace("viewtopic", "dl") : "";
    }
}
