package org.gismarzf.jevernote;

import java.util.List;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;
import com.google.common.collect.Lists;

public class Evernote {

	private UserStoreClient userStore;
	private NoteStoreClient noteStore;
	private String newNoteGuid;

	private static final String AUTH_TOKEN =
		"S=s1:U=8b9d6:E=149a4d264f3:"
			+ "C=1424d2138f4:P=1cd:A=en-devtoken:"
			+ "V=2:H=b91f9552fe434cef234885ebe369586b";

	/**
	 * Retrieve and display a list of the user's notes.
	 */
	public List<org.gismarzf.jevernote.model.Note>
		listNotes() throws Exception {

		Notebook notebook =
			noteStore.listNotebooks().get(0);

		// Next, search for the first 100 notes in this notebook, ordering
		// by creation date
		NoteFilter filter = new NoteFilter();
		filter.setNotebookGuid(notebook.getGuid());
		filter.setOrder(NoteSortOrder.CREATED.getValue());
		filter.setAscending(true);

		NoteList noteList =
			noteStore.findNotes(filter, 0, 100);

		List<org.gismarzf.jevernote.model.Note> notes =
			Lists.newArrayList();

		for (Note note : noteList.getNotes()) {
			notes
				.add(new org.gismarzf.jevernote.model.Note(
					note, noteStore));
		}

		return notes;

	}

	public static void main(String args[]) {

		Evernote en = new Evernote();

		try {
			en.setUp();
			en.listNotes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Authenticates and sets the noteStore field
	 * 
	 * @throws Exception
	 */
	public void setUp() throws Exception {
		// Set up the UserStore client and check that we can speak to the server
		EvernoteAuth evernoteAuth =
			new EvernoteAuth(EvernoteService.SANDBOX,
				AUTH_TOKEN);

		ClientFactory factory =
			new ClientFactory(evernoteAuth);
		userStore = factory.createUserStoreClient();

		boolean versionOk =
			userStore
				.checkVersion(
					"Evernote EDAMDemo (Java)",
					com.evernote.edam.userstore.Constants.EDAM_VERSION_MAJOR,
					com.evernote.edam.userstore.Constants.EDAM_VERSION_MINOR);
		if (!versionOk) {
			System.err
				.println("Incompatible Evernote client protocol version");
			System.exit(1);
		}

		// Set up the NoteStore client
		noteStore = factory.createNoteStoreClient();
	}

}
