package dev.struchkov.godfather.context.domain.content;

import dev.struchkov.godfather.context.domain.content.attachment.Attachment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Сообщение от пользователя типа "Личное сообщение".
 *
 * @author upagge [08/07/2019]
 */
@Entity
@Table(name = "mail")
public class Mail extends Message {

    /**
     * Имя отправителя.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия отправителя.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Вложения к сообщению.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @Column(name = "attachment")
    private List<Attachment> attachments;

    /**
     * Пересланные сообщения.
     */
    @OneToMany
    @Column(name = "forward_mail")
    private List<Mail> forwardMail;

    public Mail() {
        type = ContentType.MAIL;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Mail> getForwardMail() {
        return forwardMail;
    }

    public void setForwardMail(List<Mail> forwardMail) {
        this.forwardMail = forwardMail;
    }

}
