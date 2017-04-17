package torrent.play;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by vaa25 on 04.04.2017.
 */
public class ShaOne {

    private final byte[] bytes;

    public ShaOne(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        try {
            return byteArray2Hex(MessageDigest.getInstance("SHA-1").digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Imposible! There is no SHA-1 encoder!", e);
        }
    }

    private String byteArray2Hex(final byte[] hash) {
        final Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%%%02x", b);
        }
        return formatter.toString();
    }
}
