package torrent.play;

import bt.bencoding.BEParser;
import bt.bencoding.model.BEObject;
import lombok.ToString;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * Created by vaa25 on 01.04.2017.
 */
@ToString
public class TorrentFile {

    private final BencodeMap files;

    public TorrentFile(byte[] bytes) {
        this.files = new BencodeMap(bytes);
    }

    public String path() {
        List<? extends BEObject<?>> path = new BEParser(files.get("path").getContent()).readList().getValue();
        return path.stream().map(o -> new String(o.getContent())).collect(joining("/"));
    }

    public Long length() {
        return new BEParser(files.get("length").getContent()).readInteger().getValue().longValueExact();
    }


}
