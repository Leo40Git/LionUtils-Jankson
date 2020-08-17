package adudecalledleo.lionutils.serialize;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import io.github.cottonmc.jankson.JanksonFactory;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * {@link ObjectFormat} that delegates to a {@link Jankson} instance.
 */
public class JanksonObjectFormat implements ObjectFormat {
    /**
     * Default {@link JsonGrammar} to use. Features:
     * <ul>
     *     <li>Comments</li>
     *     <li>Trailing commas</li>
     *     <li>Bare special numerics</li>
     *     <li>Unquoted keys</li>
     * </ul>
     */
    public static final JsonGrammar DEFAULT_GRAMMAR = JsonGrammar.builder()
            .withComments(true)
            .printTrailingCommas(true)
            .bareSpecialNumerics(true)
            .printUnquotedKeys(true)
            .build();

    /**
     * {@code JanksonObjectFormat} instance that delegates to a {@link Jankson} instance created by {@link JanksonFactory#createJankson()}
     * and uses {@linkplain #DEFAULT_GRAMMAR the default grammar} for saving objects.
     */
    public static final ObjectFormat INSTANCE = new JanksonObjectFormat(JanksonFactory.createJankson());

    private final Jankson delegate;
    private final JsonGrammar grammar;

    /**
     * Constructs a {@code JanksonObjectFormat}.
     * @param delegate {@link Jankson} instance to delegate to
     * @param grammar {@link JsonGrammar} to use while saving
     */
    public JanksonObjectFormat(Jankson delegate, JsonGrammar grammar) {
        this.delegate = delegate;
        this.grammar = grammar;
    }

    /**
     * Constructs a {@code JanksonObjectFormat} with {@linkplain #DEFAULT_GRAMMAR the default grammar}.
     * @param delegate {@link Jankson} instance to delegate to
     */
    public JanksonObjectFormat(Jankson delegate) {
        this(delegate, DEFAULT_GRAMMAR);
    }

    /**
     * {@inheritDoc}
     * @param reader source reader
     * @param typeOfT type of object to read
     * @param <T> type of object to read
     * @return the object that was read
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public <T> T read(Reader reader, Class<T> typeOfT) throws IOException {
        try (ReaderInputStream ris = new ReaderInputStream(reader, StandardCharsets.UTF_8)) {
            JsonObject obj = delegate.load(ris);
            return delegate.fromJson(obj, typeOfT);
        } catch (IOException | SyntaxError e) {
            throw new IOException("JSON deserialization failed", e);
        }
    }

    /**
     * {@inheritDoc}
     * @param src object to write
     * @param writer destination {@link Appendable}
     * @param <T> type of object to write
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public <T> void write(T src, Appendable writer) throws IOException {
        try {
            JsonElement obj = delegate.toJson(src);
            writer.append(obj.toJson(grammar));
        } catch (IOException e) {
            throw new IOException("JSON serialization failed", e);
        }
    }
}
