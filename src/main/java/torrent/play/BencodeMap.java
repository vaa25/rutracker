package torrent.play;

import bt.bencoding.BEParser;
import bt.bencoding.model.BEObject;

import java.util.Set;

/**
 * Created by vaa25 on 01.04.2017.
 */
public class BencodeMap {

    private final byte[] bytes;

    public BencodeMap(byte[] bytes) {
        this.bytes = bytes;
    }

    public BEObject<?> get(String key) {
        return new BEParser(bytes).readMap().getValue().get(key);
    }

    public Set<String> keySet() {
        return new BEParser(bytes).readMap().getValue().keySet();
    }

    @Override
    public String toString() {
        return new BEParser(bytes).readMap().getValue().toString();
//        return keySet().toString();
    }
}
