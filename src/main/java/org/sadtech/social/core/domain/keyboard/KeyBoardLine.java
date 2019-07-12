package org.sadtech.social.core.domain.keyboard;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import org.sadtech.social.core.utils.Description;

import java.util.ArrayList;
import java.util.List;

/**
 * Строка в меню клавиатуры {@link KeyBoard}.
 *
 * @author upagge [08/07/2019]
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class KeyBoardLine {

    @Description("Кнопки в строке")
    @Singular(value = "buttonKeyBoard")
    private List<KeyBoardButton> keyBoardButtons = new ArrayList<>();

}
