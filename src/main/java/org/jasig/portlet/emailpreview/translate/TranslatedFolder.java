/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * 
 */
package org.jasig.portlet.emailpreview.translate;

import java.util.Locale;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.springframework.context.MessageSource;

/**
 * Wrapper arrond Folder to provide translation.
 * 
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public class TranslatedFolder extends Folder {

	private static final String MESSAGE_PREFIX = "inboxName.translation.";
	
	private Folder backingFolder;
	
	private MessageSource messageSource;
	
	private Locale locale;
	
	/**
	 * TranslatedFolder unique constructor.
	 * 
	 * @param folder the backing folder, cannot be null.
	 * @param translation the message source, cannot be null.
	 * @param locale the locale use for translation.
	 */
	public TranslatedFolder(final Folder folder, final MessageSource translation, final Locale locale) {
		super(folder.getStore());
		
		this.backingFolder = folder;
		this.messageSource = translation;
		this.locale = locale;
	}

	/**
	 * Override the toString method to provide translation.
	 */
	@Override
	public String toString() {
		final String originalValue = super.toString();
		final String translation = this.messageSource.getMessage(MESSAGE_PREFIX + originalValue, null, originalValue, this.locale);

		return translation;
	}

	@Override
	public String getName() {
		return this.backingFolder.getName();
	}

	@Override
	public String getFullName() {
		return this.backingFolder.getFullName();
	}

	@Override
	public Folder getParent() throws MessagingException {
		return this.backingFolder.getParent();
	}

	@Override
	public boolean exists() throws MessagingException {
		return this.backingFolder.exists();
	}

	@Override
	public Folder[] list(String pattern) throws MessagingException {
		return this.backingFolder.list();
	}

	@Override
	public char getSeparator() throws MessagingException {
		return this.backingFolder.getSeparator();
	}

	@Override
	public int getType() throws MessagingException {
		return this.backingFolder.getType();
	}

	@Override
	public boolean create(int type) throws MessagingException {
		return this.backingFolder.create(type);
	}

	@Override
	public boolean hasNewMessages() throws MessagingException {
		return this.backingFolder.hasNewMessages();
	}

	@Override
	public Folder getFolder(String name) throws MessagingException {
		return this.backingFolder.getFolder(name);
	}

	@Override
	public boolean delete(boolean recurse) throws MessagingException {
		return this.backingFolder.delete(recurse);
	}

	@Override
	public boolean renameTo(Folder f) throws MessagingException {
		return this.backingFolder.renameTo(f);
	}

	@Override
	public void open(int mode) throws MessagingException {
		this.backingFolder.open(mode);
	}

	@Override
	public void close(boolean expunge) throws MessagingException {
		this.backingFolder.close(expunge);
	}

	@Override
	public boolean isOpen() {
		return this.backingFolder.isOpen();
	}

	@Override
	public Flags getPermanentFlags() {
		return this.backingFolder.getPermanentFlags();
	}

	@Override
	public int getMessageCount() throws MessagingException {
		return this.backingFolder.getMessageCount();
	}

	@Override
	public Message getMessage(int msgnum) throws MessagingException {
		return this.backingFolder.getMessage(msgnum);
	}

	@Override
	public void appendMessages(Message[] msgs) throws MessagingException {
		this.backingFolder.appendMessages(msgs);
	}

	@Override
	public Message[] expunge() throws MessagingException {
		return this.backingFolder.expunge();
	}

}
