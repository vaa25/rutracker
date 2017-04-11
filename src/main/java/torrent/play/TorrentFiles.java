package torrent.play;

import bt.bencoding.BEParser;
import bt.bencoding.model.BEList;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by vaa25 on 01.04.2017.
 */
public class TorrentFiles {

    private final BEList files;

    public TorrentFiles(byte[] bytes) {
        this.files = new BEParser(bytes).readList();
    }

    public List<TorrentFile> list() {
        return files.getValue().stream().map(file -> new TorrentFile(file.getContent())).collect(toList());
    }

}
