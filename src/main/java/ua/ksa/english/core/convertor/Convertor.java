package ua.ksa.english.core.convertor;

public interface Convertor <E, DTO>  {
    E convert(DTO dto);
    DTO inverse(E entity);
}
