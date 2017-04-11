package torrent;

import org.jsoup.Connection;

/**
 * Created by vaa25 on 25.03.2017.
 */
public class ResponseTorrent extends AbstractTorrent implements Torrent {

    private final Connection.Response response;

    public ResponseTorrent(Connection.Response response) {
        this.response = response;
    }

    @Override
    public String name() {
        return response.contentType().split("=")[1].replace("\"", "");
    }

    @Override
    public byte[] content() {
        return response.bodyAsBytes();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
