package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.PrivateMessage;

import java.util.Date;
import java.util.List;

public interface PrivateMessageDao extends GenericDao<PrivateMessage, Long> {
    List<PrivateMessage> findByEmail(String email, int firstResult, int maxResults);

    List<PrivateMessage> getDialogue(String email, Long idUser, int firstResult, int maxResults);

    List<PrivateMessage> getUnreadMessages(String email, int firstResult, int maxResults);

    List<PrivateMessage> getMessageFilteredByPeriod(String email,
                                                    Date startPeriodDate,
                                                    Date endPeriodDate,
                                                    int firstResult,
                                                    int maxResults);

    PrivateMessage findByIdAndEmail(String email, Long messageId);
}
