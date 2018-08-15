package com.kodilla.library.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

//TODO
//move to utils package
@Converter(autoApply = true)
public class LocalDateToSQLDateConverter implements AttributeConverter<LocalDate, Date> {

        @Override
        public Date convertToDatabaseColumn(LocalDate locDate) {
            return (locDate == null ? null : Date.valueOf(locDate));
        }

        @Override
        public LocalDate convertToEntityAttribute(Date sqlDate) {
            return (sqlDate == null ? null : sqlDate.toLocalDate());
        }

}