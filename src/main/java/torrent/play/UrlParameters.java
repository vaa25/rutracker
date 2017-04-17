package torrent.play;

import java.util.Map;

import static java.util.stream.Collectors.joining;

/**
 * Created by vaa25 on 16.04.2017.
 */
public class UrlParameters {

    private final Map<String, String> data;

    public UrlParameters(Map<String, String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.entrySet().stream().map(s -> s.getKey() + "=" + s.getValue()).collect(joining("&", "?", ""));
    }
}
