package edu.rims.medi_track.service;

import edu.rims.medi_track.entity.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(String senderId, String receiverId, String content);

    List<Message> getMessages(String userId);
}
