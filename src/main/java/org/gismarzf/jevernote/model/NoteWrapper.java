package org.gismarzf.jevernote.model;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Resource;
import com.google.common.collect.Lists;

public class NoteWrapper {

	private static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
	private static final Format format = new SimpleDateFormat(
		DATE_FORMAT);

	private Note note;
	private NoteStoreClient noteStore;

	private long created, updated;
	private String title, content, guid;
	private List<String> tags;
	private List<Resource> resources;

	public static class Builder {
		// required
		private Note note;

		// optional
		private long created, updated;
		private List<String> tags = Lists.newArrayList();
		private String title, content, guid;
		private List<Resource> resources;

		public Builder(Note note) {
			this.note = note;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder created(long created) {
			this.created = created;
			return this;
		}

		public Builder updated(long updated) {
			this.updated = updated;
			return this;
		}

		public Builder tags(List<String> tags) {
			this.tags = tags;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
			return this;
		}

		public Builder resources(List<Resource> resources) {
			this.resources = resources;
			return this;
		}

		public Builder guid(String guid) {
			this.guid = guid;
			return this;
		}

		public NoteWrapper build() {
			return new NoteWrapper(this);
		}

	}

	public NoteWrapper(Builder builder) {
		this.note = builder.note;
		this.title = builder.title;
		this.created = builder.created;
		this.updated = builder.updated;
		this.content = builder.content;
		this.tags = builder.tags;
		this.guid = builder.guid;
		this.resources = builder.resources;
	}

	public Note getNote() {
		return note;
	}

	public String getGuid() {
		return this.guid;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public String getContent() {
		return content;
	}

	public String getTitle() {
		return title;
	}
}
