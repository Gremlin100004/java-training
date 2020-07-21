package com.senla.carservice.container.propertyinjection.enumaration;

public enum DefaultValue {
    PROPERTY_FILE_NAME("application.properties");
    private String value;

    DefaultValue(final String value) {
        this.value = value;
    }

    // меня в свое время ругали за такой лайфхак - типа надо оставить возможность
    // получить стринговое имя енама в верхнем регистре (дефолтная реализация)
    // а для получения значения нужен метод типа get()
    // но лично я не против такого использования - можно пользоваться автопреобразованием к строке
    // например при конкатенации
    // но ты имей в виду на будущее, могут придраться
    @Override
    public String toString() {
        return value;
    }
}
