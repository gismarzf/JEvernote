package org.gismarzf.jevernote;

import java.net.URL;
import java.util.List;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;
import com.google.common.collect.Lists;

public class EvernoteAPI {

	private NoteStoreClient noteStore;
	private UserStoreClient userStore;
	private List<Note> notes;

	private final ENML4jAPI enml = new ENML4jAPI();

	private static final String AUTH_TOKEN =
		"S=s1:U=8b9d6:E=149a4d264f3:C=1424d2138f4:P=1cd:"
			+ "A=en-devtoken:V=2:H=b91f9552fe434cef234885ebe369586b";

	public void saveNotesToDisk() throws Exception {
		enml.setNoteStore(noteStore);
		enml.saveNotesToDisk(notes);
	}

	public URL getNoteHTMLContentPath(Note note) {
		assert enml != null;
		return enml.getPathToNotesIndexHTML().get(note.getGuid());
	}

	/**
	 * Retrieve a list of the user's notes.
	 */
	public void downloadNotes(String searchString, int maxNotes)
		throws Exception {

		// TODO only searching in first notebook
		Notebook notebook = noteStore.listNotebooks().get(0);

		NoteFilter filter = new NoteFilter();

		filter.setNotebookGuid(notebook.getGuid());
		filter.setWords(searchString);
		filter.setOrder(NoteSortOrder.CREATED.getValue());
		filter.setAscending(true);

		List<Note> noteList = Lists.newArrayList();

		for (Note note : noteStore.findNotes(filter, 0, maxNotes)
			.getNotes()) {
			noteList.add(note);
		}

		this.notes = noteList;

	}

	/**
	 * Authenticates and sets the noteStore field
	 * 
	 * @throws Exception
	 */
	public void setUp() throws Exception {

		// Set up the UserStore client and check that we can speak to the server
		EvernoteAuth evernoteAuth =
			new EvernoteAuth(EvernoteService.SANDBOX, AUTH_TOKEN);

		ClientFactory factory = new ClientFactory(evernoteAuth);
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

	public NoteStoreClient getNoteStore() {
		return noteStore;
	}

	public List<Note> getNotes() {
		return notes;
	}

}
