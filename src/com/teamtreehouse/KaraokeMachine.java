package com.teamtreehouse;

import com.teamtreehouse.model.Song;
import com.teamtreehouse.model.SongBook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class KaraokeMachine {
    private SongBook mSongbook;
    private BufferedReader mReader;
    private Map<String, String> mMenu;
    private Queue<Song> mSongQue;

    public KaraokeMachine(SongBook songBook) {
        mSongbook = songBook;
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mSongQue = new ArrayDeque<Song>();
        mMenu = new HashMap<>();
        mMenu.put("add", "Add a new song to the song book");
        mMenu.put("play", "Play next song in the queue");
        mMenu.put("choose", "a song to sing");
        mMenu.put("quit", "Give up.     Exit the program");
    }

    private String promptAction() throws IOException {
        System.out.printf("There Are %d songs available and %d in the queue . Your options are: %n%n", mSongbook.getSongCount(), mSongQue.size());

        for (Map.Entry<String, String> option : mMenu.entrySet()) {
            System.out.printf("%s - %s %n", option.getKey(), option.getValue());
        }

        System.out.print("What do you want to do:   ");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice) {
                    case "add":
                        Song song = promptNewSong();
                        mSongbook.addSong(song);
                        System.out.printf("%s added!    %n%n", song);
                        break;
                    case "choose":
                        String artist = promptArtist();
                        Song artistSong = promptSongForArtist(artist);
                        mSongQue.add(artistSong);
                        System.out.printf("You Chose: %s %n%n", artistSong);
                        break;
                    case "play":
                        playNext();
                        break;
                    case "quit":
                        System.out.println("Thanks for playing");
                        break;
                    default:
                        System.out.printf("Unknown Choice: '$s'. Try Again. %n%n%n", choice);
                }
            } catch (IOException ioe) {
                System.out.println("Problem with input");
                ioe.printStackTrace();
            }
        } while (!choice.equals("quit"));
    }

    private Song promptNewSong() throws IOException {
        System.out.print("Enter the artist's name:  ");
        String artist = mReader.readLine();
        System.out.print("Enter the title:  ");
        String title = mReader.readLine();
        System.out.print("Enter the video URL:  ");
        String videoUrl = mReader.readLine();
        return new Song(artist, title, videoUrl);
    }

    private String promptArtist() throws IOException {
        System.out.println("Available artists:");
        List<String> artist = new ArrayList<>(mSongbook.getArtists());
        int index = promptForIndex(artist);
        return artist.get(index);
    }

    private Song promptSongForArtist(String artist) throws IOException {
        List<Song> songs = mSongbook.getSongsForArtists(artist);
        List<String> songTitles = new ArrayList<>();
        for (Song song : songs) {
            songTitles.add(song.getTitle());
        }
        System.out.printf("Available songs for %s %n", artist);
        int index = promptForIndex(songTitles);
        return songs.get(index);
    }

    private int promptForIndex(List<String> options) throws IOException {
        int counter = 1;
        for (String option : options) {
            System.out.printf("%d.)  %s %n", counter, option);
            counter++;
        }
        System.out.printf("Your choice:     ");
        String optionsAsString = mReader.readLine();
        int choice = Integer.parseInt(optionsAsString.trim());
        return choice - 1;
    }

    public void playNext() {
        Song song = mSongQue.poll();
        if (song == null) {
            System.out.println("Sorry there are no songs in the queue. Use choose from the menu to add some");
        } else {
            System.out.printf("%n%n%n Open %s to hear %s by %s %n%n%n", song.getVideoUrl(), song.getTitle(), song.getArtist());
        }
    }
}

