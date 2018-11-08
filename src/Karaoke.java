import com.teamtreehouse.KaraokeMachine;
import com.teamtreehouse.model.SongBook;

import java.io.IOException;

public class Karaoke {

    public static void main(String[] args) throws IOException {
        SongBook songBook = new SongBook();
        songBook.importFrom("songs.txt");
        KaraokeMachine machine = new KaraokeMachine(songBook);
        machine.run();
        System.out.println("Saving Book.........");
        songBook.exportTo("songs.txt");

    }
}
