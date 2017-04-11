package torrent;

import torrent.play.TorrentRoot;

/**
 * Created by vaa25 on 25.03.2017.
 */
public interface Torrent {
    String name();

    byte[] content();

    TorrentRoot root();

}
