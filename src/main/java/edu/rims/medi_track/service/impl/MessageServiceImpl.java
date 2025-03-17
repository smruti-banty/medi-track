package edu.rims.medi_track.service.impl;

import edu.rims.medi_track.entity.Message;
import edu.rims.medi_track.repository.MessageRepository;
import edu.rims.medi_track.repository.UserRepository;
import edu.rims.medi_track.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void sendMessage(String senderId, String receiverId, String content) {
        var sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        var receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        var message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);

        messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(String userId) {
        return messageRepository.findByReceiverIdOrderByCreatedDateDesc(userId);
    }
}
