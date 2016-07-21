package application;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

	public class Controllers {
		@FXML private ListView<Songs> listView;
		@FXML private TextField Song;
		@FXML private TextField Artist;
		@FXML private TextField Album;
		@FXML private TextField Year;
		@FXML private Label Warning;
		@FXML private Label DArtist;
		@FXML private Label DAlbum;
		@FXML private Label DYear;
		@FXML private Button Add;
		@FXML private Button Cancel;
		@FXML private Button Edit;
		@FXML private Button Delete;
		private ObservableList<Songs> SongList;

		Comparator<? super Songs> comparatorSongs = new Comparator<Songs>() {

			@Override
			public int compare(Songs o1, Songs o2) {
			return o1.getSong().compareToIgnoreCase(o2.getSong());
	        }
	    };

	    public void start() throws IOException {
		   SongList = FXCollections.observableArrayList();
		   SortedList<Songs> sortedData = new SortedList<Songs>(SongList, comparatorSongs);
		   listView.setItems(sortedData);
		   deserialize();
		   listView.getSelectionModel().select(0);

		      String artist="";
			  String album="";
			  String year="";

			 if(SongList.size()!=0){
			  artist = listView.getSelectionModel().getSelectedItem().getArtist();
			  album = listView.getSelectionModel().getSelectedItem().getAlbum();
			  year = listView.getSelectionModel().getSelectedItem().getYear();

			  if(album==null){
				  album=" ";
			  }

			  if(year==null){
				  year=" ";
			  }
			 }
			  DArtist.setText("Artist: " + artist);
			  DAlbum.setText("Album: " + album);
			  DYear.setText("Year: " +year);

		}
	    @FXML private void Cancel(ActionEvent action){
	    		Song.setText("");
	    		Artist.setText("");
	    		Album.setText("");
	    		Year.setText("");

	    }


	    @FXML private void Add(ActionEvent action){
	    	String song = Song.getText().trim();
			String album = Album.getText().trim();
			String artist = Artist.getText().trim();
			String year = Year.getText().trim();
			Songs obj;

			if(song.equals("") || artist.equals("")){
				Warning.setText("Error: Song and Artist Needed");
				Warning.setOpacity(1);
				return;
			}
			if(album.equals("") && year.equals("")){
				obj = new Songs(song,artist);
			}
			else if(year.equals("") || album.equals("")){
			   if(year.equals("")) {
				obj = new Songs(song,artist,album);
				}
			   else {
				obj = new Songs(song,artist,year);
			   }
			}
			else{
				obj = new Songs(song,artist,album,year);
			}
			for(Songs i : SongList){
				if(i.getSong().equals(song) && i.getArtist().equals(artist)){
					Warning.setText("Song already exists");
					Warning.setOpacity(1);
					return;
				}

			}
			Warning.setOpacity(0);
			SongList.add(obj);

			listView.getSelectionModel().select(obj);
			DArtist.setText("Artist: " + artist);
			DAlbum.setText("Album: " + album);
			DYear.setText("Year: " +year);

	    	Song.clear();
	    	Artist.clear();
	    	Album.clear();
	    	Year.clear();

	    	serialize();
	    }

	    @FXML private void Edit(ActionEvent action){
	    	String song = Song.getText().trim();
			String album = Album.getText().trim();
			String artist = Artist.getText().trim();
			String year = Year.getText().trim();
			Songs obj;

			if(song.equals("") || artist.equals("")){
				Warning.setText("Error: Need Song and Artist");
				Warning.setOpacity(1);
				return;
			}
			if(album.equals("") && year.equals("")){
				obj = new Songs(song,artist);
			}
			else if(year.equals("") || album.equals("")){
			   if(year.equals("")) {
				obj = new Songs(song,artist,album);
				}
			   else {
				obj = new Songs(song,artist,year);
			   }
			}
			else{
				obj = new Songs(song,artist,album,year);
			}
			for(Songs i : SongList){
				if(i.getSong().equals(song) && i.getArtist().equals(artist)){
					Warning.setText("No Duplicates Allowed");
					Warning.setOpacity(1);
					return;
				}
			}
			Songs delete = listView.getSelectionModel().getSelectedItem();
			if(delete!=null){
	    		SongList.remove(delete);
	    	}
			Warning.setOpacity(0);
			SongList.add(obj);
			Song.clear();
	    	Artist.clear();
	    	Album.clear();
	    	Year.clear();
	    	serialize();
	    }

	    @FXML private void Delete(ActionEvent action){
	    	Songs delete = listView.getSelectionModel().getSelectedItem();

	    	if(delete!=null){

	    		int pointer = listView.getSelectionModel().getSelectedIndex()+1;
	    		listView.getSelectionModel().select(pointer);
	    		SongList.remove(delete);

	    		if (SongList.size()!=0){
	    		  String artist = listView.getSelectionModel().getSelectedItem().getArtist();
				  String album = listView.getSelectionModel().getSelectedItem().getAlbum();
				  String year = listView.getSelectionModel().getSelectedItem().getYear();

				  if(album==null){
					  album=" ";
				  }

				  if(year==null){
					  year=" ";
				  }
				  DArtist.setText("Artist: " + artist);
				  DAlbum.setText("Album: " + album);
				  DYear.setText("Year: " +year);
	    		}
	    	}
	    	serialize();
        }

	    public void serialize() {
	    	try {
	           FileOutputStream fileOut = new FileOutputStream("test.ser");
	           ObjectOutputStream out = new ObjectOutputStream(fileOut);

	           for(Songs x : SongList) {
	        	   out.writeObject(x);
	           }

	           out.close();
	           fileOut.close();
	        }
	    	catch(IOException i) {
	            i.printStackTrace();
	        }
	     }

	    public void deserialize() {
	    	Songs song = null;
	        try {
	           FileInputStream fileIn = new FileInputStream("test.ser");
	           ObjectInputStream in = new ObjectInputStream(fileIn);

	           try {
	        	   while(true) {
	        		   song = (Songs) in.readObject();
	        		   SongList.add(song);
	        	   }
	           }catch(EOFException x) {
	        	   in.close();
	        	   fileIn.close();
	           }
	        }catch(IOException i) {
	           return;
	        }catch(ClassNotFoundException c) {
	           return;
	        }
        }

	  @FXML public void Details() throws IOException {
		  String artist;
		  String album;
		  String year;

		  if(SongList.size()!=0){
			  artist = listView.getSelectionModel().getSelectedItem().getArtist();
			  album = listView.getSelectionModel().getSelectedItem().getAlbum();
			  year = listView.getSelectionModel().getSelectedItem().getYear();

			  if(album==null){
				  album=" ";
			  }

			  if(year==null){
				  year=" ";
			  }
			  DArtist.setText("Artist: " + artist);
			  DAlbum.setText("Album: " + album);
			  DYear.setText("Year: " +year);
		  }
	  }
	 }


