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
                songList.remove(index); //Removes the song if it is found
                System.out.println(songToRemove + " has been removed from your library.\n");
                songFound = true; 
                return; // Exits the method after removing the song since there is no need to look through the rest
            }
        }

        if (!songFound) {
            System.out.println("Song not found in the library.\n"); //Notifies the user if the song isnt found
        }
    }

    /**
     * Displays all songs in songList within a table
     */
    public static void viewLibrary() {
        if (songList.isEmpty()) {
            System.out.println("Your library is empty.\n");
        } else {
            System.out.println("----- Your Song Library -----");
            //Header
            System.out.printf("%-40s %-30s %-12s\n", "Song Title", "Artist", "Play Count"); // "%-40s %-30s %-12d" represents width in characters of each table column
            System.out.println("------------------------------------------------------------------------------");

            //Rows
            for (Song song : songList) {
                System.out.printf("%-40s %-30s %-12d\n", song.getSongTitle(), song.getArtistName(), song.getPlayCount());
            }
            System.out.println();
        }
    }

    /**
     * Displays the main menu which allows the user to navigate to different areas of the app
     */
    public static void main(String[] args) {
        while (true) {
            System.out.println("----- Not Spotify -----");
            System.out.println("1. Add a new song to your library");
            System.out.println("2. Remove a song from your library");
            System.out.println("3. View your library");
            System.out.println("4. View songs by play count");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int userSelection = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (userSelection) {
                case 1:
                    addSong();
                    break;
                case 2:
                    removeSong();
                    break;
                case 3:
                    viewLibrary();
                    break;
                case 4:
                    // Implement view songs by play count functionality
                    break;
                case 5:
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.\n");
            }
        }
    }
}
