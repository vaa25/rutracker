package torrent.play;

import bt.bencoding.BEParser;
import bt.bencoding.BEType;
import bt.bencoding.model.BEList;
import bt.bencoding.model.BEMap;
import bt.bencoding.model.BEObject;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import torrent.Torrent;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static java.lang.Thread.sleep;

/**
 * Created by vaa25 on 25.03.2017.
 */
public class PlayTorrent {
    private final byte[] torrent;

    public PlayTorrent(Torrent torrent) {
        this.torrent = torrent.content();
    }


    public void play() throws IOException, InterruptedException {
        BEParser parser = new BEParser(torrent);

        TorrentRoot root = new TorrentRoot(parser.readMap().getContent());
//        System.out.println(root.creationDate());
//        System.out.println(root.publisher());
//        System.out.println(root.comment());
        System.out.println(root.announceList());
//        System.out.println(root.createdBy());
        System.out.println(root.announce());
//        System.out.println(root.encoding());
//        System.out.println(root.publisherUrl());
//        System.out.println(root.info().name());
//        List<TorrentFile> files = root.info().files().list();
//        files.forEach(file-> {
//            System.out.println(file.path());
//            System.out.println(file.length());
//        });
//        System.out.println(root.info().pieceLength());
//        System.out.println(root.info().pieces());
//        play(parser.readMap(), type);
        String announce = root.announce();
        Map<String, String> data = new HashMap<>();
//        data.put("info_hash", root.info().hash());
        data.put("info_hash", "aaaaaaaaaaaaaaaaaaaa");
        data.put("peer_id", UUID.randomUUID().toString().replace("-", "").substring(0, 20));
        data.put("peer_id", "aaaaaaaaaaaaaaaaaaaa");
        data.put("port", String.valueOf(6881));
        data.put("uploaded", String.valueOf(0));
        data.put("downloaded", String.valueOf(0));
        data.put("left", String.valueOf(root.info().files().list().stream().mapToLong(file -> file.length()).sum()));
        data.put("event", "started");
        Connection.Response response = connect(announce, data);
        response.body();
    }

    private Connection.Response connect(String announce, Map<String, String> data)
            throws IOException, InterruptedException {
        Connection.Response response = null;
        int port = 0;
        do {
            try {
                sleep(1000);
                port = new Random().nextInt(1000) + 6000;
//                data.put("port", String.valueOf(port));
                response = Jsoup.connect(announce).data(data).execute();
            } catch (HttpStatusException e) {
                System.out.println(e.toString());
            }
        } while (response == null || response.statusCode() == 403);
        System.out.println(port);
        System.out.println(response.statusCode());
        return response;
    }

    public void play(BEObject object, BEType type) {
        if (type == BEType.INTEGER) {
            BigInteger value = new BEParser(object.getContent()).readInteger().getValue();
            System.out.println(value);
        } else if (type == BEType.STRING) {
            String s = new String(object.getContent());
//            String value = new BEParser(object.getContent()).readString().getValue(Charset.forName("utf-8"));
            System.out.println(s);
        } else if (type == BEType.LIST) {
            readList(new BEParser(object.getContent()).readList());
        } else if (type == BEType.MAP) {
            readMap(new BEParser(object.getContent()).readMap());
        }
    }

    private void readList(BEList beList) {
        beList.getValue().forEach(object -> {
            BEType type = object.getType();
            play(object, type);
            System.out.println();
        });
    }

    private void readMap(BEMap map) {
        Map<String, BEObject<?>> value = map.getValue();
        value.keySet().forEach(key -> {
            System.out.println(key + ":  frozen data");
//            BEObject<?> object = value.get(key);
//            BEType type = object.getType();
//            play(object, type);
//            System.out.println();
        });
    }
}
