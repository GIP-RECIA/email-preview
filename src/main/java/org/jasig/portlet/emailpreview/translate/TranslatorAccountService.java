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

import javax.mail.Folder;
import javax.portlet.PortletRequest;

import org.jasig.portlet.emailpreview.AccountSummary;
import org.jasig.portlet.emailpreview.EmailMessage;
import org.jasig.portlet.emailpreview.EmailPreviewException;
import org.jasig.portlet.emailpreview.dao.IEmailAccountService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.util.Assert;

/**
 * This is a Wrapper of IEmailAccountService which perform translation on some
 * messages.
 * 
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public class TranslatorAccountService implements IEmailAccountService, InitializingBean, MessageSourceAware {

	private IEmailAccountService backingService;

	private MessageSource messageSource;
	
	@Override
	public AccountSummary getAccountSummary(PortletRequest req, int start, int max, boolean refresh, String folder)
			throws EmailPreviewException {
		return this.backingService.getAccountSummary(req, start, max, refresh, folder);
	}

	@Override
	public EmailMessage getMessage(PortletRequest req, String messageId) {
		return this.backingService.getMessage(req, messageId);
	}

	@Override
	public boolean deleteMessages(PortletRequest req, String[] messageIds) {
		return this.backingService.deleteMessages(req, messageIds);
	}

	@Override
	public boolean setSeenFlag(PortletRequest req, String[] messageIds, boolean read) {
		return this.backingService.setSeenFlag(req, messageIds, read);
	}

	@Override
	public Folder[] getAllUserInboxFolders(PortletRequest req) {
		Folder[] result = null;
		
		final Folder[] folders = this.backingService.getAllUserInboxFolders(req);

		if (folders != null) {
			final Folder[] translatedFolders = new Folder[folders.length];
			int k = 0;
			for (Folder folder : folders) {
				if (folder != null) {
					translatedFolders[k] = new TranslatedFolder(folder, this.messageSource, req.getLocale());
					k++;
				}
			}
			result = translatedFolders;
		} else {
			result = folders;
		}
		
		return result;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.backingService, "No backingService supplied !");
		Assert.notNull(this.messageSource, "MessageSource bean wasn't injected !");
	}

	/**
	 * Getter of backingService.
	 *
	 * @return the backingService
	 */
	public IEmailAccountService getBackingService() {
		return backingService;
	}

	/**
	 * Setter of backingService.
	 *
	 * @param backingService the backingService to set
	 */
	public void setBackingService(IEmailAccountService backingService) {
		this.backingService = backingService;
	}

}