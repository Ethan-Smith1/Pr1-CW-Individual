/**
 * Represents a song object with its title, its artists name and its play count
 */
public class Song {
    private String songTitle; //name of the song
    private String artistName; //name of the artist who made that song
    private int playCount; //the number of times that that song has been played

    /**
     * The constructor for the song class
     */
    public Song(String songTitle, String artistName, int playCount) {
        this.songTitle = songTitle;
        this.artistName = artistName;
        this.playCount = playCount;
    }

    /**
     * Returns a string representation of the song so that it can be written to the songs.txt file
     */
    @Override //Overrides the default toString() method
    public String toString() {
        return songTitle + "," + artistName + "," + playCount;
    }

    /**
     * Increments playCount by one each time the song is listened to
     */
    public void incrementPlayCount() {
        playCount++; 
    }

    /**
     * Gets the songs title
     */
    public String getSongTitle() {
        return songTitle;
    }

    /**
     * Gets the artists name
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Gets the songs play count
     */
    public int getPlayCount() {
        return playCount;
    }
}

