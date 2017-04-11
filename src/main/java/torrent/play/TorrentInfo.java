package torrent.play;

import bt.bencoding.BEParser;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vaa25 on 01.04.2017.
 */
public class TorrentInfo {

    private final BencodeMap info;
    private final byte[] bytes;

    public TorrentInfo(byte[] bytes) {
        this.info = new BencodeMap(bytes);
        this.bytes = bytes;
    }

    public List<byte[]> pieces() {
        byte[] bytes = info.get("pieces").getContent();
        List<byte[]> pieces = new ArrayList<>(bytes.length / 20);
        for (int i = 0; i < bytes.length; i += 20) {
            pieces.add(Arrays.copyOfRange(bytes, i, i + 20));
        }
        return pieces;
    }

    public String name() {
        return new String(info.get("name").getContent());
    }

    public TorrentFiles files() {
        return new TorrentFiles(info.get("files").getContent());
    }

    public BigInteger pieceLength() {
        return new BEParser(info.get("piece length").getContent()).readInteger().getValue();
    }

    public String hash() {
        return new ShaOne(bytes).toString();
    }

}
