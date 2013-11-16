package org.gismarzf.jevernote.model;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;

public class Note {

	private static final String DATE_FORMAT =
		"MM/dd/yyyy HH:mm:ss";
	private static final Format format =
		new SimpleDateFormat(DATE_FORMAT);

	private com.evernote.edam.type.Note everNote;
	private NoteStoreClient noteStore;

	private String title, created, updated, tags = "";

	public Note(com.evernote.edam.type.Note note,
		NoteStoreClient noteStore) {

		this.everNote = note;
		this.noteStore = noteStore;
		this.title = note.getTitle();

		Date date = new Date(everNote.getCreated());
		this.created = format.format(date).toString();

		date = new Date(everNote.getUpdated());
		this.updated = format.format(date).toString();

		/**
		 * I don't understand why the check for null is necessary.. It seems
		 * instead of returning an EMPTY collection, they just give you null
		 * instead. But why? Also, the getNames method doesn't work on a note
		 * object
		 * 
		 */
		Iterator<String> it =
			everNote.getTagGuidsIterator();

		while (it != null && it.hasNext()) {
			try {

				this.tags +=
					noteStore.getTag(it.next()).getName();
				this.tags += it.hasNext() ? ", " : "";

			} catch (EDAMUserException
				| EDAMSystemException
				| EDAMNotFoundException | TException e) {
				e.printStackTrace();
			}
		}

	}

	public com.evernote.edam.type.Note getEverNote() {
		return everNote;
	}

	public NoteStoreClient getNoteStore() {
		return noteStore;
	}

	public String getTitle() {
		return title;
	}

	public String getCreated() {
		return created;
	}

	public String getUpdated() {
		return updated;
	}

	public String getTags() {
		return tags;
	}

}
