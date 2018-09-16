package ua.ksa.english.core.convertor;

public interface Converter<E, DTO>  {
    E convert(DTO dto);
    DTO inverse(E entity);
}
