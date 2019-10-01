package com.iluwatar.abstractdocument;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
public abstract class AbstractDocument implements Document 
  {
    private final Map<String, Object> properties;
    protected AbstractDocument(Map<String, Object> properties)
      {
        Objects.requireNonNull(properties, "properties map is required");
        this.properties = properties;
      }
    @Override
    public Void put(String key, Object value)  
      {   
        properties.put(key, value);
        return null;
      }
    @Override
    public Object get(String key)   
      {
        return properties.get(key);
      }
    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor)   
      {
        Optional<List<Map<String, Object>>> any = Stream.of(get(key)).filter(Objects::nonNull)
        .map(el -> (List<Map<String, Object>>) el).findAny();
        return any.map(maps -> maps.stream().map(constructor)).orElseGet(Stream::empty);
      }
    @Override
    public String toString()  
      {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName()).append("[");
        properties.forEach((key, value) -> builder.append("[").append(key).append(" : ").append(value).append("]"));
        builder.append("]");
        return builder.toString();
     }

}
