package org.sadtech.social.core.repository.jpa;

import org.sadtech.social.core.domain.content.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс JPA репозитория для сущности {@link Mail}. При этом сам не является репозиторием, а подставляется в
 * {@link org.sadtech.social.core.repository.impl.jpa.MailRepositoryJpaImpl}
 *
 * @author upagge [27/07/2019]
 */
public interface MailRepositoryJpa extends JpaRepository<Mail, Integer> {

    List<Mail> findByCreateDateBetween(LocalDateTime from, LocalDateTime to);

}
