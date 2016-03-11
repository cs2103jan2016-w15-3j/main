package view;

import java.awt.Font;

import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;

public enum Icon {
        ICON_GLASS('\uf000'),
      // insert here all icon unicodes
        ICON_FOLDER_OPEN_ALT('\uf115');

        public static final String FONT_NAME = "FontAwesome";
        private final char code;

        private Icon(char code) {
          this.code = code;
        }

        public char getChar() {
          return code;
        }
}
/*
        public Text get(int iconSize) {
          return TextBuilder.create()
                  .font(Font.getFont(FONT_NAME, iconSize))
                  .text(code + "")
                  .build();
        }

        public Text get() {
          return get(12);
        }
      }
*/
