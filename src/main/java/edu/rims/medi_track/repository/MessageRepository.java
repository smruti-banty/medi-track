package edu.rims.medi_track.repository;

import edu.rims.medi_track.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findByReceiverIdOrderByCreatedDateDesc(String receiverId);
}
