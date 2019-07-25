package org.sadtech.social.core.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sadtech.social.core.domain.content.attachment.GeoCoordinate;
import org.sadtech.social.core.domain.keyboard.KeyBoard;
import org.sadtech.social.core.utils.Description;

/**
 * Контейнер, которые содержит данные, которые будут отправлены пользователю как ответ на его запрос.
 *
 * @author upagge [08/07/2019]
 */
@EqualsAndHashCode
@ToString
@Getter
@Builder(toBuilder = true)
public class BoxAnswer {

    @Setter
    @Description("Обычное текстовое сообщение")
    private String message;

    @Description("Клавиатура - меню")
    private KeyBoard keyBoard;

    @Description("Географические координаты")
    private GeoCoordinate coordinates;

    @Description("Идентификатор стикера")
    private Integer stickerId;

    public static BoxAnswer of(String message) {
        return BoxAnswer.builder().message(message).build();
    }

}
