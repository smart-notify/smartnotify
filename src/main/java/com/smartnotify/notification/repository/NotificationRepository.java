package com.smartnotify.notification.repository;

import com.smartnotify.notification.model.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, String> {
}
