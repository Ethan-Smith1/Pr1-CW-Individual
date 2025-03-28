//These classes are used to read and write to the songs.txt file
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.util.ArrayList; //ArrayList class used to create a list of songs (which acts as the music library)
import java.util.Scanner; //Scanner class to read user inputs from the console

//These classes allow us to sort the songList alphabetically
import java.util.Collections;
import java.util.Comparator;

/**
 * The main class for the Not Spotify app.
 * It allows users to add songs, remove songs, view their library and view songs by play count.
 */
public class MusicApp {
    private static ArrayList<Song> songList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final int SONG_LENGTH = 4; //The length of time that as song will simulate being played for

    /**
        * Loads songs fom songs.txt into songList
     */
    public static void loadSongs() {
        File file = new File("songs.txt");

        //The resaon buffer reader is used is becuase it is able to read chuncks of files at a time (in this case a line) whereas the normal file reader reads one character per line making less efficent. 
        //The larger the songs.txt file gets the bigger performace impact it would have hence why I have used buffered reader
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) { 
            String line;
            while ((line = bufferedReader.readLine()) != null) { 
                String[] parts = line.split(","); //Splits the whole line which is one string into multiple where there are ',' so that each part can be assigned to a variable
                String songTitle = parts[0].trim(); 
                String artistName = parts[1].trim();
                int playCount = Integer.parseInt(parts[2].trim());
                Song song = new Song(songTitle, artistName, playCount);
                songList.add(song);
                    
                }
            }
            catch (IOException e) { 
                System.out.println("Error whilst loading songs\n");
            }
        }

    /**
     * Saves the songList to the songs.txt file
     */
    public static void saveSongs() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("songs.txt"))) {
            for (Song song : songList) {
                writer.write(song.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving songs\n");
        }
    }

    /**
     * Simulates playing a song
     */
    public static void playSong() {
        System.out.print("Enter the title of the song you want to play: ");
        String songToPlay = scanner.nextLine();

        boolean songFound = false;
        for (Song song : songList) {
            if (song.getSongTitle().equalsIgnoreCase(songToPlay)) {
                System.out.println("Now playing: " + song.getSongTitle() + " by " + song.getArtistName()+"...\n");
                songFound = true;
                try { //Thread.sleep requires a try and catch statement 
                    Thread.sleep(SONG_LENGTH*1000); //Simulates the song playing for the time specified by SONG_LENGTH (SONG_LENGTH is converted to miliseconds)
                } catch (InterruptedException e) {
                    System.out.println("There was an error playing the song\n");
                }
                return; //Exits the method after removing the song since there is no need to look through the rest
            }
        if (!songFound) {
            System.out.println("Song not found in your library\n"); //Informs the user if the song they are searching for doesnt exist
        }}
    }

    /**
     * Handles the logic for adding new songs to the songList 
     */
    public static void addSong() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine(); //Used to read what the user has entered and stores it in a variable 
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();
        System.out.print("Enter play count: ");
        int playCount = scanner.nextInt();
        scanner.nextLine();

        Song song = new Song(title, artist, playCount); //Creates a new song object with the info provided by the user
        songList.add(song);//Adds the new object to songList (essentailly adding a new song to the music library)
        System.out.println(song.getSongTitle()+" by "+song.getArtistName()+" added to library!\n");
        Collections.sort(songList, Comparator.comparing(Song::getSongTitle)); //Sorts songList alphabetically (A-Z)
        saveSongs(); //saves the changes to songList
    }

    /**
     * Handles the logic for removing a song from the songList by its title.
     */
    public static void removeSong() {
        System.out.print("Enter the title of the song that you want to remove from your library: ");
        String songToRemove = scanner.nextLine(); 

        boolean songFound = false; 
        for (int index = 0; index < songList.size(); index++) { 
            Song song = songList.get(index);
            if (song.getSongTitle().equalsIgnoreCase(songToRemove)) {
                songFound = true; 
                songList.remove(index); //Removes the song if it is found
                System.out.println(songToRemove + " has been removed from your library.\n");
                saveSongs(); //Saves the changes to songList
                return; //Exits the method after removing the song since there is no need to look through the rest
            }
        }

        if (!songFound) {
            System.out.println("Song not found in your library.\n"); //Notifies the user if the song isnt found
        }
    }

    /**
     * Displays all songs in songList
     */
    public static void viewLibrary() {
        if (songList.isEmpty()) {
            System.out.println("Your library is empty.\n");
        } else {
            System.out.println("----- Your Song Library -----");
            //Header
            System.out.printf("%-40s %-30s %-12s\n", "Song Title", "Artist", "Play Count"); // "%-40s %-30s %-12d" represents width in characters of each table column
            System.out.println("---------------------------------------------------------------------------------------");

            //Rows
            for (Song song : songList) {
                System.out.printf("%-40s %-30s %-12d\n", song.getSongTitle(), song.getArtistName(), song.getPlayCount());
            }
            System.out.println();
        }
    }

    /**
     * Displays all songs with a play count equal to or higher than the user's input
     */
    public static void viewSongsByPlayCount() {
        System.out.print("Enter the minimum play count: ");
        int minPlayCount = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Song> filteredSongList = new ArrayList<>(); //New list where only songs with a playCount >= minPlayCount will be added
        for (Song song : songList) {
            if (song.getPlayCount() >= minPlayCount) {
                filteredSongList.add(song); //Adds the song to the filtered list
            }
        }

        if (filteredSongList.isEmpty()) {
            System.out.println("No songs found with a play count of " + minPlayCount + " or higher.\n");
        } else {
            Collections.sort(filteredSongList, Comparator.comparingInt(Song::getPlayCount).reversed()); // Sorts filteredSongList by highest play count to lowest

            System.out.println("----- Songs with at least " + minPlayCount + " plays -----");
            System.out.printf("%-40s %-30s %-12s\n", "Song Title", "Artist", "Play Count");
            System.out.println("---------------------------------------------------------------------------------------");

            //Rows
            for (Song song : filteredSongList) {
                System.out.printf("%-40s %-30s %-12d\n", song.getSongTitle(), song.getArtistName(), song.getPlayCount());
            }
            System.out.println();
        }
    }

    /**
     * Displays the main menu which allows the user to navigate to different areas of the app
     */
    public static void main(String[] args) {
        loadSongs();
        while (true) {
            System.out.println("----- Not Spotify -----");
            System.out.println("1. Play song");
            System.out.println("2. Add a new song to your library");
            System.out.println("3. Remove a song from your library");
            System.out.println("4. View your library");
            System.out.println("5. View songs by play count");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int userSelection = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (userSelection) {
                case 1:
                    playSong();
                    break;
                case 2:
                    addSong();
                    break;
                case 3:
                    removeSong();
                    break;
                case 4:
                    viewLibrary();
                    break;
                case 5:
                    viewSongsByPlayCount();
                    break;
                case 6:
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.\n");
            }
        }
    }
}
