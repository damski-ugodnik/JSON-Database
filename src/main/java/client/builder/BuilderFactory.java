package client.builder;

import java.util.Set;

public class BuilderFactory {
    private final Set<IRequestBuilder> builders;

    public BuilderFactory(Set<IRequestBuilder> builders) {
        this.builders = builders;
    }

    public IRequestBuilder getBuilder(String title) {
        for (IRequestBuilder builder : builders) {
            if (builder.getSupportedTitles().contains(title)) {
                return builder;
            }
        }
        throw new UnsupportedOperationException("No such operation");
    }
}
