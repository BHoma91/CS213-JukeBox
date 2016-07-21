package application;

public class Songs implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public String songName;
	public String artistName;
	public String albumName;
	public String year;

	public Songs(String sName, String arName, String alName, String y ){
		this.songName = sName;
		this.artistName = arName;
		this.albumName = alName;
		this.year = y;
	}
	public Songs(String sName, String arName, String alName) {
		this.songName = sName;
		this.artistName = arName;

		if (tryParseInt(alName)) {
			this.albumName = null;
			this.year = alName;
			}
		else {
			this.albumName = alName;
			this.year = null;
		}
	}
	public Songs(String sName, String arName){
		this.songName = sName;
		this.artistName = arName;
	}

	boolean tryParseInt(String value) {
	     try {
	         Integer.parseInt(value);
	         return true;
	      } catch (NumberFormatException e) {
	         return false;
	      }
	}

	public String getSong() {
		return songName;
	}

	public void setSong(String song) {
		this.songName = song;
	}

	public String getArtist() {
		return artistName;
	}

	public void setArtist(String artist) {
		this.artistName = artist;
	}

	public String getAlbum() {
		return albumName;
	}

	public void setAlbum(String album) {
		this.albumName = album;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String toString(){
		return this.getSong();
	}

}
