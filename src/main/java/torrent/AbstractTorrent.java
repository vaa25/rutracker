package torrent;

import bt.bencoding.BEParser;
import torrent.play.TorrentRoot;

import java.util.Arrays;

/**
 * Created by vaa25 on 25.03.2017.
 */
public abstract class AbstractTorrent implements Torrent {

    public String getName() {
        return name();
    }

    public byte[] getContent() {
        return content();
    }

    @Override
    public TorrentRoot root() {
        return new TorrentRoot(new BEParser(content()).readMap().getContent());
    }

    @Override
    public String toString() {
        return name() + "(" + content().length + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractTorrent)) {
            return false;
        }

        AbstractTorrent that = (AbstractTorrent) o;

        return Arrays.equals(getContent(), that.getContent());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getContent());
    }
}
