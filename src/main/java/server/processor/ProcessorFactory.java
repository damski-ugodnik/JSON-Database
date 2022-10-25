package server.processor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProcessorFactory {
    private final Map<String, ICommandProcessor> processors;

    public ProcessorFactory(List<ICommandProcessor> processors) {
        this.processors = processors.stream().collect(Collectors.toMap(ICommandProcessor::getSupportedTitle, Function.identity()));
    }

    public ICommandProcessor getProcessor(String title) {
        return processors.get(title);
    }
}
