package torrent.play;

import bt.bencoding.BEParser;
import bt.bencoding.model.BEObject;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.ZoneOffset.UTC;
import static java.util.stream.Collectors.toList;

/**
 * Created by vaa25 on 01.04.2017.
 */
public class TorrentRoot {

    private final BencodeMap root;

    public TorrentRoot(byte[] bytes) {
        this.root = new BencodeMap(bytes);
    }

    public LocalDateTime creationDate() {
        byte[] content = root.get("creation date").getContent();
        long epochSecond = new BEParser(content).readInteger().getValue().longValueExact();
        return LocalDateTime.ofEpochSecond(epochSecond, 0, UTC);
    }

    public String publisher() {
        final BEObject<?> publisher = root.get("publisher");
        return publisher == null ? "" : new String(publisher.getContent());
    }

    public String comment() {
        return new String(root.get("comment").getContent());
    }

    public String encoding() {
        return new String(root.get("encoding").getContent());
    }

    public String publisherUrl() {
        return new String(root.get("publisher-url").getContent());
    }

    public String createdBy() {
        return new String(root.get("created by").getContent());
    }

    public String announce() {
        return new String(root.get("announce").getContent());
    }

    public TorrentInfo info() {
        return new TorrentInfo(root.get("info").getContent());
    }

    public List<String> announceList() {
        return new BEParser(root.get("announce-list").getContent())
                .readList()
                .getValue()
                .stream()
                .flatMap(announce -> new BEParser(announce.getContent()).readList().getValue().stream())
                .map(o -> new String(o.getContent()))
                .collect(toList());

    }

}
