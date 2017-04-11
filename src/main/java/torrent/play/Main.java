package torrent.play;

import torrent.FileTorrent;
import torrent.jbittorrent.BDecoder;

import java.io.IOException;
import java.util.Map;

/**
 * Created by vaa25 on 01.04.2017.
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        FileTorrent torrent = new FileTorrent("[rutracker.org].t5339459.torrent");
        Map decode = BDecoder.decode(torrent.content());

        PlayTorrent playTorrent = new PlayTorrent(torrent);

        playTorrent.play();
    }
}
